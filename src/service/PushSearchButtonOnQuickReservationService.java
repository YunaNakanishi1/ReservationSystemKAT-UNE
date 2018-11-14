/**
 *
 */
package service;

import static handler.MessageHolder.*;

import java.sql.SQLException;

import dto.TimeDto;

/**
 * @author p000527259
 *
 */
public class PushSearchButtonOnQuickReservationService implements Service {
	private TimeDto _usageStartTime;
	private TimeDto _usageEndTime;
	private int _capacity;
	private String _validationMessage;


	public PushSearchButtonOnQuickReservationService(TimeDto usageStartTime, TimeDto usageEndTime, int capacity) {
		this._usageStartTime = usageStartTime;
		this._usageEndTime = usageEndTime;
		this._capacity = capacity;
	}

	/* (非 Javadoc)
	 * @see service.Service#validate()
	 */
	@Override
	public boolean validate() {
		//利用終了時間に24時以降が設定されていた場合、エラーメッセージが表示されている
		if ((_usageEndTime.getHour() == 24) && (_usageEndTime.getMinutes() != 0) ) {
			_validationMessage = EM51;
			return false;
		}

		ServiceValidator serviceValidator = new ServiceValidator();
		boolean check = serviceValidator.checkQuickReservationValidate(_capacity, _usageStartTime, _usageEndTime);
		if (!check) {
			_validationMessage = serviceValidator.getValidationMessage();
			return false;
		}

		return true;
	}

	/* (非 Javadoc)
	 * @see service.Service#execute()
	 */
	@Override
	public void execute() throws SQLException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public TimeDto getUsageStartTime() {
		return _usageStartTime;
	}
	public TimeDto getUsageEndTime() {
		return _usageEndTime;
	}
	public int getCapacity() {
		return _capacity;
	}

	public String getValidationMessage() {
		return _validationMessage;
	}

}
