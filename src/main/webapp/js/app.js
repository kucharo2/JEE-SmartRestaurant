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
        var orderItem = [];
        for(var i=0; i < $scope.selectedDishes.length; i++){
            var dish = $scope.selectedDishes[i];
            orderItem.push(dish);
        }
        $scope.order.push(orderItem);
        $scope.selectedDishes = [];
        $scope.detailItem = null;
    };

    $scope.addSideDish = function (sideDish) {
        var index;
        if((index = $scope.selectedDishes.indexOf(sideDish)) >= 0){
            $scope.selectedDishes[index].count++;
        }else {
            sideDish["count"] = 1;
            sideDish["main"] = false;
            $scope.selectedDishes.push(sideDish);
        }
    };

    $scope.decreaseAmountOfDish = function (dish) {
        var index;
        if((index = $scope.selectedDishes.indexOf(dish)) >= 0){
            if(dish.main){
                $scope.selectedDishes = [];
                $scope.detailItem = null;
            } else{
                $scope.selectedDishes[index].count--;
                if($scope.selectedDishes[index].count === 0){
                    $scope.selectedDishes.splice(index, 1);
                }
            }
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
        dish["main"] = true;
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
