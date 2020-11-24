#! /bin/bash
#code buy euraxluo
while getopts ivh name #getopts 有三个参数，分别执行安装，查看版本，帮助说明
do 
    case $name in
        i)iopt=1;;
        v)vopt=1;;
        h)hopt=1;;
        *)echo "Invalid arg";;
    esac
done
#
if [[ ! -z $iopt ]] #如果iopt非空则执行
then
{
    wd=$(pwd)
    basename "$(test -L "$0" && readlink "$0" || echo "$0")" > /tmp/scriptname
    #basename命令会删掉所有的前缀，包括最后一个slash（‘/’）字符，然后将字符串显示
    scriptname=$(echo -e -n $wd/ && cat /tmp/scriptname) #scriptname 就是本脚本的地址
    su -c "cp $scriptname /usr/bin/monitor" root && echo "now run monitor Command" || echo "Installation failed"
}
fi
#
if [[ ! -z $vopt ]]
then
{
    echo -e "script version 0.1\nReleased Under MIT Licence"
}
fi
#
if [[ ! -z $hopt ]]
then
{
    echo -e " -i                                            Install script"
    echo -e " -v                                            Print version information and exit"
    echo -e " -h                                            Print help (this information) and exit"
}
fi
#
if [[ $# -eq 0 ]]
then
{
clear #清屏
unset tecrest os architecture kernelrelease internalip externalip nameserver loadeverage
#unset 命令用于删除已定义的shell变量和shell函数
tecreset=$(tput sgr0) #定义变量 tecreset

ping -c 1 www.baidu.com &> /dev/null && echo -e '\E[32m'"Internet : $tecreset Connected" || echo -e '\E[32m'"Internet : $tecreset Connected Disablted"
# \E[32m 将打印的信息变为绿色
os=$(uname -o) #查看系统的类型
echo -e '\E[32m'"OS type : " $tecreset $os

#查看系统的版本和名字
OS=`uname -s`
REV=`uname -r`
MACH=`uname -m`

GetVersionFromFile()
{
    VERSION=`cat $1 | tr "\n" ' ' | sed s/.*VERSION.*=\ //`
}
#
if [ "${os}" = "SunOS" ] ; then
    OS=Solaris
    ARCH=`uname -p`
    OSSTR="${OS} ${REV}(${ARCH} `uname -v`)"
    #uname命令用于打印当前系统的相关信息（内核版本号，硬件架构，主机名和操作系统类型）
elif [ "${OS}" = "AIX" ] ; then
    OSSTR="${OS} `oslevel` (`oslevel -r`)"
    #AIX是IBM开发的linux系统
elif [ "${OS}" = "Linux" ] ; then
    KERNEL=`uname -r`
    if [ -f /etc/redhat-release ] ; then
        DIST='RedHat'
        PSUEDONAME=`cat /etc/redhat-release | sed s/.*\(// | sed s/\)//`
        REV=`cat /etc/redhat-release | sed s/.*release\ // | sed s/\ .*//`
        #sed通常用来怕匹配一个或多个正则表达式的文本进行处理
    elif [ -f /etc/SuSE-release ] ; then
        DIST=`cat /etc/SuSE-release | tr "\n" ' ' | sed s/VERSION.*//`
        REV=`cat /etc/SuSE-release | tr "\n" ' ' | sed s/.*=\ // `
    elif [ -f /etc/mandrake-release ] ; then
        DIST='Mandrake'
        PSUEDONAME=`cat /etc/mandrake-release | sed s/.*\(// | sed s/\)//`
        REV=`cat /etc/mandrake-release | sed s/.*release\ // | sed s/\ .*//`
    elif [ -f /etc/debian_version ] ; then
        DIST="Debian `cat /etc/debian_version`"
        REV=""
    fi
    #
    if  ${OSSTR} [ -f /etc/UnitedLinux-release ] ; then
        DIST="${DIST} [`cat /etc/UnitedLinux-release | tr "\n" ' ' | sed s/VERSION.*//`]"
    fi
    #
    OSSTR="${OS} ${DIST} ${REV} (${PSUEDONAME} ${KERNEL} ${MACH})"

fi
###################################
#监控系统的各种信息，并打印出来
#查看操作系统版本
echo -e '\E[32m'"OS Version :" $tecreset $OSSTR
#查看系统类型
architecture=$(uname -m)
echo -e '\E[32m'"Architecture :" $tecreset $architecture
#查看内核版本
kernelrelease=$(uname -r)
echo -e '\E[32m'"Kernal Release :" $tecreset $kernelrelease
#查看主机名
echo -e '\E[32m'"Hostname :" $tecreset $HOSTNAME
#查看内网地址
internalip=$(hostname -I)
echo -e '\E[32m'"Internal IP :" $tecreset $internalip
#查看外网地址
externalip=$(curl -s ipecho.net/plain;echo)
echo -e '\E[32m'"External IP :" $tecreset $externalip
#查看DNS
nameservers=$(cat /etc/resolv.conf | sed '1 d' | awk '{print $2}' )
echo -e '\E[32m'"DNS :" $tecreset $namesevers
#查看登陆的用户
echo -e '\E[32m'"Logged In users :" $tecreset && `who`
#查看系统内存使用情况
free -h | grep -v + > /tmp/ramcache
echo -e '\E[32m'"Ram Usages :" $tecreset
cat /tmp/ramcache | grep -v "Swap"
echo -e '\E[32m'"Swap Usages :" $tecreset
cat /tmp/ramcache | grep -v "Mem"
#查看磁盘使用情况
df -h | grep 'Filesystem\|/dev/sda*' > /tmp/diskusage
echo -e '\E[32m'"Disk Usages:" $tecreset
cat /tmp/diskusage
#查看系统的负载
loadaverage=$(top -n 1 -b | grep "load average:" | awk '{print $10 $11 $12}')
echo -e '\E[32m'"Load Average:" $tecreset $loadaverage
#查看系统的运行时间
tecputime=$(uptime | awk '{print $3,$4}' | cut -f1 -d,)
echo -e '\E[32m'"System Uptime Days/(HH:MM) :" $tecreset $tecputime
#删除以上的变量，释放资源
unset tecreset os architecture kernelrelease internalip externalip nameserver loadaverage
rm /tmp/ramcache /tmp/diskusage     
}
fi
#
#
shift $(($OPTIND -1)) #shift命令用于对参数的移动（左移）
