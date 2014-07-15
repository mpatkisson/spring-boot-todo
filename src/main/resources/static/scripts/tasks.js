
function TaskCtrl($scope, $http, $window) {
    var baseUrl = 'http://localhost:8080/api/tasks';
    $scope.tasks = [];
    $scope.editing = false;

    $http.get(baseUrl).
          success(function (data) {
              for (var i = 0; i < data.length; i++) {
                  var task = data[i];
                  $scope.tasks.push(task);
              }        
          }).
          error(function (data) {
            alert("Unable to get all tasks.");
          });
    

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
    }

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
    }

}

