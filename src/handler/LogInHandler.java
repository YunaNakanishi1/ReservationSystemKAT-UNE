package handler;

import static handler.MessageHolder.*;
import static handler.ViewHolder.*;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dto.User;
import service.LogInService;

public class LogInHandler implements Handler {

	public String handleService(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");

		//クロスサイトスクリプティング対策まだだよ～
	    userId = userId.replaceAll("<","&lt;");
	    userId = userId.replaceAll(">","&gt;");
	    userId = userId.replaceAll("\"","&quot;");
	    password = password.replaceAll("<","&lt;");
	    password = password.replaceAll(">","&gt;");

	    //再表示用
	    request.setAttribute("userId", userId);

	    CommonValidator commonValidator = new CommonValidator();

	    //入力されているかチェック
	    if (commonValidator.notSetOn(userId)) {
            request.setAttribute("Emessage", EM01);
            return LOG_IN;
        }
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

				if (resultUser != null) {
					HttpSession session = request.getSession(true);
					session.setAttribute("userId", resultUser.getUserId());
					session.setAttribute("autority", resultUser.getAuthority());
					session.setMaxInactiveInterval(1800);
					return RESERVE_LIST;
				} else {
					request.setAttribute("Emessage", EM06);
					return LOG_IN;
				}
			} catch(SQLException e) {
				return ERROR_PAGE;
			}
		} else {
			request.setAttribute("Emessage", loginService.getValidationMessage());
			return LOG_IN;
		}

	}

}
