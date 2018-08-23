# -*- coding: utf-8 -*-
from __future__ import unicode_literals
import requests
import random
import json
import hashlib
from django.shortcuts import render, get_object_or_404
from django.template import loader
from django.http import HttpResponse, HttpRequest
from rest_framework.renderers import JSONRenderer
from .models import RegisterUser, ShawPark, UserBook
from .serializers import UserSerializer, ShawSerializer, BookSerializer

def uregister(request, uname):
	dup_user = RegisterUser.objects.filter(usarname=uname).exists()
	
	if dup_user:
		return HttpResponse("<h1>Choose different username.<h1>", content_type="text/html")
	else:
	#	paas = hashlib.pbkdf2_hmac('sha256', "abc", uname, 10000)
	#	user = RegisterUser(username=uname, password=hashlib.sha256("abc"))
		user = RegisterUser(usarname=uname)
		user.save()
		return HttpResponse("<h1>you have been successfully registered!<h1>", content_type="text/html")
       
def ulogin(request, uname):
#	paas = hashlib.pbkdf2_hmac('sha256', "abc", uname, 10000)
#	user = get_object_or_404(RegisterUser, username=uname, password=hashlib.sha256("abc"))
    	user = get_object_or_404(RegisterUser, usarname=uname)
    	
	userserializer = UserSerializer(user)
	userjson = JSONRenderer().render(userserializer.data)
    
	return HttpResponse(userjson, content_type="application/json")
        
def upark(request, uname):
	shawpark = ShawPark(parkid=1, city="Udaipur", address="C-31, Surajpole")
	shawpark.save()	
    
	shawserializer = ShawSerializer(shawpark)
	shawjson = JSONRenderer().render(shawserializer.data)
    
	return HttpResponse(shawjson, content_type="application/json")
    
def ubook(request, uname):
	uRl = "http://127.0.0.1:9000/"
	req = requests.get(url=uRl).json()
    
	req1 = json.dumps(req)
	data = json.loads(req1)
    
	plot=[]
    
	for c in data['mystring']:
		if c!=' ' and c!= '\n':
			if int(c)<58 and int(c)>47:
        	       		plot.append(int(c)-48)	

	for i in plot:
        	usarbook=UserBook(plotid=i, userpin=random.randint(1,10)*1000)
        	usarbook.save()

    	for j in UserBook.objects.all():
        	if j.givenplot == 0:
        		j.givenplot == 1
            		j.save()
            		break
    
	userbook = UserBook(plotid=j.plotid, userpin=random.randint(1,10)*1000)
	userbook.save()
    
	bokserializer = BookSerializer(userbook)
	bokjson = JSONRenderer().render(bokserializer.data)
    
	return HttpResponse(bokjson, content_type="application/json") 
 
def check(request):
    #template = loader.get_template('mc/index.html')
    #return HttpResponse(template.render(request))
	if request.method == 'GET':
		return render(request, 'mc/index.html')
	else:
		peen = request.POST.get('PIN')
		userexists = UserBook.objects.filter(userpin=peen)
		userexists.givenplot = 0
		userexists.update()
		
	if userexists.exists():
      # return render('mc/index.html')
		return HttpResponse("<h1>Correct pin!<h1>", content_type="text/html")
        else:
        	return HttpResponse("<h1>Wrong pin!<h1>", content_type="text/html")

