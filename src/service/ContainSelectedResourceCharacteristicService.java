package service;

import java.util.List;

import dto.FacilityDto;
import exception.MyException;

public class ContainSelectedResourceCharacteristicService implements Service{

	private List<String> _facilityIdList;
	List<FacilityDto> _facilityList;

	private boolean _result;

	public ContainSelectedResourceCharacteristicService(List<String> _facilityIdList, List<FacilityDto> _facilityList) throws MyException {
		super();
		if (_facilityList == null) {
			throw new MyException();
		}
		this._facilityIdList = _facilityIdList;
		this._facilityList = _facilityList;
	}

	@Override
	public boolean validate() {
		return true;
	}

	@Override
	public void execute(){
		if (_facilityIdList == null) {
			_result = true;
		} else {
			_result = true;
			for (FacilityDto facility : _facilityList) {
				for (String facilityId : _facilityIdList) {
					if (!(facilityId.equals(facility.getFacilityId()))) {
					_result = false;
				}
					}
			}
		}


	}

	public boolean getResult() {
		return _result;
	}

}
