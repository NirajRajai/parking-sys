# -*- coding: utf-8 -*-
from __future__ import unicode_literals
from django.db import models
from django.contrib.auth.models import User

class RegisterUser(models.Model):
	usarname = models.CharField(max_length=20, primary_key=True, default='')
#	paasword = models.CharField(max_length=64, default=None)
#	email_id = models.CharField(max_length=256, primary_key=True)
#   	user_ptr_id = models.IntegerField(primary_key=True)
    	def __str__(self):
        	return self.usarname
        	
class ShawPark(models.Model):
	parkid   = models.IntegerField(primary_key=True)
	lon      = models.FloatField(default=73.7236456)
    	let      = models.FloatField(default=24.5968106)
    	tot      = models.IntegerField(default=8)
    	avail    = models.IntegerField(default=4)
    	city     = models.CharField(max_length=10)
    	address  = models.CharField(max_length=100)
  
    	def __str__(self):
    	    return self.city
        
class UserBook(models.Model):
    	givenplot= models.IntegerField(default=0)
    	plotid   = models.IntegerField(primary_key=True)
    	userpin  = models.IntegerField()
    	
    	def __str__(self):
    	    return self.plotid
    	   
