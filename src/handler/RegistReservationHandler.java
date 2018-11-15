package handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RegistReservationHandler implements Handler {
	private HttpSession _session;
	private HttpServletRequest _request;

	@Override
	public String handleService(HttpServletRequest request) {

		return null;
	}

	private boolean validate(){
		String usageStartTimeStr=_request.getParameter("usageStartTime");


		return true;
	}

}
