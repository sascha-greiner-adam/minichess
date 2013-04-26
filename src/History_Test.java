
public class History_Test {

	static String field_pawn_begin	="0 W .....P........................";

	public static void main(String[] args) {
		
		board myBoard = new board(field_pawn_begin);
		
		myBoard.loadHistory("hist_test.txt");
		Move myMove = new Move("a5-a4");

		System.out.println(myMove.getHistory());
		
		myMove.addToHistory();
		myMove.addToHistory();
		myMove.addToHistory();
		myMove.addToHistory();
		myMove.addToHistory();
		
		System.out.println(myMove.getHistory());
		myBoard.saveHistory("hist_test.txt");
	}

}
