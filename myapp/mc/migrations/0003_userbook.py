# -*- coding: utf-8 -*-
# Generated by Django 1.11.7 on 2017-12-02 15:09
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('mc', '0002_shawpark'),
    ]

    operations = [
        migrations.CreateModel(
            name='UserBook',
            fields=[
                ('plotid', models.IntegerField(primary_key=True, serialize=False)),
                ('userpin', models.IntegerField()),
            ],
        ),
    ]
