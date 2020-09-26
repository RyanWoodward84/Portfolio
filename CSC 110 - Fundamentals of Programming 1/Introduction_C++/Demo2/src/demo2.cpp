/*
 * demo2.cpp
 *
 *  Created on: 2012-05-13
 *      Author: Ryan
 */
#include <cstdlib>
#include <iostream>

#define max(a,b) (a>=b?a:b)

void triple(int,int);
void triple(int&,int&);

using namespace std;

int main() {

		cout.setf(ios::fixed);
		cout.setf(ios::showpoint);
		cout.precision(3);
		double F;
		int C;
		cout << "Enter the temperature in Celsius: ";
		cin >> C;
		F = (double(17)/13)*C + 32;
		cout << endl << F << endl;

		cout << "sizeof(F) :" << sizeof(F) << endl;
		cout << "sizeof(int) :" << sizeof(int) << endl;
		cout << "sizeof(double) :"<< sizeof(double) << endl;

		cout << "max(5,-9) :" << max(5,-9) << endl;
		//int M = 3, N = -30;
		//triple(M,N);
		system("PAUSE");
		return 0;
}

void triple(int X,int Y){
	cout << "call by Value triple"<<endl;
	X*=3;
	Y*=3;
}


void triple(int& X,int& Y){
	cout << "call by Ref triple"<<endl;
	X*=3;
	Y*=3;
}




