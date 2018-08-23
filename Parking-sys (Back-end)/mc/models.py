# -*- coding: utf-8 -*-
from __future__ import unicode_literals
from django.db import models

class RegisterUser(models.Model):
    username = models.CharField(max_length=10, primary_key=True)
    
    def __str__(self):
        return self.username
        
class ShawPark(models.Model):
    parkid   = models.IntegerField(primary_key=True)
    lon     = models.FloatField(default=73.7236456)
    let      = models.FloatField(default=24.5968106)
    tot      = models.IntegerField(default=8)
    avail    = models.IntegerField(default=4)
    city     = models.CharField(max_length=10)
    address  = models.CharField(max_length=100)
  
    
    def __str__(self):
        return self.city
        
class UserBook(models.Model):
    givenplot= models.BooleanField(default=False)
    plotid   = models.IntegerField(primary_key=True)
    userpin  = models.IntegerField(default=0)
    
    def __str__(self):
        return self.plotid
