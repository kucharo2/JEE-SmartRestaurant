/**
 * Angular service for user registration
 * @type {angular.service}
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 */
app.service('RegistrationService', function ($http) {

    /**
     * Registers user
     * @returns {null}
     */
    this.registerUser = function (user) {
        return $http({
            url: apiPrefix + "/account/register",
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            data: user
        });
    }

});