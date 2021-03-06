
/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package handler;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exception.MyException;

/**
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 *         リソースの入力項目に対して、正しい入力形式であるかをチェックするためのクラス.
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
	    if(val==null){
	       return true;
	    }
		boolean notNumeric=false;
		Pattern numericPattern;
		numericPattern=Pattern.compile("^-?[0-9]+$");
		Matcher m = numericPattern.matcher(val);
		notNumeric = !m.find();

		if(!notNumeric){
		try {
			_number = Integer.parseInt(val);
		} catch (NumberFormatException e) {
			return true;
		}
		}

		return notNumeric;

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
	 * @return 渡されたデータが正しい場合、false 誤りがある場合、true
	 */
	protected boolean notDateOn(String date, String hour, String minute) {
    	/*【方針】
    	 1．入力された日付が「yyyy/MM/dd」形式でチェックした場合に正しいかをチェック
    	    正しい場合はtimestamp型に日付を変換する
    	 2．1．の形式が当てはまらない場合、「MM/dd」形式であるならば正しいかをチェック
    	    ※「MM/dd」形式では、入力された時の年を「yyyy」とする
		 */

		boolean notDate = false;
		Pattern datePattern;

    	/* 「yyyy/MM/dd」形式で正しい日付か	*/
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy/MM/dd");
		inputFormat.setLenient(false); // 日時解析を厳密に行う

		SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd");
		timestampFormat.setLenient(false); // 日時解析を厳密に行う

		datePattern = Pattern.compile("^[0-9]{4}/[0-9]{2}/[0-9]{2}$");
		Matcher m = datePattern.matcher(date);
		notDate = !m.find();

		/* 「yyyy/MM/dd」形式で正しい日付か */
		if (!notDate) {

			try {
				_date = Timestamp
						.valueOf(timestampFormat.format(inputFormat.parse(date)) + " " + hour + ":" + minute + ":00");
				notDate = false;
			} catch (ParseException e) {
				notDate = true;
			}
		}

		if (notDate) {
			/*
			 * // 「MM/dd」形式で正しい日付か inputFormat = new
			 * SimpleDateFormat("yyyy/MM/dd"); inputFormat.setLenient(false);
			 * //日時解析を厳密に行う
			 *
			 * //後ほどyyyyを補完して文字列連結するために頭に「-」をつけたフォーマットを用意 timestampFormat =new
			 * SimpleDateFormat("yyyy-MM-dd");
			 * timestampFormat.setLenient(false); //日時解析を厳密に行う
			 *
			 */

			datePattern = Pattern.compile("^[0-9]{2}/[0-9]{2}$");
			m = datePattern.matcher(date);
			notDate = !m.find();

			if (!notDate) {

				// 入力年を補完した状態の日付をフィールドに格納
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				try {
					_date = Timestamp.valueOf(timestampFormat.format(inputFormat.parse(year + "/" + date)) + " " + hour
							+ ":" + minute + ":00");
					notDate = false;

				} catch (ParseException e1) { // 入力した日付が正しくない場合
					notDate = true;
				}
			}
		}

		return notDate;

	}
	   /**
     * notLenientDateOnがfalseを返すとき、yyyy/mm/ddとして日付データがセットされる
     */
    private String _dateStr;
	/**
     * 引数のデータが正しい日付であるかを判断するメソッド.
     * 正しい日付の場合、falseを返却し、_dateフィールドにtimeStamp型でセットする.
     *
     * @param date ユーザから入力される年月日(例："2018/10/03"　"10/03")
     * @return 渡されたデータが正しい場合、false 誤りがある場合、true
     */
    protected boolean notLenientDateOn(String dateStr){
        boolean notDate = false;
        Pattern datePattern;

        //フォーマット確認「yyyy/M/d」か？
        datePattern = Pattern.compile("^[0-9]{4}/[0-9]{1,2}/[0-9]{1,2}$");
        Matcher m = datePattern.matcher(dateStr);
        notDate = !m.find();

        /* 「yyyy/M/d」形式でない */
        if(notDate){
            /*「M/d形式か？」*/
            datePattern = Pattern.compile("^[0-9]{1,2}/[0-9]{1,2}$");
            m = datePattern.matcher(dateStr);
            notDate = !m.find();
            if(!notDate){
                /*「M/d」形式であるのでyyyyを付け加える*/
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                dateStr = year + "/" + dateStr;
            }else{
                //yyyy/M/dでもM/dでもないので間違い
                return true;
            }
        }

        /* 「yyyy/MM/dd」形式で厳密に正しい日付か    */
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy/M/d");
        inputFormat.setLenient(false); // 日時解析を厳密に行う
        try{
            inputFormat.parse(dateStr);
            _dateStr = dateStr;
            return false;

        }catch (ParseException e) {
            return true;
        }
    }

    /**
     * 定員の入力が半角整数だった場合その値を返却する.
     * 定員の入力が無かった場合、0を返却.
     * それ以外の場合、MyException
     * @param capacity 定員（文字列）
     * @return 定員（数値）
     */
    protected int getCapacityValue(String capacity) {
    	if(!notSetOn(capacity)) {
    		if(!notNumericOn(capacity)) {
    			int capacityInt = Integer.parseInt(capacity);

    			return capacityInt;
    		} else {
    			throw new MyException();
    		}
    	} else {
    		return 0;
    	}

    }

    protected String getDateStr() {
        return _dateStr;
    }
	protected Timestamp getDate() {
		return _date;
	}

}
