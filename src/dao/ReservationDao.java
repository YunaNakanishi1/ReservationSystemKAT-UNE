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
			="select distinct reserve_id,resources.resource_id, usage_start_date, usage_end_date, reservation_name ,reserved_person_id, co_reserved_person_id, number_of_participants, reservations.attendance_type_id, reserve_supplement, reservations.deleted as reservation_deleted, resource_name, office_name, category_name, capacity, supplement, usage_stop_start_date, usage_stop_end_date, resources.deleted as resource_deleted, attendance_type, users.user_id, users.password, users.family_name, users.first_name, users.authority, users.tel, users.mail_address, cousers.user_id as co_user_id, cousers.password as co_password, cousers.family_name as co_family_name, cousers.first_name as co_first_name, cousers.authority as co_authority, cousers.tel as co_tel, cousers.mail_address as co_mail_address "
			        +"from reservations, resources, attendance_types, users , users as cousers , offices , categories "
			        +"where resources.resource_id = reservations.resource_id "
			        +"and resources.office_id = offices.office_id "
			        +"and resources.category_id = categories.category_id "
			        +"and users.user_id = reservations.reserved_person_id "
			        +"and reserve_id = ?; ";



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
			String reserveSupplement=null; //補足
			int reservationDeleted = 0; //予約削除済み


			//resourcesテーブルのカラムをセットするために用意
			String resourceName = null;	//リソース名
			String officeName = null;	//オフィス名
			String category = null;	//カテゴリ
			int capacity = 0;	//定員
			String supplement = null; //補足
			List<String> facility = new ArrayList<>();	//設備リスト
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



			if (rs.next()) {
				resourceId = rs.getString("resource_id");
				usageStartDate = rs.getTimestamp("usage_start_date");
				usageEndDate = rs.getTimestamp("usage_end_date");
				reservationName = rs.getString("reservation_name");
				reservedPersonId = rs.getString("reserved_person_id");
				coReservedPersonId = rs.getString("co_reserved_person_id");
				numberOfParticipants = rs.getInt("number_of_participants");
				attendanceTypeId = rs.getInt("attendance_type_id");
				reserveSupplement = rs.getString("reserve_supplement");
				reservationDeleted = rs.getInt("reservation_deleted");

				resourceName = rs.getString("resource_name");
				officeName = rs.getString("office_name");
				category = rs.getString("category_name");
				capacity = rs.getInt("capacity");
				supplement = rs.getString("supplement");
				usageStopStartDate = rs.getTimestamp("usage_stop_start_date");
				usageStopEndDate = rs.getTimestamp("usage_stop_end_date");
				resourceDeleted = rs.getInt("resource_deleted");

				attendanceType = rs.getString("attendance_type");

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

			//リソース特性取得
			final String featuresSql = "select resource_features.resource_characteristic_id,resource_characteristic_name "
                    + "from resource_features,resource_characteristics " + "where resource_id=? and "
                    + "resource_features.resource_characteristic_id "
                    + "= resource_characteristics.resource_characteristic_id ";

			PreparedStatement featuresPstmt = _con.prepareStatement(featuresSql);
			featuresPstmt.setString(1, resourceId);

            ResultSet featuresRs = featuresPstmt.executeQuery(); // 実行

            while (featuresRs.next()) {
                String facilityName = featuresRs.getString("resource_characteristic_name");
                facility.add(facilityName);
            }

			//ResourceDtoを作成
			Resource resource = new Resource(resourceId, resourceName, officeName, category,
				capacity, supplement, resourceDeleted, facility, usageStopStartDate,usageStopEndDate);


			//「利用日」を作る(年月日)
			//SimpleDateFormat usageDateFormat = new SimpleDateFormat("yyyy年M月d日");
			//String usageDate = usageDateFormat.format(usageStartDate);

			//System.out.println(usageStartDate);

			//「利用日」を作る(yyyy/MM/mm)
			SimpleDateFormat usageDateFormat = new SimpleDateFormat("yyyy/M/d");
			String usageDate = usageDateFormat.format(usageStartDate);


			//「利用開始時間」「利用終了時間」のDTOを作る
			TimeDto usageStartTime = new TimeDto(usageStartDate);
			TimeDto usageEndTime = new TimeDto(usageEndDate);
			//利用終了が00:00だったら24:00に直す
			if(usageEndTime.getTimeMinutesValue()==0){
				usageEndTime=new TimeDto(24, 0);
			}

			//「予約者」のDTOを作る
			User reservedPerson = new User(userId, password, authority, familyName,
					firstName, phoneNumber, mailAddress);

			//「共同予約者」のDTOを作る
			User coReservedPerson;

			if(coUserId != null){
			coReservedPerson = new User(coUserId, coPassword, coAuthority,
					coFamilyName, coFirstName, coFhoneNumber, coMailAddress);
			}else{
			    coReservedPerson = null;
			}

			//「参加者種別」のDTO
			AttendanceTypeDto attendanceTypeDto;
			if(attendanceTypeId != 0){
			    attendanceTypeDto = new AttendanceTypeDto(attendanceTypeId, attendanceType);
			}else{
			    attendanceTypeDto = null;
			}


			reservationDto = new ReservationDto(reserveId, resource,
					usageDate, usageStartTime, usageEndTime, reservationName,
					reservedPerson, coReservedPerson, numberOfParticipants,
					attendanceTypeDto, reserveSupplement, reservationDeleted);

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

	/**入力の条件を満たすリソースの一覧を返す.
	 * @param usageDate 利用日
	 * @param usageStartTime 利用開始時間
	 * @param usageEndTime 利用終了時間
	 * @param officeId 事業所ID
	 * @param categoryId カテゴリID
	 * @param userId ユーザーID
	 * @param onlyMyReservation 自分の予約のみ表示
	 * @param pastReservation 過去の予約も表示
	 * @param deletedReservation 削除済みの予約も表示
	 * @return 予約の一覧
	 * @throws SQLException データベースエラー
	 */
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

			//SQL文の作成
			sqlBuilder.append("WITH params AS ( SELECT ?::timestamp  AS p1_usage_date,?::timestamp AS p2_after_30_date,? AS p3_usage_start_minute_value,? AS p4_usage_end_minute_value ,? AS p5_office_id,? AS p6_category_id,? AS p7_user_id )");
			sqlBuilder.append("SELECT reserve_id reserveid,reservations.resource_id resourceid,resource_name resourcename,office_name officename,category_name categoryname,usage_start_date starttime,usage_end_date endtime,reservation_name reservename,family_name familyname,first_name firstname,reservations.deleted reservedeleted ");
			sqlBuilder.append("FROM reservations,users,resources,categories,offices,params ");
			sqlBuilder.append("WHERE reservations.resource_id=resources.resource_id AND resources.office_id=offices.office_id AND resources.category_id=categories.category_id AND reserved_person_id=user_id ");

			Timestamp usageDateTimestamp=new Timestamp(0);
			Timestamp after30Timestamp=new Timestamp(0);
			if(usageDate!=null){
				sqlBuilder.append("AND usage_start_date >= p1_usage_date AND usage_end_date <= p2_after_30_date ");
				usageDateTimestamp=new TimeDto(0).getTimeStamp(usageDate);
				Calendar calendar=Calendar.getInstance();
				calendar.setTime(usageDateTimestamp);
				calendar.add(Calendar.DAY_OF_MONTH, TERM_FOR_RESERVATION_SEARCH);
				after30Timestamp=new Timestamp(calendar.getTime().getTime());
			}

			sqlBuilder.append("AND (EXTRACT(hour FROM usage_start_date)*60 + EXTRACT(minute FROM usage_start_date)) >= p3_usage_start_minute_value AND (EXTRACT(hour FROM usage_end_date)*60 + EXTRACT(minute FROM usage_end_date)) <= p4_usage_end_minute_value ");

			if(officeId!=null){
				sqlBuilder.append("AND resources.office_id=p5_office_id ");
			}

			if(categoryId!=null){
				sqlBuilder.append("AND resources.category_id=p6_category_id ");
			}

			if(onlyMyReservation){
				sqlBuilder.append("AND (reserved_person_id = p7_user_id OR co_reserved_person_id = p7_user_id) ");
			}

			if(!pastReservation){
				sqlBuilder.append("AND usage_end_date > current_timestamp ");
			}

			if(deletedReservation){
				//sqlBuilder.append("AND (reservations.deleted = 1 OR (reserved_person_id=p7_user_id OR co_reserved_person_id=p7_user_id)) ");
				sqlBuilder.append("AND (reservations.deleted = 1 OR reservations.deleted = 0) ");
			}else{
				sqlBuilder.append("AND reservations.deleted = 0 ");
			}

			sqlBuilder.append("ORDER BY usage_start_date , resources.office_id,resources.category_id,reservations.resource_id,reserve_id;");

			preparedStatement=_con.prepareStatement(sqlBuilder.toString());

			preparedStatement.setTimestamp(1, usageDateTimestamp);
			preparedStatement.setTimestamp(2, after30Timestamp);
			preparedStatement.setInt(3, usageStartTime.getTimeMinutesValue());
			preparedStatement.setInt(4, usageEndTime.getTimeMinutesValue());
			preparedStatement.setString(5, officeId);
			preparedStatement.setString(6, categoryId);
			preparedStatement.setString(7, userId);

			//SQLの実行
			rs=preparedStatement.executeQuery();

			//結果の取得
			while(rs.next()){
				Resource resource = new Resource(rs.getString("resourceid"), rs.getString("resourcename"), rs.getString("officename"), rs.getString("categoryname"), 0, null, 0, null, null, null);
				User user=new User(null, null, 0, rs.getString("familyname"), rs.getString("firstname"), null, null);
				String resultUsageDate=new SimpleDateFormat("yyyy/MM/dd").format(rs.getTimestamp("starttime"));
				TimeDto resultUsageStartTime=new TimeDto(rs.getTimestamp("starttime"));
				TimeDto resultUsageEndTime=new TimeDto(rs.getTimestamp("endtime"));
				//利用終了が00:00だったら24:00に直す
				if(resultUsageEndTime.getTimeMinutesValue()==0){
					resultUsageEndTime=new TimeDto(24, 0);
				}

				ReservationDto reservation = new ReservationDto(rs.getInt("reserveid"), resource, resultUsageDate, resultUsageStartTime, resultUsageEndTime, rs.getString("reservename"), user, null, 0, null, null, rs.getInt("reservedeleted"));
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

	public List<ReservationDto> queryByResources(List<Resource> resourceIdList, Timestamp startTime, Timestamp endTime) throws SQLException
	{
	    List<ReservationDto> reservationList = new ArrayList<>();

	    DBHelper dbHelper = new DBHelper();
        _con = dbHelper.connectDb(); //dbに接続

        if (_con == null) {
            _log.error("DatabaseConnectError");
            throw new SQLException();   //エラー処理はハンドラーに任せる
        }

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        //SQLの前半部分　NOTMUCHRESOURCEIDはリソースIDの文字をつなげやすくするためのダミー要素
        String sql = "select reserve_id reserveid,reservations.resource_id resourceid,resource_name resourcename,office_name officename,category_name categoryname,usage_start_date starttime,usage_end_date endtime,reservation_name reservename,family_name familyname,first_name firstname,reservations.deleted reservedeleted,resources.capacity capacity,resources.supplement supplement,usage_stop_start_date ,usage_stop_end_date "
                    +"from reservations,resources,offices,categories,users "
                    +"where reservations.resource_id=resources.resource_id AND resources.office_id=offices.office_id AND resources.category_id=categories.category_id AND reserved_person_id=user_id "
                    +"and reservations.deleted=0 "
                    +"and usage_start_date >= ? "
                    +"and usage_end_date <= ? "
                    +"and reservations.resource_id in ('NOTMUCHRESOURCEID' ";

        //リソースIDの要素
        for(int i=0;i<resourceIdList.size();i++){
            sql += ",? ";
        }
        //SQLの後半部分
        sql += ");";
        try{
            preparedStatement = _con.prepareStatement(sql);

            //パラメータの設定
            int StatementCount = 1;
            preparedStatement.setTimestamp(StatementCount++,startTime);
            preparedStatement.setTimestamp(StatementCount++,endTime);
            for(int i=0;i<resourceIdList.size();i++){
                preparedStatement.setString(StatementCount++,resourceIdList.get(i).getResourceId());
            }

            rs = preparedStatement.executeQuery();  //実行
            while(rs.next()){

                Resource resource = new Resource(rs.getString("resourceid"), rs.getString("resourcename"), rs.getString("officename"), rs.getString("categoryname"), rs.getInt("capacity"), rs.getString("supplement"), 0, null, rs.getTimestamp("usage_stop_start_date"), rs.getTimestamp("usage_stop_end_date"));
                String resultUsageDate=new SimpleDateFormat("yyyy/MM/dd").format(rs.getTimestamp("starttime"));
                TimeDto resultUsageStartTime=new TimeDto(rs.getTimestamp("starttime"));
                TimeDto resultUsageEndTime=new TimeDto(rs.getTimestamp("endtime"));
              //利用終了が00:00だったら24:00に直す
				if(resultUsageEndTime.getTimeMinutesValue()==0){
					resultUsageEndTime=new TimeDto(24, 0);
				}

                ReservationDto reservation = new ReservationDto(rs.getInt("reserveid"), resource, resultUsageDate, resultUsageStartTime, resultUsageEndTime, rs.getString("reservename"), null, null, 0, null, null, rs.getInt("reservedeleted"));
                reservationList.add(reservation);            }


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
	public int deleteReservation(int reserveId) throws SQLException{
		DBHelper dbHelper = new DBHelper();
		_con = dbHelper.connectDb(); //dbに接続

		if (_con == null) {
			_log.error("DatabaseConnectError");
			throw new SQLException();	//エラー処理はハンドラーに任せる
            }

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int result;

		try{
			String sql = "UPDATE reservations SET deleted = 1 WHERE reserve_id = ?";
			preparedStatement = _con.prepareStatement(sql);
			preparedStatement.setInt(1, reserveId);
            result = preparedStatement.executeUpdate();

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

	    return result;

	}

	public List<ReservationDto> queryBetweenDate(String resourceId,Timestamp startTime,Timestamp endTime) throws SQLException{
		List<ReservationDto> reservationList = new ArrayList<ReservationDto>();

		DBHelper dbHelper = new DBHelper();
		_con = dbHelper.connectDb(); //dbに接続

		if (_con == null) {
			_log.error("DatabaseConnectError");
			throw new SQLException();	//エラー処理はハンドラーに任せる
            }

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try{

//			StringBuilder sqlBuilder = new StringBuilder("select reserve_id, resources.resource_id, usage_start_date, usage_end_date, "
//					+ " reservation_name ,reserved_person_id, co_reserved_person_id, "
//					+ "number_of_participants, attendance_types.attendance_type_id, "
//					+ "reserve_supplement, reservations.deleted as reservation_deleted, "
//					+ "resource_name, office_name, category_name, capacity, supplement, "
//					+ "usage_stop_start_date, usage_stop_end_date, "
//					+ "resources.deleted as resource_deleted, "
//					+ "	attendance_type, "
//					+ "resource_characteristic_name, "
//					+ "users.user_id, users.password, users.family_name, users.first_name, "
//					+ "users.authority, users.tel, users.mail_address, "
//					+ "cousers.user_id as co_user_id, cousers.password as co_password, "
//					+ "cousers.family_name as co_family_name, cousers.first_name as co_first_name, "
//					+ " cousers.authority as co_authority, cousers.tel as co_tel, "
//					+ "cousers.mail_address as co_mail_address ");


			StringBuilder sqlBuilder = new StringBuilder("select reserve_id, resources.resource_id, usage_start_date, usage_end_date, "
					+ " reservation_name ,reserved_person_id, co_reserved_person_id, "
					+ " number_of_participants, attendance_types.attendance_type_id, "
					+ "reserve_supplement, reservations.deleted as reservation_deleted, "
					+ "resource_name, office_name, category_name, capacity, supplement, "
					+ "usage_stop_start_date, usage_stop_end_date, "
					+ "resources.deleted as resource_deleted, "
					+ "attendance_type, users.user_id, users.password, users.family_name, users.first_name, "
					+ "users.authority, users.tel, users.mail_address, "
					+ " cousers.user_id as co_user_id, cousers.password as co_password, "
					+ "cousers.family_name as co_family_name, cousers.first_name as co_first_name, "
					+ "cousers.authority as co_authority, cousers.tel as co_tel, cousers.mail_address as co_mail_address ");



//			sqlBuilder.append("from reservations, resources, attendance_types, users , users as cousers ,"
//					+ " resource_features,resource_characteristics , offices , categories ");

			sqlBuilder.append("from reservations "
					+ "left outer join users as cousers on reservations.co_reserved_person_id = cousers.user_id "
					+ "left outer join attendance_types on attendance_types.attendance_type_id = reservations.attendance_type_id, "
					+ "resources, users , offices , categories ");



//			sqlBuilder.append("where resources.resource_id = reservations.resource_id "
//				+ "and resource_features.resource_characteristic_id = resource_characteristics.resource_characteristic_id "
//				+ "and (attendance_types.attendance_type_id = reservations.attendance_type_id or reservations.attendance_type_id is NULL) "
//				+ "and users.user_id = reservations.reserved_person_id "
//				+ "and (cousers.user_id = reservations.co_reserved_person_id or reservations.co_reserved_person_id is NULL) "
//				+ "and resources.resource_id = resource_features.resource_id "
//				+ "and resources.office_id = offices.office_id "
//				+ "and resources.category_id = categories.category_id ");

			sqlBuilder.append("where resources.resource_id = reservations.resource_id "
					+ "and users.user_id = reservations.reserved_person_id "
					+ "and resources.office_id = offices.office_id "
					+ "and resources.category_id = categories.category_id ");

			sqlBuilder.append("and reservations.resource_id=? and usage_end_date > ? and usage_start_date < ? ");
			sqlBuilder.append("order by reserve_id ;");

			preparedStatement=_con.prepareStatement(sqlBuilder.toString());

			preparedStatement.setString(1, resourceId);
			preparedStatement.setTimestamp(2, startTime);
			preparedStatement.setTimestamp(3, endTime);


			rs=preparedStatement.executeQuery();


			//reservationsテーブルのカラムをセットするために用意
			int reserveId=0;
			Timestamp usageStartDate = null; //利用開始日時
			Timestamp usageEndDate = null; //利用終了日時
			String reservationName = null;	//予約名称
			int numberOfParticipants = 0;	//利用人数
			int attendanceTypeId = 0;	//参加者種別ID
			String reserveSupplement=null; //補足
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
				//System.out.println("ReservationDao rs.next()");
				reserveId = rs.getInt("reserve_id");
				usageStartDate = rs.getTimestamp("usage_start_date");

				//System.out.println(usageStartDate);

				usageEndDate = rs.getTimestamp("usage_end_date");
				reservationName = rs.getString("reservation_name");
				numberOfParticipants = rs.getInt("number_of_participants");
				attendanceTypeId = rs.getInt("attendance_type_id");
				reserveSupplement = rs.getString("reserve_supplement");
				reservationDeleted = rs.getInt("reservation_deleted");

				resourceName = rs.getString("resource_name");
				officeName = rs.getString("office_name");
				category = rs.getString("category_name");
				capacity = rs.getInt("capacity");
				supplement = rs.getString("supplement");
				usageStopStartDate = rs.getTimestamp("usage_stop_start_date");
				usageStopEndDate = rs.getTimestamp("usage_stop_end_date");
				resourceDeleted = rs.getInt("resource_deleted");

				attendanceType = rs.getString("attendance_type");
				//facility.add(rs.getString("resource_characteristic_name"));
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

				//ResourceDtoを作成
				Resource resource = new Resource(resourceId, resourceName, officeName, category,
					capacity, supplement, resourceDeleted, facility, usageStopStartDate,usageStopEndDate);


				//「利用日」を作る
				SimpleDateFormat usageDateFormat = new SimpleDateFormat("yyyy/M/d");
				String usageDate = usageDateFormat.format(usageStartDate);

				//「利用開始時間」「利用終了時間」のDTOを作る
				TimeDto usageStartTime = new TimeDto(usageStartDate);
				TimeDto usageEndTime = new TimeDto(usageEndDate);
				//利用終了が00:00だったら24:00に直す
				if(usageEndTime.getTimeMinutesValue()==0){
					usageEndTime=new TimeDto(24, 0);
				}

				//「予約者」のDTOを作る
				User reservedPerson = new User(userId, password, authority, familyName,
						firstName, phoneNumber, mailAddress);

				//「共同予約者」のDTOを作る
				User coReservedPerson = new User(coUserId, coPassword, coAuthority,
						coFamilyName, coFirstName, coFhoneNumber, coMailAddress);

				//「参加者種別」のDTO
				AttendanceTypeDto attendanceTypeDto =
						new AttendanceTypeDto(attendanceTypeId, attendanceType);


				ReservationDto reservationDto = new ReservationDto(reserveId, resource,
						usageDate, usageStartTime, usageEndTime, reservationName,
						reservedPerson, coReservedPerson, numberOfParticipants,
						attendanceTypeDto, reserveSupplement, reservationDeleted);

				reservationList.add(reservationDto);
				//System.out.println(reservationList.size());
			}

		} catch(SQLException e) {
			e.printStackTrace();
			throw new SQLException();


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

	public int insertReservation(ReservationDto reservation) throws SQLException{
		int reserveId = -1;

		DBHelper dbHelper = new DBHelper();
		_con = dbHelper.connectDb(); //dbに接続

		if (_con == null) {
			_log.error("DatabaseConnectError");
			throw new SQLException();	//エラー処理はハンドラーに任せる
            }

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try{
			_con.setAutoCommit(false);
			StringBuilder sqlBuilder=new StringBuilder("INSERT INTO reservations (reserve_id,resource_id,usage_start_date,usage_end_date,reservation_name,reserved_person_id,co_reserved_person_id,number_of_participants,attendance_type_id,reserve_supplement,deleted)");
			sqlBuilder.append("VALUES (nextval('reserve_id_seq'),?,?,?,?,?,?,?,?,?,?)");
			sqlBuilder.append("RETURNING reserve_id");

			preparedStatement=_con.prepareStatement(sqlBuilder.toString());

			preparedStatement.setString(1, reservation.getResource().getResourceId());
			String usageDate=reservation.getUsageDate();
			preparedStatement.setTimestamp(2, reservation.getUsageStartTime().getTimeStamp(usageDate));
			preparedStatement.setTimestamp(3, reservation.getUsageEndTime().getTimeStamp(usageDate));
			preparedStatement.setString(4, reservation.getReservationName());
			preparedStatement.setString(5, reservation.getReservedPerson().getUserId());
			preparedStatement.setString(6, reservation.getCoReservedPerson().getUserId());
			preparedStatement.setInt(7, reservation.getNumberOfParticipants());
			if(reservation.getAttendanceTypeDto()!=null){
			preparedStatement.setInt(8, reservation.getAttendanceTypeDto().getAttendanceTypeId());
			}else{
				preparedStatement.setNull(8, java.sql.Types.INTEGER);
			}
			preparedStatement.setString(9, reservation.getSupplement());
			preparedStatement.setInt(10, reservation.getDeleted());

			rs=preparedStatement.executeQuery();

			if(rs.next()){
				reserveId=rs.getInt(1);
				_con.commit();
			}else{
				_con.rollback();
			}

		}catch(SQLException e){
			_con.rollback();
			throw e;
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

		return reserveId;
	}

	/**
	 * 予約の変更処理
	 * @param reservation 予約情報
	 * @return
	 */
	public int updateReservation(ReservationDto reservation) throws SQLException {
		int result = -1;
        PreparedStatement stmt = null;

		DBHelper dbHelper = new DBHelper();
		_con = dbHelper.connectDb(); //dbに接続

		if (_con == null) {
			_log.error("DatabaseConnectError");
			throw new SQLException();	//エラー処理はハンドラーに任せる
        }


		try{
			_con.setAutoCommit(false);
			String usageDate = reservation.getUsageDate();
			String sql = "UPDATE public.reservations SET usage_start_date=?, usage_end_date=?, reservation_name=?, co_reserved_person_id=?, number_of_participants=?, attendance_type_id=?, reserve_supplement=? WHERE reserve_id=?;";

			stmt=_con.prepareStatement(sql);
			stmt.setTimestamp(1, reservation.getUsageStartTime().getTimeStamp(usageDate));
			stmt.setTimestamp(2, reservation.getUsageEndTime().getTimeStamp(usageDate));
			stmt.setString(3, reservation.getReservationName());
			stmt.setString(4, reservation.getCoReservedPerson().getUserId());
			stmt.setInt(5, reservation.getNumberOfParticipants());
			stmt.setInt(6, reservation.getAttendanceTypeDto().getAttendanceTypeId());
			stmt.setString(7, reservation.getSupplement());
			stmt.setInt(8, reservation.getReservationId());
            result = stmt.executeUpdate();

            _con.commit();

		} catch (SQLException e) {
			_con.rollback();
			throw e;

		} finally {
			try {
	            dbHelper.closeResource(stmt);
	        } catch (Exception e) {
	            e.printStackTrace();
	            _log.error("Exception");
	        }
	        dbHelper.closeDb();
		}

		return result;
	}

	public List<ReservationDto> queryByResourceId(String resourceId) throws SQLException{
		List<ReservationDto> reservationList=new ArrayList<ReservationDto>();
		DBHelper dbHelper=new DBHelper();
		_con=dbHelper.connectDb();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		if(_con==null){
			_log.error("DatabaseConnectError");
			throw new SQLException();
		}

		try{
			String sql ="select reserve_id,resources.resource_id, usage_start_date, usage_end_date,reserved_person_id,reservations.deleted,usage_stop_start_date, usage_stop_end_date,users.user_id,users.family_name,users.first_name,users.tel, users.mail_address from reservations, resources,users where resources.resource_id = reservations.resource_id and users.user_id = reservations.reserved_person_id and reservations.deleted=0 and  usage_start_date>CURRENT_TIMESTAMP and reservations.resource_id = ?;";
			stmt=_con.prepareStatement(sql);
			stmt.setString(1, resourceId);
			rs=stmt.executeQuery();

			//結果の取得
			while(rs.next()){
				Resource resource = new Resource(rs.getString("resource_id"),null,null,null, 0, null,0, null, null,null);
				User user=new User(rs.getString("user_id"), null, 0, rs.getString("family_name"), rs.getString("first_name"), rs.getString("tel"),rs.getString("mail_address"));
				String resultUsageDate=new SimpleDateFormat("yyyy/MM/dd").format(rs.getTimestamp("usage_start_date"));
				TimeDto resultUsageStartTime=new TimeDto(rs.getTimestamp("usage_start_date"));
				TimeDto resultUsageEndTime=new TimeDto(rs.getTimestamp("usage_end_date"));
				//利用終了が00:00だったら24:00に直す
				if(resultUsageEndTime.getTimeMinutesValue()==0){
					resultUsageEndTime=new TimeDto(24, 0);
				}

				ReservationDto reservation = new ReservationDto(rs.getInt("reserve_id"), resource,resultUsageDate, resultUsageStartTime, resultUsageEndTime,null, user, null, 0, null, null, rs.getInt("deleted"));
				reservationList.add(reservation);

			}


		}catch (SQLException e) {
			_con.rollback();
			throw e;

		} finally {
			try {
	            dbHelper.closeResource(stmt);
	        } catch (Exception e) {
	            e.printStackTrace();
	            _log.error("Exception");
	        }
	        dbHelper.closeDb();
		}

		return reservationList;
	}






}


