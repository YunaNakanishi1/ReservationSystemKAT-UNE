package handler;

import static handler.MessageHolder.*;
import static handler.ViewHolder.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * ログアウト処理を行うクラス.
 * @author p000527259
 *
 */
public class LogOutHandler implements Handler {

	/**
	 * セッションがある場合セッションを破棄し、ログイン画面のアドレスを返却するメソッド.
	 */
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
