/**
 *
 */
package handler;

import static handler.MessageHolder.*;
import static handler.ViewHolder.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.AttendanceTypeDto;
import dto.ReservationDto;
import dto.TimeDto;
import dto.User;
import exception.MyException;
import service.CheckReservationInputService;
import service.GetReservationListBetweenDateService;
import service.IsNotOverlapInReservationListService;

/**
 * サーブレット番号：1
 * 予約変更可・不可チェック
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
public class ChangeReservationHandler implements Handler {
	private static Logger _log = LogManager.getLogger();
	private HttpSession session;
	private ReservationDto _reservation;

	/* (非 Javadoc)
	 * @see handler.Handler#handleService(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String handleService(HttpServletRequest request) {
		session =request.getSession(true);

		if (!validate(request)) {
			return SHOW_RESERVATION_CHANGE_SERVLET;
		}

		try {
			if(!reservableCheck()) {
				request.setAttribute("messageForReservationChange", EM24);
				return PUSH_CHANGE_BUTTON_ON_RESERVATION_DETAILS_SERVLET;
			}
		} catch (MyException e) {
			return ERROR_PAGE;
		}

		return null;
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
		//利用終了時間の分

		String reservationNameForReservationChange = request.getParameter("reservationName");
		String numberOfParticipantsForReservationChange = request.getParameter("numberOfParticipants");
		String coReservedPersonIdForReservationChange = request.getParameter("coReservedPersonId");
		String attendanceTypeIdForReservationChange = request.getParameter("attendanceTypeId");
		String reserveSupplementForReservationChange = request.getParameter("reserveSupplement");

		//取得した時間をTimeDto型に変換
		int usageStartMinutes = Integer.parseInt("usageStartMinutesStr");
		int usageEndMinutes = Integer.parseInt("usageEndMinutesStr");
		TimeDto usageStartTimeForReservationChange = new TimeDto(usageStartMinutes);
		TimeDto usageEndTimeForReservationChange = new TimeDto(usageEndMinutes);

		//セッションに再セット
		session.setAttribute("usageStartTimeForReservationChange", usageStartTimeForReservationChange);
		session.setAttribute("usageEndTimeForReservationChange", usageEndTimeForReservationChange);
		session.setAttribute("reservationNameForReservationChange", reservationNameForReservationChange);
		session.setAttribute("numberOfParticipantsForReservationChange", numberOfParticipantsForReservationChange);
		session.setAttribute("coReservedPersonIdForReservationChange", coReservedPersonIdForReservationChange);
		session.setAttribute("attendanceTypeIdForReservationChange", attendanceTypeIdForReservationChange);
		session.setAttribute("reserveSupplementForReservationChange", reserveSupplementForReservationChange);

		CommonValidator commonValidator = new CommonValidator();
		int numberOfParticipants = 0;
		try {
			numberOfParticipants = commonValidator.getCapacityValue(numberOfParticipantsForReservationChange);
		} catch(MyException e) {
			request.setAttribute("messageForReservationChange", EM20);
			return false;
		}

		//reservationDto作成準備
		int attendanceTypeId = Integer.parseInt(attendanceTypeIdForReservationChange);
		User coReservedPerson = new User(coReservedPersonIdForReservationChange, null, 0, null, null, null, null);
		AttendanceTypeDto attendanceTypeDto = new AttendanceTypeDto(attendanceTypeId, null);

		_reservation = new ReservationDto(reservationDTOForReservationChange.getReservationId(), reservationDTOForReservationChange.getResource(), reservationDTOForReservationChange.getUsageDate(), usageStartTimeForReservationChange, usageEndTimeForReservationChange, reservationNameForReservationChange, reservationDTOForReservationChange.getReservedPerson(), coReservedPerson, numberOfParticipants, attendanceTypeDto, reserveSupplementForReservationChange, reservationDTOForReservationChange.getDeleted());

		//入力内容チェック
		CheckReservationInputService checkReservationInputService = new CheckReservationInputService(_reservation);
		//結果が正しくなかった場合
		if (!checkReservationInputService.validate()) {
			String message = checkReservationInputService.getValidationMessage();
			session.setAttribute("messageForReservationChange", message);
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

		TimeDto startTime = _reservation.getUsageStartTime();
		String startHour = String.valueOf(startTime.getHour());
		String startMinute = String.valueOf(startTime.getMinutes());

		TimeDto endTime = _reservation.getUsageEndTime();
		String endHour = String.valueOf(endTime.getHour());
		String endMinute = String.valueOf(endTime.getMinutes());

		String usageStartTime = usageDate + " " + startHour + ":" + startMinute;
		String usageEndTime = usageDate + " " + endHour + ":" + endMinute;

		Timestamp usageStartTimestamp  = null;
		Timestamp usageEndTimestamp  = null;

		try {
			usageStartTimestamp = new Timestamp(new SimpleDateFormat("yyyy/MM/dd hh:mm").parse(usageStartTime).getTime());
			usageEndTimestamp = new Timestamp(new SimpleDateFormat("yyyy/MM/dd hh:mm").parse(usageEndTime).getTime());
			System.out.println(usageStartTimestamp);
			System.out.println(usageEndTimestamp);
			//これ出力してみる
		} catch (ParseException e) {
			throw new MyException();
		}

		//予約があったらリストで返却
		GetReservationListBetweenDateService getReservationListBetweenDateService = new GetReservationListBetweenDateService(_reservation.getResource().getResourceId(), usageStartTimestamp, usageEndTimestamp);
		List<ReservationDto> reservationList = null;
		if (getReservationListBetweenDateService.validate()) {
			try {
				getReservationListBetweenDateService.execute();
				reservationList = getReservationListBetweenDateService.getReservationList();

			} catch(SQLException e) {
				_log.error("SQLException");
				throw new MyException();
			}
		} else {
			throw new MyException();
		}

		//自分以外に予約が無ければtrue
		IsNotOverlapInReservationListService isNotOverlapInReservationListService = new IsNotOverlapInReservationListService(reservationList, _reservation);
		boolean result = isNotOverlapInReservationListService.validate();

		return result;
	}

}
