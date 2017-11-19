/**
 * Angular controller for login
 * @type {angular.controller}
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 */
app.controller('LoginController', function LoginController($scope, $mdToast, $base64, $location, LoginService) {
    $scope.errors = [];
    $scope.username = "";
    $scope.password = "";

    /**
     * Logs user into application
     */
    var loginUser = function () {
        var base64credentials = $base64.encode($scope.username+":"+$scope.password);
        LoginService.loginUser(base64credentials).then(function (response) {
            if(response.data !== ""){
                localStorage.setItem("loggedUser", base64credentials);
                $location.path('/')
            } else {
                $scope.username = "";
                $scope.password = "";
                $scope.errors.push("Nesprávné uživatelské jméno nebo heslo.");
            }
        });
    };

    /**
     * Logouts user
     */
    $scope.logout = function () {
        LoginService.logout();
        $scope.loggedUser = null;
        $scope.toggleUsersProfile();
        $mdToast.show(
            $mdToast.simple()
                .textContent('Uživatel byl odhlášen!')
                .position('bottom right')
                .hideDelay(1500)
        );
    };

    /**
     * Toggles user profile
     */
    $scope.toggleUsersProfile = function () {
        $('#userModal').toggle();
    };

    /**
     * Shows login page
     */
    $scope.showLoginPage = function () {
        $location.path("/login");
    };


    $scope.getLoggedUser = function(){
        var promise;
        if((promise = LoginService.getLoggerUser()) !== null){
            promise.then(function (response) {
                if(response.data !== ""){
                    $scope.loggedUser = response.data;
                }
            })
        }else {
            //no user logged in
        }
    };

    var loginForm;
    if((loginForm = $("#loginForm"))){
        loginForm.submit(function () {
            loginUser();
        });
    }

    $scope.getLoggedUser();

});