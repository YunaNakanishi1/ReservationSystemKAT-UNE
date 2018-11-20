package handler;

import static handler.MessageHolder.*;
import static handler.ViewHolder.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.ReservationDto;
import service.GetReservationListBetweenDateService;

public class PushDeleteButtonOnSuspensionUseConfirmHandler implements Handler{

	private static Logger _log = LogManager.getLogger();


	@Override
	public String handleService(HttpServletRequest request) {
		HttpSession session =request.getSession(false);

		//リクエストからresourceIdをString型で受け取る
		String resourceId = request.getParameter("resourceId");


		//セッションから情報を取得
		Timestamp stopStartDate=(Timestamp) session.getAttribute("stopStartDate");
		Timestamp stopEndDate=(Timestamp) session.getAttribute("stopEndDate");



		//リクエストからチェックボックスの値を取得
		String checkBox =request.getParameter("checkedConfirm");

		if(checkBox != null){
			//sessionからreservationDtoを取得
			List<ReservationDto> previousReservationList = (List<ReservationDto>)session.getAttribute("reservationListForSuspensionUseConfirm");
			int count =0;

			List<ReservationDto> currentReservationList =new ArrayList<ReservationDto>();
			GetReservationListBetweenDateService getReservationListFromResourceIdService=new GetReservationListBetweenDateService(resourceId,stopStartDate,stopEndDate);
			try{
				getReservationListFromResourceIdService.execute();
				currentReservationList=getReservationListFromResourceIdService.getReservationList();
			}catch(SQLException e){
				_log.error("SQLException");
				return ERROR_PAGE;
			}

			//結果のリストの各要素について
			for(ReservationDto current : currentReservationList){
				//sessionから取得したリストの各要素について
				for(ReservationDto previous :previousReservationList){
					if(current.getReservationId()==previous.getReservationId()){
						count++;
					}

				}
			}

			if(currentReservationList.size()==count){
				session.setAttribute("reservationListForSuspensionUseConfirm",currentReservationList);
				request.setAttribute("resourceId",resourceId);
				return SET_RESOURCE_DETAILS_SERVLET;
			}
			session.setAttribute("reservationListForSuspensionUseConfirm",currentReservationList);
			request.setAttribute("messageForSuspensionUseConfirm",EM41);
			return SUSPENSION_USE_CONFIRM;



		}else{
			request.setAttribute("messageForSuspensionUseConfirm",EM43);
			request.setAttribute("resourceId",resourceId);
			return SUSPENSION_USE_CONFIRM;
		}



		}

	}


