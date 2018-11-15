package service;

import java.util.List;

import dto.FacilityDto;
import exception.MyException;

/**
 * リソース特性IDがnullまたはリストに含まれているかを調べる. 19
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
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
		int count = 0;

		if (_facilityIdList.size()==0) {
			_result = true;
		} else {
			_result = false;

			for (String facilityId : _facilityIdList) {
				for (FacilityDto facility : _facilityList) {
					System.out.println(facilityId);
					if (facilityId.equals(facility.getFacilityId())) {
						count++;
						break;
					}
				}
			}

			if (_facilityIdList.size() == count) {
				_result = true;
			}

		}
	}

	//結果をboolean型で取得し_resultを返す
	public boolean getResult() {
		return _result;
	}

}
