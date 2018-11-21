package handler;

import static handler.ViewHolder.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dto.ReservationDto;
import dto.TimeDto;

/**
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 *         予約検索の初期値をセットする.
 *
 */
public class ShowFirstReservationListHandler implements Handler {

	/**
	 * @see handler.Handler#handleService(javax.servlet.http.HttpServletRequest)
	 *
	 *      予約検索の初期値をセットする.
	 */
	@Override
	public String handleService(HttpServletRequest request) {
		HttpSession session = request.getSession(true);

		HandlerHelper.initializeAttributeForReservationRegist(session);

		String usageDateForReservationList = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());

		session.setAttribute("reservationListForReservationList", new ArrayList<ReservationDto>());
		session.setAttribute("usageDateForResourceSelect", usageDateForReservationList);
		session.setAttribute("usageStartTimeForReservationList", new TimeDto(0, 0));
		session.setAttribute("usageEndTimeForReservationList", new TimeDto(24, 0));
		session.setAttribute("displayOnlyMyReservation", true);
		session.setAttribute("displayPastReservation", false);
		session.setAttribute("displayDeletedReservation", false);
		return SEARCH_RESERVATION_LIST_SERVLET;
	}

}
