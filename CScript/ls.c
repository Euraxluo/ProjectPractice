#include<stdio.h>
#include<stdlib.h>
#include<dirent.h>
int main(int argc,char *argv[]){
	DIR *dir;
	struct dirent *dirent;
    if(argc >2)
        printf("usage: ls path");
    else if(argc == 1){
        char * c = ".";
        dir = opendir(c);
        while((dirent = readdir(dir))!= NULL)
            printf("%s\n",dirent->d_name);
        closedir(dir);
    }else if(argc == 2){
        if((dir = opendir(argv[1]))==NULL)
            printf("can't open %s\n",argv[1]);
        while((dirent = readdir(dir))!= NULL)
            printf(" %s\n",dirent->d_name); 
        closedir(dir);
    }
	exit(0);
}

