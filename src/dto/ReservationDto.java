package dto;

/**
 * 予約情報を格納するDTO.
 *
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
public class ReservationDto {
	private int _reservationId;
	private Resource _resource;
	private String _usageDate;
	private TimeDto _usageStartTime;
	private TimeDto _usageEndTime;
	private String _reservationName;
	private User _reservedPerson;
	private User _coReservedPerson;
	private int _numberOfParticipants;
	private AttendanceTypeDto _AttendanceTypeDto;
	private String supplement;
	public ReservationDto(int _reservationId, Resource _resource, String _usageDate, TimeDto _usageStartTime,
			TimeDto _usageEndTime, String _reservationName, User _reservedPerson, User _coReservedPerson,
			int _numberOfParticipants, AttendanceTypeDto _AttendanceTypeDto, String supplement) {
		super();
		this._reservationId = _reservationId;
		this._resource = _resource;
		this._usageDate = _usageDate;
		this._usageStartTime = _usageStartTime;
		this._usageEndTime = _usageEndTime;
		this._reservationName = _reservationName;
		this._reservedPerson = _reservedPerson;
		this._coReservedPerson = _coReservedPerson;
		this._numberOfParticipants = _numberOfParticipants;
		this._AttendanceTypeDto = _AttendanceTypeDto;
		this.supplement = supplement;
	}
	public int get_reservationId() {
		return _reservationId;
	}
	public Resource get_resource() {
		return _resource;
	}
	public String get_usageDate() {
		return _usageDate;
	}
	public TimeDto get_usageStartTime() {
		return _usageStartTime;
	}
	public TimeDto get_usageEndTime() {
		return _usageEndTime;
	}
	public String get_reservationName() {
		return _reservationName;
	}
	public User get_reservedPerson() {
		return _reservedPerson;
	}
	public User get_coReservedPerson() {
		return _coReservedPerson;
	}
	public int get_numberOfParticipants() {
		return _numberOfParticipants;
	}
	public AttendanceTypeDto get_AttendanceTypeDto() {
		return _AttendanceTypeDto;
	}
	public String getSupplement() {
		return supplement;
	}



}
