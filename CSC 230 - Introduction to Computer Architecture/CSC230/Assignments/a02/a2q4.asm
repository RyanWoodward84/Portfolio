; Ryan Woodward
; V00857268
; Assignment 2, Part 2, Question 4
;
; a2q4.asm
;
; Fix the button subroutine program so that it returns
; a different value for each button
;

;
; Definitions for PORTA and PORTL when using
; STS and LDS instructions (ie. memory mapped I/O)
;
.equ DDRB=0x24
.equ PORTB=0x25
.equ DDRL=0x10A
.equ PORTL=0x10B

;
; Definitions for using the Analog to Digital Conversion
.equ ADCSRA=0x7A
.equ ADMUX=0x7C
.equ ADCL=0x78
.equ ADCH=0x79


		; initialize the Analog to Digital conversion

		ldi r16, 0x87
		sts ADCSRA, r16
		ldi r16, 0x40
		sts ADMUX, r16

		; initialize PORTB and PORTL for ouput
		ldi	r16, 0xFF
		sts DDRB,r16
		sts DDRL,r16

;;;;;:Added section from previous code to initialize certain registers


.def shiftCount = r19
.def revCount = r18
	
		ldi r20, 0x40		; This is from A2Q2 to change the delay timer

;;;;;;
		clr r0
		call display
lp:
		call check_button
		tst r24
		breq lp
		mov	r0, r24

		call display
		ldi r20, 99
		call delay
		ldi r20, 0
		mov r0, r20
		call display
		rjmp lp

;
; An improved version of the button test subroutine
;
; Returns in r24:
;	0 - no button pressed
;	1 - right button pressed
;	2 - up button pressed
;	4 - down button pressed
;	8 - left button pressed
;	16- select button pressed
;
; this function uses registers:
;	r24
;
; if you consider the word:
;	 value = (ADCH << 8) +  ADCL
; then:
;
; value > 0x3E8 - no button pressed
;
; Otherwise:
; value < 0x032 - right button pressed
; value < 0x0C3 - up button pressed
; value < 0x17C - down button pressed
; value < 0x22B - left button pressed
; value < 0x316 - select button pressed
; 
check_button:
		; start a2d
		lds	r16, ADCSRA	
		ori r16, 0x40
		sts	ADCSRA, r16

		; wait for it to complete
wait:	lds r16, ADCSRA
		andi r16, 0x40
		brne wait

		; read the value
		lds r16, ADCL
		lds r17, ADCH

		; put your new logic here:
		clr r24
		clr r25

		;Checking for right and up button
		cpi r17, 0
		breq rightUpDown
		cpi r17, 1
		breq downLeft
		cpi r17, 2
		breq leftSelect
		cpi r17, 3
		breq selectNone
		ret

rightUpDown:
		cpi r16, 0x32
		brlt rightSet
		cpi r16, 0xFA
		brlt upSet
		rjmp downSet	; No need to check here, at this point the only possible
					; button that could have been pressed is the down button
		ret

rightSet:
		ldi r24,0x01
		ldi r25,0x01
		ret

upSet:
		ldi r24,0x02
		ldi r25,0x02
		ret

downLeft:
		cpi r16, 0xC2
		brlt downSet
		rjmp leftSet	;At this point only Left could have been pushed
		

downSet:
		ldi r24, 4
		ldi r25, 0x04
		ret

leftSelect:
		cpi r16, 0x8C
		brlt leftSet
		rjmp selectSet

leftSet:
		ldi r24, 0x08
		ldi r25, 0x08
		ret

selectSet:
		ldi r24, 0x10
		ldi r25, 0x10
		ret

selectNone:
		cpi r16, 0xE8
		brlt selectSet
		ldi r24, 0
		ldi r25, 0
		ret

;
; delay
;
; set r20 before calling this function
; r20 = 0x40 is approximately 1 second delay
;
; this function uses registers:
;
;	r20
;	r21
;	r22
;
delay:	
del1:		nop
		ldi r21,0x10
del2:		nop
		ldi r22, 0x10
del3:		nop
		dec r22
		brne del3
		dec r21
		brne del2
		dec r20
		brne del1	
		ret

;
; display
; 
; display the value in r0 on the 6 bit LED strip
;
; registers used:
;	r0 - value to display
;
display:
		; copy your code from a2q2.asm here
			ldi shiftCount, 0x01
			ldi revCount, 0x80
			ldi r23, 0x00

	loopL:						; This loop will determine leds that need to be turned
			mov r0, r25			; on for port L
			and r0, shiftCount
			brne bitSetL
			cpi shiftCount, 0x10	; Once the bit has been shifted to this postion exit
									; and analyze the other side
			brne shiftNotEqualL
			rjmp portRight

	shiftNotEqualL:					; If the bits are not equal adjust the shift
			lsl shiftCount			; and rev bit counters and re loop
			lsr revCount
			lsr revCount
			cpi shiftCount, 0x10	; exit once the bit counters have exceeded range
			breq portRight
			rjmp loopL

	bitSetL:						; This function will set the bit on portL
									; as well as check the bit counters to see
									; if they are in range. If they aren't it
									; jumps to the port b analysis
			or r23, revCount
			lsr revCount
			lsr revCount
			lsl shiftCount
			cpi shiftCount, 0x10
			breq portRight
			rjmp loopL

	portRight:						; The initial start to analyze port b

			sts PORTL, r23
			ldi r23, 0x00
			ldi shiftCount, 0x10
			ldi revCount, 0x08

	loopB:							; This loop will determine what bits should
									; be set for port B.
			mov r0, r25
			and r0, shiftCount
			brne bitSetB
			cpi shiftCount, 0x80
			brne shiftNotEqualB
			rjmp finalSet

	shiftNotEqualB:					; Will shift the bit counters to appropriate
									; positions if bit hasn't been set
			lsl shiftCount
			lsr revCount
			lsr revCount
			rjmp loopB

	bitSetB:						; Will set a bit and then shift the bit
									; counters to appropriate positions. If they
									; are out of range will jump to end of program.		
			or r23, revCount
			lsr revCount
			lsr revCount
			lsl shiftCount
			cpi shiftCount, 0x40
			breq finalSet
			rjmp loopB	

	finalSet:
		sts PORTB, r23
		ret

