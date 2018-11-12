/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package handler;

import static handler.ViewHolder.*;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.ReservationDto;
import dto.User;
import service.GetReservationFromIdService;

/**
 * (26).
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
public class ShowReservationDetailsHandler implements Handler{

	private static Logger _log = LogManager.getLogger();


	@Override
	public String handleService(HttpServletRequest request) {

		HttpSession session = request.getSession(false);

		int reserveId = (int) session.getAttribute("reserveId");
		String currentUserId = (String) session.getAttribute("userId");

		if(currentUserId == null){
			return ERROR_PAGE;
		}


		GetReservationFromIdService getReservationFromIdService
		= new GetReservationFromIdService(reserveId);

		if(getReservationFromIdService.validate()){	//s4の処理
			try {
				//予約IDをもとにreservationDtoを作成＆フィールドにセットする
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

		//reservationDto（フィールド）を取得
		ReservationDto reservationDto = getReservationFromIdService.getReservation();

		//セッションに保存
		session.setAttribute("reservationDTOForReservationDetails",reservationDto);







		int deleted = reservationDto.getDeleted(); //リソースが削除済みかの判定に使用

		if(deleted == 0){ //リソースは削除されていない
			User user =reservationDto.getReservedPerson();
			User coUser =reservationDto.getCoReservedPerson();

			if(user!= null){
				String userId=user.getUserId();
				String coUserId=coUser.getUserId();

			}

		}else{

		}



		return null;
	}

}
