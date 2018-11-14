package handler;

import static handler.MessageHolder.*;
import static handler.ViewHolder.*;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.ReservationDto;
import dto.TimeDto;
import exception.MyException;
import service.SearchReservationListService;

/**
 * 予約の検索、プルダウンの項目の取得を行う.
 *
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
public class SearchReservationListHandler implements Handler {
	private HttpServletRequest _request;
	private HttpSession _session;

	private static Logger _log = LogManager.getLogger();

	@Override
	public String handleService(HttpServletRequest request) {
		_session=request.getSession(true);
		_request=request;
		HandlerHelper handlerHelper = new HandlerHelper();

		boolean result=handlerHelper.getOfficeAndCategory((String)_session.getAttribute("officeIdForReservationList"), (String)_session.getAttribute("categoryIdForReservationList"));
		if(result){
			_request.setAttribute("categoryListForReservationList", handlerHelper.getCategoryList());
			_request.setAttribute("officeListForReservationList", handlerHelper.getOfficeList());
		}else{
			return ERROR_PAGE;
		}

		if(!search()){
			return ERROR_PAGE;
		}

		return RESERVE_LIST;
	}

	/**予約検索を行う内部メソッド.
	 * @return 正常に処理が終了したか
	 */
	private boolean search(){
		String officeId=(String)_session.getAttribute("officeIdForReservationList");
		String categoryId=(String)_session.getAttribute("categoryIdForReservationList");
		String usageDate=(String)_session.getAttribute("usageDateForReservationList");
		TimeDto usageStartTime=(TimeDto)_session.getAttribute("usageStartTimeForReservationList");
		TimeDto usageEndTime=(TimeDto)_session.getAttribute("usageEndTimeForReservationList");
		String userId=(String)_session.getAttribute("userIdOfLoggedIn");
		boolean onlyMyReservation=(boolean)_session.getAttribute("displayOnlyMyReservation");
		boolean pastReservation=(boolean)_session.getAttribute("displayPastReservation");
		boolean deletedReservation=(boolean)_session.getAttribute("displayDeletedReservation");

		try {
			SearchReservationListService searchReservationListService = new SearchReservationListService(officeId, categoryId, usageDate, usageStartTime, usageEndTime, userId, onlyMyReservation, pastReservation, deletedReservation);

			if(searchReservationListService.validate()){
				searchReservationListService.execute();
				List<ReservationDto> reservationList=searchReservationListService.getReservationList();
				if(reservationList==null){
					_log.error("reservationList is null");
					return false;
				}else if(reservationList.size()==0){
					_session.setAttribute("messageForReservationListLower", EM08);
				}else{
					_session.setAttribute("messageForReservationListLower", null);
				}

			_session.setAttribute("reservationListForReservationList", reservationList);
			_session.setAttribute("reservationListSizeForReservationList", reservationList.size());
			}

		} catch (MyException e) {
			_log.error("input error");
			return false;
		} catch (SQLException e) {
			_log.error("database error");
			e.printStackTrace();
			return false;
		}

		return true;
	}

}
