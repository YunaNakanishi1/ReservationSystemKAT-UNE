/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package handler;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.Resource;
import service.GetResouceFromIdService;


/**
 * (26).
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
public class ShowReservationRegistHandler {

	private HttpServletRequest _request;
	private HttpSession _session;
	private static Logger _log = LogManager.getLogger();


	public String handleService(HttpServletRequest request){

		// セッションを取得
		_session = request.getSession(false);

		_request = request;

		setResource();



		return null;

	}


	public boolean setResource(){

		String resourceId = _request.getParameter("resourceId");
		GetResouceFromIdService getResouceFromIdService = new GetResouceFromIdService(resourceId);

		getResouceFromIdService.validate(); //trueが返る

		try {
			getResouceFromIdService.execute();
			Resource resource = getResouceFromIdService.getResource();

			if(resource == null){
				_log.error("setResource()_resource is null");
				return false;
			}else{
				_session.setAttribute("resourceIdForReservationRegist", resource.getResourceId());
				_session.setAttribute("resourceNameForReservationRegist", resource.getResourceName());
			}

		} catch (SQLException e) {
			_log.error("setResource()_SQLException");
			e.printStackTrace();
			return false;
		}

		return true;

	}


	public boolean setSlider(){
		return false;

	}
}
