package com.lgh.eastmoney.bo;

/**
 * DB里的股票信息
 * @author liuguohu
 *
 */
public class EastMoneyStock {
	protected String type;
	protected String emStockId;
	protected String emStockName;
	public String getType() {
		return this.type;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof EastMoneyStock){
			EastMoneyStock o = (EastMoneyStock)obj;
			if(o.getType()!=null&&type!=null&&o.getEmStockId()!=null&&this.emStockId!=null)
			return o.getType().equals(this.type)&&o.getEmStockId().equals(this.emStockId);
		}
		return false;
	}


	@Override
	public int hashCode() {
		if(emStockId!=null){
			return Integer.valueOf(emStockId).intValue();
		}
		return 0;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmStockId() {
		return this.emStockId;
	}

	public void setEmStockId(String emStockId) {
		this.emStockId = emStockId;
	}
	public String getEmStockName() {
		return this.emStockName;
	}

	public void setEmStockName(String emStockName) {
		this.emStockName = emStockName;
	}
}