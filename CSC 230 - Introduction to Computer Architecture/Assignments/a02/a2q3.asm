; Ryan Woodward
; V00857268
; Assignment 2, Part 2, Question 3
;
; a2q3.asm
;
; Write a main program that increments a counter when the buttons are pressed
;
; Use the subroutine you wrote in a2q2.asm to solve this problem.
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

; Your code here


	ldi r25, 0x33		; display the value
	mov r0, r25		; in r0 on the LEDs

.def shiftCount = r19
.def revCount = r18

	ldi r20, 0x10		; This is from A2Q2 to change the delay timer

start:					;;;THE START OF THE PROGRAM;;;
	cpi r25, 0x40		;;This will reset the LED's to 0 if they reach
						;;a max of 3F which is the most we can display
	brne skip
	ldi r25, 0x00	

skip:	
	call display		; Will call the display subroutine
	call check_button	; Will set r24 to 1 if pressed, and 0 if not pressed
	call delay			; Will delay the program
	rjmp start			; Will loop back the start of the program
						; 	making it an infinite loop
; make sure your code is an infinite loop



done:		jmp done		; if you get here, you're doing it wrong

;
; the function tests to see if the button
; UP or SELECT has been pressed
;
; on return, r24 is set to be: 0 if not pressed, 1 if pressed
;
; this function uses registers:
;	r16
;	r17
;	r24
;
; This function could be made much better.  Notice that the a2d
; returns a 2 byte value (actually 12 bits).
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
; This function 'cheats' because I observed
; that ADCH is 0 when the right or up button is
; pressed, and non-zero otherwise.
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

		clr r24
		cpi r17, 3
		brsh skip1		
		ldi r24,1
		inc r25			; At this point if the button push is recognized
						; it will increment the value of r25, which is the
						; value that is being displayed on the LEDs
skip1:	ret

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
		ldi r21,0x40
del2:		nop
		ldi r22, 0x40
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
; copy your display subroutine from a2q2.asm here
 
; display the value in r0 on the 6 bit LED strip
;
; registers used:
;	r0 - value to display
;	r17 - value to write to PORTL
;	r18 - value to write to PORTB
;
;   r16 - scratch
display:
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

