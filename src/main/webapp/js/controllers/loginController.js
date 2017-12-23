/**
 * Angular controller for login
 * @type {angular.controller}
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 */
app.controller('LoginController', function LoginController($scope, $rootScope, $http, $mdToast, $base64, $location, LoginService, ErrorService) {
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
                $location.path('/');
                $http.defaults.headers.common.Authorization = 'Basic ' + base64credentials;
                $rootScope.$emit("getActiveOrder");
            } else {
                $scope.username = "";
                $scope.password = "";
                $scope.errors.push("Nesprávné uživatelské jméno nebo heslo.");
            }
        }, ErrorService.serverErrorCallback);
    };

    /**
     * Logouts user
     */
    $scope.logout = function () {
        LoginService.logout();
        $http.defaults.headers.common.Authorization = 'Basic ' + $base64.encode("anonymous:cvut2017");
        $rootScope.$emit("getActiveOrder");
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
                    var loggedUserBase64 = localStorage.getItem("loggedUser");
                    if(loggedUserBase64 !== null && loggedUserBase64 !== undefined ) {
                        $http.defaults.headers.common.Authorization = 'Basic ' + loggedUserBase64;
                    }
                }
                $rootScope.$emit("getActiveOrder");
            }, ErrorService.serverErrorCallback);
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