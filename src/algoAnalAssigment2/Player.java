package algoAnalAssigment2;

public class Player {
	String name;
	int position;
	int rating;
	int price;
	double raitio;

	public Player(String name, int position, int rating, int price) {
		this.name = name;
		this.position = position;
		this.rating = rating;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public double getRaitio() {
		return raitio;
	}

	public void setRaitio(double raitio) {
		this.raitio = raitio;
	}

}
