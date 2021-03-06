package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import handler.Handler;
import handler.PushNewReservationButtonHandler;

/**
 * サーブレット番号：7
 * 予約一覧画面から新規予約ボタンを押下したときに呼び出されるサーブレット
 *@author リコーITソリューションズ株式会社 KAT-UNE
 */
@WebServlet("/reservesystem/pushNewReservationButton")
public class PushNewReservationButtonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Handler pushNewReservationHandler = new PushNewReservationButtonHandler();

	       String view = pushNewReservationHandler.handleService(request);


		 RequestDispatcher rd = request.getRequestDispatcher(view);
	        rd.forward(request, response);
	 }





}