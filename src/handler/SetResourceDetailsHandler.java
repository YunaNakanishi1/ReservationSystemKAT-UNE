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

import dto.Resource;
import service.ChangeResourceService;
import service.RegistResourceService;

/**リソースの登録、変更を行う.
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
public class SetResourceDetailsHandler implements Handler {
	private HttpServletRequest _request;
	private Resource _resource;
	private String _type;
	private Logger _log = LogManager.getLogger();

	@Override
	public String handleService(HttpServletRequest request) {
		_request = request;

		// セッションから権限を取得
		HttpSession session = request.getSession(false);
		int authority = (int) session.getAttribute("authority");

		if(authority==0){
			//表示する内容があることを示す
			request.setAttribute("hasResourceData", true);

			//機能に依存しないバリデーションチェック
			if(precheck()){
				//typeの値に応じて登録、変更を行う
				if("regist".equals(_type)){
					return regist();
				}else{
					return change();
				}
			}else{
				//入力に不備があればリソース入力ページに戻す
				return RESOURCE_REGIST_SERVLET;
			}

		}else{
			_log.error("no authority");
			return ERROR_PAGE;
		}

	}

	/**入力内容の取得と機能に依存しないバリデーションチェック.
	 * @return チェック結果
	 */
	private boolean precheck() {
		//登録か変更かを取得し、フィールドとリクエストにセットする
		_type = _request.getParameter("type");
		_request.setAttribute("type", _type);

		//入力内容を取得し、再表示用にsetAttributeしなおす
		String resourceId = _request.getParameter("resourceId");
		_request.setAttribute("resourceId", resourceId);
		String resourceName = _request.getParameter("resourceName");
		_request.setAttribute("resourceName", resourceName);
		String category = _request.getParameter("category");
		_request.setAttribute("category", category);
		String capacity = _request.getParameter("capacity");
		_request.setAttribute("capacity", capacity);
		String officeName = _request.getParameter("officeName");
		_request.setAttribute("officeName", officeName);
		String stopStartDay = _request.getParameter("stopStartDay");
		_request.setAttribute("stopStartDay", stopStartDay);
		String stopStartHour = _request.getParameter("stopStartHour");
		_request.setAttribute("stopStartHour", stopStartHour);
		String stopStartMinute = _request.getParameter("stopStartMinute");
		_request.setAttribute("stopStartMinute", stopStartMinute);
		String stopEndDay = _request.getParameter("stopEndDay");
		_request.setAttribute("stopEndDay", stopEndDay);
		String stopEndHour = _request.getParameter("stopEndHour");
		_request.setAttribute("stopEndHour", stopEndHour);
		String stopEndMinute = _request.getParameter("stopEndMinute");
		_request.setAttribute("stopEndMinute", stopEndMinute);
		String supplement = _request.getParameter("supplement");
		_request.setAttribute("supplement", supplement);

		//リソース特性をリストとしてセット
		List<String> facility = new ArrayList<String>();
		String[] facilityArray=_request.getParameterValues("facility");
		if(facilityArray!=null){
		facility = new ArrayList<String>(Arrays.asList(facilityArray));
		}
		_request.setAttribute("facility", facility);

		CommonValidator commonValidator = new CommonValidator();

		//リソース名が入力されているか調べる
		if (commonValidator.notSetOn(resourceName)) {
			_request.setAttribute("Emessage", EM27);
			return false;
		}
		//カテゴリ情報があるか調べる
		if (commonValidator.notSetOn(category)) {
			_request.setAttribute("Emessage", EM37);
			return false;
		}
		//定員が入力されているか調べる
		if (commonValidator.notSetOn(capacity)) {
			_request.setAttribute("Emessage", EM30);
			return false;
		}
		//定員が数字になっているか調べる
		if (commonValidator.notNumericOn(capacity)) {
			_request.setAttribute("Emessage", EM31);
			return false;
		}
		//数字に変換した定員を取得する
		int capacityNumber = commonValidator.getNumber();
		//事業所情報があるか調べる
		if (commonValidator.notSetOn(officeName)) {
			_request.setAttribute("Emessage", EM33);
			return false;
		}
		//利用停止開始日時が設定されているか調べる
		Timestamp stopStartDate = null;
		if (!commonValidator.notSetOn(stopStartDay)) {
			//日付のフォーマットが正しいか調べる
			if (commonValidator.notDateOn(stopStartDay, stopStartHour, stopStartMinute)) {
				_request.setAttribute("Emessage", EM38);
				return false;
			}
			stopStartDate = commonValidator.getDate();
		}
		//利用停止終了日時が設定されているか調べる
		Timestamp stopEndDate = null;
		if (!commonValidator.notSetOn(stopEndDay)) {
			//日付のフォーマットが正しいか調べる
			if (commonValidator.notDateOn(stopEndDay, stopEndHour, stopEndMinute)) {
				_request.setAttribute("Emessage", EM38);
				return false;
			}
			stopEndDate = commonValidator.getDate();
		}
		//全てのチェックが通るなら、リソースのDTOを作成する
		_resource = new Resource(resourceId, resourceName, officeName, category, capacityNumber, supplement, 0,
				facility, stopStartDate, stopEndDate);
		return true;
	}

	/**リソースの登録を行う
	 * @return 遷移先のアドレス
	 */
	private String regist() {
		RegistResourceService registResourceService = new RegistResourceService(_resource);

		//バリデーションチェック
		if (registResourceService.validate()) {
			try {
				registResourceService.execute();
				//登録できた件数を取得する
				int result = registResourceService.getResult();
				//自動生成したIDを取得する
				String resourceId = registResourceService.getResourceId();
				//正しく1件登録できたか、IDが設定されているか調べる
				if(resourceId==null){
					_log.error("resourceId overflow");
					return ERROR_PAGE;
				}else if(result!=1){
					_log.error("regist failed");
					return ERROR_PAGE;
				}else{
					Resource resultResource=registResourceService.getResultResource();
					_request.setAttribute("resource", resultResource);
					return RESOURCE_DETAILS;
				}
			} catch (SQLException e) {
				_log.error("SQLException");
				return ERROR_PAGE;
			}
		} else {
			//入力に不備があればリソース入力ページに戻す
			String validationMessage = registResourceService.getValidationMessage();
			_request.setAttribute("Emessage", validationMessage);
			return RESOURCE_REGIST_SERVLET;
		}

	}

	/**リソース情報を変更する
	 * @return 遷移先のアドレス
	 */
	private String change() {
		ChangeResourceService changeResourceService = new ChangeResourceService(_resource);

		//バリデーションチェック
		if (changeResourceService.validate()) {
			try {
				changeResourceService.execute();
				//変更した件数を取得する
				int result = changeResourceService.getResult();
				//正しく1件登録できているか調べる
				if (result == 1) {
					Resource resultResource=changeResourceService.getResultResource();
					_request.setAttribute("resource", resultResource);
					return RESOURCE_DETAILS;
				} else {
					_log.error("change failed");
					return ERROR_PAGE;
				}
			} catch (SQLException e) {
				_log.error("SQLException");
				return ERROR_PAGE;
			}
		} else {
			//入力に不備があればリソース入力ページに戻す
			String validationMessage = changeResourceService.getValidationMessage();
			_request.setAttribute("Emessage", validationMessage);
			return RESOURCE_REGIST_SERVLET;
		}

	}

}
