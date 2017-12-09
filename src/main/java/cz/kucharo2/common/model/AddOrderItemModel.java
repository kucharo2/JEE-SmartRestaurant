package cz.kucharo2.common.model;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
public class AddOrderItemModel {

    private Integer orderId;
    private Integer tableId;
    private Integer[] itemsToAdd;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public Integer[] getItemsToAdd() {
        return itemsToAdd;
    }

    public void setItemsToAdd(Integer[] itemsToAdd) {
        this.itemsToAdd = itemsToAdd;
    }
}
