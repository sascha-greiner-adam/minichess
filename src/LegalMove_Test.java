//Copyright © 2013 Sascha Greiner-Adam, Matthias Karl

public class LegalMove_Test {
	
	static String field_pawn_begin	="41 W ....." +
											"....." +
											".k.q." +
											"P...." +
											"....." +
											"..K..";
	
	public static void main(String[] args) {
		board myBoard = new board(field_pawn_begin);
		
		System.out.println("Legal Moves: " + myBoard.legalMoves() + "Score: " + myBoard.negamax_move(myBoard, 2));
		System.out.println("Result: " + myBoard.gameOver());
		
	}

}
