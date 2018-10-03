

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
    	//フォーマットパターン（yyyy/MM/dd）を設定する
    	SimpleDateFormat inputFormat =new SimpleDateFormat("yyyy/MM/dd");
    	//入力された日付が正しいかどうかチェックする
    	inputFormat.setLenient(false);
    	//フォーマットパターン（yyyy/MM/dd）を設定する
    	SimpleDateFormat timestampFormat =new SimpleDateFormat("yyyy-MM-dd");
    	//入力された日付が正しいかどうかチェックする
    	timestampFormat.setLenient(false);

    	try{
    	//【フォーマット通りに入力された場合（yyyy/MM/dd）】
    	//入力された日付がフォーマット通り（yyyy/MM/dd）だった場合、フィールドにセットする
    		_date=Timestamp.valueOf(timestampFormat.format(inputFormat.parse(date))+" "+hour+":"+minute+":00");

    	}catch(ParseException e){
    	//【年を省略して入力された場合（MM/dd）】
    	//※年(yyyy)が入力されていない状態なので、現在時刻で補完する
    		inputFormat = new SimpleDateFormat("MM/dd");
    		inputFormat.setLenient(false);
    	//後ほどyyyyを補完して文字列連結するために頭に「-」をつけたフォーマットにする
    		timestampFormat =new SimpleDateFormat("-MM-dd");
    		timestampFormat.setLenient(false);

    	//現在年を取得しyearに代入
    		Calendar cal = Calendar.getInstance();
    		int year = cal.get(Calendar.YEAR);
    		try {
				_date=Timestamp.valueOf(year + timestampFormat.format(inputFormat.parse(date))+" "+hour+":"+minute+":00");

		//入力した日付が正しくない場合、trueを返却する
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
