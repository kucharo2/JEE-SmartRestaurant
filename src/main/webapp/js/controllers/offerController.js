/**
 * Angular controller for dishes list
 * @type {angular.controller}
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 */
app.controller('MenuListController', function MenuListController($rootScope, $scope, $location, $cookies, MenuListService) {
    if(($scope.tableId = $cookies.get("table")) === undefined) {
        $location.path("/tables");
    }
    $scope.selectedDishes = [];
    $scope.order = [];
    $scope.selectionPrice = 0;

    MenuListService.getAllDishesGroupedByCategories().then(function (response) {
        $scope.items = response.data;
    });

    /**
     * Starts to select main dish - if previous selection was not commited provides modal window with solution
     * @param category
     * @param dish
     */
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

    /**
     * Discards currently selected dishes
     */
    $scope.discardSelection = function () {
        $scope.selectedDishes = [];
    };

    /**
     * Confirms currently selected dishes and informs ordering controller to add it into order
     */
    $scope.confirmSelection = function(){

        var orderItem = [];
        for(var i=0; i < $scope.selectedDishes.length; i++){
            var dish = $scope.selectedDishes[i];
            orderItem.push(dish);
        }
        $rootScope.$emit("addToOrder", {orderItem : orderItem});
        $scope.selectedDishes = [];
        $scope.detailItem = null;
        $scope.selectionPrice = 0;
    };

    /**
     * Adds side dish into current selection of dishes
     * @param sideDish
     */
    $scope.addSideDish = function (sideDish) {
        var index;
        if((index = $scope.selectedDishes.indexOf(sideDish)) >= 0){
            $scope.selectedDishes[index].count++;
        }else {
            sideDish["count"] = 1;
            sideDish["main"] = false;
            $scope.selectedDishes.push(sideDish);
        }
        $scope.selectionPrice += sideDish.price;
    };

    /**
     * Removes 1 dish from current selection. If the dish is main remove all side dishes which belongs to it
     * @param dish
     */
    $scope.decreaseAmountOfDish = function (dish) {
        var index;
        if((index = $scope.selectedDishes.indexOf(dish)) >= 0){
            if(dish.main){
                $scope.selectedDishes = [];
                $scope.detailItem = null;
                $scope.selectionPrice = 0;
            } else{
                $scope.selectedDishes[index].count--;
                if($scope.selectedDishes[index].count === 0){
                    $scope.selectedDishes.splice(index, 1);
                }
                $scope.selectionPrice -= sideDish.price;
            }
        }
    };

    /**
     * Toggles items in category
     * @param categoryIndex
     */
    $scope.toggleCategoryItems = function (categoryIndex) {
        $('.category' + categoryIndex).toggleClass('invisibleElement')
    };


    /**
     * Inserts main dish into current selection. Only one main dish can be in current selection.
     * @param category
     * @param dish
     */
    var selectMainDish = function (category, dish) {
        dish["count"] = 1;
        dish["main"] = true;
        $scope.detailItem = dish;
        $scope.selectedDishes = [];
        $scope.selectedDishes.push(dish);
        $scope.selectionPrice += dish.price;
        MenuListService.fetchCombinationsForItem(dish.id).then(function (response) {
            $scope.sideDishes = response.data;
        });
    };

    /**
     * Shows unconfirmed selection dialog to resolve conflict
     */
    var showConfirmSelectionDialog = function(){
        $("#unconfirmedSelectionModal").show();
    };

    /**
     * Hides unconfirmed selection dialog.
     */
    var hideConfirmSelectionDialog = function () {
        $("#unconfirmedSelectionModal").hide();
    }
});