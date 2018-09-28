package handler;

import static handler.ViewHolder.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.Resource;
import service.ShowResourceChangeService;

/**
 * リソース入力画面に表示するカテゴリ、事業所、リソース特性と元のリソース情報を求める.
 *
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
public class ShowResourceChangeHandler implements Handler{

	private static Logger _log = LogManager.getLogger();

	/**
	 * リソース入力画面に表示するカテゴリ、事業所、リソース特性と元のリソース情報を求める.
	 * 戻るボタンの行き先を設定する.
	 *
	 * @see handler.Handler#handleService(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String handleService(HttpServletRequest request) {

		//セッションから権限を取得
		HttpSession session =request.getSession(false);

		int authority=(int) session.getAttribute("authority");

		if(authority==0){

			//リソース情報があることを設定する
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

					//リソースが見つからなかったり、削除済みならエラー
					if(resource==null){
						_log.error("no Resource");
						return ERROR_PAGE;
					}
					if(resource.getDeleted()!=0){
						_log.error("Resource is deleted");
						return ERROR_PAGE;
					}



					request.setAttribute("returnPage",RESOURCE_DETAILS_SERVLET);;

					//それぞれのリストをセットして入力画面に遷移する
					request.setAttribute("categoryList", categoryList);
					request.setAttribute("officeList", officeList);
					request.setAttribute("facilityList", facilityList);

					request.setAttribute("resourceId", resource.getResourceId());
					request.setAttribute("resourceName", resource.getResourceName());
					request.setAttribute("category", resource.getCategory());
					request.setAttribute("capacity", resource.getCapacity());
					request.setAttribute("officeName", resource.getOfficeName());


					//日時の情報を日付、時間、分に分けてセットする
					Timestamp stopStartDate=resource.getUsageStopStartDate();
					Timestamp stopEndDate=resource.getUsageStopEndDate();

					if(stopStartDate!=null&&stopEndDate!=null){
					SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
					request.setAttribute("stopStartDay", format.format(stopStartDate));
					request.setAttribute("stopEndDay", format.format(stopEndDate));

					format=new SimpleDateFormat("hh");
					request.setAttribute("stopStartHour", format.format(stopStartDate));
					request.setAttribute("stopEndHour", format.format(stopEndDate));

					format=new SimpleDateFormat("mm");
					request.setAttribute("stopStartMinute", format.format(stopStartDate));
					request.setAttribute("stopEndMinute", format.format(stopEndDate));

					request.setAttribute("stopStartDate", resource.getUsageStopStartDate());
					request.setAttribute("stopEndDate", resource.getUsageStopEndDate());
					}

					//選択されているリソース特性の情報をセットする
					List<String> facility = resource.getFacility();
					List<Boolean> selectedFacility = new ArrayList<Boolean>();
					for(String facilityElement:facilityList){
						selectedFacility.add(facility.contains(facilityElement));
					}
					request.setAttribute("selectedFacility", selectedFacility);

					request.setAttribute("supplement", resource.getSupplement());

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
