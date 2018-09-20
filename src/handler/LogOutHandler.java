package handler;

import static handler.MessageHolder.*;
import static handler.ViewHolder.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogOutHandler implements Handler {

	public String handleService(HttpServletRequest request) {

		HttpSession session = request.getSession(false);

		//セッションの破棄
		if (session != null) {
			session.invalidate();
		}

		request.setAttribute("message", PM01);

		return LOG_IN;
	}
}
