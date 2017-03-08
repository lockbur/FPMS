#!/bin/bash							
# -----------------------------------------------------------------------------------------
# ---设置参数---
dbconstr=$1
ctlpath=$2
datafilepath=$3
datafilename=$4
tradetype=$5
sqlldr_fullpath=$6

dfname=${datafilename/.TXT/}

CtlFile=$ctlpath/${tradetype}.ctl
SqlLdrFileLog=$datafilepath/${dfname}.log
BadFile=$datafilepath/${dfname}.bad
DisCard=$datafilepath/${dfname}.dsc
datafile=${datafilepath}/${datafilename}

# ---设置oracle环境变量---
#oracle_path=/oracle/app/oracle/oracle10g/bin
oracle_path=${sqlldr_fullpath}

# ---清空历史文件---
rm -rf $datafilepath/*${dfname}.log
rm -rf $datafilepath/*${dfname}.bad
rm -rf $datafilepath/*${dfname}.dsc

$oracle_path/sqlldr $dbconstr errors=200000 control=$CtlFile rows=10000 bindsize=20971520 readsize=20971520 discard=$DisCard log=$SqlLdrFileLog bad=$BadFile data=$datafile
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
