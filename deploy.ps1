# Ajet Demo 快速部署脚本
# 用于快速构建、安装和启动应用

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Ajet Demo 快速部署工具" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# 检查设备连接
Write-Host "正在检查设备连接..." -ForegroundColor Yellow
$devices = & "G:\AndroidSDK\platform-tools\adb.exe" devices
if ($devices -match "device") {
    Write-Host "✓ 设备已连接" -ForegroundColor Green
} else {
    Write-Host "✗ 未检测到设备，请连接设备后重试" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "步骤 1: 清理并构建项目..." -ForegroundColor Yellow
.\gradlew.bat clean assembleDebug

if ($LASTEXITCODE -ne 0) {
    Write-Host "✗ 构建失败" -ForegroundColor Red
    exit 1
}

Write-Host "✓ 构建成功" -ForegroundColor Green
Write-Host ""

Write-Host "步骤 2: 安装应用到设备..." -ForegroundColor Yellow
& "G:\AndroidSDK\platform-tools\adb.exe" install -r -d app\build\outputs\apk\debug\app-debug.apk

if ($LASTEXITCODE -ne 0) {
    Write-Host "✗ 安装失败" -ForegroundColor Red
    exit 1
}

Write-Host "✓ 安装成功" -ForegroundColor Green
Write-Host ""

Write-Host "步骤 3: 启动应用..." -ForegroundColor Yellow
& "G:\AndroidSDK\platform-tools\adb.exe" shell am force-stop com.ajet.demo
& "G:\AndroidSDK\platform-tools\adb.exe" shell am start -n com.ajet.demo/.MainActivity

Write-Host "✓ 应用已启动" -ForegroundColor Green
Write-Host ""

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  部署完成！" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "提示：" -ForegroundColor Yellow
Write-Host "- 查看日志: adb logcat | Select-String 'ajet.demo'" -ForegroundColor White
Write-Host "- 截图保存: MyDoc\home_screen.png" -ForegroundColor White
Write-Host ""
