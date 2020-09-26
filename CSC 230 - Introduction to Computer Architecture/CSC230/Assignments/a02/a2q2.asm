; Ryan Woodward
; V00857268
; Assignment 2, Part 2, Question 2
;
; a2q2.asm
;
;
; Turn the code you wrote in a2q1.asm into a subroutine
; and then use that subroutine with the delay subroutine
; to have the LEDs count up in binary.
;
;
; These definitions allow you to communicate with
; PORTB and PORTL using the LDS and STS instructions
;
.equ DDRB=0x24
.equ PORTB=0x25
.equ DDRL=0x10A
.equ PORTL=0x10B


; Your code here
; Be sure that your code is an infite loop


	ldi r16, 0xFF
	sts DDRB, r16		; PORTB all output
	sts DDRL, r16		; PORTL all output

	ldi r16, 0x00		; display the value
	mov r0, r16			; in r0 on the LEDs

.def shiftCount = r17
.def revCount = r18

	ldi r20, 0x10		; This is used to set the delay timer

start:					;;;THE START OF THE PROGRAM;;;
	cpi r16, 0x40		;;This will reset the LED's to 0 if they reach
						;;a max of 3F which is the most we can display
	brne skip
	ldi r16, 0x00	

skip:	
	call display		; Will call the display subroutine
	inc r16				; Will increment the number to be displayed
	call delay			; Will delay the program
	rjmp start			; Will loop back the start of the program
						; 	making it an infinite loop



	done:		jmp done	; if you get here, you're doing it wrong

	;
	; display
	; 
	; display the value in r0 on the 6 bit LED strip
	;
	; registers used:
	;	r0 - value to display
	;
	display:


			ldi shiftCount, 0x01
			ldi revCount, 0x80
			ldi r23, 0x00

	loopL:						; This loop will determine leds that need to be turned
			mov r0, r16			; on for port L
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
			mov r0, r16
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


;
; delay
;
; set r20 before calling this function
; r20 = 0x40 is approximately 1 second delay
;
; registers used:
;	r20
;	r21
;	r22
;
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
