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

    $scope.addToOrder = function (orderItem) {
        if($scope.order.length === 0){
            OrderService.createAndAddItemToOrder($scope.tableId, orderItem).then(function (response) {
                console.log(response.data);
                $scope.billId = response.data;
            })

        } else {
            OrderService.addItemToOrder($scope.tableId, $scope.billId, orderItem).then(function (response) {
                console.log("Item added");
            })
        }
        $scope.order.push(orderItem);
        $("#basketButton").addClass("shake");
        setTimeout(function() {
            $("#basketButton").removeClass("shake");
        }, 800);
    };

    $rootScope.$on('addToOrder', function(event, args) {
        $scope.addToOrder(args.orderItem);
    });
});