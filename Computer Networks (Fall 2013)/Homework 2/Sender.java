import java.net.InetAddress;
/*
 % sender file.txt 128.59.15.38 20000 20001 1152 logfile.txt 
command line exec with filename, remote_IP, remote_port, ack_port_number, window_size, log_filename 
Delivery completed successfully 

Total bytes sent = 1152 
Segments sent = 2 
Segments retransmitted = 0 
>% program finished: shell prompt 

The sender sends the data in the specified file (filename) to the remote host at the 
specified IP address (remote_IP) and port number (remote_port). In the above example 
the remote host (which can be either the receiver or the link emulator proxy) is located 
at 128.59.15.38 and port 20000. The command-line parameter ack_port_number 
specifies the local port for the received acknowledgements. The window_size is 
measured in bytes and your sender should support values from 1-65535. As before a log 
filename is specified. The log entry output format should be similar to the one used by 
the receiver, however, it should have one additional output field (append at the end), 
which is the estimated RTT.
 */

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
import java.util.Arrays;
import java.util.Date;

public class Sender {
	private FileInputStream filename;
	private PrintWriter logfile;
	private DatagramSocket send_socket;
	private boolean stdout = false;
	PacketHandler rec_packet;
	PacketHandler send_packet;
	private InetAddress remote_IP; 
	private short remote_port;
	private String log;
	private double alpha = 0.125;
	private double beta = 0.25;
	private double devRTT = 1;
	private double estRTT = 3;
	private boolean validRTT = true;
	private long startRTT = 0;
	private int timeout = (int) (estRTT + (4*devRTT));
	private int totalBytesSent = 0;
	private int segSent = 0;
	private int segRetrans = 0;
	private long filesize;
	private long prevPercent = 0;
	
	private Sender(String f, String rIP, String rP, String ack_port, String window_size, String logName){
		try {
			send_socket = new DatagramSocket(Short.parseShort(ack_port));
			initalizeFiles(f, logName);
			remote_port = Short.parseShort(rP);
			remote_IP = InetAddress.getByName(rIP);
			log = logName;
			initalizePackets(Short.parseShort(ack_port), Short.parseShort(window_size));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		//send_address = new InetSocketAddress(rIP,rP);
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
		filename = new FileInputStream(f);
		filesize = myfile.length();
		if (!log.equalsIgnoreCase("stdout")){
			//logfile = new PrintWriter(new BufferedWriter(new FileWriter(log,true)));
		}
		else
			stdout=true;
		}
		catch(IOException e) {
			System.err.println("Unable to access/create the given input file or log file.");
			e.printStackTrace();
		} 
	}
	
	private void initalizePackets(short ack_port, short window_size){
		ByteBuffer header = ByteBuffer.allocate(PacketHandler.TCP_header_size);
		int sequence = 0;
		int acknowledgement = 0;
		header.putShort(ack_port);
		header.putShort(remote_port);
		header.putInt(sequence);
		header.putInt(acknowledgement);
		header.put((byte) 0x50); //set the header length to 5 32-bit words
		header.put((byte) 0x10); //set ack to 1 and fin to 0
		header.putShort((short)1); //set rec_window to 1. However, window_size is passed in for future revisions
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
	
	public void send() throws IOException{
		byte[] data = new byte[PacketHandler.MSS];
		int bytesRead;
		while((bytesRead = filename.read(data)) != -1){
			//validRTT = true;
			send_packet.setSeq(rec_packet.getAck());
	    	send_packet.setAck(rec_packet.getSeq()+1);
	    	send_packet.setAckFlag(false);
	    	if (bytesRead<data.length || filename.available()==0){
	    		send_packet.setFinFlag(true);
	    		data = Arrays.copyOfRange(data, 0, bytesRead);
	    	}
			send_packet = new PacketHandler(send_packet.getHeader(), data, send_packet.getPacket().getAddress(), send_packet.getPacket().getPort());
			send_socket.send(send_packet.getPacket());
			startRTT = System.currentTimeMillis();
			totalBytesSent += bytesRead;
			segSent++;
			double sentSoFar = PacketHandler.MSS*(segSent-segRetrans);
			long percent = Math.round(sentSoFar*100/filesize);
			if (percent>100)
				percent =100;
			if (prevPercent != percent && prevPercent!=0){
				if(filesize>200*PacketHandler.MSS)
						validRTT =false;
				System.out.println(percent+"% transferred");
			}
			prevPercent = percent;
			log(send_packet);
			while(true){
				send_socket.setSoTimeout(timeout);
				rec_packet = new PacketHandler();
			    try {
			        send_socket.receive(rec_packet.getPacket());
			        log(rec_packet);
			        //send_socket.setSoTimeout((int)estRTT);
			    } catch (SocketTimeoutException e) {
			       // resend
			       //System.out.println("Sender Timeout");
			       startRTT = System.currentTimeMillis();
			       send_socket.send(send_packet.getPacket());
			       totalBytesSent += bytesRead;
			       segSent++;
			       segRetrans++;
			       log(send_packet);
			       continue;
			    }
			    //System.out.print(rec_packet.verifyChecksum());
			    if(!rec_packet.getAckFlag()){
			    	validRTT = false;
			    	totalBytesSent += bytesRead;
					segSent++;
					segRetrans++;
			    	send_socket.send(send_packet.getPacket());
				    log(send_packet);
				    //continue;
			    	break;
			    }
			    else{
			    	if(validRTT)
			    		updateTimeout();
			    	break;
			    }
			}
		}
		filename.close();
		//logfile.close();	
	}
	
	private void updateTimeout() {
		long sampleRTT = System.currentTimeMillis()-startRTT;
		estRTT = (1-alpha)*estRTT+(alpha*(sampleRTT));
		devRTT = (1-beta) *devRTT + (beta*Math.abs(sampleRTT-estRTT));
		timeout = (int) (estRTT + (4*devRTT));
		validRTT =true;
	}
	private String finalStats(){
		return ("Delivery completed sucessfully\nTotal bytes sent = "+totalBytesSent+"\nSegments sent = "
				+ segSent + "\nSegments retransmitted = " + segRetrans +"\n");
	}


	public void log(PacketHandler myPacketHandler){
		try {
			logfile = new PrintWriter(new BufferedWriter(new FileWriter(log,true)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String thisLog = getTime() + " Source: " + myPacketHandler.getSource() + " Destination: " + myPacketHandler.getDest() + " Sequence#: " + myPacketHandler.getSeq()
	    		+" Acknowledgement#: "+ myPacketHandler.getAck()+ " ACK FLAG: " + myPacketHandler.getAckFlag()+ " FIN FLAG: " + myPacketHandler.checkFinFlag()+ " RTT: " + estRTT;
	    if(stdout)
	    	System.out.print(thisLog);
	    else
	    	logfile.println(thisLog);
	    logfile.close();
	}
	

	public static void main(String [] args)
	{
		//create a receiver
		Sender sender = new Sender(args[0],args[1], args[2],args[3], args[4], args[5]);
		try {
			sender.send();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.print(sender.finalStats());
	}
}

