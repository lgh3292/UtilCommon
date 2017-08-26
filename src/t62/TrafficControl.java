package t62;

import java.applet.*;
import java.awt.Graphics;

public class TrafficControl extends Applet { // XR主类；
	Lamp lamp11;
	Lamp lamp12;
	Lamp lamp21;
	Lamp lamp22;
	Lamp lamp31;
	Lamp lamp32;
	Lamp lamp41;
	Lamp lamp42; // 四组八个灯；
	
	Motor motor1;
	Motor motor12;
	Motor motor2;
	Motor motor3;
	Motor motor4;
	Motor motor5;
	Motor motor6; // 七辆小车；
	
	//RunTime time;
	Road r = new Road();
	public int p = 0;

	@Override
	public void init() {

	}

	@Override
	public void start() {

		Graphics g = getGraphics();
		if (lamp11 == null && lamp12 == null) // 四组灯的产生；
		{
			lamp11 = new Lamp(g, 50, 120, 2, true);
			lamp11.start();
			lamp12 = new Lamp(g, 270, 120, 2, true);
			lamp12.start();
		}
		if (lamp21 == null && lamp22 == null) {
			lamp21 = new Lamp(g, 165, 100, 1, true);
			lamp21.start();
			lamp22 = new Lamp(g, 385, 100, 1, true);
			lamp22.start();
		}
		if (lamp31 == null && lamp32 == null) {
			lamp31 = new Lamp(g, 70, 215, 1, false);
			lamp31.start();
			lamp32 = new Lamp(g, 290, 215, 1, false);
			lamp32.start();
		}
		if (lamp41 == null && lamp42 == null) {
			lamp41 = new Lamp(g, 165, 215, 2, false);
			lamp41.start();
			lamp42 = new Lamp(g, 385, 215, 2, false);
			lamp42.start();
		}

		if (motor1 == null) // 小车的产生；
		{
			motor1 = new Motor(g, lamp21, lamp11, 5, 180, 0, r);
			motor1.start();
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

		if (motor12 == null) {
			motor12 = new Motor(g, lamp21, lamp11, 5, 180, 0, r);
			motor12.start();
		}
		if (motor2 == null) {
			motor2 = new Motor(g, lamp41, lamp11, 500, 130, 2, r);
			motor2.start();
		}
		if (motor3 == null) {
			motor3 = new Motor(g, lamp41, lamp11, 0, 180, 0, r);
			motor3.start();
		}
		if (motor4 == null) {
			motor4 = new Motor(g, lamp12, lamp42, 355, 300, 3, r);
			motor4.start();
		}
		if (motor5 == null) {
			motor5 = new Motor(g, lamp12, lamp42, 310, 0, 1, r);
			motor5.start();
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

		if (motor6 == null) {
			motor6 = new Motor(g, lamp11, lamp41, 140, 300, 3, r);
			motor6.start();
		}
	}

	@Override
	public void paint(Graphics g) {
		r.show(g);

	}
}