package service;

import java.sql.SQLException;
import java.util.List;

import dao.FacilityDao;
import dto.FacilityDto;

/**
 * リソース特性一覧を取得するサービス.22
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
public class GetResourceCharacteristicListService implements Service{
	private List<FacilityDto> _facilityList;

	@Override
	public boolean validate() {
		return true;
	}

	@Override
	public void execute() throws SQLException {
		FacilityDao facilityDao = new FacilityDao();
		_facilityList = facilityDao.queryAll();
	}

	public List<FacilityDto> getFacilityList() {
		return _facilityList;
	}


}
