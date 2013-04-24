//Copyright © 2013 Sascha Greiner-Adam, Matthias Karl
//telnet_login: user = ai_megachess_8000_ger passwort = minichess2013


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class board {
	char[][]field={{' ',' ',' ',' ',' ',' '},{' ','R','N','B','Q','K'},{' ','P','P','P','P','P'},{' ','.','.','.','.','.'},{' ','.','.','.','.','.'},{' ','p','p','p','p','p'},{' ','k','q','b','n','r'}};
	int moveNum=1;
	char onMove='W';

	
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

	//Calculate the current score for the active player and returns it
	public int getScore(){
		
		int scoreWhite = 0;
		int scoreBlack = 0;
		
		for(int i = 1; i <= 6; i++){
			for(int j = 1; j <= 5; j++){
				
				switch(field[i][j]){
					case 'K': 	//countWhite++;
								scoreWhite += 10000;
								break;
								
					case 'Q': 	scoreWhite += 100;
								break;
		
					case 'B': 	scoreWhite += 30;
								break;
								
					case 'N': 	scoreWhite += 30;
								break;
								
					case 'R': 	scoreWhite += 50;
								break;
		
					case 'P': 	scoreWhite += 10;
								break;
		
					case 'k':	//countBlack++;
								scoreBlack += 10000;
								break;
								
					case 'q':	scoreBlack += 100;
								break;
	
					case 'b':	scoreBlack += 30;
								break;
								
					case 'n':	scoreBlack += 30;
								break;
								
					case 'r':	scoreBlack += 50;
								break;
								
					case 'p':	scoreBlack += 10;
								break;
								
					default:	break;
				}
			}
		}
		if(onMove == 'B')
			return scoreBlack - scoreWhite;
		else
			return scoreWhite - scoreBlack;
	}
	
//Moves a figure without check

	public char move(Move myMove) {

		int countWhite = 0; 
		int countBlack = 0;

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
		
		if(countBlack == 0)
			return 'W';
		
		else if(countWhite == 0)
			return 'B';
		
		else if(moveNum >= 40)
			return 'R';
		
		else
			return '?';
	}

	public char gameOver(){
		
		int countWhite = 0; 
		int countBlack = 0;

		for(int i = 1; i <= 6; i++){
			for(int j = 1; j <= 5; j++){
				if(field[i][j] == 'K')
					countWhite++;
				if(field[i][j] == 'k')
					countBlack++;
			}
		}
		
		if(countBlack == 0) return 'W';
		
		if(countWhite == 0)	return 'B';
		
		if(moveNum >= 40) return 'R';
		
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
	       if (moves.isEmpty()) System.out.println("---ACHTUNG--- Movelist leer!!!");
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
			if (captureMode <= 1 && field[temp_row][temp_col]=='.') moves.add(new Move(start, new Square(temp_col,temp_row)));
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
		System.out.println("Round: "+moveNum+" On Move: "+onMove);
		for (int i=6; i>=1; i--) {
			print_out="";
			for (int j=1; j<=5; j++) {
				print_out=print_out+field[i][j];
			}
			System.out.println(print_out);
		}
		System.out.println();
	}

	public int negamax(board b, int d) {
		int score=-10000;
		if (b.gameOver() != '?' || d == 0) return b.getScore();

		ArrayList<Move> ml = b.legalMoves();
		for (Move m : ml) {
			board b2=new board(b.toString());
			b2.move(m);
			score = Math.max(score, -negamax(b2,d-1));
		}
		return score;
	}
	
	public char dumb_random() {
	ArrayList<Move> movelist = legalMoves();
	double rnd = Math.random();
	int rnd_int = (int)(rnd*movelist.size());
	if (movelist.isEmpty()) {
		System.out.println("Dumb Movelist leer!");
		return '=';
	} else {
		Move act_move = movelist.get(rnd_int);
		return move(act_move);
	}
}

	public char half_dumb_random() {

		int score=10000;
		board copy = new board(this.toString());
		ArrayList<Move> movelist = copy.legalMoves();
		ArrayList<Move> exec_movelist = new ArrayList<Move>();
		for (Move m : movelist) {
			copy = new board(this.toString());
			copy.move(m);
			if (copy.getScore() < score) score=copy.getScore();
		}
		for (Move m : movelist) {
			copy = new board(this.toString());
			copy.move(m);
			if (copy.getScore() <= score) {
				exec_movelist.add(m);
			}
		}
		double rnd = Math.random();
		int rnd_int = (int)(rnd*exec_movelist.size());
		if (movelist.isEmpty()) {
			System.out.println("Halfdumb Movelist leer!");
			return '=';
		} else {
			Move act_move = exec_movelist.get(rnd_int);
			return move(act_move);
		}
		
	}
	public char nega_player_quick() {
		Map<Move, Integer> map = new HashMap<Move, Integer>();
		
		int score=10000;
		int negascore = 0;
		
		board copy = new board(this.toString());
		ArrayList<Move> movelist = copy.legalMoves();
		ArrayList<Move> exec_movelist = new ArrayList<Move>();
		for (Move m : movelist) {
			copy = new board(this.toString());
			copy.move(m);
			negascore = negamax(copy,3);
			if (negascore < score) score=negascore;
			map.put(m, negascore);
		}
		/*for (Move m : movelist) {
			copy = new board(this.toString());
			copy.move(m);
			if (negamax(copy,3) <= score) {
				exec_movelist.add(m);
			}
		}*/

		for (Map.Entry<Move, Integer> entry : map.entrySet()) {
		    if (entry.getValue() <= score) exec_movelist.add(entry.getKey());
		}
		
		double rnd = Math.random();
		int rnd_int = (int)(rnd*exec_movelist.size());
		if (movelist.isEmpty()) {
			System.out.println("Negamax Movelist leer!");
			return '=';
		} else {
			Move act_move = exec_movelist.get(rnd_int);
			return move(act_move);
		}
	}
	
	public char nega_player() {
		
		int score=10000;
		int negascore = 0;
		
		board copy = new board(this.toString());
		ArrayList<Move> movelist = copy.legalMoves();
		ArrayList<Move> exec_movelist = new ArrayList<Move>();
		for (Move m : movelist) {
			copy = new board(this.toString());
			copy.move(m);
			negascore = negamax(copy,3);
			if (negascore < score) score=negascore;
		}
		for (Move m : movelist) {
			copy = new board(this.toString());
			copy.move(m);
			if (negamax(copy,3) <= score) {
				exec_movelist.add(m);
			}
		}

		double rnd = Math.random();
		int rnd_int = (int)(rnd*exec_movelist.size());
		if (movelist.isEmpty()) {
			System.out.println("Negamax Movelist leer!");
			return '=';
		} else {
			Move act_move = exec_movelist.get(rnd_int);
			return move(act_move);
		}
	}

	public static void main(String[] args){
		char move_result;
		long $startmilli = 0;
		long $time_black = 0;
		long $time_white = 0;
		board myBoard=new board();
		myBoard.print();
			do{
				$startmilli = System.currentTimeMillis();
				if (myBoard.onMove=='B') {
					move_result = myBoard.nega_player();
					$time_black+=(System.currentTimeMillis()-$startmilli);
				} else {
					move_result = myBoard.nega_player_quick();
					$time_white+=(System.currentTimeMillis()-$startmilli);
				}
				System.out.println("Current Score: " + myBoard.getScore()+ " MoveResult: "+move_result+" Time: "+(System.currentTimeMillis()-$startmilli));
				myBoard.print();
				System.out.println();
			} while(move_result == '?');
			
			myBoard.print();
			switch (move_result) {
				case 'B': System.out.println("Black wins");
				break;
				case 'W': System.out.println("White wins");
				break;
				case 'R': System.out.println("Remis");
				break;
			}
			System.out.println("Black needs around "+($time_black/myBoard.moveNum)+" Millisconds per move");
			System.out.println("White needs around "+($time_white/myBoard.moveNum)+" Millisconds per move");
	}
}