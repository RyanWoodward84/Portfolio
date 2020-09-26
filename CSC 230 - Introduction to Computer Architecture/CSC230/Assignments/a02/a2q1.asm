;Ryan Woodward
;V00857268
;Assignment 2, Part 2, Question 1


; a2q1.asm
;
; Write a program that displays the binary value in r16
; on the LEDs.
;
; See the assignment PDF for details on the pin numbers and ports.
;
;
;
; These definitions allow you to communicate with
; PORTB and PORTL using the LDS and STS instructions
;
.equ DDRB=0x24
.equ PORTB=0x25
.equ DDRL=0x10A
.equ PORTL=0x10B



		ldi r16, 0xFF
		sts DDRB, r16		; PORTB all output
		sts DDRL, r16		; PORTL all output

		ldi r16, 0x33		; display the value
		mov r0, r16			; in r0 on the LEDs

; Your code here



.def shiftCount = r17		; shift counter will keep track of where I am pointing
.def revCount = r18			; rev counter will keep track of how far the bit needs
							; to be shifted into port position
		
		ldi shiftCount, 0x01
		ldi revCount, 0x80
		ldi r20, 0x00

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
		or r20, revCount
		sts PORTL, r20
		lsr revCount
		lsr revCount
		lsl shiftCount
		cpi shiftCount, 0x10
		breq portRight
		rjmp loopL

portRight:						; The initial start to analyze port b

		ldi shiftCount, 0x10
		ldi revCount, 0x08

loopB:							; This loop will determine what bits should
								; be set for port B.
		mov r0, r16
		and r0, shiftCount
		brne bitSetB
		cpi shiftCount, 0x60
		brne shiftNotEqualB
		rjmp done

shiftNotEqualB:					; Will shift the bit counters to appropriate
								; positions if bit hasn't been set
		lsl shiftCount
		lsr revCount
		lsr revCount
		rjmp loopB

bitSetB:						; Will set a bit and then shift the bit
								; counters to appropriate positions. If they
								; are out of range will jump to end of program.		
		or r20, revCount
		sts PORTB, r20
		lsr revCount
		lsr revCount
		lsl shiftCount
		cpi shiftCount, 0x40
		breq done
		rjmp loopB		
;
; Don't change anything below here
;
done:	jmp done
