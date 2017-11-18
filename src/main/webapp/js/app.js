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
    $scope.order = [];

    $http.get(apiPrefix + "/menu/dishes")
        .then(function (response) {
            console.log(response.data);
            $scope.items = response.data;
        });

    $scope.startSelectingMainDish = function (category, dish) {
        if($scope.selectedDishes.length > 0){
            showConfirmSelectionDialog();
            $("#continuePrevSelectionBtn").click(function () {
               hideConfirmSelectionDialog();
            });
            $("#cancelPrevSelectionBtn").click(function () {
                $scope.discardSelection();
                selectMainDish(category, dish);
                hideConfirmSelectionDialog();
            });
            $("#confirmPrevSelectionBtn").click(function () {
                $scope.confirmSelection();
                selectMainDish(category, dish);
                hideConfirmSelectionDialog();
            });
        } else {
            selectMainDish(category, dish);
        }
    };

    $scope.discardSelection = function () {
        $scope.selectedDishes = [];
    };

    $scope.confirmSelection = function(){
        for(var i=0; i < $scope.selectedDishes.length; i++){
            var dish = $scope.selectedDishes[i];
            var index = -1;
            if((index = $scope.order.indexOf(dish)) >= 0){
                $scope.order[index].count++;
            }else {
                dish["count"] = 1;
                $scope.order.push(dish);
            }
        }
        $scope.selectedDishes = [];
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

    };

    var selectMainDish = function (category, dish) {
        dish["count"] = 1;
        $scope.detailItem = dish;
        $scope.selectedDishes = [];
        $scope.selectedDishes.push(dish);
        fetchCombinationsForItem(dish.id);
    }

    var showConfirmSelectionDialog = function(){
       $("#unconfirmedSelectionModal").show();
    }

    var hideConfirmSelectionDialog = function () {
        $("#unconfirmedSelectionModal").hide();
    }

});
