/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package handler;

import static handler.MessageHolder.*;
import static handler.ViewHolder.*;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import service.CheckAuthorityService;


/**
*
* セッションの有無を調べるハンドラ.
* @author リコーITソリューションズ株式会社 KAT-UNE
*
*/
public class SessionUpdateHandler implements Handler{

    private static Logger _log = LogManager.getLogger();

	@Override
	public String handleService(HttpServletRequest request) {
		//旧
		//HttpSession session =request.getSession(false);
		HttpSession session =request.getSession(true);

		//新
		Object authorityCheck = session.getAttribute("authorityOfLoggedIn");
		//新
		if(authorityCheck!=null){
			String userId=(String)session.getAttribute("userIdOfLoggedIn");


			CheckAuthorityService checkAuthorityService =new CheckAuthorityService(userId);

			if(checkAuthorityService.validate()){
				try{
					checkAuthorityService.execute();

					int authority = checkAuthorityService.getAuthority();


					if(authority==0||authority==1){
						session.setAttribute("userIdOfLoggedIn", userId);//sessionに変更
						session.setAttribute("authorityOfLoggedIn", authority);//sessionに変更
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
