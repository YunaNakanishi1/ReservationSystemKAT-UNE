/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

	private static Logger _log = LogManager.getLogger(); // これはクラス図にはないんですが
	Connection _con = null;

	/**
	 * リソース全件の一覧を表示するためのDaoメソッド.
	 *
	 * @return resourceを返す
	 */
	public List<Resource> displayAll() throws SQLException {
		DBHelper dbHelper = new DBHelper();
		Statement stmt = null;
		ResultSet rs = null;

		// 空のresourceListを用意
		List<Resource> resourceList = new ArrayList<Resource>();

		try {
			_con = dbHelper.connectDb(); // データベースに接続

			String sql = "select resources.resource_id,resource_name,office_name,"
					+ "category_name,capacity,supplement,usage_stop_start_date," + "usage_stop_end_date,deleted "
					+ "from resources,categories,offices " + "where resources.category_id = categories.category_id and "
					+ "resources.office_id = offices.office_id "
					+ "order by offices.office_id asc , categories.category_id asc " + ", resources.resource_id asc;";

			if (_con == null) {
				_log.error("DatabaseConnectError");
				throw new SQLException();
			}
			stmt = _con.createStatement();
			rs = stmt.executeQuery(sql); // 実行

			while (rs.next()) {
				String resourceId = rs.getString("resource_id");
				String resourceName = rs.getString("resource_name");
				String officeName = rs.getString("office_name");
				String categoryName = rs.getString("category_name");
				int capacity = rs.getInt("capacity");
				String supplement = rs.getString("supplement");
				int deleted = rs.getInt("deleted");
				Timestamp usageStopStartDate = rs.getTimestamp("usage_stop_start_date");
				Timestamp usageStopEndDate = rs.getTimestamp("usage_stop_end_date");

				// リストに追加
				resourceList.add(new Resource(resourceId, resourceName, officeName, categoryName, capacity, supplement,
						deleted, null, usageStopStartDate, usageStopEndDate));

			}

		} finally {
			// Statementのクローズ
			try {
				dbHelper.closeResource(stmt);
			} catch (Exception e1) {
				e1.printStackTrace();
				_log.error("displayAll() Exception e1");
			}

			// ResultSetのクローズ
			try {
				dbHelper.closeResource(rs);
			} catch (Exception e2) {
				e2.printStackTrace();
				_log.error("displayAll() Exception e2");
			}

			// ヘルパーに接続解除を依頼
			dbHelper.closeDb();
		}
		return resourceList;
	}

	public int regist(Resource resource) throws SQLException {
		int result = 0;
		DBHelper dbHelper = new DBHelper();
		_con = dbHelper.connectDb(); // データベースに接続
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		String sql1 = "INSERT INTO resources VALUES(?,?,(SELECT category_id FROM categories WHERE category_name=?),(SELECT office_id FROM offices WHERE office_name=?),?,?,?,?,0);";
		String sql2 = "INSERT INTO resource_features VALUES ((SELECT resource_characteristic_id FROM resource_characteristics WHERE resource_characteristic_name==?),?);";
		try{
			if(_con==null){
				_log.error("DatabaseConnectError");
				throw new SQLException();
			}
			_con.setAutoCommit(false);
			stmt1=_con.prepareStatement(sql1);
			stmt1.setString(1, resource.getResourceId());
			stmt1.setString(2, resource.getResourceName());
			stmt1.setString(3, resource.getCategory());
			stmt1.setString(4, resource.getOfficeName());
			stmt1.setInt(5,resource.getCapacity());
			stmt1.setString(6, resource.getSupplement());
			stmt1.setTimestamp(7, resource.getUsageStopStartDate());
			stmt1.setTimestamp(8, resource.getUsageStopEndDate());
			result=stmt1.executeUpdate();
			stmt2=_con.prepareStatement(sql2);
			stmt2.setString(2, resource.getResourceId());
			for(String facilityElement:resource.getFacility()){
				stmt2.setString(1, facilityElement);
				stmt2.executeUpdate();
			}
			_con.commit();
		}finally{
			try{
				dbHelper.closeResource(stmt1);
			}catch(Exception e){
				e.printStackTrace();
				_log.error("regist() Exception e");
			}
			try{
				dbHelper.closeResource(stmt2);
			}catch(Exception e){
				e.printStackTrace();
				_log.error("regist() Exception e");
			}
			dbHelper.closeDb();
		}
		return result;
	}

	public int change(Resource resource) throws SQLException{
		int result = 0;
		DBHelper dbHelper = new DBHelper();
		_con = dbHelper.connectDb(); // データベースに接続
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		PreparedStatement stmt3 = null;

		String sql1 = "UPDATE resources SET resource_name=?,category_id=(SELECT category_id FROM categories WHERE category_name=?),office_id=(SELECT office_id FROM offices WHERE office_name=?),capacity=?,supplement=?,usage_stop_start_date=?,usage_stop_end_date=? WHERE resource_id=? and deleted=0;";
		String sql2 = "DELETE FROM resource_features WHERE resource_id=?;";
		String sql3 = "INSERT INTO resource_features VALUES ((SELECT resource_characteristic_id FROM resource_characteristics WHERE resource_characteristic_name==?),?);";
		try{
			if(_con==null){
				_log.error("DatabaseConnectError");
				throw new SQLException();
			}
			_con.setAutoCommit(false);
			stmt1=_con.prepareStatement(sql1);
			stmt1.setString(8, resource.getResourceId());
			stmt1.setString(1, resource.getResourceName());
			stmt1.setString(2, resource.getCategory());
			stmt1.setString(3, resource.getOfficeName());
			stmt1.setInt(4,resource.getCapacity());
			stmt1.setString(5, resource.getSupplement());
			stmt1.setTimestamp(6, resource.getUsageStopStartDate());
			stmt1.setTimestamp(7, resource.getUsageStopEndDate());
			result=stmt1.executeUpdate();
			if (result == 1) {
				stmt2=_con.prepareStatement(sql2);
				stmt2.setString(1, resource.getResourceId());
				stmt2.executeUpdate();
				stmt3=_con.prepareStatement(sql3);
				stmt3.setString(2, resource.getResourceId());
				for(String facilityElement:resource.getFacility()){
					stmt3.setString(1, facilityElement);
					stmt3.executeUpdate();
				}
				_con.commit();
			}

		}finally{
			try{
				dbHelper.closeResource(stmt1);
			}catch(Exception e){
				e.printStackTrace();
				_log.error("regist() Exception e");
			}
			try{
				dbHelper.closeResource(stmt2);
			}catch(Exception e){
				e.printStackTrace();
				_log.error("regist() Exception e");
			}
			try{
				dbHelper.closeResource(stmt3);
			}catch(Exception e){
				e.printStackTrace();
				_log.error("regist() Exception e");
			}
			dbHelper.closeDb();
		}
		return result;
	}

	/**
	 * 指定されたresourceIdのdeleteを1に書き換える.
	 *
	 * @param resourceId 削除したいリソースID
	 * @return 削除結果（1だと成功）
	 * @throws SQLException
	 */
	public int delete(String resourceId) throws SQLException {
		int result = 0;
		DBHelper dbHelper = new DBHelper();
		_con = dbHelper.connectDb(); // データベースに接続
		PreparedStatement stmt = null;
		String sql = "UPDATE resources SET deleted = 1 WHERE resource_id = ?";

		try {
			if (_con == null) {
				_log.error("DatabaseConnectError");
				throw new SQLException();
			}
			stmt = _con.prepareStatement(sql);
			stmt.setString(1, resourceId);
			result = stmt.executeUpdate();

		} finally {
			// Statementのクローズ
			try {
				dbHelper.closeResource(stmt);
			} catch (Exception e1) {
				e1.printStackTrace();
				_log.error("displayAll() Exception e1");
			}

			// ヘルパーに接続解除を依頼
			dbHelper.closeDb();
		}

		return result;
	}

	/**
	 * リソース詳細を表示するために必要なDao.
	 *
	 * @param resourceId
	 * @return resourceをリターン
	 * @throws SQLException
	 */
	public Resource displayDetails(String resourceId) throws SQLException {
		DBHelper dbHelper = new DBHelper();

		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		// PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		// ResultSet rs3 = null;

		boolean foundResource = false;

		Resource resource = null;

		try {
			_con = dbHelper.connectDb(); // データベースに接続

			if (_con == null) {
				_log.error("DatabaseConnectError");
				throw new SQLException();
			}

			final String sql = "select resource_name,office_name,"
					+ "capacity,usage_stop_start_date,usage_stop_end_date,supplement,category_name "
					+ "from resources,offices,categories "
					+ "where offices.office_id = resources.office_id and categories.category_id=resources.category_id "
					+ "and resource_id = ?;";

			final String sql2 = "select resource_characteristic_name "
					+ "from resource_features,resource_characteristics " + "where resource_id=? and "
					+ "resource_features.resource_characteristic_id "
					+ "= resource_characteristics.resource_characteristic_id "
					+ "order by resource_features.resource_characteristic_id;";

			String resourceName = "";
			String officeName = "";
			int capacity = 0;
			String category = "";
			String supplement = "";
			Timestamp usageStopStartDate = null;
			Timestamp usageStopEndDate = null;

			List<String> facilityList = new ArrayList<String>();

			// 選んだリソースの詳細を表示するためにIDをもとにデータベースからresourceDTO
			// をとってくる
			pstmt = _con.prepareStatement(sql);
			pstmt.setString(1, resourceId);

			rs = pstmt.executeQuery(); // 実行

			if (rs.next() == true) {
				resourceName = rs.getString("resource_name");
				officeName = rs.getString("office_name");
				capacity = rs.getInt("capacity");
				supplement = rs.getString("supplement");
				usageStopStartDate = rs.getTimestamp("usage_stop_start_date");
				usageStopEndDate = rs.getTimestamp("usage_stop_end_date");
				category = rs.getString("category_name");// 追記

				foundResource = true; // ResourceIdが一致したのでtrueで上書き
			}

			// ResourceIdが一致した場合は設備のリストを作成してクリエイト
			if (foundResource == true) {
				// 設備表示のために設備のリストを作成
				pstmt2 = _con.prepareStatement(sql2);
				pstmt2.setString(1, resourceId);

				rs2 = pstmt2.executeQuery(); // 実行

				while (rs2.next()) {
					String facility = rs2.getString("resource_characteristic_name");
					facilityList.add(facility);
				}

				resource = new Resource(resourceId, resourceName, officeName, category, capacity, supplement, 0,
						facilityList, usageStopStartDate, usageStopEndDate);
			}

			return resource;// データベースのresorce_idと一致していない場合はnullが返る

		} finally {
			// PreparedStatementのクローズ
			try {
				dbHelper.closeResource(pstmt);
			} catch (Exception e3) {
				e3.printStackTrace();
				_log.error("displayAll() Exception e3");
			}

			// ResultSetのクローズ
			try {
				dbHelper.closeResource(rs);
			} catch (Exception e4) {
				e4.printStackTrace();
				_log.error("displayAll() Exception e4");
			}

			// PreparedStatementのクローズ
			try {
				dbHelper.closeResource(pstmt2);
			} catch (Exception e5) {
				e5.printStackTrace();
				_log.error("displayAll() Exception e3");
			}

			// ResultSetのクローズ
			try {
				dbHelper.closeResource(rs2);
			} catch (Exception e6) {
				e6.printStackTrace();
				_log.error("displayAll() Exception e4");
			}
			// ヘルパーに接続解除を依頼
			dbHelper.closeDb();
		}
	}

	public String getMaxId() throws SQLException{
		DBHelper dbHelper = new DBHelper();
		Statement stmt=null;
		ResultSet rs=null;
		String sql = "SELECT MAX(resource_id) from resources;";
		String maxId = null;
		try{
			_con = dbHelper.connectDb(); // データベースに接続

			if (_con == null) {
				_log.error("DatabaseConnectError");
				throw new SQLException();
			}
			stmt=_con.createStatement();
			rs=stmt.executeQuery(sql);
			if(rs.next()){
				maxId=rs.getString(1);
			}
		}finally{
			try{
				dbHelper.closeResource(rs);
			}catch(Exception e1){
				e1.printStackTrace();
				_log.error("getMaxId() Exception e1");
			}
			try{
				dbHelper.closeResource(stmt);
			}catch(Exception e2){
				e2.printStackTrace();
				_log.error("getMaxId() Exception e2");
			}
			dbHelper.closeDb();
		}
		return maxId;
	}
}
