#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>

void forkexample()
{
	int x = 1;

if (fork() == 0)
{
	printf("c has x = %d\n", ++x);
	if (fork() == 0)
	{
		printf("c2 has x = %d\n", ++x);
	}
	else
		printf("p2 has x = %d\n", --x);
}
else
	printf("p has x = %d\n", --x);
}

int main()
{
	forkexample();
	return 0;
}
