cd "C:\Development\GraalVM-Windows-Installer\NetBeans Project\00_Release"
call "D:\Program Files\Microsoft Visual Studio\2022\Community\VC\Auxiliary\Build\vcvars64.bat"

native-image -jar "C:\Development\GraalVM-Windows-Installer\NetBeans Project\00_Release\GraalVMInstaller.jar"