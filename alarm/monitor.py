#!/usr/bin/env python
#_*_coding: utf-8 _*_
#@Time    : 2022/5/26
#@Author  : C

import json
import os
import push
import utils


cur_dir = os.path.split(os.path.realpath(__file__))[0]
print("cur dir: " + cur_dir)

record_file = cur_dir + "/monitor_record.txt"

log_files = [
    # "D:/data/logs/test_project/test_project.log",
    "D:/data/logs/test_project/exception.log"
]

# tool 目录, tool_path 末尾不要加 "/", 目录结构 ./1/a/log/*.log
tool_path = "D:/data/logs"
if os.path.exists(tool_path) :
    for root, dirs, files in os.walk(tool_path):
        for dir in dirs:
            for subroot, subdirs, subfiles in os.walk(root + "/" + dir):
                for subdir in subdirs:
                    tool_log_file = subroot + "/" + subdir + "/log/exception.log"
                    if os.path.exists(tool_log_file) :
                        print("add log file to log_files, path: " + tool_log_file)
                        log_files.append(tool_log_file)

logs = []
log_len = 100

read_records = {}
if os.path.exists(record_file) :
    with open(record_file, 'r') as f :
        content = f.read()
        read_records = json.loads(content)

print(read_records)

new_read_records = {}
for log_file in log_files:
    if (not os.path.exists(log_file)) :
        print("file " + log_file + " is not exist")
        continue
    (file_path, file_name) = os.path.split(log_file)
    read_record = read_records.get(log_file, {})
    modify_time = read_record.get("modify_time", 0)
    read_offset = read_record.get("read_offset", 0)
    head_content = read_record.get("head_content", "") 

    if (modify_time == os.path.getmtime(log_file)) :
        print("file " + log_file + " modify time is not change")
        utils.write_record(new_read_records, log_file, modify_time, read_offset, head_content)
        continue

    modify_time = os.path.getmtime(log_file)

    with open(log_file, 'r') as f :
        # 判断是否是新文件
        f.seek(0, 0)
        now_head_content = f.read(19)
        if (head_content != now_head_content) :
            head_content = now_head_content
            read_offset = 0

        f.seek(read_offset, 0)
        line = f.readline()
        while line :
            logs.append(line)
            if (len(logs) >= log_len) :
                msg = {"type": "Java Alert", "file": log_file, "content": logs}
                push.dingding_alert(msg)
                logs.clear()
            line = f.readline()
        read_offset = f.tell()
        
    if (len(logs) > 0) :
        msg = {"type": "Java Alert", "file": log_file, "content": logs}
        push.dingding_alert(msg)
        logs.clear()
    
    utils.write_record(new_read_records, log_file, modify_time, read_offset, head_content)

with open(record_file, 'w') as f :
    f.write(json.dumps(new_read_records))
