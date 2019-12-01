package cs223_project;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;



public class Display_tree extends JFrame {
	private static final long serialVersionUID = 1L;
	JScrollPane scrollpane;
	Display_Panel panel;
	  

	  public Display_tree(phylogenetic_tree t) {
		 panel = new Display_Panel(t);
	    panel.setPreferredSize(new Dimension(900, 900));
	    scrollpane = new JScrollPane(panel);
	    
	    getContentPane().add(scrollpane, BorderLayout.CENTER);
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    pack();  // cleans up the window panel
	  }


}
