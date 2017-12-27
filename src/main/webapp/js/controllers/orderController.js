/**
 * Angular controller for order
 * @type {angular.controller}
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 */
app.controller('OrderController', function MenuListController($scope, $rootScope, $mdToast, $cookies, OrderService, ErrorService) {

    //get table id from cookies
    if (($scope.tableId = $cookies.get("table")) === undefined) {
        $location.path("/tables");
    }

    $scope.preparedOrders = [];
    $scope.orderItems = [];
    $scope.orderPrice = 0;

    var getActiveOrderOnTable = function () {
        $scope.orderItems = [];
        $scope.orderPrice = 0;
        $scope.order = null;
        OrderService.getActiveOrderOnTable($scope.tableId).then(function (response) {
            if (response.data !== "") {
                $("#basketButton").prop('disabled', false);
                processGetBillResponse(response);
            } else {
                $("#basketButton").prop('disabled', true);
            }
        }, ErrorService.serverErrorCallback);
    };

    var getActiveOrderListener = $rootScope.$on("getActiveOrder", function (event) {
        //if there is a active order on the table get it ...
        getActiveOrderOnTable();
    });

    getActiveOrderOnTable();

    var initializeOrderWebsocket = function() {
        var orderSocket = new WebSocket("ws://localhost:8080/SmartRestaurant/orderStatusWebSocket");
        var browserSupport = ("WebSocket" in window) ? true : false;
        if (browserSupport) {
            orderSocket.onopen = function () {
                orderSocket.send("ping");
            };
            orderSocket.onmessage = function (event) {
                var data = event.data;
                var orderId = data.split("-")[0];
                for (var i in $scope.preparedOrders) {
                    if ($scope.preparedOrders[i].id === parseInt(orderId)) {
                        $scope.preparedOrders.splice(i, 1);
                        $mdToast.show(
                            {
                                template:
                                '<md-toast>' +
                                '<div class="md-toast-content offerReady">' +
                                'Objednávka číslo '+ orderId +' je připravena!'+
                                '</div>' +
                                '</md-toast>',
                                controllerAs: 'toast',
                                bindToController: true,
                                position: 'bottom right',
                                hideDelay: 10000
                            }
                        );
                    }
                }
            }
        } else {
            alert("WebSocket is NOT supported by your Browser!");
        }
    };

    initializeOrderWebsocket();

    /**
     * Shows order in dialog
     */
    $scope.showOrderDialog = function () {
        $("#orderModal").show();
    };

    /**
     * Closes order dialog
     */
    $scope.closeOrderDialog = function () {
        $("#orderModal").hide();
    };

    /**
     * Decreases amount of dish in order - if the dish is main side dishes are removed as well
     * @param dish
     */
    $scope.decreaseAmount = function (dish) {
        var idToRemove = dish.ids[0];
        dish.count--;
        dish.ids.splice(0, 1);
        if (dish.count === 0) {
            $scope.orderItems.slice($scope.orderItems.indexOf(dish), 1);
        }
        OrderService.removeItemFromOrder(idToRemove).then(function (response) {
            processRemoveResponse(response);
        }, ErrorService.serverErrorCallback);
    };

    /**
     * Adds dish into order
     * @param orderItem
     */
    $scope.addToOrder = function (orderItem) {
        if ($scope.order === undefined || $scope.order === null) {
            OrderService.createAndAddItemToOrder($scope.tableId, orderItem).then(function (response) {
                processAddResponse(response);
            }, ErrorService.serverErrorCallback)
        } else {
            OrderService.addItemToOrder($scope.tableId, $scope.order.id, orderItem).then(function (response) {
                processAddResponse(response);
            }, ErrorService.serverErrorCallback)
        }

    };

    /**
     * Cancels order
     */
    $scope.cancelOrder = function () {
        OrderService.cancelOrder($scope.order.id).then(function (response) {
            $scope.orderItems = [];
            $scope.order = null;
            $("#basketButton").prop('disabled', true);
            $mdToast.show(
                $mdToast.simple()
                    .textContent('Objednávka byla zrušena!')
                    .position('bottom right')
                    .hideDelay(1500)
            );
        }, ErrorService.serverErrorCallback);
    };

    /**
     * Confirms and sends order
     */
    $scope.confirmOrder = function () {
        OrderService.confirmOrder($scope.order.id).then(function (response) {
            $scope.orderItems = [];
            $scope.preparedOrders.push($scope.order);
            $scope.order = null;
            $scope.closeOrderDialog();
            $("#basketButton").prop('disabled', true);
            $mdToast.show(
                $mdToast.simple()
                    .textContent('Vaše objednávka byla odeslána!')
                    .position('bottom right')
                    .hideDelay(1500)
            );
        }, ErrorService.serverErrorCallback);
    };

    /**
     * Reacts on add to order event from offer controller and adds current selection into order
     */
    var addToOrderListener = $rootScope.$on('addToOrder', function (event, args) {
        $scope.addToOrder(args.orderItem);
    });

    /**
     * Processes active order on table if there is one
     * @param response
     */
    var processGetBillResponse = function (response) {
        $scope.order = response.data;
        refreshOrder();
    };

    /**
     * Processes response from server after adding a new selection into order
     * @param response
     */
    var processAddResponse = function (response) {
        $scope.order = response.data;
        refreshOrder();
        shakeButton();
        $mdToast.show(
            $mdToast.simple()
                .textContent('Výběr přidán do vaší objednávky.')
                .position('bottom right')
                .hideDelay(1500)
        );
    };

    /**
     * Processes response from server after removing an item from order
     * @param response
     */
    var processRemoveResponse = function (response) {
        $scope.order = response.data;
        refreshOrder();
        $mdToast.show(
            $mdToast.simple()
                .textContent('Položka odebrána z objednávky.')
                .position('bottom right')
                .hideDelay(1500)
        );
    };

    /**
     * Refresh order according to data from server response
     */
    var refreshOrder = function () {
        var itemArr = [];
        var drinksArr = [];
        var lonelySideDishesArr = [];
        var itemGroup = [];
        var totalPrice = 0;
        if ($scope.order.orderItems.length === 0) {
            $("#basketButton").prop('disabled', true);
        } else {
            $("#basketButton").prop('disabled', false);
        }
        for (var i = 0; i < $scope.order.orderItems.length; i++) {
            var orderItem = $scope.order.orderItems[i];
            var index;
            if (orderItem.item.category.parentCategory.code === "DRINKS") {
                if ((index = orderContainsItem(orderItem.item, drinksArr)) >= 0) {
                    drinksArr[index]["count"]++;
                    drinksArr[index]["ids"].push(orderItem.id);
                } else {
                    orderItem.item["count"] = 1;
                    orderItem.item["ids"] = [orderItem.id];
                    orderItem.item["drink"] = true;
                    drinksArr.push(orderItem.item);
                }
            } else if(orderItem.item.category.code === "PRILOHA" && orderItem.parentOrderItem === null){
                if ((index = orderContainsItem(orderItem.item, lonelySideDishesArr)) >= 0) {
                    lonelySideDishesArr[index]["count"]++;
                    lonelySideDishesArr[index]["ids"].push(orderItem.id);
                } else {
                    orderItem.item["count"] = 1;
                    orderItem.item["ids"] = [orderItem.id];
                    lonelySideDishesArr.push(orderItem.item);
                }
            } else {
                if (orderItem.parentOrderItem === null) {
                    //push existing and start new item group
                    if (itemGroup.length > 0) {
                        itemArr.push(itemGroup);
                        itemGroup = [];
                    }
                }
                if ((index = orderContainsItem(orderItem.item, itemGroup)) >= 0) {
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
            itemArr.push(itemGroup);
        }
        if (lonelySideDishesArr.length !== 0){
            itemArr.push(lonelySideDishesArr);
        }
        if (drinksArr.length !== 0) {
            itemArr.push(drinksArr);
        }


        $scope.orderItems = itemArr;
        $scope.orderPrice = totalPrice;
    };

    /**
     * Checks if array contains item
     * @param item needle
     * @param arr haystack
     * @returns {number} index of item in array
     */
    var orderContainsItem = function (item, arr) {
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].id === item.id) {
                return i;
            }
        }
        return -1;
    };

    /**
     * Shakes order button
     */
    var shakeButton = function () {
        $("#basketButton").addClass("shake");
        setTimeout(function () {
            $("#basketButton").removeClass("shake");
        }, 800);
    };

    $scope.$on('$destroy', function() {
        getActiveOrderListener();
        addToOrderListener();
    });

});