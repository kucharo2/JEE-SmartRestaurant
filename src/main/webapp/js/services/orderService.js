app.service('OrderService', function ($http) {

    this.createAndAddItemToOrder = function (tableid, orderItem) {
        var data = {
            "tableId": tableid,
            "itemsToAdd": createItemIdsFromOrderItem(orderItem)
        };
        return $http.post(apiPrefix + "/order/addItems", data)
    };

    this.addItemToOrder = function (tableid, billid, orderItem) {
        var itemsIds = [];
        for (var i = 0; i < orderItem.length; i++) {
            for (var j = 0; j < orderItem[i].count; j++) {
                itemsIds.push(orderItem[i].id);
            }
        }
        var data = {
            "tableId": tableid,
            "billId": billid,
            "itemsToAdd": createItemIdsFromOrderItem(orderItem)
        };
        console.log(data);
        return $http.post(apiPrefix + "/order/addItems", data)
    };

    this.removeItemFromOrder = function (billItemId) {
        return $http.delete(apiPrefix + "/order/deleteItem/" + billItemId);
    };

    this.confirmOrder = function (billId) {
        return $http.post(apiPrefix + "/order/" + billId + "/confirm", null);
    };

    var createItemIdsFromOrderItem = function (orderItem) {
        var itemsIds = [];
        for (var i = 0; i < orderItem.length; i++) {
            for (var j = 0; j < orderItem[i].count; j++) {
                itemsIds.push(orderItem[i].id);
            }
        }
        return itemsIds;
    }

});