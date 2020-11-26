"""
from socket import *
from select import select
import sys

HOST = '222.108.65.104'
PORT = 8765
BUFSIZE = 1024

clientSocket = socket(AF_INET,SOCK_STREAM)

try:
    clientSocket.connect((HOST, PORT))
    clientSocket.sendall(bytes("Hello, Server!\n",'UTF-8'))
    print("SEND")
    data = clientSocket.recv(BUFSIZE)
    print(data.decode())
except Exception as e:
     print(e)

"""

from socket import *
import serial
from datetime import datetime
now = datetime.now()

s=socket(AF_INET,SOCK_STREAM)
s.connect(('222.108.65.104',8765))
ser = serial.Serial('/dev/ttyACM0', 9600)

strser = ser.readline().decode('UTF-8')

time = '%s-%s-%s %s:%s'%(now.year, now.month, now.day, now.hour, now.minute)

data=strser+time

print(data)
try:
    s.sendall(bytes(data,'UTF-8'))
    print("SEND")
    msg = s.recv(1024)
    print(msg.decode())
    if msg != "empty":
        print("RECEIVED")
except Exception as ex:
    print(ex)
