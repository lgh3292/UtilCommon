package t62;

import java.awt.Color;
import java.awt.Graphics;

public class Lamp extends Thread // 灯类：实际上也是线程；
{
	Graphics g; // 数据成员；
	int x, y, j;
	public boolean c, i = true;

	public Lamp(Graphics g, int x, int y, int j, boolean c) // 灯类的constructor;
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
			paint(g, x, y, j); // 十字路口交通灯每过4秒钟变一次；
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
			}

		}
	}

	public void paint(Graphics g, int x, int y, int j) // 四组灯，同一路口的两两相反，不同路口相同位置两两相同；
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