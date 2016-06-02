package ns.rtv4sensor.io;
import java.util.Calendar;
import java.util.Random;


public class SimulateDataSource extends DataSource {
	private static final long INTERVAL = 100; // MS
	private static final int  MAX      = 100;
	private static final int  MIN      = 0;
	
	long interval = INTERVAL;
	int max       = MAX;
	int min       = MIN;
	
	public static byte[] int2byte(int n){
		
	}
	
	@Override
	public boolean start() {
		if(isClosed){
			return false;
		}
		
		new Thread(()->{
			Random random = new Random();
			while(true){
				Calendar c    = Calendar.getInstance();
				int      r    = random.nextInt(MAX);
				byte[]   data = ;
				
				new Thread(()->{
					dataReceiver.receiveData(data, c.getTimeInMillis());
				}).start();
				try {
					Thread.sleep(INTERVAL);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}//end while
		}).start();
		
		return true;
	}

	
	@Override
	public boolean close() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
}
