/**
 *
 */
package handler;

import static handler.ViewHolder.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.TimeDto;

/**
 * @author p000527259
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
		//HandleHelper.initializeAttributeForReservationRegist(session);
		//実装未だ

		//当日の日付取得, セット
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDateTime usageDate = LocalDateTime.now();
		String usageDateForReservationList = usageDate.format(formatter);
		session.setAttribute("usageDateForReservationList", usageDateForReservationList);

		Date currentTime = new Date();
		TimeDto currentTimeDto = null;
		try {
			currentTimeDto = new TimeDto(currentTime);
		} catch(MyException e) {
			return ERROR_PAGE;
		}

		int hour = currentTimeDto.getHour();
		int minutes = currentTimeDto.getMinutes();

		int usageStartMinutes = 0;
		if (0 <= minutes && minutes < 15) {
			usageStartMinutes = 0;
		} else if (15 <= minutes && minutes < 30) {
			usageStartMinutes = 15;
		} else if (30 <= minutes && minutes < 45) {
			usageStartMinutes = 30;
		} else if (45 <= minutes && minutes < 60) {
			usageStartMinutes = 45;
		}

		TimeDto usageStartTimeForResourceSelect = new TimeDto(hour, usageStartMinutes);


		return null;
	}

}
