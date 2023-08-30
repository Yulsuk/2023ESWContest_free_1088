import RPi.GPIO as GPIO
import subprocess
import time
import requests
from PIN import *
 
# Use BCM GPIO references
# instead of physical pin numbers
GPIO.setmode(GPIO.BCM)
 
# Define GPIO to use on Pi
# url to send image for upload
url = "http://220.69.240.29/uploadImg.php"
 

 
# Set pin as input
GPIO.setup(GPIO_PIR,GPIO.IN)      # Echo


class DetectInvader():
  def __init__(self):
    self.Current_State  = 0
    self.Previous_State = 0
    
  def DetectOperate(self):
    try: 
      print ("PIR Module Test (CTRL-C to exit)")
      print ("Waiting for PIR to settle ...")
     
      # Loop until PIR output is 0
      while GPIO.input(GPIO_PIR)==1:
        self.Current_State  = 0
     
      print ("  Ready")
     
      # Loop until users quits with CTRL-C
      while True :   
        # Read PIR state
        self.Current_State = GPIO.input(GPIO_PIR)   
        if self.Current_State == 1 and self.Previous_State==0:
          # PIR is triggered
          print ("  Motion detected!")
          # date time string for file name
          timestr = time.strftime("%Y%m%d-%H%M%S")
          imageFileName = "img-"+timestr+".jpeg"
          # capture image
          subprocess.Popen(["libcamera-jpeg","-o",imageFileName])
          time.sleep(10)
          # send file to server
          files = {'file': open(imageFileName, 'rb')}
          rq = requests.post(url,files=files)
          # Record previous state
          self.Previous_State=1
        elif self.Current_State==0 and self.Previous_State==1:
          # PIR has returned to ready state
          print ("  Ready")
          self.Previous_State=0
     
        # Wait for 10 milliseconds
        time.sleep(0.01)
     
    except KeyboardInterrupt:
      print ("  Quit")
      # Reset GPIO settings
      GPIO.cleanup()
