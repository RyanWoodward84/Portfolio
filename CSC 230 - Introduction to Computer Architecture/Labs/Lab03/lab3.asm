.cseg
.org 0

;pins.asm
;learn which port is mapped to which pin and how to turn it on/off
.equ PORTL	 = 0x10B
.equ DDRL	 = 0x10A
.equ DDRB	 = 0x24
.equ PORTB	 = 0x25

	;configure PORTL as output by setting DDRL to 1
	ldi r16, 0xff
	sts DDRL, r16
	sts DDRB, r16

	;set LED at pin 48 on
	ldi r19, 0xFF
	sts PORTL, r19
	sts PORTB, r19

done: jmp done
