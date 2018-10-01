package service;

import java.sql.SQLException;

import dao.ResourceDao;
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
		ResourceDao resourceDao = new ResourceDao();
		_result = resourceDao.change(_resource);

	}

	public String getValidationMessage() {
		return _validationMessage;
	}

	public int getResult() {
		return _result;
	}



}
