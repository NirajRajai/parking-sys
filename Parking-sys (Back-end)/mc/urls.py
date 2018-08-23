from django.conf.urls import url
from . import views

app_name = 'mc'

urlpatterns = [
    url(r'^$', views.u_index, name='index'),
   # url(r'^register/(?P<uname>[a-z]+)/$', views.uregister, name='register'),
  #  url(r'^login/(?P<uname>[a-z]+)/$', views.ulogin, name='login'),
  #  url(r'^login/(?P<uname>[a-z]+)/(?P<lgvalue>[0-9]+)/(?P<ltvalue>[0-9]+)/$', views.upark, name='park'),
    url(r'^(?P<uname>[a-z]+)/find/$', views.upark, name='find'),
    url(r'^(?P<uname>[a-z]+)/book/$', views.ubook, name='book'),
    url(r'^check/$', views.check, name='check'), 
]
