package dngo.neumont.basket;

public class ItemWithAmount {
    private Item containedItem;
    private int quantity;

    public Item getContainedItem() {
        return containedItem;
    }

    public void setContainedItem(Item containedItem) {
        this.containedItem = containedItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
