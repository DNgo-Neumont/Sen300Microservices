package dngo.neumont.email;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Iterator;
import java.util.List;

//email password: ufltjnpkzwqtpdln
//email account: ngo2springapitest@gmail.com


@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private UserJPA userJPA;


    @RequestMapping(path = "/sendEmail/{id}",method = RequestMethod.POST)
    public void sendEmailOfOrder(@RequestBody JsonNode incomingData, @PathVariable Long id) throws Exception{
        if(id > 0){
            User userToSendTo = userJPA.findById(id).orElseThrow();
            String email = userToSendTo.getEmail();

            MimeMessage message = emailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom("ngo2springapitest@gmail.com");
            helper.setTo(email);
            helper.setSubject("Order From E-Commerce Application");
            String body = formatEmail(incomingData);

            String bodyWithDetails = body + "\nThe address on file for you is " + userToSendTo.getStreetaddress() + "\n" +
                    "The name on file for this order is " + userToSendTo.getFname() + " " + userToSendTo.getLname();

            helper.setText(bodyWithDetails);
            emailSender.send(message);

        }else{

            JsonNode userToSendTo = incomingData.get("user");
            String email = userToSendTo.get("email").asText();

            MimeMessage message = emailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom("ngo2springapitest@gmail.com");
            helper.setTo(email);
            helper.setSubject("Order from E-Commerce Application");
            String body = formatEmail(incomingData);

            String bodyWithDetails = body + "\nThe address given is " + userToSendTo.get("streetaddress").asText() + "\n" +
                    "The name given for this order is " + userToSendTo.get("fname").asText() + " " + userToSendTo.get("lname").asText();
            helper.setText(bodyWithDetails);
            emailSender.send(message);
        }
    }

    private String formatEmail(JsonNode node) throws JsonProcessingException {
        StringBuilder body = new StringBuilder();

        body.append("Your order has been processed and is on the way. \n").append("Details are as follows.\n").append("ITEM MANIFEST: \n");

        JsonNode basket = node.get("basket");

        JsonNode itemList = basket.get("items");

//        System.out.println(itemList);
//        System.out.println(itemList.toString());
//        System.out.println(itemList.toPrettyString());

        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        List<JsonNode> items = objectMapper.readValue(itemList.toString(), typeFactory.constructCollectionType(List.class, JsonNode.class));
        for (JsonNode item: items) {
            JsonNode containedItem = item.get("containedItem");
            body.append("Item name: ").append(containedItem.get("title").toString().replaceAll("(\")", ""))
                    .append("\n").append("Item Description: ").append(containedItem.get("description").toString().replaceAll("(\")","")).append("\n");
            body.append("Item quantity: ").append(item.get("quantity")).append("\n");
        }

        body.append("If you did not place this order, please let us know as soon as possible.");

        return body.toString();

    }


}
