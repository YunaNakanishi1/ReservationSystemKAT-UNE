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
 * 予約検索の初期値をセットする
 *
 */
public class ShowFirstReservationListHandler implements Handler{

	@Override
	public String handleService(HttpServletRequest request) {
		HttpSession session =request.getSession(true);

		String usageDateForReservationList = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance());

		session.setAttribute("reservationListForReservationList", new ArrayList<ReservationDto>());
		session.setAttribute("usageDateForReservationList", usageDateForReservationList);
		session.setAttribute("usageStartHourForReservationList", new TimeDto(0, 0));
		session.setAttribute("usageEndHourForReservationList", new TimeDto(24,0));
		session.setAttribute("displayOnlyMyReservation", true);
		return SEARCH_RESERVATION_LIST_SERVLET;
	}

}
