package ns.rtv4sensor.io;

/* データを受け取るインタフェース　*/
public interface DataReceiver {
	public void receiveData(byte[] data,long time);
}
