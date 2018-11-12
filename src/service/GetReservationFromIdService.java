/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package service;

import java.sql.SQLException;

import dao.ReservationDao;
import dto.ReservationDto;

/**
 * 予約IDをもとに予約情報を取得(4).
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
public class GetReservationFromIdService implements Service{

	int _reserveId;
	ReservationDto _reservation;

	public GetReservationFromIdService(int reserveId){
		_reserveId = reserveId;
	}



	@Override
	public boolean validate() {
		return true;
	}

	@Override
	public void execute() throws SQLException {
		ReservationDao reservationDao = new ReservationDao();

	}

	public ReservationDto getReservation(){
		return null;
	}

}
