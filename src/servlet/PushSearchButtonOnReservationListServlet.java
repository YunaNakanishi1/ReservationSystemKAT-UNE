package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import handler.PushSearchButtonOnReservationListHandler;

/**
 * Servlet implementation class PushSearchButtonOnReservationListServlet
 */
/**
 * 12番　予約一覧画面の検索項目から検索するサーブレット
 * @author リコーITソリューションズ株式会社 z00s600124
 *
 */
@WebServlet("/reservesystem/pushSearchButtonOnReservationList")
public class PushSearchButtonOnReservationListServlet extends HttpServlet {

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    PushSearchButtonOnReservationListHandler pHandler = new PushSearchButtonOnReservationListHandler();

	    String view = pHandler.handleService(request);
	    RequestDispatcher rd = request.getRequestDispatcher(view);
        rd.forward(request, response);

	}

}
