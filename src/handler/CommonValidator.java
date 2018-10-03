

package handler;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author リコーITソリューションズ株式会社 z00s600124
 *
 * リソースの入力項目に対して、正しい入力形式であるかをチェックするためのクラス.
 *
 */
public class CommonValidator {
	/**
     * 引数valの内容が設定されているかどうかチェックする.
     *
     * @param val 検証する値
     * @return 設定されていればfalse、設定されていなければtrue
     */
    protected boolean notSetOn(String val) {

        if (val == null) {
            return true;
        }
        if ("".equals(val)) {
            return true;
        }

        return false;

    }

    private int _number;

    /**
     * 引数valの内容が半角整数値かどうかチェック.
     *  数値であればフィールドintValにその数値を保存
     *
     * @param val 検証する値
     * @return 半角整数値であればfalse、半角整数値でなければtrue
     */
    protected boolean notNumericOn(String val) {

        try {
            _number = Integer.parseInt(val);
        } catch (NumberFormatException e) {
            return true;
        }

        return false;

    }

    /**
     * intValフィールドのgetter.
     *
     * @return intValフィールド
     */
    protected int getNumber() {
        return _number;
    }

    /**
     * notDateOnがfalseを返すとき、notDateOnの引数の日付データがセットされる
     */
    private Timestamp _date;

    /**
     * 引数のデータが正しい日付であるかを判断するメソッド.
     * 正しい日付の場合、falseを返却し、_dateフィールドにセットする。
     *
     * @param date ユーザから入力される年月日(例："2018/10/03"　"10/03")
     * @param hour ユーザからプルダウンによって選択された時間
     * @param minute ユーザからプルダウンによって選択された時間
     * @return　渡されたデータが正しい場合、false 誤りがある場合、true
     */
    protected boolean notDateOn(String date ,String hour,String minute){
    	SimpleDateFormat inputFormat =new SimpleDateFormat("yyyy/MM/dd");
    	inputFormat.setLenient(false);
    	SimpleDateFormat timestampFormat =new SimpleDateFormat("yyyy-MM-dd");
    	timestampFormat.setLenient(false);
    	try{
    		_date=Timestamp.valueOf(timestampFormat.format(inputFormat.parse(date))+" "+hour+":"+minute+":00");
    	}catch(ParseException e){
    		inputFormat = new SimpleDateFormat("MM/dd");
    		inputFormat.setLenient(false);
    		timestampFormat =new SimpleDateFormat("-MM-dd");
    		timestampFormat.setLenient(false);
    		Calendar cal = Calendar.getInstance();
    		int year = cal.get(Calendar.YEAR);
    		try {
				_date=Timestamp.valueOf(year + timestampFormat.format(inputFormat.parse(date))+" "+hour+":"+minute+":00");

			} catch (ParseException e1) {
				return true;
			}
    	}
    	return false;

    }

    protected Timestamp getDate(){
    	return _date;
    }

}
