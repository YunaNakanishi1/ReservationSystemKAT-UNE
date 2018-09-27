package handler;

import static handler.ViewHolder.*;

import java.sql.SQLException;
import java.sql.Timestamp;
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



			request.setAttribute("checkDisplay", true);

			String type=request.getParameter("type");
			request.setAttribute("type", type);

			//選択されたリソースのIDを取り出す
			String resourceId=request.getParameter("resourceId");

			ShowResourceChangeService showResourceChangeService=new ShowResourceChangeService(resourceId);
			if(showResourceChangeService.validate()){
				try{
					showResourceChangeService.execute();

					List<String> categoryList=showResourceChangeService.getCategoryList();
					List<String> officeList=showResourceChangeService.getOfficeList();
					List<String> facilityList=showResourceChangeService.getFacilityList();
					Resource resource=showResourceChangeService.getResource();

					//各要素をすべて取り出す
					resourceId=resource.getResourceId();
					String resourceName=resource.getResourceName();
					String officeName=resource.getOfficeName();
					String category =resource.getCategory();
					int  capacity=resource.getCapacity();
					String supplement=resource.getSupplement();
					int deleted = resource.getDeleted();
					List<String> facility=resource.getFacility();
					Timestamp stopStartDate=resource.getUsageStopStartDate();
					Timestamp stopEndDate=resource.getUsageStopEndDate();

					//それぞれの値をリクエストにセットする
					request.setAttribute("resourceId", resourceId);
					request.setAttribute("resourceName", resourceName);
					request.setAttribute("officeName", officeName);
					request.setAttribute("category", category );
					request.setAttribute("capacity",capacity );
					request.setAttribute("supplement",supplement );
					request.setAttribute("deleted ",deleted  );
					request.setAttribute("facility",facility );
					request.setAttribute("stopStartDate",stopStartDate );
					request.setAttribute("stopEndDate",stopEndDate);

					List<Boolean> selectedFacility=new ArrayList<Boolean>();

					for(String fac:facilityList)
					{selectedFacility.add(facility.contains(fac));}


					request.setAttribute("selectedFacility",selectedFacility);


					//変更時で「戻る」が押された場合→リソース詳細画面に遷移
					request.setAttribute("returnPage",RESOURCE_DETAILS_SERVLET);


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
				_log.error("No authority");
	            return ERROR_PAGE;
			}



	}else{
		_log.error("No authority");
        return ERROR_PAGE;
	}


	}
}
