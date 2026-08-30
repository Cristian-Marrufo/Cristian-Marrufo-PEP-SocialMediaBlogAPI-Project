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

  public Message deleteMsgById(int messageId) {

    Message retrievedMessage = dao.getMessageById(messageId);

    if(retrievedMessage != null) {

      dao.deleteMessagebyId(messageId);
      return retrievedMessage;
    }

    return null;
  }

  public Message updateMsgById(int messageId, String updatedText) {

    int messageLength = updatedText.length();
    Message retrievedMessage = dao.getMessageById(messageId);

    if((retrievedMessage != null) && (updatedText != "") && (messageLength <= 255)) {

      dao.updateMessageById(messageId, updatedText);
      retrievedMessage.setMessage_text(updatedText);
      return retrievedMessage;
    }

    return null;
  }
  
  public List<Message> getMsgsByAccountId(int accountId) {

    return dao.getMessagesByAccountId(accountId);
  }

  
}
