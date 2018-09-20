/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.User;

/**
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
public class UserDao {

	private Connection _con = null;

	public User getUser(User user) throws SQLException{
		String sql = "SELECT user_id, password, authority FROM users WHERE (user_id = ?) AND (password = ?)";
		User returnUser = null;

		DBHelper dbHelper = new DBHelper();
        // ヘルパーに接続を依頼
        _con = dbHelper.connectDb();

        String userId = user.getUserId();
        String password = user.getPassword();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = _con.prepareStatement(sql);
            stmt.setString(1, userId);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if (rs.next()) {
            	returnUser = new User(rs.getString(1), rs.getString(2), rs.getInt(3));
            }
        } finally {
            // PreparedStatementのクローズ
            try {
                dbHelper.closeResource(stmt);
            } catch (Exception e) {
                // SQLException以外の例外が発生
                e.printStackTrace();
                // LOGへ記録
            }
            // ResultSetのクローズ
            try {
                dbHelper.closeResource(rs);
            } catch (Exception e) {
                // SQLException以外の例外が発生
                e.printStackTrace();
                // LOGへ記録
            }
            dbHelper.closeDb();
        }

		return returnUser;
	}

	public int getAuthority(String userId) throws SQLException{
		String sql = "SELECT authority FROM users WHERE user_id = ?";

		DBHelper dbHelper = new DBHelper();
        // ヘルパーに接続を依頼
        _con = dbHelper.connectDb();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        int authority=-1;

        try{
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
            }
            // ResultSetのクローズ
            try {
                dbHelper.closeResource(rs);
            } catch (Exception e) {
                // SQLException以外の例外が発生
                e.printStackTrace();
                // LOGへ記録
            }
            dbHelper.closeDb();
        }

        return authority;
	}
}
