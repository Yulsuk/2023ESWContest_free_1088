import spidev
import time
import RPi.GPIO as GPIO
import pigpio
from PIN import *

spi = spidev.SpiDev()
spi.open(0, 0)
mcp3008_channel = 0 #ADC channel
delay = 0.1
pi = pigpio.pi()

def ReadSenser(channel): #convert pressure to data
    adc = spi.xfer([1, (8 + channel) << 4, 0])
    data = ((adc[1] & 3) << 8) + adc[2]
    return data

class MesurePressure: #mesure the pressure
    def __init__(self):
        self.pressure = 0
    def SetPressure(self):
        analog_level = ReadSenser(mcp3008_channel)
        self.pressure = analog_level
    def GetPressure(self):
        return self.pressure

class JudgementCollapseRisk: #judgement the collapserisk based on pressure
    def __init__(self):
        self.collapseRisk = False
        self.pressure = 0
    def SetPressure(self, pressure):
        self.pressure = pressure
    def SetCollapseRisk(self):
        if(self.pressure > 100):
            print("pressure : ",self.pressure)
            self.collapseRisk = True 
        else:
            self.collapseRisk = False
    def GetCollapseRisk(self):
        return self.collapseRisk
        
class OperateSnowRemovalDevice:
    def __init__(self):
        self.operateSnowRemovalDevice = False
    def SetOperateSnowRemovalDevice(self, collapserisk):
        if(collapserisk == True): #when collapserisk is true : operate snowremovaldevice
            time.sleep(1)
            pi.set_servo_pulsewidth(servo_pin1,600)
            pi.set_servo_pulsewidth(servo_pin2,2200)
            time.sleep(1)
            pi.set_servo_pulsewidth(servo_pin1,1400)
            pi.set_servo_pulsewidth(servo_pin2,1400)
            time.sleep(1)
            print("Wiper is Operating")
        elif(collapserisk == False): #when collapserisk is false : do nothing
            print("Wiper is Idle")
            time.sleep(1)
	
if __name__ == '__main__':
    MesurePressure = MesurePressure()
    JudgementCollapseRisk = JudgementCollapseRisk()
    OperateSnowRemovalDevice = OperateSnowRemovalDevice()
           
    pressure = 0
    collapserisk = False
    while True:
        MesurePressure.SetPressure()
        pressure = MesurePressure.GetPressure()
        JudgementCollapseRisk.SetPressure(pressure)
        JudgementCollapseRisk.SetCollapseRisk()
        collapserisk = JudgementCollapseRisk.GetCollapseRisk()
        OperateSnowRemovalDevice.SetOperateSnowRemovalDevice(collapserisk)
