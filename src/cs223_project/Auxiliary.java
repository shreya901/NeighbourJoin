package cs223_project;

public   class Auxiliary {
	public static double [][] get_Q_matrix(double [][] dist_mat)
	 {
		 int n =dist_mat.length;
		 
		 double q_mat[][]=new double[n][n];
		 for (int i =0;i<n;i++)
		 {
			 double dist_i=0.0;
			 
			 for (int k=0; k<n;k++)
				 dist_i+=dist_mat[i][k];
			 
				 
			 for(int j =i+1;j<n;j++)
			 {
				 
				 
				 double dist_j=0.0;
					 for (int k=0; k<n;k++)
						 dist_j+=dist_mat[j][k];
					 
					 
					 q_mat[i][j]=(n-2)*dist_mat[i][j]-dist_i-dist_j;
					 q_mat[j][i]=q_mat[i][j];
					//(n-2)*dist_mat[j][i]-dist_i-dist_j
					 
					
				 
			 }
		 }
		 return q_mat;
	 }
	 public static int [] get_closest_pair(double [][] q_mat)
	 {
		 int n =q_mat.length;
		 int index_pair[]=new int[2];
		 double min=1000.0;
		 for(int i =0;i<n;i++)
		 {
			 for(int j =i+1;j<n;j++)
				 if(q_mat[i][j]<min)
				 {
					 min=q_mat[i][j];
					 index_pair[0]=i;
					 index_pair[1]=j;
				 }
				 
		 }
		 return index_pair;
	 }
	 public static double[][] get_dist_mat(double[][] dist_mat,int[] index_pair)
	 {
		 
		 int n =dist_mat.length;
		 int index_of_u=0;
		 int count=1;
		 double[][] new_dist_mat=new double[n-1][n-1];
		 for (int k = 0 ; k<n;k++)
		 {
			if(k!= index_pair[0] && k!=index_pair[1])
			{
				new_dist_mat[index_of_u][count]=0.5*(dist_mat[index_pair[0]][k]+dist_mat[index_pair[1]][k]- dist_mat[index_pair[0]][index_pair[1]]);
				new_dist_mat[count][index_of_u]=new_dist_mat[index_of_u][count];
				
				
				count+=1;
			}
		 }
		 int row_count=1;
		 int col_count=2;
		
		
		 for (int i=0;i<n;i++)
		 {
			 if(i!= index_pair[0] && i!=index_pair[1] && row_count<n-1)
			 {
				 for(int j=i+1;j<n;j++)
				 {
					 if(j!= index_pair[0] && j!=index_pair[1]  && col_count<n-1)
					 {
						 
						 new_dist_mat[row_count][col_count]=dist_mat[i][j];
						 new_dist_mat[col_count][row_count]=dist_mat[j][i];
						 
						
						 col_count+=1;
					 }
				 }
				
				 row_count+=1;
				 col_count=row_count+1;
			 }
		 
			 
		 }
		 return new_dist_mat;
		 
	}
	 
	 public static double[] get_distance_closest_pair_parent(double[][] dist_mat,int[] index_pair)
	 {
		 double distance_closest_pair_parent[]=new double[2];
		 int n =dist_mat.length;
		 double dist_pair_a=0.0;
		 double dist_pair_b=0.0;
		 for(int k =0;k<n;k++)
		 {
			
				 dist_pair_a+=dist_mat[index_pair[0]][k];
				 dist_pair_b+=dist_mat[index_pair[1]][k]; 
				 
			 
		 }
		
		 distance_closest_pair_parent[0]=0.5*dist_mat[index_pair[0]][index_pair[1]]+0.5*((dist_pair_a-dist_pair_b)/(n-2));
		 distance_closest_pair_parent[1]=dist_mat[index_pair[0]][index_pair[1]]-distance_closest_pair_parent[0];
		  
		 return distance_closest_pair_parent;
	 }




}
