package handler;

import static handler.ViewHolder.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import service.ShowResourceRegistService;

/**
 * 新規登録時、再表示のリソース入力画面に表示するカテゴリ、事業所、リソース特性情報のセット等を行う.
 *
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
public class ShowResourceRegistHandler implements Handler {

	private static Logger _log = LogManager.getLogger();

	/**
	 * カテゴリ、事業所、リソース特性の情報を取得し、リクエストにセットする.
	 * 戻るボタンの行き先を設定する.
	 *
	 * @see handler.Handler#handleService(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String handleService(HttpServletRequest request) {

		// セッションから権限を取得
		HttpSession session = request.getSession(false);

		int authority = (int) session.getAttribute("authorityOfLoggedIn");

		//リソース管理者
		if (authority == 0) {

			//登録か、変更かtypeを取得
			String type = request.getParameter("type");
			request.setAttribute("type", type);

			//再表示するデータがあるかチェック
			//登録ボタン押下時には必ずnullで、falseをセット
			//入力エラーがあった際は、再表示のためにSetRestrueDetailsHandlerでtrueがセットされている
			Boolean hasResourceData = (Boolean) request.getAttribute("hasResourceData");
			if (hasResourceData == null) {
				hasResourceData = false;
				request.setAttribute("hasResourceData", hasResourceData);
			}

			ShowResourceRegistService showResourceRegistService = new ShowResourceRegistService();
			if (showResourceRegistService.validate()) {
				try {
					showResourceRegistService.execute();

					List<String> categoryList = showResourceRegistService.getCategoryList();
					List<String> officeList = showResourceRegistService.getOfficeList();
					List<String> facilityList = showResourceRegistService.getFacilityList();

					//ユーザーの選択したものが削除されていないかチェックする
					if (hasResourceData) {
						String category = (String) request.getAttribute("category");
						String officeName = (String) request.getAttribute("officeName");
						List<String> facility = (List<String>) request.getAttribute("facility");
						CommonValidator commonValidator = new CommonValidator();

						//カテゴリがあるかチェック
						if (!commonValidator.notSetOn(category)) {
							if (!categoryList.contains(category)) {
								_log.error("category not found");
								return ERROR_PAGE;
							}
						}
						//事業所があるかチェック
						if (!commonValidator.notSetOn(officeName)) {
							if (!officeList.contains(officeName)) {
								_log.error("officeName not found");
								return ERROR_PAGE;
							}
						}
						//特性があるかチェック
						for (String facilityElement : facility) {
							if (!facilityList.contains(facilityElement)) {
								_log.error("facility not found");
								return ERROR_PAGE;
							}
						}

						//リソース特性を選択しているチェックボックスにおいて、上から
						//チェックがついている要素をtrue、ついていない要素をfalseというデータで
						//リストを作成し、再表示用にセットする
						List<Boolean> selectedFacility = new ArrayList<Boolean>();
						for(String facilityElement:facilityList){
							selectedFacility.add(facility.contains(facilityElement));
						}

						request.setAttribute("selectedFacility", selectedFacility);
					}

					// jspで表示する「戻る」ボタンのリンク先をリソース登録・リソース変更時で分ける処理
					// 登録時で「戻る」が押された場合→リソース一覧画面に遷移
					// 変更時で「戻る」が押された場合→リソース詳細画面に遷移
					if ("regist".equals(type)) {
						request.setAttribute("returnPage", SHOW_RESOURCE_LIST_SERVLET);
					} else {
						request.setAttribute("returnPage", RESOURCE_DETAILS_SERVLET);
					}
					// それぞれのリストをセットして入力画面に遷移する
					request.setAttribute("categoryList", categoryList);
					request.setAttribute("officeList", officeList);
					request.setAttribute("facilityList", facilityList);
					return RESOURCE_REGIST;

				} catch (SQLException e) {
					_log.error("SQLException");
					return ERROR_PAGE;

				}
			} else {
				_log.error("validateError");
				return ERROR_PAGE;
			}

		} else {
			_log.error("No authority");
			return ERROR_PAGE;
		}

	}

}
