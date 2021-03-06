/**
 *
 */
package handler;

import static handler.MessageHolder.*;
import static handler.ViewHolder.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.AttendanceTypeDto;
import dto.ReservationDto;
import dto.TimeDto;
import dto.User;
import exception.MyException;
import service.ChangeReservationService;
import service.CheckReservationInputService;
import service.GetReservationListBetweenDateService;
import service.IsNotOverlapInReservationListService;
import service.IsNotOverlapUsageTimeService;

/**
 * サーブレット番号：1
 * 予約変更可・不可チェック, 変更処理まで行う
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
public class ChangeReservationHandler implements Handler {
	private static Logger _log = LogManager.getLogger();
	private HttpSession session;
	private ReservationDto _reservation;
	private Timestamp _usageStartTimestamp;
	private Timestamp _usageEndTimestamp;
	private List<ReservationDto> _reservationList;



	/*
	 *
	 * 予約変更可・不可チェック, 変更処理まで行う
	 */
	@Override
	public String handleService(HttpServletRequest request) {
		session =request.getSession(true);

		//入力チェック
		if (!validate(request)) {
			return SHOW_RESERVATION_CHANGE_SERVLET;
		}

		//予約被りチェック
		try {
			if(!reservableCheck()) {
				request.setAttribute("messageForReservationChange", EM24);

				//PushChangeButtonReservationDetailsHandler用
				String reserveIdStr = request.getParameter("reserveId");
				request.setAttribute("reserveId", reserveIdStr);

				//System.out.println("ChangeReservationHandler2");

				return PUSH_CHANGE_BUTTON_ON_RESERVATION_DETAILS_SERVLET;
			}
		} catch (MyException e) {
			return ERROR_PAGE;
		}

		//利用停止期間チェック
		try {
			if(!usageStopDateCheck()) {
				request.setAttribute("messageForReservationChange", EM24);
				return PUSH_CHANGE_BUTTON_ON_RESERVATION_DETAILS_SERVLET;
			}
		} catch (MyException e) {
			return ERROR_PAGE;
		}

		//変更処理
		try {
			boolean changeSucceed = changeReservation(_reservation);

			if (changeSucceed) {
				HandlerHelper.initializeAttributeForReservationRegist(session);
				session.setAttribute("reservationIdForReservationDetails", _reservation.getReservationId());
				request.setAttribute("messageForReservationChange", PM02);
				return SHOW_RESERVATION_DETAILS_SERVLET;

			} else {
				return ERROR_PAGE;
			}

		} catch (MyException e) {
			return ERROR_PAGE;
		}
	}

	/**
	 * 変更情報の入力チェック
	 * @param request
	 * @return
	 */
	private boolean validate(HttpServletRequest request) {
		ReservationDto reservationDTOForReservationChange = (ReservationDto)session.getAttribute("reservationDTOForReservationChange");

		//入力されたセッション情報を取得
		String usageStartMinutesStr = request.getParameter("usageStartTime");
		//利用開始時間の分
		String usageEndMinutesStr = request.getParameter("usageEndTime");


		//追加
		int endSumMin =  (int)session.getAttribute("usageEndTimeForReservationChange");

		///////追加
		TimeDto usageStartTimeForReservationChange = null;
		TimeDto usageEndTimeForReservationChange = null;
		int usageEndTime = 0;
		if (("NaN").equals(usageStartMinutesStr)) {
			usageStartTimeForReservationChange = (TimeDto)session.getAttribute("usageStartTimeForReservationChange");
			usageEndTime = usageStartTimeForReservationChange.getTimeMinutesValue() + endSumMin;
			usageEndTimeForReservationChange = new TimeDto(usageEndTime);

		} else {
			//取得した時間をTimeDto型に変換
			int usageStartMinutes = Integer.parseInt(usageStartMinutesStr);
			int usageEndMinutes = Integer.parseInt(usageEndMinutesStr);
			usageEndTime = usageStartMinutes + endSumMin;
			usageStartTimeForReservationChange = new TimeDto(usageStartMinutes);
			usageEndTimeForReservationChange = new TimeDto(usageEndTime);
		}
		////////

		String reservationNameForReservationChange = request.getParameter("reservationName");
		String numberOfParticipantsForReservationChange = request.getParameter("numberOfParticipants");

		//どうやって持ってくるの？
		String coReservedPersonIdForReservationChange = request.getParameter("coReservedPersonId");

		String attendanceTypeIdForReservationChange = request.getParameter("attendanceTypeId");
		String reserveSupplementForReservationChange = request.getParameter("reserveSupplement");

		//セッションに再セット
		session.setAttribute("usageStartTimeForReservationChange", usageStartTimeForReservationChange);
		session.setAttribute("usageEndTimeForReservationChange", endSumMin);
		session.setAttribute("reservationNameForReservationChange", reservationNameForReservationChange);
		session.setAttribute("numberOfParticipantsForReservationChange", numberOfParticipantsForReservationChange);
		session.setAttribute("coReservedPersonIdForReservationChange", coReservedPersonIdForReservationChange);
		session.setAttribute("attendanceTypeIdForReservationChange", attendanceTypeIdForReservationChange);
		session.setAttribute("reserveSupplementForReservationChange", reserveSupplementForReservationChange);

		CommonValidator commonValidator = new CommonValidator();

		//人数チェック
		int numberOfParticipants = 0;
		try {
			numberOfParticipants = commonValidator.getCapacityValue(numberOfParticipantsForReservationChange);
		} catch(MyException e) {
	        // 正規表現のパターンを作成
	        Pattern p = Pattern.compile("^[0-9]+$");
	        Matcher m = p.matcher(numberOfParticipantsForReservationChange);
			if(m.find()){	//数値のみの時
				request.setAttribute("messageForReservationChange", EM21);
			}else{	//数値のみだが、22222222222222など値が大きすぎる
				request.setAttribute("messageForReservationChange", EM20);
			}
			return false;
		}

		//reservationDto作成準備
		int attendanceTypeId = -1;
		//System.out.println(attendanceTypeIdForReservationChange);
		if(attendanceTypeIdForReservationChange != ""){
			attendanceTypeId = Integer.parseInt(attendanceTypeIdForReservationChange);
		}

		User coReservedPerson = new User(coReservedPersonIdForReservationChange, null, 0, null, null, null, null);
		AttendanceTypeDto attendanceTypeDto = new AttendanceTypeDto(attendanceTypeId, null);

//
//		//reservationDto作成準備
//		int attendanceTypeId = Integer.parseInt(attendanceTypeIdForReservationChange);
//		User coReservedPerson = new User(coReservedPersonIdForReservationChange, null, 0, null, null, null, null);
//		AttendanceTypeDto attendanceTypeDto = new AttendanceTypeDto(attendanceTypeId, null);

		if(commonValidator.notSetOn(reservationNameForReservationChange)){
			reservationNameForReservationChange="名称なし";
		}

		_reservation = new ReservationDto(reservationDTOForReservationChange.getReservationId(), reservationDTOForReservationChange.getResource(), reservationDTOForReservationChange.getUsageDate(), usageStartTimeForReservationChange, usageEndTimeForReservationChange, reservationNameForReservationChange, reservationDTOForReservationChange.getReservedPerson(), coReservedPerson, numberOfParticipants, attendanceTypeDto, reserveSupplementForReservationChange, reservationDTOForReservationChange.getDeleted());

		//入力内容チェック
		CheckReservationInputService checkReservationInputService = new CheckReservationInputService(_reservation);
		//結果が正しくなかった場合
		if (!checkReservationInputService.validate()) {
			String message = checkReservationInputService.getValidationMessage();
			request.setAttribute("messageForReservationChange", message);
			return false;
		}

		return true;

	}

	/**
	 * リソースの予約状況を確認して予約の可否を返却するメソッド
	 * @return 予約の可否
	 */
	private boolean reservableCheck() {
		String usageDate = _reservation.getUsageDate();

		//TimeStamp作成
		TimeDto startTime = _reservation.getUsageStartTime();
		_usageStartTimestamp = startTime.getTimeStamp(usageDate);

		TimeDto endTime = _reservation.getUsageEndTime();
		_usageEndTimestamp = endTime.getTimeStamp(usageDate);



		//予約があったらリストで返却
		GetReservationListBetweenDateService getReservationListBetweenDateService = new GetReservationListBetweenDateService(_reservation.getResource().getResourceId(), _usageStartTimestamp, _usageEndTimestamp);

		if (getReservationListBetweenDateService.validate()) {

			try {
				getReservationListBetweenDateService.execute();
				_reservationList = getReservationListBetweenDateService.getReservationList();

				//System.out.println(_reservationList.size());

			} catch(SQLException e) {
				_log.error("SQLException_check");
				throw new MyException();
			}
		} else {
			throw new MyException();
		}


		//自分以外に予約が無ければtrue
		IsNotOverlapInReservationListService isNotOverlapInReservationListService = new IsNotOverlapInReservationListService(_reservationList, _reservation);
		boolean result = isNotOverlapInReservationListService.validate();

		return result;
	}

	/**
	 * 利用停止期間に入っていないかチェック
	 * @return 利用停止期間に入っている又は削除済みの場合falseを返却
	 */
	private boolean usageStopDateCheck() {
		IsNotOverlapUsageTimeService isNotOverlapUsageTimeService = new IsNotOverlapUsageTimeService(_reservation.getResource(), _usageStartTimestamp, _usageEndTimestamp);
		if (!isNotOverlapUsageTimeService.validate()) {
			return false;
		}

		if (_reservation.getResource().getDeleted() == 1) {
			return false;
		}

		return true;

	}

	/**
	 * 変更処理を行う
	 * @param reservation 変更する予約
	 * @return 変更件数
	 */
	private boolean changeReservation(ReservationDto reservation) {
		ChangeReservationService changeReservationService = new ChangeReservationService(reservation);

		if (changeReservationService.validate()) {

			try {
				changeReservationService.execute();
			} catch (SQLException e) {
				e.printStackTrace();
				_log.error("SQLException_change");
				throw new MyException();
			}

			int result = changeReservationService.getResult();
			if (result == 1) {
				return true;
			}
			return false;

		} else {
			throw new MyException();
		}

	}
}
