package t62;

import java.awt.Color;
import java.awt.Graphics;

public class Lamp extends Thread // ���ࣺʵ����Ҳ���̣߳�
{
	Graphics g; // ���ݳ�Ա��
	int x, y, j;
	public boolean c, i = true;

	public Lamp(Graphics g, int x, int y, int j, boolean c) // �����constructor;
	{
		this.g = g;
		this.x = x;
		this.y = y;
		this.j = j;
		this.c = c;
	}

	@Override
	public void run() {
		while (true) {
			if (i) {
				i = false;
			} else {
				i = true;
			}
			paint(g, x, y, j); // ʮ��·�ڽ�ͨ��ÿ��4���ӱ�һ�Σ�
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
			}

		}
	}

	public void paint(Graphics g, int x, int y, int j) // ����ƣ�ͬһ·�ڵ������෴����ͬ·����ͬλ��������ͬ��
	{
		if (i == true && j == 1 && c == true) {
			g.setColor(Color.yellow);
			g.fillOval(x, y, 7, 7);
			g.setColor(Color.lightGray);
			g.fillOval(x, y + 10, 7, 7);
			g.setColor(Color.red);
			g.fillOval(x, y + 20, 7, 7);
		} else if (i == false && j == 1 && c == true) {
			g.setColor(Color.yellow);
			g.fillOval(x, y, 7, 7);
			g.setColor(Color.lightGray);
			g.fillOval(x, y + 10, 7, 7);
			g.setColor(Color.green);
			g.fillOval(x, y + 20, 7, 7);
		}

		if (i == false && j == 1 && c == false) {
			g.setColor(Color.green);
			g.fillOval(x, y, 7, 7);
			g.setColor(Color.lightGray);
			g.fillOval(x, y + 10, 7, 7);
			g.setColor(Color.yellow);
			g.fillOval(x, y + 20, 7, 7);
		} else if (i == true && j == 1 && c == false) {
			g.setColor(Color.red);
			g.fillOval(x, y, 7, 7);
			g.setColor(Color.lightGray);
			g.fillOval(x, y + 10, 7, 7);
			g.setColor(Color.yellow);
			g.fillOval(x, y + 20, 7, 7);
		}

		if (i == false && j == 2 && c == true) {
			g.setColor(Color.yellow);
			g.fillOval(x, y, 7, 7);
			g.setColor(Color.lightGray);
			g.fillOval(x + 10, y, 7, 7);
			g.setColor(Color.red);
			g.fillOval(x + 20, y, 7, 7);
		} else if (i == true && j == 2 && c == true) {
			g.setColor(Color.yellow);
			g.fillOval(x, y, 7, 7);
			g.setColor(Color.lightGray);
			g.fillOval(x + 10, y, 7, 7);
			g.setColor(Color.green);
			g.fillOval(x + 20, y, 7, 7);
		}

		if (i == false && j == 2 && c == false) {
			g.setColor(Color.red);
			g.fillOval(x, y, 7, 7);
			g.setColor(Color.lightGray);
			g.fillOval(x + 10, y, 7, 7);
			g.setColor(Color.yellow);
			g.fillOval(x + 20, y, 7, 7);
		} else if (i == true && j == 2 && c == false) {
			g.setColor(Color.green);
			g.fillOval(x, y, 7, 7);
			g.setColor(Color.lightGray);
			g.fillOval(x + 10, y, 7, 7);
			g.setColor(Color.yellow);
			g.fillOval(x + 20, y, 7, 7);
		}

	}
}