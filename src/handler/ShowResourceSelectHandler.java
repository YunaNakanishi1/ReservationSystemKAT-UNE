package handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dto.CategoryDto;
import dto.OfficeDto;
import exception.MyException;


public class ShowResourceSelectHandler implements Handler{

	@Override
	public String handleService(HttpServletRequest request) {
		HttpSession session =request.getSession(true);
		HandlerHelper handlerHelper = new HandlerHelper();

		String categoryId = (String)session.getAttribute("categoryIdForResourceSelect");
		String officeId = (String)session.getAttribute("officeIdForResourceSelect");
		String facilityIdListForResourceSelect = (String)session.getAttribute("facilityIdListForResourceSelect");
		String selectedFacilityListForResourceSelect = (String)session.getAttribute("selectedFacilityListForResourceSelect");



}
