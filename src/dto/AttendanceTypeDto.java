package dto;

/**
 * 参加者種別情報を格納するDTO.
 *
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
public class AttendanceTypeDto {
	private String _attendanceTypeId;
	private String _attendanceTypeName;
	public AttendanceTypeDto(String attendanceTypeId, String attendanceTypeName) {
		super();
		this._attendanceTypeId = attendanceTypeId;
		this._attendanceTypeName = attendanceTypeName;
	}
	public String getAttendanceTypeId() {
		return _attendanceTypeId;
	}
	public String getAttendanceTypeName() {
		return _attendanceTypeName;
	}



}
