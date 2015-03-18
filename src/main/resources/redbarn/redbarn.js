// Creates the redbarn namespace.
//  red - The redbarn global variable.
(function (red) {
    'use strict';

    red.iterate = function (selector, items, binder) {
        var $ele = $(selector),
            template = $ele.html();
        $ele.html('');
        items.forEach(function (item) {
            var $template = $(template);
            if (binder) {
                binder($template, item);
            }
            $ele.append($template);
        });
    };

})(redbarn);
