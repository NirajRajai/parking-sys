from django.shortcuts import render
from django.http import HttpResponse
from django.http import JsonResponse
import json
# Create your views here.
def index(request):
	f=open("/home/no_name/Rajasthan_Hack/matlab/ny.txt")
	c=f.read()
	return JsonResponse({'mystring':"%s"%(c)})
