package algoAnalAssigment2;

import java.io.*;
import java.util.Scanner;

public class Test {

	static Player[] Player = new Player[100];

	private static void sort(Player[] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				if (arr[i].raitio > arr[j].raitio) {
					Player temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;
				}
			}
		}

	}

	static void readPlayer() throws IOException {

		FileInputStream fileRead = new FileInputStream("input.txt");
		DataInputStream data = new DataInputStream(fileRead);
		BufferedReader read = new BufferedReader(new InputStreamReader(data));
		int numOfPlayers = 0;

		String line, name = null;
		int position = 0, rating = 0, price = 0;
		String[] inputs = null;

		while ((line = read.readLine()) != null) {
			// Every line of the file was assigned to 'line' at every turn.
			inputs = line.split(",");
			if (inputs.length == 4) {
				for (int i = 0; i < inputs.length; i++) {
					switch (i) {
					case 0:
						name = inputs[i];
						break;
					case 1:
						position = Integer.parseInt(inputs[i]);
						break;
					case 2:
						rating = Integer.parseInt(inputs[i]);
						break;
					case 3:
						price = Integer.parseInt(inputs[i]);
						break;

					}

				}
				Player[numOfPlayers] = new Player(name, position, rating, price);
				numOfPlayers++;

			}

		}
		System.out.println("File is read.");

		read.close();
	}

	public static Player[] findPlayers(int n, int k) { // find first k players in first n position in player array
		Player[] tempPlayer = new Player[n * k];
		int count2 = 0;
		for (int i = 1; i <= n; i++) {
			int count = 0;
			for (int l = 0; l < Player.length; l++) {
				if (Player[l].position == i) {
					if (count >= k)
						break;
					tempPlayer[count2] = Player[l];
					count2++;
					count++;
				}

			}

		}

		return tempPlayer;
	}

	public static void Greedy_Approach(int x, int n, int k) {
		System.out.println("Signed players: ");
		Player[] tempPlayer = new Player[n * k];
		int totalRating = 0;
		int totalPrice = 0;
		tempPlayer = findPlayers(n, k);
		for (int i = 0; i < tempPlayer.length; i++)
			tempPlayer[i].setRaitio((double) tempPlayer[i].getRating() / (double) tempPlayer[i].getPrice());
		sort(tempPlayer);
		int temp[] = new int[n];
		for (int i = 0; i < temp.length; i++)
			temp[i] = 0;
		int count = 0;
		for (int i = 0; i < tempPlayer.length; i++) {
			if (count == n) {
				break;

			} else {
				int count2 = 0;
				for (int j = 0; j < temp.length; j++) {
					if (tempPlayer[i].getPosition() != temp[j])
						count2++;
				}
				if (count2 == temp.length && totalPrice <= x) {
					totalPrice += tempPlayer[i].getPrice();
					if (totalPrice > x) {
						totalPrice -= tempPlayer[i].getPrice();
					} else {
						System.out.println(tempPlayer[i].getName() + "     Position: " + tempPlayer[i].getPosition());
						totalRating += tempPlayer[i].getRating();
						temp[count] = tempPlayer[i].getPosition();
						count++;

					}

				}

			}

		}
		System.out.println("The maximum value of Rating is " + totalRating);
		System.out.println("Total money spend: " + totalPrice);

	}

	public static void Dynamic_Programming_Approach(int x, int n, int k) {
		Player[] tempPlayer = new Player[n * k];
		int totalRating = 0;
		int totalPrice = 0;
		int count2 = 0;
		tempPlayer = findPlayers(n, k);
		Player[][] Players = new Player[n][k];
		for (int i = 0; i < Players.length; i++) {
			int count = 0;
			while (count != k) {
				Players[i][count] = tempPlayer[count2];
				count++;
				count2++;
			}
		}

		dynamic(Players, n, k, x);
	}

	public static void dynamic(Player[][] p, int N, int P, int X) {
		int[][] v = new int[N + 1][X];

		int[][] who = new int[N + 1][X];

		int k;

		for (int x = 0; x < X; x++) {
			v[N][x] = 0;
			who[N][x] = 0;
			for (k = 0; k < P; k++) {
				if (p[N - 1][k].getPrice() <= x && p[N - 1][k].getRating() > v[N][x]) {
					v[N][x] = p[N - 1][k].getRating();
					who[N][x] = k;
				}
			}
		}
		for (int i = N - 2; i > 1; i--) {
			for (int x = 0; x < X; x++) {
				v[i][x] = v[i + 1][x];
				who[i][x] = 0;
				for (k = 0; k < P; k++) {
					if (p[i][k].getPrice() <= x && v[i + 1][x - p[i][k].getPrice()] + p[i][k].getRating() > v[i][x]) {
						v[i][x] = v[i + 1][x - p[i][k].getPrice()] + p[i][k].getRating();

					}
					who[i][x] = k;
				}
			}
		}

		int amt = X;
		System.out.println(v[0][X - 1]);

		for (int i = 0; i < N; i++) {
			k = who[i + 1][X - 1];
			if (k != 0) {
				System.out.println("players: ");
				System.out.println(p[i][k].getName() + "--" + p[i][k].getPosition());
				amt = amt - p[i][k].getPrice();
			}

		}
		int spend = X - amt;
		System.out.println("total spend money: " + spend);

	}

	public static void main(String[] args) throws IOException {
		readPlayer();
		System.out.print("Enter the amount to spend(X): ");
		Scanner reader = new Scanner(System.in);
		int x = reader.nextInt();
		System.out.print("Enter the number of the positions (N): ");
		int n = reader.nextInt();
		System.out.print("Enter the number of available players for each position (K): ");
		int k = reader.nextInt();
		long startTime = System.nanoTime();
		Greedy_Approach(x, n, k);
		long totalTime = System.nanoTime() - startTime;
		System.out.println("total time in nanosecond: " + totalTime);

	}

}
