package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import handler.Handler;
import handler.LogInHandler;

/**
 * Servlet implementation class LogInServlet
 */
/**
 * サーブレット番号：3
 * ログイン画面でログインボタンを押下された際に呼び出されるサーブレット.
 * @author リコーITソリューションズ株式会社 z00s600124
 *
 */
@WebServlet("/login")
public class LogInServlet extends HttpServlet {

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Handler loginHandler = new LogInHandler();

        //遷移先URL
        String view = loginHandler.handleService(request);

        RequestDispatcher rd = request.getRequestDispatcher(view);
        rd.forward(request, response);
	}

}
