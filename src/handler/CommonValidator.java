package handler;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
     * 引数valの内容が数値かどうかチェック.
     *  数値であればフィールドintValにその数値を保存
     *
     * @param val 検証する値
     * @return 数値であればfalse、数値でなければtrue
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

    private Timestamp _date;

    protected boolean notDateOn(String date ,String hour,String minute){
    	SimpleDateFormat inputFormat =new SimpleDateFormat("yyyy/MM/dd");
    	SimpleDateFormat timestampFormat =new SimpleDateFormat("yyyy-MM-dd");
    	try{
    		_date=Timestamp.valueOf(timestampFormat.format(inputFormat.parse(date))+" "+hour+":"+minute);

    	}catch(ParseException e){
    		inputFormat = new SimpleDateFormat("MM/dd");
    		timestampFormat =new SimpleDateFormat("MM-dd");
    		Calendar cal = Calendar.getInstance();
    		int year = cal.get(Calendar.YEAR);
    		try {
				_date=Timestamp.valueOf(year + timestampFormat.format(inputFormat.parse(date))+" "+hour+":"+minute);

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
