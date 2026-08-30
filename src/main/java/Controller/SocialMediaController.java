package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;
import Service.SocialMediaService;
import Model.Account;
import Model.Message;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    SocialMediaService services;

    public SocialMediaController() {
        this.services = new SocialMediaService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {

        Javalin app = Javalin.create();

        app.get("/example-endpoint", this::exampleHandler);
        
        // Endpoint for processing new user registrations
        app.post("/register", this::registrationHandler);
        // Endpoint for processing login
        app.post("/login", this::loginHandler);
        // Endpoint for processing new messages
        app.post("/messages", this::messageCreationHandler);
        // Endpoint for retrieving all messages
        app.get("/messages", this::allMessagesHandler);
        // Endpoint for retrieving a message by using message_id
        app.get("/messages/{message_id}", this::messageByIdHandler);
        // Endpoint for deleting a message by using message_id
        app.delete("/messages/{message_id}", this::messageDeletionByIdHandler);
        // Endpoint for updating a message by using message_id
        app.patch("/messages/{message_id}", this::messageUpdateHandler);
        // Endpoint for retrieving all messages from a user using account_id
        app.get("/accounts/{account_id}/messages", this::;
        
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registrationHandler(Context context) throws JsonProcessingException {
        
        ObjectMapper mapper = new ObjectMapper();
        Account newAccount = mapper.readValue(context.body(), Account.class);
        Account createdAccount = services.getNewAccount(newAccount);

        if(createdAccount != null) {
            context.json(createdAccount);
            context.status(200);
        } else {
            context.status(400);
        }
    }

    private void loginHandler(Context context) throws JsonProcessingException {
        
        ObjectMapper mapper = new ObjectMapper();
        Account userAccount = mapper.readValue(context.body(), Account.class);
        Account retrievedAccount = services.verifyAccountLogin(userAccount);

        if(retrievedAccount != null) {
            context.json(retrievedAccount);
            context.status(200);
        } else {
            context.status(401);
        }
    }

    private void messageCreationHandler(Context context) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        Message newMessage = mapper.readValue(context.body(), Message.class);
        Message createdMessage = services.getNewMessage(newMessage);

        if(createdMessage != null) {
            context.json(createdMessage);
            context.status(200);
        } else {
            context.status(400);
        }
    }

    private void allMessagesHandler(Context context) {

        List<Message> allMessages = services.getAllMsgs();

        context.json(allMessages);
        context.status(200);
    }

    private void messageByIdHandler(Context context) {
        
        int messageId = context.pathParamAsClass("message_id", Integer.class).get(); 
        Message retrievedMessage = services.getMsgById(messageId);

        if(retrievedMessage != null) {
            context.json(retrievedMessage);
        }
        context.status(200);
    }

   private void messageDeletionByIdHandler(Context context) {

        int messageId = context.pathParamAsClass("message_id", Integer.class).get();
        Message retrievedMessage = services.deleteMsgById(messageId);

        if(retrievedMessage != null) {
            context.json(retrievedMessage);
        }
        context.status(200);
   }

   private void messageUpdateHandler(Context context) throws JsonProcessingException {
        
        ObjectMapper mapper = new ObjectMapper();
        Message messageToUpdate = mapper.readValue(context.body(), Message.class);
        String updatedMessage = messageToUpdate.getMessage_text();
        int messageId = context.pathParamAsClass("message_id", Integer.class).get();
        Message retrievedMessage = services.updateMsgById(messageId, updatedMessage);

        if(retrievedMessage != null) {
            context.json(retrievedMessage);
            context.status(200);
        } else {
            context.status(400);
        }
   }

   private void allMessagesByAccountIdHandler(Context context) {

   }
   
    
}