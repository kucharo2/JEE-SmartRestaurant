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
    $scope.selectedDishes = [];

    $http.get(apiPrefix + "/menu/items")
        .then(function (response) {
            $scope.items = response.data;
        });

    $scope.selectMainDish = function (id) {
        for (var index = 0; index < $scope.items.length; index++) {
            if (id === $scope.items[index].id) {
                var dish = $scope.items[index];
                dish["count"] = 1;
                $scope.detailItem = dish;
                $scope.selectedDishes = [];
                $scope.selectedDishes.push(dish);
                fetchCombinationsForItem(id);
                return;
            }
        }
    };

    $scope.selectSideDish = function (id) {
        for (var index = 0; index < $scope.sideDishes.length; index++) {
            if (id === $scope.sideDishes[index].id) {
                var sideDish = $scope.sideDishes[index];
                if((index = $scope.selectedDishes.indexOf(sideDish)) >= 0){
                    $scope.selectedDishes[index].count++;
                }else {
                    sideDish["count"] = 1;
                    $scope.selectedDishes.push(sideDish);
                }
                return;
            }
        }
    };

    var fetchCombinationsForItem = function (id) {
        $http.get(apiPrefix + "/menu/item/" + id + "/sideDish")
            .then(function (response) {
                $scope.sideDishes = response.data;
            });

    }

});
