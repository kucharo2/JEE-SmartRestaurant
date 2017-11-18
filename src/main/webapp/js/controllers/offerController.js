/**
 * Angular controller for dishes list
 */
app.controller('MenuListController', function MenuListController($rootScope, $scope, MenuListService) {
    $scope.selectedDishes = [];
    $scope.order = [];

    MenuListService.getAllDishesGroupedByCategories().then(function (response) {
        $scope.items = response.data;
    });

    $scope.startSelectingMainDish = function (category, dish) {
        if($scope.selectedDishes.length > 0){
            showConfirmSelectionDialog();
            $("#continuePrevSelectionBtn").off('click').on('click', function () {
                hideConfirmSelectionDialog();
            });
            $("#cancelPrevSelectionBtn").off('click').on('click', function () {
                $scope.discardSelection();
                selectMainDish(category, dish);
                hideConfirmSelectionDialog();
            });
            $("#confirmPrevSelectionBtn").off('click').on('click', function () {
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
        $rootScope.$emit("addToOrder", {orderItem : orderItem});
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

    $scope.toggleCategoryItems = function (categoryIndex) {
        $('.category' + categoryIndex).toggleClass('invisibleElement')
    };

    var selectMainDish = function (category, dish) {
        dish["count"] = 1;
        dish["main"] = true;
        $scope.detailItem = dish;
        $scope.selectedDishes = [];
        $scope.selectedDishes.push(dish);
        MenuListService.fetchCombinationsForItem(dish.id).then(function (response) {
            $scope.sideDishes = response.data;
        });
    };

    var showConfirmSelectionDialog = function(){
        $("#unconfirmedSelectionModal").show();
    }

    var hideConfirmSelectionDialog = function () {
        $("#unconfirmedSelectionModal").hide();
    }
});