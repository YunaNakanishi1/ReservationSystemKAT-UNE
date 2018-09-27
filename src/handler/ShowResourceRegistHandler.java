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

		
			Boolean hasResourceData = (Boolean)request.getAttribute("hasResourceData");
			if(hasResourceData==null){
				request.setAttribute("hasResourceData", false);
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

		//jspで表示する「戻る」ボタンのリンク先をリソース登録・リソース変更時で分ける処理
		//登録時で「戻る」が押された場合→リソース一覧画面に遷移
		//変更時で「戻る」が押された場合→リソース詳細画面に遷移
					if("regist".equals(type)){
						request.setAttribute("returnPage",SHOW_RESOURCE_LIST_SERVLET);;
					}else{
						request.setAttribute("returnPage",RESOURCE_DETAILS_SERVLET);;

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
