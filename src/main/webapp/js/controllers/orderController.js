/**
 * Angular controller for order
 */
app.controller('OrderController', function MenuListController($scope, $rootScope, OrderService) {
    $scope.order = [];
    $scope.billId = -1;
    $scope.tableId = 1; //TODO dummy

    $scope.showOrderDialog = function () {
        $("#orderModal").show();
    };

    $scope.closeOrderDialog = function () {
        $("#orderModal").hide();
    };

    $scope.decreaseAmount = function (dish) {
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

    $scope.addToOrder = function (orderItem) {
        if($scope.order.length === 0){
            OrderService.createAndAddItemToOrder($scope.tableId, orderItem).then(function (response) {
                processAddResponse(response);
                $scope.order.push(orderItem);
            })

        } else {
            OrderService.addItemToOrder($scope.tableId, $scope.billId, orderItem).then(function (response) {
                processAddResponse(response);
                $scope.order.push(orderItem);
            })
        }

    };

    $rootScope.$on('addToOrder', function(event, args) {
        $scope.addToOrder(args.orderItem);
    });

    var processAddResponse = function(response){
        console.log(response.data);
        $scope.billId = response.data.id;
        $scope.bill = response.data;
        shakeButton();
    }

    var shakeButton = function () {
        $("#basketButton").addClass("shake");
        setTimeout(function() {
            $("#basketButton").removeClass("shake");
        }, 800);
    }
});