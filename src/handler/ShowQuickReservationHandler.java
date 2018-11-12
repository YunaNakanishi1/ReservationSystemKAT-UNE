package handler;

import static handler.ViewHolder.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dto.CategoryDto;
import dto.OfficeDto;
import dto.TimeDto;
import exception.MyException;


public class ShowQuickReservationHandler implements Handler {

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
			return ERROR_PAGE;
		}

		session.setAttribute("usageStartTimeForResourceSelect", usageStartTimeForResourceSelect);

		return QUIICK_RESERVATION;
	}

}
