
public class History_Test {

	static String field_pawn_begin	="0 W .....P........................";

	public static void main(String[] args) {
		
		board myBoard = new board(field_pawn_begin);
		Move myMove = new Move("a5-a4");
		myBoard.addToHistory(myMove);
		myBoard.addToHistory(myMove);
		myBoard.addToHistory(myMove);
		myBoard.addToHistory(myMove);
		myBoard.addToHistory(myMove);
		myBoard.addToHistory(myMove);

		
		
		System.out.println(myBoard.getHistory(myMove));
	}

}
