package handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUpdateHandler implements Handler{

	@Override
	public String handleService(HttpServletRequest request) {
		HttpSession session =request.getSession(false);


		return null;
	}


}
