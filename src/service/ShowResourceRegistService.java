package service;

import java.sql.SQLException;
import java.util.List;

import dao.CategoryDao;
import dao.FacilityDao;
import dao.OfficeDao;

public class ShowResourceRegistService implements Service{

	private List<String> _categoryList;
	private List<String> _officeList;
	private List<String> _facilityList;

	@Override
	public boolean validate() {
		return true;
	}

	@Override
	public void execute() throws SQLException {
		CategoryDao categoryDao=new CategoryDao();
		_categoryList=categoryDao.category();

		OfficeDao officeDao=new OfficeDao();
		_officeList=officeDao.officeName();

		FacilityDao facilityDao = new FacilityDao();
		_facilityList=facilityDao.facility();
	}

	public List<String> getCategoryList() {
		return _categoryList;
	}

	public List<String> getOfficeList() {
		return _officeList;
	}

	public List<String> getFacilityList() {
		return _facilityList;
	}



}
