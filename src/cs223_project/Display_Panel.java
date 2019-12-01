package cs223_project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

class Display_Panel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	phylogenetic_tree t;
     int xs;
     int ys;

   public Display_Panel(phylogenetic_tree t) {
     this.t = t; 
     setBackground(Color.white);
     setForeground(Color.black);
   }

   protected void paintComponent(Graphics g) {
     g.setColor(getBackground()); //colors the window
     g.fillRect(0, 0, getWidth(), getHeight());
     g.setColor(getForeground()); //set color and fonts
     Font MyFont = new Font("SansSerif",Font.BOLD,10);
     g.setFont(MyFont);
     xs=10;   //where to start printing on the panel
     ys=10;
     g.drawString("Phylogenetic Tree:\n",xs,ys);
     ys=ys+10;
    
     MyFont = new Font("SansSerif",Font.BOLD,10); //bigger font for tree
     g.setFont(MyFont);
     this.drawTree(g, t.root); // draw the tree
     revalidate(); //update the component panel
   }

     public void drawTree(Graphics g, Node root) {
     int dx, dy, dx2, dy2;
     int SCREEN_WIDTH=700; 
     int SCREEN_HEIGHT=900;
     int XSCALE, YSCALE;  
     XSCALE=SCREEN_WIDTH/phylogenetic_tree.totalnodes; 
     YSCALE=(SCREEN_HEIGHT-ys)/(t.maxheight+5); 

     if (root != null) { 
       drawTree(g, root.left); 
       dx = root.xpos * XSCALE; 
       dy = root.ypos * YSCALE +ys;
       String s = (String) root.data; 
       g.drawString(s, dx, dy); 
       if(root.left!=null){ //draws the line to left child if it exists
         dx2 = root.left.xpos * XSCALE; 
         dy2 = root.left.ypos * YSCALE +ys;
         g.drawLine(dx,dy,dx2,dy2);
       }
       if(root.right!=null){ //draws the line to right child if it exists
         dx2 = root.right.xpos * XSCALE ;//get right child x,y scaled position
         dy2 = root.right.ypos * YSCALE + ys;
         g.drawLine(dx,dy,dx2,dy2);
       }
       drawTree(g, root.right);  
     }
   }
}
