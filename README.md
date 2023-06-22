# Seaper Server Manager

[![version](https://img.shields.io/github/v/release/Xiaoyi311/Seaper)](https://github.com/Xiaoyi311/Seaper/releases)
[![issues](https://img.shields.io/github/issues/Xiaoyi311/Seaper)](https://github.com/Xiaoyi311/Seaper/issues)
[![download](https://img.shields.io/github/downloads/Xiaoyi311/Seaper/total)](https://github.com/Xiaoyi311/Seaper/releases)
[![license](https://img.shields.io/github/license/Xiaoyi311/Seaper.svg)](LICENSE)
[![skyWorldStudio](https://img.shields.io/badge/Powered%20By-SkyWorldStudio-blue.svg?style=flat-square)](https://skyworldstudio.top)

🌟 Seaper Server Manager - 一个全新的服务管理网页面板 🌟

Seaper Server Manager (简称 Seaper) 是一个**全新的，全能的，简洁的，跨平台的**管理服务器上服务的网页面板，你可以使用 Seaper 轻松管理各种控制台程序。Seaper 采用前后分离架构： Springboot + React 使得在集成且实用的同时不失安全。

## 目录

- [安全问题](#安全问题)
- [项目背景](#项目背景)
- [安装方法](#安装方法)
- [使用方法](#使用方法)
- [API](#api)
- [贡献者](#贡献者)
- [开源协议](#开源协议)

## 安全问题

### 目前没有任何安全问题

## 项目背景

### 为什么要成立这个项目

其实 **Seaper** 的灵感源自 **Minecraft Server Manager (MCSM)** 与 **Pterodactyl (翼龙)**，Seaper 融合了两方各自的**优点与特色**，致力于打造一个**完美的网页面板**

### 名称由来

**Seaper** 其实是 **Sea (海)** 与 **Paper (纸张)** 的结合，暗示了开发初衷是能让我们的程序可以在**任何平台上运行**，同时也**轻便、实用**，就像一张即使是在海上也不会损坏的纸

## 安装方法

**Seaper** 是一个**开源项目**，所以安装方式也分成两种：**_打包安装、编译安装_**

### 打包安装

推荐**普通使用者**用的一种方式，您只需要**下载**我们准备好的**程序压缩包**，**解压运行**就可以了

1. 进入此仓库的 [Release](https://github.com/Xiaoyi311/Seaper/releases)，下载最新版本的压缩包 (不是 Source Code!)
2. 解压下载下来的压缩包，Windows 系统需要打开 ```start.bat```，Linux 系统需要执行 ```start.sh```

### 编译安装

推荐**开发者**用的一种方式，比较麻烦，需要**配置开发环境**

需要的环境：
- Maven 3 —— 后端打包
- npm —— 前端打包

#### 一、准备工作
1. 下载**后端源代码**
2. 下载**前端源代码**
3. 新建**任意文件夹**，用来放置**打包后文件**

#### 二、后端打包
1. 进入**后端源代码**文件夹，运行命令 `maven clean package -Dmaven.test.skip=true` 打包
2. 等待打包完成 (控制台出现 `BUILD SUCCESS` )
3. 进入 `target` 文件夹，找到两个文件/文件夹:
   - `SeaperServerManager-x.x.x.jar` —— 主程序
   - `lib` —— 依赖库
4. 将**找到的文件/文件夹**复制到提前**准备好的文件夹**

#### 三、前端打包
1. 进入**前端源代码**文件夹，运行命令 `npm install` 下载**前端依赖**
2. 等待**下载完成后**再运行命令 `npm run build` 打包**项目**
3. 等待打包完成后进入 `dist` 文件夹，复制其中**所有内容**
4. 进入提前**准备好的文件夹**，新建文件夹 `public` 放入复制的文件

#### 四、文件完善
1. 进入提前**准备好的文件夹**
2. 新建文件夹 `data`，内部再新建文件夹 `langs`
3. 放入**后端源代码**中 `languages` 文件夹中的**所有文件**

#### 五、启动命令
启动命令为：`java -Dloader.path=lib -jar 主程序文件名.jar`

## 使用方法

介于文件限制，详细用法见 [Seaper Wiki]()

## API

所有关于 Seaper 的 API 均可在 [Seaper API](https://docs.skyworldstudio.top/seaper) 查看

## 贡献者

查看**所有贡献者**请见 [贡献者](CONTRIBUTING.md) 文件!

我们欢迎任何**开发者或者用户**来帮助我们完善 Seaper，如果有能力可以发送 **PR**，没有能力也可以提交 **Issues**，**_感谢所有支持 Seaper 的用户_**！

## 鸣谢

- 感谢 **MCSM** 为本项目提供灵感
- 感谢 **Pterodactyl** 为本项目提供灵感

## 开源协议

[Apache License 2.0](LICENSE)