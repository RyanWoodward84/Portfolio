(*  V00857268     *)
(*  CSC 330 - A01 *)
(*  Assignment #1 *)

type DATE = (int * int * int)
exception InvalidParameter

(* This file is where your solutions go *)

(* #1 *)
	      
fun is_older (d1: DATE, d2: DATE): bool =
    if (#1 d1) < (#1 d2)
    then true
    else
	if (#1 d1) > (#1 d2)
	then false
	else
	    if (#2 d1) < (#2 d2)
	    then true
	    else
		if (#2 d1) > (#2 d2)
		then false
		else
		    if (#3 d1) < (#3 d2)
		    then true
		    else false
			     
(* #2 *)
			     
fun number_in_month (dl : DATE list, m : int): int =
    if null dl
    then 0
    else
	if #2 (hd dl) = m
	then 1 + number_in_month(tl dl, m)
	else number_in_month(tl dl, m)
			    
(* #3 *)
			    
fun number_in_months (dl : DATE list, ml : int list): int =
    if null ml
    then 0
    else number_in_month(dl, hd ml) + number_in_months(dl, tl ml)

(* #4 *)

fun dates_in_month (dl : DATE list, m : int) : DATE list =
    if null dl
    then []
    else
	if #2 (hd dl) = m
	then hd dl :: dates_in_month(tl dl, m)
	else dates_in_month(tl dl, m)
			   
(* #5 *)

fun dates_in_months (dl : DATE list, ml : int list) : DATE list =
    if null ml
    then []
    else dates_in_month(dl, hd ml) @ dates_in_months(dl, tl ml)
						    
(* #6 *)

fun get_nth (sl : string list, n : int) : string =
    if null sl
    then raise InvalidParameter
    else
	if n < 1
	then raise InvalidParameter
	else
	    if n = 1
	    then hd sl
	    else get_nth(tl sl, n-1)

(* #7 *)

fun date_to_string (d : DATE) : string =
    let
	val ml = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"]
	val m = #2 (d)
    in
	if m < 1
	then raise InvalidParameter
	else get_nth(ml, m) ^ " " ^ (Int.toString(#3 d)) ^ ", " ^ (Int.toString(#1 d))
    end

(* #8 *)
	
							
fun number_before_reaching_sum (sum : int, il : int list) : int =
    let
	fun counter (c : int, countList: int list) : int =
	    if (c + hd countList) < sum
	    then 1 + counter(c + hd countList, tl countList)
	    else 0
    in
	counter(0, il)
    end
	
(* #9 *)
	
fun what_month (day : int) : int =
    let
	val dayList = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
    in
	1 + number_before_reaching_sum(day, dayList)
    end
	
				     
(* #10 *)

fun month_range (day1: int, day2: int) : int list =
    if day1 > day2
    then []
    else what_month(day1) ::  month_range(day1 + 1, day2)

(* #11 *)
					 
fun oldest(dl : DATE list) = 
    if null dl
    then NONE
    else
	let
	    fun oldNonEmpty(dl : DATE list) =
		if null (tl dl)
		then hd dl
		else
		    let
			val tl_date = oldNonEmpty(tl dl)
		    in
			if is_older(hd dl, tl_date)
			then hd dl
			else tl_date
		    end
	in
	    SOME (oldNonEmpty dl)
	end

(* #12 *)
	    
fun reasonable_date(d : DATE) =
    if #1 d < 1
    then false
    else
	if #2 d < 1 orelse #2 d > 12
	then false
	else
	    if #2 d = 1 orelse #2 d = 3 orelse #2 d = 5 orelse #2 d = 7 orelse #2 d = 8 orelse #2 d = 10 orelse #2 d = 12
	    then
		if #3 d < 1 orelse #3 d > 31
		then false
		else true
	    else
		if #2 d = 4 orelse #2 d = 6 orelse #2 d = 9 orelse #2 d = 11
		then
		    if #3 d < 1 orelse #3 d > 30
		    then false
		    else true
		else
		    if #3 d < 1 orelse #3 d > 29
		    then false
		    else
			if #3 d = 28
			then true
			else
			    if #1 d mod 4 <> 0
			    then false
			    else
				if #1 d mod 100 = 0 andalso #1 d mod 400 = 0
				then true
				else
				    if #1 d mod 100 = 0
				    then false
				    else true
					     
						      
				     
					 
			    
						 
	    
