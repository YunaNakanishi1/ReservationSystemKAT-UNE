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
import dto.ReservationDto;
import dto.TimeDto;
import service.GetOfficeAndCategoryListService;
import service.GetResourceCharacteristicListService;

public class PushCopyReservationButtonHandler implements Handler{

    private Logger _log = LogManager.getLogger();

	@Override
	public String handleService(HttpServletRequest request) {

		HttpSession session =request.getSession(true);
		//コピー元からの予約情報（reservationDto）をsessionから取得
		ReservationDto reservationDto = (ReservationDto) session.getAttribute("reservationDTOForReservationDetails");


		//各値を初期化し、セッションに保存する
		HandlerHelper handlerHelper=new HandlerHelper();
		handlerHelper.initializeAttributeForReservationRegist(session);


		//リクエストからreservationIdをString型で受け取る
			String reservationIdForReservationDetailsStr = request.getParameter("reservationIdForReservationDetails");

			int reservationIdForReservationDetails;

			//reservationIdをint型になおす
			try{
				reservationIdForReservationDetails = Integer.parseInt(reservationIdForReservationDetailsStr);
			}catch(NumberFormatException e){
				_log.error("NumberFormatException");
				return ERROR_PAGE;
			}

		//sessionに戻るボタンを押下した際の行き先（予約詳細画面）を保存
		//コピー元の予約IDも保存
			session.setAttribute("returnPageForResourceSelect", SHOW_RESERVATION_DETAILS_SERVLET);
			session.setAttribute("reservationIdForReservationDetails", reservationIdForReservationDetails);

			session.setAttribute("usageDateForReservationRegist",reservationDto.getUsageDate());
			System.out.println(reservationDto.getUsageDate());
			session.setAttribute("usageStartTimeForResourceSelect",reservationDto.getUsageStartTime());
			session.setAttribute("usageEndTimeForResourceSelect",reservationDto.getUsageEndTime());
			session.setAttribute("resourceNameForResourceSelect",reservationDto.getResource().getResourceName());
			session.setAttribute("capacityForResourceSelect",reservationDto.getResource().getCapacity());
			session.setAttribute("reservationNameForReservationRegist",reservationDto.getReservationName());
			session.setAttribute("numberOfParticipantsForReservationRegist",reservationDto.getNumberOfParticipants());
			session.setAttribute("coReservedPersonIdForReservationRegist",reservationDto.getCoReservedPerson().getUserId());
			session.setAttribute("attendanceTypeIdForReservationRegist",reservationDto.getAttendanceTypeDto().getAttendanceTypeId());
			session.setAttribute("reserveSupplementForReservationRegist",reservationDto.getSupplement());

		//終了時間ー開始時間で実利用時間を計算する
			//①予約情報から終了時間、開始時間をTimedto型で取得する
			TimeDto usageEndTime=reservationDto.getUsageEndTime();
			TimeDto usageStartTime=reservationDto.getUsageStartTime();

			//②終了時間、開始時間を分に変換する
			int usageEndTimeMinutes=usageEndTime.getTimeMinutesValue();
			int usageStartTimeMinutes=usageStartTime.getTimeMinutesValue();

			//③予約の実利用時間を計算する
			int usageTimeMinutes=usageEndTimeMinutes-usageStartTimeMinutes;

			//④分をTimedto型に変換する
			TimeDto usageTime =new TimeDto(usageTimeMinutes);

		//実利用時間をsessionにセット
			session.setAttribute("usageTimeForReservationSelect",usageTime);



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


			//全件取得したCategoryDtoのリストから予約したカテゴリの名前が一致するcategorydtoを作成する
			CategoryDto containCategory = null;
			for(CategoryDto category:categoryList){
				if(category.getCategoryName().equals(reservationDto.getResource().getCategory())){
					containCategory=new CategoryDto(category.getCategoryId(),category.getCategoryName());
				}
			}

			//取得できなかった場合はエラーページに飛ばす
			if(containCategory==null){
				 _log.error("containCategoryIsNull");
				return ERROR_PAGE;
			}

			//全件取得したOfficeDtoのリストから予約した事業所の名前が一致するOfficedtoを作成する
			OfficeDto containOffice=null;
			for(OfficeDto office:officeList){
				if(office.getOfficeName().equals(reservationDto.getResource().getOfficeName())){
					containOffice=new OfficeDto(office.getOfficeId(),office.getOfficeName());
				}
			}

			//取得できなかった場合はエラーページに飛ばす
			if(containOffice==null){
				 _log.error("containContailIsNull");
				return ERROR_PAGE;
			}

			//全件取得したFacilityDtoのリストから予約した設備の名前が一致するfacilitydtoのリストを作成する
			List<String> containFacilityIdList = new ArrayList<String>();
			for(FacilityDto facility:facilityList){
				for(String facilityName:reservationDto.getResource().getFacility()){
					if(facility.getFacilityName().equals(facilityName)){
						containFacilityIdList.add(facility.getFacilityId());
					}
				}
			

			}
			
			System.out.println(containFacilityIdList.size());
			for(:containFacilityIdList){
				
			}

			//セッションにカテゴリID,事業所ID,設備IDのリストをセット
			session.setAttribute("categoryIdForResourceSelect",containCategory.getCategoryId());
			session.setAttribute("officeIdForResourceSelect",containOffice.getOfficeId());
			session.setAttribute("facilityIdListForResourceSelect",containFacilityIdList);
			return SEARCH_RESOURCE_LIST_SERVLET;





	}

}
