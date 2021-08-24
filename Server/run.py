


from flask import Flask, request,jsonify
import mraa
import json
import firebase
import thread
import time
from light import ldr_getvalues,ledon,ledoff,xchangemode
from servo import fanoff,fanon,autofanon,changemode
import pyupm_grove as grove

app = Flask(__name__)


# This is used while interfacing with normal LEDs
# ledpin = mraa.Gpio(13)
# ledpin.dir(mraa.DIR_OUT)
# ldrpin=mraa.Aio(0)



db = firebase.FirebaseApplication('https://homeautomation-890be.firebaseio.com/',None)


light = grove.GroveLight(1)
pwm = mraa.Pwm(5)


@app.route('/fetchdata')
def fetchdata():
	data = firebaseget()
	return json.dumps(data)

##LIGHT


@app.route('/setlightmode',methods=['POST'])
def setmode():
	data=request.data
	print data
	data=json.loads(data)
	print data
	if data['mode']=="0":
		xchangemode()
                print "manual"
	else:
		thread.start_new_thread( ldr_getvalues, () )
                print "auto"
	return jsonify({
			'message':'mode set'
			})

@app.route('/ledmanipulate', methods=['POST'])
def ledmanipulate():
    data = request.data
    print data
    data = json.loads(data)
    print data
    if data['state'] =="1":
        ledon()
       firebasepush(data)
    else:
        ledoff()
            firebasepush(data)
    return jsonify({
        'message':'success'
        })


##FAN

@app.route('/setfanmode',methods=['POST'])
def setfanmode():
	data=request.data
	data=json.loads(data)
	print data
	if data['mode']=="0":
		changemode()
		print "manual"
		firebasepush(data)
	else:
		thread.start_new_thread( autofanon, () )
		print "fan on"
		firebasepush(data)
	return jsonify({
			'message':'mode set'
			})


@app.route('/fanmanipulate',methods=['POST'])
def fanmanipulate():
	data = request.data
	print data
	data = json.loads(data)
	print data

	if data['state'] == "1":
		print "fan turned on"
		fanon()
		firebasepush(data)
	else:
		print "fan turned off"
		fanoff()
		firebasepush(data)
	return jsonify({
			'message':'success'
			})






if(__name__ == '__main__'):
    app.run(host='0.0.0.0')
