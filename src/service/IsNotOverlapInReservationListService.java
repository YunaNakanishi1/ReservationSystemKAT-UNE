/**
 *
 */
package service;

import java.sql.SQLException;
import java.util.List;

import dto.ReservationDto;

/**
 *
 * 指定の予約チェック
 * @author p000527259
 *
 */
public class IsNotOverlapInReservationListService implements Service {
	private List<ReservationDto> _reservationList;
	private ReservationDto _reservation;

	public IsNotOverlapInReservationListService(List<ReservationDto> reservationList, ReservationDto reservation) {
		this._reservationList = reservationList;
		this._reservation = reservation;
	}

	/* (非 Javadoc)
	 * @see service.Service#validate()
	 */
	@Override
	public boolean validate() {

		if (_reservationList.size() == 1) {
			if (_reservationList.get(0).getReservationId() == _reservation.getReservationId()) {
				return true;
			}
		}
		return false;
	}

	/* (非 Javadoc)
	 * @see service.Service#execute()
	 */
	@Override
	public void execute() throws SQLException {
		// TODO 自動生成されたメソッド・スタブ

	}

}
