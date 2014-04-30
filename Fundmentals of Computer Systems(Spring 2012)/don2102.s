#############################################################################
## 
##       		     HOMEWORK 4
##
## - Fill in the five functions described below.
## - Modify only the function bodies.  Do not modify any of the function
##   names or other parts of this file.
## - All of your implementations should adhere to MIPS calling conventions.
## - When working correctly, your file should print out the following (one
##   line of output for each question):	
##
##	$ spim -file mak2191.s
##      SPIM Version 6.5 of January 4, 2003
##      Copyright 1990-2003 by James R. Larus (larus@cs.wisc.edu).
##      All Rights Reserved.
##      See the file README for a full copyright notice.
##      Loaded: /opt/spim-6.5/share/spim-6.5/trap.handler
##      Problem 1: 18 750 30
##      Problem 2: 1000 1000 10
##      Problem 3: 3 -1 -1 0
##      Problem 4: 1 0 1
##      Problem 5: 0 4 2 2
##	
##                Administrative Instructions
##	
## - Use Spim to develop and test your code.  Spim is available:
##	- On cunix.cc.columbia.edu (log in using your UNI)
##	- For download, from http://spimsimulator.sourceforge.net/
## - Documentation can be found here:
##         http://spimsimulator.sourceforge.net/further.html
## - We will grade on CUNIX, so if you use your own computer it would
##   be wise to run your code there before turning in.
## - To turn in your code
##      1. Rename this file from UNI.s to your UNI (e.g. "mv UNI.s mak2191.s")
##	2. Upload your file to Courseworks > Class Files > Shared Files > HW4 Turnin
##
##   NB: The entire file is a single namespace, meaning your label names
##   need to be globally unique.
##	
##################################################################### 

	      .data
newline:      .asciiz "\n"
problem:      .asciiz "Problem"
somechars:    .asciiz "ABC?.3"
hello:	      .asciiz "hello"
world:	      .asciiz "world"
help:	      .asciiz "help"
vec1:	      .word 40, 89, 100, 0
vec2:	      .word 40, 31, 35, 47, 200, 43, 37, 31, 90, 62
vec3:	      .word -5, 0
vec4:	      .word 0
	      
	      .text
	      .globl main
	      
##################################################################### 
##    		 PROBLEM 1: weighted_avg
##
## Compute a weighted average of three 32-bit numbers, A, B, and C
## 	return (.25A + .5B + .25C)
## A,B, and C are in $a0, $a1, and $a2 respectively.  The result
## should be returned in $v0.
##	
## HINT: Do not use mult/div or floating point values.  To keep
## precision for as long as possible, minimize the number of
## division operations.	
##################################################################### 
				    
weighted_avg:
              li    $t0, 25  
              li    $t1, 50  
              li    $t2, 25  
              mult  $a0, $t0
              mflo  $t4
              mult  $a1,$t1
              mflo  $t5
              mult  $a2,$t2
              mflo  $t6
	          addu  $t7,$t4,$t5
              addu  $v0,$t7,$t6
	          li    $t0, 100
	          divu  $v0,$t0
	          mflo  $v0
	          jr    $ra
	      
##################################################################### 
##    		  PROBLEM 2: max4
##
## Return the maximum, assuming unsigned integers, of $a0, $a1, $a2,
## and $a3 in $v0.
##################################################################### 	
	      
max4:
		  move    $t1, $a0
          sgt     $t0, $a0, $a1
		  beq     $t0, $zero, .L1
		  sgt     $t0, $a0, $a2
		  beq     $t0, $zero, .L2
		  sgt     $t0, $a0, $a3
		  beq     $t0, $zero, .L3
		  move    $v0, $a0
		  b     .End
.L1:
		  move    $t1, $a1
          sgt     $t0, $a1, $a2
		  beq     $t0, $zero, .L2
		  sgt     $t0, $a0, $a3
		  beq     $t0, $zero, .L3
		  move    $v0, $a1
		  b      .End
.L2:
		  move    $t1, $a2
          sgt     $t0, $a2, $a1
		  beq     $t0, $zero, .L1
		  sgt     $t0, $a2, $a3
		  beq     $t0, $zero, .L3
		  move    $v0, $a2
		  b     .End
.L3:
		  move    $t1, $a3
          sgt     $t0, $a3, $a1
		  beq     $t0, $zero, .L1
		  sgt     $t0, $a3, $a2
		  beq     $t0, $zero, .L2
		  move    $v0, $a3
		  b     .End
.End:
	      jr    $ra

	
##################################################################### 
##    		PROBLEM 3: length
##
## Given a zero-terminated list of 4 byte integers ranging from 1-100
## (inclusive), count the number of elements in the list.
##  - The terminating zero should not be included in the count.
##  - If a value outside of legal range (1 >= x <= 100) is found
##    indicate an error by returning -1.
##  - Function takes one argument, a pointer to the start of the list
##################################################################### 
	
length:
		li    $t4, 100
		move  $t1,$a0
		move  $a1, $0 # i = 0

BODY:
		sll   $t3, $a1, 2 # i * 4
		addu  $t3, $t1, $t3 # &a[i]
		lw    $t3, 0($t3) # fetch a[i]
		blt  $t3, $0, .End2
		bgt  $t3, $t4, .End2
		beq  $t3, $0, .End1
		addiu $a1, $a1, 1
		b     BODY
.End1:
        move  $v0, $a1		  	
	    jr    $ra
.End2:
		li  $v0, -1
		jr    $ra

#####################################################################
##    		    PROBLEM 4: diffchar
##	
##  Given two ASCII characters in $a0 and $a1, return
##     - 1 if they are different
##     - 0 if they are the same
#####################################################################

diffchar:     
          seq   $t0,$a0,$a1
          beqz  $t0, noMatch
	      li    $v0, 0
	      jr    $ra
noMatch:
		  li    $v0, 1
	      jr    $ra 

#####################################################################
##	        PROBLEM 5: hamming
##
##  Compute the hamming distance (i.e., number of different characters)
##  between two strings.
##  - The two arguments are pointers to two null-terminated strings
##    of characters.
##  - If one string is shorter than the other, the mismatches all
##    count as 1, e.g., hello
##                      help
##                      00011 = hamming distance of 2        
##  - You should use your diffchar function from problem 4.
#####################################################################
	
hamming:  
		add   $t1,$zero,$a0
		add   $t2,$zero,$a1
		move    $a2,$0
Loop:
		
		lb    $t3($t1)  #load a byte from each string
		lb    $t4($t2)
		sne   $t5, $t3,$0
		beq   $t5, $t4, Ending
		bne   $t3, $t4, NoMatch
		addi  $t1,$t1,1  #t1 points to the next byte of str1
		addi  $t2,$t2,1
		b     Loop
NoMatch:
		addi  $t1,$t1,1  #t1 points to the next byte of str1
		addi  $t2,$t2,1
		addi  $a2,$a2,1
		b     Loop
Ending:
	    move    $v0, $a2
	    jr    $ra

####################################################################
##            DO NOT MODIFY BELOW THIS LINE                       ##
####################################################################

main:
########################################
##         TEST PROBLEM 1             ##
########################################

	      li    $a0, 1
	      jal   print_problem_header

	      li    $a0, 42
	      li    $a1, 10
	      li    $a2, 10
	      jal   weighted_avg              # weighted_avg = 18
	      move  $a0, $v0
	      jal   print_int
	      jal   print_space

	      li    $a0, 500
	      li    $a1, 1000
	      li    $a2, 500
	      jal   weighted_avg              # weighted_avg = 750
	      move  $a0, $v0
	      jal   print_int
	      jal   print_space

	      li    $a0, 30
	      li    $a1, 31
	      li    $a2, 30
	      jal   weighted_avg              # weighted_avg = 30
	      move  $a0, $v0
	      jal   print_int
	      jal   print_newline

########################################
##            TEST PROBLEM 2          ##
########################################

	      li    $a0, 2
	      jal   print_problem_header

	      li    $a0, 1
	      li    $a1, 10
	      li    $a2, 100
	      li    $a3, 1000
	      jal   max4              # max4 = 1000
	      move  $a0, $v0
	      jal   print_int
	      jal   print_space

	      li    $a0, 1000
	      li    $a1, 10
	      li    $a2, 100
	      li    $a3, 1000
	      jal   max4              # max4 = 1000
	      move  $a0, $v0
	      jal   print_int
	      jal   print_space

	      li    $a0, 10
	      li    $a1, 1
	      li    $a2, 1
	      li    $a3, 8
	      jal   max4              # max4 = 10
	      move  $a0, $v0
	      jal   print_int
	      jal   print_newline

########################################
##            TEST PROBLEM 3          ##
########################################

	      li    $a0, 3
	      jal   print_problem_header

	      la    $a0, vec1
	      jal   length            # length = 3
	      move  $a0, $v0
	      jal   print_int
	      jal   print_space

	      la    $a0, vec2
	      jal   length            # length = -1
	      move  $a0, $v0
	      jal   print_int
	      jal   print_space

	      la    $a0, vec3
	      jal   length            # length = -1
	      move  $a0, $v0
	      jal   print_int
	      jal   print_space

	      la    $a0, vec4
	      jal   length            # length = 0
	      move  $a0, $v0
	      jal   print_int
	      jal   print_newline

########################################
##            TEST PROBLEM 4          ##
########################################

	      li    $a0, 4
	      jal   print_problem_header

	      la    $s0, somechars

	      la    $a0, 0($s0)
	      la    $a1, 1($s0)
	      jal   diffchar         # diffchar = 1
	      move  $a0, $v0
	      jal   print_int
	      jal   print_space

	      la    $a0, 2($s0)
	      la    $a1, 2($s0)
	      jal   diffchar        # diffchar = 0
	      move  $a0, $v0
	      jal   print_int
	      jal   print_space

	      la    $a0, 4($s0)
	      la    $a1, 5($s0)
	      jal   diffchar        # diffchar = 1
	      move  $a0, $v0
	      jal   print_int
	      jal   print_newline

########################################
##            TEST PROBLEM 5          ##
########################################

	      li    $a0, 5
	      jal   print_problem_header

	      la    $a0, hello
	      la    $a1, hello
	      jal   hamming         # hamming = 0
	      move  $a0, $v0
	      jal   print_int
	      jal   print_space

	      la    $a0, hello
	      la    $a1, world
	      jal   hamming         # hamming = 4
	      move  $a0, $v0
	      jal   print_int
	      jal   print_space

	      la    $a0, hello
	      la    $a1, help
	      jal   hamming         # hamming = 2
	      move  $a0, $v0
	      jal   print_int
	      jal   print_space

	      la    $a0, help
	      la    $a1, hello
	      jal   hamming         # hamming = 2
	      move  $a0, $v0
	      jal   print_int
	      jal   print_newline

########################################
##            EXIT                    ##
########################################

	      li    $v0, 10
	      syscall


####################################################################
##                      HELPER FUNCTIONS                          ##
####################################################################

print_problem_header:
	      addi  $sp, $sp -8
	      sw    $s0, 0($sp)
	      sw    $ra, 4($sp)
	      move  $s0, $a0
	      la    $a0, problem
	      jal   print_string
	      jal   print_space
	      move  $a0, $s0
	      jal   print_int
	      li    $a0, 58
	      jal   print_char
	      jal   print_space
	      lw    $s0, 0($sp)
	      lw    $ra, 4($sp)
	      addi  $sp, $sp, 8
	      jr    $ra

print_string:
	      li    $v0, 4
	      syscall
	      jr    $ra

print_char:   li    $v0, 11
	      syscall
	      jr    $ra
	      
print_int:    li    $v0, 1 
	      syscall
	      jr    $ra

print_space:
	li    $a0, 32
	li    $v0, 11
	syscall
	jr    $ra
	
print_newline:
	      la    $a0, newline
	      li    $v0, 4
	      syscall
	      jr    $ra

####################################################################
##                       END OF FILE                              ##
####################################################################
