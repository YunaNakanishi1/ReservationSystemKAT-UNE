/**
 *
 */
package dto;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日時を取り扱うクラス
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
public class TimeDto {
	private int _hour;
	private int _minutes;

	public TimeDto(int hour, int minutes) {

	}

	/**
	 * date型の引数を時間と分に変換してフィールドにセットする
	 * @param date date型の引数
	 */
	public TimeDto(Date date) {

		if (date == null) {
			//MyExceptionを発生させる
		}

		try {
			SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
			try {
				SimpleDateFormat minutesFormat = new SimpleDateFormat("mm");
				String strHour = hourFormat.format(date);
				String strMinutes = minutesFormat.format(date);

				try {
					this._hour = Integer.parseInt(strHour);
				} catch(NumberFormatException e) {
					//MyException発生
				}

				try {
					this._minutes = Integer.parseInt(strMinutes);
				} catch(NumberFormatException e) {
					//MyException発生
				}

			} catch(NullPointerException e) {
				//MyException発生
			}
		} catch(IllegalArgumentException e) {
			//MyException発生
		}
	}
}
