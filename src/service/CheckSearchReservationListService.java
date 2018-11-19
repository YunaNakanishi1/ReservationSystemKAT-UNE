package service;

import java.sql.SQLException;

import dto.TimeDto;
import exception.MyException;
import handler.MessageHolder;

/**
 * 予約一覧のバリデーションチェックのためのサービス. 31
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
public class CheckSearchReservationListService implements Service {

    private TimeDto _startTime;
    private TimeDto _endTime;
    private String _message;
    static int HOUR24_MINUTES = 24*60;


    public CheckSearchReservationListService(TimeDto startTime, TimeDto endTime) {

        if(startTime == null || endTime == null){
            throw new MyException();
        }
        this._startTime = startTime;
        this._endTime = endTime;
    }

    @Override
    public boolean validate() {
        if(_endTime.getTimeMinutesValue() < _startTime.getTimeMinutesValue()){
            _message = MessageHolder.EM10;
            return false;
        }

        if (_endTime.getTimeMinutesValue() == _startTime.getTimeMinutesValue()) {
        	_message = MessageHolder.EM49;
        	return false;
        }

        if(_startTime.getTimeMinutesValue() > HOUR24_MINUTES){
            _message = MessageHolder.EM51;
            return false;
        }

        if(_endTime.getTimeMinutesValue() > HOUR24_MINUTES){
            _message = MessageHolder.EM51;
            return false;
        }

        return true;
    }
    @Override
    public void execute() throws SQLException {

    }
    public String getMessage() {
        return _message;
    }
}
