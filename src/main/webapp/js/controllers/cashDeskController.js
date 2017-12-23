/**
 * Angular controller for payments
 * @type {angular.controller}
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 */
app.controller('CashDeskController', function CashDeskController($rootScope, $location, $scope, $cookies, $mdToast, CashDeskService, ErrorService) {
    if (($scope.tableId = $cookies.get("table")) === undefined) {
        $location.path("/tables");
    }

    $scope.selectedDishes = [];
    $scope.selectionPrice = 0;

    /**
     * Adds dish into array of dishes which will be payed
     *
     * Main food and side dishes are added together
     * Lonely side dishes and drinks are added separately
     * @param index
     * @param dish
     */
    $scope.addToPayed = function (index, dish) {
        for (var i = 0; i < $scope.unpaidItems.length; i++) {
            if ($scope.unpaidItems[i].id === index) {
                var itemArr = $scope.unpaidItems[i];
                if (itemArr.items[0].main) {
                    //add whole item arr into selection
                    $scope.selectedDishes.push({
                        index : i,
                        items: $scope.unpaidItems[i].items
                    });
                    for (var k = 0; k < itemArr.items.length; k++) {
                        $scope.selectionPrice += itemArr.items[k].price * itemArr.items[k].count;
                    }
                    $scope.unpaidItems[i].items = [];
                    return;
                } else if (itemArr.items[0].drink || itemArr.items[0].lonelySideDish) {
                    //add only this item into selection
                    var indexOfItemInSelected = -1;
                    var indexOfItemInUnpaid = itemArr.items.indexOf(dish);
                    var itemFromUnpaid = itemArr.items[indexOfItemInUnpaid];
                    if ((indexOfItemInSelected = itemsContainsItem(itemFromUnpaid, $scope.selectedDishes)) === -1) {
                        var item = {
                            name: itemFromUnpaid.name,
                            price: itemFromUnpaid.price,
                            drink: itemFromUnpaid.drink,
                            lonelySideDish: itemFromUnpaid.lonelySideDish,
                            ids: [].concat(itemFromUnpaid.ids[0]),
                            count: 1,
                            id: itemFromUnpaid.id
                        };
                        $scope.selectedDishes.push({
                            index : i,
                            items: [].concat(item)
                        });
                        $scope.selectionPrice += item.price;

                    } else {
                        var ii = parseInt(indexOfItemInSelected.split(":")[0]);
                        var jj = parseInt(indexOfItemInSelected.split(":")[1]);
                        $scope.selectedDishes[ii].items[jj].ids.push(itemFromUnpaid.ids[0]);
                        $scope.selectedDishes[ii].items[jj].count++;
                        $scope.selectionPrice += $scope.selectedDishes[ii].items[jj].price;
                    }
                    $scope.unpaidItems[i].items[indexOfItemInUnpaid].count--; //lower count
                    $scope.unpaidItems[i].items[indexOfItemInUnpaid].ids.splice(0, 1); //remove added id
                    if ($scope.unpaidItems[i].items[indexOfItemInUnpaid].count === 0) {
                        $scope.unpaidItems[i].items.splice(indexOfItemInUnpaid, 1);
                    }
                    return;
                }
            }
        }
    };

    /**
     * Removes dish from array of dishes which will be payed
     * @param selectedDish
     */
    $scope.removeFromPayed = function (selectedDish) {
        console.log($scope.unpaidItems);
        console.log(selectedDish);
        if(selectedDish.items[0].main) {
            $scope.unpaidItems[selectedDish.index].items = $scope.unpaidItems[selectedDish.index].items.concat(selectedDish.items);
            $scope.selectedDishes.splice($scope.selectedDishes.indexOf(selectedDish), 1);
        } else if (selectedDish.items[0].drink || selectedDish.items[0].lonelySideDish){
            var indexOfItemInUnpaid;
            var itemFromSelected = selectedDish.items[0];
            if((indexOfItemInUnpaid = itemsContainsItem(itemFromSelected, $scope.unpaidItems)) === -1){
                //insert that item
                var item = {
                    name: itemFromSelected.name,
                    price: itemFromSelected.price,
                    drink: itemFromSelected.drink,
                    lonelySideDish: itemFromSelected.lonelySideDish,
                    ids: [].concat(itemFromSelected.ids[0]),
                    count: 1,
                    id: itemFromSelected.id
                };
                $scope.unpaidItems[selectedDish.index].items = $scope.unpaidItems[selectedDish.index].items.concat(item);
                $scope.selectionPrice -= item.price;
            } else {
                //only increase count
                var ii = parseInt(indexOfItemInUnpaid.split(":")[0]);
                var jj = parseInt(indexOfItemInUnpaid.split(":")[1]);
                $scope.unpaidItems[ii].items[jj].ids.push(selectedDish.items[0].ids[0])
                $scope.unpaidItems[ii].items[jj].count++;
                $scope.selectionPrice -= $scope.unpaidItems[ii].items[jj].price;
            }
            var indexOfItemInSelected = $scope.selectedDishes.indexOf(selectedDish);
            $scope.selectedDishes[indexOfItemInSelected].items[0].count--;
            $scope.selectedDishes[indexOfItemInSelected].items[0].ids.splice(0, 1);
            if( $scope.selectedDishes[indexOfItemInSelected].items[0].count === 0){
                $scope.selectedDishes.splice(indexOfItemInSelected, 1);
            }
        }
    };

    /**
     * Pays selected items and get updated unpaid items
     */
    $scope.pay = function () {
        var toBePayedIds = [];
        for(var i=0; i < $scope.selectedDishes.length; i++){
            for(var j=0; j < $scope.selectedDishes[i].items.length; j++){
                toBePayedIds = toBePayedIds.concat($scope.selectedDishes[i].items[j].ids);
            }
        }
        CashDeskService.payItems(toBePayedIds).then(function () {
            //update
            getUnpaidOrderItems();
            $mdToast.show(
                $mdToast.simple()
                    .textContent("Platba položek byla úspěšně přijata")
                    .position('bottom right')
                    .hideDelay(1500)
            );
            $scope.selectedDishes = [];
            $scope.selectionPrice = 0;

        }, ErrorService.serverErrorCallback);
    };

    /**
     * Checks if array contains item
     * @param item
     * @param arr
     * @returns {number} index of item in array
     */
    var arrayContainsItem = function (item, arr) {
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].id === item.id) {
                return i;
            }
        }
        return -1;
    };

    /**
     * Checks if array items which is in array of objects contains specified item
     * @param item
     * @param arr
     * @returns {*} index in array of objects and index in items array separated with :, or -1 if the item was not found
     */
    var itemsContainsItem = function (item, arr) {
        for (var i = 0; i < arr.length; i++) {
            for(var j = 0; j < arr[i].items.length; j++){
                if (arr[i].items[j].id === item.id) {
                    return i+":"+j;
                }
            }
        }
        return -1;
    };

    /**
     * Groups unpaid items
     */
    var groupUnpaidItems = function () {
        var itemArr = [];
        var drinksArr = [];
        var lonelySideDishesArr = [];
        var itemGroup = [];
        var totalPrice = 0;
        var groupId = 0;
        for (var i = 0; i < $scope.unpaidItems.length; i++) {
            var orderItem = $scope.unpaidItems[i];
            var index;
            if (orderItem.item.category.parentCategory.code === "DRINKS") {
                if ((index = arrayContainsItem(orderItem.item, drinksArr)) >= 0) {
                    drinksArr[index]["count"]++;
                    drinksArr[index]["ids"].push(orderItem.id);
                } else {
                    orderItem.item["count"] = 1;
                    orderItem.item["ids"] = [orderItem.id];
                    orderItem.item["drink"] = true;
                    drinksArr.push(orderItem.item);
                }
            } else if (orderItem.item.category.code === "PRILOHA" && orderItem.parentOrderItem === null) {
                if ((index = arrayContainsItem(orderItem.item, lonelySideDishesArr)) >= 0) {
                    lonelySideDishesArr[index]["count"]++;
                    lonelySideDishesArr[index]["ids"].push(orderItem.id);
                } else {
                    orderItem.item["count"] = 1;
                    orderItem.item["ids"] = [orderItem.id];
                    orderItem.item["lonelySideDish"] = true;
                    lonelySideDishesArr.push(orderItem.item);
                }
            } else {
                if (orderItem.parentOrderItem === null) {
                    //push existing and start new item group
                    if (itemGroup.length > 0) {
                        itemArr.push({
                            id: groupId,
                            items: itemGroup
                        });
                        itemGroup = [];
                        groupId++;
                    }
                }
                if ((index = arrayContainsItem(orderItem.item, itemGroup)) >= 0) {
                    itemGroup[index]["count"]++;
                    itemGroup[index]["ids"].push(orderItem.id);
                } else {
                    orderItem.item["count"] = 1;
                    orderItem.item["ids"] = [orderItem.id];
                    orderItem.item["main"] = orderItem.parentOrderItem === null;
                    itemGroup.push(orderItem.item);
                }
            }
            totalPrice += orderItem.item.price;
        }
        //push last item group
        if (itemGroup.length !== 0) {
            itemArr.push({
                id: groupId,
                items: itemGroup
            });
            groupId++;
        }
        if (lonelySideDishesArr.length !== 0) {
            itemArr.push({
                id: groupId,
                items: lonelySideDishesArr
            });
            groupId++;
        }
        if (drinksArr.length !== 0) {
            itemArr.push({
                id: groupId,
                items: drinksArr
            });
        }

        $scope.unpaidItems = itemArr;
    };

    /**
     * Gets unpaid order items from server
     */
    var getUnpaidOrderItems = function () {
        CashDeskService.getUnpaidOrderItems($scope.tableId).then(function (response) {
            $scope.unpaidItems = response.data;
            groupUnpaidItems();
        }, ErrorService.serverErrorCallback);
    };

    getUnpaidOrderItems();
});