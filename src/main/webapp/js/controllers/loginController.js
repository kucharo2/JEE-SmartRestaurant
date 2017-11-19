/**
 * Angular controller for login
 */
app.controller('LoginController', function LoginController($scope, LoginService) {
    $scope.username = "";
    $scope.password = "";

    var loginUser = function () {
        //TODO
        LoginService.loginUser();
    }

    $("#loginForm").submit(function () {
        loginUser();
    })

});