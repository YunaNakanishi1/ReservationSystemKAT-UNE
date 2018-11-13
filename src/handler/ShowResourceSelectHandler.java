package handler;

import static handler.ViewHolder.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.CategoryDto;
import dto.FacilityDto;
import dto.OfficeDto;
import service.ContainSelectedCategoryService;
import service.ContainSelectedOfficeService;
import service.GetOfficeAndCategoryListService;
import service.GetResourceCharacteristicListService;


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
		List<String> facilityIdListForResourceSelect = (List<String>)session.getAttribute("facilityIdListForResourceSelect");

		//事務所・カテゴリ一覧取得
		GetOfficeAndCategoryListService getOfficeAndCategoryListService = new GetOfficeAndCategoryListService();
		//空のリストを用意
		List<CategoryDto> categoryList=new ArrayList<CategoryDto>();
		List<OfficeDto> officeList=new ArrayList<OfficeDto>();

		if(getOfficeAndCategoryListService.validate()){
			try{
				getOfficeAndCategoryListService.execute();

				categoryList= getOfficeAndCategoryListService.getCategoryList();
				officeList= getOfficeAndCategoryListService.getOfficeList();
			}catch(SQLException e){
			    _log.error("SQLException");
				return ERROR_PAGE;
			}


		}else{
			 _log.error("validateError");
			return ERROR_PAGE;
		}

		//リソース特性一覧取得
		GetResourceCharacteristicListService getResourceCharacteristicListService = new GetResourceCharacteristicListService();
		//空のリストを取得
		List<FacilityDto> facilityList=new ArrayList<FacilityDto>();


		if(getResourceCharacteristicListService.validate()){
			try{
				getResourceCharacteristicListService.execute();
				facilityList= getResourceCharacteristicListService.getFacilityList();
			}catch(SQLException e){
			    _log.error("SQLException");
				return ERROR_PAGE;
			}



		}else{
			 _log.error("validateError");
			return ERROR_PAGE;
		}

		//カテゴリ選択チェック取得
		ContainSelectedCategoryService containSelectedCategoryService = new ContainSelectedCategoryService(categoryList,categoryId);
		//boolean型の変数を用意
		boolean selectedCategory;
		if(containSelectedCategoryService.validate()){
			try{
				containSelectedCategoryService.execute();
				selectedCategory=containSelectedCategoryService.getResult();

			}catch(SQLException e){
					  _log.error("SQLException");
				return ERROR_PAGE;
			}

		}else{
				_log.error("validateError");
			return ERROR_PAGE;
		}

		//事業所選択チェック取得
		ContainSelectedOfficeService containSelectedOfficeService = new ContainSelectedOfficeService(officeList,officeId);
		//boolean型の変数を用意
		boolean selectedOffice;
			if(containSelectedOfficeService.validate()){
				try{
					containSelectedOfficeService.execute();
					selectedOffice=containSelectedOfficeService.getResult();

				}catch(SQLException e){
					_log.error("SQLException");
					return ERROR_PAGE;
				}

			}else{
				_log.error("validateError");
				return ERROR_PAGE;
			}

		//事業所選択チェック取得
		ContainSelectedCharacteristicService containSelectedCharacteristicService = new ContainSelectedCharacteristicService(facilityList,officeId);
		//boolean型の変数を用意
			boolean selectedOffice;
				if(containSelectedOfficeService.validate()){
					try{
						containSelectedOfficeService.execute();
						selectedOffice=containSelectedOfficeService.getResult();

					}catch(SQLException e){
						_log.error("SQLException");
						return ERROR_PAGE;
					}

				}else{
					_log.error("validateError");
					return ERROR_PAGE;
				}

		}

}
