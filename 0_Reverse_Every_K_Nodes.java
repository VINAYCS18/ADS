/*
  Given a linked list of size N, write a program to reverse every k nodes (where k is an input to the function) in the linked list.
Sample Input 1:
Linked List: 1->2->2->4->5->6->7->8
K = 4
Output: 4 2 2 1 8 7 6 5
*/
import java.util.Scanner;

class LinkedList 
{ 
	Node head; 

	class Node 
	{ 
		int data; 
		Node next; 
		Node(int d) {data = d; next = null; } 
	} 

	Node reverse(Node head, int k) 
	{ 
	Node current = head; 
	Node next = null; 
	Node prev = null; 
		
	int count = 0; 

	while (count < k && current != null) 
	{ 
		next = current.next; 
		current.next = prev; 
		prev = current; 
		current = next; 
		count++; 
	} 

	if (next != null) 
		head.next = reverse(next, k); 

	return prev; 
	}					 

					

	public void push(int new_data) 
	{ 
		Node new_node = new Node(new_data); 

		new_node.next = head; 

		head = new_node; 
	} 

	void printList() 
	{ 
		Node temp = head; 
		while (temp != null) 
		{ 
		System.out.print(temp.data+" "); 
		temp = temp.next; 
		} 
		System.out.println(); 
	} 

	public static void main(String args[]) 
	{ 
		Scanner scan = new Scanner(System.in);
		LinkedList llist = new LinkedList(); 
		while(true) {
			System.out.println("Enter 1 to add element in list\nEnter 2 to printlist\nEnter 3 to reverse every k elemnts");
			int m = scan.nextInt();
			switch(m) {
				case 1: System.out.println("Enter the element");
						int x = scan.nextInt();
						llist.push(x); 
						break;
			 

				case 2:		System.out.println("The Reversed list"); 
							llist.printList(); 
							break;

				case 3: 	System.out.println("Enter K");
							int K = scan.nextInt();
							llist.head = llist.reverse(llist.head, K); 
							break;
				default:  System.exit(0);

			 
			}
		}
		

	} 
} 


