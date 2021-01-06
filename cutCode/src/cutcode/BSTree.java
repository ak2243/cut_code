package cutcode;
import java.util.ArrayList;
import java.util.List;

public class BSTree<T extends Comparable<T>> {
	private Node<T> root;

	/**
	 * @param data -- value added to the tree
	 */
	public void add(T data) {
		// if root is null, can just add to root and be done with it
		if (root == null) {
			root = new Node<T>(data);
			return;
		}

		Node<T> check = root;

		int compare = data.compareTo(check.data);
		// loop to figure out where a node should be added
		while (check != null) {
			// less than or equal to zero means go left
			if (compare <= 0) {
				if (check.left == null) { // if no left child, add node
					check.left = new Node<T>(data);
					break;
				} else { // no node, so need to change check to the left node
					check = check.left;
				}
			} else { // greater than, so go right
				if (check.right == null) { // if no right child, add 
					check.right = new Node<T>(data);
					break;
				} else { // no node, so need to change check to the right node
					check = check.right;
				}
			}
			compare = data.compareTo(check.data); // update comparison in case we haven't inserted a node yet
		}

	}

	/**
	 * @param data -- value to be removed
	 * @return the value of the removed. null if it doesn't exist
	 */
	public T remove(T data) {
		Node<T> rem = root; //Starts with root
		Node<T> par = null; //parent of root is null
		while (rem != null && rem.data.compareTo(data) != 0) { //Looks for the node to be removed and it's parent
			par = rem; //rem will go left or right
			if (data.compareTo(rem.data) < 0) { //if value less than, go left
				rem = rem.left;
			} else {
				rem = rem.right;
			}
			//rem is null if it can't be found
		}
		if (rem != null) {
			T ret = rem.data; //data needs to be saved in case nodes are switched (2 children remove)
			removeNode(rem, par); //removes the node
			return ret;
		}

		return null; //if not found
	}

	private void removeNode(Node<T> rem, Node<T> par) {
		if (rem.left == null || rem.right == null) { //zero or one children
			Node<T> child = rem.left;
			if (rem.left == null) {
				child = rem.right;
			}
			//child is the child of rem, null if 0 children
			if (par == null) { //root is being removed
				root = child;
			} else {
				boolean lc = true; //is the child the left child
				if (par.left != rem) {
					lc = false;
				}
				if (lc) { //child is left child
					par.left = child; //null if no child
				} else { //child is right child
					par.right = child; //null if no child
				}
			}
		} else { //two children
			Node<T> rmlPar = rem; //parent of rightmost left
			Node<T> rml = rem.left; //rightmost left
			while (rml.right != null) {
				rmlPar = rml; //follows rml and makes sure we keep track of the parent
				rml = rml.right; //goes right as far as possible
			}
			T tmp = rml.data; //placeholder
			rml.data = rem.data;
			rem.data = tmp;
			//data has been switched
			removeNode(rml, rmlPar); //now we have to remove the rightmost left

		}
	}

	/**
	 *
	 * @return Inorder traversal of the list
	 */
	public List<T> inOrder() {
		if (root == null)
			return null;
		List<T> list = new ArrayList<T>();
		lcr(list, root); //recursion
		return list;
	}

	private void lcr(List<T> list, Node<T> node) {
		if (node.left != null) { //goes left first
			lcr(list, node.left); //algorithm resets when you go left
		}
		list.add(node.data); //next, add the value
		if (node.right != null) { //goes right
			lcr(list, node.right); //algorithm resets when you go right
		}
	}

	private class Node<E extends Comparable<E>> {

		public Node<E> left;
		public Node<E> right;
		public E data;

		public Node(E data) {
			this.data = data;
		}
	}

}
