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
	private static final int NUMBER_LENGTH=9;//リソースIDは「r+9桁の数字」で構成

	private int _result;
	/*executeUpdate()の結果、返ってくる数値を格納するフィールド
	  getメソッドで取得可能。Handler側で_resultが1でない時(アップデート失敗時）
	  はエラーページ飛ばす仕様   */

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
		・既リソースIDの数字部分の最大値に1を加えたものをリソースIDとして振り当てる
		⇒既登録リソースIDの最大値が「999999999」(9桁)の場合、新たに登録する
		リソースIDは「最大値＋１」になるので、「10000000000」(10桁)になってしまう
		・リソースID数字部分は9桁であるため、9桁に満たない数字は0で埋める。
		⇒例：000000002

		以上に注意して以下の処理を行う。
		1．既リソースIDの最大値を取得

		2．【リソースIDが一件以上データベースに登録されていた(maxId!=null)場合】
		   ⇒「r」を除いた数字部分のみを数値として、仮新規最大登録リソースIDとする
		   【リソースIDがデータベースに登録されていない(maxId=null)場合】
		   ⇒1を仮新規最大登録リソースIDとする

		3．仮新規最大登録リソースIDが9桁以内で納まっていることを確認する
		   ⇒10桁以上の場合は_resourceIdにnullを格納しメソッドを強制終了。
		   ※Handler側でリソースIDがnullの場合はエラーページに飛ばす仕様

		4． 4.1 何桁分0埋めするか
			4.2 0埋め部分の作成
			4.3「r」と0埋め部分を結合する

		5. 新規最大登録リソースIDの完成
		*/




		// 1．既リソースIDの最大値を取得
		ResourceDao resourceDao = new ResourceDao();
		String maxId = resourceDao.getMaxId();


		/* 2．【リソースIDが一件以上データベースに登録されていた(maxId!=null)場合】
		   ⇒「r」を除いた数字部分のみに＋１した数値を、仮新規最大登録リソースIDとする   */
		int maxIdInt=0; //仮新規最大登録リソースID
		if(maxId!=null){
			String maxIdNumber = maxId.replace("r", "");
			maxIdInt = Integer.parseInt(maxIdNumber);
		}
		maxIdInt++; //【リソースIDがデータベースに登録されていない(maxId=null)場合】
					// ⇒1を仮新規最大登録リソースIDとする



		/* 3．仮新規最大登録リソースIDが9桁以内で納まっていることを確認する
		  9桁より大きい場合は_resourceIdにnullを格納し、メソッドを強制終了。  */

		//仮新規最大登録リソースIDの桁数を得るために文字列化
		String idNumber=Integer.toString(maxIdInt);
		if (NUMBER_LENGTH < idNumber.length()) {
			_resourceId = null; //※Handler側でリソースIDがnullの場合はエラーページに飛ばす仕様
			return;
		}




		/* 4．9桁に満たない数字は0で埋める。
			4.1 何桁分0埋めするか(9桁 - 仮新規最大登録リソースIDの桁数)  */
		int remainingLength = NUMBER_LENGTH - idNumber.length();

		 // 4.2 0埋め部分の作成
		String formerOfResourceId = ""; // 「r」+ 0埋め部分。初期値は空とする
		// 0をremainingLength回文字列連結することで0埋めを実現
		for (int i = 0; i < remainingLength; i++) {
			formerOfResourceId = "0" + formerOfResourceId;
		}

		 // 4.3「r」と0埋め部分を結合する
		formerOfResourceId = "r" + formerOfResourceId;


		// 5. 新規最大登録リソースIDの完成
		_resourceId = formerOfResourceId + idNumber; //（「r」+ 0埋め部分）＋ 仮新規最大登録リソースID


		/* 6. リソースのDTOを作成し、regist(Resource)メソッド・
		displayDetails(Resource)メソッドに渡す  */
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
