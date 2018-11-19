package handler;

import javax.servlet.http.HttpServletRequest;

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

		//リクエストからリソースIDを取得
		String resourceId = request.getParameter("resourceId");


	}
}
