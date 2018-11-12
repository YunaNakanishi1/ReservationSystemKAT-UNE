/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package handler;

import static handler.ViewHolder.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import service.GetReservationFromIdService;

/**
 * (26).
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
public class ShowReservationDetailsHandler implements Handler{

	private static Logger _log = LogManager.getLogger();


	@Override
	public String handleService(HttpServletRequest request) {

		//servletでセッションは作られているのでfalseでよい
		HttpSession session = request.getSession(false);

		String reserveId = (String) session.getAttribute("reserveId");
		String userId = (String) session.getAttribute("userId");

		CommonValidator commonValidator = new CommonValidator();

		if(commonValidator.notSetOn(userId)){	//userIdがない場合
			return ERROR_PAGE;
		}

		GetReservationFromIdService getReservationFromIdService
		= new GetReservationFromIdService();

		return null;
	}

}
