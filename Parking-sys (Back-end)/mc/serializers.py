from rest_framework import serializers
from django.db import models
from .models import RegisterUser, ShawPark, UserBook

class UserSerializer(serializers.ModelSerializer):
    username   = serializers.CharField(max_length=10)
    
    class Meta:
        model    = RegisterUser
        ordering = ["username"]
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
    givenplot= serializers.BooleanField()      
    plotid   = serializers.IntegerField()
    userpin = serializers.IntegerField()
    
    class Meta:
        model    = UserBook
        ordering = ["givenplot", "plotid", "userpin"]
        fields   = '__all__'
