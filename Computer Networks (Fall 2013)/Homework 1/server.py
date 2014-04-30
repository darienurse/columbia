'''
Created on Sep 21, 2013

@author: darienurse
'''
import asyncore
import socket, time
import datetime

user_pass={}
blocked={}
current={}
has_logged_in={}
handlers=[]
one_minute = 60
one_hour = 3600

class AsyncServer(asyncore.dispatcher):

    def __init__(self, host, port):
        asyncore.dispatcher.__init__(self)
        self.create_socket(socket.AF_INET, socket.SOCK_STREAM)
        self.set_reuse_addr()
        self.bind((host, port))
        self.listen(5)
        file = open('users.txt', 'r')
        for line in file:
            user_pass[line.partition(' ')[0].rstrip()]=line.partition(' ')[2].rstrip()
    def handle_accepted(self, sock, addr):
        print('Incoming connection from %s' % repr(addr))
        #print(sock)
        #print(addr[0] in blocked)
        #print(str(blocked.items()))
        if addr[0] in blocked:
            if (datetime.datetime.now()-blocked[addr[0]]).seconds>one_minute:
                blocked.pop(addr[0])
                handler = Handler(sock,addr[0])
                handlers.append(handler)
            else:
                handler = Handler(sock,addr[0])
                handlers.append(handler)
        else:        
            handler = Handler(sock,addr[0],self)
            handlers.append(handler)
    def broadcast(self,hi, message):
        #print("yo")
        for hand in handlers:
            hand.send(b"2 " + message.encode("utf-8"))
        
        
        
class Handler(asyncore.dispatcher_with_send):
    def __init__(self, sock, addr, server):
        asyncore.dispatcher_with_send.__init__(self,sock)
        self.state = 0
        self.fail_counter = 0
        self.user =""
        self.addr = addr
        self.socket = sock
        self.server = server
        
    def login_fail(self):
        self.fail_counter += 1
        if self.fail_counter == 3:                   
            blocked[self.addr] = datetime.datetime.now()
            self.send(b"Your address has been blocked")
            self.close()
        else:
            self.send("0 Incorrect username/password or this user is already logged in. Please, try again.".encode("utf-8"))
        pass
    
    def handle_close(self):
        if self.user in current:
            current.pop(self.user)
            has_logged_in[self.user] = datetime.datetime.now()  
        self.close
        
    def handle_read(self):
        data = self.recv(8192).decode("utf-8")
        if self.state==0:
            self.state = 1
            self.send(b"0 Server Connection Established")
        if not data.isspace():    
            data = data.strip()
            
            if self.state==1:
                self.send(b"1 user enetered")
                self.user = data
                self.state = 2  
                
            elif self.state==2:
                #print(str(user_pass[self.user]==data))
                #print("PASS:" +user_pass[self.user]+ str(len(user_pass[self.user])) + "   Data:" + data+str(len(data)))
                #print("USER: " +self.user+" PASS: " + str(data))
                if(self.user in user_pass):
                    if(user_pass[self.user]==data and self.user not in current):
                        self.send(('2 Welcome to the server, '+self.user).encode("utf-8"))
                        current[self.user] = self.socket
                        self.state = 3
                    else:
                        self.login_fail()
                        self.state = 1
                else:
                   self.login_fail()
                   
            elif self.state==3:     
                if data=="whoelse":
                    self.send(("2 " + ', '.join(current.keys())).encode("utf-8"))
                elif data=="wholasthr":
                    cur = []
                    for user in has_logged_in.keys():
                        if(datetime.datetime.now()-has_logged_in[user]).seconds<one_hour:
                            cur.append(user)
                    self.send(("2 " + ', '.join(cur+list(current.keys()))).encode("utf-8"))
                elif data.partition(' ')[0] == "broadcast":
                    if data.partition(' ')[2]=="":
                        #print (data.partition(' ')[2])
                        self.send(b"2 You have to send a message with your broadcast, silly!")
                    else:
                        print("Broadcasting from <" + self.user +">: '"+data.partition(' ')[2]+"'")
                        self.server.broadcast(self,data.partition(' ')[2])
                elif data == "logout":
                    self.send(b"4 See ya later dude!")
                    self.handle_close()
                else:
                    self.send(b"2 INVALID command")
                    
            
if __name__ == '__main__':
    server = AsyncServer('localhost',8080)
    asyncore.loop()