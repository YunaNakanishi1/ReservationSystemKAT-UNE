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
 * resourcesテーブルを扱うdaoクラス.
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
public class ResourceDao {

    private static Logger _log = LogManager.getLogger(); // これはクラス図にはないんですが
    Connection _con = null;

    /**
     * リソース全件の一覧を表示するためのDaoメソッド
     *
     * @return リソースのリスト(リソーステーブルが0件の場合、空のリストを返す）
     * @throws SQLException データベースに接続できない場合、SQLの実行に失敗した場合
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


    /**
     * リソース登録の処理を行う.
     * 登録できれば１を返すが、失敗するとSQExceptionを発生する
     *
     * @param resource 入力情報
     * @return　登録件数
     * @throws SQLException　登録の失敗
     */
    public int regist(Resource resource) throws SQLException {
        int result = 0;
        DBHelper dbHelper = new DBHelper();
        _con = dbHelper.connectDb(); // データベースに接続
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        String sql1 = "INSERT INTO resources VALUES(?,?,(SELECT category_id FROM categories WHERE category_name=?),(SELECT office_id FROM offices WHERE office_name=?),?,?,?,?,0);";
        String sql2 = "INSERT INTO resource_features VALUES ((SELECT resource_characteristic_id FROM resource_characteristics WHERE resource_characteristic_name=?),?);";

        //引数がnullの場合、SQLを実行できないので、変更した件数0を返す
        if(resource == null){
            _log.error("resource is null");
            return 0;
        }

        //tryの中に記述されていたので、catch内の処理のロールバックでNullPointerExceptionが発生したため外に出した
        if(_con==null){
            _log.error("DatabaseConnectError");
            throw new SQLException();
        }
        try{

            _con.setAutoCommit(false);
            //リソーステーブルへの登録
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
            //リソース特性対応テーブル（設備）への登録
            stmt2=_con.prepareStatement(sql2);
            stmt2.setString(2, resource.getResourceId());
            for(String facilityElement:resource.getFacility()){
                stmt2.setString(1, facilityElement);
                stmt2.executeUpdate();
            }
            _con.commit();
        }catch(SQLException e){
            //ロールバックして例外を投げなおす
            _con.rollback();
            throw e;
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

    /**
     * リソースの変更処理を行う.
     * 登録できれば１を返すが、失敗すると０を返す
     * @param resource 入力情報
     * @return 変更した件数
     * @throws SQLException 変更に失敗した場合
     */
    public int change(Resource resource) throws SQLException{
        int result = 0;
        DBHelper dbHelper = new DBHelper();
        _con = dbHelper.connectDb(); // データベースに接続
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        PreparedStatement stmt3 = null;

        String sql1 = "UPDATE resources SET resource_name=?,category_id=(SELECT category_id FROM categories WHERE category_name=?),office_id=(SELECT office_id FROM offices WHERE office_name=?),capacity=?,supplement=?,usage_stop_start_date=?,usage_stop_end_date=? WHERE resource_id=? and deleted=0;";
        String sql2 = "DELETE FROM resource_features WHERE resource_id=?;";
        String sql3 = "INSERT INTO resource_features VALUES ((SELECT resource_characteristic_id FROM resource_characteristics WHERE resource_characteristic_name=?),?);";
        //引数がnullの場合、SQLを実行できないので、変更した件数0を返す
        if(resource == null){
            _log.error("resource is null");
            return 0;
        }
        //tryの中に記述されていたので、catch内の処理のロールバックでNullPointerExceptionが発生したため外に出した
        if(_con==null){
            _log.error("DatabaseConnectError");
            throw new SQLException();
        }
        try{

            _con.setAutoCommit(false);
            //リソーステーブルの変更
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

            //リソーステーブルの変更に成功した場合
            //stmt2 リソース特性対応テーブルの削除を行う
            //stmt3 リソース特性対応テーブルの登録を行う
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
            }else{
                //変更件数が1でなければロールバック
                _con.rollback();
            }
        }catch(SQLException e){
            //ロールバックして例外を投げなおす
            _con.rollback();
            throw e;
        }
        finally{
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
     * 指定されたresourceIdのdeleteを1に書き換えるメソッド.
     *
     * @param resourceId 削除したいリソースID
     * @return 削除結果（1だと成功）
     * @throws SQLException 変更に失敗した場合
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
     * リソーステーブルと引数のIDと一致するレコードを取得する
     *
     * @param resourceId リソーステーブルのresource_id
     * @return リソーステーブルと引数が一致するレコードをResourceとしてリターン　
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
                    + "capacity,usage_stop_start_date,usage_stop_end_date,supplement,category_name,deleted "
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
            int deleted = 0;//追加
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
                deleted = rs.getInt("deleted");//追記

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

                resource = new Resource(resourceId, resourceName, officeName, category, capacity, supplement, deleted,
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

    /**
     * リソーステーブルの IDの最大値を返す
     *
     *
     * @return IDの最大値,リソースがなければnull
     * @throws SQLException 取得失敗
     */
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
    /**
     * 検索条件からリソースの一覧を返す
     * @param capacity
     * @param resourceName
     * @param categoryId
     * @param officeId
     * @param facilityIdList
     * @return
     * @throws SQLException
     */
    public List<Resource> queryByInput(int capacity,String resourceName, String categoryId, String officeId,  List<String> facilityIdList) throws SQLException{
        List<Resource> resources = new ArrayList<>();
        DBHelper dbHelper = new DBHelper();
        PreparedStatement pstmt=null;
        ResultSet rs=null;



        try{
            _con = dbHelper.connectDb(); // データベースに接続

            if (_con == null) {
                _log.error("DatabaseConnectError");
                throw new SQLException();
            }

        //SQL文生成
            //リソース特性のパラメータ（facility_id_[n])
            String facilityIdParams = "";
            for (int i=0;i < facilityIdList.size();i++) {
                facilityIdParams += "? as facility_id_"+i+", ";
            }
            //SQL文(With句)
            String sqlWith = "with params as( "
                    +"select "
                    + facilityIdParams
                    +"? as facility_checked_num, "
                    +"? as resource_name, "
                    +"? as category_id, "
                    +"? as office_id, "
                    +"? as capacity "
                    +") ";



            //SQL文（Select句）
            String sqlSelect = "select * from resources,offices,categories,params where offices.office_id = resources.office_id and categories.category_id=resources.category_id ";
            if(resourceName != null){
                //リソース名が指定されている
                sqlSelect += "and resource_name like (params.resource_name) ";
            }
            if(categoryId != null){
                //カテゴリIDが指定されている
                sqlSelect += "and category_id = params.category_id ";
            }
            if(officeId != null){
                //オフィスIDが指定されている
                sqlSelect += "office_id = params.office_id ";
            }

            //定員
            sqlSelect += "and capacity >= params.capacity ";

            if(facilityIdList.size() > 0){
                //リソース特性IDが指定されている
                sqlSelect += "and resource_id in ( "
                        +"select resource_features.resource_id "
                        +"from resources "
                        +"inner join resource_features "
                        +"on resources.resource_id = resource_features.resource_id "
                        +"where 1=0 ";
                for(int i=0;i<facilityIdList.size();i++){
                    sqlSelect += "or resource_characteristic_id = params.facility_id_"+i+" ";
                }
                sqlSelect += "group by resource_features.resource_id having count(*) >= params.facility_checked_num";

            }
            //SQL文作成
            String sql = sqlWith + sqlSelect;
        //パラメータセット
            //preparedStatement用カウント変数
            int pCount = 1;
            pstmt = _con.prepareStatement(sql);
            //リソース特性の?セット
            for (int i=0;i < facilityIdList.size();i++) {
                pstmt.setString(pCount++, facilityIdList.get(i));
            }
            //リソース特性の検索数をセット
            pstmt.setInt(pCount++, facilityIdList.size());

            //リソース名
            pstmt.setString(pCount++, "%"+resourceName+"%");


            //カテゴリID
            pstmt.setString(pCount++, categoryId);

            //オフィスID
            pstmt.setString(pCount++, officeId);

            //定員
            pstmt.setInt(pCount++, capacity);

          //実行
            rs=pstmt.executeQuery(sql);
            if(rs.next()){
                String resourceIdResult = rs.getString("resource_id");
                String resourceNameResult = rs.getString("resource_name");
                String officeNameResult = rs.getString("office_name");
                String categoryResult = rs.getString("category_name");
                int capacityResult = rs.getInt("capacity");
                String supplementResult = rs.getString("supplement");
                int deletedResult = rs.getInt("deleted");
                Timestamp usageStopStartDateResult = rs.getTimestamp("usage_stop_start_date");
                Timestamp usageStopEndDateResult = rs.getTimestamp("usage_stop_end_date");

                Resource resource = new Resource(resourceIdResult, resourceNameResult, officeNameResult, categoryResult, capacityResult, supplementResult, deletedResult, null, usageStopStartDateResult, usageStopEndDateResult);
                resources.add(resource);
            }
        }finally{
            try{
                dbHelper.closeResource(rs);
            }catch(Exception e1){
                e1.printStackTrace();
                _log.error("queryByInput() Exception e1");
            }
            try{
                dbHelper.closeResource(pstmt);
            }catch(Exception e2){
                e2.printStackTrace();
                _log.error("queryByInput() Exception e2");
            }
            dbHelper.closeDb();
        }


        return resources;
    }
}

