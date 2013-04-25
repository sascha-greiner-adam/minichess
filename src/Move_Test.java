//Copyright © 2013 Sascha Greiner-Adam, Matthias Karl


public class Move_Test {

	//Testing class for move from a to b without any check for legal moves
	//and promoting a pawn to a queen
	
	static String field_pawn_begin	="0 W .....P........................";
	static String field_pawn_end	="0 B ..........P...................";

	static String field_pawn_promote_begin	="0 W .....P........................";
	static String field_pawn_promote_end	="1 B Q.............................";

	static boolean promote = true;
	
	
	public static boolean equals(board b1, board b2){
		boolean trigger = false;
		for(int i=1; i<=6; i++){
			for(int j=1; i<=5; i++){
				if(b1.field[i][j] == b2.field[i][j])
					trigger = true;
				else
					trigger = false;
			}
		}
		return trigger;
	}
	
	public static void main(String[] args) {
		
		if(promote == false){
			board myBoard = new board(field_pawn_begin);
			board myBoard2 = new board(field_pawn_end);
	
			Move myMove = new Move("a5-a4");
			myBoard.move(myMove);
			
			if(equals(myBoard, myBoard2))
				System.out.println("Test finished expected");
			else
				System.out.println("Test finished unexpected");
		}
		else{
			board myBoard_promote = new board(field_pawn_promote_begin);
			board myBoard_promote2 = new board(field_pawn_promote_end);

			Move myMove = new Move("a5-a6");
			myBoard_promote.move(myMove);

			if(equals(myBoard_promote,myBoard_promote2))
				System.out.println("Test finished expected");
			else
				System.out.println("Test finished unexpected");
		}
	}

}
