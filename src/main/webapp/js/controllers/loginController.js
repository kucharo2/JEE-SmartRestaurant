/**
 * Angular controller for dishes list
 */
app.controller('LoginController', function LoginController($scope, LoginService) {
    $scope.username = "";
    $scope.password = "";

    var loginUser = function () {
        //TODO
        LoginService.loginUser();
    }

    $("#loginForm").submit(function () {
        console.log($scope.username);
        console.log($scope.password);
        loginUser();
    })

});