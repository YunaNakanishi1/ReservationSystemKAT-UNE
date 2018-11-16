package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import handler.PushSearchButtonOnResourceSelectHandler;

/**
 * サーブレット番号：14
 * リソース選択のバリデーションチェック
 *@author リコーITソリューションズ株式会社 KAT-UNE
 */
@WebServlet("/reservesystem/pushSearchButtonOnResourceSelect")
public class PushSearchButtonOnResourceSelectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PushSearchButtonOnResourceSelectHandler pHandler = new PushSearchButtonOnResourceSelectHandler();

	    String view = pHandler.handleService(request);
	    RequestDispatcher rd = request.getRequestDispatcher(view);
        rd.forward(request, response);
	}

}
