/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package handler;

import static handler.ViewHolder.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 遷移先を決める(25).
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
public class ShowReservationChangeHandler implements Handler{
	private static Logger _log = LogManager.getLogger();

	public String handleService(HttpServletRequest request) {

		String coReservedPersonId = (String) request.getAttribute("coReservedPersonIdForReservationChange");
		String attendanceTypeId = (String) request.getAttribute("attendanceTypeIdForReservationChange");

		HandlerHelper handlerHelper = new HandlerHelper();

		if(handlerHelper.getUserAndAttendanceType(coReservedPersonId, attendanceTypeId)){
			request.setAttribute("userListForReservationChange", handlerHelper.getUserList());
			request.setAttribute("attendanceTypeListForReservationChange", handlerHelper.getAttendanceTypeList());
		}else{
			_log.error("getUserAndAttendanceType() == false");
            return ViewHolder.ERROR_PAGE;
		}
		return RESERVE_CHANGE;
	}

}
