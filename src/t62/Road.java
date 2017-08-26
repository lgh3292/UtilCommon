package t62;

import java.awt.Color;
import java.awt.Graphics;

public class Road
{
	int rd1x = 120, rd2x = 340, rdy = 170; // 两个十字路口的x、y坐标（120，170）、（340，170）；
	int halfwid = 40;// 路宽的一半
	int mleft = 10, mright = 790, mup = 50, mdown = 300;
	int s[][] = new int[600][400]; // 整个区域的一个映射数组；
	int guan1 = 0, guan2 = 0, guan3 = 0, guan4 = 0, guan5 = 0;
	int guan6 = 0, guan7 = 0, guan8 = 0, guan9 = 0, guanA = 0;

	public Road() // 路类的构造函数：每个全区域映射数组代表一个点，为1表示有车在上面，为0表示没有；
	{
		for (int t = 0; t <= 599; t++)
			for (int f = 0; f <= 399; f++)
				s[t][f] = 0;
	}

	void show(Graphics g) // 具体的画这个系统；
	{

		g.setColor(Color.BLACK);
		g.fillRoundRect(75, 330, 310, 25, 5, 5);
		g.setColor(Color.cyan); // 十字路口中心的中心线；
		g.fillRoundRect(35, 165, 45, 10, 5, 5);
		g.fillRoundRect(160, 165, 140, 10, 5, 5);
		g.fillRoundRect(380, 165, 75, 10, 5, 5);
		g.fillRoundRect(115, 55, 10, 75, 5, 5);
		g.fillRoundRect(115, 210, 10, 75, 5, 5);
		g.fillRoundRect(335, 55, 10, 75, 5, 5);
		g.fillRoundRect(335, 210, 10, 75, 5, 5);
		g.setColor(Color.magenta); // 十字路口中心的交警指挥台；
		g.fillOval(112, 162, 16, 16);
		g.fillOval(332, 162, 16, 16);
		g.setColor(Color.green); // 周边的绿地；
		g.fillRoundRect(10, 55, 55, 55, 10, 10);
		g.fillRoundRect(10, 230, 55, 60, 10, 10);
		g.fillRoundRect(180, 55, 100, 60, 10, 10);
		g.fillRoundRect(180, 230, 100, 60, 10, 10);
		g.fillRoundRect(400, 55, 60, 60, 10, 10);
		g.fillRoundRect(400, 230, 60, 60, 10, 10);
		g.setColor(Color.gray); // 道路边界；
		g.drawLine(mleft, rdy - halfwid, rd1x - halfwid, rdy - halfwid);
		g.drawLine(rd1x - halfwid, rdy - halfwid, rd1x - halfwid, mup);
		g.drawLine(rd1x + halfwid, mup, rd1x + halfwid, rdy - halfwid);
		g.drawLine(rd1x + halfwid, rdy - halfwid, rd2x - halfwid, rdy - halfwid);
		
		g.drawLine(rd2x - halfwid, rdy - halfwid, rd2x - halfwid, mup);
		g.drawLine(rd2x + halfwid, mup, rd2x + halfwid, rdy - halfwid);
		g.drawLine(rd2x + halfwid, rdy - halfwid, mright, rdy - halfwid);
		g.drawLine(rd2x + halfwid, rdy + halfwid, mright, rdy + halfwid);
		g.drawLine(rd2x + halfwid, rdy + halfwid, rd2x + halfwid, mdown);
		g.drawLine(rd2x - halfwid, rdy + halfwid, rd2x - halfwid, mdown);
		
		g.drawLine(rd1x + halfwid, rdy + halfwid, rd2x - halfwid, rdy + halfwid);
		g.drawLine(rd1x + halfwid, rdy + halfwid, rd1x + halfwid, mdown);
		g.drawLine(rd1x - halfwid, rdy + halfwid, rd1x - halfwid, mdown);
		g.drawLine(mleft, rdy + halfwid, rd1x - halfwid, rdy + halfwid);
		g.drawLine(rd2x + halfwid, rdy - halfwid, mright, rdy - halfwid);

	}
}