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
import handler.SetResourceDetailsHandler;

/**
* 登録ボタンが押された時にリソース詳細に飛ばすサーブレット.
* @author リコーITソリューションズ株式会社 KAT-UNE
*/
@WebServlet("/reservesystem/setresource")
public class SetResourceDetailsServlet extends HttpServlet {


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Handler setResourceDetailsHandler = new SetResourceDetailsHandler();

        //遷移先URL
        String view = setResourceDetailsHandler.handleService(request);

        RequestDispatcher rd = request.getRequestDispatcher(view);
        rd.forward(request, response);
	}

}
