/**
 * Angular service for payments
 * @type {angular.service}
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 */
app.service('CashDeskService', function ($http) {

    /**
     * Registers user
     * @returns {HttpPromise}
     */
    this.getUnpaidOrderItems = function (tableId) {
        return $http.get(apiPrefix + "/cashDesk/" + tableId + "/unpaidOrderItems");
    };

    /**
     * Pays order items by ids
     * @param orderItemsIds - array of integers representing order item ids
     * @returns {*}
     */
    this.payOrderItems = function (orderItemsIds) {
        return $http({
            url: apiPrefix + "/cashDesk/pay",
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            data: orderItemsIds
        });
    }

});