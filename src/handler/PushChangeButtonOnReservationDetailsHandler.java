package handler;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.ReservationDto;
import dto.Resource;
import exception.MyException;
import service.GetReservationFromIdService;
import service.GetReservationListBetweenDateService;
import service.IsNotOverlapUsageTimeService;

public class PushChangeButtonOnReservationDetailsHandler implements Handler {

	private Logger _log = LogManager.getLogger();

	@Override
	public String handleService(HttpServletRequest request) {

		return null;
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
			_log.error("database error");
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
			if(reservationList.size()==1){
				if(reservationList.get(0).getReservationId()!=reservation.getReservationId()){
					_log.error("anothr reservation exist");
					throw new MyException();
				}

			}else{
				_log.error("anothr reservation exist");
				throw new MyException();
			}
		}
		}catch (MyException e) {
			_log.error("GetReservationListBetweenDateService input error");
			throw e;
		}catch (SQLException e){
			_log.error("database error");
			throw new MyException();
		}

		return reservation;
	}

}
