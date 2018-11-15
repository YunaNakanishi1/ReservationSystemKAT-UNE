package dto;

/**
 * 参加者種別情報を格納するDTO.
 *
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
public class AttendanceTypeDto {
	private int _attendanceTypeId;
	private String _attendanceTypeName;
	
	
	
	public AttendanceTypeDto(int attendanceTypeId, String attendanceTypeName) {
		super();
		this._attendanceTypeId = attendanceTypeId;
		this._attendanceTypeName = attendanceTypeName;
	}
	public int getAttendanceTypeId() {
		return _attendanceTypeId;
	}
	public String getAttendanceTypeName() {
		return _attendanceTypeName;
	}



}
