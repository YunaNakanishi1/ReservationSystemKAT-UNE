package service;

import java.sql.SQLException;

import dao.ResourceDao;
import dto.Resource;

/**リソースの変更を行う.
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
public class ChangeResourceService implements Service{
	private String _validationMessage;
	private Resource _inputResource;
	private Resource _resultResource;
	private int _result;



	public ChangeResourceService(Resource resource) {
		super();
		_inputResource = resource;
	}

	/* 機能に依存するバリデーションチェックを行う.
	 * @see service.Service#validate()
	 */
	@Override
	public boolean validate() {
		ServiceValidator serviceValidator = new ServiceValidator();
		boolean validate = serviceValidator.setResourseDetailValidate(_inputResource);
		if(!validate){
			_validationMessage=serviceValidator.getValidationMessage();
		}
		return validate;
	}

	/* リソース情報を変更する.
	 * @see service.Service#execute()
	 */
	@Override
	public void execute() throws SQLException {
		ResourceDao resourceDao = new ResourceDao();
		_result = resourceDao.change(_inputResource);
		_resultResource=resourceDao.displayDetails(_inputResource.getResourceId());

	}

	public String getValidationMessage() {
		return _validationMessage;
	}

	public int getResult() {
		return _result;
	}

	public Resource getResultResource() {
		return _resultResource;
	}





}
