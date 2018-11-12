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

	public TimeDto(Date date) {
		String strHour = new SimpleDateFormat("h").format(date);
		this._hour = Integer.parseInt(strHour);
		String strMinutes = new SimpleDateFormat("m").format(date);
		this._minutes = Integer.parseInt(strMinutes);
	}
}
