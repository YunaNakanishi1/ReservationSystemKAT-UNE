package service;

import java.sql.SQLException;

import dto.TimeDto;

public class CheckResourceSelectInputService implements Service {
	private String _date;
	private TimeDto _usageStartTime;
	private TimeDto _usageEndTime;
	private TimeDto _usageTime;
	private String _capacity;
	private String _resourceName;

	public CheckResourceSelectInputService(String date, TimeDto usageStartTime, TimeDto usageEndTime, TimeDto usageTime, String capacity, String resourceName) {
		this._date = date;
		this._usageStartTime = usageStartTime;
		this._usageEndTime = usageEndTime;
		this._usageTime = usageTime;
		this._capacity = capacity;
		this._resourceName = resourceName;
	}

	@Override
	public boolean validate() {
		ServiceValidator serviceValidator = new ServiceValidator();
		boolean serviceValidate =  serviceValidator.checkQuickReservationValidate(_date, _usageStartTime, _usageEndTime, _usageTime, _capacity, _resourceName);
		return serviceValidate;
	}

	@Override
	public void execute() throws SQLException {

	}

}
