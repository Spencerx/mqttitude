#!/usr/bin/env python

from peewee import *
from config import Config
import datetime

cf = Config()

mysql_db = MySQLDatabase(cf.get('dbname', 'mqttitude'),
    user=cf.get('dbuser'),
    passwd=cf.get('dbpasswd'))

class MySQLModel(Model):

    class Meta:
        database = mysql_db

class Location(MySQLModel):
    topic           = BlobField(null=False)
    username        = CharField(null=False)
    device          = CharField(null=False)
    lat             = CharField(null=False)
    lon             = CharField(null=False)
    tst             = DateTimeField(default=datetime.datetime.now, index=True)
    acc             = CharField(null=True)
    if cf.get('feature_weather'):
        weather         = CharField(null=True)
        celsius         = CharField(null=True)
        weather_data    = TextField(null=True)
    if cf.get('feature_revgeo'):
        map_data        = TextField(null=True)


if __name__ == '__main__':
    mysql_db.connect()

    try:
        Location.create_table()
    except Exception, e:
        print str(e)

