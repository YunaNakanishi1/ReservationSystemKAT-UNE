package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import handler.Handler;
import handler.ShowFirstReservationListHandler;

/**
 * Servlet implementation class ShowFirstReservationListServlet
 */
/**
 * 予約一覧を最初に表示するときに呼び出される.
 *
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
@WebServlet("/showfirstreservationlist")
public class ShowFirstReservationListServlet extends HttpServlet {

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Handler showFirstReservationListHandler = new ShowFirstReservationListHandler();
		//遷移先URL
        String view = showFirstReservationListHandler.handleService(request);

        RequestDispatcher rd = request.getRequestDispatcher(view);
        rd.forward(request, response);
	}

}
