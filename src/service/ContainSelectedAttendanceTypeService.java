/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package service;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.AttendanceTypeDto;
import exception.MyException;

/**
/**
 * (26).
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
public class ContainSelectedAttendanceTypeService implements Service{

	private int _attendanceTypeId;
	private List<AttendanceTypeDto> _attendanceTypeList;
	private static Logger _log = LogManager.getLogger();


	public ContainSelectedAttendanceTypeService(int attendanceTypeId, List<AttendanceTypeDto> attendanceTypeList) {
		_attendanceTypeId = attendanceTypeId;
		_attendanceTypeList = attendanceTypeList;
		if(attendanceTypeList == null){
			_log.error("_userList == null");
            throw new MyException();   //エラー処理はハンドラーに任せる
		}
	}

	@Override
	public boolean validate() {
		boolean attendanceTypeIdIsTheSame = false;
		if(_attendanceTypeId==-1){
			attendanceTypeIdIsTheSame = true;
		}else{
			for(int i=0; i<_attendanceTypeList.size(); i++){
				AttendanceTypeDto attendanceTypeDto = _attendanceTypeList.get(i);
				if(attendanceTypeDto.getAttendanceTypeId()==(_attendanceTypeId)){
					attendanceTypeIdIsTheSame = true;
				}
			}
		}
		return attendanceTypeIdIsTheSame;

	}

	@Override
	public void execute() throws SQLException {
		// TODO 自動生成されたメソッド・スタブ

	}


}
