app.service('MenuListService', function ($http) {
    this.getAllDishesGroupedByCategories = function(){
        return $http.get(apiPrefix + "/menu/dishes", {headers: $http.defaults.headers});
    };

    this.fetchCombinationsForItem = function (id) {
        return $http.get(apiPrefix + "/menu/" + id + "/sideDish");
    };
});
