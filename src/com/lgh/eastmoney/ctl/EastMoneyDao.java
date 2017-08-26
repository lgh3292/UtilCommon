package com.lgh.eastmoney.ctl;

import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import oracle.sql.BLOB;

import com.lgh.eastmoney.bo.EastMoneyRiseDrop;
import com.lgh.eastmoney.bo.EastMoneyRiseDropHistory;
import com.lgh.eastmoney.bo.EastMoneyStock;
import com.lgh.eastmoney.bo.EastMoneyVolume;
import com.lgh.eastmoney.bo.EastMoneyVolumeDetail;
import com.lgh.util.dao.BaseDao;
import com.lgh.util.db.DBConnectionPoolManager;
import com.lgh.util.logging.LogUtil;

/**
 * a class that use to save/update the EAST_MONEY_VOLUME
 * @author liuguohu
 *
 */
public class EastMoneyDao extends BaseDao{
	//table:east_money_volume
	private static String east_money_volume_detail = "east_money_volume_detail";
//	private static String tableName = "east_money_volume_temp";
	private static Class<EastMoneyVolumeDetail> EAST_MONEY_VOLUME_DETAIL_CLASS = EastMoneyVolumeDetail.class;

	private static EastMoneyDao EAST_MONEY_DAO = new EastMoneyDao();
	public static EastMoneyDao getInstance(){
		if(EAST_MONEY_DAO==null){
			EAST_MONEY_DAO = new EastMoneyDao();
		}
		return EAST_MONEY_DAO;
	}
	private EastMoneyDao(){
		
	}
	/**
	 * insert the FlagReason object
	 * 
	 * @param flagReason
	 * @return
	 * @throws NonSupportException
	 * @throws SQLException 
	 * @throws FraudDaoException 
	 */
	public void insertEastMoneyVolumeDetail(Object entity,Connection con) throws Exception{
		long emVolumeId = getEastMoneyVolumeDetailSequenceCurrval(con);
		String sqlStr = "insert into " + east_money_volume_detail + "(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE,EM_DATE_ID)";
		sqlStr += " values("+emVolumeId+",?,empty_blob(),?,?)";
		String[] fieldNames = new String[]{"emStockId","emVolumeDate","emDateId"};
		save(con,entity, EAST_MONEY_VOLUME_DETAIL_CLASS, sqlStr, fieldNames);
		updateEastMoneyVolumeDetailBLOB(con,emVolumeId,entity);
	}
	
	/**
	 * update the blob by the emVolumeId
	 * @param con
	 * @param emVolumeId
	 * @throws Exception
	 */
	public void updateEastMoneyVolumeDetailBLOB(Connection con,long emVolumeId,Object entity) throws Exception{
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select EM_VOLUME_DETAIL from "+east_money_volume_detail+" where em_Volume_Id = "+emVolumeId+" for update");
		if(rs.next()){
			oracle.sql.BLOB blob = (BLOB) rs.getBlob(1);
			OutputStream os = blob.getBinaryOutputStream();
			EastMoneyVolumeDetail emv = (EastMoneyVolumeDetail)entity;
			os.write(emv.getEmVolumeDetail(), 0, emv.getEmVolumeDetail().length);
			os.flush();
			os.close();
		}
	}
	

	/**
	 * find by EastMoneyVolume
	 * @param eastMoneyVolumeDetail
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public List<?> findEastMoneyVolumeDetail(EastMoneyVolumeDetail eastMoneyVolumeDetail,Connection con) throws Exception {
		StringBuffer sb = new StringBuffer();
		String[] columns = new String[]{"EM_VOLUME_ID","EM_STOCK_ID","EM_VOLUME_DETAIL","EM_VOLUME_DATE","EM_DATE_ID"};
		
		String[] fieldNames = new String[]{"emVolumeId","emStockId","emVolumeDetail","emVolumeDate","emDateId"};
		
		sb.append("SELECT EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE,EM_DATE_ID FROM "+ east_money_volume_detail+" WHERE 1=1 ");
		String emStockId = eastMoneyVolumeDetail.getEmStockId();
		if(!nullOrTrimNull(emStockId)){
			sb.append(" and EM_STOCK_ID = "+emStockId);
		}
		 
		return find(con, sb.toString(), columns, fieldNames,EAST_MONEY_VOLUME_DETAIL_CLASS);
	}
	
	
	/**
	 * get currval sequence
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public long getEastMoneyVolumeDetailSequenceCurrval(Connection con) throws SQLException{
		StringBuffer sb = new StringBuffer();
		sb.append("select EAST_MONEY_VOLUME_DETAIL_SEQ.nextval as nextval from dual");
		return find(con, sb.toString(), "nextval");
	}
	
	/********************************************************************************
	***************************east_money_volume************************************
	********************************************************************************/
	//table:east_money_volume
	private static String east_money_volume = "east_money_volume";
	private static Class<EastMoneyVolume> EAST_MONEY_VOLUME_CLASS = EastMoneyVolume.class;
	
	public void insertEastMoneyVolume(Object entity,Connection con) throws Exception{
		String sqlStr = "insert into " + east_money_volume + "(em_stock_id,trans_in_money,trans_out_money,trans_in_volume,trans_out_volume,em_date_id)";
		sqlStr += " values(?,?,?,?,?,?)";
		String[] fieldNames = new String[]{
				"emStockId","transInMoney","transOutMoney","transInVolume","transOutVolume","emDateId"};
		save(con,entity, EAST_MONEY_VOLUME_CLASS, sqlStr, fieldNames);
	}
 
	/**
	 * 
	 * @param EastMoneyVolume
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public long findCountByEastMoneyVolume(EastMoneyVolume eastMoneyVolume,Connection con) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) as count from "+east_money_volume+" where 1=1 ");
		int emDateId = eastMoneyVolume.getEmDateId();
		if(!isNull(emDateId)){	
			sb.append(" and em_date_id="+emDateId);
		}
		return find(con, sb.toString(), "count");
	}
	/**
	 * find the runned stocks current day查找某天的所有的股票ID
	 * @param emDateId
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public List<?> findRunnedEastMoneyVolume(long emDateId,Connection con)throws Exception{
		StringBuffer sb = new StringBuffer();
		String[] columns = new String[]{"EM_STOCK_ID"};
		String[] fieldNames = new String[]{"emStockId"};
		sb.append("SELECT EM_STOCK_ID FROM "+east_money_volume+" WHERE 1=1 ");
		if(emDateId!=0){
			sb.append(" and em_date_id ="+emDateId);
		}
		return find(con, sb.toString(), columns, fieldNames,EAST_MONEY_VOLUME_CLASS);
	}
	
	/********************************************************************************
	********************************esat_money_risedrop**********************************
	********************************************************************************/
	
	
	//table:esat_money_risedrop
	private static String esat_money_risedrop = "east_money_risedrop";
	private static Class<EastMoneyRiseDrop> EAST_MONEY_RISEDROP_CLASS = EastMoneyRiseDrop.class;
	
	/**
	 * insert the EastMoneyRiseDrop objects
	 * @param entity
	 * @param con
	 * @throws Exception
	 */
	public void insertEastMoneyRiseDrop(Object entity,Connection con) throws Exception{
		String sqlStr = "insert into " + esat_money_risedrop + "(code,em_stock_id,em_stock_name,yesterday_stop,today_start,newest_price,"+     
				"higest_price,lowest_price,trans_money,trans_volume,rise_drop_money,"+  
				"rise_drop_scope,average_price,scope,weibi,weicha,xianshou,"+
				"neiPan,waiPan,xianshou_flag,unknow1,fiveMinRise_drop,"+
				"liangbi,change_rate,city_earnings,unknow2,buy1,"+
				"sell1,trans_date,unknow3,unknow4)";
		sqlStr += " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String[] fieldNames = new String[]{
				"code","emStockId","emStockName","yesterdayStop","todayStart","newestPrice","higestPrice",
				"lowestPrice","transMoney","transVolume","riseDropMoney","riseDropScope","averagePrice",
				"scope","weibi","weicha","xianshou","neiPan","waiPan","xianshouFlag","unknow1",
				"fiveMinRiseDrop","liangbi","changeRate","cityEarnings","unknow2","buy1","sell1",
				"transDate","unknow3","unknow4"};
		save(con,entity, EAST_MONEY_RISEDROP_CLASS, sqlStr, fieldNames);
	}

	
	/**
	 * 
	 * @param eastMoneyRiseDrop
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public long findCountByEastMoneyRiseDrop(EastMoneyRiseDrop eastMoneyRiseDrop,Connection con) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) as count from "+esat_money_risedrop+" where 1=1 ");
		Long transDate = eastMoneyRiseDrop.getTransDate();
		if(!isNull(transDate)){	
			//sb.append("and to_char(trans_date,'yyyymmdd')=to_char(to_date('"+DateUtil.getTimestampToString("yyyyMMdd", transDate)+"','yyyymmdd'),'yyyymmdd')");
			sb.append(" and trans_date="+transDate);
		}
		return find(con, sb.toString(), "count");
	}
	
	/**
	 * find all EastMoneyStocks
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public List<?> findEastMoneyRiseDrop(EastMoneyRiseDrop entity,Connection con)throws Exception{
		StringBuffer sb = new StringBuffer();
		String[] columns = new String[]{"code","em_stock_id","em_stock_name","yesterday_stop","today_start","newest_price",""+     
				"higest_price","lowest_price","trans_money","trans_volume","rise_drop_money",""+  
				"rise_drop_scope","average_price","scope","weibi","weicha","xianshou",""+
				"neiPan","waiPan","xianshou_flag","unknow1","fiveMinRise_drop",""+
				"liangbi","change_rate","city_earnings","unknow2","buy1",""+
				"sell1","trans_date","unknow3","unknow4"};
		String[] fieldNames = new String[]{"code","emStockId","emStockName","yesterdayStop","todayStart","newestPrice","higestPrice",
				"lowestPrice","transMoney","transVolume","riseDropMoney","riseDropScope","averagePrice",
				"scope","weibi","weicha","xianshou","neiPan","waiPan","xianshouFlag","unknow1",
				"fiveMinRiseDrop","liangbi","changeRate","cityEarnings","unknow2","buy1","sell1",
				"transDate","unknow3","unknow4"};
		sb.append("SELECT code,em_stock_id,em_stock_name,yesterday_stop,today_start,newest_price,"+     
				"higest_price,lowest_price,trans_money,trans_volume,rise_drop_money,"+  
				"rise_drop_scope,average_price,scope,weibi,weicha,xianshou,"+
				"neiPan,waiPan,xianshou_flag,unknow1,fiveMinRise_drop,"+
				"liangbi,change_rate,city_earnings,unknow2,buy1,"+
				"sell1,trans_date,unknow3,unknow4 from "+esat_money_risedrop+" WHERE 1=1 ");
		if(entity!=null){
			if(!nullOrTrimNull(entity.getEmStockId())){
				sb.append(" and em_stock_id='"+entity.getEmStockId()+"'");
			}
			if(!isNull(entity.getTransStartDate())){
				sb.append(" and trans_date>="+entity.getTransStartDateToInt());
			}
			if(!isNull(entity.getTransEndDate())){
				sb.append(" and trans_date<="+entity.getTransEndDateToInt());
			}
		}
		sb.append(" order by trans_date");
		return find(con, sb.toString(), columns, fieldNames,EAST_MONEY_RISEDROP_CLASS);
	}
	/**
	 * insert object by the Class type
	 * @param object
	 * @param cls
	 * @param con
	 * @throws Exception 
	 */
	public void insertObject(Object entity,Class cls,Connection con) throws Exception{
		if(cls==EAST_MONEY_VOLUME_DETAIL_CLASS){
			insertEastMoneyVolumeDetail(entity, con);
		}else if(cls == EAST_MONEY_RISEDROP_CLASS){
			insertEastMoneyRiseDrop(entity, con);
		}else if(cls == EAST_MONEY_VOLUME_CLASS){
			insertEastMoneyVolume(entity, con);
		}else if(cls==EAST_MONEY_RISEDROP_HISTORY_CLASS){
			insertRiseDropHistory(entity, con);
		}else if(cls==EAST_MONEY_STOCK){
			insertEastMoneyStock(entity, con);
		}
	}	
	
	
	/********************************************************************************
	********************************esat_money_risedrop_history**********************
	********************************************************************************/
	
	private static String east_money_risedrop_history = "east_money_risedrop_history";
	private static Class<EastMoneyRiseDropHistory> EAST_MONEY_RISEDROP_HISTORY_CLASS = EastMoneyRiseDropHistory.class;

	/**
	 * insert the RiseDropHistory object
	 * 
	 * @param flagReason
	 * @return
	 * @throws NonSupportException
	 * @throws SQLException 
	 * @throws FraudDaoException 
	 */
	public void insertRiseDropHistory(Object entity,Connection con) throws Exception{
		String sqlStr = "insert into " + east_money_risedrop_history + "(TYPE,EM_STOCK_ID,TRANS_DATE,KAIPAN,HIGEST_PRICE,LOWEST_PRICE" +
				",SHOUPAN,TRANS_VOLUME,TRANS_MONEY)";
		sqlStr += " values(?,?,?,?,?,?,?,?,?)";
		String[] fieldNames = new String[]{"type","emStockId","transDate","kaipan","higestPrice","lowestPrice","shoupan","transVolume","transMoney"};
		save(con,entity, EAST_MONEY_RISEDROP_HISTORY_CLASS, sqlStr, fieldNames);
	}
	
	/**
	 * find all EastMoneyStocks
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public List<?> findRiseDropHistory(EastMoneyRiseDropHistory entity,Connection con)throws Exception{
		StringBuffer sb = new StringBuffer();
		String[] columns = new String[]{"TYPE","EM_STOCK_ID","TRANS_DATE","KAIPAN","HIGEST_PRICE","LOWEST_PRICE","SHOUPAN","TRANS_VOLUME","TRANS_MONEY"};
		String[] fieldNames = new String[]{"type","emStockId","transDate","kaipan","higestPrice","lowestPrice","shoupan","transVolume","transMoney"};
		sb.append("SELECT TYPE,EM_STOCK_ID,TRANS_DATE,KAIPAN,HIGEST_PRICE,LOWEST_PRICE,SHOUPAN,TRANS_VOLUME,TRANS_MONEY from "+east_money_risedrop_history+" WHERE 1=1 ");
		if(entity!=null){
			if(!nullOrTrimNull(entity.getEmStockId())){
				sb.append(" and em_stock_id='"+entity.getEmStockId()+"'");
			}
			if(!isNull(entity.getTransStartDate())){
				sb.append(" and trans_date>="+entity.getTransStartDateToInt());
			}
			if(!isNull(entity.getTransEndDate())){
				sb.append(" and trans_date<="+entity.getTransEndDateToInt());
			}
		}
		return find(con, sb.toString(), columns, fieldNames,EAST_MONEY_RISEDROP_HISTORY_CLASS);
	}
	
	/**
	 * 
	 * @param EastMoneyRiseDropHistory
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public long findCountByEastMoneyRiseDropHistory(EastMoneyRiseDropHistory eastMoneyRiseDropHistory,Connection con) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) as count from "+east_money_risedrop_history+" where 1=1 ");
		Long transDate = eastMoneyRiseDropHistory.getTransDate();
		if(!isNull(transDate)){	
			sb.append(" and trans_date="+transDate);
		}
		return find(con, sb.toString(), "count");
	}
	/********************************************************************************
	********************************esat_money_stock**********************
	********************************************************************************/
	
	private static String east_money_stock = "east_money_stock";
	private static Class<EastMoneyStock> EAST_MONEY_STOCK = EastMoneyStock.class;

	/**
	 * find all EastMoneyStocks
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public List<?> findEastMoneyStock(EastMoneyStock entity,Connection con)throws Exception{
		StringBuffer sb = new StringBuffer();
		String[] columns = new String[]{"TYPE","EM_STOCK_ID","EM_STOCK_NAME"};
		String[] fieldNames = new String[]{"type","emStockId","emStockName"};
		sb.append("SELECT TYPE,EM_STOCK_ID,EM_STOCK_NAME FROM "+east_money_stock+" WHERE 1=1 ");
		return find(con, sb.toString(), columns, fieldNames,EAST_MONEY_STOCK);
	}
	

	/**
	 * insert the RiseDropHistory object
	 * 
	 * @param RiseDropHistory
	 * @return
	 * @throws NonSupportException
	 * @throws SQLException 
	 * @throws FraudDaoException 
	 */
	public void insertEastMoneyStock(Object entity,Connection con) throws Exception{
		String sqlStr = "insert into "+east_money_stock+"(TYPE,EM_STOCK_ID,EM_STOCK_NAME)";
		sqlStr += " values(?,?,?)";
		String[] fieldNames = new String[]{"type","emStockId","emStockName"};
		save(con,entity, EAST_MONEY_STOCK, sqlStr, fieldNames);
	}
	
	public Connection getConnection(){
//		Connection con = DBConnectionManager.getLocalOracleInstance().getConnection();
//		return con;
		//user Connection pool to manager db connecton
		Connection con = DBConnectionPoolManager.getInstance().getConnection();
		return con;
	}
	
	
	
	public static void main(String[] args) {
		try {
			
			Connection con = new EastMoneyDao().getConnection();
			long start=System.currentTimeMillis();
			EastMoneyDao dao = new EastMoneyDao();
			/**EastMoneyRiseDropHistory**/
//			EastMoneyRiseDropHistory history = new EastMoneyRiseDropHistory();
//			history.setEmStockId("600000");
//			List<?> list= dao.findRiseDropHistory(history,con);
			
			/**EastMoneyStock**/
			List<?> list = dao.findRunnedEastMoneyVolume(20110825, con);
			
			System.out.println(list.size());
			/**EastMoneyRiseDrop**/
			/****/
//			EastMoneyRiseDrop drop  = new EastMoneyRiseDrop();
//			drop.setTransDate(new Timestamp(new Date().getTime()));
//			long count = dao.findCountByEastMoneyRiseDrop(drop, con);;
//			LogUtil.info(count);
//			drop.setEmStockId("600000");
//			drop.setTransStartDate(new Long(20110701));
//			drop.setTransEndDate(new Long(20110727));
//			List list2 = dao.findData(drop, EAST_MONEY_RISEDROP_CLASS);
//			LogUtil.info(list2.size()+"");
			
			
			/**EastMoneyVolumeDetail((/
			/**
			EastMoneyVolumeDetail e = new EastMoneyVolumeDetail();
			e.setEmStockId("601992");
			e.setEmVolumeDetail(StreamUtil.getByteArrayByObject(new StringBuffer("hellO")));
			e.setEmVolumeDate(new Timestamp(new Date().getTime()));
			con.setAutoCommit(false);
			dao.insertObject(e, EAST_MONEY_VOLUME_DETAIL_CLASS, con);
			List<?> list = dao.findByEastMoneyVolume(e, con);
			for(Object es:list){
				EastMoneyVolumeDetail e2 = (EastMoneyVolumeDetail)es;
				LogUtil.println(e2.getEmVolumeDetailStr());
			}
			con.commit();
			con.close();**/
			
			LogUtil.info("time waste in main:"+(System.currentTimeMillis()-start));
		} catch (Exception e1) {
			e1.printStackTrace();LogUtil.error("error", e1);
		}
		
	}
	
	
	

}



