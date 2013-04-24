//Copyright © 2013 Sascha Greiner-Adam, Matthias Karl



public class Move {
	Square from;;
	Square to;
	
	//Constructor for creating an Move object. You have to
	//insert a string like "c1 c2"
	public Move(String fields){	
			String[] splitResult = fields.split("-");
			this.from = new Square(splitResult[0]);
			this.to = new Square(splitResult[1]);
	}
	
	public Move(Square from_sq, Square to_sq){
		this.from = from_sq;
		this.to = to_sq;
	}
	
	// Converts the given square fields into a string
	public String toString() {
		return from + "-" + to;
	}
	/*
	public static void main(String[] args) {
		try{
			Move myMove = new Move("c4 c5");
			System.out.println(myMove);
		}catch(NullPointerException g){
			System.out.println("False Input given: " + g.getMessage());
		}
	}*/
}

