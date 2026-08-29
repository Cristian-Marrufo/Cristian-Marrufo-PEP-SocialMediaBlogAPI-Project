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

    //String username = newAccount.getUsername();
    //int passLength = (newAccount.getPassword()).length();

    String username = null;
    int passLength = 5;

    if((username != "") && (dao.getAccountByUsername(username) == null) && (passLength >= 4)) {

      return dao.createAccount(newAccount);
    }

    return null;
  }
  
}
