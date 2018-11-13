/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package service;

import static handler.MessageHolder.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dto.Resource;
import dto.TimeDto;
import exception.MyException;

/**
 * 入力画面で入力されたデータの妥当性をチェックするクラス.
 *
 * @author リコーITソリューションズ株式会社 z00s600124
 *
 */
public class ServiceValidator {


    private static int MAX_RESOURCE_NAME_LENGTH=30;
    private static int MIN_CAPACITY=0;
    private static int MAX_CAPACITY=999;
    private static int MAX_SUPPLEMENT_LENGTH=500;
    private static int SIXTY_MINUTES = 60;
    private static int FIFTEEN_MINUTES = 15;
    private static int TWENTY_FOUR_HOUR = 24;
    private String _validationMessage;


    public String getValidationMessage() {
        return _validationMessage;
    }


    /**
     * 登録・変更時の機能に依存するValidateチェック
     * チェックに引っかかった場合は_validationMessageフィールドにエラーメッセージをセットする.
     *
     * @param resource  ユーザーの入力したリソース情報
     * @return 引数に渡されたデータが全て正しいとき、trueを返す
     */
    public boolean setResourseDetailValidate(Resource resource){



        String resourceName=resource.getResourceName();

        //リソース名が30文字を越えていないかチェック
        if(resourceName.length()>MAX_RESOURCE_NAME_LENGTH){
            _validationMessage=EM28;
            return false;

        }

        int capacity=resource.getCapacity();

        //定員が負の数あるいは999を越えていないかチェック
        if(capacity<MIN_CAPACITY||MAX_CAPACITY<capacity){
            _validationMessage=EM32;
            return false;

        }


        Timestamp stopStartDate =resource.getUsageStopStartDate();
        Timestamp stopEndDate =resource.getUsageStopEndDate();

        //利用停止開始日時が記入されて、利用停止終了日時が記入されていない場合をはじく
        if(stopStartDate!=null&&stopEndDate==null){
            _validationMessage=EM35;
            return false;
        }

        //利用停止終了日時が記入されて、利用停止開始日時が記入されていない場合、falseを返す
        if(stopStartDate==null&&stopEndDate!=null){
            _validationMessage=EM34;
            return false;
        }

        //利用停止開始日時が利用停止終了日時より後の場合、falseを返す
        if(stopStartDate!=null&&stopEndDate!=null){
            if(stopStartDate.compareTo(stopEndDate)>0){
                _validationMessage=EM36;
                return false;
            }
        }

        String supplement = resource.getSupplement();

        //補足が500文字を超えている場合、falseを返す
        if(supplement!=null){
            if(supplement.length()>MAX_SUPPLEMENT_LENGTH){
                _validationMessage=EM22;
                return false;
            }
        }

        //ひとつもチェックに引っかからなかった場合、trueを返す
        return true;



    }


 /**
  * リソース選択のバリデーションチェック
  * @param date 日付
  * @param usageStartTime 利用開始時間
  * @param usageEndTime 利用終了時間
  * @param usageTime 利用時間
  * @param capacity 定員
  * @param resourceName リソース名
  * @return
  */
    public boolean checkQuickReservationValidate(String date, TimeDto usageStartTime, TimeDto usageEndTime, TimeDto usageTime, String capacity, String resourceName) {
    	SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/M/d");
        Date useDate;
		try {
			useDate = sdFormat.parse(date);
		} catch (ParseException e) {
			throw new MyException();
		}
    	Date today = new Date();

    	//利用日が本日以降の場合エラー
    	if (useDate.compareTo(today) > 0) {
    		_validationMessage = EM09;
    		return true;
    	}

    	SimpleDateFormat sdfHour = new SimpleDateFormat("H");
    	SimpleDateFormat sdfMinutes = new SimpleDateFormat("m");
    	String hourStr = sdfHour.format(today);
    	String minutesStr = sdfMinutes.format(today);

    	int hourInt = 0;
    	int minutesInt = 0;

    	try {
    		hourInt = Integer.parseInt(hourStr);
    		minutesInt = Integer.parseInt(minutesStr);
    	} catch(NumberFormatException e) {
    		throw new MyException();
    	}

    	//現在時刻よりも利用終了時間が前だとエラー
    	int nowTimeMinutesValue = hourInt * SIXTY_MINUTES + minutesInt;
    	int endTime = usageEndTime.getTimeMinutesValue();
    	if (nowTimeMinutesValue >= endTime) {
    		_validationMessage = EM40;
    		return true;
    	}

    	//利用開始時間より利用終了時間が先の場合エラー
    	int startTime = usageStartTime.getTimeMinutesValue();
    	if (endTime <= startTime) {
    		_validationMessage = EM10;
    		return true;
    	}

    	//利用時間が15分以下の場合エラー
    	if (endTime - startTime < FIFTEEN_MINUTES) {
    		_validationMessage = EM49;
    		return true;
    	}

    	//利用開始時間または終了時間が24時15分以上が選択されている場合エラー
    	if ((startTime > TWENTY_FOUR_HOUR * SIXTY_MINUTES) || (endTime > TWENTY_FOUR_HOUR * SIXTY_MINUTES)) {
    		_validationMessage = EM51;
    		return true;
    	}

    	//capacityが半角の整数でなかったらエラー
    	if (!capacity.matches("^[0-9]+")) {
    		_validationMessage = EM31;
    		return true;
    	}

    	int capacityInt = 0;
    	try {
    		capacityInt = Integer.parseInt(capacity);
    	} catch(NumberFormatException e) {
    		throw new MyException();
    	}

    	//定員が0-999人でない場合エラー
    	if ((capacityInt < 0) || (capacityInt > 999)) {
    		_validationMessage = EM32;
    		return true;
    	}

    	//リソース名が30文字より長い場合エラー
    	if (resourceName.length() > 30) {
    		_validationMessage = EM28;
    		return true;
    	}

    	return false;
    }

}
