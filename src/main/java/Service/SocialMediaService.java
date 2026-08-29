package Service;

import java.util.*;
import DAO.SocialMediaDAO;
import Model.Account;
import Model.Message;

public class SocialMediaService {

  SocialMediaDAO dao;

  public SocialMediaService() {
    this.dao = new SocialMediaDAO();
  }

  public Account getNewAccount(Account newAccount) {

    String username = newAccount.getUsername();
    int passLength = (newAccount.getPassword()).length();
    Account retrievedAccount = dao.getAccountByUsername(username);

    if((retrievedAccount == null) && (username != "") && (passLength >= 4)) {

      return dao.createAccount(newAccount);
    }

    return null;
  }

  public Account verifyAccountLogin(Account userAccount) {

    String username = userAccount.getUsername();
    String password = userAccount.getPassword();
    Account retrievedAccount = dao.getAccountByUsername(username);

    if(retrievedAccount != null) {

      String retrievedPass = retrievedAccount.getPassword();

      if(retrievedPass.equals(password)) {
        return retrievedAccount;
      }
      
    }

    return null;
  }

  public Message getNewMessage(Message newMessage) {

    int accountId = newMessage.getPosted_by();
    String messageText = newMessage.getMessage_text();
    int messageLength = messageText.length();
    Account userAccount = dao.getAccountById(accountId);

    if((userAccount != null) && (messageText != "") && (messageLength <= 255)) {

      return dao.createMessage(newMessage);
    }

    return null;
  }

  public List<Message> getAllMsgs() {

    return dao.getMessages();
  }

  public Message getMsgById(int messageId) {

    return dao.getMessageById(messageId);
  }

  
}
