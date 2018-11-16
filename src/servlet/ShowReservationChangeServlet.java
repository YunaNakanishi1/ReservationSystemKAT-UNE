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

import handler.ShowReservationChangeHandler;

/**
 * サーブレット番号：25
 * PushChangeButtonOnReservationDetailsサーブレットから、予約変更画面に遷移させる.
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
@WebServlet("/reservesystem//ShowReservationChangeServlet")
public class ShowReservationChangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ShowReservationChangeHandler showReservationChangeHandler =
				new ShowReservationChangeHandler();

		//遷移先URL
        String view =showReservationChangeHandler.handleService(request);

        RequestDispatcher rd = request.getRequestDispatcher(view);
        rd.forward(request, response);
	}

}
