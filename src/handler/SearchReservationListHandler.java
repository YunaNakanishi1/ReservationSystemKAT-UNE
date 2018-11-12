package handler;

import static handler.ViewHolder.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

		return null;
	}

	private boolean search(){
		return true;
	}

}
