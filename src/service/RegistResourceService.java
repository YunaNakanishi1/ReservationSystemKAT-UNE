package service;

import java.sql.SQLException;

import dao.ResourceDao;
import dto.Resource;

public class RegistResourceService implements Service {

	private String _validationMessage;
	private Resource _inputResource;
	private int _result;
	private String _resourceId;
	private Resource _resultResource;
	private static final int NUMBER_LENGTH=9;

	public RegistResourceService(Resource resource) {
		super();
		_inputResource = resource;
	}

	@Override
	public boolean validate() {
		ServiceValidator serviceValidator = new ServiceValidator();
		boolean validate = serviceValidator.setResourseDetailValidate(_inputResource);
		if (!validate) {
			_validationMessage = serviceValidator.getValidationMessage();
		}
		return validate;
	}

	@Override
	public void execute() throws SQLException {
		// 全リソースIDを取得
		ResourceDao resourceDao = new ResourceDao();
		String maxId = resourceDao.getMaxId();

		int maxIdInt=0;

		//idがnullではないとき
		//rを取り除く、int型に変換する
		if(maxId!=null){
		String maxIdNumber = maxId.replace("r", "");
		maxIdInt = Integer.parseInt(maxIdNumber);
		}
		maxIdInt++;

		String idNumber=Integer.toString(maxIdInt);

		// 最大桁に対して桁数が残っているか調べる
		int remainingLength = NUMBER_LENGTH - idNumber.length();
		if (remainingLength <= 0) {
			_resourceId = null;
			return;
		}
		// IDの補完する部分を求める
		String formerOfResourceId = "";
		for (int i = 0; i < remainingLength; i++) {
			formerOfResourceId = "0" + formerOfResourceId;
		}
		formerOfResourceId = "r" + formerOfResourceId;
		// 前半部分と数字部分を結合する
		_resourceId = formerOfResourceId + idNumber;

		_inputResource = new Resource(_resourceId, _inputResource.getResourceName(), _inputResource.getOfficeName(),
				_inputResource.getCategory(), _inputResource.getCapacity(), _inputResource.getSupplement(), 0, _inputResource.getFacility(),
				_inputResource.getUsageStopStartDate(), _inputResource.getUsageStopEndDate());

		_result=resourceDao.regist(_inputResource);

		_resultResource=resourceDao.displayDetails(_resourceId);

	}

	public String getValidationMessage() {
		return _validationMessage;
	}

	public int getResult() {
		return _result;
	}

	public String getResourceId() {
		return _resourceId;
	}

	public Resource getResultResource() {
		return _resultResource;
	}



}
