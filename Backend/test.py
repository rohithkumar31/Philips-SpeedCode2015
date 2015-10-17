import os
import time
import psycopg2
import urlparse
import hashlib
import json
from bottle import Bottle
from psycopg2.extras import RealDictCursor

app = Bottle(__name__)
urlparse.uses_netloc.append("postgres")
url = urlparse.urlparse(os.environ["DATABASE_URL"])

@app.route('/signup/<email>/<passwd>')
def signup(email,passwd):
	conn = psycopg2.connect(
    database=url.path[1:],
    user=url.username,
    password=url.password,
    host=url.hostname,
    port=url.port
	)

	cur = conn.cursor()

	
	var1 = email
	var2 = passwd

	hash_object = hashlib.sha256(str(var2))
	new_passwd = str(hash_object.hexdigest())

	sql = "INSERT INTO public.\"Auth\" VALUES ('"+str(var1)+"','"+new_passwd+"')"

	cur.execute(sql)

	conn.commit()
	cur.close()
	conn.close()

	return "1"

@app.route('/login/<email>/<passwd>')
def login(email,passwd):
	conn = psycopg2.connect(
    database=url.path[1:],
    user=url.username,
    password=url.password,
    host=url.hostname,
    port=url.port
	)

	cur = conn.cursor()

	var1 = email
	var2 = passwd

	hash_object = hashlib.sha256(str(var2))
	new_passwd = str(hash_object.hexdigest())

	sql = "SELECT passwrd FROM public.\"Auth\" WHERE email='"+str(var1)+"'"

	cur.execute(sql)

	res = cur.fetchone()
	res = str(res[0])

	if new_passwd == res :
		return "1"
	else :
		return "0"




@app.route('/insert_prof/<email>/<name>/<sex>/<age>/<address>/<phone>/<emergency>')
def insert_prof(email,name,sex,age,address,phone,emergency):
	conn = psycopg2.connect(
    database=url.path[1:],
    user=url.username,
    password=url.password,
    host=url.hostname,
    port=url.port
	)

	cur = conn.cursor()

	var1 = email
	var2 = name
	var3 = sex
	var4 = age
	var5 = address
	var6 = phone
	var7 = emergency

	
	sql = "INSERT INTO public.\"Profile\"(email,name,sex,age,address,phone,emergency) VALUES ('"+str(var1)+"','"+str(var2)+"','"+str(var3)+"','"+str(var4)+"','"+str(var5)+"','"+str(var6)+"','"+str(var7)+"')"

	cur.execute(sql)

	conn.commit()
	cur.close()
	conn.close()

	return "1"



@app.route('/get_prof/<email>')
def get_prof(email):
	conn = psycopg2.connect(
    database=url.path[1:],
    user=url.username,
    password=url.password,
    host=url.hostname,
    port=url.port
	)

	cur = conn.cursor()

	var1 = email
		
	sql = "SELECT * FROM public.\"Profile\" WHERE email='"+str(var1)+"'"

	cur.execute(sql)

	rows = cur.fetchall()

	x =""

	for i in range(len(rows)):
		
		var1 = str(rows[i]).replace("(","")

		var2 = str(var1).replace(")","")

		x += var2 + "\n"

	return str(x)

	


@app.route('/update_prof/<email>/<name>/<sex>/<age>/<address>/<phone>/<emergency>')
def update_prof(email,name,sex,age,address,phone,emergency):
	conn = psycopg2.connect(
    database=url.path[1:],
    user=url.username,
    password=url.password,
    host=url.hostname,
    port=url.port
	)

	cur = conn.cursor()

	var1 = email
	var2 = name
	var3 = sex
	var4 = age
	var5 = address
	var6 = phone
	var7 = emergency

	
	sql = "UPDATE public.\"Profile\"SET name = '"+str(var2)+"',sex = '"+str(var3)+"' ,age = '"+str(var4)+"',address = '"+str(var5)+"' ,phone = '"+str(var6)+"',emergency = '"+str(var7)+"' WHERE email= '"+str(var1)+"'"

	cur.execute(sql)

	conn.commit()
	cur.close()
	conn.close()

	return "1"



@app.route('/article_feed/<feed>')
def article_feed(feed):
	conn = psycopg2.connect(
    database=url.path[1:],
    user=url.username,
    password=url.password,
    host=url.hostname,
    port=url.port
	)

	cur = conn.cursor()

	
	var1 = feed
	
	
	sql = "INSERT INTO public.\"article\" VALUES ('"+str(var1)+"')"

	cur.execute(sql)

	conn.commit()
	cur.close()
	conn.close()

	return "1"


@app.route('/get_feed')
def get_feed():
	conn = psycopg2.connect(
    database=url.path[1:],
    user=url.username,
    password=url.password,
    host=url.hostname,
    port=url.port
	)

	cur = conn.cursor()

	
	
	
	sql = "SELECT feed FROM public.\"article\""

	cur.execute(sql)

	rows = cur.fetchall()

	x = ""

	for i in range(len(rows)):
		x += str(rows[i]).split("'")[1] + ','

	return str(x)
	


@app.route('/insert_plog/<email>/<heading>/<description>/<medication>/<date>')
def insert_plog(email,heading,description,medication,date):
	conn = psycopg2.connect(
    database=url.path[1:],
    user=url.username,
    password=url.password,
    host=url.hostname,
    port=url.port
	)

	cur = conn.cursor()

	var1 = email
	var2 = heading
	var3 = description
	var4 = medication
	var5 = date
	
	
	sql = "INSERT INTO public.\"Personal_log\"(email,heading,description,medication,t_date) VALUES ('"+str(var1)+"','"+str(var2)+"','"+str(var3)+"','"+str(var4)+"','"+str(var5)+"')"

	cur.execute(sql)

	conn.commit()
	cur.close()
	conn.close()

	return "1"



@app.route('/get_plog/<email>')
def get_plog(email):
	conn = psycopg2.connect(
    database=url.path[1:],
    user=url.username,
    password=url.password,
    host=url.hostname,
    port=url.port
	)

	cur = conn.cursor()

	var1 = email
	
	sql = "SELECT * FROM public.\"Personal_log\" WHERE email='"+str(var1)+"'"

	cur.execute(sql)

	rows = cur.fetchall()

	x =""

	for i in range(len(rows)):
		
		var1 = str(rows[i]).replace("(","")

		var2 = str(var1).replace(")","")

		x += var2 + "\n"

	return str(x)


@app.route('/insert_dlog/<email>/<disease>/<t_date>/<doctor>/<medication>/<report_images>')
def insert_dlog(email,disease,t_date,doctor,medication,report_images):
	conn = psycopg2.connect(
    database=url.path[1:],
    user=url.username,
    password=url.password,
    host=url.hostname,
    port=url.port
	)

	cur = conn.cursor()

	var1 = email
	var2 = disease
	var3 = t_date
	var4 = doctor
	var5 = medication
	var6 = report_images
	
	
	sql = "INSERT INTO public.\"Doc_log\"(email,disease,t_date,doctor,medication,report_images) VALUES ('"+str(var1)+"','"+str(var2)+"','"+str(var3)+"','"+str(var4)+"','"+str(var5)+"','"+str(var6)+"')"

	cur.execute(sql)

	conn.commit()
	cur.close()
	conn.close()

	return "1"
	


@app.route('/get_dlog/<email>')
def get_dlog(email):
	conn = psycopg2.connect(
    database=url.path[1:],
    user=url.username,
    password=url.password,
    host=url.hostname,
    port=url.port
	)

	cur = conn.cursor()

	var1 = email
	
	sql = "SELECT * FROM public.\"Doc_log\" WHERE email='"+str(var1)+"'"

	cur.execute(sql)

	rows = cur.fetchall()

	x =""

	for i in range(len(rows)):
		
		var1 = str(rows[i]).replace("(","")

		var2 = str(var1).replace(")","")

		x += var2 + "\n"

	return str(x)