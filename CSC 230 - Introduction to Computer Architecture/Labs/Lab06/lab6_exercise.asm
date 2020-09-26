;
; lab 5
; 

.include "m2560def.inc"

.cseg

	ldi ZH,high(msg<<1)	; initialize index register Z to point to msg in flash memory
	ldi ZL,low(msg<<1) ;why msg<<1

	;write your code here, initialize index register X to point to msg_copy in SRAM

	ldi XH, high(msg_copy) ;Setting the pointer to
	ldi XL, low(msg_copy)

	ldi r16, -1		;initialize counter to -1		

next_char:
	;write your code here
	;write a loop, copy each character from flash memory to at msg_copy in SRAM in reverse order!!
	;get the length of the string, store it at str_len in SRAM
	
	lpm r23, Z+ ;
	inc r16
	adiw X, 1 ;Add word will move x to the 10th position
	tst r23
	brne next_char
	

	sts str_len, r16

	ldi ZH, high(msg<<1)
	ldi ZL, low (msg<<1)
	clr r23
	st X, r23

	ldi r16, -1

next_char_V2:
	lpm r23, Z+
	st X, r23
	sbiw X, 1
	dec r16
	tst r16
	brne next_char_V2
	
done:	jmp done

msg: .db "0123456789", 0 ; c-string format

.dseg
.org 0x200
msg_copy: .byte 14
str_len: .byte 1
