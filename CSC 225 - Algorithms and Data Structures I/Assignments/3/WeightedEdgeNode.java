/* 
   The WeightedEdgeNode class is provided to you for CSC 225 
   by Wendy Myrvold.  You do not have permission to distribute 
   this code or to use it for any other purpose.

   In order for your code to compile when marked, you
   should NOT modify this code.

   This class provides a node that can be used in
   a linked list of weighted edge nodes.
   The data in a weighted edge node consists of an 
   edge (u, v) that has a weight w. 
   Edge names are standardized so that u < v. 
   Instead of using 3 fields w, u, and v,
   the values are stored in an array:

   edge[0]= w
   edge[1]= u
   edge[2]= v

   I did it this way so that the code for sorting
   the edges by weight will be easier to write.

   Edges are compared using a lexiographic order
   by considering first the weight w, then u, then v.

*/
public class WeightedEdgeNode
{
    int [] edge;
    WeightedEdgeNode next;

    public WeightedEdgeNode(int w, int u, int v, WeightedEdgeNode next_node)
    {
        edge= new int[3];
        edge[0]= w;
        if (u < v)
        {
           edge[1]= u; 
           edge[2]= v;
        }
        else
        {
           edge[1]= v;
           edge[2]= u;
        }
        next= next_node;
    }
/* 
    This is used for checking the sorted order.
*/
    public int compare(WeightedEdgeNode y)
    {
        int i;

        for (i=0; i <= 2; i++)
        {
           if (edge[i] < y.edge[i]) return(-1);
           if (edge[i] > y.edge[i]) return( 1);
        }
        return(0);
    }
}

 
