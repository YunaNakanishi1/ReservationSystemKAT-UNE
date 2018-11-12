/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

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
	  * 引数でもらった予約IDからReservationDtoを作成しリターンする.
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

		//実行するSQL文
		String selectReservationsSql ="select * from reservations where reserve_id = ?";

		preparedStatement = _con.prepareStatement(selectReservationsSql);
		preparedStatement.setInt(1,reserveId);
		rs = preparedStatement.executeQuery();	//実行



		//reservationsテーブルのカラムをセットするために用意
		String resourceId;	//リソースID
		Timestamp usageStartDate; //利用開始日時
		Timestamp usageEndDate; //利用終了日時
		String reservationName;	//予約名称
		String reservedPersonId; //予約者ID
		String coReservedPersonId;//共同予約者ID
		int numberOfParticipants;	//利用人数
		int attendanceTypeId;	//参加者種別ID
		Timestamp reserveSupplement; //補足


		while (rs.next()) {
			resourceId = rs.getString("resource_id");	//リソースID
			usageStartDate = rs.getTimestamp("usage_start_date"); //利用開始日時
			usageEndDate = rs.getTimestamp("usage_end_date"); //利用終了日時
			reservationName = rs.getString("reservation_name");	//予約名称
			reservedPersonId = rs.getString("reserved_person_id"); //予約者ID
			coReservedPersonId = rs.getString("co_reserved_person_id");//共同予約者ID
			numberOfParticipants = rs.getInt("number_of_participants");	//利用人数
			attendanceTypeId = rs.getInt("attendance_type_id");	//参加者種別ID
			reserveSupplement = rs.getTimestamp("reserve_supplement"); //補足
		}



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

		ReservationDto reservationDto = new ReservationDto(reserveId, _resource,
				_usageDate, _usageStartTime, _usageEndTime, _reservationName,
				_reservedPerson, _coReservedPerson, _numberOfParticipants,
				_AttendanceTypeDto, supplement);

		return null;

	}

}
