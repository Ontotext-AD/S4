# Copyright 2014, Ontotext AD

# This file is free software; you can redistribute it and/or modify it under
# the terms of the GNU Lesser General Public License as published by the Free
# Software Foundation; either version 2.1 of the License, or (at your option)
# any later version.
# This library is distributed in the hope that it will be useful, but WITHOUT
# ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
# FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
# details.
# You should have received a copy of the GNU Lesser General Public License
# along with this library; if not, write to the Free Software Foundation, Inc.,
# 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA


# import sys
# import os
import re
import urllib.request
import urllib.error
import urllib.parse
import http.client
import json
import datetime

from .models import *


class ApiClient:

    """Generic API client for Swagger client library builds"""

    def __init__(self, apiKey=None, apiSecret=None, cookie=None):
        if type(apiKey) is None:
            raise Exception('You must pass an apiKey when instantiating the '
                            'APIClient')
        self.apiKey = apiKey
        self.apiSecret = apiSecret
        self.cookie = cookie

    def callAPI(self, resourcePath, method, queryParams, postData,
                headerParams=None):

        """Makes the appropriate request with specific parameters"""

        url = resourcePath
        headers = {}
        if headerParams:
            for param, value in headerParams.items():
                headers[param] = value

        password_mgr = urllib.request.HTTPPasswordMgrWithDefaultRealm()
        password_mgr.add_password(
            None, resourcePath, self.apiKey, self.apiSecret)
        handler = urllib.request.HTTPBasicAuthHandler(password_mgr)
        opener = urllib.request.build_opener(handler)
        urllib.request.install_opener(opener)

        if self.cookie:
            headers['Cookie'] = self.cookie

        data = None

        if queryParams:
            sentQueryParams = {}
            for param, value in queryParams.items():
                if type(value) is not None:
                    sentQueryParams[param] = value
            url = url + '?' + urllib.parse.urlencode(sentQueryParams)

        if method in ['GET']:
            headers = {}
            data = None
            postData = None
            requestParams = MethodRequest(method=method, url=url)
            request = urllib.request.urlopen(requestParams)
            return request.read()

        elif method in ['PATCH', 'POST', 'PUT', 'DELETE']:

            if postData:
                # headers["Content-Type"] = "application/json"
                data = self.sanitizeForSerialization(postData)
                data = json.dumps(data)

        else:
            raise Exception('Method ' + method + ' is not recognized.')

        if data:
            data = data.encode('utf-8')
        requestParams = MethodRequest(method=method, url=url,
                                      headers=headers, data=data)

        # Make the request
        request = urllib.request.urlopen(requestParams)
        encoding = request.headers.get_content_charset()
        if not encoding:
            encoding = 'utf-8'
        response = request.read().decode(encoding)
        # try:
        #     data = json.loads(response)
        # except ValueError:  # PUT requests don't return anything
        #     data = None

        return response

    def toPathValue(self, obj):
        """Convert a string or object to a path-friendly value
        Args:
            obj -- object or string value
        Returns:
            string -- quoted value
        """
        if type(obj) == list:
            return urllib.parse.quote(','.join(obj))
        else:
            return urllib.parse.quote(str(obj))

    def sanitizeForSerialization(self, obj):
        """Dump an object into JSON for POSTing."""

        if type(obj) is None:
            return None
        elif type(obj) in [str, int, float, bool]:
            return obj
        elif type(obj) == list:
            return [self.sanitizeForSerialization(subObj) for subObj in obj]
        elif type(obj) == datetime.datetime:
            return obj.isoformat()
        else:
            if type(obj) == dict:
                objDict = obj
            else:
                objDict = obj.__dict__
            return {key: self.sanitizeForSerialization(val)
                    for (key, val) in objDict.items()
                    if key != 'swaggerTypes'}

    def _iso8601Format(self, timesep, microsecond, offset, zulu):
        """Format for parsing a datetime string with given properties.

        Args:
            timesep -- string separating time from date ('T' or 't')
            microsecond -- microsecond portion of time ('.XXX')
            offset -- time offset (+/-XX:XX) or None
            zulu -- 'Z' or 'z' for UTC, or None for time offset (+/-XX:XX)

        Returns:
            str - format string for datetime.strptime"""

        return '%Y-%m-%d{}%H:%M:%S{}{}'.format(
            timesep,
            '.%f' if microsecond else '',
            zulu or ('%z' if offset else ''))

    # http://xml2rfc.ietf.org/public/rfc/html/rfc3339.html#anchor14
    _iso8601Regex = re.compile(
        r'^\d\d\d\d-\d\d-\d\d([Tt])\d\d:\d\d:\d\d(\.\d+)?(([Zz])|(\+|-)\d\d:?\d\d)?$')

    def _parseDatetime(self, d):
        if d is None:
            return None
        m = ApiClient._iso8601Regex.match(d)
        if not m:
            raise Exception('datetime regex match failed "%s"' % d)
        timesep, microsecond, offset, zulu, plusminus = m.groups()
        format = self._iso8601Format(timesep, microsecond, offset, zulu)
        if offset and not zulu:
            d = d.rsplit(sep=plusminus, maxsplit=1)[
                0] + offset.replace(':', '')
        return datetime.datetime.strptime(d, format)

    def deserialize(self, obj, objClass):
        """Derialize a JSON string into an object.

        Args:
            obj -- string or object to be deserialized
            objClass -- class literal for deserialzied object, or string
                of class name
        Returns:
            object -- deserialized object"""

        # Have to accept objClass as string or actual type. Type could be a
        # native Python type, or one of the model classes.
        if type(objClass) == str:
            if 'list[' in objClass:
                match = re.match('list\[(.*)\]', objClass)
                subClass = match.group(1)
                return [self.deserialize(subObj, subClass) for subObj in obj]

            if (objClass in ['int', 'float', 'dict', 'list',
                             'str', 'bool', 'datetime']):
                objClass = eval(objClass)
            else:  # not a native type, must be model class
                objClass = eval(objClass + '.' + objClass)

        if objClass in [int, float, dict, list, str, bool]:
            return objClass(obj)
        elif objClass == datetime:
            return self._parseDatetime(obj)

        instance = objClass()

        for attr, attrType in instance.swaggerTypes.items():

            if attr in obj:
                value = obj[attr]
                if attrType in ['str', 'int', 'float', 'bool']:
                    attrType = eval(attrType)
                    try:
                        value = attrType(value)
                    except UnicodeEncodeError:
                        value = unicode(value)
                    except TypeError:
                        value = value
                    setattr(instance, attr, value)
                elif (attrType == 'datetime'):
                    setattr(instance, attr, self._parseDatetime(value))
                elif 'list[' in attrType:
                    match = re.match('list\[(.*)\]', attrType)
                    subClass = match.group(1)
                    subValues = []
                    if not value:
                        setattr(instance, attr, None)
                    else:
                        for subValue in value:
                            subValues.append(self.deserialize(subValue,
                                                              subClass))
                    setattr(instance, attr, subValues)
                else:
                    setattr(instance, attr, self.deserialize(value,
                                                             attrType))

        return instance


class MethodRequest(urllib.request.Request):

    def __init__(self, *args, **kwargs):
        """Construct a MethodRequest. Usage is the same as for
        `urllib.Request` except it also takes an optional `method`
        keyword argument. If supplied, `method` will be used instead of
        the default."""

        if 'method' in kwargs:
            self.method = kwargs.pop('method')
        return urllib.request.Request.__init__(self, *args, **kwargs)

    def get_method(self):
        return getattr(self, 'method', urllib.request.Request.get_method(self))
