package service;

import java.sql.SQLException;

import dto.Resource;

/**
 *サーブレット番号：37
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
public class PushRegistButtonOnResourceRegistService implements Service{

	public String validationMessage;
	private Resource resource;

	//コンストラクタ生成
	public PushRegistButtonOnResourceRegistService(Resource resource) {
		super();
		this.resource = resource;
	}

	@Override
	public boolean validate() {

		ServiceValidator serviceValidator = new ServiceValidator();
		boolean validateOk = serviceValidator.setResourseDetailValidate(resource);

		if(!validateOk){
			validationMessage = serviceValidator.getValidationMessage();
		}
		return validateOk;
	}

	@Override
	public void execute() throws SQLException {
	}



}
