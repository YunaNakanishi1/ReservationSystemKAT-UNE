/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package handler;

import static handler.MessageHolder.*;
import static handler.ViewHolder.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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

/**
 * リソースの登録、変更処理を行うクラス.
 * _typeフィールドの内容によってどちらの処理を行うかを決める.
 *
 *
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
public class SetResourceDetailsHandler implements Handler {
	private HttpServletRequest _request;
	private Resource _resource;
	private String _type;
	private Logger _log = LogManager.getLogger();

	/* (非 Javadoc)
	 * @see handler.Handler#handleService(javax.servlet.http.HttpServletRequest)
	 * _typeフィールドの内容に従って、新規登録処理と変更処理を分ける。
	 *
	 */
	@Override
	public String handleService(HttpServletRequest request) {
		_request = request;

		// セッションから権限を取得
		//管理者でないもののアクセスは許可しない
		HttpSession session = request.getSession(false);
		int authority = (int) session.getAttribute("authority");


		if (authority == 0) {//0:管理者 1:一般利用者

			// 入力に不備があった際、入力欄に再表示する内容があることを示す
			request.setAttribute("hasResourceData", true);

			//入力の不備をチェック
			// 新規登録処理と変更処理のどちらも同じバリデーションチェックを行う。
			if (precheck()) {
				// typeの値に応じて登録、変更を行う
			    //遷移先の文字列は各々のメソッドから取得し返却する。
				if ("regist".equals(_type)) {
					return regist();
				} else if("change".equals(_type)){
					return change();
				}else{
				    //_typeに異常な値を検知
					_log.error("wrong type");
					return ERROR_PAGE;
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
		_request.setAttribute("type", _type);

		// 入力内容を取得し、再表示用にsetAttributeしなおす
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

		// リソース特性をリストとしてセット
		List<String> facility = new ArrayList<String>();
		String[] facilityArray = _request.getParameterValues("facility");
		if (facilityArray != null) {
			facility = new ArrayList<String>(Arrays.asList(facilityArray));
		}
		_request.setAttribute("facility", facility);

		CommonValidator commonValidator = new CommonValidator();

		// リソース名が入力されているか調べる
		if (commonValidator.notSetOn(resourceName)) {
			_request.setAttribute("Emessage", EM27);
			return false;
		}
		// カテゴリ情報があるか調べる
		if (commonValidator.notSetOn(category)) {
			_request.setAttribute("Emessage", EM37);
			return false;
		}
		// 定員が入力されているか調べる
		if (commonValidator.notSetOn(capacity)) {
			_request.setAttribute("Emessage", EM30);
			return false;
		}
		// 定員が数字になっているか調べる
		if (commonValidator.notNumericOn(capacity)) {
			_request.setAttribute("Emessage", EM31);
			return false;
		}
		// 数字に変換した定員を取得する
		int capacityNumber = commonValidator.getNumber();
		// 事業所情報があるか調べる
		if (commonValidator.notSetOn(officeName)) {
			_request.setAttribute("Emessage", EM33);
			return false;
		}
		// 利用停止開始日時が設定されているか調べる
		Timestamp stopStartDate = null;
		if (!commonValidator.notSetOn(stopStartDay)) {
			// 日付のフォーマットが正しいか調べる
			if (commonValidator.notDateOn(stopStartDay, stopStartHour, stopStartMinute)) {
				_request.setAttribute("Emessage", EM38);
				return false;
			}
			stopStartDate = commonValidator.getDate();
		}
		// 利用停止終了日時が設定されているか調べる
		Timestamp stopEndDate = null;
		if (!commonValidator.notSetOn(stopEndDay)) {
			// 日付のフォーマットが正しいか調べる
			if (commonValidator.notDateOn(stopEndDay, stopEndHour, stopEndMinute)) {
				_request.setAttribute("Emessage", EM38);
				return false;
			}
			stopEndDate = commonValidator.getDate();
		}
		// 全てのチェックが通るなら、リソースのDTOを作成する
		_resource = new Resource(resourceId, resourceName, officeName, category, capacityNumber, supplement, 0,
				facility, stopStartDate, stopEndDate);
		return true;
	}

	/**
	 * リソースの登録を行う
	 *リソースが正しく登録されればリソース詳細画面に遷移し、登録した内容が表示される.
	 *入力に不備があれば、入力画面に戻す
	 *
	 * @return 遷移先のアドレス
	 */
	private String regist() {

	    //try
		RegistResourceService registResourceService = new RegistResourceService(_resource);


		// バリデーションチェック
		if (registResourceService.validate()) {
			try {
			    //登録処理実行.登録できればresultに1が入り、登録できなければSQLExceptionを発生させる。
				registResourceService.execute();
				// 登録できた件数を取得する
				int result = registResourceService.getResult();
				// 自動生成したIDを取得する
				String resourceId = registResourceService.getResourceId();
				// 正しく1件登録できたか、IDを取得できているか調べる
				if (resourceId == null) {
					_log.error("resourceId overflow");
					return ERROR_PAGE;
				} else if (result != 1) {
					_log.error("regist failed");
					return ERROR_PAGE;
				} else {
				    //リソース詳細画面を表示するための準備
				    //必要なデータをセットする
					Resource resultResource = registResourceService.getResultResource();
					if ((resultResource.getUsageStopStartDate() != null)
							&& (resultResource.getUsageStopEndDate() != null)) {
						// 利用停止期間をフォーマットに即して変換してセット
						String format = "yyyy/MM/dd　H時m分";
						String stopStartDate = new SimpleDateFormat(format)
								.format(resultResource.getUsageStopStartDate());
						String stopEndDate = new SimpleDateFormat(format).format(resultResource.getUsageStopEndDate());
						_request.setAttribute("stopStartDate", stopStartDate);
						_request.setAttribute("stopEndDate", stopEndDate);
					}
					_request.setAttribute("resource", resultResource);
					_request.setAttribute("Pmessage", PM06);
					return RESOURCE_DETAILS;
				}
			} catch (SQLException e) {
				_log.error("SQLException");
				return ERROR_PAGE;
			}
		} else {
			// 入力に不備があればリソース入力ページに戻す
			String validationMessage = registResourceService.getValidationMessage();
			_request.setAttribute("Emessage", validationMessage);
			return RESOURCE_REGIST_SERVLET;
		}

	}

	/**
    /**
     * リソースの変更を行う
     *リソースが正しく登録されればリソース詳細画面に遷移し、登録した内容が表示される.
     *入力に不備があれば、入力画面に戻す
     *
     * @return 遷移先のアドレス
     */
	private String change() {
		ChangeResourceService changeResourceService = new ChangeResourceService(_resource);

		// バリデーションチェック
		if (changeResourceService.validate()) {
			try {
			    //update処理.登録できればresultに1が入り、できなければ0が入る.
				changeResourceService.execute();
				// 変更した件数を取得する
				int result = changeResourceService.getResult();
				// 正しく1件登録できているか調べる
				if (result == 1) {
				  //リソース詳細画面を表示するための準備
                    //必要なデータをセットする
					Resource resultResource = changeResourceService.getResultResource();
					if ((resultResource.getUsageStopStartDate() != null)
							&& (resultResource.getUsageStopEndDate() != null)) {
						// 利用停止期間をフォーマットに即して変換してセット
						String format = "yyyy/MM/dd　H時m分";
						String stopStartDate = new SimpleDateFormat(format)
								.format(resultResource.getUsageStopStartDate());
						String stopEndDate = new SimpleDateFormat(format).format(resultResource.getUsageStopEndDate());
						_request.setAttribute("stopStartDate", stopStartDate);
						_request.setAttribute("stopEndDate", stopEndDate);
					}
					_request.setAttribute("resource", resultResource);
					_request.setAttribute("Pmessage", PM06);
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
			// 入力に不備があればリソース入力ページに戻す
			String validationMessage = changeResourceService.getValidationMessage();
			_request.setAttribute("Emessage", validationMessage);
			return RESOURCE_REGIST_SERVLET;
		}

	}

}
