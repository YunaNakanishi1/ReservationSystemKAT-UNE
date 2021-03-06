/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package handler;


import static handler.MessageHolder.*;
import static handler.ViewHolder.*;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.ReservationDto;
import service.DeleteResourceService;

/**
 * @author リコーITソリューションズ株式会社 KAT-UNE
 * 削除ボタンが押された時の処理を行うためのクラス.
 *
 */
public class DeleteResourceHandler implements Handler {
	private static Logger _log = LogManager.getLogger();

	public String handleService(HttpServletRequest request) {

      HttpSession httpSession = request.getSession(false);
      //セッションは存在する
      int authority = (int) httpSession.getAttribute("authorityOfLoggedIn");
      String deleteId = (String) request.getAttribute("resourceId");

      //セッションからList<ReservationDto>取得
      List<ReservationDto> reservationList=(List<ReservationDto>) httpSession.getAttribute("reservationListForResourceDeleteConfirm");


      CommonValidator commonValidator = new CommonValidator();

	  //resourceIdがあるかチェック
	  if (commonValidator.notSetOn(deleteId)) {
		  _log.error("noResourceId");
          return ERROR_PAGE;
      }

      //権限がある場合
      if (authority == 0) {
    	  DeleteResourceService deleteResourceService = new DeleteResourceService(deleteId,reservationList);

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

