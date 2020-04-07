#!/bin/bash
# 程序的根目录
basedir=/usr/local/fdr-inspect

PID=$(cat $basedir/fdr.pid)
kill "$PID"
