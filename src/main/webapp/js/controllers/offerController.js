/**
 * Angular controller for dishes list page
 * @type {angular.controller}
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 */
app.controller('MenuListController', function MenuListController($rootScope, $scope, $location, $mdToast, $cookies, OfferService, ErrorService) {
    if(($scope.tableId = $cookies.get("table")) === undefined) {
        $location.path("/tables");
    }
    $scope.selectedDishes = [];
    $scope.orderItems = [];
    $scope.selectionPrice = 0;

    OfferService.getAllDishesGroupedByCategories().then(function (response) {
        $scope.food = response.data;
    }, ErrorService.serverErrorCallback);

    OfferService.getDrinksGroupedByCategories().then(function (response) {
        $scope.drinks = response.data;
    }, ErrorService.serverErrorCallback);
    initOffer();

    function initOffer() {
        $("#foodOffer").click(function () {
            displayOffer("FOOD");
        });

        $("#drinksOffer").click(function () {
            displayOffer("DRINKS");
        });
        displayOffer("FOOD");
    }

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
        $scope.selectedDishes = [];
        $scope.detailItem = null;
        $scope.selectionPrice = 0;
        $rootScope.$emit("addToOrder", {orderItem : orderItem});
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
                $scope.selectionPrice -= dish.price;
            }
        }
    };

    /**
     * Toggles food items in category
     * @param categoryIndex
     */
    $scope.toggleFoodCategoryItems = function (categoryIndex) {
        $('.food-category' + categoryIndex).toggleClass('invisibleElement')
    };

    /**
     * Toggles drink items in category
     * @param categoryIndex
     */
    $scope.toggleDrinkCategoryItems = function (categoryIndex) {
        $('.drink-category' + categoryIndex).toggleClass('invisibleElement')
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
        OfferService.fetchCombinationsForItem(dish.id).then(function (response) {
            $scope.sideDishes = response.data;
        }, ErrorService.serverErrorCallback);
    };

    $scope.addDrink = function(category, drink) {
        var index;
        if((index = $scope.selectedDishes.indexOf(drink)) >= 0){
            $scope.selectedDishes[index].count++;
        }else {
            drink["count"] = 1;
            drink["main"] = false;
            $scope.selectedDishes.push(drink);
        }
        $scope.selectionPrice += drink.price;
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
    };

    function displayOffer (type) {
        var foodTab = $("#foodOffer");
        var drinkTab = $("#drinksOffer");
        var drinksTable = $("#drinksTable");
        var foodTable = $("#dishesTable");
        if(type === "FOOD"){
            foodTab.addClass("active");
            drinkTab.removeClass("active");
            drinksTable.addClass("invisibleElement");
            foodTable.removeClass("invisibleElement");
        } else if(type === "DRINKS"){
            foodTab.removeClass("active");
            drinkTab.addClass("active");
            drinksTable.removeClass("invisibleElement");
            foodTable.addClass("invisibleElement");
        } else {
            console.error("Unknown type of offer");
        }
    }
});