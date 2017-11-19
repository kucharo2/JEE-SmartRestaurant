/**
 * Angular controller for order
 */
app.controller('OrderController', function MenuListController($scope, $rootScope, $mdToast, $cookies, OrderService) {
    if(($scope.tableId = $cookies.get("table")) === undefined) {
        $location.path("/tables");
    }
    OrderService.getActiveOrderOnTable($scope.tableId).then(function (response) {
        if(response.data !== ""){
            processGetBillResponse(response);
        }
    });

    $scope.order = [];
    $scope.orderPrice = 0;

    $scope.showOrderDialog = function () {
        $("#orderModal").show();
    };

    $scope.closeOrderDialog = function () {
        $("#orderModal").hide();
    };

    $scope.decreaseAmount = function (dish) {
        var idToRemove = dish.ids[0];
        dish.count --;
        dish.ids.splice(0,1);
        if(dish.count === 0){
            $scope.order.slice($scope.order.indexOf(dish), 1);
        }
        OrderService.removeItemFromOrder(idToRemove).then(function (response) {
            processRemoveResponse(response);
        });
    };

    $scope.addToOrder = function (orderItem) {
        console.log(orderItem);
        if($scope.bill === undefined || $scope.bill === null){
            OrderService.createAndAddItemToOrder($scope.tableId, orderItem).then(function (response) {
                processAddResponse(response);
            })

        } else {
            OrderService.addItemToOrder($scope.tableId, $scope.bill.id, orderItem).then(function (response) {
                processAddResponse(response);
            })
        }

    };

    $scope.cancelOrder = function () {
        OrderService.cancelOrder($scope.bill.id).then(function (response) {
            $scope.order = [];
            $scope.bill = null;
            $mdToast.show(
                $mdToast.simple()
                    .textContent('Objednávka byla zrušena!')
                    .position('bottom right')
                    .hideDelay(1500)
            );
            console.log(response);
        });
    };

    $scope.confirmOrder = function () {
        OrderService.confirmOrder($scope.bill.id).then(function (response) {
            $scope.order = [];
            $scope.bill = null;
            $scope.closeOrderDialog();
            $mdToast.show(
                $mdToast.simple()
                    .textContent('Vaše objednávka byla odeslána!')
                    .position('bottom right')
                    .hideDelay(1500)
            );
            console.log(response);
        });
    };

    $rootScope.$on('addToOrder', function(event, args) {
        $scope.addToOrder(args.orderItem);
    });

    var processGetBillResponse = function (response) {
        $scope.bill = response.data;
        refreshOrder();
    };

    var processAddResponse = function(response){
        $scope.bill = response.data;
        refreshOrder();
        shakeButton();
        $mdToast.show(
            $mdToast.simple()
                .textContent('Výběr přidán do vaší objednávky.')
                .position('bottom right')
                .hideDelay(1500)
        );
    };

    var processRemoveResponse = function (response) {
        $scope.bill = response.data;
        refreshOrder();
        $mdToast.show(
            $mdToast.simple()
                .textContent('Položka odebrána z objednávky.')
                .position('bottom right')
                .hideDelay(1500)
        );
    };

    var refreshOrder = function () {
        var itemArr = [];
        var itemGroup = [];
        var totalPrice = 0;
        for(var i = 0; i < $scope.bill.billItems.length; i++){
            var billItem = $scope.bill.billItems[i];
            if(billItem.parentBillItem === null){
                //push existing and start new item group
                if(itemGroup.length > 0){
                    itemArr.push(itemGroup);
                    itemGroup = [];
                }
            }
            var index;
            if((index = orderContainsItem(billItem.item, itemGroup)) >= 0){
                itemGroup[index]["count"]++;
                itemGroup[index]["ids"].push(billItem.id);
            }else{
                billItem.item["count"] = 1;
                billItem.item["ids"] = [billItem.id];
                billItem.item["main"] = billItem.parentBillItem === null;
                itemGroup.push(billItem.item);
            }
            totalPrice += billItem.item.price;
        }
        //push last item group
        if(itemGroup.length !== 0){
            itemArr.push(itemGroup);
        }

        $scope.order = itemArr;
        $scope.orderPrice = totalPrice;
    };

    var orderContainsItem = function(item, arr){
        for(var i = 0; i < arr.length; i++){
            if(arr[i].id === item.id){
                return i;
            }
        }
        return -1;
    };

    var shakeButton = function () {
        $("#basketButton").addClass("shake");
        setTimeout(function() {
            $("#basketButton").removeClass("shake");
        }, 800);
    };

});