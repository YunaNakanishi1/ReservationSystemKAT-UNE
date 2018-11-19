/**
 *
 */
package handler;

import static handler.MessageHolder.*;
import static handler.ViewHolder.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.TimeDto;
import exception.MyException;
import service.CheckResourceSelectInputService;

/**
 *
 * サーブレット番号：14
 * リソース選択のバリデーションチェックを行う
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
public class PushSearchButtonOnResourceSelectHandler implements Handler {
	private static Logger _log = LogManager.getLogger();

	/**
	 * リソース選択のバリデーションチェックを行うメソッド
	 */
	@Override
	public String handleService(HttpServletRequest request) {
		HttpSession session = request.getSession(true);

        //入力項目の取得
        String dateStr = request.getParameter("date");
        String startHourStr = request.getParameter("startHour");
        String startMinutesStr = request.getParameter("startMinutes");
        String endHourStr = request.getParameter("endHour");
        String endMinutesStr = request.getParameter("endMinutes");
        String actualUseTimeHourStr = request.getParameter("actualUseTimeHour");
        String actualUseTimeMinutesStr = request.getParameter("actualUseTimeMinutes");

        String officeStr = request.getParameter("office");
        String categoryStr = request.getParameter("category");
        String capacityStr = request.getParameter("capacity");
        String resourceNameStr = request.getParameter("resourceName");
        String[] resourceCaracteristicsStrArray = request.getParameterValues("resourceCharacteristics");


        List<String> resourceCaracteristicsList = new ArrayList<String>();
        if (resourceCaracteristicsStrArray != null) {
	        for (int i = 0; i < resourceCaracteristicsStrArray.length; i++) {
	        	resourceCaracteristicsList.add(resourceCaracteristicsStrArray[i]);
	        }
        }

        //再表示用にセット
        session.setAttribute("usageDateForReservationRegist", dateStr);
        session.setAttribute("categoryIdForResourceSelect", categoryStr);
        session.setAttribute("officeIdForResourceSelect", officeStr);
        session.setAttribute("displayCapacityForResourceSelect",capacityStr);
        session.setAttribute("resourceNameForResourceSelect", resourceNameStr);
        session.setAttribute("facilityIdListForResourceSelect", resourceCaracteristicsList);

        //数値に変換
        int capacity = 0;
        int startHourInt, startMinutesInt, endHourInt, endMinutesInt, actualUseTimeHourInt, actualUseTimeMinutesInt;
        CommonValidator commonValidator = new CommonValidator();
        try {
	        startHourInt = Integer.parseInt(startHourStr);
	        startMinutesInt = Integer.parseInt(startMinutesStr);
	        endHourInt = Integer.parseInt(endHourStr);
	        endMinutesInt = Integer.parseInt(endMinutesStr);
	        actualUseTimeHourInt = Integer.parseInt(actualUseTimeHourStr);
	        actualUseTimeMinutesInt = Integer.parseInt(actualUseTimeMinutesStr);

        } catch(NumberFormatException e) {
        	_log.error("NumberFormatException");
        	return ERROR_PAGE;
        }

        //入力時間をTimeDtoに変換
        TimeDto usageStartTimeForResourceSelect = new TimeDto(startHourInt, startMinutesInt);
        TimeDto usageEndTimeForResourceSelect = new TimeDto(endHourInt, endMinutesInt);
        TimeDto usageTimeForResourceSelect = new TimeDto(actualUseTimeHourInt, actualUseTimeMinutesInt);
        session.setAttribute("usageStartTimeForResourceSelect", usageStartTimeForResourceSelect);
        session.setAttribute("usageEndTimeForResourceSelect", usageEndTimeForResourceSelect);
        session.setAttribute("usageTimeForResourceSelect", usageTimeForResourceSelect);


        //入力チェック
        CommonValidator validator = new CommonValidator();
        //日付入力有無チェック
        if(validator.notSetOn(dateStr)) {
        	request.setAttribute("messageForResourceSelectUpper", EM13);
        	return SHOW_RESOURCE_SELECT_SERVLET;
        }

        //日付の入力チェック
        if(validator.notLenientDateOn(dateStr)) {
        	request.setAttribute("messageForResourceSelectUpper", EM42);
        	return SHOW_RESOURCE_SELECT_SERVLET;
        } else {
        	dateStr = validator.getDateStr();
        }

        if(commonValidator.notSetOn(capacityStr)){
        try {
			capacity=commonValidator.getCapacityValue(capacityStr);
		} catch (MyException e1) {
			session.setAttribute("messageForResourceSelectUpper", EM31);
			return SHOW_RESOURCE_SELECT_SERVLET;
		}
        }
        session.setAttribute("capacityForResourceSelect",capacity);

        //入力バリデーションチェック
        CheckResourceSelectInputService checkResourceSelectInputService = new CheckResourceSelectInputService(dateStr, usageStartTimeForResourceSelect, usageEndTimeForResourceSelect, usageTimeForResourceSelect, capacityStr, resourceNameStr);
        try {
        	//入力エラーがある場合
        	if(!checkResourceSelectInputService.validate()) {
        		String message = checkResourceSelectInputService.getValidationMessage();
        		request.setAttribute("messageForResourceSelectUpper", message);
        		return SHOW_RESOURCE_SELECT_SERVLET;
        	}
        } catch (MyException e) {
        	_log.error("validateion error");
        	e.printStackTrace();
        	return ERROR_PAGE;
        }


        int startTime = usageStartTimeForResourceSelect.getTimeMinutesValue();
        int endTime = usageEndTimeForResourceSelect.getTimeMinutesValue();
        int actualUseTime = usageTimeForResourceSelect.getTimeMinutesValue();


        //利用開始時間, 利用終了時間の時間幅よりも実利用時間の方が長い場合
        if ((endTime - startTime) < actualUseTime) {
        	request.setAttribute("MessageForReservationListUpper",PM10);
        	TimeDto updateUsageTime = new TimeDto(endTime - startTime);

        	//新たに実利用時間をセットしなおす
        	session.setAttribute("usageTimeForResourceSelect", updateUsageTime);
        }

		return SEARCH_RESOURCE_LIST_SERVLET;
	}

}
