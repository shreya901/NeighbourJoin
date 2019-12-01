package cs223_project;



public class phylogenetic_tree {
	String inputString= new String();
    Node root;
    static int totalnodes = 0; //keeps track of the total number of nodes for horizontal scaling 
    int maxheight=0;//keeps track of the depth of the tree for vertical scaling

    phylogenetic_tree() {
     root = null;
    }
    public int treeHeight(Node t){
    	if(t==null) return 0;
              else return 1 + max(treeHeight(t.left),treeHeight(t.right));
        }
        public int max(int a, int b){
    	  if(a>b) return a; else return b;
        }

        public void computeNodePositions() {
          int depth = 1;
          inorder_traversal(root, depth);
        }

    //traverses tree and computes x,y position of each node, stores it in the node

        public void inorder_traversal(Node t, int depth) { 
          if (t != null) {
            inorder_traversal(t.left, depth + 1); //add 1 to depth (y coordinate) 
            t.xpos = totalnodes++; //x coord is node number in inorder traversal
            t.ypos = depth; // mark y coord as depth
            inorder_traversal(t.right, depth + 1);
          }
    }

  

        public Node insert(Node parent, String s) { // Binary Search tree insert
          if (parent == null) {
            parent = new Node(s, null, null);
            this.root=parent;
            return this.root;
          }
          else {
        	Node newnode=  new Node(s, null, null);
        	
            if (s.compareTo((String)(parent.data)) == 0) 
               return null;  /* duplicate word  found - do nothing */
            else if (parent.left==null)
            {
            	parent.left=newnode;
            	return parent;
            }
                          
                else if(parent.right==null)
                {
                	parent.right=newnode;
                	return parent;
                }
                         
                else
                {
                	Node x=insert(parent.left,s);
                	if(x==null)
                		x=insert(parent.right,s);
                	return parent;
                }
          }
        }

public Node change_root(Node root, String s)
{
	Node newnode=  new Node(s, null, null);
	Node oldroot=this.root;
	newnode.left=oldroot;
	this.root=newnode;
	return this.root;
	
}
}
