#include <stdio.h>
#include <unistd.h>

int main()
{
	pid_t p = fork();
	if (p == 0)
		printf("ppid when p is zero: %d\n", getppid());
	if (p > 0)
		printf("ppid when p above zero: %d\n", getppid());
	printf("%d %d\n", getpid(), p);
	return 0;
}
