package service;

import java.sql.SQLException;

import dto.Resource;

public class ChangeResourceService implements Service{
	private String _validationMessage;
	private Resource _resource;
	private int _result;



	public ChangeResourceService(Resource resource) {
		super();
		this._resource = resource;
	}

	@Override
	public boolean validate() {
		ServiceValidator serviceValidator = new ServiceValidator();
		boolean validate = serviceValidator.setResourseDetailValidate(_resource);
		if(!validate){
			_validationMessage=serviceValidator.getValidationMessage();
		}
		return validate;
	}

	@Override
	public void execute() throws SQLException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public String getValidationMessage() {
		return _validationMessage;
	}

	public int getResult() {
		return _result;
	}



}
