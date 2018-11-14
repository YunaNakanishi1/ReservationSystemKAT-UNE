package handler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.AttendanceTypeDto;
import dto.CategoryDto;
import dto.OfficeDto;
import dto.TimeDto;
import dto.User;
import exception.MyException;
import service.ContainSelectedCategoryService;
import service.ContainSelectedOfficeService;
import service.GetOfficeAndCategoryListService;

public class HandlerHelper {

	private static Logger _log = LogManager.getLogger();
	private List<User> _userList;
	private List<AttendanceTypeDto> _attendanceTypeList;

	/**
	 * sessionに（属性名、初期値）の形でセット
	 *
	 * @param session
	 * @author リコーITソリューションズ株式会社 KAT-UNE
	 */
	public static void initializeAttributeForReservationRegist(HttpSession session) {
		session.setAttribute("usageStartTimeForReservationChange", null);
		session.setAttribute("usageEndTimeForReservationChange", null);
		session.setAttribute("reservationNameForReservationChange", null);
		session.setAttribute("numberOfParticipantsForReservationChange", 0);
		session.setAttribute("coReservedPersonIdForReservationChange", null);
		session.setAttribute("attendanceTypeIdForReservationChange", null);
		session.setAttribute("reserveSupplementForReservationChange", null);
		session.setAttribute("reservationIdForReservationDetails", null);
		session.setAttribute("usageDateForReservationRegist", null);
		session.setAttribute("usageStartTimeForResourceSelect", null);
		session.setAttribute("usageEndTimeForResourceSelect", null);
		session.setAttribute("usageTimeForReservationSelect", null);
		session.setAttribute("resourceNameForResourceSelect", null);
		session.setAttribute("categoryIdForResourceSelect", null);
		session.setAttribute("officeIdForResourceSelect", null);
		session.setAttribute("facilityIdListForResourceSelect", null);
		session.setAttribute("selectedFacilityListForResourceSelect", null);
		session.setAttribute("capacityForResourceSelect", 0);
		session.setAttribute("displayCapacityForResourceSelect", null);
		session.setAttribute("reservationNameForReservationRegist", null);
		session.setAttribute("numberOfParticipantsForReservationRegist", 0);
		session.setAttribute("displayNumberOfParticipantsForReservationRegist", null);
		session.setAttribute("coReservedPersonIdForReservationRegist", null);
		session.setAttribute("attendanceTypeIdForReservationRegist", null);
		session.setAttribute("reserveSupplementForReservationRegist", null);
		session.setAttribute("returnPageForResourceSelect", null);
		session.setAttribute("reservationListForResourceDeleteConfirm", null);
		session.setAttribute("resourceNameForReservationRegist", null);
		session.setAttribute("resourceIdForReservationRegist", null);
		session.setAttribute("reservationDTOForReservationChange", null);
		session.setAttribute("userListForReservationRegist", null);
		session.setAttribute("attendanceTypeListForReservationRegist", null);
		session.setAttribute("reservationListForSuspensionUseConfirm", null);
		session.setAttribute("categoryIdForReservationList", null);
		session.setAttribute("officeIdForReservationList", null);
		session.setAttribute("usageDateForReservationList", null);
		session.setAttribute("usageStartHourForReservationList", null);
		session.setAttribute("usageEndHourForResourceSelect", null);
		session.setAttribute("displayOnlyMyReservation", null);
		session.setAttribute("displayPastReservation", null);
		session.setAttribute("displayDeletedReservation", null);
		session.setAttribute("usageStartHourForReservationRegist", null);
		session.setAttribute("usageEndHourForReservationRegist", null);
		session.setAttribute("reservableListForResourceSelect", null);
		session.setAttribute("reservationListForReservationList", null);
		session.setAttribute("categoryListForResourceSelect", null);
		session.setAttribute("officeListForResourceSelect", null);
		session.setAttribute("usableStartTimeForReservationRegist", null);
		session.setAttribute("usableEndTimeForReservationRegist", null);
		session.setAttribute("usableStartTimeForReservationChange", null);
		session.setAttribute("usableEndTimeForReservationChange", null);
		session.setAttribute("userListForReservationChange", null);
		session.setAttribute("attendanceTypeListForReservationChange", null);
		session.setAttribute("linkToResourceDetails", null);
		session.setAttribute("reservationDTOForReservationDetails", null);
		session.setAttribute("categoryListForReservationList", null);
		session.setAttribute("officeListForReservationList", null);
		session.setAttribute("messageForReservationListLower", null);
		session.setAttribute("messageForReservationListUpper", null);
		session.setAttribute("messageForResourceSelectLower", null);
		session.setAttribute("messageForResourceSelectUpper", null);
		session.setAttribute("messageForQuickReservation", null);
		session.setAttribute("messageForReservationChange", null);
		session.setAttribute("messageForResourceDeleteConfirm", null);
		session.setAttribute("messageForSuspensionUseConfirm", null);
		session.setAttribute("messageForReservationRegist", null);
		session.setAttribute("resourceDTOForResourceDetailsTab", null);
		session.setAttribute("facilityListForResourceSelect", null);

	}

	/**
	 * 今すぐ予約の現在時刻を取得するメソッド
	 *
	 * @return usageStartTimeForResourceSelect 今すぐ予約時間
	 */
	public TimeDto getUsageStartTime() throws MyException {

		Date currentTime = new Date();
		TimeDto currentTimeDto = new TimeDto(currentTime);

		int hour = currentTimeDto.getHour();
		int minutes = currentTimeDto.getMinutes();

		// 利用開始時間の設定
		int usageStartMinutes = 0;
		if (0 <= minutes && minutes < 15) {
			usageStartMinutes = 0;
		} else if (15 <= minutes && minutes < 30) {
			usageStartMinutes = 15;
		} else if (30 <= minutes && minutes < 45) {
			usageStartMinutes = 30;
		} else if (45 <= minutes && minutes < 60) {
			usageStartMinutes = 45;
		}

		TimeDto usageStartTimeForResourceSelect = new TimeDto(hour, usageStartMinutes);
		return usageStartTimeForResourceSelect;

	}

	private List<OfficeDto> _officeList;
	private List<CategoryDto> _categoryList;

	/**
	 * 全カテゴリ事業所の取得、および引数のIDを持つものが含まれているかを調べる. H6
	 *
	 * @param officeId 事業所ID
	 * @param categoryId カテゴリID
	 * @return 正常に処理が終了すればtrue
	 */
	public boolean getOfficeAndCategory(String officeId, String categoryId) {
		//カテゴリと事業所の一覧の取得
		List<OfficeDto> officeList=new ArrayList<OfficeDto>();
		List<CategoryDto>categoryList=new ArrayList<CategoryDto>();
		GetOfficeAndCategoryListService getOfficeAndCategoryService = new GetOfficeAndCategoryListService();
		if (getOfficeAndCategoryService.validate()) {
			try {
				getOfficeAndCategoryService.execute();
				officeList = getOfficeAndCategoryService.getOfficeList();
				categoryList = getOfficeAndCategoryService.getCategoryList();
			} catch (SQLException e) {
				_log.error("getOfficeAndCategoryError");
				return false;
			}
		} else {
			_log.error("validationError");
			return false;
		}

		//選択されているカテゴリが一覧にあるか調べる
		try {
			ContainSelectedCategoryService containSelectedCategoryService =new ContainSelectedCategoryService(categoryList, categoryId);
			if(containSelectedCategoryService.validate()){
				containSelectedCategoryService.execute();

				if(!containSelectedCategoryService.getResult()){
					_log.error("selectedCategoryIsNotFound");
					return false;
				}
			}else{
				_log.error("validationError");
				return false;
			}
		} catch (MyException e) {
			_log.error("categoryListIsNull");
			return false;
		}

		//選択されている事業所が一覧にあるか調べる
		try {
			ContainSelectedOfficeService containSelectedOfficeService =new ContainSelectedOfficeService(officeList, officeId);

			if(containSelectedOfficeService.validate()){
				containSelectedOfficeService.execute();
				if(!containSelectedOfficeService.getResult()){
					_log.error("selectedOfficeIsNotFound");
					return false;
				}
			}else{
				_log.error("validationError");
				return false;
			}
		} catch (MyException e) {
			_log.error("officeListIsNull");
			return false;
		}
		_categoryList=categoryList;
		_officeList=officeList;

		return true;
	}

	public List<OfficeDto> getOfficeList() {
		return _officeList;
	}

	public List<CategoryDto> getCategoryList() {
		return _categoryList;
	}


	/**
	 * @param userIs
	 * @param attendanceTypeId
	 * @return
	 */
	public boolean getUserAndAttendanceType(String userIs,String attendanceTypeId){
		return false;

	}

	public List<User> getUserList(){
		return _userList;

	}

	public List<AttendanceTypeDto> getAttendanceTypeList(){
		return _attendanceTypeList;

	}
}
