package service;

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

	public void execute(){
	}

	public int getResult(){
		return 1;
	}

}
