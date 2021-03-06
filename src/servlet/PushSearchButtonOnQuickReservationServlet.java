package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import handler.PushSearchButtonOnQuickReservationHandler;

/**
 * サーブレット番号：13
 * 今すぐ予約で検索ボタンを押下した際の処理
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
@WebServlet("/reservesystem/pushSearchButtonOnQuickReservation")
public class PushSearchButtonOnQuickReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PushSearchButtonOnQuickReservationHandler pHandler = new PushSearchButtonOnQuickReservationHandler();

	    String view = pHandler.handleService(request);
	    RequestDispatcher rd = request.getRequestDispatcher(view);
        rd.forward(request, response);

	}

}
