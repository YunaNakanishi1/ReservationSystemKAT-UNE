package service;

import java.sql.SQLException;

import dao.ReservationDao;

/**
 * 予約削除を行うサービス.
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
public class DeleteReservationService implements Service{

	private int reserveId;
	private int result;

	public DeleteReservationService(int reserveId){
		this.reserveId = reserveId;
	}

	@Override
	public boolean validate() {
		return true;
	}

	@Override
	public void execute() throws SQLException {
		ReservationDao reservationDao = new ReservationDao();
		result = reservationDao.deleteReservation(reserveId);
	}

	public int getResult(){
		return result;
	}

}
