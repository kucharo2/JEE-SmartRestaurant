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

    $http.get(apiPrefix + "/menu/dishes")
        .then(function (response) {
            console.log(response.data);
            $scope.items = response.data;
        });

    $scope.selectMainDish = function (category, dish) {
        dish["count"] = 1;
        $scope.detailItem = dish;
        $scope.selectedDishes = [];
        $scope.selectedDishes.push(dish);
        fetchCombinationsForItem(dish.id);
    };

    $scope.selectSideDish = function (sideDish) {
        var index = -1;
        if((index = $scope.selectedDishes.indexOf(sideDish)) >= 0){
            $scope.selectedDishes[index].count++;
        }else {
            sideDish["count"] = 1;
            $scope.selectedDishes.push(sideDish);
        }
    };

    var fetchCombinationsForItem = function (id) {
        $http.get(apiPrefix + "/menu/" + id + "/sideDish")
            .then(function (response) {
                $scope.sideDishes = response.data;
            });

    }

    var showWarningDialog = function(){
       $("#unconfirmedSelectionModal").show();
    }

});
