package service;

import java.util.List;

import dto.CategoryDto;
import exception.MyException;

/**
 * カテゴリIDがnullまたはリストに含まれているかを調べる. 17
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
public class ContainSelectedCategoryService implements Service {

	private List<CategoryDto> _categoryList;
	private String _categoryId;

	private boolean _result;

	public ContainSelectedCategoryService(List<CategoryDto> _categoryList, String categoryId) throws MyException {
		super();
		if (_categoryList == null) {
			throw new MyException();
		}
		this._categoryList = _categoryList;
		this._categoryId = categoryId;
	}

	@Override
	public boolean validate() {
		return true;
	}

	@Override
	public void execute(){
		if (_categoryId == null) {
			_result = true;
		} else {
			_result = false;
			for (CategoryDto category : _categoryList) {
				if (_categoryId.equals(category.getCategoryId())) {
					_result = true;
				}
			}
		}
	}

	//結果をboolean型で取得し_resultを返す
	public boolean getResult() {
		return _result;
	}

}
