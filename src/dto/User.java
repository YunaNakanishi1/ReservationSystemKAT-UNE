/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package dto;

/**
 *
 * データベースのユーザ情報を格納するクラス.
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 */
public class User {

	private String _userId;
	private String _password;
	private int _authority;
	private String _familyName;
	private String _firstName;
	private String _phoneNumber;
	private String _mailAddress;




	public User(String _userId, String _password, int _authority, String _familyName, String _firstName,
			String _phoneNumber, String _mailAddress) {
		super();
		this._userId = _userId;
		this._password = _password;
		this._authority = _authority;
		this._familyName = _familyName;
		this._firstName = _firstName;
		this._phoneNumber = _phoneNumber;
		this._mailAddress = _mailAddress;
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

	public String getFamilyName() {
		return _familyName;
	}

	public String getFirstName() {
		return _firstName;
	}


	public String getPhoneNumber() {
		return _phoneNumber;
	}


	public String getMailAddress() {
		return _mailAddress;
	}


}
