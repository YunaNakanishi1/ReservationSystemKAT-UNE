package handler;

import static handler.ViewHolder.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.TimeDto;

public class PushSearchButtonOnReservationListHandler implements Handler{
    private static Logger _log = LogManager.getLogger();

    @Override
    public String handleService(HttpServletRequest request) {
        HttpSession session = request.getSession(true);

        //入力項目の取得
        String dateStr = request.getParameter("date");
        String startHourStr = request.getParameter("startHour");
        String startMinutesStr = request.getParameter("startMinutes");
        String endHourStr = request.getParameter("endHour");
        String endMinutesStr = request.getParameter("endMinutes");
        String officeIdStr = request.getParameter("officeId");
        String categoryIdStr = request.getParameter("categoryId");
        String displayOnlyMyReservationStr = request.getParameter("displayOnlyMyReservation");
        String displayPastReservationStr = request.getParameter("displayPastReservation");
        String displayDeletedReservationStr = request.getParameter("displayDeletedReservation");

        //TimeDto生成のための変数
        int startHour;
        int startMinutes;
        int endHour;
        int endMinutes;

        //変換処理
        try{
            startHour = Integer.parseInt(startHourStr);
            startMinutes = Integer.parseInt(startMinutesStr);
            endHour = Integer.parseInt(endHourStr);
            endMinutes = Integer.parseInt(endMinutesStr);
        }catch (NumberFormatException e) {
            _log.error("NumberFormatException");
            return ERROR_PAGE;
        }

        TimeDto startTime = new TimeDto(startHour, startMinutes);
        TimeDto endTime = new TimeDto(endHour, endMinutes);

        //再表示用にセッションにセット
        session.setAttribute("IDcategoryIdForReservationList", categoryIdStr);
        session.setAttribute("IDofficeIdForReservationList", officeIdStr);
        session.setAttribute("usageDateForReservationList", dateStr);
        session.setAttribute("usageStartHourForReservationList", startTime);
        session.setAttribute("usageEndHourForResourceSelect", endTime);
        session.setAttribute("displayOnlyMyReservation", displayOnlyMyReservationStr);
        session.setAttribute("displayPastReservation", displayPastReservationStr);
        session.setAttribute("displayDeletedReservation", displayDeletedReservationStr);

        //入力された日付が正しいか
        CommonValidator cValidator = new CommonValidator();
        boolean notCorrectDate = cValidator.notLenientDateOn(dateStr);
        if(notCorrectDate){
            boolean setComplete = setCategoryAndOffice(request, categoryIdStr, officeIdStr);
            if(setComplete){
                session.setAttribute("messageForReservationListUpper", "MessageHolder.EM42");
                return RESERVE_LIST;
            }
            else{
                _log.error("NumberFormatException");
                return ERROR_PAGE;
            }
        }
        
        
        return null;
    }
    private boolean setCategoryAndOffice(HttpServletRequest request,String categoryId,String officeId){
        //後で定義
        return false;
    }
}
