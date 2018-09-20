package handler;


import javax.servlet.http.HttpServletRequest;

public class DeleteResourceHandler implements Handler {
	public String handleService(HttpServletRequest request) {
		//セッションから権限を取得
        int authority = 0; //0 権限あり 1 なし

//      HttpSession httpSession = request.getSession(false);
//
//      //セッションは存在する
//      authority = (int) httpSession.getAttribute("authority");

		return null;
	}
}
