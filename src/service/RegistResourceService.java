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

	private String _validationMessage;//"ユーザIDは必須入力です"等、画面上に出すメッセージ
	private Resource _inputResource; //ResourceのDTOを格納するフィールド
	private String _resourceId; //リソースIDフィールド
	private int _result;/*executeUpdate()の結果、返ってくる数値を格納するフィールド */
	private static final int NUMBER_LENGTH=9;//リソースIDは「r+9桁の数字」で構成

	private Resource _resultResource;
	//resourceDaoのdisplayDetails(String)メソッド実行で返ってくるResourceのDTOを格納するフィールド



	//コンストラクタ
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

		if (!validate) {	//画面に表示するメッセージをフィールドに格納。
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
		 ●入力リソースを、システム内で自動的にIDを振り当てた状態で登録する

		【リソースID振り当てルール】
		・IDは「r」始まりで、加えて9桁の数字を組み合わせた10桁の文字列
		・既にデータベースに登録されているIDの最大値の次の値を割り当てる
		  ※1件もデータベースに登録されていない場合はr000000001を割り当てる
		・9桁に満たない数字の場合は先頭から0埋めする

		具体的処理はコードを参照
		*/




		/* 1．既リソースIDの最大値を取得	*/
		ResourceDao resourceDao = new ResourceDao();
		String maxId = resourceDao.getMaxId();


		/* 2．新規登録するリソースIDの数字部分を決める(0埋め処理は未処理)	*/
		int maxIdInt=0;
		if(maxId!=null){
			String maxIdNumber = maxId.replace("r", "");
			maxIdInt = Integer.parseInt(maxIdNumber);
		}
		maxIdInt++;



		/* 3．2．の数字部分が9桁以内に収まっているかを確認	*/
		String idNumber=Integer.toString(maxIdInt);
		if (NUMBER_LENGTH < idNumber.length()) {
			_resourceId = null;
			return;
		}



		/* 4．「r」と0埋め部分を作成	*/
			//何桁分0埋めするか
		int remainingLength = NUMBER_LENGTH - idNumber.length();

		String formerOfResourceId = ""; // 「r」+ 0埋め部分。

		for (int i = 0; i < remainingLength; i++) {		// 0埋め部分の作成
			formerOfResourceId = "0" + formerOfResourceId;
		}
		formerOfResourceId = "r" + formerOfResourceId;



		/* 5. 新規最大登録リソースIDの完成	*/
		_resourceId = formerOfResourceId + idNumber; //（「r」+ 0埋め部分）＋ 仮新規最大登録リソースID



		/* 6. リソースのDTOを作成  */
		_inputResource = new Resource(_resourceId, _inputResource.getResourceName(), _inputResource.getOfficeName(),
				_inputResource.getCategory(), _inputResource.getCapacity(), _inputResource.getSupplement(), 0, _inputResource.getFacility(),
				_inputResource.getUsageStopStartDate(), _inputResource.getUsageStopEndDate());

		_result=resourceDao.regist(_inputResource); //リソース登録をする

		/*リソース詳細に表示するためのリソースDTOを格納
		  ※ 不慮の書き換えによるデータ変更に備えるため新しく作りなおす	*/
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
