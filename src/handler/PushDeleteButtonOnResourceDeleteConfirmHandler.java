package handler;

import static handler.MessageHolder.*;
import static handler.ViewHolder.*;

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
		HttpSession session =request.getSession(true);

	//リクエストからresourceIdをString型で受け取る
	String resourceId = request.getParameter("resourceId");

	//リクエストからチェックボックスの値を取得
	String checkBox =request.getParameter("checkedConfirm");

	if(checkBox != null){




	}else{
		request.setAttribute("messageForResourceDeleteConfirm",EM43);
		return RESOURCE_DELETE_CONFIRM;
	}
	//sessionからreservationDtoを取得
	List<ReservationDto> reservationList = (List<ReservationDto>)session.getAttribute("reservationListForResourceDeleteConfirm");
	int count = 1;

	List<ReservationDto> resultList =new ArrayList<ReservationDto>();
	GetReservationListFromResourceIdService getReservationListFromResourceIdService=new GetReservationListFromResourceIdService(resourceId);
	resultList=getReservationListFromResourceIdService.getList();

	//結果のリストの各要素について
	for(ReservationDto result : resultList){
		//sessionから取得したリストの各要素について
		for(ReservationDto reservationDto : reservationList){
			if() //ここから

		}
	}


	}

}
