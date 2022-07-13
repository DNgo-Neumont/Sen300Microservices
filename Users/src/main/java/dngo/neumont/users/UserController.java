package dngo.neumont.users;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    UserJPA userJPA;

    //Messing around with matching user details to make sure we only have one user account per person
    @RequestMapping(method = RequestMethod.POST,
    path = "/addUser")
    public void addUserToDatabase(@RequestBody User user){
        ExampleMatcher matchOnlyFields = ExampleMatcher.matching()
                .withIgnorePaths("id").withIncludeNullValues();
        Example<User> userExample = Example.of(user, matchOnlyFields);

        if(userJPA.findOne(userExample).isEmpty() && !user.getEmail().isEmpty() && !user.getStreetaddress().isEmpty()) {
            userJPA.save(user);
            userJPA.flush();
        }
    }

    @RequestMapping(method = RequestMethod.PATCH,
    path = "/updateUser")
    public void updateUser(@RequestBody JsonNode userDetails){
        if(userJPA.findById(userDetails.get("id").asLong()).isPresent()){
            User userToUpdate = userJPA.findById(userDetails.get("id").asLong()).get();
            Iterator<String> keySet =  userDetails.fieldNames();
            while (keySet.hasNext()) {
                String key = keySet.next();
                switch(key){
                    case "fname":
                        userToUpdate.setFname(userDetails.get("fname").asText());
                        break;
                    case "lname":
                        userToUpdate.setLname(userDetails.get("lname").asText());
                        break;
                    case "email":
                        userToUpdate.setEmail(userDetails.get("email").asText());
                        break;
                    case "streetaddress":
                        userToUpdate.setStreetaddress(userDetails.get("streetaddress").asText());
                        break;
                    default:
                        break;
                }
            }
            userJPA.saveAndFlush(userToUpdate);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE,
    path = "/deleteUser")
    public void deleteUserFromDatabase(@RequestBody JsonNode userId){
        userJPA.deleteById(userId.get("id").asLong());
    }

    @RequestMapping(method = RequestMethod.GET,
    path = "/getUser/{userId}")
    public User getUserFromDatabase(@PathVariable Long userId){
        return userJPA.findById(userId).orElse(null);
    }

    @RequestMapping(method = RequestMethod.GET,
    path = "/testUsers")
    public List<User> getAllUsers(){
        return userJPA.findAll();
    }

}
