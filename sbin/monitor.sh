#!/bin/bash
{
	export MILTRACK=/Users/david/Development/STRATUX/StratuxMilTracker
	export TARGET=${MILTRACK}/target
	export LOGS=${MILTRACK}/logs
	export SBIN=${MILTRACK}/sbin
	export LOGNAME=`date "+%Y.%m.%d.log"`
	export JAR=${TARGET}/StratuxMilTracker-1.0-SNAPSHOT-jar-with-dependencies.jar

	echo Starting Mil Tracking in Monitor/Logging mode, writing to ${LOGNAME}
	nohup java -jar ${JAR}  --host=192.168.1.200 --port=30003 --lat=29.88058 --lon=-98.23455 --range=8 >> ${LOGS}/${LOGNAME} &
	tail -f ${LOGS}/${LOGNAME}
}
