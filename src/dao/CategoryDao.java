/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Categoriesテーブルを扱うdaoクラス.
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
public class CategoryDao {

	private Connection _con=null;
	private static Logger _log = LogManager.getLogger();


	/**
	 * カテゴリを全件取得するメソッド.
	 *
	 * @return 文字列のリスト(カテゴリテーブルが0件の場合、空のリストを返す）
	 * @throws SQLException データベースに接続できない場合、SQLの実行に失敗した場合
	 */
	public List<String> category() throws SQLException {
		List<String> categoryList=new ArrayList<String>();
		DBHelper dbHelper =new DBHelper();

			_con=dbHelper.connectDb();

			String sql="select category_name from categories order by category_id";

			Statement stmt=null;
			ResultSet rs=null;


			try{

				if (_con == null) {
					_log.error("DatabaseConnectError");
					throw new SQLException();
				}
				stmt=_con.createStatement();
				rs=stmt.executeQuery(sql);

				while(rs.next()){
					categoryList.add(rs.getString(1));
				}
			}finally{
				try{
					dbHelper.closeResource(stmt);

				}catch(Exception e1){
					e1.printStackTrace();
					 _log.error("category() Exception e1");
				}

				try {
					dbHelper.closeResource(rs);
				} catch (Exception e2) {
					e2.printStackTrace();
					_log.error("category() Exception e2");
				}
				 dbHelper.closeDb();
			}



		return categoryList;
	}
}
