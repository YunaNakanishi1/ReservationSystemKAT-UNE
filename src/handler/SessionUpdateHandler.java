package handler;

import static handler.MessageHolder.*;
import static handler.ViewHolder.*;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import service.CheckAuthorityService;

public class SessionUpdateHandler implements Handler{

    private static Logger _log = LogManager.getLogger();

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
                        _log.error("authority is not 0 or 1");
						return ERROR_PAGE;
					}
				}catch (SQLException e){
					//ログを残す
                    _log.error("SQLException");
					return ERROR_PAGE;
				}
			}else{
				//ログを残す
                _log.error("validate Error");
				return ERROR_PAGE;
			}


		}else{
			request.setAttribute("Emessage", EM39);
			return LOG_IN;
		}

	}


}
