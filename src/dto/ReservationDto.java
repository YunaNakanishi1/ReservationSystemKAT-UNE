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
	private String _supplement;
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
		this._supplement = supplement;
	}
	public int getReservationId() {
		return _reservationId;
	}
	public Resource getResource() {
		return _resource;
	}
	public String getUsageDate() {
		return _usageDate;
	}
	public TimeDto getUsageStartTime() {
		return _usageStartTime;
	}
	public TimeDto getUsageEndTime() {
		return _usageEndTime;
	}
	public String getReservationName() {
		return _reservationName;
	}
	public User getReservedPerson() {
		return _reservedPerson;
	}
	public User getCoReservedPerson() {
		return _coReservedPerson;
	}
	public int getNumberOfParticipants() {
		return _numberOfParticipants;
	}
	public AttendanceTypeDto getAttendanceTypeDto() {
		return _AttendanceTypeDto;
	}
	public String getSupplement() {
		return _supplement;
	}



}
