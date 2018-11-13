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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.ReservationDto;
import dto.Resource;

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
		PreparedStatement preparedStatementForFacility = null;
		ResultSet rsForFacility = null;

		try {
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
			String resourceName;	//リソース名
			String officeName;	//オフィス名
			String category;	//カテゴリ
			int capacity;	//定員
			String supplement; //補足
			List<String> facility;
			Timestamp usageStopStartDate;	//利用停止開始日時
			Timestamp usageStopEndDate;	//利用停止終了日時

			//attendance_typesテーブルのカラムをセットするために用意
			String attendance_type; //参加者種別


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

				resourceName = rs.getString("resource_name");
				officeName = rs.getString("office_name");
				category = rs.getString("category");
				capacity = rs.getInt("capacity");
				supplement = rs.getString("supplement");
				usageStopStartDate = rs.getTimestamp("usage_stop_start_date");
				usageStopEndDate = rs.getTimestamp("usage_stop_end_date");

				attendance_type = rs.getString("resource_id");
			}


			//List<String> facility を作るために、実行するSQL文
			String selectResourceCharacteristicNameSql
			="select resource_characteristics.resource_characteristic_name "
			+ "from resource_features,resources,resource_characteristics "
			+ "where resource_features.resource_characteristic_id = resource_characteristics.resource_characteristic_id  "
			+ "and resources.resource_id = resource_features.resource_id "
			+ "and resources.resource_id = ? ;";

			preparedStatementForFacility = _con.prepareStatement(selectResourceCharacteristicNameSql);
			preparedStatementForFacility.setString(1,resourceId);
			rsForFacility = preparedStatementForFacility.executeQuery();	//実行

			facility = new ArrayList<String>();

			while (rs.next()) {
				facility.add(rsForFacility.getString("resource_characteristic_name"));
			}

			//ResourceDtoを作成
			Resource resource = new Resource(resourceId, resourceName, officeName, category,
				capacity, supplement, deleted, facility, usageStopStartDate,usageStopEndDate);

			
			//「利用日」「利用開始時間」「利用終了時間」を作る
			SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy年M月d");

//			/* 「yyyy/MM/dd」形式で正しい日付か	*/
//			SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy/MM/dd");
//			inputFormat.setLenient(false); // 日時解析を厳密に行う
//
//			SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd");
//			timestampFormat.setLenient(false); // 日時解析を厳密に行う
//
//			datePattern = Pattern.compile("^[0-9]{4}/[0-9]{2}/[0-9]{2}$");
//			Matcher m = datePattern.matcher(date);
//			notDate = !m.find();
//
//			/* 「yyyy/MM/dd」形式で正しい日付か */
//			if (!notDate) {
//
//				try {
//					_date = Timestamp
//							.valueOf(timestampFormat.format(inputFormat.parse(date)) + " " + hour + ":" + minute + ":00");
//					notDate = false;
//				} catch (ParseException e) {
//					notDate = true;
//				}
//			}
			
			
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

			ReservationDto reservationDto = new ReservationDto(reserveId, resource,
					usageDate, _usageStartTime, _usageEndTime, _reservationName,
					_reservedPerson, _coReservedPerson, _numberOfParticipants,
					_AttendanceTypeDto, supplement);

		}finally{
			try {
				dbHelper.closeResource(rs);
			} catch (SQLException e) {
				e.printStackTrace();
				_log.error("SQLException");
			}

			try {
				dbHelper.closeResource(preparedStatement);
			} catch (SQLException e) {
				e.printStackTrace();
				_log.error("SQLException");
			}
		}


		return null;

	}

}
