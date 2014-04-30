import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;


public class bfclient extends TimerTask{
	//InetAddress ipAddress;
	Integer port;
	int timeout;
	HashMap<String, HashMap<String, ArrayList<String>>> neighbors = new HashMap<String, HashMap<String, ArrayList<String>>>();
	ServerSocket listener;
	public bfclient(String[] args){
		//this.ipAddress=InetAddress.getByName("localhost");
		this.port=Integer.parseInt(args[0]);
		this.timeout=Integer.parseInt(args[1]);
		for (int i = 2; i<args.length; i+=3){
			HashMap<String, ArrayList<String>> hash = new HashMap<String, ArrayList<String>>(); //hash table of port and Array of [Current weight, Actual weight, cost, gateway ip, gateway port] 
			ArrayList<String> costAndSource = new ArrayList<String>();
			costAndSource.add(args[i+2]); //current cost
			costAndSource.add(args[i+2]); //actual cost
			costAndSource.add(args[i]);   //Source IP
			costAndSource.add(args[i+1]); //Source Port
			hash.put(args[i+1], costAndSource);
			neighbors.put(args[i],hash);
		}
		try {
			listener = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(this, 0, timeout*3);
	}

	public void run(){
		StringBuffer buffer = new StringBuffer("");
		//listen phase
		Socket responseFrom;
		try {
			responseFrom = listener.accept();
			DataInputStream is = new DataInputStream(responseFrom.getInputStream());
			buffer.append(is.read());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		updateTable(buffer.toString().split("[ ]+"));
		for(String address:neighbors.keySet()){
			for(String port:neighbors.get(address).keySet()){
				ArrayList<String> info = neighbors.get(address).get(port);
				if(Double.parseDouble(info.get(0)) != Double.MAX_VALUE)
					buffer.append(address+" "+port+ " " + info.get(0)+" "+info.get(2)+" "
				+listener.getInetAddress().toString()+" "+listener.getLocalPort()+ " "); //attach address, port, cost, source address, source port
			}
        }
        
        for(String address:neighbors.keySet()){
 			for(String port:neighbors.get(address).keySet()){
 				Socket sendTo;
				try {
					DataOutputStream os;
					sendTo = new Socket(InetAddress.getByName(address), Integer.parseInt(port));
					os = new DataOutputStream(sendTo.getOutputStream());
					os.writeBytes(buffer.toString());
					os.close();
					
				} catch (NumberFormatException | IOException e) {
					e.printStackTrace();
				} 
 			}
        }		
	}
	
	
	private void updateTable(String[] fields) {
		for(int i = 0; i< fields.length; i+=5){
			HashMap<String, ArrayList<String>> hash = new HashMap<String, ArrayList<String>>(); //hash table of port and Array of [Current weight, Actual weight,cost, gateway ip, gateway port] 
			ArrayList<String> costAndSource = new ArrayList<String>();
			costAndSource.add(fields[i+2]); //current cost
			costAndSource.add(fields[i+2]); //actual cost
			costAndSource.add(fields[i+3]);   //Source IP
			costAndSource.add(fields[i+4]); //Source Port
			hash.put(fields[i+1], costAndSource);
			if(neighbors.containsKey(fields[i+0]) && neighbors.get(fields[i+0]).containsKey(fields[i+1])){
				if( Integer.parseInt(neighbors.get(fields[i+0]).get(fields[i+1]).get(i+2)) > 
				Integer.parseInt(fields[i+2] + neighbors.get(fields[i+3]).get(fields[i+4]).get(i+2))){
					neighbors.get(fields[i+0]).put(fields[i+1], costAndSource);
				}
			}
			//else if (neighbors.containsKey(fields[i+0]) && !neighbors.get(fields[i+0]).containsKey(fields[i+1])){
			//	neighbors.put(fields[i+0], hash);
			//}
			else if(!neighbors.containsKey(fields[i+0])){ 
				neighbors.put(fields[i+0], hash);
			}
		}
	}

	public void begin(){
		 Scanner in = new Scanner(System.in);
	     System.out.print("bellmen@HW3 $ ");
	     String [] input = in.nextLine().split("[ ]+");
	     if(input[0].equals("LINKDOWN"))
    		 linkdown(input[1], input[2]);
	     else if(input[0].equals("LINKUP"))
	    	 linkup(input[1], input[2]);
		 else if(input[0].equals("SHOWRT"))
			 showrt();
		 else if(input[0].equals("CLOSE")){
			 in.close();
			 System.exit(0);
		 }
		 else
			 System.out.println("Invalid Command. Please try again.");
	}
	

	public void linkdown(String ip, String p){
		if(neighbors.containsKey(ip)){
			if(neighbors.get(ip).containsKey(p)){
				neighbors.get(ip).get(p).set(0,String.valueOf(Double.MAX_VALUE));
			}
			else
				System.out.println("Port number invalid. Please try again.");
		}
		else
			System.out.println("IP Address does not exist in table. Please try again.");
	}
	
	public void linkup(String ip, String p){
		if(neighbors.containsKey(ip)){
			if(neighbors.get(ip).containsKey(p)){
				neighbors.get(ip).get(p).set(0,neighbors.get(ip).get(p).get(1));
			}
			else 
				System.out.println("Port number invalid. Please try again.");
		}
		else
			System.out.println("IP Address does not exist in table. Please try again.");
	}
	
	public void showrt(){
		for(String address:neighbors.keySet()){
			for(String port:neighbors.get(address).keySet()){
				ArrayList<String> info = neighbors.get(address).get(port);
				if(Double.parseDouble(info.get(0)) != Double.MAX_VALUE)
					System.out.println("Destination = " + address+":"+port+", Cost = " + info.get(0)+", Link = ("+info.get(2)+":"+info.get(3)+")");
				else
					System.out.println("Destination = " + address+":"+port+", Cost = " + Double.POSITIVE_INFINITY+", Link = ("+info.get(2)+":"+info.get(3)+")");
			}
		}
	}

	
	public static void main(String [] args){
		bfclient bell = new bfclient(args);
		while(true){
			bell.begin();
		}
	}
}
