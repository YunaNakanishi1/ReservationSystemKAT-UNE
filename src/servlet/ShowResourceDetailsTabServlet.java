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

import handler.Handler;
import handler.ShowResourceDetailsTabHandler;

/**
*
* リソース名押下により、リソース詳細画面へ遷移する(31).
* @author リコーITソリューションズ株式会社 KAT-UNE
*
*/
@WebServlet("/reservesystem/showresourcedetailstab")
public class ShowResourceDetailsTabServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Handler handler = new ShowResourceDetailsTabHandler();
		String view = handler.handleService(request);

		RequestDispatcher rd = request.getRequestDispatcher(view);
		rd.forward(request, response);
	}

}
