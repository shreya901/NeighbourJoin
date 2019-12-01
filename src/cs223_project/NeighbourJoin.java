package cs223_project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.swing.*;
import javax.swing.event.*;



import java.awt.*;
import java.awt.List;
import java.io.*;
import java.util.*;



public class NeighbourJoin  {
	
public static  HashMap<Integer,String> seq_index = new HashMap<Integer,String>();
	
    public static void main(String[] args) throws FileNotFoundException {
	

	
	
	String path= "/Users/shreya90/Desktop/data_bio.csv";

	String line = "";
    
   
    
    ArrayList<String> sequences= new ArrayList<String>();
    ArrayList<String> ID= new ArrayList<String>();
    
 
    

    try (BufferedReader br = new BufferedReader(new FileReader(path))) 
    {
    	int index=0;
        while ((line = br.readLine()) != null )
        {
        	String[] csv_read = line.split(",");
        	String x="";
        	for(int i =1;i<csv_read.length;i++)
        		x+=csv_read[i];
        	
            sequences.add(x);
            ID.add(csv_read[0]);
            seq_index.put(index,csv_read[0]);
            index+=1;
        }

    } catch (IOException e) 
    {
        e.printStackTrace();
    }
    
   /* distance_mat[0][1]=5.0;
    distance_mat[1][0]=5.0;
    distance_mat[0][2]=4.0;
    distance_mat[2][0]=4.0;
    distance_mat[0][3]=7.0;
    distance_mat[3][0]=7.0;
    distance_mat[0][4]=6.0;
    distance_mat[4][0]=6.0;
    distance_mat[1][2]=7.0;
    distance_mat[1][3]=9.0;
    distance_mat[1][4]=9.0;
    distance_mat[2][1]=7.0;
    distance_mat[3][1]=9.0;
    distance_mat[4][1]=9.0;
    distance_mat[2][3]=21.0;
    distance_mat[2][4]=12.0;
    distance_mat[3][2]=21.0;
    distance_mat[4][2]=12.0;
    distance_mat[3][4]=5.0;
    distance_mat[4][3]=5.0;
    distance_mat[5][0]=8.0;
    distance_mat[0][5]=8.0;
    distance_mat[5][1]=11.0;
    distance_mat[1][5]=11.0;
    distance_mat[2][5]=8.0;
    distance_mat[5][2]=8.0;
    distance_mat[5][3]=19.0;
    distance_mat[3][5]=19.0;
    distance_mat[5][4]=10.0;
    distance_mat[4][5]=10.0;
    /*distance_mat[0][1]=5.0;
    distance_mat[0][2]=9.0;
    distance_mat[0][3]=9.0;
    distance_mat[0][4]=8.0;
    distance_mat[1][0]=5.0;
    distance_mat[2][0]=9.0;
    distance_mat[3][0]=9.0;
    distance_mat[4][0]=8.0;
    distance_mat[1][2]=10.0;
    distance_mat[1][3]=10.0;
    distance_mat[1][4]=9.0;
    distance_mat[2][1]=10.0;
    distance_mat[3][1]=10.0;
    distance_mat[4][1]=9.0;
    distance_mat[2][3]=8.0;
    distance_mat[2][4]=7.0;
    distance_mat[3][2]=8.0;
    distance_mat[4][2]=7.0;
    distance_mat[3][4]=3.0;
    distance_mat[4][3]=3.0;*/
    /*seq_index.put(0,"a");
    seq_index.put(1,"b");
    seq_index.put(2,"c");
    seq_index.put(3,"d");
    seq_index.put(4,"e");
    seq_index.put(5,"f");*/
   
    
   
   
    double[][] distance_mat =NeighbourJoin.get_distance_mat(sequences,ID);
    double [][] q_mat=Auxiliary.get_Q_matrix(distance_mat);
    System.out.println();
    System.out.println("Initial distance Matrix");
    for (int i =0;i<distance_mat.length;i++)
    {
    	for (int j =0;j<distance_mat[0].length;j++)
    	{
    		System.out.print(distance_mat[i][j] +  "      ");
    	}
    	System.out.println();
    }
    
    int [] index_pair= Auxiliary.get_closest_pair(q_mat);
    double [] dist_to_parent= Auxiliary.get_distance_closest_pair_parent(distance_mat, index_pair);
    double new_dist_mat[][]=Auxiliary.get_dist_mat(distance_mat, index_pair);
    
   
    
	
	System.out.println("Index of the Closest pair obtained from Q matrix");
	System.out.println(index_pair[0]);
    System.out.println(index_pair[1]);
    System.out.println("Current mapping of indexes and sequences/taxons");
    for (Integer key:seq_index.keySet())
	{
		System.out.print(key+"------->");
		System.out.print(seq_index.get(key));
		System.out.println();		
	}
    System.out.println("Distance of the OTU's/Taxons from its immediate parent");
    System.out.println(dist_to_parent[0]);
    System.out.println(dist_to_parent[1]);
    
  
	
    System.out.println(".........................................................................");
    
    phylogenetic_tree t = new phylogenetic_tree();// t is Binary tree we are displaying
    int count=100;
    ArrayList<phylogenetic_tree> intermediate_subtrees=new ArrayList<phylogenetic_tree>();
    ArrayList<String> intermediate_root_val=new ArrayList<String>();
    Node root=t.insert(null,Integer.toString(count));
    
    t.insert(root,seq_index.get(index_pair[0]));
    t.insert(root,seq_index.get(index_pair[1]));
    intermediate_root_val.add(Integer.toString(count));
    intermediate_subtrees.add(t);
    
    NeighbourJoin.update_seq_index(index_pair, count);
   
    while(new_dist_mat.length>=2)
    {
    	
    	count+=1;
    	q_mat=Auxiliary.get_Q_matrix(new_dist_mat);
    	index_pair=Auxiliary.get_closest_pair(q_mat);
    	dist_to_parent =Auxiliary.get_distance_closest_pair_parent(new_dist_mat, index_pair);
    	
    	
    	 if(intermediate_root_val.contains(seq_index.get(index_pair[1]))==false && intermediate_root_val.contains(seq_index.get(index_pair[0]))==false )
    	{
    		phylogenetic_tree intermed_subtree = new phylogenetic_tree();
    		Node intermed_root=intermed_subtree.insert(null,Integer.toString(count));
    	    
    		intermed_subtree.insert(intermed_root,seq_index.get(index_pair[0]));
    		intermed_subtree.insert(intermed_root,seq_index.get(index_pair[1]));
    		intermediate_subtrees.add(intermed_subtree);
    		intermediate_root_val.add(Integer.toString(count));
    	}
    	else if(intermediate_root_val.contains(seq_index.get(index_pair[1]))==true && intermediate_root_val.contains(seq_index.get(index_pair[0]))==true )
    	{
    		phylogenetic_tree tr1 = null;
    		phylogenetic_tree tr2=null;
    		for (phylogenetic_tree tr:intermediate_subtrees)
    		{
    			if(tr.root.data.equals(seq_index.get(index_pair[1])))
    				tr1=tr;
    			
    			if(tr.root.data.equals(seq_index.get(index_pair[0])))
    					tr2=tr;
    		}
    		phylogenetic_tree new_subtr=new phylogenetic_tree();
    		Node new_subtr_root=new_subtr.insert(null,Integer.toString(count));
    		new_subtr_root.left=tr1.root;
    		new_subtr_root.right=tr2.root;
    		intermediate_subtrees.remove(tr1);
    		intermediate_subtrees.remove(tr2);
    		intermediate_subtrees.add(new_subtr);
    		intermediate_root_val.remove(seq_index.get(index_pair[1]));
    		intermediate_root_val.remove(seq_index.get(index_pair[0]));
    		intermediate_root_val.add(Integer.toString(count));
	
    	}
    	else if(intermediate_root_val.contains(seq_index.get(index_pair[1]))==true && intermediate_root_val.contains(seq_index.get(index_pair[0]))==false)
    	{
    	
    		phylogenetic_tree tre= null;
    		
    		for (phylogenetic_tree tr:intermediate_subtrees)
    		{
    			if(tr.root.data.equals(seq_index.get(index_pair[1])))
    			{
    				tre=tr;
    			}
    			
    		}
    		Node tre_root=tre.change_root(tre.root, Integer.toString(count));
    		tre.insert(tre_root,seq_index.get(index_pair[0]));
    		intermediate_root_val.remove(seq_index.get(index_pair[1]));
    		intermediate_root_val.add(Integer.toString(count));
    		
    		
    	}
    	else if(intermediate_root_val.contains(seq_index.get(index_pair[0]))==true && intermediate_root_val.contains(seq_index.get(index_pair[1]))==false)
    	{
    	
    		phylogenetic_tree tre= null;
    		
    		for (phylogenetic_tree tr:intermediate_subtrees)
    		{
    			if(tr.root.data.equals(seq_index.get(index_pair[0])))
    			{
    				tre=tr;
    			}
    			
    		}
    		Node tre_root=tre.change_root(tre.root, Integer.toString(count));
    		tre.insert(tre_root,seq_index.get(index_pair[1]));
    		intermediate_root_val.remove(seq_index.get(index_pair[0]));
    		intermediate_root_val.add(Integer.toString(count));
    	}
    	
    		 
    	
    	
    	
    	System.out.println();
    	System.out.println("Index of the Closest pair obtained from Q matrix");
    	System.out.println(index_pair[0]);
        System.out.println(index_pair[1]);
        System.out.println("Current mapping of indexes and sequences/taxons");
    	for (Integer key:seq_index.keySet())
    	{
    		System.out.print(key+"------->");
    		System.out.print(seq_index.get(key));
    		System.out.println();		
    	}
    	System.out.println("Distance of the OTU's/Taxons from its immediate parent");
    	System.out.println(dist_to_parent[0]);
        System.out.println(dist_to_parent[1]);
        System.out.println();
        System.out.println("Current distance matrix");
        
        for(int i =0;i<new_dist_mat.length;i++)
        {
        	for(int j =0 ;j<new_dist_mat.length;j++)
        	{
        		System.out.print(new_dist_mat[i][j]+"        ");
        	}
        	System.out.println();
        }
        
    	System.out.println("...........................................................................");
    	
    	
    	NeighbourJoin.update_seq_index(index_pair, count);
    	new_dist_mat = Auxiliary.get_dist_mat(new_dist_mat, index_pair);
    	
    	
    }
    
   
    
    
    intermediate_subtrees.get(0).computeNodePositions(); //finds x,y positions of the tree nodes
    intermediate_subtrees.get(0).maxheight=intermediate_subtrees.get(0).treeHeight(intermediate_subtrees.get(0).root); //finds tree height for scaling y axis
    //System.out.println(root.xpos +" "+root.ypos);
    //System.out.println(x.xpos +" "+x.ypos);
    //System.out.println(z.xpos +" "+z.ypos);
    //System.out.println(y.xpos +" "+y.ypos);
    Display_tree dt = new Display_tree(intermediate_subtrees.get(0));//get a display panel
    dt.setVisible(true); //show the display

    
    
}
public static double[][] get_distance_mat(ArrayList<String> sequences, ArrayList<String> ID)
    {
    	int n= sequences.size();
    	double[][] distance_mat=new double[n][n];
    	
    	for(int i =0;i<sequences.size();i++)
    	{
    		String seq1=sequences.get(i);
    		String id1=ID.get(i);
			
			
    		for (int j =i+1;j<sequences.size();j++)
    		{
    			String seq2=sequences.get(j);
    			String id2=ID.get(j);
    			String []  aligned_Seq= Alignment.global_alignment(seq1, seq2,id1,id2);
    			double dst=compute_distance(aligned_Seq[0],aligned_Seq[1]);
    			
    			
    			distance_mat[i][j]=dst;
    			distance_mat[j][i]=dst;
    		}
    			
    	}
    	
    	return distance_mat;
    }
public static  double compute_distance(String s1,String s2)

    {
    	int count1=0;
    	for (int i =0;i<s1.length();i++)
    	{
    		
    		if(s1.charAt(i)!=s2.charAt(i))
    			count1+=1;
    	}
    	double temp=(double)count1/(double)s1.length();
 
    	double d = (-0.75)*Math.log(1-(1.33*temp));
    	
    	return d;
    		
    }
public static  void update_seq_index(int [] index_pair,int u_node)
{
	seq_index.remove(index_pair[0]);
	seq_index.remove(index_pair[1]);
	 HashMap<Integer,String> seq_index_copy = new HashMap<Integer,String>();
	int counter=1;
	Iterator<Entry<Integer, String>> it = seq_index.entrySet().iterator();
	while(it.hasNext())
	{
		String val=it.next().getValue();
		seq_index_copy.put(counter,val);
		it.remove();
		counter+=1;
	}
	for (Integer key:seq_index_copy.keySet())
	{
		String val=seq_index_copy.get(key);
		seq_index.put(key,val);
		
	}
	seq_index.put(0,Integer.toString(u_node));
	
	
}


 

}
