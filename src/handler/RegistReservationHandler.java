package handler;

import static handler.MessageHolder.*;
import static handler.ViewHolder.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

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
import service.GetReservationListBetweenDateService;
import service.GetResouceFromIdService;
import service.IsNotOverlapUsageTimeService;
import service.RegistReservationService;

public class RegistReservationHandler implements Handler {
	private static Logger _log = LogManager.getLogger();
	private HttpSession _session;
	private HttpServletRequest _request;
	private ReservationDto _reservation;

	@Override
	public String handleService(HttpServletRequest request) {
		_session = request.getSession(false);
		_request=request;
		try {
			if(!validate()){
				return SHOW_RESERVATION_REGIST_SERVLET;
			}
		} catch (MyException e) {
			return ERROR_PAGE;
		}

		try {
			if(!reservable()){
				_session.setAttribute("messageForResourceSelectUpper", EM24);
				return SEARCH_RESOURCE_LIST_SERVLET;
			}
		} catch (MyException e) {
			return ERROR_PAGE;
		}
		if(!regist()){
			return ERROR_PAGE;
		}

		_session.setAttribute("messageForReservationRegist", PM02);

		return SHOW_RESERVATION_DETAILS_SERVLET;
	}

	private boolean validate() {
		CommonValidator commonValidator = new CommonValidator();
		String usageStartTimeStr = _request.getParameter("usageStartTime");
		String usageEndTimeStr = _request.getParameter("usageEndTime");
		String reservationName = _request.getParameter("reservationName");
		String displayNumberOfParticipants = _request.getParameter("numberOfParticipants");
		String coReservedPersonId = _request.getParameter("coReservedPersonId");
		if (commonValidator.notSetOn(coReservedPersonId)) {
			coReservedPersonId = null;
		}
		String attendanceTypeIdStr = _request.getParameter("attendanceTypeId");
		String supplement = _request.getParameter("supplement");

		_session.setAttribute("reservationNameForReservationRegist", reservationName);
		_session.setAttribute("displayNumberOfParticipantsForReservationRegist", displayNumberOfParticipants);
		_session.setAttribute("coReservedPersonIdForReservationRegist", coReservedPersonId);
		_session.setAttribute("reserveSupplementForReservationRegist", supplement);

		AttendanceTypeDto attendanceType = null;
		int attendanceTypeId=-1;
		if(!commonValidator.notSetOn(attendanceTypeIdStr)){
		try {
			attendanceTypeId=Integer.parseInt(attendanceTypeIdStr);
			attendanceType = new AttendanceTypeDto(attendanceTypeId, null);
			_session.setAttribute("attendanceTypeIdForReservationRegist",attendanceTypeId );
		} catch (NumberFormatException e) {
			_log.error("attendanceType not number");
			throw new MyException();
		}
		}else{
			_session.setAttribute("attendanceTypeIdForReservationRegist", null);
		}

		int usageStartTimeInt = 0;

		if (commonValidator.notNumericOn(usageStartTimeStr)) {
			_log.error("usageStartTime not number");
			throw new MyException();
		} else {
			usageStartTimeInt = commonValidator.getNumber();
		}
		TimeDto usageStartTime = new TimeDto(usageStartTimeInt);

		int usageEndTimeInt = 0;

		if (commonValidator.notNumericOn(usageEndTimeStr)) {
			_log.error("usageEndTime not number");
			throw new MyException();
		} else {
			usageEndTimeInt = commonValidator.getNumber();
		}
		TimeDto usageEndTime = new TimeDto(usageEndTimeInt);

		_session.setAttribute("usageStartHourForReservationRegist", usageStartTime);
		_session.setAttribute("usageEndHourForReservationRegist", usageEndTime);
		int numberOfParticipants = 0;
		try {
			numberOfParticipants = commonValidator.getCapacityValue(displayNumberOfParticipants);
		} catch (MyException e) {
			_session.setAttribute("messageForReservationRegist", EM20);
			return false;
		}
		String usageDate = (String) _session.getAttribute("usageDateForReservationRegist");
		String resourceId = (String) _session.getAttribute("resourceIdForReservationRegist");
		String reservedPersonId = (String) _session.getAttribute("userIdOfLoggedIn");

		if(commonValidator.notSetOn(reservationName)){
			reservationName="名称なし";
		}



		_reservation = new ReservationDto(-1, new Resource(resourceId, null, null, null, 0, null, 0, null, null, null),
				usageDate, usageStartTime, usageEndTime, reservationName,
				new User(reservedPersonId, null, 0, null, null, null, null),
				new User(coReservedPersonId, null, 0, null, null, null, null), numberOfParticipants, attendanceType,
				supplement, 0);

		CheckReservationInputService checkReservationInputService = new CheckReservationInputService(_reservation);
		if (!checkReservationInputService.validate()) {
			_session.setAttribute("messageForReservationRegist", checkReservationInputService.getValidationMessage());
			return false;
		}

		return true;
	}

	private boolean reservable() {
		String usageDate = _reservation.getUsageDate();
		TimeDto usageStartTime = _reservation.getUsageStartTime();
		TimeDto usageEndTime = _reservation.getUsageEndTime();
		Timestamp usageStartTimestamp = usageStartTime.getTimeStamp(usageDate);
		Timestamp usageEndTimestamp = usageEndTime.getTimeStamp(usageDate);

		Resource resource = _reservation.getResource();
		List<ReservationDto> reservationList = null;
		try {
			GetReservationListBetweenDateService getReservationListBetweenDateService = new GetReservationListBetweenDateService(
					resource.getResourceId(), usageStartTimestamp, usageEndTimestamp);
			if (getReservationListBetweenDateService.validate()) {
				getReservationListBetweenDateService.execute();
				reservationList = getReservationListBetweenDateService.getReservationList();
				if (reservationList == null) {
					_log.error("reservationList is null");
					throw new MyException();
				} else if (reservationList.size() != 0) {
					return false;
				}

			} else {
				_log.error("validate error");
				throw new MyException();
			}

		} catch (MyException e) {
			_log.error("GetReservationListBetweenDateService input error");
			throw e;
		} catch (SQLException e) {
			_log.error("database error");
			e.printStackTrace();
			throw new MyException();
		}

		GetResouceFromIdService getResouceFromIdService = new GetResouceFromIdService(resource.getResourceId());
		if (getResouceFromIdService.validate()) {
			try {
				getResouceFromIdService.execute();
				resource = getResouceFromIdService.getResource();
			} catch (SQLException e) {
				_log.error("database error");
				e.printStackTrace();
				throw new MyException();
			}
		} else {
			_log.error("validate error");
			throw new MyException();
		}
		try {
			IsNotOverlapUsageTimeService isNotOverlapUsageTimeService = new IsNotOverlapUsageTimeService(resource,
					usageStartTimestamp, usageEndTimestamp);
			if (!isNotOverlapUsageTimeService.validate()) {
				return false;
			}
		} catch (MyException e) {
			_log.error("IsNotOverlapUsageTimeService input error");
			throw e;
		}
		if (resource.getDeleted() == 1) {
			return false;
		}

		return true;
	}

	private boolean regist(){
		try{
		RegistReservationService registReservationService = new RegistReservationService(_reservation);
		if(registReservationService.validate()){
			registReservationService.execute();
			int reserveId = registReservationService.getReserveId();
			if(reserveId==-1){
				_log.error("illegal reserveId");
				return false;
			}else{
				_session.setAttribute("reservationIdForReservationDetails", reserveId);
			}
		}
		}catch (MyException e) {
			_log.error("RegistReservationService input error");
			return false;
		} catch (SQLException e) {
			_log.error("database error");
			e.printStackTrace();
			return false;
		}

		return true;
	}

}
