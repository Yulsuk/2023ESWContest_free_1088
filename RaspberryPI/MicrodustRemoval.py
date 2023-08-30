import DB#for sql
from PIN import *
import time
from collections import deque

import serial
from PMS7003 import PMS7003

dust = PMS7003()
Speed = 9600	# Baud Rate
UART = '/dev/ttyS0'	# UART
SERIAL_PORT = UART	# USE PORT
ser = serial.Serial(SERIAL_PORT, Speed, timeout = 1)	#serial setting

class MesureMicrodust:		
	def __init__(self):
		self.PM1_0 = deque(maxlen = 100)
		self.PM2_5 = deque(maxlen = 100)
		self.PM10_0 = deque(maxlen = 100)
		self.pm1_0 = 0
		self.pm2_5 = 0
		self.pm10_0 = 0
		
	def MesureMicrodust(self):
		ser = serial.Serial(SERIAL_PORT, Speed, timeout = 1)
		buffer = ser.read(1024)
		if(dust.protocol_chk(buffer)):
			data = dust.unpack_data(buffer)
			print ("PMS 7003 dust data")
			print ("PM 1.0 : %s" % (data[dust.DUST_PM1_0_ATM]))
			print ("PM 2.5 : %s" % (data[dust.DUST_PM2_5_ATM]))
			print ("PM 10.0 : %s" % (data[dust.DUST_PM10_0_ATM]))
			self.pm1_0 = int(data[dust.DUST_PM1_0_ATM])
			self.pm2_5 = int(data[dust.DUST_PM2_5_ATM])
			self.pm10_0 = int(data[dust.DUST_PM10_0_ATM])
			self.PM1_0.append(self.pm1_0)
			self.PM2_5.append(self.pm2_5)
			self.PM10_0.append(self.pm10_0)
		else:
			print ("data read Err")
		ser.close()
		
	def SendMicrodustToDB(self, pm1_0, pm2_5, pm10_0):
		DB.SendMicrodustToDB(self.pm1_0, self.pm2_5, self.pm10_0)
		
	def GetMicroDustSum(self):
		return sum(self.PM1_0) + sum(self.PM2_5) + sum(self.PM10_0)
		
	def GetMicroDust(self):
		return self.pm1_0, self.pm2_5, self.pm10_0

class JudgementMicrodustRemovalDevice:
	def __init__(self):
		self.microdustSum = 0
		self.outsideWaterSpray = False
		
	def SetMicrodustSum(self, microdustSum):
		self.microdustSum = microdustSum
		
	def JudgementMicrodustRemovalDevice(self):
		if self.microdustSum > 3000:
			self.outsideWaterSpray = True
		else:
			self.outsideWaterSpray = False
	def GetMicrodustRemovalDeviceOperation(self):
		return self.outsideWaterSpray
			
			
class ControlOutsideWaterSpray:
	def SetOperateOutsideWaterSpray(self, value):
		if value == 'T' or value == True:
			GPIO.output(OUTSIDE_WATERSPRAY,True)
			print('outsideWaterSpray is operating')
		else:
			GPIO.output(OUTSIDE_WATERSPRAY,False)
			print('outsideWaterSpray is idle')		


if __name__ == '__main__':
	aaa = ControlOutsideWaterSpray()
	bbb = MesureMicrodust()
	ccc = JudgementMicrodustRemovalDevice()
	pm1_0 = 0
	pm2_5 = 0
	pm10_0 = 0
	while True:
		bbb.MesureMicroDust()
		pm1_0, pm2_5, pm10_0 = bbb.GetMicroDust()
		bbb.SendMicrodustToDB(pm1_0, pm2_5, pm10_0)
		microdustSum = bbb.GetMicroDustSum()
		ccc.SetMicrodustSum(microdustSum)
		ccc.JudgementMicrodustRemovalDevice()
		#outsideWaterSpray = ccc.GetMicrodustRemovalDeviceOperation()
		#aaa.SetOperateOutsideWaterSpray(outsideWaterSpray)
