#!/bin/bash							
# -----------------------------------------------------------------------------------------
# ---传入参数以及检查---
if [ $# != 1 ] 
then
 echo "parameter count error."
 exit 1;
fi

expr $1 + 100 &> /dev/null
if [ $? -ne 0 ] 
then
 echo "parameter $1 is not number."
 exit 1;
fi

if [ $1 -lt 0 ] 
then
 echo "parameter $1 must be greater than 0."
 exit 1;
fi

dayago=$1
curdatestr=$(date +%Y%m%d)
dayagodatestr=$(date -d "${dayago} days ago" +%Y%m%d)

dbconstr=FPMS/Passabcd1234@22.188.46.239:1521/FPMSDB
#dbconstr=FPMS/Passabcd1234@21.123.80.162:1521/FPMSDB
expdir=/home/fpms/export

#导出dmp文件
export NLS_LANG="SIMPLIFIED CHINESE_CHINA".ZHS16GBK
echo `date +'%Y-%m-%d %H:%M:%S'` "oracle backup..." | tee -a $expdir/fpmsbakfile.log
exp $dbconstr file=$expdir/fpms_$curdatestr.dmp 1>>$expdir/fpmsbakfile.log 2>>$expdir/fpmsbakfile.log
if [ $? = 0 ]
then
 echo `date +'%Y-%m-%d %H:%M:%S'` "oracle backup successfully." | tee -a $expdir/fpmsbakfile.log
else
 echo `date +'%Y-%m-%d %H:%M:%S'` "error,oracle backup unsuccessfully." | tee -a $expdir/fpmsbakfile.log
 exit 1
fi

#压缩备份文件
echo `date +'%Y-%m-%d %H:%M:%S'` "zip file..." | tee -a $expdir/fpmsbakfile.log
gzip -f $expdir/fpms_$curdatestr.dmp 1>>$expdir/fpmsbakfile.log 2>>$expdir/fpmsbakfile_error.log
if [ $? = 0 ]
then
 echo `date +'%Y-%m-%d %H:%M:%S'` "zip file successfully." | tee -a $expdir/fpmsbakfile.log
else
 echo `date +'%Y-%m-%d %H:%M:%S'` "error,zip file unsuccessfully." | tee -a $expdir/fpmsbakfile.log
 exit 1
fi

#转移备份文件
#echo `date +'%Y-%m-%d %H:%M:%S'` "copy file to file server..." | tee -a $expdir/fpmsbakfile.log

#echo `date +'%Y-%m-%d %H:%M:%S'` "copy file to file server successfully." | tee -a $expdir/fpmsbakfile.log

#删除$dayago前备份文件
echo `date +'%Y-%m-%d %H:%M:%S'` "remove $dayago days ago file..." | tee -a $expdir/fpmsbakfile.log
find $expdir -name "fpms_*.*" -mtime +$dayago -exec rm -rf {} \; 
if [ $? = 0 ]
then
 echo `date +'%Y-%m-%d %H:%M:%S'` "remove $dayago days ago file successsfully." | tee -a $expdir/fpmsbakfile.log
else
 echo `date +'%Y-%m-%d %H:%M:%S'` "error,remove $dayago days ago file unsuccesssfully." | tee-a $expdir/fpmsbakfile.log
 exit 1
fi

echo "$curdatestr dmp backup successsfully."  | tee -a $expdir/fpmsbakfile.log
exit 0

###
