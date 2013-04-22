//Copyright © 2013 Matthias Karl

public class Square{
		public int col = 0;
		public int row = 0;
		public char[] arr;
		public char spalte;
		public int zeile;
		public int int_spalte;
		

		public Square(String turn){
			arr = turn.toCharArray();
			
			int_spalte = (int)arr[0] - (int)'a';
			
			this.col = int_spalte;
			this.row = arr[1];
			
		}
		
		public Square(int c, int r){
			this.row = r;
			this.col = c;
		}
		
		public String toString(){
			return "" + (char)(col + (int)'a') + (char)row;
		}
		
		public static void main(String[] args){
			String turn = "d2";
			Square mysquare = new Square(turn);
			System.out.println(mysquare);
		}
}
