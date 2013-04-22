
public class board {

	char[][]board;
	int moveNum=1;
	char onMove='B';
	
	public void board() {
		for(int i=0;i<4;i++) {
			board[i][1]='P';
			board[i][4]='p';
			switch (i) {
			case 0:	board[i][0]='R';
					board[i][5]='k';
					break;
			case 1:	board[i][0]='N';
					board[i][5]='q';
					break;
			case 2:	board[i][0]='B';
					board[i][5]='b';
					break;
			case 3:	board[i][0]='Q';
					board[i][5]='n';
					break;
			case 4:	board[i][0]='K';
					board[i][5]='r';
					break;
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}