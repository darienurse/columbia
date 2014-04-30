import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;


public class PacketHandler{
	public static final int TCP_header_size = 20;
	public static final int MSS = 576;
	public static final int MTU = TCP_header_size+MSS;
	private int source_pos = 0;
	private int dest_pos = 2;
	private int seq_pos = 4;
	private int ack_pos = 8;
	//private int rec_pos = 14;
	private int check_pos = 16;
	private DatagramPacket packet;
	
	
	PacketHandler(){
		packet = new DatagramPacket(new byte[MTU], MTU);
	}
	PacketHandler(byte[] head, byte[] data, InetAddress address, int port){
		assert head.length == TCP_header_size;
		byte[] buffer = concatenateByteArrays(head, data);
		packet = new DatagramPacket(buffer, buffer.length, address, port);
		clearCheckSum();
		setCheckSum();
	}
	public DatagramPacket getPacket() {
		return packet;
	}
	public byte[] getPacketData(){
		return Arrays.copyOfRange(packet.getData(), TCP_header_size, MTU);
	}
	public byte[] getHeader(){
		return Arrays.copyOfRange(packet.getData(),0, TCP_header_size);
	}
	public short getSource(){
		ByteBuffer dataBuffer = ByteBuffer.wrap(packet.getData());
		return dataBuffer.getShort(source_pos);
	}
	public short getDest(){
		ByteBuffer dataBuffer = ByteBuffer.wrap(packet.getData());
		return dataBuffer.getShort(dest_pos);
	}
	public int getSeq(){
		ByteBuffer dataBuffer = ByteBuffer.wrap(packet.getData());
		return dataBuffer.getInt(seq_pos);
	}
	public void setSeq(int value){
		ByteBuffer dataBuffer = ByteBuffer.wrap(packet.getData());
		dataBuffer.putInt(seq_pos, value);
		packet.setData(dataBuffer.array());
	}
	public int getAck(){
		ByteBuffer dataBuffer = ByteBuffer.wrap(packet.getData());
		return dataBuffer.getInt(ack_pos);
	}
	public void setAck(int value){
		ByteBuffer dataBuffer = ByteBuffer.wrap(packet.getData());
		dataBuffer.putInt(ack_pos, value);
		packet.setData(dataBuffer.array());
	}
	public short getCheckSum(){
		//return (short) (packet.getData()[check_pos] << 8 | packet.getData()[check_pos+1]);
		ByteBuffer dataBuffer = ByteBuffer.wrap(packet.getData());
		return dataBuffer.getShort(check_pos);
	}
	public void setCheckSum(){
		ByteBuffer dataBuffer = ByteBuffer.wrap(packet.getData());
		dataBuffer.putShort(check_pos, checksum_calc());
		packet.setData(dataBuffer.array());
	}
	private void clearCheckSum(){
		ByteBuffer dataBuffer = ByteBuffer.wrap(packet.getData());
		dataBuffer.putShort(check_pos, (short) 0);
		packet.setData(dataBuffer.array());
	}
	public boolean verifyChecksum(){
		if(checksum_calc() == 0)
			return true;
		else
			return false;
	}
	public void setAckFlag(boolean set){
		if (set){
			//sets the ACK Flag to 1
			byte[] data = packet.getData();
			data[13] |= 1<<4; 
			packet.setData(data);
			
		}
		else{
			//sets the ACK Flag to 0
			byte[] data = packet.getData();
			data[13] &= ~(1<<4); 
			packet.setData(data);
		}
	}
	public boolean getAckFlag(){
		return ((packet.getData()[13] & (1<<4)) != 0);
	}
	public void setFinFlag(boolean set){
		if (set){
			//sets the FIN Flag to 1
			byte[] data = packet.getData();
			data[13] |= 1<<0; 
			packet.setData(data);
		}
		else{
			//sets the FIN Flag to 0
			byte[] data = packet.getData();
			data[13] &= ~(1<<0); 
			packet.setData(data);
		}
	}
	public boolean checkFinFlag(){
		return ((packet.getData()[13] & (1<<0)) != 0);
	}
	
	//Checksum implimentation borrowed from Gary Rowe at StackOverflow
	private short checksum_calc(){
		byte[] buf = packet.getData();
		int length = buf.length;
	    int i = 0;

	    long sum = 0;
	    long data;
	    
	    while (length > 1) {
	      data = (((buf[i] << 8) & 0xFF00) | ((buf[i + 1]) & 0xFF));
	      sum += data;
	      if ((sum & 0xFFFF0000) > 0) {
	        sum = sum & 0xFFFF;
	        sum += 1;
	      }

	      i += 2;
	      length -= 2;
	    }

	    if (length > 0) {
	      sum += (buf[i] << 8 & 0xFF00);
	      if ((sum & 0xFFFF0000) > 0) {
	        sum = sum & 0xFFFF;
	        sum += 1;
	      }
	    }
	    
	    sum = ~sum;
	    sum = sum & 0xFFFF;
	    return (short)sum;
    }
	
	
	byte[] concatenateByteArrays(byte[] a, byte[] b) {
	    byte[] result = new byte[a.length + b.length]; 
	    System.arraycopy(a, 0, result, 0, a.length); 
	    System.arraycopy(b, 0, result, a.length, b.length); 
	    return result;
	} 
}
