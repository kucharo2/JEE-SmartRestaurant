/**
 * Angular controller for order
 */
app.controller('OrderController', function MenuListController($scope, $rootScope, OrderService) {
    $scope.order = [];
    $scope.tableId = 1; //TODO dummy

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
            processAddResponse(response);

        });

    };

    $scope.addToOrder = function (orderItem) {
        console.log(orderItem);
        if($scope.bill === undefined){
            OrderService.createAndAddItemToOrder($scope.tableId, orderItem).then(function (response) {
                processAddResponse(response);
            })

        } else {
            OrderService.addItemToOrder($scope.tableId, $scope.bill.id, orderItem).then(function (response) {
                processAddResponse(response);
            })
        }

    };

    $rootScope.$on('addToOrder', function(event, args) {
        $scope.addToOrder(args.orderItem);
    });

    var processAddResponse = function(response){
        console.log(response.data);
        $scope.bill = response.data;
        //sort bill items by id - time when they were added
        $scope.bill.billItems.sort(function(item1, item2){return item1.id-item2.id});

        var itemArr = [];
        var itemGroup = [];
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
        }
        //push last item group
        if(itemGroup.length !== 0){
            itemArr.push(itemGroup);
        }

        $scope.order = itemArr;
        shakeButton();
    };

    var orderContainsItem = function(item, arr){
        for(var i = 0; i < arr.length; i++){
            if(arr[i].id === item.id){
                return i;
            }
        }
        return -1;
    }

    var shakeButton = function () {
        $("#basketButton").addClass("shake");
        setTimeout(function() {
            $("#basketButton").removeClass("shake");
        }, 800);
    }
});