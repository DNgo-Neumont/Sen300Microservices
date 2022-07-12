package dngo.neumont.basket;

import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basket")
public class BasketController {

    @Autowired
    ItemJPA newItemJpa;

    Basket basket = new Basket();

    @RequestMapping(
            value = "/addItem",
            method = RequestMethod.PUT)
    public void addItemToBasket(@RequestBody JsonNode item){
        ItemWithAmount itemToAdd = new ItemWithAmount();
        System.out.println(item);
        itemToAdd.setContainedItem(newItemJpa.findItemById(item.get("id").asLong()));
        itemToAdd.setQuantity((item.get("quantity").asInt()));
        if(itemToAdd.getContainedItem() != null){
            basket.addItem(itemToAdd);
        }else{
            throw new NullPointerException("Provide an actual item! Do not just send a quantity!");
        }
    }

    @RequestMapping(
            value = "/removeItem",
            method = RequestMethod.DELETE
    )
    public void removeItemFromBasket(@RequestBody JSONObject item){
        ItemWithAmount itemToRemove = basket.getItems().stream().filter(items -> items.getContainedItem().getId() == (Integer) item.get("id")).findFirst().orElse(null);
        basket.getItems().remove(itemToRemove);
    }
    @RequestMapping(
            value = "/updateItem",
            method = RequestMethod.PATCH
    )
    public void updateItemInBasket(@RequestBody JSONObject item){
        ItemWithAmount itemToUpdate = basket.getItems().stream().filter(items -> items.getContainedItem().getId() == (Integer) item.get("id")).findFirst().orElse(null);
        int indexOf = basket.getItems().indexOf(itemToUpdate);
        basket.getItems().get(indexOf).setQuantity((Integer) item.get("quantity"));
    }

    @RequestMapping(
            value = "/deleteBasket",
            method = RequestMethod.DELETE
    )
    public void resetBasket(){
        basket = new Basket();
    }

    @RequestMapping(
            value = "/getBasket",
            method = RequestMethod.GET
    )
    public Basket getBasket(){
        return basket;
    }


}
