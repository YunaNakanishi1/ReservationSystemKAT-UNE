/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package service;

import java.sql.SQLException;
import java.util.List;

import dto.AttendanceTypeDto;
import dto.User;

/**
 * (26).
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
public class GetUserAndAttendanceTypeListService implements Service{

	private List<User> _userList;
	private List<AttendanceTypeDto>_attendanceTypeList;


	@Override
	public boolean validate() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
	@Override
	public void execute() throws SQLException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public List<User> getUserList(){
		return _userList;

	}

	public List<AttendanceTypeDto> getAttendanceTypeList(){
		return _attendanceTypeList;

	}

}
