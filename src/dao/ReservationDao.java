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
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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


		try {
			//実行するSQL文
			String sql
			="select * from reservations, resources,attendance_types "
		   + "where resources.resource_id = reservations.resource_id "
		   + "and attendance_types.attendance_type_id = reservations.attendance_type_id"
		   + "and reserve_id = ?;";

			preparedStatement = _con.prepareStatement(sql);
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
			int reservationDeleted; //予約削除済み


			//resourcesテーブルのカラムをセットするために用意
			String resourceName;	//リソース名
			String officeName;	//オフィス名
			String category;	//カテゴリ
			int capacity;	//定員
			String supplement; //補足
			List<String> facility;	//設備リスト
			Timestamp usageStopStartDate;	//利用停止開始日時
			Timestamp usageStopEndDate;	//利用停止終了日時
			int resourceDeleted; //リソース削除済み

			//attendance_typesテーブルのカラムをセットするために用意
			String attendance_type; //参加者種別

			//List<String> facility を作るために用意
			facility = new ArrayList<String>();

			//「予約者」「共同予約者」のUserDtoを作るために用意
			String userId;
			String password;
			int authority;
			String familyName;
			String firstName;
			String phoneNumber;
			String mailAddress;

			String coUserId;
			String coPassword;
			int coAuthority;
			String coFamilyName;
			String coFirstName;
			String coFhoneNumber;
			String coMailAddress;



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

				attendance_type = rs.getString("attendance_type");

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
			User user = new User(userId, password, authority, familyName, firstName, phoneNumber, mailAddress);


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

	public List<ReservationDto> queryByInput(Timestamp currenTime,String usageDate,TimeDto usageStartTime,TimeDto usagEndTime,String officeId,String categoryId,String userId,boolean onlyMyReservation,boolean pastReservation,boolean deletedReservation)throws SQLException{
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
			sqlBuilder.append("WITH PARAMS AS ( SELECT ? AS p1_current_time,? AS p2_usage_date,? AS p3 _");


		}finally{

		}

		return reservationList;
	}
}
