for %%i in (*.proto) do (
    .\protoc.exe --java_out=..\..\java\ %%i
)
pause