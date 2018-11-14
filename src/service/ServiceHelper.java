package service;

import static java.util.Comparator.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dto.ReservationDto;
import dto.Resource;
import dto.TimeDto;

public class ServiceHelper {
    /**
     * 予約可能な時間帯をTimeDtoのリストで返す。
     *
     * @param resource 対象とするリソース
     * @param reservationList　予約リスト
     * @param usageDate　指定日
     * @return
     */
    public List<TimeDto> getAvailableListForResource(Resource resource, List<ReservationDto> reservationList, String usageDate){
        List<TimeDto> timeList = new ArrayList<>();

        for (ReservationDto reservation : reservationList) {
            //リソースが一致
            if(reservation.getResource().getResourceId() == resource.getResourceId()){
                //予約状況をリストにセット
                timeList.add(reservation.getUsageStartTime());
                timeList.add(reservation.getUsageEndTime());
            }
        }

      //利用停止期間がある(仮想的な予約としてリソース利用停止期間を計算する)
        if(resource.getUsageStopStartDate() != null && resource.getUsageStopEndDate() != null){
            TimeDto usageStopStartTime = convertUsageStopTimeToOneDay(resource.getUsageStopStartDate(), usageDate);
            TimeDto usageStopEndTime = convertUsageStopTimeToOneDay(resource.getUsageStopEndDate(), usageDate);
          //停止状況をリストにセット
            timeList.add(usageStopStartTime);
            timeList.add(usageStopEndTime);
        }


        //分に換算したときの小さい順にソート
        timeList.sort(comparing(TimeDto::getTimeMinutesValue));

        //リストの最初に0:0のTimeDto、最後に24:00のTimeDtoを追加する
        timeList.add(0, new TimeDto(0, 0));
        timeList.add(new TimeDto(24, 0));

        return timeList;
    }

    /**
     *  タイムスタンプの日付が指定日よりも前だったら指定日の最初。後だったら指定日の最後のTimeDtoを返す
     *  タイムスタンプの日付が当日だったら、その時分のTimeDtoを返す
     * @param convertTime　変換対象
     * @param date　指定日
     * @return　TimeDto
     */
    public TimeDto convertUsageStopTimeToOneDay(Timestamp convertTime, String date){
        TimeDto minTimeDto = new TimeDto(0, 0);
        TimeDto maxTimeDto = new TimeDto(24, 0);

        Timestamp minTimeStamp = minTimeDto.getTimeStamp(date);
        Timestamp maxTimeStamp = maxTimeDto.getTimeStamp(date);

        if(convertTime.before(minTimeStamp)){
            return minTimeDto;
        }
        else if(convertTime.after(maxTimeStamp)){
            return maxTimeDto;
        }
        else{
            return new TimeDto(convertTime);
        }
    }
}
