package service;

import static java.util.Comparator.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
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

        _availableList = new ArrayList<AvailableDto>();

        ReservationDao rDao = new ReservationDao();
        Timestamp startTime = _usageStartTime.getTimeStamp(_usageDate)      ;
        Timestamp endTime = _usageEndTime.getTimeStamp(_usageDate);

        List<ReservationDto> reservationList = rDao.queryByResources(_resourceList, startTime, endTime);

        ServiceHelper sHelper = new ServiceHelper();

        //リソース毎に予約可能な時間を計算
        for (Resource resource : _resourceList) {
            List<TimeDto> reservableTimeList = sHelper.getAvailableListForResource(resource, reservationList, _usageDate);
            //結果の2つ毎に予約可能な時間が得られる
            for(int i=0;i<reservableTimeList.size();i+=2){

                TimeDto sTime = reservableTimeList.get(i);
                TimeDto eTime = reservableTimeList.get(i+1);
                int startMinutes = sTime.getTimeMinutesValue();
                int endMinutes = eTime.getTimeMinutesValue();

                System.out.println("SM:"+startMinutes);
                System.out.println("EM:"+endMinutes);
                System.out.println("USM:"+_usageStartTime.getTimeMinutesValue());
                System.out.println("UEM:"+_usageEndTime.getTimeMinutesValue());

                System.out.println();

                //予約可能な時間が予約したい時間よりも前
                if(endMinutes < _usageStartTime.getTimeMinutesValue()){
                    //追加しない
                    continue;
                }
                //予約可能な時間が予約したい時間よりも後
                if(startMinutes > _usageEndTime.getTimeMinutesValue()){
                  //追加しない
                    continue;
                }
                //予約可能な時間内に予約したい時間の開始時間があれば、予約可能な時間の開始を予約したい時間の開始時間にする
                if(startMinutes < _usageStartTime.getTimeMinutesValue() && _usageStartTime.getTimeMinutesValue() < endMinutes){
                    sTime = _usageStartTime;
                }
                //予約可能な時間内に予約したい時間の終了時間があれば、予約可能な時間の終了を予約したい時間の終了時間にする
                if(startMinutes < _usageEndTime.getTimeMinutesValue() && _usageEndTime.getTimeMinutesValue() < endMinutes){
                    eTime = _usageEndTime;
                }
                //利用可能時間が実利用時間を超えていたら
                if((endMinutes - startMinutes) >= _usageTime.getTimeMinutesValue()){
                    boolean hasSupplement = false;
                    if(resource.getSupplement() != null){
                    	if(resource.getSupplement().length() > 0){
                        	hasSupplement = true;
                    	}
                    }
                    AvailableDto available = new AvailableDto(resource.getResourceId(),resource.getResourceName(), sTime, eTime, resource.getCapacity(), resource.getOfficeName(), resource.getCategory(), hasSupplement);
                    _availableList.add(available);
                }
            }
        }
        //_availableListを利用可能開始時間の昇順、定員の昇順、事業所の昇順 カテゴリの昇順 リソースIDの昇順でソートする
        _availableList.sort(comparing((AvailableDto o) -> o.getStartResource().getTimeMinutesValue())
                            .thenComparing(AvailableDto::getCapacity)
                            .thenComparing(AvailableDto::getOfficeName)
                            .thenComparing(AvailableDto::getCategoryName)
                            .thenComparing(AvailableDto::getResourceId));
    }
    public List<AvailableDto> getAvailableList(){
        return _availableList;
    }

}
