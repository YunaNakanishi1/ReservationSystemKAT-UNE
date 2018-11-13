package handler;

import static handler.ViewHolder.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.CategoryDto;
import dto.OfficeDto;
import dto.TimeDto;
import exception.MyException;

/**
 *
 * サーブレット番号：24
 * 今すぐ予約画面を表示するための情報をセット.
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
public class ShowQuickReservationHandler implements Handler {
	private Logger _log = LogManager.getLogger();

	@Override
	public String handleService(HttpServletRequest request) {
		HttpSession session =request.getSession(true);
		HandlerHelper handlerHelper = new HandlerHelper();

		String categoryId = (String)session.getAttribute("categoryIdForResourceSelect");
		String officeId = (String)session.getAttribute("officeIdForResourceSelect");

		boolean hasOfficeAndCategory = handlerHelper.getOfficeAndCategory(categoryId, officeId);
		if (hasOfficeAndCategory) {
			List<CategoryDto> categoryList = handlerHelper.getCategoryList();
			request.setAttribute("categoryListForResourceSelect", categoryList);
			List<OfficeDto> officeList = handlerHelper.getOfficeList();
			request.setAttribute("officeListForResourceSelect", officeList);
		} else {
			return ERROR_PAGE;
		}

		TimeDto usageStartTimeForResourceSelect = null;
		try {
			usageStartTimeForResourceSelect = handlerHelper.getUsageStartTime();
		} catch (MyException e) {
			_log.error("format error");
			return ERROR_PAGE;
		}

		session.setAttribute("usageStartTimeForResourceSelect", usageStartTimeForResourceSelect);

		return QUIICK_RESERVATION;
	}

}
