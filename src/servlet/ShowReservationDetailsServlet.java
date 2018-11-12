/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package servlet;

import java.io.IOException;

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
@WebServlet("/showReservationDetails")
public class ShowReservationDetailsServlet extends HttpServlet {


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String reservedId = request.getParameter("reservedId");

		HttpSession session = request.getSession(true);
		session.setAttribute("reservedId", reservedId);

		doPost(request, response);
	}





	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 ShowReservationDetailsHandler showReservationDetailsHandler
		 = new ShowReservationDetailsHandler();

		 showReservationDetailsHandler.handleService(request);

	}

}
