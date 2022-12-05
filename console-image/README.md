### Use a terminal ANSI + True color support
See https://gist.github.com/XVilka/8346728

Linux

javac ConsoleImage.java
java ConsoleImage house.png

Windows

PowerShell

[Console]::OutputEncoding = [Text.UTF8Encoding]::UTF8
javac ConsoleImage.java
java ConsoleImage [IMAGE] | Out-Host

Programming language: Java