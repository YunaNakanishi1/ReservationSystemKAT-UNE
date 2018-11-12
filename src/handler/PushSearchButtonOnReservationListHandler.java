package handler;

import static handler.ViewHolder.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        String categoryId = request.getParameter("categoryId");
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
        
        
        return null;
    }

}
