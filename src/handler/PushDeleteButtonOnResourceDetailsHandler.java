package handler;

import static handler.ViewHolder.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.ReservationDto;

/**
 * サーブレット番号：6
 * リソース詳細画面から削除ボタンを押下した際にとぶハンドラー.
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
public class PushDeleteButtonOnResourceDetailsHandler implements Handler{

	private static Logger _log = LogManager.getLogger();

	@Override
	public String handleService(HttpServletRequest request) {

		HttpSession session = request.getSession(false);

		//リクエストからリソースIDを取得
		String resourceId = request.getParameter("resourceId");
		//DELETE_RESOURCE用にリクエストにresourceIdをセット
		request.setAttribute("resourceId",resourceId);

		//sessionからユーザーIDと権限を取得
		String userIdOfLoggedIn = (String)session.getAttribute("userIdOfLoggedIn");
		int authorOfLoggedIn = (int)session.getAttribute("authorOfLoggedIn");

		//userIdがない場合またはリソース管理者でない場合はシステムエラー画面に遷移
		CommonValidator commonValidator=new CommonValidator();
		if(commonValidator.notSetOn("userIdOfLoggedIn")||authorOfLoggedIn!=0){
			_log.error("NoUserid");
			return ERROR_PAGE;

		}

		List<ReservationDto> reservationList =new ArrayList<ReservationDto>();
		GetReservationListFromResourceIdService getReservationListFromResourceIdService=new GetReservationListFromResourceIdService(resourceId);
		if(getReservationListFromResourceIdService.validate()){
			try{
				getReservationListFromResourceIdService.execute();
				reservationList=getReservationListFromResourceIdService.getList();
			}catch(SQLException e){
				_log.error("SQLException");
				return ERROR_PAGE;
			}
		}else{
			_log.error("ValidateError");
			return ERROR_PAGE;
		}

		if(reservationList.size()==0){
			return DELETE_RESOURCE;

		}

		session.setAttribute("reservationListForResourceDeleteConfirm",reservationList);
		return RESOURCE_DELETE_CONFIRM;
	}
}
