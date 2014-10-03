
function TaskCtrl($scope, $http, $window) {
    var baseUrl = '/api/tasks';
    $scope.tasks = [];
    $scope.editing = false;

    for (var i = 0; i < $window.initial.length; i++) {
        var task = $window.initial[i];
        $scope.tasks.push(task);
    }

    $scope.total = function() {
        return $scope.tasks.length;
    };

    $scope.addTask = function() {
        var task = {
            "item": $scope.taskText
        },
        url = baseUrl + '/task';
        $http.post(url, task).
              success(function(data){
                $scope.tasks.push(data);
                $scope.taskText = "";
              }).
              error(function(data) {
                alert("Unable to save task.");
              });
    };

    $scope.updateTask = function(task) {
        var url = baseUrl + '/task';
        $http.put(url, task).
            success(function(data){
                $scope.editing = false;
            }).
            error(function(data) {
                alert("Unable to save task.");
            });
        $scope.editing = false;
    };

    $scope.removeTask = function(index) {
        var task = $scope.tasks[index],
            url = baseUrl + '/task/' + task.id;
        $http.delete(url).
              success(function(data) {
                $scope.tasks.splice(index, 1);
              }).
              error(function(data) {
                 alert("Unable to delete task.");
              });
    };

}
