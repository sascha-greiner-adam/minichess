
public class board {
	char[][]field={{'R','N','B','Q','K'},{'P','P','P','P','P'},{'.','.','.','.','.'},{'.','.','.','.','.'},{'p','p','p','p','p'},{'k','q','b','n','r'}};
	int moveNum=1;
	char onMove='B';
	public board() {
	}
	
	public String toString() {
		
		return "Bla";
		
	}

	public void print() {
		String print_out="";
		for (int i=5; i>=0; i--) {
			print_out="";
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
