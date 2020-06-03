#!/bin/sh
# 程序的根目录
basedir=/usr/local/fdr-all

PID=$(cat $basedir/fdr.pid)
kill "$PID"
