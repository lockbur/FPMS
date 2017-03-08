#!/bin/bash
# +=============== Bank of China FPMS project ========================
# | Name:
# |      fpmsstartstop.sh
# | Description:
# |      IBM WEBSPHERE SERVER start or stop.
# | Notes:
# |      需要以root用户运行此脚本.
# +===================================================================
exp_log=/home/fpms/export/fpmsbakfile.log
#exp_log=/fpms/BACKUP/fpmsbakfile.log
export NLS_LANG="SIMPLIFIED CHINESE_CHINA".ZHS16GBK
export ORACLE_HOME=/u01/app/oracle/product/11.2.0/client_1
export PATH=$ORACLE_HOME/bin:$PATH
export ORACLE_SID=FPMS
dbconstr=FPMS/Passabcd1234@22.188.46.239:1521/FPMSDB
sys_day=`date +%Y%m%d`

while true
do
    clear
    echo "\n"
    echo "    +-----------------------------------------------------+"
    echo "    |                 FPMS系统执行菜单                                                                                  |"
    echo "    +-----------------------------------------------------+"
    echo "    |                                                     |"     
    echo "    |              A. 检查DMP备份情况                                                                                      |"    
    echo "    |                                                     |"
    echo "    |              B. 关闭应用系统                                                                                                |"
    echo "    |                                                     |"
    echo "    |              C. 开启应用系统                                                                                               |"
    echo "    |                                                     |"
    echo "    |              Q. 退出                                                                                                                |"
    echo "    |                                                     |"     
    echo "    +-----------------------------------------------------+"
    echo "    |                  欢迎使用FPMS系统                                                                               |"
    echo "    +-----------------------------------------------------+"
    echo "                \033[1m请仔细确认您的选择!\033[0m    选择码: \c"
    read operate
    case $operate in
    
        [Aa]  ) 
        
        	 echo "确认运行【检查RMAN备份状态】? (确认请输入Y )\c"
             read answer
             if [ $answer = "Y" -o $answer = "y" ]
             then
               echo "+----------------------------------------------------------------------+"
               tail -n 1 $exp_log
               echo "+----------------------------------------------------------------------+"
               echo "按回车键继续 \c"
               read tmp               
             fi
             ;;
             
        [Bb]  ) 
        
        	 echo "确认运行【关闭应用系统进程】? (确认请输入Y )\c"
             read answer
             if [ $answer = "Y" -o $answer = "y" ]
             then
               echo "+----------------------------------------------------------------------+"
               sh /was/IBM/WebSphere/AppServer/profiles/AppSrv01/bin/stopServer.sh server1
               echo "+----------------------------------------------------------------------+"
               echo "按回车键继续 \c"
               read tmp               
             fi
             ;;

        [Cc]  ) 
        
        	 echo "确认运行【开启应用系统进程】? (确认请输入Y )\c"
             read answer
             if [ $answer = "Y" -o $answer = "y" ]
             then
               echo "+----------------------------------------------------------------------+"
               sh /was/IBM/WebSphere/AppServer/profiles/AppSrv01/bin/startServer.sh server1
               echo "+----------------------------------------------------------------------+"
               echo "按回车键继续 \c"
               read tmp               
             fi
             ;;

        [Qq] )  reset
             exit ;;
        * )  echo  "请输入正确选择!!!"
             ;;
    esac
done
exit
