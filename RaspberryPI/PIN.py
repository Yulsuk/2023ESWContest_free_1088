import RPi.GPIO as GPIO
import pigpio

GPIO.setwarnings(False)

FAN = 4
HEATINGWIRE = 12
INSIDE_WATERSPRAY = 21
OUTSIDE_WATERSPRAY = 2
GPIO_PIR = 16
DHT_GPIO = 6#for mesure temp and hum
SERVO_MOTOR1 = 13#for greenhouseSide
SERVO_MOTOR2 = 17
servo_pin1 = 18#for wiper
servo_pin2 = 23

pi = pigpio.pi()

GPIO.setmode(GPIO.BCM)
GPIO.setup(FAN, GPIO.OUT)
GPIO.setup(HEATINGWIRE, GPIO.OUT)
GPIO.setup(INSIDE_WATERSPRAY, GPIO.OUT)
GPIO.setup(OUTSIDE_WATERSPRAY, GPIO.OUT)
