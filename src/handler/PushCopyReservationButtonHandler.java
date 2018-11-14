package handler;

import static handler.ViewHolder.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class PushCopyReservationButtonHandler implements Handler{

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

		//コピー元からの予約情報（reservationdto）をsessionから取得
			String usageDateForReservationRegist=request.getParameter("usageDateForReservationRegist");
			String usageStartTimeForResourceSelect=request.getParameter("usageStartTimeForResourceSelect");
			String usageEndTimeForResourceSelect=request.getParameter("usageEndTimeForResourceSelect");
			String resourceNameForResourceSelect=request.getParameter("resourceNameForResourceSelect");
			String categoryIdForResourceSelect=request.getParameter("categoryIdForResourceSelect");
			String officeIdForResourceSelect=request.getParameter("officeIdForResourceSelect");
			String facilityIdListForResourceSelect=request.getParameter("facilityIdListForResourceSelect");
			String capacityForResourceSelect=request.getParameter("capacityForResourceSelect");
			String reservationNameForReservationRegist=request.getParameter("reservationNameForReservationRegist");
			String numberOfParticipantsForReservationRegist=request.getParameter("numberOfParticipantsForReservationRegist");
			String coReservedPersonIdForReservationRegist=request.getParameter("coReservedPersonIdForReservationRegist");
			String attendanceTypeIdForReservationRegist=request.getParameter("attendanceTypeIdForReservationRegist");
			String reserveSupplementForReservationRegist=request.getParameter("reserveSupplementForReservationRegist");

		//
		return null;
	}

}
