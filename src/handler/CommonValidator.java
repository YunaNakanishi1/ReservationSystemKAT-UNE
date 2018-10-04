

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
     * 正しい日付の場合、falseを返却し、_dateフィールドにtimeStamp型でセットする.
     *
     * @param date ユーザから入力される年月日(例："2018/10/03"　"10/03")
     * @param hour ユーザからプルダウンによって選択された時間
     * @param minute ユーザからプルダウンによって選択された時間
     * @return　渡されたデータが正しい場合、false 誤りがある場合、true
     */
    protected boolean notDateOn(String date ,String hour,String minute){
    	/*【方針】
    	 1．入力された日付が「yyyy/MM/dd」形式でチェックした場合に正しいかをチェック
    	    正しい場合はtimestamp型に日付を変換する
    	 2．1．の形式が当てはまらない場合、「MM/dd」形式であるならば正しいかをチェック
    	    ※「MM/dd」形式では、入力された時の年を「yyyy」とする
    	 */


    	/* 「yyyy/MM/dd」形式で正しい日付か	*/
    	SimpleDateFormat inputFormat =new SimpleDateFormat("yyyy/MM/dd");
    	inputFormat.setLenient(false);	//日時解析を厳密に行う

    	SimpleDateFormat timestampFormat =new SimpleDateFormat("yyyy-MM-dd");
    	timestampFormat.setLenient(false);	//日時解析を厳密に行う

    	try{
    		_date=Timestamp.valueOf(timestampFormat.format(inputFormat.parse(date))+" "+hour+":"+minute+":00");



    	}catch(ParseException e){

    	/* 「MM/dd」形式で正しい日付か	*/
    		inputFormat = new SimpleDateFormat("MM/dd");
    		inputFormat.setLenient(false);	//日時解析を厳密に行う

    	//後ほどyyyyを補完して文字列連結するために頭に「-」をつけたフォーマットを用意
    		timestampFormat =new SimpleDateFormat("-MM-dd");
    		timestampFormat.setLenient(false);	//日時解析を厳密に行う



    	//入力年を補完した状態の日付をフィールドに格納
    		Calendar cal = Calendar.getInstance();
    		int year = cal.get(Calendar.YEAR);
    		try {
				_date=Timestamp.valueOf(year + timestampFormat.format(inputFormat.parse(date))+" "+hour+":"+minute+":00");


			} catch (ParseException e1) {	//入力した日付が正しくない場合
				return true;
			}
    	}
    	return false;

    }

    protected Timestamp getDate(){
    	return _date;
    }

}
