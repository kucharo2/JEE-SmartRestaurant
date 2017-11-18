package cz.kucharo2.rest.model;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
public class AddOrderItemModel {

    private Integer billId;
    private Integer tableId;
    private Integer[] itemsToAdd;

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
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
