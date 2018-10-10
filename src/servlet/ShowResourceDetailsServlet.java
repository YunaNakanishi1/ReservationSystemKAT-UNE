package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import handler.ShowResourceDetailsHandler;

/**
 * Servlet implementation class ShowResourceDetailsServlet
 */
/**
 * リソース一覧画面からリソース名を選択したときに呼ばれ、リソース詳細画面に遷移させるサーブレット.
 * @author リコーITソリューションズ株式会社 z00s600124
 *
 */
@WebServlet("/reservesystem/resourcedetails")
public class ShowResourceDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    ShowResourceDetailsHandler showResourceDetailsHandler = new ShowResourceDetailsHandler();
        String view = showResourceDetailsHandler.handleService(request);

        RequestDispatcher rd = request.getRequestDispatcher(view);
        rd.forward(request, response);
	}

}
