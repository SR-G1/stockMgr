package startup;

import java.io.IOException;

import inputControl.Control;
import readWriteInfo.ReadWriteInfo;

class Demo implements Runnable{
	private String process;
	private static int inc=0;
	public Demo(String process) {
		this.process=process;
	}
	
	@Override
	public void run() {
		try {
			for(;;){
				System.out.print(process);
				Thread.sleep(1000);
				if(inc==1) break;
			}
		} catch (InterruptedException e) {}
	}
	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.print("Loading");
		ReadWriteInfo rwi = new ReadWriteInfo();
		Demo loading = new Demo(".");
		Control control = new Control();
		Thread t=new Thread(loading);
		t.start();
		//rwi.writeStock(null, ReadWriteInfo.stockFile, 1_000_000);
		inc=1;
		t.join();
		System.out.println();
		ReadWriteInfo.iRead(null,1,5);
		control.showControl();
		control.controlStatement();
	}
}
