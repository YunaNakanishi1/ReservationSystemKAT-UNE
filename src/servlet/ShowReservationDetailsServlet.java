/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import handler.ShowReservationDetailsHandler;

/**
 * 予約名称リンクからのフォワード先を決める(26).
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
@WebServlet("/reservesystem/showReservationDetails")
public class ShowReservationDetailsServlet extends HttpServlet {


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String reservedIdStr = request.getParameter("reservedId");

		int reservedId = Integer.parseInt(reservedIdStr);

		HttpSession session = request.getSession(true);
		session.setAttribute("reservationIdForReservationDetails", reservedId);

		doPost(request, response);
	}





	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 ShowReservationDetailsHandler showReservationDetailsHandler
		 = new ShowReservationDetailsHandler();

		 String view = showReservationDetailsHandler.handleService(request);

		 RequestDispatcher rd = request.getRequestDispatcher(view);
	        rd.forward(request, response);

	}

}
