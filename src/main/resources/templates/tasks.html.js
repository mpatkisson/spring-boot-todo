
function writeTask($, task) {
    var span = $.find('span');
    span.text(task.item);
}

function transform(tasks, myTask, options) {
    $('h1').text(myTask.item);
    $('h4').text('Total = ' + tasks.length);
    redbarn.iterate('#tasks', tasks, writeTask);
    for (var property in options) {
        print(property);
    }
    print(options.httpSession.id);
    print(options.httpSession.getId());
}