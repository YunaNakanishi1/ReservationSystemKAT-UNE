package handler;

import static handler.ViewHolder.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.TimeDto;
import exception.MyException;
import service.CheckSearchReservationListService;

public class PushSearchButtonOnReservationListHandler implements Handler{
    private static Logger _log = LogManager.getLogger();

    @Override
    public String handleService(HttpServletRequest request) {
        HttpSession session = request.getSession(true);

        CommonValidator commonValidator = new CommonValidator();

        //入力項目の取得
        String dateStr = request.getParameter("usageDate");
        String startHourStr = request.getParameter("usageStartHour");
        String startMinutesStr = request.getParameter("usageStartMinutes");
        String endHourStr = request.getParameter("usageEndHour");
        String endMinutesStr = request.getParameter("usageEndMinutes");
        String officeIdStr = request.getParameter("officeId");
        String categoryIdStr = request.getParameter("categoryId");
        boolean displayOnlyMyReservationStr =  !commonValidator.notSetOn(request.getParameter("displayOnlyMyReservation"));
        boolean displayPastReservationStr = !commonValidator.notSetOn(request.getParameter("displayPastReservation"));
        boolean displayDeletedReservationStr = !commonValidator.notSetOn(request.getParameter("displayDeletedReservation"));

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
            //バリデーションNG時の処理
            boolean setComplete = setCategoryAndOffice(request, categoryIdStr, officeIdStr);
            if(setComplete){
                session.setAttribute("messageForReservationListUpper", MessageHolder.EM42);
                return RESERVE_LIST;
            }
            else{
                _log.error("NumberFormatException");
                return ERROR_PAGE;
            }
        }
        CheckSearchReservationListService cService;
        try{
            cService = new CheckSearchReservationListService(startTime, endTime);
        }catch (MyException e) {
            _log.error("ConstructorError");
            return ERROR_PAGE;
        }

        if(cService.validate() == false){
            //バリデーションNG時の処理
            boolean setComplete = setCategoryAndOffice(request, categoryIdStr, officeIdStr);
            if(setComplete){
                session.setAttribute("messageForReservationListUpper", MessageHolder.EM42);
                return RESERVE_LIST;
            }
            else{
                _log.error("NumberFormatException");
                return ERROR_PAGE;
            }
        }



        return SEARCH_RESERVATION_LIST_SERVLET;
    }
    /**
     * プルダウン表示用にカテゴリと事業所の一覧を取得する
     * @param request
     * @param categoryId
     * @param officeId
     * @return
     */
    private boolean setCategoryAndOffice(HttpServletRequest request,String categoryId,String officeId){
        HandlerHelper helper = new HandlerHelper();
        boolean completed = helper.getOfficeAndCategory(officeId, categoryId);

        if(completed){
            request.setAttribute("categoryListForReservationList", helper.getCategoryList());
            request.setAttribute("officeListForReservationList", helper.getOfficeList());
            return true;
        }else{
            return false;
        }
    }
}
