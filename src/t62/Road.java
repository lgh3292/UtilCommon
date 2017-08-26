package t62;

import java.awt.Color;
import java.awt.Graphics;

public class Road
{
	int rd1x = 120, rd2x = 340, rdy = 170; // ����ʮ��·�ڵ�x��y���꣨120��170������340��170����
	int halfwid = 40;// ·���һ��
	int mleft = 10, mright = 790, mup = 50, mdown = 300;
	int s[][] = new int[600][400]; // ���������һ��ӳ�����飻
	int guan1 = 0, guan2 = 0, guan3 = 0, guan4 = 0, guan5 = 0;
	int guan6 = 0, guan7 = 0, guan8 = 0, guan9 = 0, guanA = 0;

	public Road() // ·��Ĺ��캯����ÿ��ȫ����ӳ���������һ���㣬Ϊ1��ʾ�г������棬Ϊ0��ʾû�У�
	{
		for (int t = 0; t <= 599; t++)
			for (int f = 0; f <= 399; f++)
				s[t][f] = 0;
	}

	void show(Graphics g) // ����Ļ����ϵͳ��
	{

		g.setColor(Color.BLACK);
		g.fillRoundRect(75, 330, 310, 25, 5, 5);
		g.setColor(Color.cyan); // ʮ��·�����ĵ������ߣ�
		g.fillRoundRect(35, 165, 45, 10, 5, 5);
		g.fillRoundRect(160, 165, 140, 10, 5, 5);
		g.fillRoundRect(380, 165, 75, 10, 5, 5);
		g.fillRoundRect(115, 55, 10, 75, 5, 5);
		g.fillRoundRect(115, 210, 10, 75, 5, 5);
		g.fillRoundRect(335, 55, 10, 75, 5, 5);
		g.fillRoundRect(335, 210, 10, 75, 5, 5);
		g.setColor(Color.magenta); // ʮ��·�����ĵĽ���ָ��̨��
		g.fillOval(112, 162, 16, 16);
		g.fillOval(332, 162, 16, 16);
		g.setColor(Color.green); // �ܱߵ��̵أ�
		g.fillRoundRect(10, 55, 55, 55, 10, 10);
		g.fillRoundRect(10, 230, 55, 60, 10, 10);
		g.fillRoundRect(180, 55, 100, 60, 10, 10);
		g.fillRoundRect(180, 230, 100, 60, 10, 10);
		g.fillRoundRect(400, 55, 60, 60, 10, 10);
		g.fillRoundRect(400, 230, 60, 60, 10, 10);
		g.setColor(Color.gray); // ��·�߽磻
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