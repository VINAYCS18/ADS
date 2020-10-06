import java.util.*;
class SkipListNode 
{
    int value;
    static int counter;
    SkipListNode next, prev, up, down;
    
    SkipListNode(int data)
    {
        value = data;
        next = prev = up = down = null;
    }
}

class SkipList
{        
    SkipListNode p, container, head[], tail[];
    int i, limit, max_height;
    
    SkipList(int n)
    {
        i = max_height = 0;
        limit = (int)(5*Math.log(n));
        head = new SkipListNode[limit];
        tail = new SkipListNode[limit];  
    }
    
    SkipListNode addElement(int input, int height, SkipListNode down_node)
    {
        SkipListNode node = new SkipListNode(input);
        p = head[height];
        while(p.next.value < input)
        {
            p = p.next;
        }
        node.down = down_node;
        node.next = p.next;
        node.prev = p;
        p.next.prev = node;
        p.next = node;
        // Let 0 represents head and 1 represents tail
        Random rand = new Random();
        int stage = rand.nextInt(2);
        if(stage == 0 && SkipListNode.counter < limit)
        {
            if(head[height+1] == null)
            {
                genLevel();
                max_height = height+1;
            }
            node.up = addElement(input, height+1, node);
            SkipListNode.counter++;
        }
        return node;
    }
    
    void add(int input)
    {
        container = addElement(input, 0, null);
        SkipListNode.counter = 0;
    }
    
    void display(int height)
    {
        System.out.print("Level " + height +" :-    ");
        if(head[height] != null)
        {
            p = head[height].next;
            while(p != tail[height])
            {
                System.out.print(p.value +"  ");
                p = p.next;
            }
            System.out.println();
        }
        else
            System.out.println("This level doesn't exist");
    }
    
    boolean search(int key)
    {
        p = head[max_height];
        System.out.println(max_height);
        while(p != null)
        {
            if(p.next.value < key)
                p = p.next;
            else if(p.next.value > key)
                p = p.down;
            else
            {
                p = p.next;
                return true;
            }
        }
        return false;
    }
    
    void delete(int key)
    {
        if(!search(key))
            System.out.println("Deletion is not possible since key to be deleted does not exist");
    
        else
        {
            SkipListNode previous;
            while(p != null)
            {
                previous = p.prev;
                previous.next = p.next;
                p.next.prev = previous;
                p.next = p.prev = null;
                p = p.down;
            }
        }
    }
    
    void genLevel()
    {
        head[i] = new SkipListNode(-99999);
        tail[i] = new SkipListNode(99999);
        head[i].next = tail[i];
        tail[i].prev = head[i];
        i++;
    }
    
}

public class SkipListMain
{
    public static void main(String args[])
    {
        SkipList list = new SkipList(7);
        list.genLevel();
        list.add(10);
        list.add(24);
        list.add(58);
        list.add(31);
        list.add(75);
        list.add(60);
        list.add(36);
        list.display(3);
        list.display(2);
        list.display(1);
        list.display(0);
        System.out.println("31 is present in the Skip List :- " + list.search(31));
        System.out.println("15 is present in the Skip List :- " + list.search(15));
        list.delete(31);
        list.display(3);
        list.display(2);
        list.display(1);
        list.display(0);
    }
}
