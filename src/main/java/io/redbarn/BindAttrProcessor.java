package io.redbarn;

import com.google.common.io.CharStreams;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.Arguments;
import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.VariablesMap;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.attr.AbstractUnescapedTextChildModifierAttrProcessor;
import org.thymeleaf.resourceresolver.IResourceResolver;
import org.thymeleaf.templateresolver.TemplateResolution;
import org.thymeleaf.util.DOMUtils;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by mike on 3/16/2015.
 */
public class BindAttrProcessor extends AbstractUnescapedTextChildModifierAttrProcessor {

    private static final Logger logger =
            LoggerFactory.getLogger(BindAttrProcessor.class);
    private ScriptEngine engine;

    public BindAttrProcessor(ScriptEngine engine) throws IOException, ScriptException {
        super("bind");
        this.engine = engine;
    }

    @Override
    protected String getText(final Arguments arguments,
                             final Element element,
                             final String attributeName) {
        String text = "";
        try {

            // Get the element's HTML and load it into a global variable
            // called "$"
            String cssClass = element.getAttributeValue("class");
            String uuid = UUID.randomUUID().toString();
            element.setAttribute("class", cssClass + " " + uuid);
            String html = DOMUtils.getHtml5For(element);
            html = html.replaceAll("\\r|\\n", "");
            String expression = "var $ = cheerio.load('" + html + "')";
            Object dollar = engine.eval(expression);

            // Read the .html.js file and evaluate it
            TemplateResolution resolution = arguments.getTemplateResolution();
            IResourceResolver resolver = resolution.getResourceResolver();
            TemplateProcessingParameters parameters = arguments.getTemplateProcessingParameters();
            String resource = resolution.getResourceName() + ".js";
            InputStream stream = resolver.getResourceAsStream(parameters, resource);
            final InputStreamReader reader = new InputStreamReader(stream);
            String function = CharStreams.toString(reader);
            Object eval = engine.eval(function);

            // Read the parameters on the function, find their corresponding
            // models and store them in as key / value pairs.
            String functionName = element.getAttributeValue(attributeName);
            String functionText = engine.getContext().getAttribute(functionName).toString();
            int start = functionText.indexOf('(') + 1;
            int end = functionText.indexOf(')');
            String paramCsv = functionText.substring(start, end);
            String[] params = paramCsv.split(",");
            Object[] args = new Object[params.length];
            for (int i = 0; i < params.length; i++) {
                String param = params[i].trim();
                Object value = getWebVariable(arguments, param);
                args[i] = value;
            }

            // Invoke the function evaluated in the html.js file arguing the
            // correct model values
            Invocable invocable = (Invocable) engine;
            invocable.invokeFunction(functionName, args);

            // Get the results
            Object redbarn = engine.get("redbarn");
            Object result = invocable.invokeMethod(redbarn, "getResult", uuid);

            text = result.toString();
            element.setAttribute("class", cssClass);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text;
    }

    @Override
    public int getPrecedence() {
        return 10000;
    }

    private Object getWebVariable(Arguments args, String key) {
        Object value;
        if (key.toLowerCase().equals("options")) {
            value = getOptions(args);
        } else {
            IContext context = args.getContext();
            VariablesMap<String, Object> variables = context.getVariables();
            value = variables.get(key);
        }
        return value;
    }

    private Map<String, Object> getOptions(Arguments args) {
        HashMap map = new HashMap();
        IWebContext context = (IWebContext) args.getContext();
        VariablesMap<String, Object> variables = context.getVariables();
        map.put("cxt", context);
        map.put("locale", context.getLocale());
        map.put("vars", variables);
        map.put("param", variables.get("param"));
        map.put("session", variables.get("session"));
        map.put("application", variables.get("application"));
        map.put("httpServletRequest", context.getHttpServletRequest());
        map.put("httpSession", context.getHttpSession());
        return map;
    }

}
