package handler;

import static handler.MessageHolder.*;
import static handler.ViewHolder.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.ReservationDto;
import service.GetReservationListFromResourceIdService;


public class PushDeleteButtonOnResourceDeleteConfirmHandler implements Handler{

	private static Logger _log = LogManager.getLogger();

	@Override
	public String handleService(HttpServletRequest request) {
		HttpSession session =request.getSession(false);

	//リクエストからresourceIdをString型で受け取る
	String resourceId = request.getParameter("resourceId");

	//リクエストからチェックボックスの値を取得
	String checkBox =request.getParameter("checkedConfirm");

	if(checkBox != null){
		//sessionからreservationDtoを取得
		List<ReservationDto> previousReservationList = (List<ReservationDto>)session.getAttribute("reservationListForResourceDeleteConfirm");
		int count =0;

		List<ReservationDto> currentReservationList =new ArrayList<ReservationDto>();
		GetReservationListFromResourceIdService getReservationListFromResourceIdService=new GetReservationListFromResourceIdService(resourceId);
		try{
			getReservationListFromResourceIdService.execute();
			currentReservationList=getReservationListFromResourceIdService.getList();
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
			session.setAttribute("reservationListForResourceDeleteConfirm",currentReservationList);
			request.setAttribute("resourceId",resourceId);
			return DELETE_RESOURCE;
		}
		session.setAttribute("reservationListForResourceDeleteConfirm",currentReservationList);
		request.setAttribute("messageForResourceDeleteConfirm",EM41);
		return RESOURCE_DELETE_CONFIRM;



	}else{
		request.setAttribute("messageForResourceDeleteConfirm",EM43);
		request.setAttribute("resourceId",resourceId);
		return RESOURCE_DELETE_CONFIRM;
	}



	}

}
