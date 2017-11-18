app.service('OrderService', function ($http) {

    this.createAndAddItemToOrder = function (tableid, orderItem) {
        var data = {
            "tableId" : tableid,
            "itemsToAdd" : createItemIdsFromOrderItem(orderItem)
        };
        return $http.post(apiPrefix + "/order/addItems", data)
    };

    this.addItemToOrder = function (tableid, billid, orderItem) {
        var itemsIds = [];
        for(var i=0; i < orderItem.length; i++){
            for(var j=0; j < orderItem[i].count; j++){
                itemsIds.push(orderItem[i].id);
            }
        }
        console.log(itemsIds);
        var data = {
            "tableId" : tableid,
            "billId": billid,
            "itemsToAdd" :  createItemIdsFromOrderItem(orderItem)
        };
        return $http.post(apiPrefix + "/order/addItems", data)
    };

    this.removeItemFromOrder = function (billItemId) {
        return $http.delete(apiPrefix + "/order/deleteItem/"+billItemId);
    };

    var createItemIdsFromOrderItem = function (orderItem) {
        var itemsIds = [];
        for(var i=0; i < orderItem.length; i++){
            for(var j=0; j < orderItem[i].count; j++) {
                itemsIds.push(orderItem[i].id);
            }
        }
        return itemsIds;
    }

});