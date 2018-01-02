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
    var loginUser = function (shouldBeWaiter) {
        $scope.errors = [];
        var base64credentials = $base64.encode($scope.username+":"+$scope.password);
        console.log(shouldBeWaiter);
        if(shouldBeWaiter){
            LoginService.loginWaiter(base64credentials).then(function (response) {
                if(response.data !== null && response.data !== ""){
                    if(response.data.accountRole === "WAITER"){
                        $location.path('/cashDesk')
                        localStorage.setItem("loggedWaiter", base64credentials);
                    }
                } else {
                    $scope.username = "";
                    $scope.password = "";
                    $scope.errors.push("Buď nejste čísník nebo jste zadali nesprávné uživatelské jméno nebo heslo.");
                }
            }, ErrorService.serverErrorCallback);
        } else {
            LoginService.loginUser(base64credentials).then(function (response) {
                if(response.data !== null && response.data !== ""){
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
        }
    };

    /**
     * Logouts user
     */
    $scope.logout = function () {
        LoginService.logout();
        $http.defaults.headers.common.Authorization = 'Basic ' + $base64.encode("anonymous:cvut2017");
        $scope.loggedUser = null;
        $scope.toggleUsersProfile();
        $mdToast.show(
            $mdToast.simple()
                .textContent('Uživatel byl odhlášen!')
                .position('bottom right')
                .hideDelay(1500)
        );
        $rootScope.$emit("getActiveOrder");
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


    /**
     * Gets logged user - checks credentials with server
     */
    $scope.getLoggedUser = function(){
        var promise;
        if((promise = LoginService.getLoggerUser()) !== null){
            promise.then(function (response) {
                if(response.data !== ""){
                    $scope.loggedUser = response.data;
                }
                $rootScope.$emit("getActiveOrder");
            }, ErrorService.serverErrorCallback);
        }
    };

    //prepare listeners for login form
    var loginForm;
    if((loginForm = $("#loginForm"))){
        loginForm.submit(function () {
            var shouldBeWaiter = $location.search().waiter;
            loginUser(shouldBeWaiter !== undefined && parseInt(shouldBeWaiter) === 1);
        });
    }

    $scope.getLoggedUser();

});