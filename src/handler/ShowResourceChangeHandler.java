package handler;

import static handler.ViewHolder.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.Resource;
import service.ShowResourceChangeService;

public class ShowResourceChangeHandler implements Handler{

	private static Logger _log = LogManager.getLogger();

	@Override
	public String handleService(HttpServletRequest request) {

		//セッションから権限を取得
		HttpSession session =request.getSession(false);

		int authority=(int) session.getAttribute("authority");

		if(authority==0){

				request.setAttribute("hasResourceData", true);

			String type=request.getParameter("type");
			request.setAttribute("type", type);

			String resourceId = request.getParameter("resourceId");

			ShowResourceChangeService showResourceChangeService=new ShowResourceChangeService(resourceId);
			if(showResourceChangeService.validate()){
				try{
					showResourceChangeService.execute();

					List<String> categoryList=showResourceChangeService.getCategoryList();
					List<String> officeList=showResourceChangeService.getOfficeList();
					List<String> facilityList=showResourceChangeService.getFacilityList();

					Resource resource = showResourceChangeService.getResource();

					if(resource==null){
						_log.error("no Resource");
						return ERROR_PAGE;
					}
					if(resource.getDeleted()!=0){
						_log.error("Resource is deleted");
						return ERROR_PAGE;
					}


					if("regist".equals(type)){
						//戻るボタンが押下された場合（新規登録時）
						request.setAttribute("returnPage",SHOW_RESOURCE_LIST_SERVLET);;
					}else{
						//戻るボタンが押下された場合（変更時）
						request.setAttribute("returnPage",RESOURCE_DETAILS_SERVLET);;

					}
					//それぞれのリストをセットして入力画面に遷移する
					request.setAttribute("categoryList", categoryList);
					request.setAttribute("officeList", officeList);
					request.setAttribute("facilityList", facilityList);

					request.setAttribute("resourceId", resource.getResourceId());
					request.setAttribute("resourceName", resource.getResourceName());
					request.setAttribute("category", resource.getCategory());
					request.setAttribute("capacity", resource.getCapacity());
					request.setAttribute("officeName", resource.getOfficeName());

					request.setAttribute("stopStartDate", resource.getUsageStopStartDate());
					request.setAttribute("stopEndDate", resource.getUsageStopEndDate());

					List<String> facility = resource.getFacility();
					List<Boolean> selectedFacility = new ArrayList<Boolean>();
					for(String fac:facilityList){
						selectedFacility.add(facility.contains(fac));
					}

					request.setAttribute("selectedFacility", selectedFacility);

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
