package service;

import java.sql.SQLException;

import dto.TimeDto;
import exception.MyException;

/**
 *
 * リソース選択のバリデーションチェックのためのサービス.
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
public class CheckResourceSelectInputService implements Service {
	private String _date;
	private TimeDto _usageStartTime;
	private TimeDto _usageEndTime;
	private TimeDto _usageTime;
	private String _capacity;
	private String _resourceName;
	private String _validationMessage;


	/**
	 * フィールドの初期化.
	 * @param date 日付
	 * @param usageStartTime 利用開始時間
	 * @param usageEndTime 利用終了時間
	 * @param usageTime 実利用時間
	 * @param capacity 定員
	 * @param resourceName リソース名
	 */
	public CheckResourceSelectInputService(String date, TimeDto usageStartTime, TimeDto usageEndTime, TimeDto usageTime, String capacity, String resourceName) {
		this._date = date;
		this._usageStartTime = usageStartTime;
		this._usageEndTime = usageEndTime;
		this._usageTime = usageTime;
		this._capacity = capacity;
		this._resourceName = resourceName;
	}

	@Override
	public boolean validate() throws MyException {
		ServiceValidator serviceValidator = new ServiceValidator();
		boolean serviceValidate =  serviceValidator.checkQuickReservationValidate(_date, _usageStartTime, _usageEndTime, _usageTime, _capacity, _resourceName);
		if (!serviceValidate) {
			_validationMessage = serviceValidator.getValidationMessage();
			return false;
		}
		return true;
	}

	@Override
	public void execute() throws SQLException {

	}


	public String getValidationMessage() {
		return _validationMessage;
	}

}
