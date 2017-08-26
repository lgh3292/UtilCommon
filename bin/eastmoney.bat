
::install
set SOURCE_DIR=C:\projects\myproject\J2SE\UtilCommon
SET TO_DIR="C:\Documents and Settings\liuguohu\Desktop\em"


cd %SOURCE_DIR%\bin


::execute two txt file to run package the em.jar and emui.jar file

echo Main-Class: com.lgh.eastmoney.ui.StockWorker>eastmoney.txt
echo Class-Path: lib\eastmoney.jar lib\util.jar lib\log4j-1.2.9.jar lib\commons-codec-1.3.jar lib\commons-httpclient-3.1-beta1.jar lib\commons-logging-1.1.jar lib\ojdbc14.jar>>eastmoney.txt
echo Main-Class: com.lgh.eastmoney.ui.EastMoneyUI>eastmoney_ui.txt
echo Class-Path: lib\eastmoney.jar lib\util.jar lib\log4j-1.2.9.jar lib\commons-codec-1.3.jar lib\commons-httpclient-3.1-beta1.jar lib\commons-logging-1.1.jar lib\ojdbc14.jar>>eastmoney_ui.txt

jar cvf util.jar com/lgh/util
jar cvf eastmoney.jar com/lgh/eastmoney/bfo com/lgh/eastmoney/bo
jar cvfm em.jar eastmoney.txt com/lgh/eastmoney/ui com/lgh/eastmoney/ctl log4j.properties
jar cvfm emui.jar eastmoney_ui.txt com/lgh/eastmoney/ui com/lgh/eastmoney/ctl log4j.properties
del eastmoney.txt
del eastmoney_ui.txt

::copy the em jar file and the config,source file
if not exist %TO_DIR% mkdir %TO_DIR%
if not exist %TO_DIR%\source mkdir %TO_DIR%\source
if not exist %TO_DIR%\conf mkdir %TO_DIR%\conf
move em.jar %TO_DIR%
move emui.jar %TO_DIR%
del /q/s %TO_DIR%\conf
del /q/s %TO_DIR%\source
copy %SOURCE_DIR%\conf %TO_DIR%\conf
copy %SOURCE_DIR%\source %TO_DIR%\source


::copy the lib jar file
if not exist %TO_DIR%\lib mkdir %TO_DIR%\lib
move util.jar %TO_DIR%\lib
move eastmoney.jar %TO_DIR%\lib
copy %SOURCE_DIR%\lib\httpclient %TO_DIR%\lib
copy %SOURCE_DIR%\lib\oracle %TO_DIR%\lib
copy %SOURCE_DIR%\lib\log4j %TO_DIR%\lib

::create two bat file to run the jar file
echo java -jar em.jar>%TO_DIR%\em.bat
echo java -jar emui.jar>%TO_DIR%\emui.bat
