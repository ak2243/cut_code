package cutcode;

import java.util.ArrayList;

public class BSTree<T extends Comparable<T>> {
	private BSTNode<T> root;

	//Keys used for traversal method
	public static final int PREORDER = 0;
	public static final int POSTORDER = 1;
	public static final int INORDER = 2;

	public void add(T data) {
		if (root == null) {//Account for if there are no nodes in the tree
			root = new BSTNode<T>(data);
			return;
		}
		BSTNode<T> current = root;
		BSTNode<T> parent = current;
		int direction = 0;
		while (current != null) {//Finds the current spot and the parent, as well as which side 
			parent = current;
			int compare = data.compareTo(current.data);
			if (compare <= 0) {//Go left 
				current = current.left;
				direction = -1;
			} else {//Go right
				current = current.right;
				direction = 1;
			}
		}
		//Create the node and attach it to the parent
		BSTNode<T> addMe = new BSTNode<T>(data);
		if (direction == -1) {
			parent.left = addMe;
		} else if (direction == 1) {
			parent.right = addMe;
		}

	}
	
	public T remove(T data) {
		
		BSTNode<T> par = null;
		BSTNode<T> rem = root;
		//Find the parent and the node being removed
		while(rem != null && rem.data.compareTo(data) != 0) {
			par = rem;
			
			if(data.compareTo(rem.data) < 0) {
				rem = rem.left;
			}else {
				rem = rem.right;
			}
		}
		
		if(rem != null) {
			//Return the data and remove the node
			T ret = rem.data;
			removeNode(rem,par);
			return ret;
		}
		//Return null if the data isn't in the tree
		return null;

	}

	

	private void removeNode(BSTNode<T> node, BSTNode<T> parent) {
		
		//Zero/One child removal
		if(node.left == null || node.right == null) {
			
			//Get the only child. Will be null if node has 0 parents
			BSTNode<T> child = node.left;
			if(node.left == null) {
				child = node.right;
			}
			//Handle the root
			if(parent == null) {
				root = child;
			}else {
				boolean leftChild = true;
				if(parent.left != node) {
					leftChild = false;
				}
				if(leftChild) {
					parent.left = child;
					
				}else {
					parent.right = child;
				}
			}
		}else {//Two child removal
			BSTNode<T> rmlPar = node;
			BSTNode<T> rml = node.left;
			//Get the rightmost left node and its parent
			while(rml.right != null) {
				rmlPar = rml;
				rml = rml.right;
			}
			
			//Switch data between rml and the node
			T temp = rml.data;
			rml.data = node.data;
			node.data = temp;
			
			//Remove the rightmost left. Guaranteed to have 1/0 children
			removeNode(rml,rmlPar);
		}
		

	}

	public ArrayList<T> traverse(int order) throws IllegalArgumentException {
		ArrayList<T> ret = new ArrayList<T>();//The arraylist that's returned
		
		//Run the correct traversal method
		switch (order) {
		case INORDER:
			inorder(ret, this.root);
			break;
		case PREORDER:
			preorder(ret, this.root);
			break;
		case POSTORDER:
			postorder(ret, this.root);
			break;
		default:
			throw new IllegalArgumentException("Not a valid order. Use variables from BSTree");

		}

		return ret;
	}

	private void preorder(ArrayList<T> addTo, BSTNode<T> node) {
		if (node == null)
			return;
		addTo.add(node.data);
		preorder(addTo, node.left);
		preorder(addTo, node.right);
	}

	private void postorder(ArrayList<T> addTo, BSTNode<T> node) {
		if (node == null)
			return;
		postorder(addTo, node.left);
		postorder(addTo, node.right);
		addTo.add(node.data);
	}

	private void inorder(ArrayList<T> addTo, BSTNode<T> node) {
		if (node == null)
			return;
		inorder(addTo, node.left);
		addTo.add(node.data);
		inorder(addTo, node.right);
	}

	private class BSTNode<E extends Comparable<T>> {
		public BSTNode<E> left;//Left child
		public BSTNode<E> right;//Right child
		public T data;//Data stored in node

		public BSTNode(T data) {
			this.data = data;
		}
	}
}
