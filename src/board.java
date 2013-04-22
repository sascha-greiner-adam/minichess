
public class board {
	char[][]field={{'.','.','.','.','.','.'},{'.','.','.','.','.','.'},{'.','.','.','.','.','.'},{'.','.','.','.','.','.'},{'.','.','.','.','.','.'}};
	int moveNum=1;
	char onMove='B';
	public board() {
		for(int i=0;i<4;i++) {
			field[i][1]='P';
			field[i][4]='p';
			switch (i) {
			case 0:	field[i][0]='R';
					field[i][5]='k';
					break;
			case 1:	field[i][0]='N';
					field[i][5]='q';
					break;
			case 2:	field[i][0]='B';
					field[i][5]='b';
					break;
			case 3:	field[i][0]='Q';
					field[i][5]='n';
					break;
			case 4:	field[i][0]='K';
					field[i][5]='r';
					break;
			}
		}
	}
	
	public String toString() {
		
		return "Bla";
		
	}

	public void print() {
		String print_out="";
		for (int i=0; i<4; i++) {
			for (int j=0; j<5; j++) {
				print_out=print_out+field[i][j];
			}
			System.out.println(print_out);
		}
	}
	public static void main(String[] args) {
		
		board myBoard=new board();
		myBoard.print();
	}
}
