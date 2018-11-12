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
import java.util.List;

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
		String selectReservationsSql
		="select * from reservations, resources,attendance_types "
	   + "where resources.resource_id = reservations.resource_id "
	   + "and attendance_types.attendance_type_id = reservations.attendance_type_id"
	   + "and reserve_id = ?;";

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
		int deleted; //削除済み


		//resourcesテーブルのカラムをセットするために用意
		String _resourceName;	//リソース名
		String _officeName;	//オフィス名
		String _category;	//カテゴリ
		int _capacity;	//定員
		String _supplement;	//補足
		List<String> _facility;	//設備
		Timestamp _usageStopStartDate;	//利用停止開始日時
		Timestamp _usageStopEndDate;	//利用停止終了日時


		while (rs.next()) {
			resourceId = rs.getString("resource_id");
			usageStartDate = rs.getTimestamp("usage_start_date");
			usageEndDate = rs.getTimestamp("usage_end_date");
			reservationName = rs.getString("reservation_name");
			reservedPersonId = rs.getString("reserved_person_id");
			coReservedPersonId = rs.getString("co_reserved_person_id");
			numberOfParticipants = rs.getInt("number_of_participants");
			attendanceTypeId = rs.getInt("attendance_type_id");
			reserveSupplement = rs.getTimestamp("reserve_supplement");
			deleted = rs.getInt("deleted");
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
