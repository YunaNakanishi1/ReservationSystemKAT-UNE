package service;

import static handler.MessageHolder.*;

import java.sql.Timestamp;

import dto.Resource;

public class ServiceValidator {


	private static int MAX_RESOURCE_NAME_LENGTH=30;
	private static int MIN_CAPACITY=0;
	private static int MAX_CAPACITY=999;
	private static int MAX_SUPPLEMENT_LENGTH=500;
	private String _validateMessage;


	public String getValidateMessage() {
		return _validateMessage;
	}


	/**
	 * 登録・変更時の機能に依存するValidateチェック
	 * @param resource//ユーザーの入力したリソース情報
	 * @return
	 */
	public boolean setResourseDetailValidate(Resource resource){



		String resourceName=resource.getResourceName();

		//リソース名が30文字を越えていないかチェック
		if(resourceName.length()>MAX_RESOURCE_NAME_LENGTH){
			_validateMessage=EM28;
			return false;

		}

		int capacity=resource.getCapacity();

		//定員が負の数あるいは999を越えていないかチェック
		if(capacity<MIN_CAPACITY||MAX_CAPACITY<capacity){
			_validateMessage=EM32;
			return false;

		}


		Timestamp stopStartDate =resource.getUsageStopStartDate();
		Timestamp stopEndDate =resource.getUsageStopEndDate();

		//利用停止開始日時が記入されて、利用停止終了日時が記入されていない場合をはじく
		if(stopStartDate!=null&&stopEndDate==null){
			_validateMessage=EM34;
			return false;
		}

		//利用停止終了日時が記入されて、利用停止開始日時が記入されていない場合、falseを返す
		if(stopStartDate==null&&stopEndDate!=null){
			_validateMessage=EM35;
			return false;
		}

		//利用停止開始日時が利用停止終了日時より後の場合、falseを返す
		if(stopStartDate!=null&&stopEndDate!=null){
			if(stopStartDate.compareTo(stopEndDate)>0){
				_validateMessage=EM36;
				return false;
			}
		}

		String supplement = resource.getSupplement();

		//補足が500文字を超えている場合、falseを返す
		if(supplement!=null){
			if(supplement.length()>MAX_SUPPLEMENT_LENGTH){
				_validateMessage=EM22;
				return false;
			}
		}

		//ひとつもチェックに引っかからなかった場合、trueを返す
		return true;



	}



}
