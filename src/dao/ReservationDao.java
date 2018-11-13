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

import dto.AttendanceTypeDto;
import dto.ReservationDto;
import dto.Resource;
import dto.TimeDto;
import dto.User;

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
		ReservationDto reservationDto;


		try {
			//実行するSQL文
			String sql
			="select resources.resource_id, usage_start_date, usage_end_date, "
					+ " reservation_name ,reserved_person_id, co_reserved_person_id, "
					+ "number_of_participants, attendance_types.attendance_type_id, "
					+ "reserve_supplement, reservations.deleted as reservation_deleted, "
					+ "resource_name, office_name, category_name, capacity, supplement, "
					+ "usage_stop_start_date, usage_stop_end_date, "
					+ "resources.deleted as resource_deleted, "
					+ "	attendance_type, "
					+ "resource_characteristic_name, "
					+ "users.user_id, users.password, users.family_name, users.first_name, "
					+ "users.authority, users.tel, users.mail_address,"
					+ "cousers.user_id as co_user_id, cousers.password as co_password, "
					+ "cousers.family_name as co_family_name, cousers.first_name as co_first_name,"
					+ " cousers.authority as co_authority, cousers.tel as co_tel, "
					+ "cousers.mail_address as co_mail_address "

			+ "from reservations, resources, attendance_types, users , users as cousers ,"
					+ " resource_features,resource_characteristics , offices , categories "
			+ "where resources.resource_id = reservations.resource_id"
				+ "and resource_features.resource_characteristic_id = resource_characteristics.resource_characteristic_id "
				+ "and attendance_types.attendance_type_id = reservations.attendance_type_id "
				+ "and users.user_id = reservations.reserved_person_id "
				+ "and cousers.user_id = reservations.co_reserved_person_id "
				+ "and resources.resource_id = resource_features.resource_id "
				+ "and resources.office_id = offices.office_id "
				+ "and resources.category_id = categories.category_id "
				+ "and reserve_id = ?;";


			preparedStatement = _con.prepareStatement(sql);
			preparedStatement.setInt(1,reserveId);
			rs = preparedStatement.executeQuery();	//実行



			//reservationsテーブルのカラムをセットするために用意
			String resourceId = null;	//リソースID
			Timestamp usageStartDate = null; //利用開始日時
			Timestamp usageEndDate = null; //利用終了日時
			String reservationName = null;	//予約名称
			String reservedPersonId; //予約者ID
			String coReservedPersonId;//共同予約者ID
			int numberOfParticipants = 0;	//利用人数
			int attendanceTypeId = 0;	//参加者種別ID
			Timestamp reserveSupplement; //補足
			int reservationDeleted = 0; //予約削除済み


			//resourcesテーブルのカラムをセットするために用意
			String resourceName = null;	//リソース名
			String officeName = null;	//オフィス名
			String category = null;	//カテゴリ
			int capacity = 0;	//定員
			String supplement = null; //補足
			List<String> facility;	//設備リスト
			Timestamp usageStopStartDate = null;	//利用停止開始日時
			Timestamp usageStopEndDate = null;	//利用停止終了日時
			int resourceDeleted = 0; //リソース削除済み

			//attendance_typesテーブルのカラムをセットするために用意
			String attendanceType = null; //参加者種別

			//List<String> facility を作るために用意
			facility = new ArrayList<String>();

			//「予約者」「共同予約者」のUserDtoを作るために用意
			String userId = null;
			String password = null;
			int authority = 0;
			String familyName = null;
			String firstName = null;
			String phoneNumber = null;
			String mailAddress = null;

			String coUserId = null;
			String coPassword = null;
			int coAuthority = 0;
			String coFamilyName = null;
			String coFirstName = null;
			String coFhoneNumber = null;
			String coMailAddress = null;



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
				reservationDeleted = rs.getInt("reservation_deleted");

				resourceName = rs.getString("resource_name");
				officeName = rs.getString("office_name");
				category = rs.getString("category_name");
				capacity = rs.getInt("capacity");
				supplement = rs.getString("supplement");
				usageStopStartDate = rs.getTimestamp("usage_stop_start_date");
				usageStopEndDate = rs.getTimestamp("usage_stop_end_date");
				resourceDeleted = rs.getInt("resources_deleted");

				attendanceType = rs.getString("attendance_type");

				facility.add(rs.getString("resource_characteristic_name"));


				userId = rs.getString("user_id");
				password = rs.getString("password");
				familyName = rs.getString("family_name");
				firstName = rs.getString("first_name");
				authority = rs.getInt("authority");
				phoneNumber = rs.getString("tel");
				mailAddress = rs.getString("mail_address");

				coUserId = rs.getString("co_user_id");
				coPassword = rs.getString("co_password");
				coFamilyName = rs.getString("co_family_name");
				coFirstName = rs.getString("co_first_name");
				coAuthority = rs.getInt("co_authority");
				coFhoneNumber = rs.getString("co_tel");
				coMailAddress = rs.getString("co_mail_address");
			}

			//ResourceDtoを作成
			Resource resource = new Resource(resourceId, resourceName, officeName, category,
				capacity, supplement, resourceDeleted, facility, usageStopStartDate,usageStopEndDate);


			//「利用日」を作る
			SimpleDateFormat usageDateFormat = new SimpleDateFormat("yyyy年M月d日");
			String usageDate = usageDateFormat.format(usageStartDate);

			//「利用開始時間」「利用終了時間」のDTOを作る
			TimeDto usageStartTime = new TimeDto(usageStartDate);
			TimeDto usageEndTime = new TimeDto(usageEndDate);

			//「予約者」のDTOを作る
			User reservedPerson = new User(userId, password, authority, familyName,
					firstName, phoneNumber, mailAddress);

			//「共同予約者」のDTOを作る
			User coReservedPerson = new User(coUserId, coPassword, coAuthority,
					coFamilyName, coFirstName, coFhoneNumber, coMailAddress);

			//「参加者種別」のDTO
			AttendanceTypeDto attendanceTypeDto =
					new AttendanceTypeDto(attendanceTypeId, attendanceType);


			reservationDto = new ReservationDto(reserveId, resource,
					usageDate, usageStartTime, usageEndTime, reservationName,
					reservedPerson, coReservedPerson, numberOfParticipants,
					attendanceTypeDto, supplement, reservationDeleted);

		}finally{
			try {
				dbHelper.closeResource(rs);
			} catch (Exception e1) {
				e1.printStackTrace();
				_log.error("queryById() Exception e1");
			}

			try {
				dbHelper.closeResource(preparedStatement);
			} catch (Exception e2) {
				e2.printStackTrace();
				_log.error("queryById() Exception e2");
			}
		}

		return reservationDto;

	}

	public List<ReservationDto> queryByInput(String usageDate,TimeDto usageStartTime,TimeDto usageEndTime,String officeId,String categoryId,String userId,boolean onlyMyReservation,boolean pastReservation,boolean deletedReservation)throws SQLException{
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
			sqlBuilder.append("SELECT reserve_id reserveid,reservations.resource_id resourceid,resource_name resourcename,office_name officename,category_name categoryname,usage_start_time starttime,usage_end_time endtime,reservation_name reservename,reservations.user_id userid, family_name familyname,first_name firstname,deleted deleted");
			sqlBuilder.append("FROM reservations,users,resources,categories,offices,params");
			sqlBuilder.append("WHERE reservations.resource_id=resources.resource_id AND resources.officeId=offices.officeId AND resources.category_id=categories_category_id AND reserved_person_id=user_id");

			Timestamp usageDateTimestamp=null;
			Timestamp after30Timestamp=null;
			if(usageDate!=null){
				sqlBuilder.append("AND usage_start_time > p1_usage_date AND usage_end_time < p2_after_30_date");
				usageDateTimestamp=new TimeDto(0).getTimeStamp(usageDate);
				Calendar calendar=Calendar.getInstance();
				calendar.setTime(usageDateTimestamp);
				calendar.add(Calendar.DATE, TERM_FOR_RESERVATION_SEARCH);
				after30Timestamp=new Timestamp(calendar.getTime().getTime());
			}

			sqlBuilder.append("AND EXTRACT(hour FROM usage_start_time)*60+EXTRACT(minute usage_start_time)>p3_usage_start_minute_value AND EXTRACT(hour FROM usage_end_time)*60+EXTRACT(minute usage_end_time)<p4_usage_end_minute_value");

			if(officeId!=null){
				sqlBuilder.append("AND resources.officeId=p5_office_id ");
			}

			if(categoryId!=null){
				sqlBuilder.append("AND resources.categoryId=p6_category_id");
			}

			if(onlyMyReservation){
				sqlBuilder.append("AND (reserved_person_id=p7_user_id OR co_reserved_person_id=p7_user_id)");
			}

			if(!pastReservation){
				sqlBuilder.append("AND usage_end_time>current_timestamp");
			}

			if(deletedReservation){
				sqlBuilder.append("AND (deleted = 0 OR (reserved_person_id=p7_user_id OR co_reserved_person_id=p7_user_id))");
			}else{
				sqlBuilder.append("AND deleted = 0");
			}

			sqlBuilder.append("ORDER BY usage_start_time , resources.office_id,resources.category_id,reservations.resource_id,reserve_id");

			preparedStatement=_con.prepareStatement(sqlBuilder.toString());

			preparedStatement.setTimestamp(1, usageDateTimestamp);
			preparedStatement.setTimestamp(2, after30Timestamp);
			preparedStatement.setInt(3, usageStartTime.getTimeMinutesValue());
			preparedStatement.setInt(4, usageEndTime.getTimeMinutesValue());
			preparedStatement.setString(5, officeId);
			preparedStatement.setString(6, categoryId);
			preparedStatement.setString(7, userId);

			rs=preparedStatement.executeQuery();

			while(rs.next()){
				Resource resource = new Resource(rs.getString("resourceid"), rs.getString("resourcename"), rs.getString("officename"), rs.getString("categoryname"), 0, null, 0, null, null, null);
				User user=new User(rs.getString("userid"), null, 0, rs.getString("familyname"), rs.getString("firstname"), null, null);
				String resultUsageDate=new SimpleDateFormat("yyyy/MM/dd").format(rs.getTimestamp("starttime"));
				TimeDto resultUsageStartTime=new TimeDto(rs.getTimestamp("starttime"));
				TimeDto resultUsageEndTime=new TimeDto(rs.getTimestamp("endtime"));

				ReservationDto reservation = new ReservationDto(rs.getInt("reserveid"), resource, resultUsageDate, resultUsageStartTime, resultUsageEndTime, rs.getString("reservename"), user, null, 0, null, null, rs.getInt("deleted"));
				reservationList.add(reservation);
			}


		}finally{
			try {
				dbHelper.closeResource(rs);
			} catch (Exception e) {
				e.printStackTrace();
				_log.error("Exception");
			}

			try {
				dbHelper.closeResource(preparedStatement);
			} catch (Exception e) {
				e.printStackTrace();
				_log.error("Exception");
			}
			dbHelper.closeDb();

		}

		return reservationList;
	}
}
