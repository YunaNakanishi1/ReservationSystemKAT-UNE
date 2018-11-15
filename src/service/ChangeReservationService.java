/**
 *
 */
package service;

import java.sql.SQLException;

import dao.ReservationDao;
import dto.ReservationDto;

/**
 * @author p000527259
 *
 */
public class ChangeReservationService implements Service {
	private ReservationDto _reservation;
	private int _result;

	public ChangeReservationService(ReservationDto reservation) {
		this._reservation = reservation;
	}

	/* (非 Javadoc)
	 * @see service.Service#validate()
	 */
	@Override
	public boolean validate() {
		return true;
	}

	/* (非 Javadoc)
	 * @see service.Service#execute()
	 */
	@Override
	public void execute() throws SQLException {
		ReservationDao rd = new ReservationDao();
		_result = rd.updateReservation(_reservation);

	}

	public int getResult() {
		return _result;
	}

}
