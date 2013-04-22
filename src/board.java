

public class board {
	char[][]field={{' ',' ',' ',' ',' ',' '},{' ','R','N','B','Q','K'},{' ','P','P','P','P','P'},{' ','.','.','.','.','.'},{' ','.','.','.','.','.'},{' ','p','p','p','p','p'},{' ','k','q','b','n','r'}};
	int moveNum=1;
	char onMove='B';
	
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
	
	public String toString() {
		String print_out=""+moveNum+" "+onMove+" ";
		for (int i=6; i>=1; i--) {
			for (int j=1; j<=5; j++) {
				print_out=print_out+field[i][j];
			}
		}
		return print_out;
	}

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
	}
	public static void main(String[] args) {

		board myBoard=new board("1 B kqbnrppp.....pp.....PPPPPRNBQK");
		myBoard.print();
		myBoard.move(new Move("b6 c5"));
		myBoard.print();
	}
}