package service;

import java.sql.SQLException;

import dao.ReservationDao;
import dto.ReservationDto;
import exception.MyException;

public class RegistReservationService implements Service {
	private ReservationDto _reservation;
	private int _reserveId;


	public RegistReservationService(ReservationDto reservation) {
		super();
		if(reservation==null){
			throw new MyException();
		}
		this._reservation = reservation;
	}

	@Override
	public boolean validate() {

		return true;
	}

	@Override
	public void execute() throws SQLException {

		ReservationDao reservationDao=new ReservationDao();
		_reserveId=reservationDao.insertReservation(_reservation);

	}

	public int getReserveId() {
		return _reserveId;
	}


}
