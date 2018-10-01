package service;

import java.sql.SQLException;
import java.util.List;

import dao.CategoryDao;
import dao.FacilityDao;
import dao.OfficeDao;
import dao.ResourceDao;
import dto.Resource;

/**カテゴリ、事業所、リソース特性のリストと、リソース情報を獲得する.
 *
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
public class ShowResourceChangeService implements Service{

	private String _resourceId;
	private List<String> _categoryList;
	private List<String> _officeList;
	private List<String> _facilityList;
	private Resource _resource;

	public ShowResourceChangeService(String resourceId){
		_resourceId=resourceId;
	}

	@Override
	public boolean validate() {
		return true;
	}

	/**カテゴリ、事業所、リソース特性のリストと、リソース情報を獲得し、フィールドにセットする.
	 * @see service.Service#execute()
	 */
	@Override
	public void execute() throws SQLException {
		//カテゴリのリストを取得
		CategoryDao categoryDao=new CategoryDao();
		_categoryList=categoryDao.category();

		//事業所のリストを取得
		OfficeDao officeDao=new OfficeDao();
		_officeList=officeDao.officeName();

		//特性のリストを取得
		FacilityDao facilityDao = new FacilityDao();
		_facilityList=facilityDao.facility();

		//リソース情報を取得
		ResourceDao resourceDao=new ResourceDao();
		_resource = resourceDao.displayDetails(_resourceId);
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

	public Resource getResource(){
		return _resource;
	}

}
