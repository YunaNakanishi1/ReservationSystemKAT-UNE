package service;

import java.sql.SQLException;

import dao.ResourceDao;

public class DeleteResourceService implements Service{

	private String _resourceId;
	private int  _result;

	public DeleteResourceService(String resourceId){
		_resourceId = resourceId;
	}

	@Override
	public boolean validate(){
		return true;
	}

	public void execute() throws SQLException {
		ResourceDao resourceDao = new ResourceDao();
		_result = resourceDao.delete(_resourceId);
	}

	public int getResult(){
		return _result;
	}

	//テスト用メソッド
	public String getResourceId(){
		return _resourceId;
	}
}
