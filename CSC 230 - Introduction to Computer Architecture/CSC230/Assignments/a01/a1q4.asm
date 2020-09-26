; Student: Ryan Woodward
; Student ID: V00857268
;
; CSc 230 Assignment 1 
; Question 4
; Author: Jason Corless
; Modified: Sudhakar Ganti

.include "m2560def.inc"
; This program should calculate the sum of the integers from 1 to (value)
;
; The result should be stored in (result).
;
; Where:
;
;   (value) refers to the byte stored at memory location value in data memory 
;   (result) refers to the byte stored at memory location result in data memory
;
; The sample code you've been given already contains labels named value and result
;
; In the AVR there is no way to automatically initialize data memory
; with constant values.  This is why I have supplied code that initializes data
; memory from program memory.
;

;--*1 Do not change anything between here and the line starting with *--
;
; You don't need to understand this code, we will get to it later
;
; But, if you are keen -- I am using the Z register as a pointer into
; program memory and X as a pointer into data memory
;
.cseg
	ldi ZH,high(init<<1)		; initialize Z to point to init
	ldi ZL,low(init<<1)
	lpm r0,Z				; get the first byte and increment Z
	sts value,r0				; store into A
;*--1 Do not change anything above this line to the --*

;***
; Your code goes here:
;
.def count=r16		; r16 will act as our counter register
.def sum=r18		; This will define register 18 as our sum register

	clr r0			; Simply clearing r0 to prove the program works
	clr sum			; Clear the register sum

	ldi count, 0x01	; Load integer 1 into r16
	
	lds r0, value	; Loads the contents of value into r0

sumCode:

	adc sum, count; ; adds the current value of counter into sum;
	
	sts result, sum	; Stores the current value of sum to result in data memory
	
	cp r0, count	; Will compare r0 (which is value) with the current count, if
					;	they are equal then it will branch to done. Otherwise the
					;	counter will increment by one
	breq done

	inc count		; increments the counter by 1
	rjmp sumCode	; Will jump back and continue to sum integers
		

;****

;--*2 Do not change anything between here and the line starting with *--
done:	jmp done
;*--2

;--*3 Do not change anything between here and the line starting with *--
; This is the constant to initialize value to
; Program memory must be specified in words (2 bytes) which
; is why there is a 2nd byte (0x00) at the end.
init:	.db 0x05, 0x00
;*--3
;--*4 Do not change anything between here and the line starting with *--
; This is in the data segment (ie. SRAM)
; The first real memory location in SRAM starts at location 0x200 on
; the ATMega 2560 processor.  Locations less than 0x200 are special
; and will be discussed much more later
;
.dseg
.org 0x200
value:	.byte 1
result:	.byte 1
;*--4
