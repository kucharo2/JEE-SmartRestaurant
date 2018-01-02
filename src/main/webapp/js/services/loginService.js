/**
 * Angular service for logging into application
 * @type {angular.service}
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 */
app.service('LoginService', function ($http) {

    /**
     * Logins user into application
     * @returns {HttpPromise}
     */
    this.loginUser = function (base64string) {
        return $http({
            url: apiPrefix + "/account/login",
            method: "POST",
            headers: {
                "Content-Type": "text/plain"
            },
            data: base64string
        });
    };

    /**
     * Logins user into application
     * @returns {HttpPromise}
     */
    this.loginWaiter = function (base64string) {
        return $http({
            url: apiPrefix + "/account/login/waiter",
            method: "POST",
            headers: {
                "Content-Type": "text/plain"
            },
            data: base64string
        });
    };

    /**
     * Logouts user - removes him from local storage
     */
    this.logout = function () {
       localStorage.removeItem("loggedUser");
    };

    /**
     * Gets logged user
     * @returns {HttpPromise}
     */
    this.getLoggerUser = function () {
        var base64string = localStorage.getItem("loggedUser");
        if (base64string !== null && base64string !== undefined && base64string !== "") {
            return $http({
                url: apiPrefix + "/account/login",
                method: "POST",
                headers: {
                    "Content-Type": "text/plain"
                },
                data: base64string
            });
        }
        return null;
    };

});