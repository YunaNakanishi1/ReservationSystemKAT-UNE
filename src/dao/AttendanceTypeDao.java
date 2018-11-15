/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.AttendanceTypeDto;

/**
 * usersテーブルを取り扱うクラス.
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
public class AttendanceTypeDao {

	private static Logger _log = LogManager.getLogger();
	private Connection _con = null;

	public List<AttendanceTypeDto> queryAll() throws SQLException{
		List<AttendanceTypeDto> attendanceTypeDtoList =
				new ArrayList<AttendanceTypeDto>();

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
            String sql = "select * from attendance_types;";

        	stmt = _con.createStatement();
        	rs = stmt.executeQuery(sql);

        	if(rs.next()){
        		int attendanceTypeId = rs.getInt("attendance_type_id");
        		String attendanceTypeName = rs.getString("attendance_type");

        		attendanceTypeDtoList.add(
        				new AttendanceTypeDto(attendanceTypeId, attendanceTypeName));
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

		return attendanceTypeDtoList;

	}
}
