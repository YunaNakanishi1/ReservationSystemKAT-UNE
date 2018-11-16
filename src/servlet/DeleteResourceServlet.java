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

import handler.DeleteResourceHandler;
import handler.Handler;

/**
 * サーブレット番号：37
* リソース詳細画面で削除ボタンが押された時にリソース一覧画面に飛ばすサーブレット.
* @author リコーITソリューションズ株式会社 KAT-UNE
*/
@WebServlet("/reservesystem/deleteresource")
public class DeleteResourceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Handler deleteResourceHandler = new DeleteResourceHandler();

        //遷移先URL
        String view = deleteResourceHandler.handleService(request);

        RequestDispatcher rd = request.getRequestDispatcher(view);
        rd.forward(request, response);
	}

}
