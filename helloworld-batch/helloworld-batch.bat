@rem ##########################################################################
@rem
@rem  helloworld-batch
@rem  Creates and compiles a HelloWorld.java file
@rem
@rem ###################################################################w #######


@echo ##########################################################################
@echo.
@echo  helloworld-batch
@echo  Creates and compiles a Java file
@echo.
@echo ##########################################################################
@echo.
@echo off

@echo Writing Java code...
@rem set main class
set mainClass=HelloWorld
@echo.
@echo --------------------------------------------------------------------------
@rem Java Code
(
    echo import java.lang.Process;
    echo import java.io.IOException;
    echo public class %mainClass% {
    echo    public static void main^(String[] args^) throws IOException {
    echo	    System.out.println^("Hello, World"^);
    echo	    System.out.println^("Press enter to start helloworld-batch"^);
    echo	    System.in.read^(^);
    echo	    Runtime.getRuntime^(^).exec^("cmd /c start helloworld-batch.bat"^);
    echo    }
    echo }
) > "%mainClass%.java"
type %mainClass%.java
@echo --------------------------------------------------------------------------
@echo.
@rem Searching for javac.exe
@rem %%D = current directory
for /f "delims=" %%D in ('dir /a:d "C:\Program Files\Java" /b') do (
    setlocal EnableDelayedExpansion
    set currentDirectory=%%D
    @rem is %%D jdk?
    echo/!currentDirectory!|find "jdk" > nul
    if !errorlevel! == 0 (
        set jdkFound=!currentDirectory!
        goto :break
    )
)
:break

@echo Compiling...
"C:\Program Files\Java\%jdkFound%\bin\javac.exe" "%mainClass%.java"
@echo Done.
@echo Press any key to start %mainClass%
@echo.
pause
start cmd.exe /c ""java.exe" "%mainClass%""
REM "java.exe" "HelloWorld"
exit