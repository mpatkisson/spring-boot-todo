
function writeTask($ele, task) {
    var span = $ele.find('span');
    span.text(task.item);
}

function transform(tasks, myTask, options) {
    $('h1').text(myTask.item);
    $('h4').text('Total = ' + tasks.length);
    redbarn.iterate($('#tasks'), tasks, writeTask);
}