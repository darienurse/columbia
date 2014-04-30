###############################################
# Darien Nurse
# COMS3101.003 - Python - Homework 1
# Part 1 
################################################
import random

def main ():
# chose a random number between 1 and 10
    gameover = 0
    maxAttempts = 5
    playerAttempts = 0
    secret_number = random.choice(range(1,11))
    guess = int (raw_input ('Guess a number between 1 and 10: ' ))
    while (guess > 10 or guess <= 0):
        print ('Invalid Input') 
        guess = int (raw_input ('Guess a number between 1 and 10: ' ))
    playerAttempts = playerAttempts + 1
    while (not gameover):
        if abs(guess-secret_number) > 5:
                print ('not even close') 
        if abs(guess-secret_number) <= 5 and abs(guess-secret_number) >= 3 :
                print ('close')  
        if abs(guess-secret_number) < 3 and abs(guess-secret_number) > 0:
                print ('very close, almost there') 
        if guess == secret_number:
                print ('YOU WIN!') 
                gameover = 1 
        if playerAttempts == maxAttempts:
                print ('YOU LOSE! The number was ' + str(secret_number) + '.')    
                gameover = 1                         
        if guess != secret_number and playerAttempts != maxAttempts:
            print ('You have ' + str(maxAttempts - playerAttempts) + ' tries left.') 
            guess = int (raw_input ('Guess a number between 1 and 10: ' ))
            while (guess > 10 or guess <= 0):
                print ('Invalid Input') 
                guess = int (raw_input ('Guess a number between 1 and 10: ' ))
            playerAttempts = playerAttempts + 1
main()