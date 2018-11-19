package handler;

import static handler.MessageHolder.*;
import static handler.ViewHolder.*;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.ReservationDto;
import dto.User;
import service.DeleteReservationService;
import service.GetReservationFromIdService;

/**
 * サーブレット番号：2
 * 予約の削除を行う.
 * @author z00h230741
 *
 */
public class DeleteReservationHandler implements Handler{
	private static Logger _log = LogManager.getLogger();

	@Override
	public String handleService(HttpServletRequest request){
		//セッション作成
		HttpSession session =request.getSession(true);

		//リクエストからreservationIdをString型で受け取る
		String reservationIdForReservationDetailsStr = request.getParameter("reservationIdForReservationDetails");

		//reservationIdのint型の変数を用意
		int reservationIdForReservationDetails;

		//reservationIdをint型になおす
		try{
			reservationIdForReservationDetails = Integer.parseInt(reservationIdForReservationDetailsStr);
		}catch(NumberFormatException e){
			_log.error("NumberFormatException");
			return ERROR_PAGE;
		}

		//セッションにreservationIdを保存
		session.setAttribute("reservationIdForReservationDetails",reservationIdForReservationDetails);

		//セッションから現在利用しているユーザのIDを取得する
		//ユーザに予約を削除する権利があるかどうか（現在利用しているユーザが利用者or共同利用者なのか）判断するのに使用します
		String userIdOfLoggedIn = (String)session.getAttribute("userIdOfLoggedIn");

		//ユーザのIDのnullチェック
		CommonValidator commonValidator = new CommonValidator();
		if(commonValidator.notSetOn(userIdOfLoggedIn)){
			 _log.error("commonValidateError");
			 return ERROR_PAGE;
		}

		//reservationIdからreservationDtoを取得する
		GetReservationFromIdService getReservationFromIdService = new GetReservationFromIdService(reservationIdForReservationDetails);
		ReservationDto reservationDto;
		if(getReservationFromIdService.validate()){
			try {
				getReservationFromIdService.execute();
				//reservationDto(フィールド)を取得
				reservationDto = getReservationFromIdService.getReservation();
				if(reservationDto != null){
					session.setAttribute("reservationDtoForReservationDetails",reservationDto);
				}else{
					 _log.error("reservationDtoIsNull");
					 return ERROR_PAGE;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				_log.error("SQLException");
				return ERROR_PAGE;
			}
		}else{
			 _log.error("validateError");
			 return ERROR_PAGE;
		}


		//reservationDtoから予約者・共同予約者（User型）を取得
		User reservedPerson =reservationDto.getReservedPerson();
		User coReservedPerson =reservationDto.getCoReservedPerson();


		//①予約者がnullでない場合
		//②予約者・共同予約者ID（string型）を取得
		//③利用者のIDがreservationDtoの予約者・共同予約者ID（string型）と一致した場合に予約の削除処理を行う
		//④削除した件数が一件の場合⇒予約削除処理成功！！次のサーブレットに飛ばす
		//※予約者がnullでない場合、削除処理に失敗した場合、削除した件数が一件ではない⇒エラーページ

		if(reservedPerson!=null){
			String reservedPersonId = reservedPerson.getUserId();
			String coReservedPersonId = null;
			if (coReservedPerson != null) {
				coReservedPersonId = coReservedPerson.getUserId();
			}

			if(userIdOfLoggedIn.equals(reservedPersonId)||userIdOfLoggedIn.equals(coReservedPersonId)){
				DeleteReservationService deleteReservationService=new DeleteReservationService(reservationIdForReservationDetails);
				if(deleteReservationService.validate()){
					try{
						deleteReservationService.execute();
						int result =deleteReservationService.getResult();
							if(result==1){
								request.setAttribute("messageForDeleteCompleted", PM05);
								return SHOW_RESERVATION_DETAILS_SERVLET;
							}else{
								_log.error("deleteError");
								 return ERROR_PAGE;
							}
					}catch(SQLException e){
						_log.error("SQLException");
						 return ERROR_PAGE;
					}

				}else{
					_log.error("ValidateError");
					 return ERROR_PAGE;
				}

			}else{
				_log.error("NoreservedPersonorcoReservedPerson");
				 return ERROR_PAGE;
			}
		}else{
			 _log.error("reservedPersonIsNull");
			 return ERROR_PAGE;
		}






	}
}