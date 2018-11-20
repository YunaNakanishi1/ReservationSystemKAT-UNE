package handler;

import static handler.MessageHolder.*;
import static handler.ViewHolder.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.ReservationDto;
import dto.Resource;
import service.GetReservationListBetweenDateService;
import service.PushRegistButtonOnResourceRegistService;

/**
 * サーブレット番号：37
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
public class PushRegistButtonOnResourceRegistHandler implements Handler{

	private HttpServletRequest _request;
	private HttpSession _session;
	private Resource _resource;
	private String _type;
	private Logger _log = LogManager.getLogger();


	@Override
	public String handleService(HttpServletRequest request) {

		_request = request;

		// セッションから権限を取得
		//管理者でないもののアクセスは許可しない
		HttpSession session = request.getSession(false);
		_session=session;
		int authority = (int) session.getAttribute("authorityOfLoggedIn");


		if (authority == 0) {//0:管理者 1:一般利用者

			// 入力に不備があった際、入力欄に再表示する内容があることを示す
			request.setAttribute("hasResourceData", true);

			//入力の不備をチェック
			// 新規登録処理と変更処理のどちらも同じバリデーションチェックを行う。
			if (precheck()) {
				// typeの値に応じて登録、変更を行う
			    //遷移先の文字列は各々のメソッドから取得し返却する。

				PushRegistButtonOnResourceRegistService pushRegistButtonOnResourceRegistService = new PushRegistButtonOnResourceRegistService(_resource);
				if(pushRegistButtonOnResourceRegistService.validate()){

					session.setAttribute("stopStartDate", _resource.getUsageStopStartDate());
					session.setAttribute("stopEndDate", _resource.getUsageStopEndDate());

					if(_type.equals("change") && _resource.getUsageStopEndDate() != null){

						GetReservationListBetweenDateService getReservationListBetweenDateService =
						new GetReservationListBetweenDateService(_resource.getResourceId(), _resource.getUsageStopStartDate(), _resource.getUsageStopEndDate());

						if(getReservationListBetweenDateService.validate()){
							try {
								getReservationListBetweenDateService.execute();
								List<ReservationDto> reservationList = getReservationListBetweenDateService.getReservationList();

								if(reservationList.size() == 0){
									return SET_RESOURCE_DETAILS_SERVLET;
								}else{
									session.setAttribute("reservationListForSuspensionUseConfirm", reservationList);
								}
								return SUSPENSION_USE_CONFIRM;

							} catch (SQLException e) {
								_log.error("SQLException");
								return ERROR_PAGE;
							}
						}else{
							_log.error("ValidationError");
							return ERROR_PAGE;
						}
					}else{
						return SET_RESOURCE_DETAILS_SERVLET;
					}
				}else{
					session.setAttribute("Emessage", pushRegistButtonOnResourceRegistService.getValidationMessage());
					return RESOURCE_REGIST_SERVLET;
				}

			} else {
				// 入力に不備があればリソース入力ページに戻す
				return RESOURCE_REGIST_SERVLET;
			}

		} else {
			_log.error("no authority");
			return ERROR_PAGE;


		}
	}

	/**
	 * 入力画面再表示用に値をセットし、正しい形式で入力チェックを行い、_resourceフィールドにセットするメソッド.
	 * 以下に処理の流れを示す。
	 *
	 * 1.入力画面で入力された内容を再表示するためにSetAttributeする。
	 * 2.バリデーションチェック。チェックに引っかかる場合はfalseを返す
	 * 3.バリデーションが通る場合、_resourceフィールドにインスタンスを生成
	 * 4.trueを返す
	 *
	 * @return チェック結果
	 */
	private boolean precheck() {
		// 登録か変更かを取得し、フィールドとリクエストにセットする
		_type = _request.getParameter("type");
		_session.setAttribute("type", _type);

		// 入力内容を取得し、再表示用にsetAttributeしなおす
		String resourceId = _request.getParameter("resourceId");
		_session.setAttribute("resourceId", resourceId);
		String resourceName = _request.getParameter("resourceName");
		_session.setAttribute("resourceName", resourceName);
		String category = _request.getParameter("category");
		_session.setAttribute("category", category);
		String capacity = _request.getParameter("capacity");
		_session.setAttribute("capacity", capacity);
		String officeName = _request.getParameter("officeName");
		_session.setAttribute("officeName", officeName);
		String stopStartDay = _request.getParameter("stopStartDay");
		_session.setAttribute("stopStartDay", stopStartDay);
		String stopStartHour = _request.getParameter("stopStartHour");
		_session.setAttribute("stopStartHour", stopStartHour);
		String stopStartMinute = _request.getParameter("stopStartMinute");
		_session.setAttribute("stopStartMinute", stopStartMinute);
		String stopEndDay = _request.getParameter("stopEndDay");
		_session.setAttribute("stopEndDay", stopEndDay);
		String stopEndHour = _request.getParameter("stopEndHour");
		_session.setAttribute("stopEndHour", stopEndHour);
		String stopEndMinute = _request.getParameter("stopEndMinute");
		_session.setAttribute("stopEndMinute", stopEndMinute);
		String supplement = _request.getParameter("supplement");
		_session.setAttribute("supplement", supplement);

		// リソース特性をリストとしてセット
		List<String> facility = new ArrayList<String>();
		String[] facilityArray = _request.getParameterValues("facility");
		if (facilityArray != null) {
			facility = new ArrayList<String>(Arrays.asList(facilityArray));
		}
		_session.setAttribute("facility", facility);

		CommonValidator commonValidator = new CommonValidator();

		// リソース名が入力されているか調べる
		if (commonValidator.notSetOn(resourceName)) {
			_session.setAttribute("Emessage", EM27);
			return false;
		}
		// カテゴリ情報があるか調べる
		if (commonValidator.notSetOn(category)) {
			_session.setAttribute("Emessage", EM37);
			return false;
		}
		// 定員が入力されているか調べる
		if (commonValidator.notSetOn(capacity)) {
			_session.setAttribute("Emessage", EM30);
			return false;
		}
		// 定員が数字になっているか調べる
		if (commonValidator.notNumericOn(capacity)) {
			_session.setAttribute("Emessage", EM31);
			return false;
		}
		// 数字に変換した定員を取得する
		int capacityNumber = commonValidator.getNumber();
		// 事業所情報があるか調べる
		if (commonValidator.notSetOn(officeName)) {
			_session.setAttribute("Emessage", EM33);
			return false;
		}
		// 利用停止開始日時が設定されているか調べる
		Timestamp stopStartDate = null;
		if (!commonValidator.notSetOn(stopStartDay)) {
			// 日付のフォーマットが正しいか調べる
			if (commonValidator.notDateOn(stopStartDay, stopStartHour, stopStartMinute)) {
				_session.setAttribute("Emessage", EM38);
				return false;
			}
			stopStartDate = commonValidator.getDate();
		}
		// 利用停止終了日時が設定されているか調べる
		Timestamp stopEndDate = null;
		if (!commonValidator.notSetOn(stopEndDay)) {
			// 日付のフォーマットが正しいか調べる
			if (commonValidator.notDateOn(stopEndDay, stopEndHour, stopEndMinute)) {
				_session.setAttribute("Emessage", EM38);
				return false;
			}
			stopEndDate = commonValidator.getDate();
		}
		// 全てのチェックが通るなら、リソースのDTOを作成する
		_resource = new Resource(resourceId, resourceName, officeName, category, capacityNumber, supplement, 0,
				facility, stopStartDate, stopEndDate);
		_session.setAttribute("resource", _resource);
		return true;
	}
}

