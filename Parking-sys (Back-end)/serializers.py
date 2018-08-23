from rest_framework import serializers
from django.db import models
from .models import RegisterUser, ShawPark, UserBook

class UserSerializer(serializers.ModelSerializer):
	usarname   = serializers.CharField(max_length=20)
#    	paasword   = serializers .CharField(max_length=64, default=None)

    	class Meta:
    	    model    = RegisterUser
   	    	ordering = ["usarname"]
    	    fields   = '__all__'

class ShawSerializer(serializers.ModelSerializer):
     	parkid    = serializers.IntegerField()
     	lon      = serializers.FloatField()
     	let      = serializers.FloatField()
     	tot      = serializers.IntegerField()
     	avail    = serializers.IntegerField()
     	city      = serializers.CharField(max_length=10)
     	address   = serializers.CharField(max_length=100)

     	class Meta:
     	   model    = ShawPark
     	   ordering = ["parkid", "lon", "let", "tot", "avail", "city", "address"]
     	   fields   = '__all__'

class BookSerializer(serializers.ModelSerializer):
     	givenplot  = serializers.IntegerField()
     	plotid     = serializers.IntegerField()
     	userpin   = serializers.IntegerField()

     	class Meta:
     	   model    = UserBook
     	   ordering = ["givenplot", "plotid", "userpin"]
     	   fields   = '__all__'
