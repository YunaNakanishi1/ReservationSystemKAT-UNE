package dto;

/**
 * カテゴリ情報を格納するDTO.
 *
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
public class CategoryDto {
	private String _categoryId;
	private String _categoryName;
	public CategoryDto(String _categoryId, String _categoryName) {
		this._categoryId = _categoryId;
		this._categoryName = _categoryName;
	}
	public String getCategoryId() {
		return _categoryId;
	}
	public String getCategoryName() {
		return _categoryName;
	}



}
