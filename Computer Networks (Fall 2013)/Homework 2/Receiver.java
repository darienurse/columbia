/* 
The TCP receiver should be invoked as follows: 
%receiver file.txt 20000 128.59.15.37 20001 logfile.txt 
command line exec with filename, listening_port remote_IP, remote_port, log_filename  Delivery completed successfully 

>% program finished: shell prompt
The receiver receives data on the listening_port, writes it to the specified file (filename) 
and sends ACKs to the remote host at IP address (remote_IP) and port number 
(remote_port). In the above example the ACKs are sent to the sender (128.59.15.37) with 
an ACK port 20001. The IP address is given in dotted-decimal notation and the port 
number is an integer value. The receiver will log the headers of all the received and sent 
packets to a log file (log_filename) specified in the command-line.
*/
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Receiver {
	private PrintWriter copyFile;
	private InetAddress remote_IP; 
	private short remote_port;
	private PrintWriter logfile;
	private DatagramSocket rec_socket;
	private boolean stdout = false;
	private PacketHandler rec_packet;
	private PacketHandler send_packet;
	private String log;
	
	private Receiver(String f, String listen, String rIP, String rP, String logName){
		try {
			rec_socket = new DatagramSocket(Short.parseShort(listen));
			remote_port = Short.parseShort(rP);
			remote_IP = InetAddress.getByName(rIP);
			initalizeFiles(f, logName);
			log = logName;
			initalizePackets(Short.parseShort(listen));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	
	public void initalizeFiles(String f, String log){
		//create the file that recieves writes to a and the log file.
		File myfile = new File(f);
		File mylog = new File(log);
		try{
			if(!myfile.exists())
			    myfile.createNewFile(); 
			if(!mylog.exists() && !log.equalsIgnoreCase("stdout"))
			    mylog.createNewFile();
		copyFile =new PrintWriter(new BufferedWriter(new FileWriter(f)));
		if (!log.equalsIgnoreCase("stdout"))
			logfile = new PrintWriter(new BufferedWriter(new FileWriter(log,true)));
		else
			stdout=true;
		}
		catch(IOException e) {
			System.err.println("Unable to access/create the given input file or log file.");
			e.printStackTrace();
		} 
	}
	
	private void initalizePackets(short listen){
		ByteBuffer header = ByteBuffer.allocate(PacketHandler.TCP_header_size);
		int sequence = 0;
		int acknowledgement = 0;
		header.putShort(listen);
		header.putShort(remote_port);
		header.putInt(sequence);
		header.putInt(acknowledgement);
		header.put((byte) 0x50); //set the header length to 5 32-bit words
		header.put((byte) 0x10); //set ack to 1 and fin to 0
		header.putShort((short) 1); //set rec_window to 1
		header.putInt(0); //places zeros in the checksum and urgent fields
		rec_packet = new PacketHandler();
		send_packet = new PacketHandler(header.array(), new byte[PacketHandler.MSS], remote_IP, remote_port);
		assert header.position() == header.capacity()-1;
	}
	
	private String getTime(){
		Date now = new Date();
		String format1 = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss.SSSS ").format(now);
		return format1;
	}
	
	public void receive() throws IOException{
		//boolean firstRec = false;
		//rec_socket.setSoTimeout(10000);
		while(!rec_packet.checkFinFlag()) {
		    rec_packet= new PacketHandler();
		    try {
		        rec_socket.receive(rec_packet.getPacket());
		        log(rec_packet);
		        rec_socket.setSoTimeout(10000);
		    } catch (SocketTimeoutException e) {
		    	// resend
		    	//System.out.println("Receiver Timeout");
		    	rec_socket.send(send_packet.getPacket());
		    	log(send_packet);
		    	continue;
		    }
		    if(!rec_packet.verifyChecksum()){
		    	logError("Previous log invalid. Received packet is corrupted. Resending packet.");
		    	//resend
		    	send_packet.setAckFlag(false);
		    	rec_socket.send(send_packet.getPacket());
		    	log(send_packet);
		    }
		    else if(send_packet.getAck() != rec_packet.getSeq()){
		    	logError("Previous log invalid. Received packet has incorrect sequence number. Discarding.");
		    	//resend
		    	//rec_socket.send(send_packet.getPacket());
		    	//log(send_packet);
		    }
		    else{
				//Packet successfully recieved. Send ACK
		    	copyFile.write(new String(rec_packet.getPacketData()).replaceAll("\u0000.*", ""));
		    	//	System.out.println("YO");
		    	
		    	send_packet.setSeq(rec_packet.getAck());
		    	send_packet.setAck(rec_packet.getSeq()+1);
		    	send_packet.setAckFlag(true);
		    	send_packet = new PacketHandler(send_packet.getHeader(), new byte[0], send_packet.getPacket().getAddress(), send_packet.getPacket().getPort());
		    	rec_socket.send(send_packet.getPacket());
		    	log(send_packet);
		    }
		}
		copyFile.close();
		//logfile.close();
	}
	
	public void log(PacketHandler myPacketHandler){
		try {
			logfile = new PrintWriter(new BufferedWriter(new FileWriter(log,true)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String thisLog = getTime() + " Source: " + myPacketHandler.getSource() + " Destination: " + myPacketHandler.getDest() + " Sequence#: " + myPacketHandler.getSeq()
	    		+" Acknowledgement#: "+ myPacketHandler.getAck()+ " ACK FLAG: " + myPacketHandler.getAckFlag()+ " FIN FLAG: " + myPacketHandler.checkFinFlag();
	    if(stdout)
	    	System.out.print(thisLog);
	    else
	    	logfile.println(thisLog);
	    logfile.close();
	}
	
	public void logError(String error){
		try {
			logfile = new PrintWriter(new BufferedWriter(new FileWriter(log,true)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String thisLog = getTime()+ " " + error;
	    if(stdout)
	    	System.out.print(thisLog);
	    else
	    	logfile.println(thisLog);
	    logfile.close();
	}
	

	public static void main(String [] args)
	{
		//create a receiver
		Receiver rec = new Receiver( args[0],args[1], args[2],args[3], args[4]);
		try {
			rec.receive();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
