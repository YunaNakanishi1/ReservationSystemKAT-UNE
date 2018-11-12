package handler;

import static handler.ViewHolder.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import service.GetOfficeAndCategoryListService;


/**
 * @author z00h230741
 *
 */
public class ShowResourceSelectHandler implements Handler{

	@Override
	public String handleService(HttpServletRequest request){
		HttpSession session =request.getSession(true);
		HandlerHelper handlerHelper = new HandlerHelper();

		String categoryId = (String)session.getAttribute("categoryIdForResourceSelect");
		String officeId = (String)session.getAttribute("officeIdForResourceSelect");
		String facilityIdListForResourceSelect = (String)session.getAttribute("facilityIdListForResourceSelect");
		String selectedFacilityListForResourceSelect = (String)session.getAttribute("selectedFacilityListForResourceSelect");

		GetOfficeAndCategoryListService getOfficeAndCategoryListService = new GetOfficeAndCategoryListService();
		if(getOfficeAndCategoryListService.validate()){
			return null; //←なおす

		}else{
			return ERROR_PAGE;
		}

		}

}
