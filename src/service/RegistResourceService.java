package service;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import dao.ResourceDao;
import dto.Resource;

public class RegistResourceService implements Service{

	private String _validationMessage;
	private Resource _resource;
	private int _result;
	private String _resourceId;

	public RegistResourceService(Resource resource) {
		super();
		_resource = resource;
	}


	@Override
	public boolean validate() {
		ServiceValidator serviceValidator = new ServiceValidator();
		boolean validate = serviceValidator.setResourseDetailValidate(_resource);
		if(!validate){
			_validationMessage=serviceValidator.getValidationMessage();
		}
		return validate;
	}

	@Override
	public void execute() throws SQLException {
		//全リソースIDを取得
		ResourceDao resourceDao=new ResourceDao();
		List<String> resourceIdList=resourceDao.getMaxId();
		//最大のIDを求めるための変数を用意
		BigInteger maxIdInteger = new BigInteger("0");
		//IDの数字部分の最大値を求める
		for(String resourceId:resourceIdList){
			//数字以外の部分を取り除く
			String resourceIdNumber=resourceId.replaceAll("[^0-9]", "");
			try {
				//数字部分を整数に直す
				BigInteger resourceIdInteger = new BigInteger(resourceIdNumber);
				//これまでの最大値より大きければ置き換える
				if(maxIdInteger.compareTo(resourceIdInteger)<0){
					maxIdInteger=resourceIdInteger;
				}
			} catch (NumberFormatException e) {
				//数字に直せないIDは無視
			}
		}
		//IDの数字の最大値に1足す
		maxIdInteger=maxIdInteger.add(new BigInteger("1"));
		//文字列に直す
		String MaxIdNumber = maxIdInteger.toString();
		//最大桁に対して桁数が残っているか調べる
		int leftLength=19-MaxIdNumber.length();
		if(leftLength<=0){
			_resourceId=null;
			return;
		}
		//IDの補完する部分を求める
		String formerOfResourceId="";
		for(int i=0;i<leftLength;i++){
			formerOfResourceId="0"+formerOfResourceId;
		}
		formerOfResourceId="r"+formerOfResourceId;
		//前半部分と数字部分を結合する
		_resourceId=formerOfResourceId+MaxIdNumber;

	}


	public String getValidationMessage() {
		return _validationMessage;
	}


	public int getResult() {
		return _result;
	}


	public String getResourceId() {
		return _resourceId;
	}



}
