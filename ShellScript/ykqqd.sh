#!/bin/bash
#换源
euraxluo=$1;
echo -e "\033[30m ---------------------换源--------------------- \033[0m";

echo -e "\033[30m ---------------------1. 备份文件--------------------- \033[0m";
echo "$euraxluo"|sudo mv /etc/apt/sources.list /etc/apt/sources.list.bak;
echo -e "\033[30m ---------------------2. 使用清华源，写入文件--------------------- \033[0m";
echo "$euraxluo"|sudo touch /etc/apt/sources.list;
echo "$euraxluo"|sudo chmod +777 /etc/apt/sources.list;   
echo "$euraxluo"|sudo cat>/etc/apt/sources.list<<EOF
deb http://mirrors.tuna.tsinghua.edu.cn/ubuntu/ xenial main multiverse restricted universe
deb http://mirrors.tuna.tsinghua.edu.cn/ubuntu/ xenial-backports main multiverse restricted universe
deb http://mirrors.tuna.tsinghua.edu.cn/ubuntu/ xenial-proposed main multiverse restricted universe
deb http://mirrors.tuna.tsinghua.edu.cn/ubuntu/ xenial-security main multiverse restricted universe
deb http://mirrors.tuna.tsinghua.edu.cn/ubuntu/ xenial-updates main multiverse restricted universe
deb-src http://mirrors.tuna.tsinghua.edu.cn/ubuntu/ xenial main multiverse restricted universe
deb-src http://mirrors.tuna.tsinghua.edu.cn/ubuntu/ xenial-backports main multiverse restricted universe
deb-src http://mirrors.tuna.tsinghua.edu.cn/ubuntu/ xenial-proposed main multiverse restricted universe
deb-src http://mirrors.tuna.tsinghua.edu.cn/ubuntu/ xenial-security main multiverse restricted universe
deb-src http://mirrors.tuna.tsinghua.edu.cn/ubuntu/ xenial-updates main multiverse restricted universe
EOF

echo -e "\033[30m ---------------------换源结束--------------------- \033[0m";
# 安装ROS
echo -e "\033[30m ---------------------安装ROS--------------------- \033[0m";

## 配置软件仓库
echo -e "\033[30m ---------------------1. 配置软件仓库--------------------- \033[0m";
echo "$euraxluo"|sudo sh -c '. /etc/lsb-release && echo "deb http://mirrors.ustc.edu.cn/ros/ubuntu/ $DISTRIB_CODENAME main" > /etc/apt/sources.list.d/ros-latest.list';
echo "$euraxluo"|sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-key 421C365BD9FF1F717815A3895523BAEEB01FA116;
echo "$euraxluo"|sudo apt-key adv --keyserver keyserver.ubuntu.com --recv-keys F42ED6FBAB17C654

## 设置dpkg
echo -e "\033[30m ---------------------2. 设置dpkg--------------------- \033[0m";
echo -e "\033[30m ---------------------2.1 更名--------------------- \033[0m";
echo "$euraxluo"|sudo mv /var/lib/dpkg/info/ /var/lib/dpkg/info_old/;
echo -e "\033[30m ---------------------2.2 创建新文件--------------------- \033[0m";
echo "$euraxluo"|sudo mkdir /var/lib/dpkg/info/;
echo -e "\033[30m ---------------------2.3 更新源--------------------- \033[0m";
echo "$euraxluo"|sudo apt-get update;
echo "$euraxluo"|sudo apt-get -f install;
echo -e "\033[30m ---------------------2.4 移动新文件--------------------- \033[0m";
echo "$euraxluo"|sudo mv /var/lib/dpkg/info/* /var/lib/dpkg/info_old/;
echo -e "\033[30m ---------------------2.5 删除新文件 --------------------- \033[0m";
echo "$euraxluo"|sudo rm -rf /var/lib/dpkg/info;
echo -e "\033[30m ---------------------2.6 使用旧文件--------------------- \033[0m";
echo "$euraxluo"|sudo mv /var/lib/dpkg/info_old/ /var/lib/dpkg/info/;
echo -e "\033[30m ---------------------2.7 更新--------------------- \033[0m";
echo "$euraxluo"|sudo apt-get upgrade -y;

## 安装ros-k版本
echo -e "\033[30m ---------------------3. 安装ros-k版本--------------------- \033[0m";
echo "$euraxluo"|sudo apt-get -y install ros-kinetic-desktop-full;

## 构建工厂依赖
echo -e "\033[30m ---------------------4. 构建工厂依赖--------------------- \033[0m";
echo "$euraxluo"|sudo apt-get install -y python-rosinstall python-rosinstall-generator python-wstool build-essential;

## 初始化ros
echo -e "\033[30m ---------------------5. 初始化ros--------------------- \033[0m";
echo "$euraxluo"|sudo rosdep init;
echo -e "\033[30m ---------------------6. 解决一个rosdep update的bug--------------------- \033[0m";
echo "$euraxluo"|sudo apt-get update;
echo "$euraxluo"|sudo apt-get install -y python-rosdep;
rosdep update;

## 刷新系统变量
echo -e "\033[30m ---------------------7. 刷新系统变量--------------------- \033[0m";
echo "source /opt/ros/kinetic/setup.bash" >> ~/.bashrc;
source ~/.bashrc;
source /opt/ros/kinetic/setup.bash;


echo -e "\033[30m ---------------------8. 安装完毕;--------------------- \033[0m";
# ros安装完毕

# 配置xming
# export DISPLAY=127.0.0.1:0;
