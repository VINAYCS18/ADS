import java.util.*;

public class OpenEndedQuestion2 {
	static void print(int x) {
		int n = x;
		boolean vis[] = new boolean[10];
		while(n>0) {
			if(vis[n%10]==true)
				return;
			vis[n%10] = true;
			n/=10;
		}
		System.out.print(x+" ");
	}
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter lower and upper limit");
		int lower = scan.nextInt();
		int upper = scan.nextInt();
		for(int i =lower;i<=upper;i++) {
			print(i);
		}
		System.out.println();
	}

}
