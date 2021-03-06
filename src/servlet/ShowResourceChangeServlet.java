package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import handler.Handler;
import handler.ShowResourceChangeHandler;

/**リソース入力ページやエラーページに移動する.
 *
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 * Servlet implementation class ShowResourceChangeServlet
 */
@WebServlet("/reservesystem/resourcechange")
public class ShowResourceChangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**リソース入力ページやエラーページに移動する.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Handler showResourceChangeHandler = new ShowResourceChangeHandler();

        //遷移先URL
        String view = showResourceChangeHandler.handleService(request);

        RequestDispatcher rd = request.getRequestDispatcher(view);
        rd.forward(request, response);
	}


}
