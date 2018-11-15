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
import service.ContainSelectedResourceCharacteristicService;
import service.GetOfficeAndCategoryListService;
import service.GetResourceCharacteristicListService;


/**
 * サーブレット番号：36
 * リソース選択画面を表示するための情報をセット.
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
public class ShowResourceSelectHandler implements Handler{
	private static Logger _log = LogManager.getLogger();

	@Override
	public String handleService(HttpServletRequest request){
		HttpSession session =request.getSession(true);

		//セッションから予約一覧画面で選択されたカテゴリID、事務所ID、設備IDを取得する
		//設備は複数選択可なのでList<String>型
		String categoryIdForResourceSelect = (String)session.getAttribute("ForResourceSelect");
		String officeIdForResourceSelect = (String)session.getAttribute("officeListForResourceSelectForResourceSelect");
		List<String> facilityIdListForResourceSelect = (List<String>)session.getAttribute("facilityIdListForResourceSelect");


		GetOfficeAndCategoryListService getOfficeAndCategoryListService = new GetOfficeAndCategoryListService();
		//空のリストを用意
		List<CategoryDto> categoryList=new ArrayList<CategoryDto>();
		List<OfficeDto> officeList=new ArrayList<OfficeDto>();

		//事務所・カテゴリ一覧取得する
		if(getOfficeAndCategoryListService.validate()){
			try{
				getOfficeAndCategoryListService.execute();

				categoryList= getOfficeAndCategoryListService.getCategoryList();
				officeList= getOfficeAndCategoryListService.getOfficeList();
			}catch(SQLException e){
			    _log.error("SQLException1");
				return ERROR_PAGE;
			}


		}else{
			 _log.error("validateError");
			return ERROR_PAGE;
		}


		GetResourceCharacteristicListService getResourceCharacteristicListService = new GetResourceCharacteristicListService();
		//空のリストを取得
		List<FacilityDto> facilityList=new ArrayList<FacilityDto>();

		//リソース特性一覧取得
		if(getResourceCharacteristicListService.validate()){
			try{
				getResourceCharacteristicListService.execute();
				facilityList= getResourceCharacteristicListService.getFacilityList();
			}catch(SQLException e){
			    _log.error("SQLException2");
				return ERROR_PAGE;
			}
		}else{
			 _log.error("validateError");
			return ERROR_PAGE;
		}


		ContainSelectedCategoryService containSelectedCategoryService = new ContainSelectedCategoryService(categoryList,categoryIdForResourceSelect);
		//boolean型の変数を用意
		boolean selectedCategory;


		//選択したカテゴリがデータベースから削除されてないか確認する

		if(containSelectedCategoryService.validate()){

				containSelectedCategoryService.execute();
				selectedCategory=containSelectedCategoryService.getResult();

			if(selectedCategory==false){
				_log.error("NoselectedCategory");
				return ERROR_PAGE;
			}
		}else{
			_log.error("validateError");
			return ERROR_PAGE;
		}

		//選択した事業所がデータベースから削除されてないか確認する
		ContainSelectedOfficeService containSelectedOfficeService = new ContainSelectedOfficeService(officeList,officeIdForResourceSelect);
		//boolean型の変数を用意
		boolean selectedOffice;
			if(containSelectedOfficeService.validate()){
				containSelectedOfficeService.execute();
				selectedOffice=containSelectedOfficeService.getResult();

				if(selectedOffice==false){
					_log.error("NoselectedOffice");
					return ERROR_PAGE;
				}


			}else{
				_log.error("validateError");
				return ERROR_PAGE;
			}

		//選択したリソース特性がデータベースから削除されてないか確認する
		ContainSelectedResourceCharacteristicService containSelectedResourceCharacteristicService = new ContainSelectedResourceCharacteristicService(facilityIdListForResourceSelect,facilityList);
		//boolean型の変数を用意

		boolean selectedResourceCharacteristic;
			if(containSelectedResourceCharacteristicService.validate()){

					containSelectedResourceCharacteristicService.execute();
					selectedResourceCharacteristic=containSelectedResourceCharacteristicService.getResult();

					if(selectedResourceCharacteristic==false){
						_log.error("NoselectedCharacteristic");
						return ERROR_PAGE;
					}

				}else{
					_log.error("validateError");
					return ERROR_PAGE;
				}

		//requestに各値をセット
				request.setAttribute("categoryListForResourceSelect",categoryList);
				request.setAttribute("officeListForResourceSelect",officeList);
				request.setAttribute("facilityListForResourceSelect", facilityList);

				return RESOURCE_SELECT;

		}

}
