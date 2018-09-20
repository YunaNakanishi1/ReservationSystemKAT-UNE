/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package dto;

/**
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
public class User {

	private String _userId;
	private String _password;
	private int _authority;

	public User(String _userId, String _password, int _authority) {
		this._userId = _userId;
		this._password = _password;
		this._authority = _authority;
	}

	public String getUserId() {
		return _userId;
	}

	public String getPassword() {
		return _password;
	}

	public int getAuthority() {
		return _authority;
	}


}
