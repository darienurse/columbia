This program consists of three classes; Receiver, PacketHandler, and 
Sender. Receiver assumes that it’s packets will be sent uninhibited to 
the Sender, however it will timeout and resend after 10 seconds. 
PacketHandler deals with pulling, getting, and manipulating data in 
various packets. Sender will initially have an estimated RTT of 250ms; 
this value will change as Sample RTT values are obtained. Sender can only 
handle window sizes of 1 and ignores the window_size parameter from the 
main method. This program simply requires the user to run the Sender and 
Receiver (in either order); however it would be better to run the reciever first to avoid 
multiple inital resends by the by the Sender. 

A. TCP segments consist of a TCP Header and data. TCP Header is always 20 
bytes long along. Data is a maximum of 576 bytes long.
B. Sender states: initialization, send, wait for response, resent 
previous packet due to timeout, resent previous packet due to bad ACK 
flag, resend on correctly received packet.
Receiver states: initialization, initial receive(waits forever for a 
packet), subsequent receive (will timeout and resend after 10 seconds, 
discard duplicate packet and wait for correct packet,  resend due to bad 
checksum, resend due to correct packet.
C. Loss Recovery is simple handled on timeouts since the window size is 
always one. If a packet being sent to the Receiver is lost, it will be 
resent by the Sender after a fixed time.
