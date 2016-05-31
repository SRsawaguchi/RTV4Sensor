package ns.rtv4sensor.io;

public abstract class DataSource {
	DataReceiver dataReceiver;
	boolean isClosed = false;
	


	public DataSource(){
	}
	
	public DataSource(DataReceiver dataReceiver) {
		this.dataReceiver = dataReceiver;
	}
	
	/* データの読み込みを開始する。 */
	public abstract boolean start();
	
	/* データソースをクローズする。 資源の開放。*/
	public abstract boolean close();
	
	public DataReceiver getDataReceiver() {
		return dataReceiver;
	}

	public void setDataReceiver(DataReceiver dataReceiver) {
		this.dataReceiver = dataReceiver;
	}
	
	public boolean isClosed() {
		return isClosed;
	}
	
	protected void setIsClosed(boolean isClosed){
		this.isClosed = isClosed;
	}
}
