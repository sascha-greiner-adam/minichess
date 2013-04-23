import java.util.ArrayList;



public class board {
	char[][]field={{' ',' ',' ',' ',' ',' '},{' ','R','N','B','Q','K'},{' ','P','P','P','P','P'},{' ','.','.','.','.','.'},{' ','.','.','.','.','.'},{' ','p','p','p','p','p'},{' ','k','q','b','n','r'}};
	int moveNum=1;
	char onMove='B';
	
//Constructors	
	public board() {
	}
	
	public board(String pattern) {
		String[] splitResult = pattern.split(" ");
		this.moveNum=Integer.parseInt(splitResult[0]);
		this.onMove=splitResult[1].charAt(0);
		for (int i=6; i>=1; i--) {
			for (int j=1; j<=5; j++) {
				field[i][j]=splitResult[2].charAt(((6-i)*5)+j-1);
			}
		}
	}

//Moves a figure without check
	public void move(Move myMove) {
		char figure = field[myMove.from.row][myMove.from.col];
		field[myMove.from.row][myMove.from.col]='.';
		field[myMove.to.row][myMove.to.col]=figure;
		moveNum++;
		if (onMove=='B') {
			onMove='W';
		} else {
			onMove='B';
		}
	}

	public ArrayList<Move> legalMoves() {
	       ArrayList<Move> moves = new ArrayList<Move>();
	       for (int i=1; i<=6; i++) {
	    	   for (int j=1; j<=5; j++) {
	    		   switch (field[i][j]) {
		    		   case 'P':	addscan(moves, new Square(j,i),1,0,true,0);
		    		   				addscan(moves, new Square(j,i),1,1,true,2);
		    		   				addscan(moves, new Square(j,i),1,-1,true,2);
		    		   break;
	    		   }
	    	   }
	       }
	       //addscan(moves, new Square("c2"),1,0,false,1);
	       for (Move m : moves)  // for() loop over list
	    	   System.out.println(m);
	       return moves;
	}

//Scan the field for moves
//captureMode 0: forbid capture, 1: allow capture, 2: only capture, no move
	public ArrayList<Move> addscan(ArrayList<Move> moves, Square start, int dr, int dc, boolean oneStep, int captureMode) {
		int temp_row=start.row+dr;
		int temp_col=start.col+dc;
		int cnt=0;
		if (onMove=='W') {
			
		}
		
		//check if move is on the board yet and if there is any figure on the target
		while ((temp_row <= 6) && (temp_row >= 1) && (temp_col <= 5) && (temp_col >= 1) && ((oneStep && cnt < 1) || (!oneStep)) && ((onMove=='W' && !(field[temp_row][temp_col] >= 'A' && field[temp_row][temp_col]<'Z')) || (onMove=='B' && field[temp_row][temp_col] >= 'a' && !(field[temp_row][temp_col]<'z')))) { 
			//check if there is any capture activity
			if (captureMode > 0 && (onMove=='W' && field[temp_row][temp_col]>='a' && field[temp_row][temp_col]<='z')) {
				moves.add(new Move(start, new Square(temp_col,temp_row)));
				break;
			}
			if (captureMode > 0 && (onMove=='B' && field[temp_row][temp_col]>='A' && field[temp_row][temp_col]<='Z')) {
				moves.add(new Move(start, new Square(temp_col,temp_row)));
				break;
			}
			if (captureMode != 2) moves.add(new Move(start, new Square(temp_col,temp_row)));
			temp_row+=dr;
			temp_col+=dc;
			cnt++;
		}
		
		return moves;
	}
	
//Return string which represents the actual state
	public String toString() {
		String print_out=""+moveNum+" "+onMove+" ";
		for (int i=6; i>=1; i--) {
			for (int j=1; j<=5; j++) {
				print_out=print_out+field[i][j];
			}
		}
		return print_out;
	}

//Prints out the actual chess board
	public void print() {
		String print_out="";
		System.out.println(moveNum+" "+onMove);
		for (int i=6; i>=1; i--) {
			print_out="";
			for (int j=1; j<=5; j++) {
				print_out=print_out+field[i][j];
			}
			System.out.println(print_out);
		}
		System.out.println();
	}

//Main method
	public static void main(String[] args) {

		board myBoard=new board("1 B kqbnrppp......p....pPPPPPRNBQK");
		myBoard.print();
		myBoard.move(new Move("b6 c5"));
		myBoard.print();
		ArrayList<Move> bla = myBoard.legalMoves();
	}
}