/**
 * Angular service for getting offer
 * @type {angular.service}
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 */
app.service('MenuListService', function ($http) {

    /**
     * Gets all dishes grouped by categories from server
     * @returns {HttpPromise}
     */
    this.getAllDishesGroupedByCategories = function(){
        return $http.get(apiPrefix + "/menu/dishes");
    };

    /**
     * Fetches combinations for selected dish from server
     * @param id id of dish
     * @returns {HttpPromise}
     */
    this.fetchCombinationsForItem = function (id) {
        return $http.get(apiPrefix + "/menu/" + id + "/sideDish");
    };
});
