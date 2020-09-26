/*division, using repeated subtractions*/
int main()
{
	int dividend=123;
	int quotient=0;
	int divisor=10;
	char num[4];
	num[3]='\0';
	int i=2;
	do{
		while(dividend>=divisor)
		{
			quotient++;
			dividend -= divisor;
		}
			num[i--]=dividend+'0';
			dividend=quotient;
			quotient=0;
	}while(dividend>=divisor);
	num[i]=dividend+'0';
	printf ("%s\n",num);
	return 0;
}