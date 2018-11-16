package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import handler.Handler;
import handler.PushChangeButtonOnReservationDetailsHandler;

/**
 * サーブレット番号：39
 * 予約詳細画面から変更ボタンを押下したときに呼び出されるサーブレット
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
@WebServlet("/reservesystem/pushChangeButtonOnReservationDetails")
public class PushChangeButtonOnReservationDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Handler handler = new PushChangeButtonOnReservationDetailsHandler();

		//遷移先URL
        String view = handler.handleService(request);

        RequestDispatcher rd = request.getRequestDispatcher(view);
        rd.forward(request, response);

	}

}
