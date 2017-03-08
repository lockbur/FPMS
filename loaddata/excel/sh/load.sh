#!/bin/bash							
# -----------------------------------------------------------------------------------------
# ---设置参数---
dbconstr=$1
ctlpath=$2
datafilepath=$3
datafilename=$4
tradetype=$5
taskBatchNo=$6
sqlldr_fullpath=$7

CtlFile=$ctlpath/${tradetype}.ctl
SqlLdrFileLog=$datafilepath/${tradetype}_${taskBatchNo}.log
BadFile=$datafilepath/${tradetype}_${taskBatchNo}.bad
DisCard=$datafilepath/${tradetype}_${taskBatchNo}.dsc
datafile=${datafilepath}/${datafilename}

# ---设置oracle环境变量---
#oracle_path=/oracle/app/oracle/oracle10g/bin
oracle_path=${sqlldr_fullpath}

# ---清空历史文件---
# rm -rf $datafilepath/*.log
# rm -rf $datafilepath/*.bad
# rm -rf $datafilepath/*.dsc

$oracle_path/sqlldr $dbconstr errors=0 control=$CtlFile rows=10000 bindsize=20971520 readsize=20971520 discard=$DisCard log=$SqlLdrFileLog bad=$BadFile data=$datafile
retcode=`echo $?`
#rm -rf $datafile
rm -rf ${datafilepath}/${datafilename}

# --- 判断导数返回码
# For UNIX, the exit codes are as follows:
# EX_SUCC 0
# EX_FAIL 1
# EX_WARN 2
# EX_FTL  3
# so EX_WARN isn't error, just warning
if [ $retcode -ne 0 -a $retcode -ne 2 ]; then
	exit $retcode
fi
exit 0
