#!/usr/bin/env python
#_*_coding: utf-8 _*_
#@Time    : 2022/5/26
#@Author  : C

import os

def write_record(new_read_records, log_file, modify_time, read_offset, head_content) :
    read_record = {}
    read_record["modify_time"] = modify_time
    read_record["read_offset"] = read_offset
    read_record["head_content"] = head_content
    new_read_records[log_file] = read_record
