package testpackage;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Customer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String qnumber;// Q号
	private Date loginDate;// 登录日期
	private List friends;// 获得好友信息
	public Customer(String qnumber, Date loginDate, List friends) {
		super();
		this.qnumber = qnumber;
		this.loginDate = loginDate;
		this.friends = friends;
	}

	public List getFriends() {
		return friends;
	}

	public void setFriends(List friends) {
		this.friends = friends;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getQnumber() {
		return qnumber;
	}

	public void setQnumber(String qnumber) {
		this.qnumber = qnumber;
	}

}
