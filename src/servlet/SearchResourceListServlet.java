package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import handler.SearchResourceListHandler;

/**
 * Servlet implementation class SearchResourceListServlet
 */
/**
 * サーブレット番号：16
 * 予約可能なリソースを検索するサーブレット
 * @author リコーITソリューションズ株式会社 z00s600124
 *
 */
@WebServlet("/reservesystem/searchResourceList")
public class SearchResourceListServlet extends HttpServlet {

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    SearchResourceListHandler sHandler = new SearchResourceListHandler();
	    String view = sHandler.handleService(request);

	    RequestDispatcher rd = request.getRequestDispatcher(view);
        rd.forward(request, response);

	}

}
