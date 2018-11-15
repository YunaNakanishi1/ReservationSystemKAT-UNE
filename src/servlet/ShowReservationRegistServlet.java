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

import handler.ShowReservationRegistHandler;

/**
 * リソース選択画面から予約登録画面に遷移させる(8).
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
@WebServlet("/reservesystem/showreserveregist")
public class ShowReservationRegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ShowReservationRegistHandler showReservationRegistHandler =
				new ShowReservationRegistHandler();

		String view = showReservationRegistHandler.handleService(request);

		RequestDispatcher rd = request.getRequestDispatcher(view);
	    rd.forward(request, response);
	}

}
