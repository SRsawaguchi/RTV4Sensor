package ns.rtv4sensor.io;

public abstract class DataSource {
	DataReceiver dataReceiver;
	
	public DataSource(){
	}
	
	public DataSource(DataReceiver dataReceiver) {
		this.dataReceiver = dataReceiver;
	}

	public DataReceiver getDataReceiver() {
		return dataReceiver;
	}

	public void setDataReceiver(DataReceiver dataReceiver) {
		this.dataReceiver = dataReceiver;
	}
}
