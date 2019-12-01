package cs223_project;



class Node {

	 Object data;
	    Node left;
	    Node right;
	    int xpos;  //stores x and y position of the node in the tree
	    int ypos;  

	    Node(String x, Node l, Node r) {
	      left = l;
	      right = r;
	      data = (Object) x;
	    }
}
