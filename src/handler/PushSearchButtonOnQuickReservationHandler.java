/**
 *
 */
package handler;

import static handler.MessageHolder.*;
import static handler.ViewHolder.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dto.TimeDto;
import exception.MyException;
import service.PushSearchButtonOnQuickReservationService;

/**
 *
 * サーブレット番号：13
 * @author p000527259
 *
 */
public class PushSearchButtonOnQuickReservationHandler implements Handler {

	/* (非 Javadoc)
	 * @see handler.Handler#handleService(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String handleService(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		String usageEndHourForResourceSelect = request.getParameter("usageEndHourForResourceSelect");
		String usageEndMinuteForResourceSelect = request.getParameter("usageEndMinuteForResourceSelect");
		String capacityForResourceSelect = request.getParameter("capacityForResourceSelect");
		String categoryIdForResourceSelect = request.getParameter("categoryIdForResourceSelect");
		String officeIdForResourceSelect = request.getParameter("officeIdForResourceSelect");

		TimeDto usageStartTimeForResourceSelect  = (TimeDto)session.getAttribute("usageStartTimeForResourceSelect");

		//再表示用にセット
		 session.setAttribute("categoryIdForResourceSelect", categoryIdForResourceSelect);
		 session.setAttribute("officeIdForResourceSelect", officeIdForResourceSelect);
		 session.setAttribute("displayCapacityForResourceSelect", capacityForResourceSelect);

		//定員の入力チェック
		CommonValidator commonValidator = new CommonValidator();
		int capacityInt = 0;
		try {
			capacityInt = commonValidator.getCapacityValue(capacityForResourceSelect);

		} catch(MyException e) {
			//入力が正しくない場合、再度今すぐ予約画面を表示
			request.setAttribute("messageForQuickReservation", EM31);
			return SHOW_QUICK_RESERVATION_SERVLET;
		}

		int usageEndHourInt = Integer.parseInt(usageEndHourForResourceSelect);
		int usageEndMinuteInt = Integer.parseInt(usageEndMinuteForResourceSelect);

		TimeDto usageEndTimeForResourceSelect = new TimeDto(usageEndHourInt, usageEndMinuteInt);

		 PushSearchButtonOnQuickReservationService pushSearchButtonOnQuickReservationService = new PushSearchButtonOnQuickReservationService(usageStartTimeForResourceSelect, usageEndTimeForResourceSelect, capacityInt);

		 //バリデーションチェックに成功した場合
		 if (pushSearchButtonOnQuickReservationService.validate()) {
			 //開始・終了時間
			 int usageStartMinutes = usageStartTimeForResourceSelect.getTimeMinutesValue();
			 int usageEndMinutes = usageEndTimeForResourceSelect.getTimeMinutesValue();

			 //実利用時間
			 TimeDto usageTimeForResourceSelect = new TimeDto(usageEndMinutes - usageStartMinutes);
			 //入力内容をsessionにセット
			 session.setAttribute("usageEndTimeForResourceSelect", usageEndTimeForResourceSelect);
			 session.setAttribute("usageTimeForResourceSelect", usageTimeForResourceSelect);
			 session.setAttribute("capacityForResourceSelect", capacityInt);

			 //利用日
		     SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/M/d");
		     String usageDate = sdFormat.format(new Date() );
             session.setAttribute("usageDateForResourceSelect", usageDate);

		 } else {
			 String message = pushSearchButtonOnQuickReservationService.getValidationMessage();
			 request.setAttribute("messageForQuickReservation", message);
			 return SHOW_QUICK_RESERVATION_SERVLET;
		 }

		return SEARCH_RESOURCE_LIST_SERVLET;
	}

}
