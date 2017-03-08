#!/bin/sh
#-+=============== Bank of China FPMS project ========================-+
#-| Name:                                                             |
#-|      fpmstrans.sh                                                 |
#-| Descriptions:                                                     |
#-|      统计当日从00:00:00至执行此脚本时间范围内的联机交易量                                                                                         |
#-| Notes:                                                            |
#-|      此脚本主要根据各模块事务表的operate_date或inst_date                      |
#-|      进行数据收集,但系统标准表并未建立相关的index                                | 
#-|      如果今后数据量非常大,建议建立相关的索引以改善性能                                                                                                        |
#-| Change Record:                                                    |
#-| Version    Date       Author    Remarks                           |
#-| =======    ====       ======    =======                           |
#-| 1.0        2016-09-18        Baselined after testing              |
#-+===================================================================+

_RESULTFILE_CNT="fpms_cnt_trans_"`date +%Y%m%d"_"%H%M%S`
_RESULTFILE_PAY="fpms_pay_trans_"`date +%Y%m%d"_"%H%M%S`
_RESULTFILE_ORD="fpms_ord_trans_"`date +%Y%m%d"_"%H%M%S`
_CNT_FILENAME="fpms_cnt_trans"
_PAY_FILENAME="fpms_pay_trans"
_ORD_FILENAME="fpms_ord_trans"


if [ -f ${_RESULTFILE_CNT}.lst ]
then
  rm -f ${_RESULTFILE_CNT}.lst
fi

if [ -f ${_RESULTFILE_PAY}.lst ]
then
  rm -f ${_RESULTFILE_PAY}.lst
fi

if [ -f ${_RESULTFILE_ORD}.lst ]
then
  rm -f ${_RESULTFILE_ORD}.lst
fi

dbconstr=FPMS/Passabcd1234@22.188.46.239:1521/FPMSDB
#dbconstr=FPMS/Passabcd1234@21.123.80.162:1521/FPMSDB
expdir=/home/fpms/trans

export NLS_LANG="SIMPLIFIED CHINESE_CHINA".ZHS16GBK

sqlplus $dbconstr <<EOF
#alter session set nls_date_format = "YYYY-MON-DD HH24:MI:SS";
set linesize 180;
set pagesize 500;
set feedback off;
set trim     off;
set trims    off;

ttitle left '                                            BOC FPMS交易量统计报表(合同管理)'
col 日期            format a17;
col 资产类新增数量        format 999,999,999;
col 资产类新增金额         format 999,999,999;
col 费用类新增数量        format 999,999,999;
col 费用类新增金额        format 999,999,999;
col 合同修改        format 999,999,999;
col 复核通过    format 999,999,999;
col 复核退回    format 999,999,999;
col 确认通过    format 999,999,999;
col 确认退回    format 999,999,999;
col 合同终止        format 999,999,999;
col 合同取消        format 999,999,999;

spool ${_RESULTFILE_CNT};
select to_char(sysdate,'yyyymmdd hh:mi:ss') as 日期,
		 sum(decode(busstype, 'zc_contract_add', busscount, 0)) as 资产类新增数量,
	   sum(decode(busstype, 'zc_contract_amt', busscount, 0)) as 资产类新增金额,
	   sum(decode(busstype, 'fy_contract_add', busscount, 0)) as 费用类新增数量,
	   sum(decode(busstype, 'fy_contract_amt', busscount, 0)) as 费用类新增金额,
       sum(decode(busstype, 'contract_modify', busscount, 0)) as 合同修改,
       sum(decode(busstype, 'contract_checkpass', busscount, 0)) as 复核通过,
       sum(decode(busstype, 'contract_checkback', busscount, 0)) as 复核退回,
       sum(decode(busstype, 'contract_surepass', busscount, 0)) as 确认通过,
       sum(decode(busstype, 'contract_sureback', busscount, 0)) as 确认退回,
       sum(decode(busstype, 'contract_end', busscount, 0)) as 合同终止,
       sum(decode(busstype, 'contract_cancel', busscount, 0)) as 合同取消
  from (select 'zc_contract_add' as busstype, count(1) as busscount
          from tl_water_book a 
          left join td_cnt b on a.bus_num = b.cnt_num
         where a.bus_type = '合同管理'
           and a.operate_type = '新增'
           and b.cnt_type = '0'
           and b.cnt_type is not null
           and operate_date = to_char(sysdate, 'yyyy-mm-dd')
        union
        select 'zc_contract_amt' as busstype, sum(b.cnt_all_amt) as busscount
          from tl_water_book a 
          left join td_cnt b on a.bus_num = b.cnt_num
         where a.bus_type = '合同管理'
           and a.operate_type = '新增'
           and b.cnt_type = '0'
           and b.cnt_type is not null
           and operate_date = to_char(sysdate, 'yyyy-mm-dd')
        union
        select 'fy_contract_add' as busstype, count(1) as busscount
          from tl_water_book a 
          left join td_cnt b on a.bus_num = b.cnt_num
         where a.bus_type = '合同管理'
           and a.operate_type = '新增'
           and b.cnt_type = '1'
           and b.cnt_type is not null
           and operate_date = to_char(sysdate, 'yyyy-mm-dd')
        union
        select 'fy_contract_amt' as busstype, sum(b.cnt_all_amt) as busscount
          from tl_water_book a 
          left join td_cnt b on a.bus_num = b.cnt_num
         where a.bus_type = '合同管理'
           and a.operate_type = '新增'
           and b.cnt_type = '1'
           and b.cnt_type is not null
           and operate_date = to_char(sysdate, 'yyyy-mm-dd')
        union
        select 'contract_modify' as busstype, count(1) as busscount
          from tl_water_book
         where bus_type = '合同管理'
           and operate_type = '修改'
           and operate_date = to_char(sysdate, 'yyyy-mm-dd')
        union
        select 'contract_checkpass' as busstype, count(1) as busscount
          from tl_water_book
         where bus_type = '合同管理'
           and operate_type = '合同复核通过'
           and operate_date = to_char(sysdate, 'yyyy-mm-dd')
        union
        select 'contract_checkback' as busstype, count(1) as busscount
          from tl_water_book
         where bus_type = '合同管理'
           and operate_type = '物料复核退回'
           and operate_date = to_char(sysdate, 'yyyy-mm-dd')
        union
        select 'contract_surepass' as busstype, count(1) as busscount
          from tl_water_book
         where bus_type = '合同管理'
           and operate_type = '合同确认通过'
           and operate_date = to_char(sysdate, 'yyyy-mm-dd')
        union
        select 'contract_sureback' as busstype, count(1) as busscount
          from tl_water_book
         where bus_type = '合同管理'
           and operate_type = '合同确认退回'
           and operate_date = to_char(sysdate, 'yyyy-mm-dd')
        union
        select 'contract_end' as busstype, count(1) as busscount
          from tl_water_book
         where bus_type = '合同管理'
           and operate_type = '合同终止'
           and operate_date = to_char(sysdate, 'yyyy-mm-dd')
        union
        select 'contract_cancel' as busstype, count(1) as busscount
          from tl_water_book
         where bus_type = '合同管理'
           and operate_type = '合同取消'
           and operate_date = to_char(sysdate, 'yyyy-mm-dd')) tmp;
exit;
EOF

_TOTAL_NUMS=`cat ${_RESULTFILE_CNT}.lst | wc -l`
echo ${_TOTAL_NUMS}
_KEEP_NUMS=`expr ${_TOTAL_NUMS} - 90`
echo ${_KEEP_NUMS}
tail -n ${_KEEP_NUMS} ${_RESULTFILE_CNT}.lst >${_RESULTFILE_CNT}.tmp
cat ${_RESULTFILE_CNT}.tmp | sed 's/SQL> exit;//g' | tee $expdir/${_RESULTFILE_CNT}.out
if [ -f $expdir/${_ORD_FILENAME}.out ] 
then
	cat $expdir/${_RESULTFILE_CNT}.out | sed '1,3d;/^$/d' | tee -a $expdir/${_CNT_FILENAME}.out
else
	cat $expdir/${_RESULTFILE_CNT}.out | sed '/^$/d' | tee -a $expdir/${_CNT_FILENAME}.out
fi
rm -r ${_RESULTFILE_CNT}.lst ${_RESULTFILE_CNT}.tmp $expdir/${_RESULTFILE_CNT}.out

sqlplus $dbconstr <<EOF
set linesize 120;
set pagesize 500;
set feedback off;
set trim     off;
set trims    off;
ttitle center 'BOC FPMS交易量统计报表(付款管理)'
col 日期            format a17;
col 付款录入        format 999,999,999;
col 付款提交        format 999,999,999;
col 复核退回        format 999,999,999;
col 复核通过        format 999,999,999;
col 付款扫描        format 999,999,999;
col 财务中心通过    format 999,999,999;
col 财务中心退回    format 999,999,999;
spool ${_RESULTFILE_PAY};
select to_char(sysdate,'yyyymmdd hh:mi:ss') as 日期,
			 sum(decode(paytype, 'payadd', paycount, 0)) as 付款录入,
       sum(decode(paytype, 'pay_submit', paycount, 0)) as 付款提交,
       sum(decode(paytype, 'pay_checkback', paycount, 0)) as 复核退回,
       sum(decode(paytype, 'pay_checkpass', paycount, 0)) as 复核通过,
       sum(decode(paytype, 'pay_scan', paycount, 0)) as 付款扫描,
       sum(decode(paytype, 'pay_finpass', paycount, 0)) as 财务中心通过,
       sum(decode(paytype, 'pay_finback', paycount, 0)) as 财务中心退回
  from (select 'pay_add' as paytype, count(1) as paycount
          from td_pay_audit_log
         where data_flag = 'A0'
           and inst_date = to_char(sysdate, 'yyyy-mm-dd')
        union
        select 'pay_submit' as paytype, count(1) as paycount
          from td_pay_audit_log
         where data_flag = 'B0'
           and inst_date = to_char(sysdate, 'yyyy-mm-dd')
        union
        select 'pay_checkback' as paytype, count(1) as paycount
          from td_pay_audit_log
         where data_flag = 'AB'
           and inst_date = to_char(sysdate, 'yyyy-mm-dd')
        union
        select 'pay_checkpass' as paytype, count(1) as paycount
          from td_pay_audit_log
         where (data_flag = 'C0' or data_flag = 'C1')
           and inst_date = to_char(sysdate, 'yyyy-mm-dd')
        union
        select 'pay_scan' as paytype, count(1) as paycount
          from td_pay_audit_log
         where data_flag = 'D0'
           and inst_date = to_char(sysdate, 'yyyy-mm-dd')
        union
        select 'pay_finpass' as paytype, count(1) as paycount
          from td_pay_audit_log
         where data_flag = 'F0'
           and inst_date = to_char(sysdate, 'yyyy-mm-dd')
        union
        select 'pay_finback' as paytype, count(1) as paycount
          from td_pay_audit_log
         where data_flag = 'CD'
           and inst_date = to_char(sysdate, 'yyyy-mm-dd')) tmp;
exit;
EOF

_TOTAL_NUMS=`cat ${_RESULTFILE_PAY}.lst | wc -l`
echo ${_TOTAL_NUMS}
_KEEP_NUMS=`expr ${_TOTAL_NUMS} - 43`
echo ${_KEEP_NUMS}
tail -n ${_KEEP_NUMS} ${_RESULTFILE_PAY}.lst >${_RESULTFILE_PAY}.tmp
cat ${_RESULTFILE_PAY}.tmp | sed 's/SQL> exit;//g' | tee $expdir/${_RESULTFILE_PAY}.out
if [ -f $expdir/${_ORD_FILENAME}.out ] 
then
	cat $expdir/${_RESULTFILE_PAY}.out | sed '1,3d;/^$/d' | tee -a $expdir/${_PAY_FILENAME}.out
else
	cat $expdir/${_RESULTFILE_PAY}.out | sed '/^$/d' | tee -a $expdir/${_PAY_FILENAME}.out
fi
rm -r ${_RESULTFILE_PAY}.lst ${_RESULTFILE_PAY}.tmp $expdir/${_RESULTFILE_PAY}.out

sqlplus $dbconstr <<EOF
set linesize 120;
set pagesize 500;
set feedback off;
set trim     off;
set trims    off;
ttitle center 'BOC FPMS交易量统计报表(订单管理)'
col 日期                format a17;
col 订单确认通过        format 999,999,999;
col 订单确认退回        format 999,999,999;
spool ${_RESULTFILE_ORD};
select to_char(sysdate,'yyyymmdd hh:mi:ss') as 日期,
			 sum(decode(ordtype, 'ord_pass', ordcount, 0)) as 订单确认通过,
       sum(decode(ordtype, 'ord_back', ordcount, 0)) as 订单确认退回
  from (select 'ord_pass' as ordtype, count(1) as ordcount
          from td_order_audit_log
         where data_flag = '02'
           and inst_date = to_char(sysdate, 'yyyymmdd')
        union
        select 'ord_back' as ordtype, count(1) as ordcount
          from td_order_audit_log
         where data_flag = '03'
           and inst_date = to_char(sysdate, 'yyyymmdd')) tmp;
exit;
EOF

_TOTAL_NUMS=`cat ${_RESULTFILE_ORD}.lst | wc -l`
echo ${_TOTAL_NUMS}
_KEEP_NUMS=`expr ${_TOTAL_NUMS} - 13`
echo ${_KEEP_NUMS}
tail -n ${_KEEP_NUMS} ${_RESULTFILE_ORD}.lst >${_RESULTFILE_ORD}.tmp
cat ${_RESULTFILE_ORD}.tmp | sed 's/SQL> exit;//g' | tee $expdir/${_RESULTFILE_ORD}.out
if [ -f $expdir/${_ORD_FILENAME}.out ] 
then
	cat $expdir/${_RESULTFILE_ORD}.out | sed '1,3d;/^$/d' | tee -a $expdir/${_ORD_FILENAME}.out
else
	cat $expdir/${_RESULTFILE_ORD}.out | sed '/^$/d' | tee -a $expdir/${_ORD_FILENAME}.out
fi
rm -r ${_RESULTFILE_ORD}.lst ${_RESULTFILE_ORD}.tmp $expdir/${_RESULTFILE_ORD}.out

#cat $expdir/${_RESULTFILE_CNT}.out >> $expdir/${_RESULTFILE}.out
#cat $expdir/${_RESULTFILE_PAY}.out >> $expdir/${_RESULTFILE}.out
#cat $expdir/${_RESULTFILE_ORD}.out >> $expdir/${_RESULTFILE}.out
#rm -r $expdir/${_RESULTFILE_CNT}.out $expdir/${_RESULTFILE_PAY}.out $expdir/${_RESULTFILE_ORD}.out
exit 0

