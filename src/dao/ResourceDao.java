/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.Resource;

/**
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
public class ResourceDao {

	private static Logger _log = LogManager.getLogger(); //これはクラス図にはないんですが
	Connection _con = null;

	/**
	  * リソース全件の一覧を表示するためのDaoメソッド.
	  * @return resourceを返す
	  */
	public List<Resource> displayAll() throws SQLException{
		DBHelper dbHelper = new DBHelper();

		_con = dbHelper.connectDb(); //データベースに接続

		String sql = "select resources.resource_id,resource_name,office_name,"
				+ "category_name,capacity,supplement,usage_stop_start_date,"
				+ "usage_stop_end_date,deleted "
				+ "from resources,categories,offices "
				+ "where resources.category_id = categories.category_id , "
				+ "resources.office_id = officers.office_id "
				+ "order by officers.office_id asc and categories.category_id asc "
				+ "and resources.resource_id asc;";

		Statement stmt = null;
		ResultSet rs = null;

		//空のresourceListを用意
        List<Resource> resourceList = new ArrayList<Resource>();

		 if (_con != null) {
	            try {
	                stmt = _con.createStatement();
	                rs = stmt.executeQuery(sql); //実行

	                while(rs.next()){
	                	String resourceId = rs.getString("resource_id");
	                	String resourceName = rs.getString("resource_name");
	                	String officeName = rs.getString("office_name");
	                	String categoryName = rs.getString("category_name");
	                	int capacity = rs.getInt("capacity");
	                	String supplement = rs.getString("spplement");
	                	int deleted = rs.getInt("deleted");
	                	Timestamp usageStopStartDate = rs.getTimestamp("usage_stop_start_date");
	                	Timestamp usageStopEndDate = rs.getTimestamp("usage_stop_end_date");

	                	//リストに追加
	                	resourceList.add(new Resource(resourceId, resourceName, officeName,
	                			categoryName, capacity, supplement, deleted, null,
	                			usageStopStartDate, usageStopEndDate));

	                }
	            } finally {
	                // Statementのクローズ
	            	try {
						dbHelper.closeResource(stmt);
					} catch (Exception e1) {
						e1.printStackTrace();
						 _log.error("displayAll() Exception e1");
					}
	            	try {
						dbHelper.closeResource(rs);
					} catch (Exception e2) {
						e2.printStackTrace();
						_log.error("displayAll() Exception e2");
					}

	                // ヘルパーに接続解除を依頼
	                dbHelper.closeDb();
	            }
	        }
 		return resourceList;
	}


	public int regist(Resource resource) {
		return 0;
	}

	public int change(Resource resource) {
		return 0;
	}

	public int delete(String resourceId) {
		return 0;
	}

	public Resource displayDetails(String resourceId) {
		return null;
	}

	public String getMaxId() {
		return null;
	}
}
