package ns.rtv4sensor.io;

public abstract class DataSource {
	DataReceiver dataReceiver;
	
	public DataSource(DataReceiver dataReceiver) {
		this.dataReceiver = dataReceiver;
	}
}
