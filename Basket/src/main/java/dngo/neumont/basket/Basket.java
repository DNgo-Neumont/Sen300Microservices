package dngo.neumont.basket;

import java.util.ArrayList;
import java.util.List;

public class Basket {
    List<ItemWithAmount> items;
    public Basket() {
        this.items = new ArrayList<>();
    }

    public void addItem(ItemWithAmount item){
        items.add(item);
    }

    //If you want to replace a stored item, do this.
    public void updateItem(int position, ItemWithAmount item){
        items.set(position, item);
    }

    public void removeItem(int itemId){
        items.remove(itemId);
    }

    public List<ItemWithAmount> getItems(){
        return items;
    }

}
