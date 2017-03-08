@echo off
setlocal
rem ---设置SID

set dbconstr=%1
set ctlpath=%2
set datafilepath=%3
set datafilename=%4
set tradetype=%5
set taskBatchNo=%6
set sqlldr_fullpath=%7

rem ---设置路径
set CtlFile=%ctlpath%\%tradetype%.ctl
set SqlLdrFileLog=%datafilepath%\%tradetype%_%taskBatchNo%.log
set BadFile=%datafilepath%\%tradetype%_%taskBatchNo%.bad
set DisCard=%datafilepath%\%tradetype%_%taskBatchNo%.dsc
set datafile=%datafilepath%\%datafilename%

rem ---清空历史文件---
rem del /Q %datafilepath%\*%tradetype%.log
rem del /Q %datafilepath%\*%tradetype%.bad
rem del /Q %datafilepath%\*%tradetype%.dsc

rem ---导数---
sqlldr %dbconstr% parallel=false errors=0 control=%CtlFile% rows=10000 bindsize=20971520 readsize=20971520 discard=%DisCard% log=%SqlLdrFileLog% bad=%BadFile% data=%datafile%
del /Q %datafilepath%\%datafilename%

endlocal

exit 0