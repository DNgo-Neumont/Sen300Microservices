package dngo.neumont.basket;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basket")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BasketController {

    @Autowired
    ItemJPA newItemJpa;

    Basket basket = new Basket();

    @RequestMapping(
            value = "/addItem",
            method = RequestMethod.OPTIONS
    )
    public ResponseEntity handleAdd() {
        return new ResponseEntity(HttpStatus.OK);
    }
    @RequestMapping(
            value = "/addItem",
            method = RequestMethod.PUT)
    public void addItemToBasket(@RequestBody JsonNode item){
        ItemWithAmount itemToAdd = new ItemWithAmount();
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
            method = RequestMethod.OPTIONS
    )
    public ResponseEntity handleRemove() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(
            value = "/removeItem",
            method = RequestMethod.DELETE
    )
    public void removeItemFromBasket(@RequestBody JsonNode item){
        ItemWithAmount itemToRemove = basket.getItems().stream().filter(items -> items.getContainedItem().getId() == item.get("id").asLong()).findFirst().orElse(null);
        basket.getItems().remove(itemToRemove);
    }
    @RequestMapping(
            value = "/updateItem",
            method = RequestMethod.OPTIONS
    )
    public ResponseEntity handleUpdate() {
        return new ResponseEntity(HttpStatus.OK);
    }
    @RequestMapping(
            value = "/updateItem",
            method = RequestMethod.PATCH
    )
    public void updateItemInBasket(@RequestBody JsonNode item){
        ItemWithAmount itemToUpdate = basket.getItems().stream().filter(items -> items.getContainedItem().getId() ==  item.get("id").asLong()).findFirst().orElse(null);
        int indexOf = basket.getItems().indexOf(itemToUpdate);
        basket.getItems().get(indexOf).setQuantity(item.get("quantity").asInt());
    }

    @RequestMapping(
            value = "/deleteBasket",
            method = RequestMethod.OPTIONS
    )
    public ResponseEntity handleDelete() {
        return new ResponseEntity(HttpStatus.OK);
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
            method = RequestMethod.OPTIONS
    )
    public ResponseEntity handleGet() {
        return new ResponseEntity(HttpStatus.OK);
    }
    @RequestMapping(
            value = "/getBasket",
            method = RequestMethod.GET
    )
    public Basket getBasket(){
        return basket;
    }


}
