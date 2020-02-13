#!/bin/bash
# 程序的根目录
basedir=/usr/share/fdr-recorder

PID=$(cat $basedir/fdr-recorder.pid)
kill "$PID"
