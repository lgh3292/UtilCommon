package testpackage;

import java.util.ArrayList;
import java.util.Date;

public class LinuxCostomer extends LoginEvent{
	private Customer customer;
	public LinuxCostomer(Customer customer) {
		 this.customer= customer;
	}
	@Override
	public void getNews() {
		doGetNews();
	}
	//处理获得信息
	private void doGetNews(){
		
	}
	public static void main(String[] args) {
		LinuxCostomer lc = new LinuxCostomer(new Customer("272401534",new Date(0),new ArrayList()));
		lc.getNews();
	}
}
