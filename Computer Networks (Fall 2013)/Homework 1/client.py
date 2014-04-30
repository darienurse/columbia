'''
Created on Sep 21, 2013

@author: darienurse
'''
import asyncore
import socket
import _thread

class AsyncClient(asyncore.dispatcher):
    
    def __init__(self, host, port):
        asyncore.dispatcher.__init__(self)
        self.create_socket(socket.AF_INET, socket.SOCK_STREAM)
        self.connect( (host, port) )
        self.buffer = (b'Hello')
        self.recieve = ''
        self.user = ""

    def handle_connect(self):
        pass

    def handle_close(self):
        if not self.connect:
            sys.stderr.write("Unable to connect to server. Please try again later")
        self.close()

    def handle_read(self):
         self.recieve = self.recv(8192).decode("utf-8")
         try:
            print(self.recieve.split(' ',1)[1])
         except:
            pass
        
    def writable(self):
        return (len(self.buffer) > 0)

    def handle_write(self):
        try:
            code = int(self.recieve.partition(' ')[0])
        except(ValueError):
            code = -1
        if code == -1:
            #print("YO")
            self.send(b" ")
        elif code == 0:
            self.enter_user()
        elif code == 1:
            self.enter_pass()
        elif code == 2:
            _thread.start_new_thread(AsyncClient.enter_command, (self,))
            #self.enter_command()
        elif code == 4:
            self.close()
        else:
            print ("NO CODE FOR MESSAGE")
        self.recieve=""
            
    def enter_user(self):
        self.user=input("Enter Username: ")
        self.send(self.user.encode("utf-8"))
    def enter_pass(self):
        self.send(input("Enter Password: ").encode("utf-8"))
    def enter_command(self):
        self.send(input(self.user+"@SimpleServer:~$ ").encode("utf-8"))
        

if __name__ == '__main__':
    client = AsyncClient('localhost',8080)
    asyncore.loop()