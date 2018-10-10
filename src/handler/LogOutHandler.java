/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package handler;

import static handler.MessageHolder.*;
import static handler.ViewHolder.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * ログアウト処理を行うハンドラ.
 * @author リコーITソリューションズ株式会社 KAT-UNE
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

		request.setAttribute("Pmessage", PM01);

		return LOG_IN;
	}

}
