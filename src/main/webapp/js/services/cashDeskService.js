/**
 * Angular service for payments
 * @type {angular.service}
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 */
app.service('CashDeskService', function ($http) {

    /**
     * Gets unpaid order items for logged user
     * @returns {HttpPromise}
     */
    this.getUnpaidOrderItems = function (tableId) {
        return $http.get(apiPrefix + "/cashDesk/" + tableId + "/unpaidOrderItems");
    };

    /**
     * Gets users which have some unpaid order items on specified table
     * @param tableId
     * @returns {HttpPromise}
     */
    this.getUsersHavingUnpaidFinishedOrdersOnTable = function (tableId) {
        var loggedWaiter = localStorage.getItem("loggedWaiter");
        if(loggedWaiter !== null && loggedWaiter !== ""){
            return $http({
               url: apiPrefix + "/cashDesk/" + tableId + "/unpaidUsers",
               method: "GET",
                headers: {
                   "Authorization" : "Basic "+loggedWaiter
                }
            });
        }
        return null;
    };

    /**
     * Gets unpaid order items on specified table which belong to specified user
     * @param tableId
     * @param userId
     * @returns {HttpPromise}
     */
    this.getUnpaidFinishedOrderItemsOnTableForUser = function (tableId, userId) {
        var loggedWaiter = localStorage.getItem("loggedWaiter");
        if(loggedWaiter !== null && loggedWaiter !== ""){
            return $http({
                url: apiPrefix + "/cashDesk/" + tableId + "/unpaidOrderItems/" + userId,
                method: "GET",
                headers: {
                    "Authorization" : "Basic "+loggedWaiter
                }
            });
        }
        return null;
    };

    /**
     * Pays order items by ids
     * @param orderItemsIds - array of integers representing order item ids
     * @returns {*}
     */
    this.payItems = function (orderItemsIds) {
        var loggedWaiter = localStorage.getItem("loggedWaiter");
        if(loggedWaiter !== null && loggedWaiter !== "") {
            return $http({
                url: apiPrefix + "/cashDesk/pay",
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Basic " + loggedWaiter
                },
                data: orderItemsIds
            });
        }
        return null;
    }

});