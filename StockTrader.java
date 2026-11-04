import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class StockTrader {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("msft_2000_2010.txt"));
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
}
