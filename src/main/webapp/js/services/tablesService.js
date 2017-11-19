app.service('TablesService', function ($http) {

    this.getAllTables = function () {
        return $http.get(apiPrefix + "/table/all");
    }

});