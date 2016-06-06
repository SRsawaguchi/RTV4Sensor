package ns.rtv4sensor.io;
import java.nio.ByteBuffer;
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
		int        bufSize = Integer.SIZE / Byte.SIZE;
		ByteBuffer buf     = ByteBuffer.allocate(bufSize);
		byte[]     data    = buf.putInt(n).array();
		
		return data;
	}
	
	public static int byte2int(byte[] data){
		int num = ByteBuffer.wrap(data).getInt();
		
		return num;
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
				byte[]   data = int2byte(r);
				
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
