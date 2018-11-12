package handler;

import static handler.ViewHolder.*;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.CategoryDto;
import dto.OfficeDto;
import service.GetOfficeAndCategoryListService;


/**
 * @author z00h230741
 *
 */
public class ShowResourceSelectHandler implements Handler{
	private static Logger _log = LogManager.getLogger();

	@Override
	public String handleService(HttpServletRequest request){
		HttpSession session =request.getSession(true);
		HandlerHelper handlerHelper = new HandlerHelper();

		String categoryId = (String)session.getAttribute("categoryIdForResourceSelect");
		String officeId = (String)session.getAttribute("officeIdForResourceSelect");
		String facilityIdListForResourceSelect = (String)session.getAttribute("facilityIdListForResourceSelect");
		String selectedFacilityListForResourceSelect = (String)session.getAttribute("selectedFacilityListForResourceSelect");

		//事務所・カテゴリ一覧取得
		GetOfficeAndCategoryListService getOfficeAndCategoryListService = new GetOfficeAndCategoryListService();
		if(getOfficeAndCategoryListService.validate()){
			try{
				getOfficeAndCategoryListService.execute();

				List<CategoryDto> categoryList= getOfficeAndCategoryListService.getCategoryList();
				List<OfficeDto> officeNameList= getOfficeAndCategoryListService.getOfficeList();
			}catch(SQLException e){
			    _log.error("SQLException");
				return ERROR_PAGE;
			}

			return null; //←なおす

		}else{
			 _log.error("validateError");
			return ERROR_PAGE;
		}

		//GetResourceCharacteristicListService getResourceCharacteristicListService = new GetResourceCharacteristicListService();
		//if(getResourceCharacteristicListService.validate()){
		//	try{
		//		getResourceCharacteristicListService.execute();

		//		List<CategoryDto> categoryList= getOfficeAndCategoryListService.getCategoryList();
		//		List<OfficeDto> officeNameList= getOfficeAndCategoryListService.getOfficeList();
		//	}catch(SQLException e){
		//	    _log.error("SQLException");
		//		return ERROR_PAGE;
		//	}

		//	return null; //←なおす

		//}else{
		//	 _log.error("validateError");
		//	return ERROR_PAGE;
		}

		}

//}
