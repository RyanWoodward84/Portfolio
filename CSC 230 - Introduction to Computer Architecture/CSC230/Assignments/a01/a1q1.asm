; Student: Ryan Woodward
; Student ID: V00857268
;
; CSc 230 Assignment 1 
; Question 1
; Author: Jason Corless
; Modified: Sudhakar Ganti

; This program should calculate:
; R0 = R16 + R17 + R18
;
;
;--*1 Do not change anything between here and the line starting with *--
.cseg
	ldi	r16, 0x30
	ldi r17, 0x31
	ldi r18, 0x32
;*--1 Do not change anything above this line to the --*

;***
;-------------------------
; Question: Why did we use r16 to r18? Can we use r0 to 15?
; Answer: We used r16 to r18 because they are the only registers available
;			for the ldi assembly instruction. Due to the opcode being
;			1110 and having 4 bits, and our K values being 8 bits, that
;			only leaves 4 bits to be able to address a register. In this case
;			we are unable to  use ro to r15 since the ldi command only allows
;			us to access r16-r31
;-------------------------
; Your code goes here:
;
	add r16, r17	;This will add register r16 and r17 and store in r16
	add r16, r18	;This will add register r16 and r18 and store in r18
	mov r0, r16		;This will move the contents of r16 to r0

;****

;--*2 Do not change anything between here and the line starting with *--
done:	jmp done
;*--2 Do not change anything above this line to the --*


