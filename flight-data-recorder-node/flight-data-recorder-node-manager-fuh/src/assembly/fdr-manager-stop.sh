#!/bin/bash
# 程序的根目录
basedir=/usr/share/fdr-manager

PID=$(cat $basedir/fdr-manager.pid)
kill "$PID"
