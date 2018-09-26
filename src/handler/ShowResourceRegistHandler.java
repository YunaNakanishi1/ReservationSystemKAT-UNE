package handler;

import static handler.ViewHolder.*;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import service.ShowResourceRegistService;

public class ShowResourceRegistHandler implements Handler{

	private static Logger _log = LogManager.getLogger();

	@Override
	public String handleService(HttpServletRequest request) {

		//セッションから権限を取得
		HttpSession session =request.getSession(false);

		int authority=(int) session.getAttribute("authority");

		if(authority==0){

			Boolean checkDisplay = (Boolean)request.getAttribute("checkDisplay");
			if(checkDisplay==null){
				request.setAttribute("checkDisplay", false);
			}
			String type=request.getParameter("type");
			request.setAttribute("type", type);

			ShowResourceRegistService showResourceRegistService=new ShowResourceRegistService();
			if(showResourceRegistService.validate()){
				try{
					showResourceRegistService.execute();

					List<String> categoryList=showResourceRegistService.getCategoryList();
					List<String> officeList=showResourceRegistService.getOfficeList();
					List<String> facilityList=showResourceRegistService.getFacilityList();

					if("regist".equals(type)){
						//戻るボタンが押下された場合（新規登録時）
						request.setAttribute("returnPage",SHOW_RESOURCE_LIST_SERVLET);
					}else{
						//戻るボタンが押下された場合（変更時）
						request.setAttribute("returnPage",RESOURCE_DETAILS_SERVLET);

					}
					//それぞれのリストをセットして入力画面に遷移する
					request.setAttribute("categoryList", categoryList);
					request.setAttribute("officeList", officeList);
					request.setAttribute("facilityList", facilityList);
					return RESOURCE_REGIST;
				}catch(SQLException e){
					_log.error("SQLException");
					return ERROR_PAGE;


				}
			}else{
				_log.error("validateError");
	            return ERROR_PAGE;
			}

		}else{
			_log.error("No authority");
            return ERROR_PAGE;
		}

	}

}
