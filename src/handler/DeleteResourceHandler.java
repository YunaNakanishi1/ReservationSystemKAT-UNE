package handler;


import static handler.MessageHolder.*;
import static handler.ViewHolder.*;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import service.DeleteResourceService;

public class DeleteResourceHandler implements Handler {
	public String handleService(HttpServletRequest request) {
		//セッションから権限を取得
        int authority = 0; //0 権限あり 1 なし

//      HttpSession httpSession = request.getSession(false);
//
//      //セッションは存在する
//      authority = (int) httpSession.getAttribute("authority");

        if (authority == 0) {

        	DeleteResourceService deleteResourceService = new DeleteResourceService(request.getParameter("resourceId"));

        	if (deleteResourceService.validate()) {

        		try {
        			deleteResourceService.execute();
        			int result = deleteResourceService.getResult();

        			if(result == 1){
        				request.setAttribute("Pmessage",PM08);
        				return SHOW_RESOURCE_LIST_SERVLET;
        			}else{
        				//ログを残す
        				return ERROR_PAGE;
        			}

        		} catch(SQLException e) {
        			//ログを残す
        			return ERROR_PAGE;
        		}
        	}
        }else{
        	//ログを残す
        	return ERROR_PAGE;
        }
		return null;
	}
}
