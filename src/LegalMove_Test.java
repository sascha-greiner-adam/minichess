//Copyright © 2013 Sascha Greiner-Adam, Matthias Karl

public class LegalMove_Test {
	
	static String field_pawn_begin	="0 W ....." +
											"k...." +
											"....p" +
											".p.r." +
											"..Q.." +
											"K....";
	
	public static void main(String[] args) {
		board myBoard = new board(field_pawn_begin);
		myBoard.time_left=5;
		System.out.println("Legal Moves: " + myBoard.legalMoves() + "Move: " + myBoard.negamax_move(myBoard, 5));
		System.out.println("Result: " + myBoard.gameOver());
		
	}

}
