# Red-Black-Tree-Two-Player-Game

Definition :
The purpose of this project is to implement the red-black tree data structure and perform insertion, deletion, search and navigation operations on it. For this purpose, a two-person game is designed as follows: one of the players makes the first move, and after that, each player makes one move.
In each move, the player performs one of the following two activities:

A) Choose a number from numbers 1 to 32 that has not been chosen before by any of the players and insert it into the tree (choosing deleted numbers is also not allowed).

B) One of the previously selected numbers that is smaller than 33 and still available in the tree is removed and the same number plus 32 is inserted in the tree.

End of the game: The game ends when either all the numbers from 1 to 32 are selected or the height of the tree becomes 7.

The winner of the game: at the end, the negative score of each person is equal to the sum of the product of the value of each number at the height of that number in the tree for the numbers in the red nodes of the tree. The winner of the game is the one who has less negative points.

- Input
The input to our program is in two lines containing the order of selection or elimination of numbers by each player, separated by commas and no other spaces between them.
Please note that the presence of a repeated number in the entry means the player's decision to remove that number and insert the same number plus 32. 

Game input may be invalid. Some of the input invalidity modes are as follows:

One person tries to add a number to the tree that has been added to the tree once during the game.
The input number should not be in the range of 1 to 32.
The input sequence is finished, but the game is not finished.
Game over if there is still an input sequence.
The second player plays one step more than the first player.

- Output
The output consists of one line. If the input was invalid, output 0.
Otherwise, the output of the program should be as follows:

"result penalty1 penalty2"

In which, penalty1 is the amount of negative points of the first player, penalty2 is the amount of negative points of the second player and result is equal to 0 if the game is tied, and if player 1 wins it is equal to 1 and if player 2 wins it is equal to 2.

Except for the corresponding tree, there is no other memory at our disposal, and we are not allowed to use any ready-made implementation of the red-black tree.

INPUT EXAMPLE 1:
15,15,3,3,30,30,25,10,25,10,29,29,16,16,1,12,12,1,9,9,11,11,20,20,19,17,17,19,14,14,24
23,21,23,21,8,8,27,27,28,28,6,6,22,22,4,4,2,2,31,31,7,7,13,26,26,13,18,32,32,18,5

OUTPUT EXAPLE 1:
1 538 912

In this example, the negative score of the first player is equal to 538 and the negative score of the second player is equal to 912, and as a result, the first player wins.


INPUT EXAMPLE 2:
5,14,27,38,15,23,31,35,39,6,40,22,13,2,4,12
17,19,20,30,18,37,26,25,8,24,16,32,9,28,7,11

OUTPUT EXAPLE 2:
0

In this example, the first player enters the number 38 in the fourth step, which is not allowed.
