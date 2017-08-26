package t62;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;

public class Motor extends Thread // �����ࣺʵ������һ���̣߳�
{
	// ���ݳ�Ա��·��Ķ���,������Ķ���lamp_1��lamp_2��
	Road road; 
	int x, y, a;
	Graphics g;
	Lamp lamp_1;
	Lamp lamp_2;
	static Image motorimg=Toolkit.getDefaultToolkit().createImage("E:/TDDOWNLOAD/motor.jpg");
	Random random = new Random(2003); // ������������Ƿ�ת���һ���������

	public Motor(Graphics g, Lamp lamp1, Lamp lamp2, int x, int y, int a,
			Road road) // motor���constructor;
	{
		this.g = g;
		this.lamp_1 = lamp1;
		this.lamp_2 = lamp2; // �����Ƶ�״̬��������̣�
		this.x = x;
		this.y = y;
		this.a = a;
		this.road = road; // ·��Ķ���road���������С����ײ���⣻
	}

	@Override
	public void run() // �̵߳�run������
	{
		while (true) {
			switch (a) // �ĸ��������еĻ���������
			{
			case 0:
				paint(g, 0);
				break; // 0Ϊ���������еĳ���ʵ�֣�
			case 1:
				paint(g, 1);
				break; // 1Ϊ���������еĳ���ʵ�֣�
			case 2:
				paint(g, 2);
				break; // 2Ϊ�Զ��������еĳ���ʵ�֣�
			case 3:
				paint(g, 3);
				break; // 3Ϊ�Ա��������еĳ���ʵ�֣�
			default:
				break;
			}
		}
	}

	void paint(Graphics g, int b) // ����Ļ���������
	{
		switch (b) {
		case 0: { // �������򶫣�
			if (x == 60 || x == 280) {
				if (Math.random() > 0.2)
					a = 0;
				else
					a = 1;
			} // �Ƿ���ת�䣻
			while (x == 60 && a == 0 && lamp_1.i == true || x == 280 && a == 0
					&& lamp_1.i == true) // �Ƿ�Ϊ��ƣ�Y��� N��ֱ�ߣ�
			{
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
				}
			}
			if (road.s[x + 25][180] == 0) // ǰ���Ƿ��г���
			{
				x = x + 5;

				g.setColor(Color.RED);
				g.drawImage(motorimg, x, 180, 20, 15, null);
			//	g.fillRect(x, 180, 20, 15);
				if (x > 0)
					road.s[x][180] = 1;
				if (x > 0)
					road.s[x - 5][180] = 0;
				g.clearRect(x - 5, 180, 10, 15);
			}
			while (a == 1 && x <= 90 && x >= 0 || x >= 280 && x <= 305
					&& a == 1) // ת��ʱ�����ƣ�
			{
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
				}
				for (int p = 150; p <= 210; p++)
					if (road.s[90][p] == 1) {
						road.guan3 = 1;
						break;
					}
				;
				for (int z = 150; z <= 210; z++)
					if (road.s[280][z] == 1) {
						road.guan4 = 1;
						break;
					}
				;

				if (road.s[x + 25][180] == 0) {
					x = x + 5;
					g.setColor(Color.RED);
					g.drawImage(motorimg, x, 180, 20, 15, null);
					//g.fillRect(x, 180, 20, 15);
					road.s[x][180] = 1;
					road.s[x - 5][180] = 0;
					g.clearRect(x - 5, 180, 10, 15);
				}
			}
			if (a == 1) {
				g.clearRect(x, 180, 20, 15);
				road.s[x][180] = 0;
			}

			if (x == 460) // ������ʱ�����ߺ��Ƿ���ת��a=2Ϊ�ǣ�a=0Ϊ��
			{
				if (Math.random() > 0.5)
					a = 0;
				else
					a = 2;
				if (a == 2)
					g.clearRect(x, 180, 20, 15);
				while (a == 2 && y >= 140) {
					for (int p = 430; p <= 495; p++)
						if (road.s[p][140] == 1) {
							road.guanA = 1;
						}
					;

					if (road.s[x][y - 25] == 0 && road.guanA == 0) // ǰ���Ƿ��г���
					{
						y = y - 5;
						g.setColor(Color.RED);
						g.drawImage(motorimg, x, y, 15, 20, null);
						//g.fillRect(x, y, 15, 20);
						road.s[x][y + 5] = 0;
						g.clearRect(x, y + 15, 15, 10);
					}
					road.guanA = 0;
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
					}
				}
				road.s[x][180] = 0;
				road.s[x - 5][180] = 0;
			}
			if (x == 460 && a == 2)
				g.clearRect(x, y, 15, 20);
			if (x == 500) {
				road.s[x][180] = 0;
				x = 0;
			}
			;
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
			y = 180;
		}
			;
			break;
		case 1: // ���Ա����ϣ�
		{
			if (y == 110) // �Ƿ���ת�䣻
			{
				if (Math.random() > 0.5) {
					a = 1;
				} else {
					a = 2;
				}
			}
			while (y == 110 && lamp_1.i != true && a == 1) // �Ƿ�Ϊ��ƣ�Y��� N��ֱ�ߣ�
			{
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
				}

			}
			if (road.s[x][y + 25] == 0) // ǰ���Ƿ��г���
			{
				y = y + 5;
				g.setColor(Color.RED);
				g.drawImage(motorimg, x, y, 15, 20, null);
				//g.fillRect(x, y, 15, 20);
				if (y > 0)
					road.s[x][y] = 1;
				if (y > 0)
					road.s[x][y - 5] = 0;
				g.clearRect(x, y - 5, 15, 10);
			}
			while (a == 2 && y <= 120 && y >= 0) // ת��ʱ�����ƣ�
			{
				if (road.s[x][y + 25] == 0) {
					y = y + 5;
					g.setColor(Color.RED);
					g.fillRect(x, y, 15, 20);
					if (y > 0)
						road.s[x][y] = 1;
					if (y > 0)
						road.s[x][y - 5] = 0;
					g.clearRect(x, y - 5, 15, 10);
				}
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
				}
			}
			if (a == 2) {
				g.clearRect(x, y, 15, 20);
				road.s[x][y] = 0;
				road.s[x][y - 5] = 0;
			}
			if (y == 300) {
				road.s[x][y] = 0;
				g.clearRect(x, y - 20, 15, 40);
				y = 0;
			}
			;
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
		}
			;
			break;
		case 2: // ���Զ�������
		{
			if (x == 150 || x == 380) // �Ƿ���ת�䣻
			{
				if (Math.random() > 0.5)
					a = 2;
				else
					a = 3;
			}
			while (x == 165 && a == 2 && lamp_1.i == true || x == 390 && a == 2
					&& lamp_1.i == true) // �Ƿ�Ϊ��ƣ�Y��� N��ֱ�ߣ�
			{
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
				}
			}
			if (x <= 25 || road.s[x - 25][140] == 0) // ǰ���Ƿ��г���
			{
				x = x - 5;
				g.setColor(Color.RED);
				g.drawImage(motorimg, x, 140, 20, 15, null);
				//g.fillRect(x, 140, 20, 15);
				road.s[x][140] = 1;
				road.s[x + 5][140] = 0;
				g.clearRect(x + 15, 140, 10, 15);
			}
			while (a == 3 && x <= 200 && x >= 140 || a == 3 && x >= 360
					&& x <= 400) // ת��ʱ�����ƣ�
			{
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
				}
				if (road.s[x - 25][140] == 0) {
					x = x - 5;
					g.setColor(Color.RED);
					g.drawImage(motorimg, x, 140, 20, 15, null);
					//g.fillRect(x, 140, 20, 15);
					road.s[x][140] = 1;
					road.s[x + 5][140] = 0;
					g.clearRect(x + 15, 140, 10, 15);
				}
			}
			if (a == 3) {
				g.clearRect(x, 140, 10, 15);
				road.s[x][140] = 0;
			}

			if (x == 15) // �Զ�����ʱ�����ߺ��Ƿ���ת��a=0Ϊ�ǣ�a=2Ϊ��
			{
				if (Math.random() > 0.5)
					a = 0;
				else
					a = 2;
				while (a == 0 && y < 180) {
					for (int p = 0; p <= 40; p++)
						if (road.s[p][180] == 1) {
							road.guan9 = 1;
						}
					;

					if (road.s[x][y + 25] == 0 && road.guan9 == 0) {
						y = y + 5;
						g.setColor(Color.RED);
						g.fillRect(x, y, 15, 20);
						if (y > 0)
							road.s[x][y] = 1;
						if (y > 0)
							road.s[x][y - 5] = 0;
						g.clearRect(x, y - 5, 15, 10);
					}
					road.guan9 = 0;
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
					}
				}
				road.s[x][140] = 0;
				road.s[x - 5][140] = 0;
			}
			if (x == 15 && a == 0)
				g.clearRect(x, y, 15, 20);
			if (x <= 0) {
				road.s[x][140] = 0;
				g.clearRect(x, 140, 20, 15);
				x = 500;
			}
			;
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
			y = 140;
		}
			;
			break;
		case 3: { // �������򱱣�
			if (y == 215) // �Ƿ���ת�䣻
			{
				if (Math.random() > 0.5) {
					a = 3;
				} else {
					a = 0;
				}
			}
			while (y == 215 && lamp_1.i != true && a == 3) // �Ƿ�Ϊ��ƣ�Y��� N��ֱ�ߣ�
			{
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
				}

			}

			if (y <= 25 || road.s[x][y - 25] == 0) // ǰ���Ƿ��г���
			{
				y = y - 5;
				g.setColor(Color.RED);
				g.drawImage(motorimg, x, y, 15, 20, null);
				//g.fillRect(x, y, 15, 20);
				road.s[x][y] = 1;
				road.s[x][y + 5] = 0;
				g.clearRect(x, y + 15, 15, 10);
			}
			while (a == 0 && y <= 225 && y >= 180) // ת��ʱ�����ƣ�
			{
				for (int p = 110; p <= 180; p++)
					if (road.s[p][180] == 1) {
						road.guan1 = 1;
					}
				;
				for (int z = 290; z <= 380; z++)
					if (road.s[z][180] == 1) {
						road.guan2 = 1;
					}
				;
				if (road.s[x][y - 25] == 0 && road.guan1 == 0
						|| road.s[x][y - 25] == 0 && road.guan2 == 0) {
					y = y - 5;
					g.setColor(Color.RED);
					g.drawImage(motorimg, x, y, 15, 20, null);
					//g.fillRect(x, y, 15, 20);
					road.s[x][y] = 1;
					road.s[x][y + 5] = 0;
					g.clearRect(x, y + 15, 15, 10);
				}
				road.guan1 = 0;
				road.guan2 = 0;
				if (y == 180)
					break;
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
				}
			}
			if (a == 0) {
				g.clearRect(x, y, 15, 20);
				road.s[x][y] = 0;
				road.s[x][y + 5] = 0;
			}
			if (y == 0) {
				g.clearRect(x, y, 15, 20);
				road.s[x][y] = 0;
				y = 300;
			}
			;
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
		}
			;
			break;
		default:
			break;
		}
	}
}
