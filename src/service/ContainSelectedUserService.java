/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package service;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.User;
import exception.MyException;

/**
 * (8).
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
public class ContainSelectedUserService implements Service{
	private String _userId;
	private List<User> _userList;
	private static Logger _log = LogManager.getLogger();



	public ContainSelectedUserService(String userId,List<User> userList){
		if(_userList == null){
			_log.error("_userList == null");
            throw new MyException();   //エラー処理はハンドラーに任せる
		}

		_userId = userId;
		_userList = userList;
	}


	@Override
	public boolean validate() {
		if(_userId == null){
			return true;
		}else{
			boolean userIdIsTheSame = false;
			for(int i=0; i<_userList.size(); i++){
				User user = _userList.get(i);
				if(user.getUserId().equals(_userId)){
					userIdIsTheSame = true;
				}
			}
			return userIdIsTheSame;
		}
	}


	@Override
	public void execute() throws SQLException {
		// TODO 自動生成されたメソッド・スタブ

	}


}
