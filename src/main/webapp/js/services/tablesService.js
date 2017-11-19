/**
 * Angulat service for tables selection
 * @type {angular.service}
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 */
app.service('TablesService', function ($http) {

    /**
     * Gets all tables from server
     * @returns {HttpPromise}
     */
    this.getAllTables = function () {
        return $http.get(apiPrefix + "/table/all");
    }

});