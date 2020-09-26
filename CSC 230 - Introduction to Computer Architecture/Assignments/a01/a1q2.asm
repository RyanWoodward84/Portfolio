; Student: Ryan Woodward
; Student ID: V00857268
;
;
; CSc 230 Assignment 1 
; Question 2
; Author: Jason Corless
; Modified: Sudhakar Ganti
;
;  
; This program should calculate:
; R0 = R16 + R17
; if the sum of R16 and R17 is > 255 (ie. there was a carry)
; then R1 = 1, otherwise R1 = 0
;

;--*1 Do not change anything between here and the line starting with *--
.cseg
	ldi	r16, 0xF0
	ldi r17, 0x31
;*--1 Do not change anything above this line to the --*

;***
;---------------------------
;Question: What are we trying to do in this program? What is the
;meaning of if sum > 255 then set R1=1?
;Why did we say that if sum > 255 then there was a carry?
;Answer: Due to the fact that each register is only able to store 8 bits, if
;			a number is > 255 we would need an extra bit in order to properly
;			represent that number. In this case the carry flag can be set and
;			then placed R1 to essentially act as a 9th bit to be able to
;			store the number.
;---------------------------
; Your code goes here:
;
;
	adc r16, r17	;This will add r16 and r17 together and set the carry
					;flag if the result is greater than 255

	mov r0, r16		;This moves the result of r16 into r0

	brcs carry		;This will check to see if the carry flag is set and
					;will branch to the carry section if it is
	
	ldi r17, 0x00	;This section will load a 0 into r1 since the carry flag
					;wasn't set
	mov r1, r17	
						
	jmp done		;If the carry flag is not set this will jump to the done
					;section of the code

carry:	ldi r17, 0x01	;This will load a 1 into r17 if the carry flag was set
		mov r1, r17		;This copies the contents of r17 into r1
	
;****
;--*2 Do not change anything between here and the line starting with *--
done:	jmp done
;*--2 Do not change anything above this line to the --*


