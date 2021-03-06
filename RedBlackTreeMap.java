import java.util.*;

// A Map ADT structure using a red-black tree, where keys must implement
// Comparable.
// The key type of a map must be Comparable. Values can be any type.
public class RedBlackTreeMap<TKey extends Comparable<TKey>, TValue> {
	// A Node class.
	private class Node {
		private TKey mKey;
		private TValue mValue;
		private Node mParent;
		private Node mLeft;
		private Node mRight;
		private boolean mIsRed;

		// Constructs a new node with NIL children.
		public Node(TKey key, TValue data, boolean isRed) {
			mKey = key;
			mValue = data;
			mIsRed = isRed;

			mLeft = NIL_NODE;
			mRight = NIL_NODE;
		}

		@Override
		public String toString() {
			return mKey + " : " + mValue + " (" + (mIsRed ? "red)" : "black)");
		}
	}

	private Node mRoot;
	private int mCount;

	// Rather than create a "blank" black Node for each NIL, we use one shared
	// node for all NIL leaves.
	private final Node NIL_NODE = new Node(null, null, false);

	//////////////////// I give you these utility functions for free.

	// Get the # of keys in the tree.
	public int getCount() {
		return mCount;
	}

	// Finds the value associated with the given key.
	public TValue find(TKey key) {
		Node n = bstFind(key, mRoot); // find the Node containing the key if any
		if (n == null || n == NIL_NODE)
			throw new RuntimeException("Key not found");
		return n.mValue;
	}

	/////////////////// You must finish the rest of these methods.

	// Inserts a key/value pair into the tree, updating the red/black balance
	// of nodes as necessary. Starts with a normal BST insert, then adjusts.
	public void add(TKey key, TValue data) {
		Node n = new Node(key, data, true); // nodes start red

		// normal BST insert; n will be placed into its initial position.
		// returns false if an existing node was updated (no rebalancing needed)
		boolean insertedNew = bstInsert(n, mRoot);
		if (!insertedNew)
			return;

		// check cases 1-5 for balance violations.
		checkBalance(n);
	}

	// Applies rules 1-5 to check the balance of a tree with newly inserted
	// node n.
	private void checkBalance(Node n) {
		if (n == mRoot) {
			// case 1: new node is root.
			n.mIsRed = false;
			return;
		}
		// handle additional insert cases here.
		else if (n.mParent.mIsRed == false) {	
			// case 2: P is black
			return;
		}
		else if (n.mParent.mIsRed == true && getUncle(n).mIsRed == true){
			// case 3: P & U are red
			n.mParent.mIsRed = false;
			getUncle(n).mIsRed = false;
			getGrandparent(n).mIsRed = true;
			//checkBalance(getGrandparent(n));
		}
		// case 4: n is lr or rl grandchild of G
		else if (n == n.mParent.mRight && n.mParent == getGrandparent(n).mLeft){
			singleRotateLeft(n.mParent);
			n = n.mLeft;
		}
		else if(n == n.mParent.mLeft && n.mParent == getGrandparent(n).mRight){
			singleRotateRight(n.mParent);
			n = n.mRight;
		}
		// case 5: n is ll or rr grandchild of G
		Node g = getGrandparent(n);
		if (n == n.mParent.mLeft){
			singleRotateRight(getGrandparent(n));
		}
		else{
			singleRotateLeft(getGrandparent(n));
		}
		n.mParent.mIsRed = false;
		g.mIsRed = true;
	}

	// Returns true if the given key is in the tree.
	public boolean containsKey(TKey key) {
		// using at most three lines of code, finish this method.
		// HINT: write the bstFind method first.
		boolean found = bstFind(key, mRoot) != null ? true : false; 
		return found;
	}

	// Prints a pre-order traversal of the tree's nodes, printing the key, value,
	// and color of each node.
	public void printStructure() {
		preOrderPrint(mRoot);
	}

	private void preOrderPrint(Node n){
		if(n == null){
			return;
		}
		System.out.println(n.toString());
		preOrderPrint(n.mLeft);
		preOrderPrint(n.mRight);
	}

	// Returns the Node containing the given key. Recursive.
	private Node bstFind(TKey key, Node currentNode) {
		// write this method. Given a key to find and a node to start at,
		// proceed left/right from the current node until finding a node whose
		// key is equal to the given key.
		if (currentNode == null || currentNode.mKey == key) {
			return currentNode;
		}
		if (currentNode.mKey != key) {
			return bstFind(key, currentNode.mRight);
		}
		return bstFind(key, currentNode.mLeft);
	}

	//////////////// These functions are needed for insertion cases.

	// Gets the grandparent of n.
	private Node getGrandparent(Node n) {
		return n.mParent.mParent;
	}

	// helps get the uncle of n
	private Node getSibling(Node n) {
		if(n.mParent == null){
			return null;
		}
		if(n == n.mParent.mLeft){
			return n.mParent.mRight;
		}
		else{
			return n.mParent.mLeft;
		}
	}

	// Gets the uncle (parent's sibling) of n.
	private Node getUncle(Node n) {
		return getSibling(n.mParent);
	}

	// Rotate the tree right at the given node.
	private void singleRotateRight(Node n) {
		Node l = n.mLeft, lr = l.mRight, p = n.mParent;
		n.mLeft = lr;
		lr.mParent = n;
		l.mRight = n;
		n.mParent = l;

		if (p == null) { // n is root
			mRoot = l;
		} else if (p.mLeft == n) {
			p.mLeft = l;
		} else {
			p.mRight = l;
		}

		l.mParent = p;
	}

	// Rotate the tree left at the given node.
	private void singleRotateLeft(Node n) {
		//do a single left rotation (AVL tree calls this a "rr" rotation)
		// at n.
		Node rt = n.mRight, p = n.mParent;
		n.mRight = rt.mLeft;
		rt.mLeft = n;
		n.mParent = rt;

		if(n.mRight == null){
			n.mRight.mParent = n;
		}
		if(p != null){
			if(n == p.mLeft){
				p.mLeft = rt;
			}
			else if(n == p.mRight){
				p.mRight = rt;
			}
		}
		rt.mParent = p;

	}

	// This method is used by insert. It is complete.
	// Inserts the key/value into the BST, and returns true if the key wasn't
	// previously in the tree.
	private boolean bstInsert(Node newNode, Node currentNode) {
		if (mRoot == null) {
			// case 1
			mRoot = newNode;
			return true;
		} else {
			int compare = currentNode.mKey.compareTo(newNode.mKey);
			if (compare < 0) {
				// newNode is larger; go right.
				if (currentNode.mRight != NIL_NODE) {
					return bstInsert(newNode, currentNode.mRight);
				} else {
					currentNode.mRight = newNode;
					newNode.mParent = currentNode;
					mCount++;
					return true;
				}
			} else if (compare > 0) {
				if (currentNode.mLeft != NIL_NODE) {
					return bstInsert(newNode, currentNode.mLeft);
				} else {
					currentNode.mLeft = newNode;
					newNode.mParent = currentNode;
					mCount++;
					return true;
				}
			} else {
				// found a node with the given key; update value.
				currentNode.mValue = newNode.mValue;
				return false; // did NOT insert a new node.
			}
		}
	}
}
