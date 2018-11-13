/**
 *
 */
package handler;

import static handler.ViewHolder.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.TimeDto;
import exception.MyException;

/**
 * servlet番号：9
 * 今すぐ予約画面の開始時刻、終了時刻を設定するメソッド
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
public class PushQuickReservationButtonHandler implements Handler {
	private static int THIRTY_MINUTES = 30;
	private static int SIXTY_MINUTES = 60;
	private static int ONE_HOUR = 1;
	private static int ZERO = 0;
	private static int TWENTY_FOUR = 24;
	private Logger _log = LogManager.getLogger();


	/* (非 Javadoc)
	 * @see handler.Handler#handleService(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String handleService(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
	      //セッションは存在する
		HandlerHelper.initializeAttributeForReservationRegist(session);

		//当日の日付取得, セット
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDateTime usageDate = LocalDateTime.now();
		String usageDateForReservationList = usageDate.format(formatter);
		session.setAttribute("usageDateForReservationList", usageDateForReservationList);

		HandlerHelper handlerHelper = new HandlerHelper();
		TimeDto usageStartTimeForResourceSelect = null;

		try {
			usageStartTimeForResourceSelect = handlerHelper.getUsageStartTime();
		} catch (MyException e) {
			_log.error("format error");
			return ERROR_PAGE;
		}

		session.setAttribute("usageStartTimeForResourceSelect", usageStartTimeForResourceSelect);
		int hour = usageStartTimeForResourceSelect.getHour();
		int usageStartMinutes = usageStartTimeForResourceSelect.getMinutes();

		//利用終了時間の設定
		int usageEndHour = 0;
		int usageEndMinutes = 0;

		if (usageStartMinutes == 0 || usageStartMinutes == 15) {
			usageEndMinutes = usageStartMinutes + THIRTY_MINUTES;
			usageEndHour = hour;
		} else {
			usageEndMinutes = usageStartMinutes + THIRTY_MINUTES - SIXTY_MINUTES;
			usageEndHour = hour + ONE_HOUR;
		}

		//24時を超えた場合24時00分にセット
		if (usageEndHour > 24) {
			usageEndMinutes = ZERO;
			usageEndHour = TWENTY_FOUR;
		}

		TimeDto usageEndTimeForResourceSelect = new TimeDto(usageEndHour, usageEndMinutes);
		session.setAttribute("usageEndTimeForResourceSelect", usageEndTimeForResourceSelect);

		//戻るボタンの行き先
		session.setAttribute("returnPageForResourceSelect", SHOW_FIRST_RESERVATION_LIST_SERVLET);

		return  SHOW_QUICK_RESERVATION_SERVLET;
	}

}
