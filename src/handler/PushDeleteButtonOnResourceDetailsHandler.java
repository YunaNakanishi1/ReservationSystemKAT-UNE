package handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * サーブレット番号：6
 * リソース詳細画面から削除ボタンを押下した際にとぶハンドラー.
 * @author リコーITソリューションズ株式会社 KAT-UNE
 * @author z00h230741
 *
 */
public class PushDeleteButtonOnResourceDetailsHandler implements Handler{

	@Override
	public String handleService(HttpServletRequest request) {

		HttpSession session = request.getSession(false);

		//リクエストからリソースIDを取得
		String resourceId = request.getParameter("resourceId");
		//DELETE_RESOURCE用にリクエストにresourceIdをセット
		request.setAttribute("resourceId",resourceId);

		//sessionからユーザーIDと権限を取得
		String userIdOfLoggedInStr = (String)session.getAttribute("userIdOfLoggedIn");
		int authorOfLoggedInInt = (int)session.getAttribute("authorOfLoggedIn");
		
		


	}
}
