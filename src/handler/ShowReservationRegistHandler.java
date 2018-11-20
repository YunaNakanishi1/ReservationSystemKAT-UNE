/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package handler;

import static handler.ViewHolder.*;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.AttendanceTypeDto;
import dto.Resource;
import dto.TimeDto;
import dto.User;
import service.GetResouceFromIdService;


/**
 * (8).
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
public class ShowReservationRegistHandler {

	private HttpServletRequest _request;
	private HttpSession _session;
	private static Logger _log = LogManager.getLogger();


	public String handleService(HttpServletRequest request){

		// セッションを取得
		_session = request.getSession(false);


/*
		//日付を表示するために変換
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");
		String date = (String) _session.getAttribute("usageDateForReservationRegist");
		try {
			Date day = sdf.parse(date);
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年M月d日");
			_session.setAttribute("usageDateForReservationRegist", sdf2.format(day));
		} catch (ParseException e) {
			e.printStackTrace();
			_log.error("ParseException");
			return ERROR_PAGE;
		}
*/

		_request = request;

		if(setResource() == false){
			_log.error("handleService_setResource()==false");
			return ERROR_PAGE;
		}

		String coReservedPersonId
		= (String) _session.getAttribute("coReservedPersonIdForReservationRegist");//共同予約者ID
		int attendanceTypeIdInt=-1;
		if(_session.getAttribute("attendanceTypeIdForReservationRegist")!=null){
		attendanceTypeIdInt= (int) _session.getAttribute("attendanceTypeIdForReservationRegist");//参加者種別ID
		}

		HandlerHelper handlerHelper = new HandlerHelper();
		if(handlerHelper.getUserAndAttendanceType(coReservedPersonId, attendanceTypeIdInt)){
			List<User> userList = handlerHelper.getUserList();
			List<AttendanceTypeDto> attendanceTypeList = handlerHelper.getAttendanceTypeList();

			request.setAttribute("userListForReservationRegist", userList);
			request.setAttribute("attendanceTypeListForReservationRegist", attendanceTypeList);

		}else{
			_log.error("getUserAndAttendanceType()==false");
			return ERROR_PAGE;
		}

		setSlider();

		return RESERVATION_REGIST;

	}


	/**
	 * リソースIDとリソース名をセッションに保存する.
	 * @return
	 */
	public boolean setResource(){

		String resourceId = _request.getParameter("resourceId");
		GetResouceFromIdService getResouceFromIdService = new GetResouceFromIdService(resourceId);

		getResouceFromIdService.validate(); //trueが返る

		try {
			getResouceFromIdService.execute();
			Resource resource = getResouceFromIdService.getResource();

			if(resource == null){
				_log.error("setResource()_resource is null");
				return false;
			}else{
				_session.setAttribute("resourceIdForReservationRegist", resource.getResourceId());
				_session.setAttribute("resourceNameForReservationRegist", resource.getResourceName());
			}

		} catch (SQLException e) {
			_log.error("setResource()_SQLException");
			e.printStackTrace();
			return false;
		}

		return true;

	}


	public boolean setSlider(){
		//利用可能開始時間
		String usableStartTimeStr = (String) _request.getParameter("usableStartTime");
		//利用可能終了時間
		String usableEndTimeStr = (String) _request.getParameter("usableEndTime");

		CommonValidator commonValidator = new CommonValidator();
		int usableStartTime;

		//引数valの内容が半角整数値かどうかチェック. 数値であればフィールドintValにその数値を保存
		if(commonValidator.notNumericOn(usableStartTimeStr) == false){
			usableStartTime = commonValidator.getNumber();
		}else{
			_log.error("setSlider()_notNumericOn == false");
			return false;
		}

		//分を渡して時分をセット
		TimeDto timeDtoForUsableStartTime = new TimeDto(usableStartTime);



		//引数valの内容が半角整数値かどうかチェック. 数値であればフィールドintValにその数値を保存
		int usableEndTime;
		if(commonValidator.notNumericOn(usableEndTimeStr) == false){
			usableEndTime = commonValidator.getNumber();
		}else{
			_log.error("setSlider()_notNumericOn2 == false");
			return false;
		}

		//分を渡して時分をセット
		TimeDto timeDtoForUsableEndTime = new TimeDto(usableEndTime);

		//実利用時間のDTO
		TimeDto usageTimeDto = (TimeDto) _session.getAttribute("usageTimeForResourceSelect");
		_session.setAttribute("usageTimeForResourceRegist", usageTimeDto);




		//利用可能開始時間をセッションに保存
		_session.setAttribute("usableStartTimeForReservationRegist", timeDtoForUsableStartTime);

		if(_session.getAttribute("usageStartTimeForResourceSelect")!=null){
		TimeDto usageStartTime=(TimeDto) _session.getAttribute("usageStartTimeForResourceSelect");
		_session.setAttribute("usageStartTimeForReservationRegist", usageStartTime);
		}

		int usageTimeForGetTimeMinutesValue = usageTimeDto.getTimeMinutesValue();
		int usableStartTimeForGetTimeMinutesValue = timeDtoForUsableStartTime.getTimeMinutesValue();

		TimeDto timeDtoForUsageEndTime = new TimeDto(usageTimeForGetTimeMinutesValue + usableStartTimeForGetTimeMinutesValue);

		//利用可能終了時間をセッションに保存
		_session.setAttribute("usageEndTimeForReservationRegist", timeDtoForUsageEndTime);
		_session.setAttribute("usableEndTimeForReservationRegist", timeDtoForUsableEndTime);



		return true;

	}
}
