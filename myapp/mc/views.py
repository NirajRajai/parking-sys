# -*- coding: utf-8 -*-
from __future__ import unicode_literals
import requests
import random
import json
from django.shortcuts import render, get_object_or_404
from django.template import loader
from django.http import HttpResponse, HttpRequest
from rest_framework.renderers import JSONRenderer
from .models import RegisterUser, ShawPark, UserBook
from .serializers import UserSerializer, ShawSerializer, BookSerializer

def u_index(request):
    return HttpResponse("<h1>Hello, motherfucker!<h1>", content_type="text/html")

def uregister(request, uname):
    dup_user = RegisterUser.objects.filter(username=uname).exists()
    if dup_user:
       return HttpResponse("<h1>Choose different username.<h1>", content_type="text/html")
    else:
       user = RegisterUser(username=uname)
       user.save()
       return HttpResponse("<h1>you have been successfully registered!<h1>", content_type="text/html")
       
def ulogin(request, uname):
    user = get_object_or_404(RegisterUser, username=uname)
    
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
    uRl = "http://192.168.43.224:8080/"
    req = requests.get(url=uRl).json()
    plot=[]  
    data = json.loads(req["mystring"])
    for c in data:
        if int(c)<58 and int(c)>47:
            plot.append(int(c))	

    for i in plot:
        usarbook=UserBook(givenplot=False, plotid=i, userpin=random.randint(1,10)*1000)
        usarbook.save()

    usarbok = UserBook.objects.filter(givenplot=False)
    usarbok.givenplot = False
    usarbok.save()
    
    bokserializer = BookSerializer(usarbok)
    bokjson = JSONRenderer().render(bokserializer.data)
    
    return HttpResponse(bokjson, content_type="application/json")
   
def check(request):
    #template = loader.get_template('mc/index.html')
    #return HttpResponse(template.render(request))
    if request.method == 'GET':
       return render(request, 'mc/index.html')
    else:
       peen = request.POST.get('PIN')
       userexists = UserBook.objects.filter(userpin=peen).exists()

    if userexists :
      # return render('mc/index.html')
       return HttpResponse("<h1>Correct pin!<h1>", content_type="text/html")
    else:
       return HttpResponse("<h1>Wrong pin!<h1>", content_type="text/html")
