/**
 * Angular service for errors
 * @type {angular.service}
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 */
app.service('ErrorService', function ($mdToast) {
    const registerErrorsTranslations = {
        ERR_EMPTY_USERNAME: "Uživatelské jméno nesmí být prázdné",
        ERR_LENGTH_USERNAME: "Uživatelské jméno musí mít alespoň 6 znaků.",
        ERR_SAME_USERNAME: "Uživatelské jméno už existuje.",
        ERR_EMPTY_PASSWORD: "Heslo musí být vyplněno.",
        ERR_LENGTH_PASSWORD: "Heslo musí mít alespoň 6 znaků.",
        ERR_LENGTH_EMAIL: "Email musí mít alespoň 6 znaků.",
        ERR_BAD_FORMAT_EMAIL: "Email má špatný formát.",
        ERR_EMPTY_PHONE: "Telefon musí být vyplněn.",
        ERR_LENGTH_PHONE: "Telefon musí mít 13 znaků (s předvolbou).",
        ERR_BAD_FORMAT_PHONE: "Telefon musí být ve formátu +420777999888",
        ERR_PASSWORD_MATCH: "Hesla se neshodují"
    };

    /**
     * Generic error function for all fe - be communication errors
     * @param response
     */
    this.serverErrorCallback = function (response) {
        $mdToast.show(
            $mdToast.error500()
        );
    };


    /**
     * Translates be error key to frontent user friendly message
     * @param key
     * @returns {*}
     */
    this.translateErrorMessage = function (key) {
        return registerErrorsTranslations[key];
    };

    /**
     * Sets error to specific field - note that proper html structure is needed for this to be functional
     * @param fieldName
     * @param errorKey
     */
    this.setErrorToField = function (fieldName, errorKey) {
        var errorDiv = $("#" + fieldName);
        if (errorDiv.html() !== "") {
            errorDiv.html(
                errorDiv.html() + "<br>" +
                this.translateErrorMessage(errorKey)
            );
        } else {
            errorDiv.html(
                this.translateErrorMessage(errorKey)
            );
        }
    };

    /**
     * Clears specific field error
     * @param fieldName
     */
    this.clearFieldError = function (fieldName) {
        var errorDiv = $("#" + fieldName);
        errorDiv.html("");
    }


});