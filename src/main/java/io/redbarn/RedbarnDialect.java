package io.redbarn;

import com.google.common.io.CharStreams;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Created by mike on 3/16/2015.
 */
public class RedbarnDialect extends AbstractDialect {

    private static final Logger logger = LoggerFactory.getLogger(RedbarnDialect.class);
    private ScriptEngine engine;
    private Locale locale;

    public RedbarnDialect(Locale locale) {
        this();
        this.locale = locale;
    }

    public RedbarnDialect() {
        this.locale = Locale.US;
        try {
            this.engine = getScriptEngine();
        } catch (IOException e) {
            logger.debug(e.getMessage(), e.getStackTrace());
        } catch (ScriptException e) {
            logger.debug(e.getMessage(), e.getStackTrace());
        }
    }

    @Override
    public String getPrefix() {
        return "redbarn";
    }

    @Override
    public Set<IProcessor> getProcessors() {
        final Set<IProcessor> processors = new HashSet<IProcessor>();
        try {
            processors.add(new BindAttrProcessor(engine));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return processors;
    }

    private synchronized ScriptEngine getScriptEngine()
            throws IOException, ScriptException {
        logger.info("Creating Script Engine");
        ScriptEngineManager manager = new ScriptEngineManager(null);
        ScriptEngine engine = manager.getEngineByName("nashorn");

        logger.info("Loading global aliases");
        evaluateScriptResource(engine, "redbarn/globals.js");

        logger.info("Loading redbarn");
        evaluateScriptResource(engine, "redbarn/redbarn.js");

        logger.info("Loading cheerio");
        evaluateScriptResource(engine, "redbarn/cheerio.js");

        return engine;
    }

    public static synchronized Object evaluateScriptResource(
            ScriptEngine engine,
            String resource)
            throws IOException, ScriptException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(resource);
        InputStreamReader reader = new InputStreamReader(is);
        String script = CharStreams.toString(reader);
        return engine.eval(script);
    }
}
