package service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import dao.ReservationDao;
import dto.AvailableDto;
import dto.ReservationDto;
import dto.Resource;
import dto.TimeDto;
import exception.MyException;

public class MakeAvailableListService implements Service{

    private List<Resource> _resourceList;
    private TimeDto _usageStartTime;
    private TimeDto _usageEndTime;
    private TimeDto _usageTime;
    private String _usageDate;
    private List<AvailableDto> _availableList;


    public MakeAvailableListService(List<Resource> _resourceList, TimeDto _usageStartTime, TimeDto _usageEndTime, TimeDto _usageTime, String _usageDate) {
        if(_resourceList == null || _usageStartTime == null || _usageEndTime == null || _usageTime == null || _usageDate == null){
            throw new MyException();
        }
        this._resourceList = _resourceList;
        this._usageStartTime = _usageStartTime;
        this._usageEndTime = _usageEndTime;
        this._usageTime = _usageTime;
        this._usageDate = _usageDate;
    }

    @Override
    public boolean validate() {
        //常にtrueを返す
        return true;
    }

    @Override
    public void execute() throws SQLException {
        ReservationDao rDao = new ReservationDao();
        Timestamp startTime = _usageStartTime.getTimeStamp(_usageDate);
        Timestamp endTime = _usageEndTime.getTimeStamp(_usageDate);

        List<ReservationDto> reservationList = rDao.queryByResources(_resourceList, startTime, endTime);

    }
    public List<AvailableDto> getAvailableList(){
        return _availableList;
    }

}
