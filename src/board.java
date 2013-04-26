/*Copyright © 2013 Sascha Greiner-Adam, Matthias Karl
/telnet_login: user = ai_megachess_8000_ger passwort = minichess2013

*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class board {
	char[][]field={{' ',' ',' ',' ',' ',' '},{' ','R','N','B','Q','K'},{' ','P','P','P','P','P'},{' ','.','.','.','.','.'},{' ','.','.','.','.','.'},{' ','p','p','p','p','p'},{' ','k','q','b','n','r'}};
	int moveNum=1;
	char onMove='W';
	static long time;
	static int time_left;
	static long countloop=0;
	static int[][] hist = new int[57][57];


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
					case 'K': 	scoreWhite += 10000;
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
					case 'k':	scoreBlack += 10000;
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
				}
			}
		}
		if(onMove == 'B')
			return scoreBlack - scoreWhite;
		else
			return scoreWhite - scoreBlack;
	}

//Calculate the current score for the active player using extended function
	public int getImpScore() {
		int scoreWhite = 0;
		int scoreBlack = 0;
		int anz_figuren = 0;
		int gamestate = 0;
		int[] queen = {100,100,120};
		int[] bishop = {30,30,35};
		int[] knight = {30,30,30};
		int[] rook = {45,45,45};
		int[] pawn = {10,10,10};
		int[] king = {10000,10000,10000};
		
		if (anz_figuren < 20 && anz_figuren >= 8) gamestate = 1;
		if (anz_figuren < 8) gamestate = 2;
		
		for(int i = 1; i <= 6; i++){ for(int j = 1; j <= 5; j++){
			switch(field[i][j]){
				case 'K': 	scoreWhite += king[gamestate];
							//reward or penalty for central positions
							if (i<=3 && gamestate<2) scoreWhite += -1*(i-1);
							if (i>3 && gamestate<2) scoreWhite += -1*(6-i);
							if (j<=3 && gamestate<2) scoreWhite += -1*(j-1);
							if (j>3 && gamestate<2) scoreWhite += -1*(5-j);
							anz_figuren++;
							break;
				case 'Q': 	scoreWhite += queen[gamestate];
							//reward or penalty for central positions
							if (i<=3 && gamestate<1) scoreWhite += -2*(i-1);
							if (i>3 && gamestate<1) scoreWhite += -2*(6-i);
							if (j<=3 && gamestate<1) scoreWhite += -2*(j-1);
							if (j>3 && gamestate<1) scoreWhite += -2*(5-j);
							anz_figuren++;
							break;
				case 'B': 	scoreWhite += bishop[gamestate];
							anz_figuren++;
							break;
				case 'N': 	scoreWhite += knight[gamestate];
							//reward or penalty for central positions
							if (i<=3) scoreWhite += 1*(i-1);
							if (i>3) scoreWhite += 1*(6-i);
							if (j<=3) scoreWhite += 1*(j-1);
							if (j>3) scoreWhite += 1*(5-j);
							anz_figuren++;
							break;
				case 'R': 	scoreWhite += rook[gamestate];
							anz_figuren++;
							break;
				case 'P': 	scoreWhite += pawn[gamestate];
							anz_figuren++;
							//reward for defended pawns
							if (defends(new Square(i,j),1,1,true,'P') || defends(new Square(i,j),1,-1,true,'P')) scoreWhite += 2;
							//penalty for backward pawns and double pawns
							if (!defends(new Square(i,j),0,1,false,'p') && (defends(new Square(i,j),1,0,true,'P') || defends(new Square(i,j),0,1,true,'P') || defends(new Square(i,j),-1,-1,true,'P') || defends(new Square(i,j),-1,1,true,'P'))) scoreWhite -= 6; 
							break;
							
				case 'k':	scoreBlack += king[gamestate];
							//reward or penalty for central positions
							if (i<=3 && gamestate<2) scoreBlack += -1*(i-1);
							if (i>3 && gamestate<2) scoreBlack += -1*(6-i);
							if (j<=3 && gamestate<2) scoreBlack += -1*(j-1);
							if (j>3 && gamestate<2) scoreBlack += -1*(5-j);
							anz_figuren++;
							break;
				case 'q':	scoreBlack += queen[gamestate];
							//reward or penalty for central positions
							if (i<=3 && gamestate<1) scoreBlack += -2*(i-1);
							if (i>3 && gamestate<1) scoreBlack += -2*(6-i);
							if (j<=3 && gamestate<1) scoreBlack += -2*(j-1);
							if (j>3 && gamestate<1) scoreBlack += -2*(5-j);
							anz_figuren++;
							break;
				case 'b':	scoreBlack += bishop[gamestate];
							anz_figuren++;
							break;
				case 'n':	scoreBlack += knight[gamestate];
							//reward or penalty for central positions
							if (i<=3) scoreBlack += 1*(i-1);
							if (i>3) scoreBlack += 1*(6-i);
							if (j<=3) scoreBlack += 1*(j-1);
							if (j>3) scoreBlack += 1*(5-j);
							anz_figuren++;
							break;
				case 'r':	scoreBlack += rook[gamestate];
							anz_figuren++;
							break;
				case 'p':	scoreBlack += pawn[gamestate];
							anz_figuren++;
							
							break;
			}
		}}

		if(onMove == 'B')
			return scoreBlack - scoreWhite;
		else
			return scoreWhite - scoreBlack;
	}

//Moves a figure on the board without check
	public char move(Move myMove) {
		
		char figure = field[myMove.from.row][myMove.from.col];
		try {
			field[myMove.from.row][myMove.from.col]='.';
			field[myMove.to.row][myMove.to.col]=figure;
		} catch(NullPointerException e){
			System.out.println("!!! Ziel des Moves auﬂerhalb des Feldes!");
			e.getMessage();
		}
		
		//If pawn is on the "pawn - row" at the opponent side and moves to the last line he promotes 
		//to a queen
		if(field[6][myMove.to.col] == 'P')
			field[6][myMove.to.col] = 'Q';
		if(field[1][myMove.to.col] == 'p')
			field[1][myMove.to.col] = 'q';
		
		if (onMove=='B') {
			onMove='W';
			moveNum++;
		} else {
			onMove='B';
		}
		return gameOver();
	}
	
//Checks the game progress and returns W = white wins, B = black wins, 
//R = remis, ? = game in progress
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
		
		if(moveNum >= 41) return 'R';
		
			return '?';
	}
	
//generate all legal moves and store and return them in/as an ArrayList<Move>
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
	
	public boolean defends(Square start, int dr, int dc, boolean oneStep, char figure) {
		int temp_row=start.row+dr;
		int temp_col=start.col+dc;
		int cnt=0;
		while(temp_row>=1 && temp_row<=6 && temp_col>=1 && temp_col<=5 && ((!oneStep || cnt<1) || oneStep)) {
			if (field[temp_row][temp_col]==figure) return true;
			if (field[temp_row][temp_col]!='.') return false;
			cnt++;
			temp_row+=dr;
			temp_col+=dc;
		}
		return false;
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
			System.out.println(i+" "+print_out);
		}
		System.out.println();
		System.out.println("  ABCDE");
	}

//method for moving using negamax-algorithm and a given depth
	public Move negamax_move(board b, int d) {
		//Move m0=null;
		int v=-10000;
		int v0 = 0;
		int alpha=-10000;
		board copy = new board(this.toString());
		ArrayList<Move> bestMoves = copy.legalMoves();
		ArrayList<Move> movelist = copy.legalMoves();
		for (Move m : movelist) {
			copy = new board(this.toString());
			copy.move(m);
			v0 = Math.max(v,-negamax_prune(copy,d,-10000,-alpha,false));
			alpha = Math.max(alpha, v0);
			//map.put(m, v0);
			if (v0 >= v) bestMoves.add(m);
			v = Math.max(v, v0);
		}
		//System.out.println(m0+" - "+v0+" Depth: "+d);
		
		Collections.sort(bestMoves);
		return bestMoves.get(0);
	}

//method for moving using negamax_move and iterative deepening
	public Move negamax_move_id(board b, int t){
		int d=0;
		long now = System.currentTimeMillis();
		Move m = null;
		time = now+t;
		time_left = (int)(time-now);
		while (time_left > 0) {
			Move m2 = negamax_move(b,d++);
			now = System.currentTimeMillis();
			time_left = (int)(time-now);
			if (time_left <= 0) {
				//System.out.println("Move: "+m+" Depth: "+d);
				return m;
			}
			m=m2;
		}
		return m;
	}

//recursive negamax method with alpha-beta-pruning

//Execute the negamax algorithm with the alpha beta prune and return the score as an integer
	public int negamax_prune(board b, int d, int alpha, int beta, boolean search_extend) {
		//ID-Test
		countloop++;
		long now=0;
		if (countloop % 10000 == 0) {
			now = System.currentTimeMillis();
			time_left = (int)(time-now);
		}
		//ID-Test END
		
		int score=-10000;
		if ((b.gameOver() != '?') || (search_extend && (d < 0)) || (!search_extend && (d < 1)) || (!search_extend && (time_left < 0))) return b.getScore();
		ArrayList<Move> ml = b.legalMoves();
		Collections.sort(ml);		
		for (Move m : ml) {
			board b2=new board(b.toString());
			b2.move(m);
			
			
			//System.out.println("Move: "+m+" Old score: "+b.getScore()+" new score: "+b2.getScore());
//			if (-b2.getScore()>b.getScore()) {
//				System.out.println("Search extended");
//				score = Math.max(score,-negamax_prune(b2,d-1,-beta,-alpha,true));
//			} else 
				score = Math.max(score,-negamax_prune(b2,d-1,-beta,-alpha,false));
			
			//score = Math.max(score,-negamax_prune(b2,d-1,-beta,-alpha,false));
			alpha = Math.max(alpha, score);
			if (score >= beta) return score;
		}
		return score;
	}
	
	public void saveHistory(String filename) {
		String print_out="";
		File file = new File(filename);
		try {
			FileWriter writer = new FileWriter(file,false);			
			for (int i=0; i<57; i++) {
				for (int j=0; j<57; j++)
					print_out+=hist[i][j]+",";
				writer.write(print_out+System.getProperty("line.separator"));
				print_out="";
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.getMessage();
		}
	}
	
	public void loadHistory(String filename){
		String read_in="";
		String[] read_in_c=null;
		File file = new File(filename);
		int j=0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			//for (int i=0; i<57; i++) {
			read_in=reader.readLine();
			while (read_in!=null) {
				read_in_c=read_in.split(",");
				for (int i=0; i<read_in_c.length; i++) {
					hist[j][i]=Integer.parseInt(read_in_c[i]);
				}
				read_in=reader.readLine();
				j++;
			}
			reader.close();
		} catch (IOException e) {
			e.getMessage();
		}
	}
	
//main method - start of the programm
	public static void main(String[] args){

		Move move_result;
		
		String id;
		long startmilli = 0;
		long time_left_1 = 300000;
		long time_left_2 = 300000;
		int intervall=0;
		char color='?';
		
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			Client myClient=null;
			System.out.println("Choose one option:");
			System.out.println("1 - Local game");
			System.out.println("2 - Network game");
			System.out.println("3 - Exit");
			int menu = Integer.parseInt(in.readLine());
						
			if (menu==2) {
				myClient = new Client("imcs.svcs.cs.pdx.edu", "3589", "ai_megachess_8000_ger", "minichess2013");
				System.out.println("1 - Create new game");
				System.out.println("2 - Join game");
				int offers = Integer.parseInt(in.readLine());
				
				if (offers==1) {
					System.out.println("Which color?");
					System.out.println("B - Black");
					System.out.println("W - White");
					System.out.println("? - Random");
					color = (char)in.read();
					myClient.offer(color);
				}
				if (offers==2) {
					System.out.println("Which game do you want to join?");
					id = in.readLine();
					color = myClient.accept(id, '?');
				}
			} else if (menu==1){			
				color='B';
			} else {
				System.exit(0);
			}
			
			
			board myBoard=new board();
			myBoard.loadHistory("hist.txt");
			myBoard.print();
			System.out.println("You are: "+color);
			do{	
					startmilli = System.currentTimeMillis();
					if (myBoard.onMove == color) {
						intervall = Math.max(2000,(int)(time_left_1/41-myBoard.moveNum)-1500);
						System.out.println("Zeit f¸r Zug: "+intervall);
						move_result = myBoard.negamax_move_id(myBoard,intervall);		// negamax player with pruning and time management
						
						if (menu==2) myClient.sendMove("! " + move_result.toString());	// needed for network play

						time_left_1 -= System.currentTimeMillis()-startmilli;
					} else {
						intervall = Math.max(2000,(int)(time_left_2/41-myBoard.moveNum)-1500);
						
						if (menu==2) move_result = new Move(myClient.getMove());
						else {
							//move_result= myBoard.negamax_move(myBoard,10);
							move_result = myBoard.negamax_move_id(myBoard,intervall);	// negamax player with pruning and time management
						}
						time_left_2 -= System.currentTimeMillis()-startmilli;
					}
					move_result.addToHistory();
					myBoard.move(move_result);
					System.out.println("============================");
					System.out.println("Score: " + myBoard.getScore()+" Time left you: "+(int)(time_left_1/1000)+ " Time left opponent: "+(int)(time_left_2/1000));
					myBoard.print();
					System.out.println("============================");

				} while(myBoard.gameOver() == '?');

				if (myBoard.gameOver()=='B') System.out.println("Black wins");
				if (myBoard.gameOver()=='W') System.out.println("White wins");
				if (myBoard.gameOver()=='R') System.out.println("Remis");

				if (menu==2) myClient.close();

				myBoard.saveHistory("hist.txt");
		}
		catch(IOException e){
			System.out.println("!!! Connection problem");
			e.getMessage();
		}
	}
}