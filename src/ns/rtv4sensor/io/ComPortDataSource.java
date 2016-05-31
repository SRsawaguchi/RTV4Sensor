package ns.rtv4sensor.io;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

public class ComPortDataSource extends DataSource {
	private static final int BUF_SIZE = 1024;
	private static final int TIMEOUT  = 2000;
	private static final int BAUDRAIT = 9600;
	private static final String APP_NAME = "CPDS";
	private static final byte   DELIMITER = '\n';
	
	String comPortId = "";
	String applicationName = APP_NAME;
	int    timeout = TIMEOUT;
	int    bufferSize = BUF_SIZE;
	int    baudrait = BAUDRAIT;
	byte[] buffer;
	int    bufPtr = 0;
	byte   delimiter = DELIMITER;
	
	public ComPortDataSource(String comPortId){
		this(null,comPortId);
	}
	
	public ComPortDataSource(DataReceiver dataReceiver,String comPortId){
		this(dataReceiver,comPortId,BUF_SIZE);
	}
	
	public ComPortDataSource(DataReceiver dataReceiver,String comPortId,int bufferSize){
		super(dataReceiver);
		this.comPortId = comPortId;
		this.bufferSize = bufferSize;
		buffer = new byte[bufferSize];
	}
	
	@Override
	public boolean start() {
		if(isClosed){
			return false;
		}
		
		new Thread(new Runnable() {
			CommPortIdentifier comID = null;
			SerialPort port = null;
			InputStream in = null;
			
			private void closeResource(){
				if(port != null){
					port.close();
				}
				
				if(in != null){
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			@Override
			public void run() {
				try {
					comID = CommPortIdentifier.getPortIdentifier(comPortId);
					port = (SerialPort) comID.open(applicationName,timeout);
					port.setSerialPortParams(baudrait,
							SerialPort.DATABITS_8,
							SerialPort.STOPBITS_1,
							SerialPort.PARITY_NONE);
					port.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
					port.setDTR(true);
					port.setRTS(false);
				
					in = port.getInputStream();

					while (true) {
						if (dataReceiver == null || isClosed) {
							break;
						}

						byte b = (byte) in.read();
						if (b == delimiter) {
							Calendar c = Calendar.getInstance();
							byte[] data = buffer.clone();
							new Thread(()->{
								dataReceiver.receiveData(data, c.getTimeInMillis());
								}).start();
							bufPtr = 0;
						} else {
							buffer[bufPtr] = b;
							bufPtr = (bufPtr + 1) % bufferSize;
						}
					}// end while
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					closeResource();
				}//end try
				closeResource();
			}//end run
		}).start();
        
        return true;
	}

	@Override
	public boolean close() {
		if(isClosed){
			return false;
		}
		
		isClosed = true;
		return true;
	}
	
	public String getComPortId() {
		return comPortId;
	}

	public void setComPortId(String comPortId) {
		this.comPortId = comPortId;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getBaudrait() {
		return baudrait;
	}

	public void setBaudrait(int baudrait) {
		this.baudrait = baudrait;
	}

	public byte getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(byte delimiter) {
		this.delimiter = delimiter;
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public byte[] getBuffer() {
		return buffer;
	}

	public int getBufPtr() {
		return bufPtr;
	}
}