package handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ShowQuickReservationHandler implements Handler {

	@Override
	public String handleService(HttpServletRequest request) {
		HttpSession session =request.getSession(true);
		return null;
	}

}
