/**
 * Angular controller for login
 * @type {angular.controller}
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 */
app.controller('LoginController', function LoginController($scope, LoginService) {
    $scope.username = "";
    $scope.password = "";

    /**
     * Logs user into application
     */
    var loginUser = function () {
        //TODO
        LoginService.loginUser();
    }

    $("#loginForm").submit(function () {
        loginUser();
    })

});