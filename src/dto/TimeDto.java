/**
 *
 */
package dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import exception.MyException;

/**
 * 日時を取り扱うクラス
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
public class TimeDto {
	private int _hour;
	private int _minutes;

	public TimeDto(int hour, int minutes) {
		this._hour = hour;
		this._minutes = minutes;
	}

	/**
	 * date型の引数を時間と分に変換してフィールドにセットする
	 * @param date date型の引数
	 */
	public TimeDto(Date date) {

		if (date == null) {
			//MyExceptionを発生させる
		    throw new MyException();
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
		            throw new MyException();
				}

				try {
					this._minutes = Integer.parseInt(strMinutes);
				} catch(NumberFormatException e) {
					//MyException発生
		            throw new MyException();
				}

			} catch(NullPointerException e) {
				//MyException発生
		         throw new MyException();
			}
		} catch(IllegalArgumentException e) {
			//MyException発生
	          throw new MyException();
		}
	}
	/**
	 * 分を渡して時分をセット
	 * @param minutes
	 */
	public TimeDto(int minutes) {
	    setTime(minutes);
	}


	/**
	 * 時間を返す
	 * @return
	 */
	public int getHour() {
		return _hour;
	}

	/**
	 * 分を返す
	 * @return\
	 */
	public int getMinutes() {
		return _minutes;
	}
	/**
	 * 時分を分で返す
	 * @return
	 */
	public int getTimeMinutesValue(){
	    return _hour * 60 + _minutes;
	}

	/**
	 * 分を渡して時間と分のフィールドにセット
	 * @param minutes
	 */
	public void setTime(int minutes){
	    _hour = minutes/60;
	    _minutes = minutes % 60;
	}
	/**
	 * 日付データを渡してTimeStampを得る
	 * @param date yyyy/M/d形式
	 * @return フィールド情報を付加したTimeStamp
	 */
	public Timestamp getTimeStamp(String date) {
	    //フォーマッタ
	    DateTimeFormatter dFormatter = DateTimeFormatter.ofPattern("yyyy/M/d/H/m");
	    //フィールドを使用して日付文字列作成
	    String addedDate = date + "/" + _hour + "/" + _minutes;
	    LocalDateTime lDateTime = null;

	    try{
	       lDateTime = LocalDateTime.parse(addedDate, dFormatter);

	    }catch (DateTimeParseException e) {
            throw new MyException();
        }

	    Timestamp timestamp = Timestamp.valueOf(lDateTime);
	    return timestamp;
   }

	@Override
	public String toString() {
	    String hourStr = String.format("%02d", _hour);
	    String minutesStr = String.format("%02d", _minutes);
	    return hourStr+":"+minutesStr;
	}
}
