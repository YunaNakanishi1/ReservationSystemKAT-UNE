package dto;

public class FacilityDto {
	private String _facilityId ;
	private String _facilityName;
	public  FacilityDto(String _facilityId, String _facilityName) {
		super();
		this._facilityId = _facilityId;
		this._facilityName = _facilityName;
	}
	public String getFacilityId() {
		return _facilityId;
	}
	public String getFacilityName() {
		return _facilityName;
	}

}
