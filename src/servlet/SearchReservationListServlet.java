package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import handler.Handler;
import handler.SearchReservationListHandler;

/**
 * サーブレット番号：29
 * 予約一覧を表示するときに最後に呼ばれるサーブレット
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
@WebServlet("/reservesystem/searchReservationList")
public class SearchReservationListServlet extends HttpServlet {


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Handler searchReservationListHandler = new SearchReservationListHandler();

        //遷移先URL
        String view = searchReservationListHandler.handleService(request);

        RequestDispatcher rd = request.getRequestDispatcher(view);
        rd.forward(request, response);
	}

}
