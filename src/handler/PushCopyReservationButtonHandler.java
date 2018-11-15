package handler;

import static handler.ViewHolder.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.ReservationDto;

public class PushCopyReservationButtonHandler implements Handler{

    private Logger _log = LogManager.getLogger();

	@Override
	public String handleService(HttpServletRequest request) {

		HttpSession session =request.getSession(true);

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

		//コピー元からの予約情報（reservationDto）をsessionから取得
			ReservationDto reservationDto = (ReservationDto) session.getAttribute("reservationForReservatiionDetails");
			session.setAttribute("usageDateForReservationRegist",reservationDto.getUsageDate());
			session.setAttribute("usageStartTimeForResourceSelect",reservationDto.getUsageStartTime());
			session.setAttribute("usageEndTimeForResourceSelect",reservationDto.getUsageEndTime());
			session.setAttribute("resourceNameForResourceSelect",reservationDto.getResource().getResourceName());
			session.setAttribute("categoryIdForResourceSelect",reservationDto.getResource().getCategory()./*カテゴリIDの取り出し方わかんない*/);
			session.setAttribute("officeIdForResourceSelect",reservationDto.getResource()./*事業所IDの取り出し方わかんない*/);
			session.setAttribute("facilityIdListForResourceSelect",reservationDto.getResource()./*ファシリティIDの取り出し方わかんない*/);
			session.setAttribute("capacityForResourceSelect",reservationDto.getResource().getCapacity());
			session.setAttribute("reservationNameForReservationRegist",reservationDto.getReservationName());
			session.setAttribute("numberOfParticipantsForReservationRegist",reservationDto.getNumberOfParticipants());
			session.setAttribute("coReservedPersonIdForReservationRegist",reservationDto.getCoReservedPerson().getUserId());
			session.setAttribute("attendanceTypeIdForReservationRegist",reservationDto.getAttendanceTypeDto().getAttendanceTypeId());
			session.setAttribute("reserveSupplementForReservationRegist",reservationDto.getSupplement());

		//終了時間ー開始時間で実利用時間を計算する



		return null;
	}

}
