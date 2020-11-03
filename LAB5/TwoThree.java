import java.util.Scanner;

public class TwoThree
{
    BTreeNode root;
    int t;


    static class BTreeNode {
        int[] keys; // An array of keys
        int t; // Minimum degree (defines the range for number of keys)
        BTreeNode[] C; // An array of child pointers
        int n; // Current number of keys
        boolean leaf; // Is true when node is leaf. Otherwise false

        // Constructor
        BTreeNode(int t, boolean leaf) {
            this.t = t;
            this.leaf = leaf;
            this.keys = new int[2 * t - 1];
            this.C = new BTreeNode[2 * t];
            this.n = 0;
        }

        // A function to traverse all nodes in a subtree rooted with this node
        public void traverse() {

            // There are n keys and n+1 children, travers through n keys
            // and first n children
            int i = 0;
            for (i = 0; i < this.n; i++) {

                // If this is not leaf, then before printing key[i],
                // traverse the subtree rooted with child C[i].
                if (!this.leaf) {
                    C[i].traverse();
                }
                System.out.print(keys[i] + " ");
            }

            // Print the subtree rooted with last child
            if (!leaf)
                C[i].traverse();
        }

        // A function to search a key in the subtree rooted with this node.
        BTreeNode search(int k) { // returns NULL if k is not present.

            // Find the first key greater than or equal to k
            int i = 0;
            while (i < n && k > keys[i])
                i++;

            // If the found key is equal to k, return this node
            if (keys[i] == k)
                return this;

            // If the key is not found here and this is a leaf node
            if (leaf)
                return null;

            // Go to the appropriate child
            return C[i].search(k);

        }

        public void insertNonFull(int k)
        {

            int i=n-1;
            if(leaf)
            {
                while(i>=0&&keys[i]>k)
                {
                    keys[i+1]=keys[i];
                    i--;
                }

                keys[i+1] = k;
                n = n+1;
            }

            else // If this node is not leaf 
            {
                // Find the child which is going to have the new key 
                while (i >= 0 && keys[i] > k)
                    i--;

                // See if the found child is full 
                if (C[i+1].n == 2*t-1)
                {
                    // If the child is full, then split it 
                    splitChild(i+1, C[i+1]);

                    // After split, the middle key of C[i] goes up and 
                    // C[i] is splitted into two.  See which of the two 
                    // is going to have the new key
                    if (keys[i+1] < k)
                        i++;
                }
                C[i+1].insertNonFull(k);
            }


        }

        private void splitChild(int i, BTreeNode y)
        {
            BTreeNode z = new BTreeNode(y.t, y.leaf);
            z.n = t - 1;

            // Copy the last (t-1) keys of y to z
            for (int j = 0; j < t-1; j++)
                z.keys[j] = y.keys[j+t];

            // Copy the last t children of y to z
            if (!y.leaf)
            {
                for (int j = 0; j < t; j++)
                    z.C[j] = y.C[j+t];
            }

            // Reduce the number of keys in y
            y.n = t - 1;

            // Since this node is going to have a new child,
            // create space of new child
            for (int j = n; j >= i+1; j--)
                C[j+1] = C[j];

            // Link the new child to this node
            C[i+1] = z;

            // A key of y will move to this node. Find the location of
            // new key and move all greater keys one space ahead
            for (int j = n-1; j >= i; j--)
                keys[j+1] = keys[j];

            // Copy the middle key of y to this node
            keys[i] = y.keys[t-1];

            // Increment count of keys in this node
            n = n + 1;



        }
    }

    TwoThree(int t) {
        this.root = null;
        this.t = t;
    }

    public void traverse() {
        if (this.root != null)
            this.root.traverse();
        System.out.println();
    }

    // function to search a key in this tree
    public BTreeNode search(int k) {
        if (this.root == null)
            return null;
        else
            return this.root.search(k);
    }



    public void insert(int k)
    {
        if(root==null)
        {
            root=new BTreeNode(t,true);
            root.keys[0]=k;
            root.n=1;
        }
        else
        {
            if(2*t-1== root.n)
            {
                BTreeNode newRoot=new BTreeNode(t,false);
                newRoot.C[0]=root;

                newRoot.splitChild(0,root);
                int i=0;
                if(newRoot.keys[0]<k)
                {
                    i++;
                }

                newRoot.C[i].insertNonFull(k);
                root=newRoot;
            }
            else
                root.insertNonFull(k);
        }

    }

    private boolean remove(Node current, T element) {
        boolean ifRemoved = true;

        // The case when we are at the deepest level of the tree, but we did not find the element (it does not exist)
        if (current == null) {
            ifRemoved = false;
            return false;
        }

        // Recursive case, we still find the element to delete
        else {

            if (!current.getLeftElement().equals(element)) {

                // If there is no element on the right or the element to be deleted is smaller than the right element
                if (current.getRightElement() == null || current.getRightElement().compareTo(element) == ROOT_IS_BIGGER) {

                    // The left element is larger than the element to be deleted, so we go through the left child element
                    if (current.getLeftElement().compareTo(element) == ROOT_IS_BIGGER) {
                        ifRemoved = remove(current.leftChild, element);
                    }

                    // Otherwise -> try to remove the middle child
                    else {
                        ifRemoved = remove(current.middleChild, element);
                    }

                } else {

                    // If the element to be deleted is not equal to the desired element, we pass the right child
                    if (!current.getRightElement().equals(element)) {
                        ifRemoved = remove(current.rightChild, element);
                    }

                    // Otherwise, we found an element
                    else {

                        // *** Situation 1 ***
                        // The element is equal to the right element of the sheet, so we just delete it
                        if (current.isLeaf()) {
                            current.setRightElement(null);
                        }

                        // *** Situation 2 ***
                        // We found the element, but it is not in the sheet
                        else {

                            // We get the min element of the right branch,
                            // delete it from the current position and place it where we found the element to delete.
                            T replacement = (T) current.getRightNode().replaceMin();
                            current.setRightElement(replacement);
                        }
                    }
                }
            }

            // The left element is equal to the element to be deleted.
            else {

                // *** Situation 1 ***
                if (current.isLeaf()) {

                    // The left element, the element to delete, is replaced by the right element
                    if (current.getRightElement() != null) {
                        current.setLeftElement(current.getRightElement());
                        current.setRightElement(null);

                    }

                    // If there is no element on the right, then balancing is required
                    else {
                        current.setLeftElement(null); // Release the node
                        return true;
                    }
                }

                // *** Situation 2 ***
                else {

                    // Move the "max" element of the left branch, where we found the element
                    T replacement = (T) current.getLeftNode().replaceMax();
                    current.setLeftElement(replacement);
                }
            }
        }

        // The lower level must be balanced
        if (!current.isBalanced()) {
            current.reBalance();

        } else if (!current.isLeaf()) {
            boolean isBalanced = false;

            while (!isBalanced) {
                if (current.getRightNode() == null) {

                    // A critical case of situation 2 for the left child
                    if (current.getLeftNode().isLeaf() && !current.getMidNode().isLeaf()) {
                        T replacement = (T) current.getMidNode().replaceMin();
                        T tempLeft = (T) current.getLeftElement();
                        current.setLeftElement(replacement);

                        add(tempLeft);
                    }

                    // A critical case of situation 2 for the right child
                    else if (!current.getLeftNode().isLeaf() && current.getMidNode().isLeaf()) {
                        if (current.getRightElement() == null) {
                            T replacement = (T) current.getLeftNode().replaceMax();
                            T tempLeft = (T) current.getLeftElement();
                            current.setLeftElement(replacement);

                            add(tempLeft);
                        }
                    }
                }

                if (current.getRightNode() != null) {
                    if (current.getMidNode().isLeaf() && !current.getRightNode().isLeaf()) {
                        current.getRightNode().reBalance();
                    }

                    if (current.getMidNode().isLeaf() && !current.getRightNode().isLeaf()) {
                        T replacement = (T) current.getRightNode().replaceMin();
                        T tempRight = (T) current.getRightElement();
                        current.setRightElement(replacement);

                        add(tempRight);
                    } else {
                        isBalanced = true;
                    }
                }

                if (current.isBalanced()) isBalanced = true;
            }
        }
        return ifRemoved;
    }

    public static void main(String ar[])
    {
        Scanner x=new Scanner(System.in);
        TwoThree ob=new TwoThree(2);
        int ch=0;
        System.out.println("Enter number of elements");
        int n=x.nextInt();
        for(int i=0;i<n;i++)
            ob.insert(x.nextInt());

        System.out.println("Traversal after deletion");
        ob.traverse();

        System.out.println("Enter value to be deleted");
        int v=x.nextInt();
        ob.delete(v);
        System.out.println("Traversal after deletion");
        ob.traverse();
    }
}
