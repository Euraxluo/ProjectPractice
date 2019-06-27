#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <unistd.h>
// #define pid_t pty(int *,cahr *,int, const struct termios *,const struct winsize *);
//int mian(void){
//    pid_t pid;
//    int status;
//    char str;
//    printf("%% ");
//    scanf("%s",&str);
//    if((pid = fork())<0){
//        printf("err：pid是负数");
//    }else if(pid == 0 ){
//        printf("子进程fork a str");
//        exit(127);
//}
// if((pid = waitpid(pid,&status,0))<0){
//   printf("err：进程错误");
// }
//   printf("%% ");
//    exit(0);
//}

// fork_test.c */
#include <sys/types.h>
int main(void)
{
    pid_t pid;
    int status;
    // /*此时仅有一个进程*/
    printf("the func is %d",getpid());
    pid = fork();
    int str;
    // /*此时已经有两个进程在同时运行*/
    scanf("%d", &str);
    printf("a num %d\n", str);
    if (pid < 0)
        printf("error in fork!\n");
    else if (pid == 0)
    {
        printf("I am the child process, my process ID is %d\n", getpid());
        printf("scanf num is %d",str);
//        time.sleep(3);
        exit(127);
    }

    if ((pid = waitpid(pid, &status, 0)) < 0)
    {
        printf("err：进程错误\n");
    }
    else{
        printf("scanf num is %d",str);
printf("I am the parent process,my process ID is %d\n",getpid());
    }
        //printf("I am the parent process, my process ID is %d\n", getpid());
    exit(0);
}
