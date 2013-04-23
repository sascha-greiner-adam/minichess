//Copyright © 2013 Matthias Karl

public class Square{
		public int col = 0;
		public int row = 0;

		//Constructor for the Objects Square with a parameter for the Move
		// as an String like c2
		public Square(String turn){

				char[] arr = turn.toCharArray();
				
				this.col = (int)(arr[0] - 'a') + 1;
				this.row = (int)(arr[1] - '0');

		
		}
		
		//Constructor for the Objects Square with two parameters for the Move
		// as two integer like (1, 2)
		public Square(int c, int r){
			this.row = r;
			this.col = c;
		}
		
		// Converts the given square fields into a string
		public String toString(){
			return "" + (char)(col + (int)'a' - 1) + (int)(row);
		}
		
		public static void main(String[] args){
			String turn = "d2";
			Square mysquare = new Square(turn);
			System.out.println(mysquare.col + " - " + mysquare.row);
		}
}
