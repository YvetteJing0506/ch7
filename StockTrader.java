//Q1: Someone who bought microsoft stock in 2000 likely purchased the stock near 
	//its peak price and experienced significant loss due to the drop.
//Q2: The economic collapse (the Great Recession) heappened during the year of 2008; 
    //It is triggered by the collapse of the U.S. housing and subprime mortgage markets; 
    //This makes sense since and economic collapse will cause stock to go down. 

//Q1: dipBuyer had the most times that it gained most, and it has never lost money; 
	//No. Each method performed best on different stock data files.
//Q2: For 

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class StockTrader {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("tiny.txt"));
        String symbol = in.next();
        int n = in.nextInt();
        int[] years = new int[n];
        int[] months = new int[n];
        double[] prices = new double[n];
		loadData (in, years, months, prices);
		printData(years, months, prices);
		int low = indexOfLowest(prices);
		System.out.println(symbol + " Lowest: $" + prices[low] + " in " 
						   + monthName(months[low]) + " " + years[low]);
		int high = indexOfHighest(prices);
		System.out.println(symbol + " Highest: $" + prices[high] + " in " 
						   + monthName(months[high]) + " " + years[high]);
		double result = simulate(years, months, prices);
		System.out.printf("Final value: $%,.2f%n", result);

    }
    public static void loadData(Scanner in, int[] years, int[] months, double[] prices) {
		for (int i=0; i<years.length; i++) {
			years[i] = in.nextInt();
			months[i] = in.nextInt();
			prices[i] = in.nextDouble();
		}
	}
	public static String monthName(int month) {
		String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", 
							   "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"}; 
		return monthNames[month-1];
	}
	public static void printData(int[] years, int[] months, double[] prices) {
		for (int i=0; i<months.length; i++) {
			System.out.print(monthName(months[i]) + " " + years[i] + ": $");
			System.out.printf("%.2f", prices[i]);
			System.out.println();
		} 
	}
	public static int indexOfLowest(double[] prices) {
		int index=0;
		double num=prices[0];
		for (int i=0; i<prices.length; i++) {
			if (prices[i] < num) {
				index = i;
				num = prices[i];
			}
		} return index;
	}
	public static int indexOfHighest(double[] prices) {
		int index=0;
		double num=0;
		for (int i=0; i<prices.length; i++) {
			if (prices[i] > num) {
				index = i;
				num = prices[i];
			}
		} return index;
	}
	
	public static final double START_CASH = 10000.0;
	public static final double FEE = 10.0;

	public static double simulate(int[] years, int[] months, double[] prices) {
		double cash = START_CASH;
		int shares = 0;
		double boughtAt = 0.0;

		for (int i = 0; i < prices.length; i++) {
			double[] history = Arrays.copyOf(prices, i + 1);
			String action = decide(history, shares > 0, boughtAt);

			if (action.equals("BUY") && shares == 0) {
				int canBuy = (int) ((cash - FEE) / prices[i]);
				if (canBuy > 0) {
					shares = canBuy;
					cash = cash - shares * prices[i] - FEE;
					boughtAt = prices[i];
					System.out.printf("%s %d: BUY %d shares at $%.2f%n",
							monthName(months[i]), years[i], shares, prices[i]);
				}
			} else if (action.equals("SELL") && shares > 0) {
				cash = cash + shares * prices[i] - FEE;
				System.out.printf("%s %d: SELL %d shares at $%.2f%n",
						monthName(months[i]), years[i], shares, prices[i]);
				shares = 0;
				boughtAt = 0.0;
			}
		}
		return cash + shares * prices[prices.length - 1];
	}
	public static String decide(double[] history, boolean holding, double boughtAt) {
		return myStrategy(history, holding, boughtAt); //change to switch strategies
	}
	public static String buyAndHold(double[] history, boolean holding, double boughtAt) {
		return "BUY";
	}
	public static String dipBuyer(double[] history, boolean holding, double boughtAt) {
		if (history.length<3) {
			return "HOLD";
		} if (holding==false) {
			if (history[history.length-1]<history[history.length-2]
				&& history[history.length-2]<history[history.length-3]) {
				return "BUY";
			}
		} else if (holding==true) {
			if (history[history.length-1]/boughtAt>=1.10) {
				return "SELL";
			} 
		} return "HOLD";
	}
	public static String movingAverage(double[] history, boolean holding, double boughtAt) {
		if (history.length<12) {
			return "HOLD";
		} if (holding==false) {
			double sum = 0;
			for (int i=0; i<history.length; i++) {
				sum+=history[i];
			} if (history[history.length-1]>sum/12.00) {
				return "BUY";
			}
		} if (holding==true) { 
			double sum = 0;
			for (int i=0; i<history.length; i++) {
				sum+=history[i];
			} if (history[history.length-1]<sum/12.00) {
				return "BUY";
			}
		} return "HOLD";
	}
	public static String myStrategy(double[] history, boolean holding, double boughtAt) {
		if (history.length<2) {
			return "HOLD";
		} if (holding==false) {
			if (history[history.length-1]/history[history.length-2]>=1.03) {
				return "BUY";
			} 
		} if (holding==true) {
			if (history[history.length-1]/history[history.length-2]<0.97 ) {
				return "SELL";
			} 
		} return "HOLD";
	}
}
