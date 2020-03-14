#!/bin/bash
# 程序的根目录
basedir=/usr/share/fdr

PID=$(cat $basedir/fdr.pid)
kill "$PID"
