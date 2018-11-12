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
import exception.MyException;

public class PushNewReservationButtonHandler implements Handler {
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
		String usageDateForReservationRegist = usageDate.format(formatter);
		session.setAttribute("usageDateForReservationRegist", usageDateForReservationRegist);

		Date currentTime = new Date();
		TimeDto currentTimeDto = null;
		try {
			currentTimeDto = new TimeDto(currentTime);
			} catch(MyException e) {
				return ERROR_PAGE;
			}

	int hour = currentTimeDto.getHour();
	int minutes = currentTimeDto.getMinutes();
	return null;
	}

	// 初期値をsetAttributeする
			String resourceId = _request.getParameter("resourceId");
			_request.setAttribute("resourceId", resourceId);
}
