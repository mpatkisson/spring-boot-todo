// Creates the redbarn namespace.
//  red - The redbarn global variable.
(function (red) {
    'use strict';

    red.iterate = function ($ele, items, binder) {
        var $first = $ele.children().first(),
            template = $('<div>').append($first.clone()).html();
        $ele.empty();
        items.forEach(function (item) {
            var $template = $(template);
            if (binder) {
                binder($template, item);
            }
            $ele.append($template);
        });
    };

    red.getResult = function (uuid) {
        return $('.' + uuid).html();
    }

})(redbarn);
