processBar()
{
    ch=('|' '/' '-' '\' '|')
    now=$1
    all=$2
    space=75
    process=`awk BEGIN'{printf "%d" ,('$space'*'$now'/'$all')}'`
    oneH=`awk BEGIN'{printf "%d" ,('$now'/'$all'*100)}'`
    bar="#"
    for((j=1;j<=process;j++))
    do
        bar="#"$bar
    done
    let index=now%5
    printf "[%-${space}s][%s%%][%c]\r" ${bar} ${oneH} ${ch[$index]}
}

#test
all=500
i=0
while [ $i -lt $all ] 
do
    let i++
    processBar $i $all
done
printf "\n"
