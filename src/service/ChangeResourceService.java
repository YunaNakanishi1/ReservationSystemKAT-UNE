/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package service;

import java.sql.SQLException;

import dao.ResourceDao;
import dto.Resource;

/**
  * リソースの変更を行う.
  *@author リコーITソリューションズ株式会社 KAT-UNE
  */
public class ChangeResourceService implements Service{
	private String _validationMessage;//"ユーザIDは必須入力です"等、画面上に出すメッセージ
	private Resource _inputResource;//ResourceのDTOを格納するフィールド
	private Resource _resultResource;
	//resourceDaoのdisplayDetails(String)メソッド実行で返ってくるResourceのDTOを格納するフィールド

	private int _result;
	/*executeUpdate()の結果、返ってくる数値を格納するフィールド   */


	//コンストラクタ
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

		//入力した内容がチェックに引っかかった場合にはfalseが返ってくる
		boolean validate = serviceValidator.setResourseDetailValidate(_inputResource);

		if(!validate){	//画面に表示するメッセージをフィールドに格納。
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
