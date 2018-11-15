/**
 *
 */
package service;

import static handler.MessageHolder.*;

import java.sql.SQLException;

import dto.ReservationDto;

/**
*
* リソース登録のバリデーションチェックのためのサービス.
* @author リコーITソリューションズ株式会社 KAT-UNE
*
*/
public class CheckReservationInputService implements Service {
	private ReservationDto _reservation;
	private String _validationMessage;

	public CheckReservationInputService(ReservationDto reservationDto) {
		this._reservation = reservationDto;
	}

	/* (非 Javadoc)
	 * @see service.Service#validate()
	 */
	@Override
	public boolean validate() {
		//リソース名の長さチェック
		if (_reservation.getReservationName().length() > 30) {
			_validationMessage = EM18;
			return false;
		}

		//参加者人数チェック
		if ((_reservation.getNumberOfParticipants() < 0) || (_reservation.getNumberOfParticipants() > 999)) {
			_validationMessage = EM21;
			return false;
		}

		//補足の長さチェック
		if (_reservation.getSupplement().length() > 500) {
			_validationMessage = EM22;
			return false;
		}

		return true;
	}

	/* (非 Javadoc)
	 * @see service.Service#execute()
	 */
	@Override
	public void execute() throws SQLException {
		// TODO 自動生成されたメソッド・スタブ

	}


	public String getValidationMessage() {
		return _validationMessage;
	}


}
