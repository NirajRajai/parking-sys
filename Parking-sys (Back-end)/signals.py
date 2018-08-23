from django.db.models.signals import post_save
from .models import RegisterUser
from django.dispatch import receiver
from rest_framework.authtoken.models import Token

@receiver(post_save, sender=RegisterUser, dispatch_uid="creating_token")
def create_token(sender, instance, created=True, **kwargs):
    if created:
        Token.objects.create(user=instance)


