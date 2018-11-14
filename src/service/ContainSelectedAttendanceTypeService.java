/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package service;

import java.sql.SQLException;
import java.util.List;

import dto.AttendanceTypeDto;

/**
/**
 * (26).
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
public class ContainSelectedAttendanceTypeService implements Service{

	private String _attendanceTypeId;
	private List<AttendanceTypeDto> _attendanceTypeList;

	/**
	 *
	 */
	public ContainSelectedAttendanceTypeService(String attendanceTypeId, List<AttendanceTypeDto> attendanceTypeList) {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public boolean validate() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public void execute() throws SQLException {
		// TODO 自動生成されたメソッド・スタブ

	}


}
