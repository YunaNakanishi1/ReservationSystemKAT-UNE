package handler;


import static handler.MessageHolder.*;
import static handler.ViewHolder.*;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import service.DeleteResourceService;

public class DeleteResourceHandler implements Handler {
	private static Logger _log = LogManager.getLogger(); //これはクラス図にはないんですが

	public String handleService(HttpServletRequest request) {
		//セッションから権限を取得
        int authority = 0; //0 権限あり 1 なし

      HttpSession httpSession = request.getSession(false);
      //セッションは存在する
      authority = (int) httpSession.getAttribute("authority");

        if (authority == 0) {
        	String deleteId = request.getParameter("resourceId");
        	DeleteResourceService deleteResourceService = new DeleteResourceService(deleteId);

        	if (deleteResourceService.validate()) {
        		try {
        			deleteResourceService.execute();
        			int result = deleteResourceService.getResult();

        			if (result == 1) {
        				request.setAttribute("Pmessage",PM08);
        				return SHOW_RESOURCE_LIST_SERVLET;
        			} else {
        				//ログを残す
        				//deleteフラグ立て失敗
        				_log.error("deleteError");
        				return ERROR_PAGE;
        			}

        		} catch (SQLException e) {
        			//ログを残す
        			_log.error("SQLException");
        			return ERROR_PAGE;
        		}
        	} else {
	        	//ログを残す
        		//validate失敗
        		_log.error("validateError");
	        	return ERROR_PAGE;
        	}
        } else {
        	//ログを残す
        	//authorityエラー
        	_log.error("authorityError");
        	return ERROR_PAGE;
        }

	}

}

