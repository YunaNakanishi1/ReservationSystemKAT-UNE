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
	private String _authority;

	public User(String _userId, String _password, String _authority) {
		this._userId = _userId;
		this._password = _password;
		this._authority = _authority;
	}

	public String get_userId() {
		return _userId;
	}

	public String get_password() {
		return _password;
	}

	public String get_authority() {
		return _authority;
	}


}
