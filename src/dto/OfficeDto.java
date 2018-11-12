package dto;

/**
 * 事業所情報を格納するDTO.
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
public class OfficeDto {
	private String _officeId;
	private String _officeName;
	public OfficeDto(String _officeId, String _officeName) {
		super();
		this._officeId = _officeId;
		this._officeName = _officeName;
	}
	public String getOfficeId() {
		return _officeId;
	}
	public String getOfficeName() {
		return _officeName;
	}



}
