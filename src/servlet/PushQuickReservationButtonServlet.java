package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import handler.Handler;
import handler.PushQuickReservationButtonHandler;

/**
 * サーブレット番号：9
 * 今すぐ予約画面を表示するためのサーブレット
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
@WebServlet("/reservesystem/pushQuickReservationButton")
public class PushQuickReservationButtonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Handler pushQuickReservationButtonHandler = new PushQuickReservationButtonHandler();

        //遷移先URL
        String view = pushQuickReservationButtonHandler.handleService(request);

        RequestDispatcher rd = request.getRequestDispatcher(view);
        rd.forward(request, response);
	}

}
