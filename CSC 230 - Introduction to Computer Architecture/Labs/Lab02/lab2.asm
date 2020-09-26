.cseg	;select current segment as code
.org 0	;begin assembling at address 0

;Define symbolic names for resources used
.def  even = r18	;Reg 18 holds odd value
.def  mask = r17 ;Reg 17 holds mask	

	ldi		even, 0x09	;Initialize even to 9
	ldi 	r19, 0x00	;Initialize r19 to 0



	andi even, 0x01
	cpi even, 0x00
	breq lp
	rjmp done
	
	

lp:	

	ldi r19, 0x01

done:

	jmp done
