/**
 * Angular controller for registration
 */
app.controller('RegistrationController', function LoginController($scope, RegistrationService) {
    $scope.errors = [];
    $scope.username = "";
    $scope.firstName = "";
    $scope.lastName = "";
    $scope.password = "";
    $scope.passwordConfirm = "";

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