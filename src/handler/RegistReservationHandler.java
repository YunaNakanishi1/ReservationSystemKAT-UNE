package handler;

import static handler.MessageHolder.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.AttendanceTypeDto;
import dto.ReservationDto;
import dto.Resource;
import dto.TimeDto;
import dto.User;
import exception.MyException;
import service.CheckReservationInputService;

public class RegistReservationHandler implements Handler {
	private static Logger _log = LogManager.getLogger();
	private HttpSession _session;
	private HttpServletRequest _request;
	private ReservationDto _reservation;

	@Override
	public String handleService(HttpServletRequest request) {

		return null;
	}

	private boolean validate(){
		CommonValidator commonValidator=new CommonValidator();
		String usageStartTimeStr=_request.getParameter("usageStartTime");
		String usageEndTimeStr=_request.getParameter("usageEndTime");
		String reservationName=_request.getParameter("reservationName");
		String displayNumberOfParticipants=_request.getParameter("numberOfParticipants");
		String coReservedPersonId=_request.getParameter("coReservedPersonId");
		if(commonValidator.notSetOn(coReservedPersonId)){
			coReservedPersonId=null;
		}
		String attendanceTypeIdStr=_request.getParameter("attendanceTypeId");
		String supplement=_request.getParameter("supplement");

		_session.setAttribute("reservationNameForReservationRegist", reservationName);
		_session.setAttribute("displayNumberOfParticipantsForReservationRegist", displayNumberOfParticipants);
		_session.setAttribute("coReservedPersonIdForReservationRegist", coReservedPersonId);
		_session.setAttribute("attendanceTypeIdForReservationRegist", attendanceTypeIdStr);
		_session.setAttribute("reserveSupplementForReservationRegist", supplement);



		int usageStartTimeInt=0;

		if(commonValidator.notNumericOn(usageStartTimeStr)){
			_log.error("usageStartTime not number");
			throw new MyException();
		}else{
			usageStartTimeInt=commonValidator.getNumber();
		}
		TimeDto usageStartTime=new TimeDto(usageStartTimeInt);

		int usageEndTimeInt=0;

		if(commonValidator.notNumericOn(usageEndTimeStr)){
			_log.error("usageEndTime not number");
			throw new MyException();
		}else{
			usageEndTimeInt=commonValidator.getNumber();
		}
		TimeDto usageEndTime=new TimeDto(usageEndTimeInt);

		_session.setAttribute("usageStartHourForReservationRegist", usageStartTime);
		_session.setAttribute("usageEndHourForReservationRegist", usageEndTime);
		int numberOfParticipants=0;
		try{
			numberOfParticipants=commonValidator.getCapacityValue(displayNumberOfParticipants);
		}catch (MyException e) {
			_session.setAttribute("messageForReservationRegist", EM20);
			return false;
		}
		String usageDate = (String)_session.getAttribute("usageDateForReservationRegist");
		String resourceId = (String)_session.getAttribute("resourceIdForReservationRegist");
		String reservedPersonId = (String)_session.getAttribute("userIdOfLoggedIn");

		AttendanceTypeDto attendanceType=null;
		try{
			attendanceType=new AttendanceTypeDto(Integer.parseInt(attendanceTypeIdStr), null);
		}catch (NumberFormatException e) {
			_log.error("attendanceType not number");
			throw new MyException();
		}

		_reservation=new ReservationDto(-1, new Resource(resourceId, null, null, null, 0, null, 0, null, null, null), usageDate, usageStartTime, usageEndTime, reservationName, new User(reservedPersonId, null, 0, null, null, null, null), new User(coReservedPersonId, null, 0, null, null, null, null), numberOfParticipants, attendanceType, supplement, 0);

		CheckReservationInputService checkReservationInputService = new CheckReservationInputService(_reservation);
		if(!checkReservationInputService.validate()){
			_session.setAttribute("messageForReservationRegist", checkReservationInputService.getValidationMessage());
			return false;
		}


		return true;
	}

}
