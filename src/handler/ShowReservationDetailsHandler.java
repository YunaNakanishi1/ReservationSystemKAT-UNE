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

import service.GetReservationFromIdService;

/**
 * (26).
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
public class ShowReservationDetailsHandler implements Handler{

	private static Logger _log = LogManager.getLogger();


	@Override
	public String handleService(HttpServletRequest request) {

		//servletでセッションは作られているのでfalseでよい
		HttpSession session = request.getSession(false);

		int reserveId = (int) session.getAttribute("reserveId");


		GetReservationFromIdService getReservationFromIdService
		= new GetReservationFromIdService(reserveId);

		if(getReservationFromIdService.validate()){

			try {
				//予約IDをもとにreservationDtoを作成＆フィールドにセットする
				getReservationFromIdService.execute();

				//reservationDto（フィールド）を取得しセッションに保存
				session.setAttribute("reservationDTOForReservationDetails",
						getReservationFromIdService.getReservation());


			} catch (SQLException e) {
				e.printStackTrace();
				_log.error("SQLException");
				return ERROR_PAGE;
			}

		}else{
			_log.error("validateError");
			return ERROR_PAGE;
		}


		return null;
	}

}
