package service;

import java.sql.SQLException;

import dao.UserDao;

public class CheckAuthorityService implements Service{
	private String _userId;
	private int _authority;


	public int getAuthority() {
		return _authority;
	}

	public CheckAuthorityService(String _userId) {
		super();
		this._userId = _userId;
	}

	@Override
	public boolean validate() {

		return true;
	}


	@Override
	public void execute() throws SQLException {
		UserDao userDao=new UserDao();
		_authority=userDao.getAuthority(_userId);



	}


}
