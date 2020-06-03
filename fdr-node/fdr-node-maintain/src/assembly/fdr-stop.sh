#!/bin/sh
# 程序的根目录
basedir=/usr/local/fdr-maintain

PID=$(cat $basedir/fdr.pid)
kill "$PID"
