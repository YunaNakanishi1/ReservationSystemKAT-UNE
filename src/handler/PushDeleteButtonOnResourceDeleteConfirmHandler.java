package handler;

import static handler.MessageHolder.*;
import static handler.ViewHolder.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class PushDeleteButtonOnResourceDeleteConfirmHandler implements Handler{

	private static Logger _log = LogManager.getLogger();

	@Override
	public String handleService(HttpServletRequest request) {

	//リクエストからresourceIdをString型で受け取る
	String resourceId = request.getParameter("resourceId");

	//リクエストからチェックボックスの値を取得
	String checkBox =request.getParameter("checkedConfirm");

	if(checkBox != null){

	}else{
		request.setAttribute("messageForResourceDeleteConfirm",EM43);
		return RESOURCE_DELETE_CONFIRM;
	}

	}

}
