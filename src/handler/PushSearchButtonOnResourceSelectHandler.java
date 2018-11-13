/**
 *
 */
package handler;

import static handler.MessageHolder.*;
import static handler.ViewHolder.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dto.TimeDto;

/**
 *
 * サーブレット番号：14
 * @author p000527259
 *
 */
public class PushSearchButtonOnResourceSelectHandler implements Handler {

	/* (非 Javadoc)
	 * @see handler.Handler#handleService(javax.servlet.http.HttpServletRequest)
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
        String[] resourceCaracteristicsStrArray = request.getParameterValues("resourceCaracteristics");

        //再表示用にセット
        session.setAttribute("usageDateForReservationRegist", dateStr);
        session.setAttribute("categoryIdForResourceSelect", categoryStr);
        session.setAttribute("officeIdForResourceSelect", officeStr);
        session.setAttribute("displayCapacityForResourceSelect",capacityStr);
        session.setAttribute("resourceNameForResourceSelect", resourceNameStr);
        session.setAttribute("facilityIdListForResourceSelect", resourceCaracteristicsStrArray);

        //数値に変換
        int startHourInt, startMinutesInt, endHourInt, endMinutesInt, actualUseTimeHourInt, actualUseTimeMinutesInt;
        try {
	        startHourInt = Integer.parseInt(startHourStr);
	        startMinutesInt = Integer.parseInt(startMinutesStr);
	        endHourInt = Integer.parseInt(endHourStr);
	        endMinutesInt = Integer.parseInt(endMinutesStr);
	        actualUseTimeHourInt = Integer.parseInt(actualUseTimeHourStr);
	        actualUseTimeMinutesInt = Integer.parseInt(actualUseTimeMinutesStr);
        } catch(NumberFormatException e) {
        	return ERROR_PAGE;
        }

        TimeDto usageStartTimeForResourceSelect = new TimeDto(startHourInt, startMinutesInt);
        TimeDto usageEndTimeForResourceSelect = new TimeDto(endHourInt, endMinutesInt);
        TimeDto usageTimeForResourceSelect = new TimeDto(actualUseTimeHourInt, actualUseTimeMinutesInt);
        session.setAttribute("usageStartHourForResourceSelect", usageStartTimeForResourceSelect);
        session.setAttribute("usageEndHourForResourceSelect", usageEndTimeForResourceSelect);
        session.setAttribute("usageTimeForResourceSelect", usageTimeForResourceSelect);


        CommonValidator validator = new CommonValidator();
        //日付入力有無チェック
        if(validator.notSetOn(dateStr)) {
        	session.setAttribute("messageForResourceSelectUpper", EM13);
        	return SHOW_RESOURCE_SELECT_SERVLET;
        }

		return null;
	}

}
