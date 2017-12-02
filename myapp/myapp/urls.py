from django.conf.urls import include, url

urlpatterns = [
    url(r'^mc/', include('mc.urls')),
]
