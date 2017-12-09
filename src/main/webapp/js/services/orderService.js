/**
 * Angular service for ordering items
 * @type {angular.service}
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 */
app.service('OrderService', function ($http) {

    /**
     * Creates order and adds item to it on server
     * @param tableid id of table
     * @param orderItem item to be added
     * @returns {HttpPromise}
     */
    this.createAndAddItemToOrder = function (tableid, orderItem) {
        var data = {
            "tableId": tableid,
            "itemsToAdd": createItemIdsFromOrderItem(orderItem)
        };
        return $http.post(apiPrefix + "/order/addItems", data)
    };

    /**
     * Adds item into existing order on server
     * @param tableid id of table
     * @param billid id of bill(order)
     * @param orderItem item to be added
     * @returns {HttpPromise}
     */
    this.addItemToOrder = function (tableid, billid, orderItem) {
        var itemsIds = [];
        for (var i = 0; i < orderItem.length; i++) {
            for (var j = 0; j < orderItem[i].count; j++) {
                itemsIds.push(orderItem[i].id);
            }
        }
        var data = {
            "tableId": tableid,
            "orderId": billid,
            "itemsToAdd": createItemIdsFromOrderItem(orderItem)
        };
        console.log(data);
        return $http.post(apiPrefix + "/order/addItems", data)
    };

    /**
     * Gets active order on table from server if there is some
     * @param tableid id of table
     * @returns {HttpPromise}
     */
    this.getActiveOrderOnTable = function (tableid) {
        return $http.get(apiPrefix + /table/+tableid+"/createdOrder");
    };

    /**
     * Removes item from specified order
     * @param billItemId id of item on order
     * @returns {HttpPromise}
     */
    this.removeItemFromOrder = function (billItemId) {
        return $http.delete(apiPrefix + "/order/deleteItem/" + billItemId);
    };

    /**
     * Confirms order
     * @param billId bill (order) id
     * @returns {HttpPromise}
     */
    this.confirmOrder = function (billId) {
        return $http.post(apiPrefix + "/order/" + billId + "/confirm", null);
    };

    /**
     * Cancels order
     * @param billId bill (order) id
     * @returns {HttpPromise}
     */
    this.cancelOrder = function (billId) {
        return $http.post(apiPrefix + "/order/" + billId + "/cancel", null);
    };

    /**
     * Creates array of order-items ids
     * @param orderItem
     * @returns {Array}
     */
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