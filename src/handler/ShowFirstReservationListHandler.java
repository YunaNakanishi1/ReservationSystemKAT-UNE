package handler;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ShowFirstReservationListHandler implements Handler{

	@Override
	public String handleService(HttpServletRequest request) {
		HttpSession session =request.getSession(true);

		String usageDateForReservationList = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance());

		return null;
	}

}
