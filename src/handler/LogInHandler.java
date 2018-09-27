package handler;

import static handler.MessageHolder.*;
import static handler.ViewHolder.*;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.User;
import service.LogInService;

public class LogInHandler implements Handler {
	private static Logger _log = LogManager.getLogger();
	static final int SESSION_INTERVAL = 1800;

	public String handleService(HttpServletRequest request) {

		//入力されたuserId, passwordを取得
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");

	    //userId再表示用
	    request.setAttribute("userId", userId);

	    CommonValidator commonValidator = new CommonValidator();

	    //userIdが入力されているかチェック
	    if (commonValidator.notSetOn(userId)) {
            request.setAttribute("Emessage", EM01);
            return LOG_IN;
        }

	    //passwordが入力されているかチェック
	    if (commonValidator.notSetOn(password)) {
            request.setAttribute("Emessage", EM04);
            return LOG_IN;
        }

		//入力されたユーザ情報
		User user = new User(userId, password, 0);
		LogInService loginService = new LogInService(user);


		//入力チェック
		if (loginService.validate()) {
			try {
				loginService.execute();
				User resultUser = loginService.getResultUser();

				//ユーザ認証成功
				if (resultUser != null) {
					HttpSession session = request.getSession(true);
					session.setAttribute("userId", resultUser.getUserId());
					session.setAttribute("authority", resultUser.getAuthority());
					session.setMaxInactiveInterval(SESSION_INTERVAL);
					return RESERVE_LIST;

				} else {
					//ユーザ認証失敗(EM06)
					String message = loginService.getValidationMessage();
					request.setAttribute("Emessage", message);
					return LOG_IN;
				}

			} catch(SQLException e) {
				_log.error("SQLException");
				e.printStackTrace();
				return ERROR_PAGE;
			}

		} else {
			//入力validateエラー
			request.setAttribute("Emessage", loginService.getValidationMessage());
			return LOG_IN;
		}

	}

}
