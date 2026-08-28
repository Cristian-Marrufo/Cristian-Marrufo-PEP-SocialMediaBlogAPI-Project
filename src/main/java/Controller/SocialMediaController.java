package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import Service.SocialMediaService;
import DAO.SocialMediaDAO;
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
        app.get("example-endpoint", this::exampleHandler);

        app.post("/registration", this::registrationHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registrationHandler(Context context) throws JsonProcessingException{
        
        SocialMediaDAO dao = new SocialMediaDAO();
        ObjectMapper mapper = new ObjectMapper();
        
        Account newAccount = mapper.readValue(context.body(), Account.class);
        Account createdAccount = services.getNewAccount(newAccount);

        if(createdAccount != null) {
            context.json(createdAccount);
            context.status(200);
        } else if(dao.getAccountByUsername(newAccount.getUsername()) != null){
            context.status(409);
        } else {
            context.status(400);
        }
    }
}