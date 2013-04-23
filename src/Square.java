//Copyright © 2013 Matthias Karl

public class Square{
		public int col = 0;
		public int row = 0;

		public Square(String turn){
			char[] arr = turn.toCharArray();
			
			this.col = (int)(arr[0] - 'a') + 1;
			this.row = (int)(arr[1] - '0');
			
		}
		
		public Square(int c, int r){
			this.row = r;
			this.col = c;
		}
		
		public String toString(){
			return "" + (char)(col + (int)'a' - 1) + (int)(row);
		}
		
		public static void main(String[] args){
			String turn = "d2";
			Square mysquare = new Square(turn);
			System.out.println(mysquare.col + " - " + mysquare.row);
		}
}
