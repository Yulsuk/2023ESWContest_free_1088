import pymysql
import time

db = pymysql.connect(host='220.69.240.29', port=3306, user='rasp', passwd='selab', db='greenhouse', charset='utf8')
curs = db.cursor()

def ReadControlValue():
	db = pymysql.connect(host='220.69.240.29', port=3306, user='rasp', passwd='selab', db='greenhouse', charset='utf8')
	curs = db.cursor()
	sql = 'SELECT * FROM apptorasp_controlvalue'
	curs.execute(sql)
	result = curs.fetchall()
	
	return result

def SendTempAndHumToDB(temp, hum):
	sql = 'INSERT INTO rasptoapp_tempandhum (temperature, humidity) VALUES (%s, %s)'
	curs.execute(sql, (temp,hum))
	db.commit()
	#db.close()
	
def SendMicrodustToDB(pm1_0, pm2_5, pm10_0):
	sql = 'INSERT INTO rasptoapp_microdust (pm1_0, pm2_5, pm10_0) VALUES (%s, %s, %s)'
	curs.execute(sql, (pm1_0,pm2_5,pm10_0))
	db.commit()
	#db.close()

if __name__ == '__main__':
	result = ReadControlValue()
	while True:
		print(result[0])
		time.sleep(5)
