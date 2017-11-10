/**
 * Module for index.html page
 * @type {angular.Module}
 */

var app = angular.module('smartRestaurantApp', []);
var apiPrefix = "api/v1";

/**
 * Angular controller for dishes list
 */
app.controller('MenuListController', function MenuListController($scope, $http) {
    $http.get(apiPrefix + "/menu/items")
        .then(function (response) {
            $scope.items = response.data;
        });

    $scope.fetchDetailOfItem = function (id) {
        for (var index = 0; index < $scope.items.length; index++) {
            if (id === $scope.items[index].id) {
                $scope.detailItem = $scope.items[index];
                fetchCombinationsForItem(id);
                return;
            }
        }
    }

    var fetchCombinationsForItem = function (id) {
        $http.get(apiPrefix + "/menu/item/" + id + "/sideDish")
            .then(function (response) {
                $scope.sideDishes = response.data;
            });

    }

});
