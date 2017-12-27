/**
 * Angular controller for registration
 * @type {angular.controller}
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 */
app.controller('RegistrationController', function LoginController($scope, RegistrationService, $base64, $location, $mdToast, ErrorService) {
    $scope.username = "";
    $scope.firstName = "";
    $scope.lastName = "";
    $scope.phone = "";
    $scope.email = "";
    $scope.password = "";
    $scope.passwordConfirm = "";

    /**
     * Registers user
     */
    var registerUser = function () {
        var user = {
            "username": $scope.username.trim(),
            "password": $base64.encode($scope.password.trim()),
            "firstName": $scope.firstName.trim(),
            "lastName": $scope.lastName.trim(),
            "email" : $scope.email.trim(),
            "phone" : $scope.phone.trim()
        };
        RegistrationService.registerUser(user).then(function (response) {
            if(response.data["responseData"] === true){
                $mdToast.show(
                    $mdToast.simple()
                        .textContent('Váš účet byl vytvořen! Nyní se můžete přihlásit.')
                        .position('bottom right')
                        .hideDelay(1500)
                );
                $location.path("/login");
                return;
            }
            $formErrors = response.data["formErrors"];
            if($formErrors !== null){
                for(var i=0; i < $formErrors.length; i++){
                    ErrorService.setErrorToField($formErrors[i]["fieldName"]+"-error", $formErrors[i]["errorMessage"]);
                }
            }
        }, ErrorService.serverErrorCallback);
    };

    //bind listeners for registration form
    $("#registrationForm").submit(function () {
        if(validateRegistrationForm()){
            registerUser();
        }
    });

    /**
     * Frontend validation for registration form
     * @returns {boolean}
     */
    var validateRegistrationForm = function(){
        resetErrors();
        var valid = true;
        if($scope.password !== $scope.passwordConfirm){
            ErrorService.setErrorToField("password-error", "ERR_PASSWORD_MATCH");
            ErrorService.setErrorToField("passwordRetype-error", "ERR_PASSWORD_MATCH");
            valid = false;
        }
        return valid;
    };

    /**
     * Clears current errors
     */
    var resetErrors = function () {
        ErrorService.clearFieldError("username-error");
        ErrorService.clearFieldError("firstName-error");
        ErrorService.clearFieldError("lastName-error");
        ErrorService.clearFieldError("phone-error");
        ErrorService.clearFieldError("email-error");
        ErrorService.clearFieldError("password-error");
        ErrorService.clearFieldError("passwordRetype-error");
    }

});