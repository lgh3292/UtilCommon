package com.lgh.eastmoney.ctl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.lgh.eastmoney.bfo.EastMoneyRDVCount;
import com.lgh.eastmoney.bo.EastMoneyRiseDrop;
import com.lgh.eastmoney.bo.EastMoneyRiseDropHistory;
import com.lgh.eastmoney.bo.EastMoneyStock;
import com.lgh.eastmoney.bo.EastMoneyVolume;
import com.lgh.util.DateUtil;
import com.lgh.util.db.DBConnectionPoolManager;
import com.lgh.util.logging.LogUtil;

/**
 * 处理所有的业务逻辑以访问DB为主
 * @author liuguohu
 *
 */
public class EastMoneyService {
	private EastMoneyDao dao = EastMoneyDao.getInstance();
	
	/**
	 * get the stock volume and save it to db
	 */
	public void saveToDB(Object object,Class cls){
		Connection con  = null;
		try {
			con = DBConnectionPoolManager.getInstance().getConnection();
			con.setAutoCommit(false);
			dao.insertObject(object,cls,con);
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();LogUtil.error("error", e);
			
		}finally{
			if(con!=null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();LogUtil.error("error", e);
				}
			}
		}
	}
	
	/**
	 * final all the data
	 * @return
	 */
	public List<?> findData(Object entity,Class cls){
		Connection con = dao.getConnection();
		try {
			if(cls==EastMoneyStock.class){
				return dao.findEastMoneyStock((EastMoneyStock)entity,con);
			}else if(cls==EastMoneyRiseDropHistory.class){
				return dao.findRiseDropHistory((EastMoneyRiseDropHistory)entity, con);
			}else if(cls==EastMoneyRiseDrop.class){
				return dao.findEastMoneyRiseDrop((EastMoneyRiseDrop)entity, con);
			}
		} catch (Exception e) {
			e.printStackTrace();LogUtil.error("error", e);
		}finally{
			if(con!=null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();LogUtil.error("error", e);
				}
			}
		}
		return null;
	}
	
	/**
	 * find the count data from db 
	 * @param object
	 * @param cls
	 * @return
	 */
	protected long findCount(Object object,Class cls){
		Connection con = dao.getConnection();
		try {
			long count = 0;
			if(object instanceof EastMoneyRiseDrop){
				con.setAutoCommit(false);
				count = dao.findCountByEastMoneyRiseDrop((EastMoneyRiseDrop)object, con);
				con.commit();
			}
			return count;
		} catch (Exception e) {
			e.printStackTrace();LogUtil.error("error", e);
		}finally{
			if(con!=null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();LogUtil.error("error", e);
				}
			}
		}
		return 0;
	}

	/**
	 * 查找某天所有的股票ID
	 * @param longValue
	 * @return
	 */
	public List<EastMoneyVolume> findRunnedEastMoneyVolume(long date) {
		Connection con = dao.getConnection();
		try {
			return (List<EastMoneyVolume>) dao.findRunnedEastMoneyVolume(date, con);
		} catch (Exception e) {
			e.printStackTrace();LogUtil.error("error", e);
		}finally{
			if(con!=null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();LogUtil.error("error", e);
				}
			}
		}
		return null;
	}
	
	/**
	 * 查看每张表历史每天的成功导入的数据量
	 */
	public List<EastMoneyRDVCount> findEastMoneyRDVCount(Calendar startDate,Calendar endDate){
		Connection con = dao.getConnection();
		List<EastMoneyRDVCount> counts = new ArrayList<EastMoneyRDVCount>();
		try {
			//return null;
			while(startDate.before(endDate)){
				EastMoneyRDVCount count = new EastMoneyRDVCount();
				String dateStr = DateUtil.getDateStr(startDate.getTimeInMillis(), "yyyyMMdd");
				count.setDate(Long.valueOf(dateStr));
				//find RiseDrop
				EastMoneyRiseDrop drop = new EastMoneyRiseDrop();
				drop.setTransDate(Long.valueOf(dateStr));
				long couEastMoneyRiseDrop = dao.findCountByEastMoneyRiseDrop(drop, con);
				count.setRisedropCount(couEastMoneyRiseDrop);
				
				//find RiseDropHis
				EastMoneyRiseDropHistory his = new EastMoneyRiseDropHistory();
				his.setTransDate(Long.valueOf(dateStr));
				long couEastMoneyRiseDropHistory = dao.findCountByEastMoneyRiseDropHistory(his, con);
				count.setRisedropHisCount(couEastMoneyRiseDropHistory);
				//find Volue
				EastMoneyVolume eastMoneyVolume = new EastMoneyVolume();
				eastMoneyVolume.setEmDateId(Integer.valueOf(dateStr));
				long volumeCount = dao.findCountByEastMoneyVolume(eastMoneyVolume, con);
				count.setVolumeCount(volumeCount);
				startDate.add(Calendar.DAY_OF_MONTH, 1);
				counts.add(count);
			}
			return counts;
		} catch (Exception e) {
			e.printStackTrace();LogUtil.error("error", e);
		}finally{
			
			if(con!=null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();LogUtil.error("error", e);
				}
			}
		}
		return null;
		
	}
	public static void main(String[] args) {
		Calendar starCal = Calendar.getInstance();
		starCal.set(Calendar.DAY_OF_MONTH, -30);
		Calendar endCal = Calendar.getInstance();
		EastMoneyService service = new EastMoneyService();
		List<EastMoneyRDVCount>  count= service.findEastMoneyRDVCount(starCal,endCal);
		System.out.println(count);
	}
}
