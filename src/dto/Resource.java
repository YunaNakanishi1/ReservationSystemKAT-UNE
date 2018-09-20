/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package dto;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
public class Resource {

	private String _resourceId;
	private String _resourceName;
	private String _officeName;
	private String _category;
	private List<String> _categoryList; //これいるよね？
	private int _capacity;
	private String _supplement;
	private int _deleted;
	private List<String> _facility;
	private Timestamp _usageStopStartDate;
	private Timestamp _usageStopEndDate;

	public Resource(String _resourceId, String _resourceName, String _officeName, String _category, int _capacity,
			String _supplement, int _deleted, List<String> _facility, Timestamp _usageStopStartDate,
			Timestamp _usageStopEndDate) {
		this._resourceId = _resourceId;
		this._resourceName = _resourceName;
		this._officeName = _officeName;
		this._category = _category;
		this._capacity = _capacity;
		this._supplement = _supplement;
		this._deleted = _deleted;
		this._facility = _facility;
		this._usageStopStartDate = _usageStopStartDate;
		this._usageStopEndDate = _usageStopEndDate;
	}

	//カテゴリリストのあるコンストラクタを作成しまし
	public Resource(String resourceId, String resourceName, String officeName, List<String> categoryList, int capacity,
			String supplement, int deleted, List<String> facilityList, Timestamp usageStopStartDate,
			Timestamp usageStopEndDate) {
		this._resourceId = resourceId;
		this._resourceName = resourceName;
		this._officeName = officeName;
		this._categoryList = categoryList;
		this._capacity = capacity;
		this._supplement = supplement;
		this._deleted = deleted;
		this._facility = facilityList;
		this._usageStopStartDate = usageStopStartDate;
		this._usageStopEndDate = usageStopEndDate;
	}

	public String getResourceId() {
		return _resourceId;
	}

	public String getResourceName() {
		return _resourceName;
	}

	public String getOfficeName() {
		return _officeName;
	}

	public String getCategory() {
		return _category;
	}

	public int getCapacity() {
		return _capacity;
	}

	public String getSupplement() {
		return _supplement;
	}

	public int getDeleted() {
		return _deleted;
	}

	public List<String> getFacility() {
		return _facility;
	}

	public Timestamp getUsageStopStartDate() {
		return _usageStopStartDate;
	}

	public Timestamp getUsageStopEndDate() {
		return _usageStopEndDate;
	}

}
