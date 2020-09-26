/*
Ryan Woodward
V00857268
CSC 360 - Assigmment 1
Due: Oct 6, 2019
*/

// Include Statements
#include <unistd.h> 	//Symbolic constants
#include <sys/types.h> 	//Primitive system data types
#include <errno.h> 	//Errors
#include <stdio.h>	//Input/output
#include <sys/wait.h>	//Wait for process termination
#include <stdlib.h>	//General Utilities
#include <string.h>	//Needed for strtok
#include <readline/readline.h>
#include <readline/history.h>
#include <limits.h> 	//Needed for printing the current working directory
//Definitions

#define MAX_USER_INPUT 128
#define NO_COMMANDS 1 


//LinkedList to store the processes
//Initial setup of a LinkedList
typedef struct processNode
{
	pid_t pid;
	char* processName;
	int processStatus;
	struct processNode* next;
} processNode;

//Function list
int getCommands(char** commands);
void runCommands(char** commands, int numCommands);
void cd(char** commands);
void bg(char** commands);
void bgList(processNode* head);
void addProcess(pid_t pid, char* name);
void removeProcess(pid_t pid, processNode* head);
processNode* head = NULL;
int pidcheck(pid_t pidCheck, processNode* head);
void processUpdate();
void pstat(pid_t pid);
char* concat(const char *s1, const char *s2);

//Beginning of program
int main()
{
	// Setting up the user prompt 
	while(1)
	{
		processUpdate();	
		char* input[MAX_USER_INPUT];
		int numCommands = getCommands(input);
	
		runCommands(input, numCommands);
		processUpdate();	
	}
}

char* concat (const char *s1, const char *s2)
{
	char *result = malloc (strlen(s1) + strlen(s2) + 1);
	strcpy(result, s1);
	strcat(result, s2);
	return result;
}


//Functions for getting user commands
int getCommands(char** commands)
{
	char* prompt = "SSI: ";
	printf(prompt);

		char* name = getlogin();
	if (!name)
		perror("getlogin() error");
	else
		printf(" %s",name);

	
	char hostname[128];
	gethostname(hostname, sizeof hostname);
	printf("@%s :", hostname);

	//getting current working directory
	
	char cwd[256];
	if (getcwd(cwd, sizeof(cwd)) != NULL)
		prompt  = getcwd(cwd, sizeof(cwd));
	else
	{
		if (getcwd(cwd, sizeof(cwd)) == NULL)
			perror("getcwd() error");
	}

	printf(" %s :",prompt);


	char* input = readline("");
		
	//char* inputLine = (char*)malloc(MAX_INPUT);
	//char* input = readline("PMan: > ");
	int i;
	int numCommands = 0;
	char* parse = strtok(input, " ");
	
	//input = strtok(input, " ");
	for (i = 0; i < MAX_USER_INPUT ; i++)
	{
		numCommands++;
		//commands[i] = malloc(strlen(input) + 1);
		commands[i] = parse;
		//strncpy(commands[i], input, strlen(input));
		parse = strtok(NULL, " ");
	}
	//Puts is a tester to just print the commands to stdout
	//for (i = 0; i < numCommands; i++)
	//{
	//	puts(commands[i]);
	//}
	//Clearing the input buffer
	//int c;
	//while ( (c = getchar()) != '\n' && c != EOF ) { }
	//free(inputLine);
	//
		
	return(numCommands);
}

void runCommands(char** commands, int numCommands)
{
	char* commandList = "";
	
 	//printf("Commands: %s\n", commands[0]);
	//printf("Num of Commands: %d\n", numCommands);

	if (!strcmp(commands[0], "cd"))
	{
		cd(commands);
	}
	else if(!strcmp(commands[0],"bg"))
	{
		bg(commands);
	}
	else if (!strcmp(commands[0],"bglist"))
	{
		bgList(head);
	}
	else
	{
		for (int i = 0; i < numCommands; i++)
		{	
			if (commands[i] == NULL)
				break;	
			commandList = concat(commandList, commands[i]);
			printf("current command list: %s\n", commandList);
			commandList = concat(commandList, " ");
		}
		system(commandList);
	}
}

//Functions for changing the current directory
//
void cd(char** commands)
{
	char cwd[MAX_INPUT];
	getcwd(cwd, sizeof(cwd));
	int i = 0;
	char* dirList = "";
	
	if (commands[1] != NULL)
	{
		if(!strcmp(commands[1], ".."))
		{
			chdir("..");
		}
		else if (!strcmp(commands[1], "~"))
		{	
				
			chdir(getenv("HOME"));
		}
		else
		{
			chdir(commands[1]);
		}
	}
}



// Functions for executing the user commands

/*
bg
- will create a new process and send it to the background

Inputs: Must accept a char** as a parameter and send it to the background
*/
void bg(char** commands)
{
	//printf("comparing the contents of commands[1]\n");
	//if (strcmp(commands[1], "ls") == 0)
	//{
	//	printf("It compares fine\n");	
	//}
	//char* execArgs = commands[1];
	//char* arv_execvp[] = {&commands[1], NULL);

	//char* args[2];
	//args[0] = (char*)commands[1];
	//args[1] = NULL;
	//variable to store the child's pid
	//
	char* cmd = commands[1];
	
	char* commandList = "";
	int i = 2;
	while (commands[i] != NULL)
	{	
		commandList = concat(commandList, commands[i]);
		printf("current command list: %s\n", commandList);
		commandList = concat(commandList, " ");
		i++;
	}
	commands[1] = concat(commands[1], "\0");
	commandList = concat(commandList, "\0");


	pid_t childpid = fork();

	//Fork fails since the child pid is less than 0
	if (childpid < 0)
	{
		printf("Attempted fork failed");
		exit(EXIT_FAILURE);
	} 
	//Fork succeeded, child pid will be 0
	else if (childpid == 0)
	{
		printf("Current Command: %s\n", commands[1]);
		printf("Current Command List: %s\n", commandList);
		execvp(*commands[1], *commandList);
		
		//printf("If reached, means execvp fail\n");
		exit(EXIT_FAILURE);
	
	}
	//Parent process code
	else
	{
		printf("New process has been created with pid: %d\n", childpid);
		addProcess(childpid, commands[1]);
		sleep(1);	
	}
}

/*
bglist
- will display a list of all the commands that are currently running in the background
*/
void bgList(processNode* head)
{
	char cwd[MAX_INPUT];
	getcwd(cwd, sizeof(cwd));
	processNode* current = head;
	int jobs = 0;
	if (head == NULL)
	{
		printf("No current background processes");
		return;
	}
	else
	{
		while (current != NULL)
		{
			if (getcwd(cwd, sizeof(cwd)) != NULL)
			{
				printf("%d:\t", current->pid);
				printf("%s/", cwd);
				printf("%s\n", current->processName);
				jobs++;
				current = current->next;
			}
		}
	}
	printf("Total background jobs: %d\n", jobs);
}


//LinkedList Functions 

void addProcess(pid_t pid, char* name)
{
	processNode* pNode = (processNode*)malloc(sizeof(processNode));
	pNode->pid = pid;
	pNode->processName = name;
	pNode->processStatus = 1;
	pNode->next = NULL;

	//if the list is empty
	if (head == NULL)
	{
		head = pNode;
	}
	//Items exist in the list
	else
	{
		processNode* current = head;
		while(current->next != NULL)
		{
			current = current->next;
		}
		current->next = pNode;
	}
}

void removeProcess(pid_t pid, processNode* head)
{
	//Have to run through the linked list to find the pid
	//Remove that pid from the list, and link to the next node

	//Case 0: pid to be removed doesn't exist
	if(!pidcheck(pid, head))
	{
		printf("Process doesn't exist\n");
		return;
	}
	
	processNode* current = head;
	processNode* prev = head;
	//Case 1: pid to be removed is at the head of the list
	if (head->pid == pid)
	{
		//printf("made it 1");
		//if that is the only element
		if (head->next == NULL)
		{
			//printf("made it 2");
			head = NULL;
			//if (head == NULL)
			//{
			//	printf("head is now null");
			//}
			return;
		}
		//printf("made it 3");
		//if the head needs to be moved over
		head = head->next;
		return;
	}

	//Case 2: pid to be removed is in the middle of the list
	current =  current->next;
	while (current != NULL)
	{
		if (current->pid == pid)
		{
			prev->next = current->next;
			return;
		}
		prev = prev->next;
		current = current->next;
	}
	
}
int pidcheck(pid_t pidCheck, processNode* head)
{
	processNode* current = head;

	while (current != NULL)
	{
		if (current->pid == pidCheck)
		{
			return 1;
		}
		current = current->next;  	
	}
	printf("pid does not exist\n");
	return 0;
}

/*
getProcessNode
This function will search the linked list and change the run status of the matching pid
*/

processNode* getProcessNode(pid_t pidsearch, processNode* head)
{
	processNode* current = head;
	
	while (current != NULL)
	{
		if (current->pid == pidsearch)
		{
			return current;
		}
		else
		{
			current = current->next;
		}
	}
}
/*
processUpdate
-This function will determine whether or not a process has been signalled.
	Based upon that signal it will follow through on the actions that are required
*/
void processUpdate()
{
	pid_t pid;
	int childStatus;
	
	while(1)
	{
		pid = waitpid(-1, &childStatus, WNOHANG | WUNTRACED | WCONTINUED);
	
		if (pid > 0)
		{
			if (WIFSIGNALED(childStatus))
			{
				printf("Process %d has been killed\n", pid);
				//have to remove the process from the linked list
				removeProcess(pid, head);	
			}
			if (WIFSTOPPED(childStatus))
			{
				printf("Process %d has been stopped\n", pid);
				//need to search for the process id and change it's run status
				processNode* current = getProcessNode(pid, head);
				current->processStatus = 0;
			}
			if (WIFCONTINUED(childStatus))
			{
				printf("Process %d has been started again\n", pid);
				processNode* current = getProcessNode(pid, head);
				current->processStatus = 1;
			}	
			if (WIFEXITED(childStatus))
			{
				printf("Process %d has been terminated\n", pid);
				removeProcess(pid, head);
			}
		}
		else
		{
			break;
		}
	}
}

	
	
