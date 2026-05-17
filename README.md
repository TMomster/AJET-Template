# AJET

**A Jet Template** - Android Jetpack Compose 项目模板

![Platform](https://img.shields.io/badge/Platform-Android-green.svg)
![Min SDK](https://img.shields.io/badge/Min%20SDK-26-orange.svg)
![Kotlin](https://img.shields.io/badge/Kotlin-2.0.0-blue.svg)
![Compose](https://img.shields.io/badge/Compose-Material3-purple.svg)
![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)

## 项目简介

**AJET**（A Jet Template）是一个 Android 应用基础模板，提供 Material Design 3 主题系统配置。

### 命名由来

- **A** = Android
- **JET** = Jetpack Compose
- **全名**: A Jet Template
- **简称**: AJET

## 技术栈

| 技术 | 版本 |
|------|------|
| Kotlin | 2.0.0 |
| Android Gradle Plugin | 8.2.0 |
| Compile SDK | 34 |
| Min SDK | 26 |
| Target SDK | 34 |
| Jetpack Compose BOM | 2024.06.00 |
| Activity Compose | 1.9.0 |

## 核心依赖

本项目使用以下开源库（均遵循 Apache License 2.0）：

- **Jetpack Compose** - 声明式 UI 框架
- **Material 3** - Material Design 3 组件库和主题系统
- **AndroidX Core KTX** - Kotlin 扩展函数

## 快速开始

### 前置要求

- Android Studio Hedgehog (2023.1.1) 或更高版本
- JDK 17
- Android SDK Platform 34
- Android SDK Build-Tools 34.0.0

### 使用模板创建新项目

1. **克隆仓库**
   ```bash
   git clone https://github.com/TMomster/AJET-Template.git MyNewApp
   cd MyNewApp
   ```

2. **修改项目标识**
   - 在 `build.gradle.kts` 中修改 `namespace` 和 `applicationId`
   - 重命名包名从 `com.ajet.template` 到您的包名
   - 更新 `strings.xml` 中的应用名称

3. **开始开发**
   - 在 `MainActivity.kt` 中编写您的业务逻辑

4. **同步 Gradle**
   - 在 Android Studio 中选择 "Sync Project with Gradle Files"

5. **运行应用**
   - 连接 Android 设备或启动模拟器
   - 点击 "Run" 按钮或使用快捷键 `Shift + F10`

### 构建 APK

```bash
# Debug 版本
./gradlew assembleDebug

# Release 版本
./gradlew assembleRelease
```

生成的 APK 文件位于 `app/build/outputs/apk/` 目录。

## 项目结构

```
AJET/
├── app/
│   ├── src/main/
│   │   ├── java/com/ajet/template/
│   │   │   ├── ui/
│   │   │   │   └── theme/
│   │   │   │       ├── Color.kt
│   │   │   │       ├── Theme.kt
│   │   │   │       └── Type.kt
│   │   │   └── MainActivity.kt
│   │   ├── res/
│   │   │   ├── values/
│   │   │   └── drawable/
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── gradle/
│   └── libs.versions.toml
├── build.gradle.kts
└── settings.gradle.kts
```

### 核心文件说明

- **[Theme.kt](app/src/main/java/com/ajet/template/ui/theme/Theme.kt)**: 主题配置
- **[Color.kt](app/src/main/java/com/ajet/template/ui/theme/Color.kt)**: 颜色定义
- **[Type.kt](app/src/main/java/com/ajet/template/ui/theme/Type.kt)**: 字体排版
- **[MainActivity.kt](app/src/main/java/com/ajet/template/MainActivity.kt)**: 主活动

## 许可证

本项目采用 **Apache License 2.0** 许可证。

Copyright 2026 MomsterTech

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

详见 [LICENSE](LICENSE) 文件。

---

**Build ID**: U7IN-DLA2-FNL3  
**Developed by**: Momster  
**Made with Jetpack Compose**
