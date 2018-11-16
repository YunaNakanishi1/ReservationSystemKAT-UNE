package handler;

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
import exception.MyException;
import service.GetReservationFromIdService;
import service.GetReservationListBetweenDateService;
import service.GetSliderWidthService;
import service.IsNotOverlapUsageTimeService;

public class PushChangeButtonOnReservationDetailsHandler implements Handler {

	private Logger _log = LogManager.getLogger();
	private TimeDto _startTimeSliderValue;
	private TimeDto _endTimeSliderValue;

	@Override
	public String handleService(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		ReservationDto reservation=null;
		String reserveIdStr=request.getParameter("reserveId");
		int reserveId=0;
		try{
		reserveId=Integer.parseInt(reserveIdStr);
		}catch (NumberFormatException e) {
			_log.error("reserveId is not number");
		}
		try{
			reservation=getAndCheckReservation(reserveId);
		}catch (MyException e) {
			return ERROR_PAGE;
		}

		session.setAttribute("reservationDTOForReservationChange", reservation);
		session.setAttribute("usageStartTimeForReservationChange", reservation.getUsageStartTime());
		session.setAttribute("usageEndTimeForReservationChange", reservation.getUsageEndTime());
		session.setAttribute("reservationNameForReservationChange", reservation.getReservationName());
		session.setAttribute("numberOfParticipantsForReservationChange", reservation.getNumberOfParticipants());
		session.setAttribute("coReservedPersonIdForReservationChange", reservation.getCoReservedPerson().getUserId());
		AttendanceTypeDto attendanceType=reservation.getAttendanceTypeDto();
		if(attendanceType!=null){
		session.setAttribute("attendanceTypeIdForReservationChange", reservation.getAttendanceTypeDto().getAttendanceTypeId());
		}else{
			session.setAttribute("attendanceTypeIdForReservationChange", null);
		}
		session.setAttribute("reserveSupplementForReservationChange", reservation.getSupplement());
		try{
			getSliderWidth(reservation);
		}catch (MyException e) {
			return ERROR_PAGE;
		}

		session.setAttribute("usableStartTimeForReservationChange", _startTimeSliderValue);
		session.setAttribute("usableEndTimeForReservationChange", _endTimeSliderValue);




		return SHOW_RESERVATION_CHANGE_SERVLET;
	}

	private ReservationDto getAndCheckReservation(int reserveId){
		ReservationDto reservation=null;

		try{
			GetReservationFromIdService getReservationFromIdService=new GetReservationFromIdService(reserveId);
			if(getReservationFromIdService.validate()){
				getReservationFromIdService.execute();
				reservation=getReservationFromIdService.getReservation();
				if(reservation==null){
					_log.error("reservation not found");
					throw new MyException();
				}else if(reservation.getDeleted()==1){
					_log.error("reservation is deleted");
					throw new MyException();
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
			_log.error("database error check");
			throw new MyException();
		}

		String usageDate=reservation.getUsageDate();
		Timestamp usageStartTimestamp=reservation.getUsageStartTime().getTimeStamp(usageDate);
		Timestamp usageEndTimestamp=reservation.getUsageEndTime().getTimeStamp(usageDate);
		Resource resource = reservation.getResource();
		try{
		IsNotOverlapUsageTimeService isNotOverlapUsageTimeService=new IsNotOverlapUsageTimeService(resource, usageStartTimestamp, usageEndTimestamp);
		if(!isNotOverlapUsageTimeService.validate()){
			_log.error("resource is stop usage");
			throw new MyException();
		}
		}catch (MyException e) {
			_log.error("isNotOverlapUsageTimeService input error");
			throw e;
		}
		try{
			GetReservationListBetweenDateService getReservationListBetweenDateService=new GetReservationListBetweenDateService(resource.getResourceId(), usageStartTimestamp, usageEndTimestamp);

			if(getReservationListBetweenDateService.validate()){
				getReservationListBetweenDateService.execute();

				List<ReservationDto> reservationList=getReservationListBetweenDateService.getReservationList();

				System.out.println(reservationList.get(0).getReservationName());

				System.out.println(reservationList.get(1).getReservationName());
				if(reservationList.size()==1){
					if(reservationList.get(0).getReservationId()!=reservation.getReservationId()){
						_log.error("anothr reservation exist id");
						throw new MyException();
					}

				}else{
					_log.error("anothr reservation exist");
					throw new MyException();
				}
			}
		} catch (MyException e) {
				_log.error("GetReservationListBetweenDateService input error");
				throw e;
		} catch (SQLException e){
				_log.error("database error listbetween");
				throw new MyException();
		}

		return reservation;
	}

	private void getSliderWidth(ReservationDto reservation){
		TimeDto minTimeDto=new TimeDto(0,0);
		TimeDto maxTimeDto=new TimeDto(24,0);
		String usageDate=reservation.getUsageDate();
		Resource resource=reservation.getResource();
		List<ReservationDto> reservationList = null;
		try{
		GetReservationListBetweenDateService getReservationListBetweenDateService = new GetReservationListBetweenDateService(resource.getResourceId(), minTimeDto.getTimeStamp(usageDate), maxTimeDto.getTimeStamp(usageDate));
		if(getReservationListBetweenDateService.validate()){
			getReservationListBetweenDateService.execute();
			reservationList=getReservationListBetweenDateService.getReservationList();

		}
		}catch (MyException e) {
			_log.error("GetReservationListBetweenDateService input error");
			throw e;
		} catch (SQLException e) {
			_log.error("database error slider");
			e.printStackTrace();
			throw new MyException();
		}
		try{
		GetSliderWidthService getSliderWidthService = new GetSliderWidthService(reservationList, reservation);
		if(getSliderWidthService.validate()){
			getSliderWidthService.execute();
			_startTimeSliderValue=getSliderWidthService.getStartTimeSliderValue();
			_endTimeSliderValue=getSliderWidthService.getEndTimeSliderValue();
		}

		}catch (MyException e) {
			_log.error("GetSliderWidthService input error");
			throw e;
		}
	}



}
