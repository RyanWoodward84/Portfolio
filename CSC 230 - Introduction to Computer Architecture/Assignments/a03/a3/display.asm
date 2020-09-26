;Ryan Woodward
;V00857268
;Assignment 3: Part 2
;display.asm
;CSC 230
;Mar 14, 2016

#define LCD_LIBONLY
.include "lcd.asm"

.cseg
	ldi r20, 0x10		; This is used to set the delay timer
	ldi r25, 0x00			; This register will be used as the program counter
	ldi r26, 0x00			; This will be used as the counter for the stars
	call lcd_init			; call lcd_init to Initialize the LCD

Start:

	call lcd_clr			; need to clear the lcd
	call init_strings		; Copies the strings from program to data

;;Initial section in order to display the first 2 lines in full
	call display_line1
	call display_line2
	call delay
	call lcd_clr

;;Switching the init_strings to change the lines
	call init_strings1
	call display_line1
	call delay
	call display_line2

;; Updating the position of the name on second line
	call delay
	call lcd_clr
	call display_line1
	call display_line2_inc

	call delay
	call lcd_clr
	call display_line1
	call display_line2_inc2

;;Clearing the display and placing "**'s" in the centre
	
	call init_strings2

Stars:
	call delay
	call lcd_clr
	call delay
	call display_line1_stars
	call display_line2_stars
	call delay
	call lcd_clr
	call delay
	call display_line1_stars
	call display_line2_stars
	inc r25
	cpi r25, 3
	brne Start

lp:	jmp lp


init_strings:
	push r16
	; copy strings from program memory to data memory
	ldi r16, high(line1)		; this the destination
	push r16
	ldi r16, low(line1)
	push r16
	ldi r16, high(msg1_p << 1) ; this is the source
	push r16
	ldi r16, low(msg1_p << 1)
	push r16
	call str_init			; copy from program to data
	pop r16					; remove the parameters from the stack
	pop r16
	pop r16
	pop r16

	ldi r16, high(line2)
	push r16
	ldi r16, low(line2)
	push r16
	ldi r16, high(msg2_p << 1)
	push r16
	ldi r16, low(msg2_p << 1)
	push r16
	call str_init
	pop r16
	pop r16
	pop r16
	pop r16

	pop r16
	ret

init_strings1:
	push r16
	; copy strings from program memory to data memory
	ldi r16, high(line1)		; this the destination
	push r16
	ldi r16, low(line1)
	push r16
	ldi r16, high(msg2_p << 1) ; this is the source
	push r16
	ldi r16, low(msg2_p << 1)
	push r16
	call str_init			; copy from program to data
	pop r16					; remove the parameters from the stack
	pop r16
	pop r16
	pop r16

	ldi r16, high(line2)
	push r16
	ldi r16, low(line2)
	push r16
	ldi r16, high(msg1_p << 1)
	push r16
	ldi r16, low(msg1_p << 1)
	push r16
	call str_init
	pop r16
	pop r16
	pop r16
	pop r16

	pop r16
	ret


display_line1:
	push r16

	ldi r16, 0x00
	push r16
	ldi r16, 0x00
	push r16
	call lcd_gotoxy
	pop r16
	pop r16

	; Starting the display for the first line

	ldi r16, high(line1)
	push r16
	ldi r16, low(line1)
	push r16
	call lcd_puts
	pop r16
	pop r16

	pop r16
	ret

display_line2:
	push r16

	ldi r16, 0x01
	push r16
	ldi r16, 0x00
	push r16
	call lcd_gotoxy
	pop r16
	pop r16

	; Displaying a string on line 2

	ldi r16, high(line2)
	push r16
	ldi r16, low (line2)
	push r16
	call lcd_puts
	pop r16
	pop r16

	pop r16
	ret

display_line2_inc:
	push r16

	ldi r16, 0x01
	push r16
	ldi r16, 0x01
	push r16
	call lcd_gotoxy
	pop r16
	pop r16

	; Displaying a string on line 2

	ldi r16, high(line2)
	push r16
	ldi r16, low (line2)
	push r16
	call lcd_puts
	pop r16
	pop r16

	pop r16
	ret

display_line2_inc2:
	push r16

	ldi r16, 0x01
	push r16
	ldi r16, 0x02
	push r16
	call lcd_gotoxy
	pop r16
	pop r16

	; Displaying a string on line 2

	ldi r16, high(line2)
	push r16
	ldi r16, low (line2)
	push r16
	call lcd_puts
	pop r16
	pop r16

	pop r16
	ret


init_strings2:
	push r16
	; copy strings from program memory to data memory
	ldi r16, high(line1)		; this the destination
	push r16
	ldi r16, low(line1)
	push r16
	ldi r16, high(msg3_p << 1) ; this is the source
	push r16
	ldi r16, low(msg3_p << 1)
	push r16
	call str_init			; copy from program to data
	pop r16					; remove the parameters from the stack
	pop r16
	pop r16
	pop r16

	ldi r16, high(line2)
	push r16
	ldi r16, low(line2)
	push r16
	ldi r16, high(msg3_p << 1)
	push r16
	ldi r16, low(msg3_p << 1)
	push r16
	call str_init
	pop r16
	pop r16
	pop r16
	pop r16

	pop r16
	ret

display_line1_stars:
	push r16

	ldi r16, 0x00
	push r16
	ldi r16, 0x07
	push r16
	call lcd_gotoxy
	pop r16
	pop r16

	; Starting the display for the first line

	ldi r16, high(line1)
	push r16
	ldi r16, low(line1)
	push r16
	call lcd_puts
	pop r16
	pop r16

	pop r16
	ret

display_line2_stars:
	push r16

	ldi r16, 0x01
	push r16
	ldi r16, 0x07
	push r16
	call lcd_gotoxy
	pop r16
	pop r16

	; Displaying a string on line 2

	ldi r16, high(line2)
	push r16
	ldi r16, low (line2)
	push r16
	call lcd_puts
	pop r16
	pop r16

	pop r16
	ret
delay:
		del1:	nop
		ldi r21,0xFF
del2:	nop
		ldi r22, 0xFF
del3:	nop
		dec r22
		brne del3
		dec r21
		brne del2
		dec r20
		brne del1	
		ret




msg1_p:	.db "Ryan", 0	
msg2_p: .db "CSC 230: Spring 2016", 0
msg3_p:	.db "**", 0

.dseg
;
; The program copies the strings from program memory
; into data memory.  These are the strings
; that are actually displayed on the lcd
;
msg1:	.byte 200
msg2:	.byte 200

line1: .byte 17
line2: .byte 17

