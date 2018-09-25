package handler;

import static handler.MessageHolder.*;
import static handler.ViewHolder.*;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import service.CheckAuthorityService;

public class SessionUpdateHandler implements Handler{

	@Override
	public String handleService(HttpServletRequest request) {
		HttpSession session =request.getSession(false);

		if(session!=null){
			String userId=(String)session.getAttribute("userId");

			CheckAuthorityService checkAuthorityService =new CheckAuthorityService(userId);

			if(checkAuthorityService.validate()){
				try{
					checkAuthorityService.execute();

					int authority = checkAuthorityService.getAuthority();

					if(authority==0||authority==1){
						session.setAttribute("userId", userId);
						session.setAttribute("authority", authority);
						return null;
					}else{
						return ERROR_PAGE;
					}
				}catch (SQLException e){
					//ログを残す
					return ERROR_PAGE;
				}
			}else{
				//ログを残す
				return ERROR_PAGE;
			}


		}else{
			request.setAttribute("EMessage", EM39);
			return LOG_IN;
		}

	}


}
