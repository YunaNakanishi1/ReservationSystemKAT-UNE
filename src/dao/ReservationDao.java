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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.ReservationDto;
import dto.Resource;
import dto.TimeDto;

/**
  * (6 8 10 11 12 13 17 18).
  * @author リコーITソリューションズ株式会社 KAT-UNE
  */
public class ReservationDao {

	private Connection _con = null;
	private static Logger _log = LogManager.getLogger();

	private static final int TERM_FOR_RESERVATION_SEARCH=30;




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
			int reservationDeleted; //削除済み


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


			//「利用日」を作る
			SimpleDateFormat usageDateFormat = new SimpleDateFormat("yyyy年M月d日");
			String usageDate = usageDateFormat.format(usageStartDate);

			//「利用開始時間」「利用終了時間」のDTOを作る
			TimeDto usageStartTime = new TimeDto(usageStartDate);
			TimeDto usageEndTime = new TimeDto(usageEndDate);


			//「予約者」「共同予約者」のUserDtoを作る


//			private String _userId;
//			private String _password;
//			private int _authority;
//			private String _familyName;
//			private String _firstName;
//			private String _phoneNumber;
//			private String _mailAddress;
//
//			user_id  | password | family_name | first_name | authority |     tel      |       mail_address

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
					usageDate, usageStartTime, usageEndTime, reservationName,
					reservedPerson, coReservedPerson, numberOfParticipants,
					AttendanceTypeDto, supplement);

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

	public List<ReservationDto> queryByInput(String usageDate,TimeDto usageStartTime,TimeDto usagEndTime,String officeId,String categoryId,String userId,boolean onlyMyReservation,boolean pastReservation,boolean deletedReservation)throws SQLException{
		List<ReservationDto> reservationList=new ArrayList<ReservationDto>();

		DBHelper dbHelper = new DBHelper();
		_con = dbHelper.connectDb(); //dbに接続

		if (_con == null) {
			_log.error("DatabaseConnectError");
			throw new SQLException();	//エラー処理はハンドラーに任せる
		}

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		StringBuilder sqlBuilder=new StringBuilder();

		try{
			sqlBuilder.append("WITH params AS ( SELECT ? AS p1_usage_date,? AS p2_after_30_date,? AS p3_usage_start_minute_value,? AS p4_usage_end_minute_value ,? AS p5_office_id ? AS p6_category_id,? AS p7_user_id");
			sqlBuilder.append("SELECT reserve_id,reservations.resource_id,resource_name,office_name,category_name,usage_start_time,usage_end_time,reservation_name,reservations.user_id, user_name,deleted");
			sqlBuilder.append("FROM reservations,users,resources,categories,offices,params");
			sqlBuilder.append("WHERE reservations.resource_id=resources.resource_id AND resources.officeId=offices.officeId AND resources.category_id=categories_category_id AND reserved_person_id=user_id");

			Timestamp usageDateTimestamp=null;
			Timestamp after30Timestamp=null;
			if(usageDate!=null){
				sqlBuilder.append("AND usage_start_time > p1_usage_date AND usage_end_time < p2_after_30_date");
				usageDateTimestamp=new TimeDto(0).getTimeStamp(usageDate);
				Calendar calendar=Calendar.getInstance();
				calendar.setTime(usageDateTimestamp);
				calendar.add(Calendar.DATE, 30);
				after30Timestamp=new Timestamp(calendar.getTime().getTime());
			}


		}finally{

		}

		return reservationList;
	}
}
