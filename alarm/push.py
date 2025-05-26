#!/usr/bin/env python
#_*_coding: utf-8 _*_
#@Time    : 2022/5/26
#@Author  : C

import requests
import json
import os
import base64
import time


def dingding_alert(alert_msg):
    url = "https://oapi.dingtalk.com/robot/send?access_token=XXX"
    headers={'Content-Type': 'application/json; charset=utf-8'}
    data = {
		"msgtype":"text",
		"text":{
			"content": alert_msg
		}
    }
    print("dingding alert, msg: " + json.dumps(data))
    r=requests.post(url, headers=headers, data=json.dumps(data))
    return (r.text)

def vm_alert(event, context):
    msg_decode = base64.b64decode(event['data']).decode('utf-8')
    msg = json.loads(msg_decode)
    status = msg['incident']['state']
    summary = msg['incident']['summary']
    started_at = time.ctime(msg['incident']['started_at'])
    ended_at = time.ctime(msg['incident']['ended_at'])
    project_id = msg['incident']['resource']['labels']['project_id']
    alert_msg = "Google Cloud Alarm Details:\n"+"Current State:"+status + "\n" \
    "started_at:" + started_at +"\n" \
    "ended_at:" + ended_at +"\n" \
    "Reason for State Change:" + summary
    print(alert_msg)
    res = dingding_alert(alert_msg)
    print(res)
