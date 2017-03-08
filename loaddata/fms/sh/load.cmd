@echo off
setlocal
rem ---设置SID

set dbconstr=%1
set ctlpath=%2
set datafilepath=%3
set datafilename=%4
set tradetype=%5
set sqlldr_fullpath=%6

set dfname=%datafilename:.TXT=%

rem ---设置路径
set CtlFile=%ctlpath%\%tradetype%.ctl
set SqlLdrFileLog=%datafilepath%\%dfname%.log
set BadFile=%datafilepath%\%dfname%.bad
set DisCard=%datafilepath%\%dfname%.dsc
set datafile=%datafilepath%\%datafilename%

rem ---清空历史文件---
del /Q %datafilepath%\*%dfname%.log
del /Q %datafilepath%\*%dfname%.bad
del /Q %datafilepath%\*%dfname%.dsc

rem ---导数---
sqlldr %dbconstr% parallel=false errors=200000 control=%CtlFile% rows=10000 bindsize=20971520 readsize=20971520 discard=%DisCard% log=%SqlLdrFileLog% bad=%BadFile% data=%datafile%
del /Q %datafilepath%\%datafilename%

endlocal

exit 0