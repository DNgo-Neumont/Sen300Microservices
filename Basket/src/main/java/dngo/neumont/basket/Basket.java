package dngo.neumont.basket;

import java.util.HashMap;
import java.util.Map;

public class Basket {
    Map<Integer, Integer> items;

    public Basket() {
        this.items = new HashMap<Integer, Integer>();
    }

    public void addItem(int itemId, int itemQuantity){
        items.put(itemId, itemQuantity);
    }

    public void updateItem(int itemId, int itemQuantity){
        items.put(itemId, itemQuantity);
    }

    public void removeItem(int itemId){
        items.remove(itemId);
    }

    public Map<Integer, Integer> getItems(){
        return items;
    }

}
