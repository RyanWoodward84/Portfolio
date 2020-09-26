/*
 * Lab 8
 */

#include <string.h>
#include <stdlib.h>
#include <stdio.h>

#include "main.h"
#include "lcd_drv.h"

// These are included by the LCD driver code, so 
// we don't need to include them here.
// #include <avr/io.h>
// #include <util/delay.h>

int main( void )
{

	lcd_init();

	lcd_xy( 0, 0 );
	/* lcd_puts takes a pointer to a null terminated
	 * string and displays it at the current cursor position.
	 *
	 * In this call, I'm using a constant string
	 */
	lcd_puts("Welcome to CSc 230");

	lcd_xy( 0, 1 );

	/* This function will delay for the number of milliseconds */
	_delay_ms(500);

	/*
	 * Here using a buffer.  Note that this isn't the normal
	 * way to initialize a C-string, but I wanted to illustrate
	 * how they are created.
	 */

	char msg[10];
	msg[0] = 'H';
	msg[1] = 'i';
	msg[2] = 0;

	lcd_xy( 0, 0 );
	lcd_blank(16);

	char name[10];
	name[0] = 'R';
	name[1] = 'y';
	name[2] = 'a';
	name[3] = 'n';
	name[4] = 0;

 	lcd_puts(msg);
	lcd_xy( 0, 1 );
	lcd_blank(10);
	
	for (int i = 0; i < 100; i++)
	{
		lcd_xy( 0, 1 );
		lcd_puts(name);
		_delay_ms(500);
		lcd_xy(0,1);
		lcd_blank(10);
		_delay_ms(500);
		lcd_xy( 0, 0 );
		lcd_puts(name);
		_delay_ms(500);
		lcd_xy(0,0);
		lcd_blank(10);
		_delay_ms(500);

	}
}
