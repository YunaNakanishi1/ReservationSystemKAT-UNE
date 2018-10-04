/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package service;

import java.sql.SQLException;

import dao.ResourceDao;
import dto.Resource;

/**リソースの登録を行う
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
public class RegistResourceService implements Service {

	private String _validationMessage;
	//"ユーザIDは必須入力です"等、画面上に出すメッセージ

	private Resource _inputResource;
	/*SetResourceDetailsHandlerでRegistResourceServiceコンストラクタ呼び
	 出し時にもらうResourceのDTOを格納*/

	private int _result; //executeUpdate()の結果、返ってくる数値を格納
	private String _resourceId; //リソースID

	private Resource _resultResource;
	//resourceDaoのdisplayDetails(String)メソッド実行で返ってくるResourceのDTOを格納

	private static final int NUMBER_LENGTH=9;
	//リソースIDは「r+9桁の数字」で構成



	public RegistResourceService(Resource resource) {
		super();
		_inputResource = resource;
	}



	/* リソース登録時のバリデーションチェックを行う.
	 * @see service.Service#validate()
	 */
	@Override
	public boolean validate() {
		ServiceValidator serviceValidator = new ServiceValidator();

		//入力した内容がチェックに引っかかった場合にはfalseが返ってくる
		boolean validate = serviceValidator.setResourseDetailValidate(_inputResource);

		if (!validate) {
			/*チェックに引っかかった場合はメッセージを画面に表示する。
			Handler側でメッセージのsetAttributeを行うため
			ここではフィールドに格納しておく。
			 ※フィールドはgetメソッドで取得可能  */
			_validationMessage = serviceValidator.getValidationMessage();
		}
		return validate;
	}




	/* リソース情報を登録する.
	 * @see service.Service#execute()
	 */
	@Override
	public void execute() throws SQLException {
		/*
		【リソースID】「r」始まりで、加えて9桁の数字を組み合わせた10桁の文字列

		●リソース登録時には登録リソースにIDをシステム内で自動的に振り当てる。

		≪リソース登録時内部処理≫
		既リソースIDの数字部分の最大値に1を加えたものをリソースIDとして振り当てる
		⇒既登録リソースIDの最大値が「999999999」(9桁)の場合、新たに登録する
		リソースIDは「最大値＋１」になるので、「10000000000」(10桁)になってしまう

		*/


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


		if (NUMBER_LENGTH < idNumber.length()) {
			_resourceId = null;
			return;
		}
		// IDの補完する部分を求める
		// 最大桁に対して桁数が残っているか調べる
		int remainingLength = NUMBER_LENGTH - idNumber.length();
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
