package DAO;

import Util.ConnectionUtil;
import Model.Account;
import Model.Message;
import java.sql.*;
import java.util.*;

public class SocialMediaDAO {

  public Account createAccount(Account newAccount) {

    Connection connection = ConnectionUtil.getConnection();

    try {
      String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
      PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

      preparedStatement.setString(1, newAccount.getUsername());
      preparedStatement.setString(2, newAccount.getPassword());

      preparedStatement.executeUpdate();
      ResultSet generatedKey = preparedStatement.getGeneratedKeys();

      while(generatedKey.next()) {

        int accountId = (int)generatedKey.getLong(1);
        return new Account(accountId, newAccount.getUsername(), newAccount.getPassword());
      }

    } catch(SQLException e) {
      e.printStackTrace();
    }

    return null;
  }

  public Account getAccountByUsername(String username) {

    Connection connection = ConnectionUtil.getConnection();

    try {
      String sql = "SELECT * FROM account WHERE username=?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);

      preparedStatement.setString(1, username);
      
      ResultSet resultSet = preparedStatement.executeQuery();

      while(resultSet.next()) {

        return new Account(resultSet.getInt("account_id"), username, resultSet.getString("password"));
      }

    } catch(SQLException e) {
      e.printStackTrace();
    }

    return null;
  }



  
  
}
