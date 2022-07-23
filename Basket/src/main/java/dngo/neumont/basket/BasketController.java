package dngo.neumont.basket;

import com.fasterxml.jackson.databind.JsonNode;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequestMapping("/basket")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BasketController {

    @Autowired
    ItemJPA newItemJpa;
    @Autowired
    EurekaClient eurekaClient;

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

    @RequestMapping(
            value = "/testBasket",
            method = RequestMethod.OPTIONS
    )
    public ResponseEntity handleTest() {
        return new ResponseEntity(HttpStatus.OK);
    }
    @RequestMapping(
            value = "/testBasket",
            method = RequestMethod.GET
    )
    public String testBasket(){
        String response = "Response from item service: ";
        try{
            System.out.println(eurekaClient.getApplication("item-service").getInstances());
            InstanceInfo service = eurekaClient.getApplication("item-service").getInstances().get(0);

            String hostname = service.getHostName();
            int port = service.getPort();

            String urlString = "http://" + hostname + ":" + port + "/itemAPI/test";

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while((inputLine = reader.readLine()) != null){
                content.append(inputLine);
            }
            reader.close();
            response = response + content.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }




}
