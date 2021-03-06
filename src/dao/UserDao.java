/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.User;

/**
 * usersテーブルを取り扱うクラス.
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
public class UserDao {
	private static Logger _log = LogManager.getLogger();
	private Connection _con = null;

	/**
	 * ユーザ認証を行うメソッド.
	 * ユーザIDとパスワードを持つデータを引数で受け取り、一致したユーザがあればそのレコードを返却する
	 * 一致しない場合や権限が異常値を持つ場合,nullを返す
	 *
	 * @param user 入力されたユーザ情報
	 * @return returnUser 認証されたユーザ情報. 認証されなかった場合、権限が無い場合null
	 * @throws SQLException
	 */
	public User getUser(User user) throws SQLException {
		String sql = "SELECT user_id, password, authority,family_name,first_name FROM users WHERE (user_id = ?) AND (password = ?) AND ((authority = 1) OR (authority = 0))";
		User returnUser = null;
		if (user == null) {
			return null;
		}

		// ヘルパーに接続を依頼
		DBHelper dbHelper = new DBHelper();
        _con = dbHelper.connectDb();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        //入力されたユーザ情報
        String userId = user.getUserId();
        String password = user.getPassword();

        try {
        	if (_con == null) {
				_log.error("DatabaseConnectError");
				throw new SQLException();
			}
            stmt = _con.prepareStatement(sql);
            stmt.setString(1, userId);
            stmt.setString(2, password);
            rs = stmt.executeQuery();

            //認証出来た場合, authority含むユーザ情報作成
            if (rs.next()) {
            	returnUser = new User(rs.getString(1), rs.getString(2), rs.getInt(3),rs.getString(4),rs.getString(5),null,null);
            }

        } finally {
            // PreparedStatementのクローズ
            try {
                dbHelper.closeResource(stmt);
            } catch (Exception e1) {
                // SQLException以外の例外が発生
                e1.printStackTrace();
                // LOGへ記録
                _log.error("displayAll() Exception e1");
            }
            // ResultSetのクローズ
            try {
                dbHelper.closeResource(rs);
            } catch (Exception e2) {
                // SQLException以外の例外が発生
                e2.printStackTrace();
                // LOGへ記録
                _log.error("displayAll() Exception e2");
            }
            dbHelper.closeDb();
        }

		return returnUser;
	}

	/**
	 * ユーザIDを指定してそのユーザのAuthorityを取得するメソッド
	 * @param userId ユーザID
	 * @return ユーザIDの権限(管理者：0、一般：1　ユーザIDがDBに登録されていない:-1)
	 * @throws SQLException
	 */
	public int getAuthority(String userId) throws SQLException{

		DBHelper dbHelper = new DBHelper();
        // ヘルパーに接続を依頼
        _con = dbHelper.connectDb();

        if(_con == null){//追記
            _log.error("DataBaseAcessError");
            throw new SQLException();
        }
        PreparedStatement stmt = null;
        ResultSet rs = null;

        int authority=-1;

        try{
            String sql = "SELECT authority FROM users WHERE user_id = ?";
        	stmt = _con.prepareStatement(sql);

        	stmt.setString(1, userId);

        	rs = stmt.executeQuery();

        	if(rs.next()){
        		authority = rs.getInt(1);
        	}
        }finally{
        	// PreparedStatementのクローズ
            try {
                dbHelper.closeResource(stmt);
            } catch (Exception e) {
                // SQLException以外の例外が発生
                e.printStackTrace();
                // LOGへ記録
                _log.error("displayAll() Exception on close");
            }
            // ResultSetのクローズ
            try {
                dbHelper.closeResource(rs);
            } catch (Exception e) {
                // SQLException以外の例外が発生
                e.printStackTrace();
                // LOGへ記録
                _log.error("displayAll() Exception on close");
            }
            dbHelper.closeDb();
        }
        return authority;
	}


	/**
	 * @return
	 * @throws SQLException
	 */
	public List<User> queryAll() throws SQLException{
		List<User> userList = new ArrayList<User>();
		DBHelper dbHelper = new DBHelper();

		// ヘルパーに接続を依頼
        _con = dbHelper.connectDb();

        if(_con == null){//追記
            _log.error("DataBaseAcessError");
            throw new SQLException();
        }

        Statement stmt = null;
        ResultSet rs = null;

        try{
            String sql = "select user_id, password, family_name, first_name, "
            		+ "authority, tel, mail_address from users;";

        	stmt = _con.createStatement();
        	rs = stmt.executeQuery(sql);

        	while(rs.next()){
        		String userId = rs.getString("user_id");
        		String password = rs.getString("password");
        		int authority = rs.getInt("authority");
        		String familyName = rs.getString("family_name");
        		String firstName = rs.getString("first_name");
        		String phoneNumber = rs.getString("tel");
        		String mailAddress = rs.getString("mail_address");

        		userList.add(new User(userId, password, authority, familyName,
        				firstName, phoneNumber, mailAddress));
        	}
        }finally{
        	// Statementのクローズ
            try {
                dbHelper.closeResource(stmt);
            } catch (Exception e) {
                // SQLException以外の例外が発生
                e.printStackTrace();
                // LOGへ記録
                _log.error("queryAll() Exception on close");
            }
            // ResultSetのクローズ
            try {
                dbHelper.closeResource(rs);
            } catch (Exception e) {
                // SQLException以外の例外が発生
                e.printStackTrace();
                // LOGへ記録
                _log.error("queryAll() Exception2 on close");
            }
            dbHelper.closeDb();
        }


		return userList;

	}





}
