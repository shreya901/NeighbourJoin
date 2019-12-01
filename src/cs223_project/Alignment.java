package cs223_project;

import java.util.HashMap;

public class Alignment {
	public static String[] global_alignment(String s1,String s2,String id1,String id2)
	{
		int row=s1.length()+1;
		int col=s2.length()+1;
		int count1=1;
		int count2=1;
		int gap=-2;
		String aligned_Seq[]=new String[2];
		Alignment_pointer [][] dp_global = new Alignment_pointer[row][col];
		Alignment_pointer start=new Alignment_pointer(0,-1,-1);
		dp_global[0][0]=start;
		for (int i =1;i<row;i++)
		{
			dp_global[i][0]=new Alignment_pointer(gap*count1,i-1,0);
			count1+=1;
			
		}
				
		for (int j =1;j<col;j++)
		{
			dp_global[0][j]=new Alignment_pointer(gap*count2,0,j-1);
			count2+=1;	
		}
			
		for (int i =1;i<row;i++)
		{
			for (int j =1;j<col;j++)
			{
				if(s1.charAt(i-1)==s2.charAt(j-1))
				{
					int x=dp_global[i-1][j-1].value+1;
					dp_global[i][j]=new Alignment_pointer(x,i-1,j-1);
					
				}
				else
				{
					int p=dp_global[i-1][j].value-2;
			        int q=dp_global[i][j-1].value-2;
			        int r=dp_global[i-1][j-1].value -1;
			        int minimum=Math.max(Math.max(p, q),r);
			        if(minimum==p)
			        	dp_global[i][j]=new Alignment_pointer(p,i-1,j);
			        else if(minimum==r)
			        	dp_global[i][j]=new Alignment_pointer(r,i-1,j-1);
			        else
			        	dp_global[i][j]=new Alignment_pointer(q,i,j-1);
			        	
			        	
				}
					
					
			}
		}
		Alignment_pointer trace_back= dp_global[row-1][col-1];
		/*for (int i =0;i<row;i++)
		{
			for(int j =0;j<col;j++)
			{
				System.out.print(dp_global[i][j].value+" ,"+dp_global[i][j].pointer_left+" ,"+dp_global[i][j].pointer_top+"     ");
			}
			System.out.println();
		}*/
		
		int current_row=row-1;
		int	current_col=col-1;
		String alignment1="";
		String alignment2="";
		while(trace_back.pointer_left>=0 && trace_back.pointer_top>=0)
			
		{
			int parent_row=trace_back.pointer_left;
			int parent_col=trace_back.pointer_top;
			
			if(parent_row==current_row-1 &&  parent_col==current_col-1)
			{
				alignment1+=s1.charAt(current_row-1);
	            alignment2+=s2.charAt(current_col-1);
			}
			else if(parent_row==current_row-1 && parent_col==current_col)
			{
				alignment1+=s1.charAt(current_row-1);
	            alignment2+='-';
				
			}
			else if(parent_row==current_row && parent_col==current_col-1)
			{
				alignment1+='-';
	            alignment2+=s2.charAt(current_col-1);
			}
			trace_back=dp_global[parent_row][parent_col];
			current_row=parent_row;
			current_col=parent_col ;
	            
	            
		}
		StringBuffer s1_Alg=new StringBuffer(alignment1).reverse();
		StringBuffer s2_Alg=new StringBuffer(alignment2).reverse();
		aligned_Seq[0]=s1_Alg.toString();
		aligned_Seq[1]=s2_Alg.toString();
		System.out.println("globally aligned sequences :");    
		System.out.println(id1);
		System.out.println(id2);
		System.out.println("Score: "+ dp_global[row-1][col-1].value);
		System.out.println();
		return aligned_Seq;

		
	}

}
