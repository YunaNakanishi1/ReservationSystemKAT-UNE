package service;

import java.sql.SQLException;

import dto.TimeDto;

/**
 * 予約一覧のバリデーションチェックのためのサービス
 * @author リコーITソリューションズ株式会社 z00s600124
 *
 */
public class CheckSearchReservationListService implements Service {

    private TimeDto _startTime;
    private TimeDto _endTime;


    public CheckSearchReservationListService(TimeDto startTime, TimeDto endTime) {
        this._startTime = startTime;
        this._endTime = endTime;
    }

    @Override
    public boolean validate() {

        return false;
    }
    @Override
    public void execute() throws SQLException {

    }
}
