import java.util.*;

public class OpenEndedQuestion1 {
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int test = scan.nextInt();
		while(test-->0) {
			int n = scan.nextInt();
			int a[] = new int[n];
			for(int i =0;i<n;i++) {
				a[i] = scan.nextInt();
			}
			Arrays.sort(a);
			for(int i =0;i<a.length-2;i++) {
				System.out.print(a[i]+" ");
			}
			System.out.println();
		}
	}

}
