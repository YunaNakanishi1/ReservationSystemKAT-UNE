/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package service;

import java.sql.SQLException;
import java.util.List;

import dto.User;

/**
 * (26).
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
public class ContainSelectedUserService implements Service{
	private String _userId;
	private List<User> _userList;


	public ContainSelectedUserService(String userId,List<User> uesrList){

	}


	@Override
	public boolean validate() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}


	@Override
	public void execute() throws SQLException {
		// TODO 自動生成されたメソッド・スタブ

	}


}
