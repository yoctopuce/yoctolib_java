echo.
echo Build JAVA API
echo ==============
javac -d Binaries -classpath Binaries -sourcepath yoctolib\src\main\java yoctolib\src\main\java\com\yoctopuce\YoctoAPI\*.java || exit /b 1
cd Binaries
jar -cvf yoctoAPI.jar com\yoctopuce\YoctoAPI\*.class || exit /b 1
IF "%1" == "" goto apidone
rd /s /q com
rd /s /q org
goto end
:apidone
cd ..
echo done
goto end

set failled=
FOR /D %%A IN (Examples\*) DO (call:BuildDir %%A %1)
IF NOT DEFINED failled goto end

echo.
echo COMPILATION HAS FAILLED ON DIRECTORIES :
echo %failled%

goto error
:BuildDir
echo build %~1 %~2
cd %~1
call buildExample.bat %~2
IF ERRORLEVEL 1 set failled=%failled% %~1 -
cd ..\..\
echo done
GOTO:EOF

goto end
:error
echo error
exit /b 1
:end