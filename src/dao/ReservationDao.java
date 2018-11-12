/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.ReservationDto;

/**
  * (6 8 10 11 12 13 17 18).
  * @author リコーITソリューションズ株式会社 KAT-UNE
  */
public class ReservationDao {

	private Connection _con = null;
	private static Logger _log = LogManager.getLogger();




	/**
	  * 引数でもらった予約IDから
	  * @param reserveId 予約ID
	  * @return
	  * @throws SQLException
	  */
	public ReservationDto queryById(int reserveId) throws SQLException {

		DBHelper dbHelper = new DBHelper();
		_con = dbHelper.connectDb(); //dbに接続

		if (_con == null) {
			_log.error("DatabaseConnectError");
			throw new SQLException();	//エラー処理はハンドラーに任せる
		}

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		String sql="select * from reservations where reserve_id = ?";

		preparedStatement = _con.prepareStatement(sql);
		preparedStatement.setInt(1,reserveId);


//		private int _reservationId;				ok
//		private Resource _resource;				リソースDTO
//		private String _usageDate;				利用日
//		private TimeDto _usageStartTime;		利用開始時間
//		private TimeDto _usageEndTime;			利用終了時間
//		private String _reservationName;		ok
//		private User _reservedPerson;			予約者DTO
//		private User _coReservedPerson;			共同予約者DTO
//		private int _numberOfParticipants;		ok 利用人数
//		private AttendanceTypeDto _AttendanceTypeDto;	参加者種別DTO
//		private String _supplement;				ok 補足

		return null;

	}

}
