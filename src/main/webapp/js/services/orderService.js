app.service('OrderService', function ($http) {

    this.createAndAddItemToOrder = function (tableid, orderItem) {
        var itemsIds = [];
        for(var i=0; i < orderItem.length; i++){
            itemsIds.push(orderItem[i].id);
        }
        var data = {
            "tableId" : 1,
            "itemsToAdd" : itemsIds
        };
        return $http.post(apiPrefix + "/order/addItems", data)
    };

    this.addItemToOrder = function (tableid, billid, orderItem) {
        var itemsIds = [];
        for(var i=0; i < orderItem.length; i++){
            itemsIds.push(orderItem[i].id);
        }
        var data = {
            "tableId" : 1,
            "billId": 2,
            "itemsToAdd" : itemsIds
        };
        return $http.post(apiPrefix + "/order/addItems", data)
    };

});