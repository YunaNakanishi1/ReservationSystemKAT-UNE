package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import handler.Handler;
import handler.SessionUpdateHandler;

/**
 * Servlet Filter implementation class ReservationSystemFilter
 */
@WebFilter("/reservesystem/*")
public class ReservationSystemFilter implements Filter {

    /**
     * Default constructor.
     */
    public ReservationSystemFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		Handler handler = new SessionUpdateHandler();
		String view=handler.handleService((HttpServletRequest)request);
		if(view!=null){
			RequestDispatcher rd = request.getRequestDispatcher(view);
			rd.forward(request, response);
		}else{
			chain.doFilter(request, response);
		}


	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
