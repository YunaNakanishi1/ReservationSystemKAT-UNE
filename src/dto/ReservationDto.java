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
	private int _deleted;


	public ReservationDto(int reservationId, Resource resource, String usageDate,
			TimeDto usageStartTime,TimeDto usageEndTime, String reservationName,
			User reservedPerson, User coReservedPerson,int numberOfParticipants,
			AttendanceTypeDto AttendanceTypeDto, String supplement, int deleted) {
		super();
		this._reservationId = reservationId;
		this._resource = resource;
		this._usageDate = usageDate;
		this._usageStartTime = usageStartTime;
		this._usageEndTime = usageEndTime;
		this._reservationName = reservationName;
		this._reservedPerson = reservedPerson;
		this._coReservedPerson = coReservedPerson;
		this._numberOfParticipants = numberOfParticipants;
		this._AttendanceTypeDto = AttendanceTypeDto;
		this._supplement = supplement;
		this._deleted = deleted;
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

	public int getDeleted() {
		return _deleted;
	}


}
