package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import handler.Handler;
import handler.ShowQuickReservationHandler;

/**
 * サーブレット番号：24
 * 今すぐ予約画面を表示するためのサーブレット.
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
@WebServlet("/reservesystem/showquickreservation")
public class ShowQuickReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Handler showQuickReservationHandler = new ShowQuickReservationHandler();

        //遷移先URL
        String view = showQuickReservationHandler.handleService(request);

        RequestDispatcher rd = request.getRequestDispatcher(view);
        rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);

	}



}
