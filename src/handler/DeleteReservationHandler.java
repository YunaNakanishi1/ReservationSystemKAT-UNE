package handler;

import static handler.MessageHolder.*;
import static handler.ViewHolder.*;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.ReservationDto;
import dto.User;
import service.DeleteReservationService;
import service.GetReservationFromIdService;

/**
 * サーブレット番号：2
 * 予約の削除を行う.
 * @author z00h230741
 *
 */
public class DeleteReservationHandler implements Handler{
	private static Logger _log = LogManager.getLogger();

	@Override
	public String handleService(HttpServletRequest request){
		HttpSession session =request.getSession(true);

		int reservationIdForReservationDetails = (int)request.getAttribute("reservationIdForReservationDetails");
		session.setAttribute("reservationIdForReservationDetails",reservationIdForReservationDetails);

		String userIdOfLoggedIn = (String)session.getAttribute("userIdOfLoggedIn");

		CommonValidator commonValidator = new CommonValidator();
		if(commonValidator.notSetOn(userIdOfLoggedIn)){
			 _log.error("commonValidateError");
			 return ERROR_PAGE;
		}

		GetReservationFromIdService getReservationFromIdService = new GetReservationFromIdService(reservationIdForReservationDetails);
		if(getReservationFromIdService.validate()){
			try {
				getReservationFromIdService.execute();
			} catch (SQLException e) {
				e.printStackTrace();
				_log.error("SQLException");
				return ERROR_PAGE;
			}
		}else{
			 _log.error("validateError");
			 return ERROR_PAGE;
		}

		//reservationDto(フィールド)を取得
		ReservationDto reservationDto = getReservationFromIdService.getReservation();
		if(reservationDto != null){
			session.setAttribute("reservationDtoForReservationDetails",reservationDto);
		}else{
			 _log.error("reservationDtoIsNull");
			 return ERROR_PAGE;
		}

		User reservedPerson =reservationDto.getReservedPerson();
		User coReservedPerson =reservationDto.getCoReservedPerson();

		if(reservedPerson!=null){
			String reservedPersonId = reservedPerson.getUserId();
			String coReservedPersonId = coReservedPerson.getUserId();

			if(userIdOfLoggedIn.equals(reservedPersonId)||userIdOfLoggedIn.equals(coReservedPersonId)){
				DeleteReservationService deleteReservationService=new DeleteReservationService(reservationIdForReservationDetails);
				if(deleteReservationService.validate()){
					try{
						deleteReservationService.execute();
						int result =deleteReservationService.getResult();
							if(result==1){
								request.setAttribute("messageForDeleteCompleted", PM05);
								return SHOW_RESERVATION_DETAILS_SERVLET;
							}else{
								_log.error("deleteError");
								 return ERROR_PAGE;
							}
					}catch(SQLException e){
						_log.error("SQLException");
						 return ERROR_PAGE;
					}

				}else{
					_log.error("ValidateError");
					 return ERROR_PAGE;
				}

			}else{
				_log.error("NoreservedPersonorcoReservedPerson");
				 return ERROR_PAGE;
			}
		}else{
			 _log.error("reservedPersonIsNull");
			 return ERROR_PAGE;
		}
<<<<<<< HEAD




=======
>>>>>>> refs/remotes/origin/develop





	}
}