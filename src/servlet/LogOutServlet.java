package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import handler.Handler;
import handler.LogOutHandler;


/**
 * セッションを破棄し、ログイン画面に飛ばすサーブレット.
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
@WebServlet("/logout")
public class LogOutServlet extends HttpServlet {

	/**
	 * セッションを破棄し、ログイン画面に飛ばす.
	 * @param request リクエストオブジェクト
     * @param response レスポンスオブジェクト
     * @throws ServletException サーブレット例外
     * @throws IOException IO例外
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Handler logoutHandler = new LogOutHandler();

        //遷移先URL
        String view = logoutHandler.handleService(request);

        RequestDispatcher rd = request.getRequestDispatcher(view);
        rd.forward(request, response);
	}

}
