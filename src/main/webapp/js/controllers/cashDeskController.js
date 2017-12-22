/**
 * Angular controller for payments
 * @type {angular.controller}
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 */
app.controller('CashDeskController', function CashDeskController($rootScope, $location, $scope, $cookies, CashDeskService, ErrorService) {
    if (($scope.tableId = $cookies.get("table")) === undefined) {
        $location.path("/tables");
    }

    $scope.selectedDishes = [];
    $scope.selectionPrice = 0;
    $scope.removed = [];

    CashDeskService.getUnpaidOrderItems($scope.tableId).then(function (response) {
        $scope.unpaidItems = response.data;
        groupUnpaidItems();
        console.log($scope.unpaidItems);
    });

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

    var selectedDishesContainsItem = function (item, arr) {
        for (var i = 0; i < arr.length; i++) {
            for(var j = 0; j < arr[i].items.length; j++){
                if (arr[i].items[j].id === item.id) {
                    return i+":"+j;
                }
            }
        }
        return -1;
    };

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
                    $scope.unpaidItems[i].items = [];

                    for (var k = 0; k < itemArr.items.length; k++) {
                        $scope.selectionPrice += itemArr.items[k].price * itemArr.items[k].count;
                    }
                    return;
                } else if (itemArr.items[0].drink || itemArr.items[0].lonelySideDish) {
                    //add only this item into selection
                    var indexOfItem = -1;
                    var itemFromArray = itemArr.items[itemArr.items.indexOf(dish)];
                    if ((indexOfItem = selectedDishesContainsItem(itemFromArray, $scope.selectedDishes)) === -1) {
                        var item = {
                            name: itemFromArray.name,
                            price: itemFromArray.price,
                            drink: itemFromArray.drink,
                            lonelySideDish: itemFromArray.lonelySideDish,
                            ids: [].concat(itemFromArray.ids[0]),
                            count: 1,
                            id: itemFromArray.id
                        };
                        $scope.selectedDishes.push({
                            index : i,
                            items: [].concat(item)
                        });
                        $scope.selectionPrice += item.price;

                    } else {
                        var ii = parseInt(indexOfItem.split(":")[0]);
                        var jj = parseInt(indexOfItem.split(":")[1]);
                        $scope.selectedDishes[ii].items[jj].ids.push(itemFromArray.ids[0])
                        $scope.selectedDishes[ii].items[jj].count++;
                        $scope.selectionPrice += $scope.selectedDishes[ii].items[jj].price;
                    }
                    $scope.unpaidItems[i].items[itemArr.items.indexOf(dish)].count--;
                    $scope.unpaidItems[i].items[itemArr.items.indexOf(dish)].ids.splice(0, 1);
                    if ($scope.unpaidItems[i].items[itemArr.items.indexOf(dish)].count === 0) {
                        $scope.unpaidItems[i].items.splice(itemArr.items.indexOf(dish), 1);
                    }
                    return;
                }
            }
        }
    };

    $scope.removeFromPayed = function (selectedDish) {
        console.log($scope.unpaidItems);
        console.log(selectedDish);
        if(selectedDish.items[0].main) {
            $scope.unpaidItems[selectedDish.index].items = $scope.unpaidItems[selectedDish.index].items.concat(selectedDish.items);
            $scope.selectedDishes.splice($scope.selectedDishes.indexOf(selectedDish), 1);
        } else if (selectedDish.items[0].drink || selectedDish.items[0].lonelySideDish){
            var indexOfItem = -1;
            if((indexOfItem = selectedDishesContainsItem(selectedDish.items[0], $scope.unpaidItems)) === -1){
                //insert that item
                var item = {
                    name: selectedDish.items[0].name,
                    price: selectedDish.items[0].price,
                    drink: selectedDish.items[0].drink,
                    lonelySideDish: selectedDish.items[0].lonelySideDish,
                    ids: [].concat(selectedDish.items[0].ids[0]),
                    count: 1,
                    id: selectedDish.items[0].id
                };
                $scope.unpaidItems[selectedDish.index].items = $scope.unpaidItems[selectedDish.index].items.concat(item);
                $scope.selectionPrice -= item.price;
            } else {
                //only increase count
                var ii = parseInt(indexOfItem.split(":")[0]);
                var jj = parseInt(indexOfItem.split(":")[1]);
                $scope.unpaidItems[ii].items[jj].ids.push(selectedDish.items[0].ids[0])
                $scope.unpaidItems[ii].items[jj].count++;
                $scope.selectionPrice -= $scope.unpaidItems[ii].items[jj].price;
            }
            $scope.selectedDishes[$scope.selectedDishes.indexOf(selectedDish)].items[0].count--;
            $scope.selectedDishes[$scope.selectedDishes.indexOf(selectedDish)].items[0].ids.splice(0, 1);
            if( $scope.selectedDishes[$scope.selectedDishes.indexOf(selectedDish)].items[0].count === 0){
                $scope.selectedDishes.splice($scope.selectedDishes.indexOf(selectedDish), 1);
            }
        }
    }
});