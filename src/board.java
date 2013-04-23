import java.util.ArrayList;
import java.io.*;

public class board {
	char[][]field={{' ',' ',' ',' ',' ',' '},{' ','R','N','B','Q','K'},{' ','P','P','P','P','P'},{' ','.','.','.','.','.'},{' ','.','.','.','.','.'},{' ','p','p','p','p','p'},{' ','k','q','b','n','r'}};
	int moveNum=1;
	char onMove='B';
	static int countWhite = 0; 
	static int countBlack = 0;
	
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
	public char move(Move myMove) {
		 

		char figure = field[myMove.from.row][myMove.from.col];
		field[myMove.from.row][myMove.from.col]='.';
		field[myMove.to.row][myMove.to.col]=figure;
		
		//If pawn is on the "pawn - row" at the opponent side and moves to the last line he promotes 
		//to a queen
		if(field[6][myMove.to.col] == 'P')
			field[6][myMove.to.col] = 'Q';
		if(field[1][myMove.to.col] == 'p')
			field[1][myMove.to.col] = 'q';
		
		for(int i = 1; i <= 6; i++){
			for(int j = 1; j <= 5; j++){
				
				if(field[i][j] == 'K')
					countWhite++;
				if(field[i][j] == 'k')
					countBlack++;
			}
		}
		

		
		moveNum++;
		if (onMove=='B') {
			onMove='W';
		} else {
			onMove='B';
		}
		
		if(countBlack == 0){
			return 'W';
		}
		else if(countWhite == 0){
			return 'B';
		}
		else if(moveNum > 40){
			return 'R';
		}
		else
			return '?';
	}
	


	public ArrayList<Move> legalMoves() {
	       ArrayList<Move> moves = new ArrayList<Move>();
	       for (int i=1; i<=6; i++) {
	    	   for (int j=1; j<=5; j++) {
	    		   if (onMove=='W') {
	    			   switch (field[i][j]) {
			    		   case 'P':	addscan(moves, new Square(j,i),1,0,true,0);
			    		   				addscan(moves, new Square(j,i),1,1,true,2);
			    		   				addscan(moves, new Square(j,i),1,-1,true,2);
			    		   				break;
			    		   case 'R':	addscan(moves, new Square(j,i),1,0,false,1);
										addscan(moves, new Square(j,i),-1,1,false,1);
										addscan(moves, new Square(j,i),0,1,false,1);
										addscan(moves, new Square(j,i),0,-1,false,1);
										break;
			    		   case 'N':	addscan(moves, new Square(j,i),1,2,true,1);
										addscan(moves, new Square(j,i),2,1,true,1);
										addscan(moves, new Square(j,i),-1,2,true,1);
										addscan(moves, new Square(j,i),-2,1,true,1);
										addscan(moves, new Square(j,i),1,-2,true,1);
										addscan(moves, new Square(j,i),2,-1,true,1);
										addscan(moves, new Square(j,i),-1,-2,true,1);
										addscan(moves, new Square(j,i),-2,-1,true,1);
										break;
			    		   case 'B':	addscan(moves, new Square(j,i),1,1,false,1);
										addscan(moves, new Square(j,i),1,-1,false,1);
										addscan(moves, new Square(j,i),-1,1,false,1);
										addscan(moves, new Square(j,i),-1,-1,false,1);
										addscan(moves, new Square(j,i),1,0,true,0);
										addscan(moves, new Square(j,i),0,1,true,0);
										addscan(moves, new Square(j,i),-1,0,true,0);
										addscan(moves, new Square(j,i),0,-1,true,0);
										break;
			    		   case 'Q':	addscan(moves, new Square(j,i),1,1,false,1);
										addscan(moves, new Square(j,i),1,-1,false,1);
										addscan(moves, new Square(j,i),-1,1,false,1);
										addscan(moves, new Square(j,i),-1,-1,false,1);
										addscan(moves, new Square(j,i),1,0,false,1);
										addscan(moves, new Square(j,i),0,1,false,1);
										addscan(moves, new Square(j,i),-1,0,false,1);
										addscan(moves, new Square(j,i),0,-1,false,1);
										break;
			    		   case 'K':	addscan(moves, new Square(j,i),1,1,true,1);
										addscan(moves, new Square(j,i),1,-1,true,1);
										addscan(moves, new Square(j,i),-1,1,true,1);
										addscan(moves, new Square(j,i),-1,-1,true,1);
										addscan(moves, new Square(j,i),1,0,true,1);
										addscan(moves, new Square(j,i),0,1,true,1);
										addscan(moves, new Square(j,i),-1,0,true,1);
										addscan(moves, new Square(j,i),0,-1,true,1);
										break;
	    		   		}
	    		   }
	    		   if (onMove=='B') {
	    			   	switch (field[i][j]) {
						   case 'p':	addscan(moves, new Square(j,i),-1,0,true,0);
										addscan(moves, new Square(j,i),-1,1,true,2);
										addscan(moves, new Square(j,i),-1,-1,true,2);
							   			break;
						   case 'r':	addscan(moves, new Square(j,i),1,0,false,1);
										addscan(moves, new Square(j,i),-1,1,false,1);
										addscan(moves, new Square(j,i),0,1,false,1);
										addscan(moves, new Square(j,i),0,-1,false,1);
										break;
						   case 'n':	addscan(moves, new Square(j,i),1,2,true,1);
										addscan(moves, new Square(j,i),2,1,true,1);
										addscan(moves, new Square(j,i),-1,2,true,1);
										addscan(moves, new Square(j,i),-2,1,true,1);
										addscan(moves, new Square(j,i),1,-2,true,1);
										addscan(moves, new Square(j,i),2,-1,true,1);
										addscan(moves, new Square(j,i),-1,-2,true,1);
										addscan(moves, new Square(j,i),-2,-1,true,1);
										break;
						   case 'b':	addscan(moves, new Square(j,i),1,1,false,1);
										addscan(moves, new Square(j,i),1,-1,false,1);
										addscan(moves, new Square(j,i),-1,1,false,1);
										addscan(moves, new Square(j,i),-1,-1,false,1);
										addscan(moves, new Square(j,i),1,0,true,0);
										addscan(moves, new Square(j,i),0,1,true,0);
										addscan(moves, new Square(j,i),-1,0,true,0);
										addscan(moves, new Square(j,i),0,-1,true,0);
										break;
						   case 'q':	addscan(moves, new Square(j,i),1,1,false,1);
										addscan(moves, new Square(j,i),1,-1,false,1);
										addscan(moves, new Square(j,i),-1,1,false,1);
										addscan(moves, new Square(j,i),-1,-1,false,1);
										addscan(moves, new Square(j,i),1,0,false,1);
										addscan(moves, new Square(j,i),0,1,false,1);
										addscan(moves, new Square(j,i),-1,0,false,1);
										addscan(moves, new Square(j,i),0,-1,false,1);
										break;
						   case 'k':	addscan(moves, new Square(j,i),1,1,true,1);
										addscan(moves, new Square(j,i),1,-1,true,1);
										addscan(moves, new Square(j,i),-1,1,true,1);
										addscan(moves, new Square(j,i),-1,-1,true,1);
										addscan(moves, new Square(j,i),1,0,true,1);
										addscan(moves, new Square(j,i),0,1,true,1);
										addscan(moves, new Square(j,i),-1,0,true,1);
										addscan(moves, new Square(j,i),0,-1,true,1);
										break;
	    			   	}
	    		   }
	    	   }
	       }
	       return moves;
	}

//Scan the field for moves
//captureMode 0: forbid capture, 1: allow capture, 2: only capture, no move
	public ArrayList<Move> addscan(ArrayList<Move> moves, Square start, int dr, int dc, boolean oneStep, int captureMode) {
		int temp_row=start.row+dr;
		int temp_col=start.col+dc;
		int cnt=0;
		
		//check if move is on the board yet and if there is any figure on the target
		while ((temp_row <= 6) && (temp_row >= 1) && (temp_col <= 5) && (temp_col >= 1) && ((oneStep && cnt < 1) || (!oneStep)) && ((onMove=='W' && !(field[temp_row][temp_col] >= 'A' && field[temp_row][temp_col]<'Z')) || (onMove=='B' && !(field[temp_row][temp_col] >= 'a' && field[temp_row][temp_col]<'z')))) { 
			//check if there is any capture activity
			if (captureMode > 0 && (onMove=='W' && field[temp_row][temp_col]>='a' && field[temp_row][temp_col]<='z')) {
				moves.add(new Move(start, new Square(temp_col,temp_row)));
				break;
			}
			if (captureMode > 0 && (onMove=='B' && field[temp_row][temp_col]>='A' && field[temp_row][temp_col]<='Z')) {
				moves.add(new Move(start, new Square(temp_col,temp_row)));
				break;
			}
			if (captureMode == 0 && captureMode != 2 && field[temp_row][temp_col]=='.') moves.add(new Move(start, new Square(temp_col,temp_row)));
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

	public static void main(String[] args){
		char move_result;
		BufferedReader bin = new BufferedReader(
                new InputStreamReader(System.in));
		String eingabe = "null";
		board myBoard=new board();
		myBoard.print();
		ArrayList<Move> movelist = myBoard.legalMoves();
	       for (Move m : movelist)  // for() loop over list
	    	   System.out.println(m);
		try{
			do{
				eingabe = bin.readLine();
				double rnd = Math.random();
				int rnd_int = (int)(rnd*movelist.size());
				Move act_move = movelist.get(rnd_int);
				System.out.println("Move: "+act_move+" Rnd: "+rnd_int);
				move_result = myBoard.move(act_move);
				myBoard.print();
				movelist = myBoard.legalMoves();
			    for (Move m : movelist)  // for() loop over list
			       System.out.println(m);
			}
			while(move_result == '?');
		}catch(NullPointerException e){
			System.out.println("False Input given: " + e.getMessage());
		}catch(IOException f){
			f.getMessage();
		}catch(ArrayIndexOutOfBoundsException f){
			System.out.println("False Input given: Please type a correct Move in the Terminal");
		}
	}
}