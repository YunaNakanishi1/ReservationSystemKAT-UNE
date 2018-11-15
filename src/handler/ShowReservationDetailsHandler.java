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
import dto.Resource;
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

		int reserveId = (int) session.getAttribute("reservationIdForReservationDetails");
		String currentUserId = (String) session.getAttribute("userIdOfLoggedIn");

		if(currentUserId == null){
			_log.error("currentUserId is null");
			return ERROR_PAGE;
		}


		GetReservationFromIdService getReservationFromIdService
		= new GetReservationFromIdService(reserveId);

		ReservationDto reservation;

		if(getReservationFromIdService.validate()){	//s4の処理
			try {
				//予約IDをもとにreservationDtoを作成＆フィールドにセットする
				getReservationFromIdService.execute();
				reservation = getReservationFromIdService.getReservation();

				if(reservation != null){
					session.setAttribute("reservationDTOForReservationDetails", reservation);
				}else{
					_log.error("reservationDTO is null");
					return ERROR_PAGE;
				}

			} catch (SQLException e) {
				e.printStackTrace();
				_log.error("SQLException");
				return ERROR_PAGE;
			}
		}else{
			_log.error("validateError");
			return ERROR_PAGE;
		}



		int deleted = reservation.getDeleted(); //予約が削除済みかの判定に使用

		if(deleted == 0){ //予約は削除されていない
			User user = reservation.getReservedPerson();
			User coUser = reservation.getCoReservedPerson();

			if(user != null){
				String userId = user.getUserId();
				String coUserId = coUser.getUserId();


				//予約者と共同予約者にのみ削除・変更ボタンを表示する。
				//jspでそれを判断するためのフラグをセット
				if(currentUserId.equals(userId)){
					request.setAttribute("flagForShowingDeleteAndChangeButton", true);
				}else if(currentUserId.equals(coUserId)){
					request.setAttribute("flagForShowingDeleteAndChangeButton", true);
				}else{
					request.setAttribute("flagForShowingDeleteAndChangeButton", false);
				}

				//予約が削除されていない場合、リソース詳細リンクは表示される。
				//jspでそれを判断するためのフラグをセット
				request.setAttribute("linkToResourceDetails", true);

			}else{
				_log.error("userDto is null");
				return ERROR_PAGE;
			}


		}else if(deleted == 1){	//予約は削除されている

			Resource resource = reservation.getResource();

			if(resource != null){

				if((resource.getDeleted())==0){	//リソースは削除されていない

					//予約が削除されている、かつリソースが削除されていない場合、
					//リソース詳細リンクは表示される。
					//jspでそれを判断するためのフラグをセット
					request.setAttribute("linkToResourceDetails", true);

				}else if((resource.getDeleted())==1){	//リソースは削除されている

					int authority = (int) session.getAttribute("authority");

					if(authority == 0){ //リソース管理者
						//予約が削除されている、かつリソースが削除されている、
						//かつ操作者がリソース管理者の場合、リソース詳細リンクは表示される。
						//jspでそれを判断するためのフラグをセット
						request.setAttribute("linkToResourceDetails", true);

					}else if(authority == 1){ //リソース利用者
						//予約が削除されている、かつリソースが削除されている、
						//かつ操作者がリソース利用者の場合、リソース詳細リンクは表示されない。
						//jspでそれを判断するためのフラグをセット
						request.setAttribute("linkToResourceDetails", false);

					}else{
						_log.error("authority is error");
						return ERROR_PAGE;
					}

				}else{
					_log.error("deleted of resources is error");
					return ERROR_PAGE;
				}


				request.setAttribute("flagForShowingDeleteAndChangeButton", false);

			}else{
				_log.error("resourceDto is null");
				return ERROR_PAGE;
			}

		}else{
			_log.error("deleted of reservation is error");
			return ERROR_PAGE;
		}



		return RESERVATION_DETAILS;
	}

}
