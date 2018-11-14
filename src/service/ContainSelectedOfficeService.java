package service;

import java.util.List;

import dto.OfficeDto;
import exception.MyException;

/**
 * 事業所IDがnullまたはリストに含まれているか調べるサービス. 18
 *
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
public class ContainSelectedOfficeService implements Service {
	private List<OfficeDto> _officeList;
	private String _officeId;

	private boolean _result;

	public ContainSelectedOfficeService(List<OfficeDto> _officeList, String officeId) throws MyException {
		super();
		if (_officeList == null) {
			throw new MyException();
		}
		this._officeList = _officeList;
		this._officeId = officeId;
	}

	@Override
	public boolean validate() {
		return true;
	}

	@Override
	public void execute(){

		if (_officeId == null) {
			_result = true;
		} else {
			_result = false;
			for (OfficeDto office : _officeList) {

				if (_officeId.equals(office.getOfficeId())) {
					_result = true;
				}
			}
		}
	}

	public boolean getResult() {
		return _result;
	}

}
