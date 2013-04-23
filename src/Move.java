//Copyright © 2013 Matthias Karl



public class Move {
	Square from;;
	Square to;
	public Move(String fields){
		String[] splitResult = fields.split(" ");
		this.from = new Square(splitResult[0]);
		this.to = new Square(splitResult[1]);
	}
	
	public Move(Square from_sq, Square to_sq){
		this.from = from_sq;
		this.to = to_sq;
	}
	
	public String toString() {
		return from + "-" + to;
	}
	
	public static void main(String[] args) {
		Move myMove = new Move("c4 c5");
		System.out.println(myMove);
	}
}

