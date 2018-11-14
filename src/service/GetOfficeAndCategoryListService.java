package service;

import java.sql.SQLException;
import java.util.List;

import dao.CategoryDao;
import dao.OfficeDao;
import dto.CategoryDto;
import dto.OfficeDto;

/**
 * 事業所とカテゴリの一覧を取得するサービス.1
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
public class GetOfficeAndCategoryListService implements Service {
	private List<OfficeDto> _officeList;
	private List<CategoryDto> _categoryList;

	@Override
	public boolean validate() {
		return true;
	}

	@Override
	public void execute() throws SQLException {
		OfficeDao officeDao = new OfficeDao();
		_officeList = officeDao.queryAll();

		CategoryDao categoryDao = new CategoryDao();
		_categoryList = categoryDao.queryAll();

	}

	public List<OfficeDto> getOfficeList() {
		return _officeList;
	}

	public List<CategoryDto> getCategoryList() {
		return _categoryList;
	}

}
