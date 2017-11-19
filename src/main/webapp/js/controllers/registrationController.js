/**
 * Angular controller for registration
 * @type {angular.controller}
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 */
app.controller('RegistrationController', function LoginController($scope, RegistrationService) {
    $scope.errors = [];
    $scope.username = "";
    $scope.firstName = "";
    $scope.lastName = "";
    $scope.password = "";
    $scope.passwordConfirm = "";

    /**
     * Registers user
     */
    var registerUser = function () {
        //TODO
        RegistrationService.registerUser();
    }

    $("#registrationForm").submit(function () {
        $scope.errors = [];
        if($scope.password === $scope.passwordConfirm){
            registerUser();
        } else {
            $scope.errors.push("Hesla se neshodují");
        }
    })

});