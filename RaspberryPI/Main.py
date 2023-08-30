from ControlTempAndHum import *
from SnowRemoval import *
from MicrodustRemoval import *
from DetectInvader import *
from DB import *

import threading
import time
import pymysql
import math
import datetime

if __name__ == '__main__':
    #1. Object declare
    
    #Control Temp and Hum
    MesureTempAndHum = MesureTempAndHum()
    JudgementTempAndHumControlFunction = JudgementTempAndHumControlFunction()
    ControlInsideWaterSpray = ControlInsideWaterSpray()
    ControlGreenHouseSide = ControlGreenHouseSide()
    ControlHeatingWire = ControlHeatingWire()
    ControlVentilator = ControlVentilator()
    
    #SnowRemoval
    MesurePressure = MesurePressure()
    JudgementCollapseRisk = JudgementCollapseRisk()
    OperateSnowRemovalDevice = OperateSnowRemovalDevice()
    
    #Detect Invader
    DetectInvader = DetectInvader()
    
    #MicrodustRemoval
    MesureMicrodust = MesureMicrodust()
    JudgementMicrodustRemovalDevice = JudgementMicrodustRemovalDevice()
    ControlOutsideWaterSpray = ControlOutsideWaterSpray()
    
    
    #2. Variable declare
    
    #Control Temp And Hum
    temp = 0
    hum = 0
    pressure = 0
    incTemp = False
    decTemp = False
    incHum = False
    decHum = False
    
    #Snow Removal
    collapserisk = False
    
    #Microdust Removal
    pm1_0 = 0
    pm2_5 = 0
    pm10_0 = 0
    microdustSum = 0
    outsideWaterSpray = False
    
    #ControlValue
    valueFromDB = ReadControlValue()
    
    def SnowRemoval():
        while True:
            if valueFromDB[0][1] == 'T':
                MesurePressure.SetPressure()
                pressure = MesurePressure.GetPressure()
                JudgementCollapseRisk.SetPressure(pressure)
                JudgementCollapseRisk.SetCollapseRisk()
                collapserisk = JudgementCollapseRisk.GetCollapseRisk()
                OperateSnowRemovalDevice.SetOperateSnowRemovalDevice(collapserisk)  
            if valueFromDB[0][1] == 'F':
                if valueFromDB[0][7] == 'T':
                    OperateSnowRemovalDevice.SetOperateSnowRemovalDevice(True)
                else:
                    OperateSnowRemovalDevice.SetOperateSnowRemovalDevice(False)
                time.sleep(2)
            
    def TempAndHumControl():
        while True:
            if valueFromDB[0][1] == 'T':
                MesureTempAndHum.SetTempAndHum()#Read temp and hum from sensor
                hum, temp = MesureTempAndHum.GetTempAndHum()
                MesureTempAndHum.SendTempAndHumToDB()#send temp and hum to db
                print("Green House Temp And Hum : ",temp, hum)
                JudgementTempAndHumControlFunction.SetSuitableTempAndHum(valueFromDB[0][10])
                #print("Suitable Temp and Hum", JudgementTempAndHumControlFunction.suitableTempMaxDay, JudgementTempAndHumControlFunction.suitableTempMinDay, JudgementTempAndHumControlFunction.suitableHumMax, JudgementTempAndHumControlFunction.suitableHumMin)            
                JudgementTempAndHumControlFunction.SetGreenHouseTemp(temp,hum)
                JudgementTempAndHumControlFunction.SetTempAndHumIncOrDec()
                incTemp, decTemp, incHum, decHum = JudgementTempAndHumControlFunction.GetTempAndHumIncOrDec()
                print("TempAndHumIncOrDec : ",incTemp, decTemp, incHum, decHum)
                print("-----------------------Actuator-----------------------")
                ControlInsideWaterSpray.SetJudgementOperateInsideSpray(incTemp, decTemp, incHum, decHum)
                ControlInsideWaterSpray.SetOperateInsideWaterSpray()
                ControlGreenHouseSide.SetJudgementOperateGreenhouseSide(incTemp, decTemp, incHum, decHum)
                ControlGreenHouseSide.SetOperateGreenHouseSide()
                ControlHeatingWire.SetJudgementOperateHeatingWire(incTemp, decTemp, incHum, decHum)
                ControlHeatingWire.SetOperateHeatingWire()
                ControlVentilator.SetJudgementOperateVentilator(incTemp, decTemp, incHum, decHum)
                ControlVentilator.SetOperateVentilator()
                print("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n")
            if valueFromDB[0][1] == 'F':
                MesureTempAndHum.SetTempAndHum()#Read temp and hum from sensor
                hum, temp = MesureTempAndHum.GetTempAndHum()
                MesureTempAndHum.SendTempAndHumToDB()#send temp and hum to db
                print("Green House Temp And Hum : ",temp, hum)
                ControlInsideWaterSpray.SetOperateInsideWaterSprayManual(valueFromDB[0][4])
                ControlGreenHouseSide.SetOperateGreenHouseSideManual(valueFromDB[0][2], valueFromDB[0][3])
                ControlHeatingWire.SetOperateHeatingWireManual(valueFromDB[0][6])
                ControlVentilator.SetOperateVentilatorManual(valueFromDB[0][5])
                print("-------------------Manual----------------------")
                time.sleep(2)
    
    def Security():
        while True:
            startTotalSeconds = valueFromDB[0][8].total_seconds()
            endTotalSeconds = valueFromDB[0][9].total_seconds()
            secondsInHour = 3600
            secondsInMinute = 60
            startHours = startTotalSeconds / secondsInHour
            startMinutes = startTotalSeconds / secondsInMinute % 60
            endHours = endTotalSeconds / secondsInHour
            endMinutes = endTotalSeconds / secondsInMinute % 60
            
            detectStart = datetime.time(math.trunc(startHours), math.trunc(startMinutes), 0)
            detectEnd = datetime.time(math.trunc(endHours), math.trunc(endMinutes), 0)
            
            if detectStart.hour < detectEnd.hour :
                if detectStart.hour < datetime.datetime.now().hour < detectEnd.hour :
                    DetectInvader.DetectOperate()
            elif detectEnd.hour < detectStart.hour :
                if (detectStart.hour < datetime.datetime.now().hour) or (datetime.datetime.now().hour < detectEnd.hour) :
                    DetectInvader.DetectOperate()
    
    def MicroDust():
        while True:
            if valueFromDB[0][1] == 'T':
                MesureMicrodust.MesureMicrodust()
                pm1_0, pm2_5, pm10_0 = MesureMicrodust.GetMicroDust()
                MesureMicrodust.SendMicrodustToDB(pm1_0, pm2_5, pm10_0)
                
                microdustSum = MesureMicrodust.GetMicroDustSum()
                JudgementMicrodustRemovalDevice.SetMicrodustSum(microdustSum)
                JudgementMicrodustRemovalDevice.JudgementMicrodustRemovalDevice()
                outsideWaterSpray = JudgementMicrodustRemovalDevice.GetMicrodustRemovalDeviceOperation()                
                ControlOutsideWaterSpray.SetOperateOutsideWaterSpray(outsideWaterSpray)                
                OperateSnowRemovalDevice.SetOperateSnowRemovalDevice(True)
                time.sleep(2)
                OperateSnowRemovalDevice.SetOperateSnowRemovalDevice(False)
                
            if valueFromDB[0][1] == 'F':
                MesureMicrodust.MesureMicrodust()
                pm1_0, pm2_5, pm10_0 = MesureMicrodust.GetMicroDust()
                MesureMicrodust.SendMicrodustToDB(pm1_0, pm2_5, pm10_0)
                ControlOutsideWaterSpray.SetOperateOutsideWaterSpray(valueFromDB[0][11])                
                time.sleep(2)
        
                
    #Operate
    
    #servo_thread1 = threading.Thread(target=TempAndHumControl)
    #servo_thread1.start()
    #servo_thread2 = threading.Thread(target=SnowRemoval)
    #servo_thread2.start()
    #servo_thread3 = threading.Thread(target=Security)
    #servo_thread3.start()
    servo_thread4 = threading.Thread(target=MicroDust)
    servo_thread4.start()
    
    while True:
        valueFromDB = ReadControlValue() 
        time.sleep(3)
