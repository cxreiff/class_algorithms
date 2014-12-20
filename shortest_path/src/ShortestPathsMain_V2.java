import java.util.Scanner;

/**
 * COSC 301 - Programming Project 3 - jreiff17
 *
 * Finding the shortest paths in a weighted, undirected graph.
 *
 * "The graph will have a special property: it can be broken down into a 2-D grid of cells, where each
 * cell has 4 special "corner" vertices shared with its neighbors, and all of the edges are between two
 * vertices in the same cell or between a non-corner vertex in a cell and one of the corner vertices of
 * the same cell."
 *
 */

public class ShortestPathsMainV2
{
	static int width;	//Number of columns of subgraphs in graph.
	static int height;	//Number of rows of subgraphs in graph.
	static int n;		//Number of vertexes within each subgraph.

	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

		width = sc.nextInt();
		height = sc.nextInt();
		n = sc.nextInt();

		sc.nextLine();

		AdjList[][] madj = new AdjList[width][height];

		for(int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				madj[i][j] = new AdjList(n + 4);

		while(sc.hasNext())	//Loop through edge list, populating representation of graph.
		{
			String edge = sc.nextLine();

			if(edge.charAt(0) == 'q') break;	//Query line hit, move on to query loop.

			int split = edge.indexOf(' ');
			int w1 = Character.getNumericValue(edge.charAt(split + 2));
			int h1 = Character.getNumericValue(edge.charAt(split + 4));
			int k1 = Integer.parseInt(edge.substring(split + 6, edge.lastIndexOf(' ')));

			int k2 = n;

			if(edge.charAt(0) == 'v') k2 = Integer.parseInt(edge.substring(5, edge.indexOf(' ')));
			else
			{
				int w2 = Character.getNumericValue(edge.charAt(1));
				int h2 = Character.getNumericValue(edge.charAt(3));

				if(w2 > w1) k2 += 1;
				if(h2 > h1) k2 += 2;
			}

			madj[w1][h1].add(k1, k2, Integer.parseInt(edge.substring(edge.lastIndexOf(' ') + 1, edge.length())));
			madj[w1][h1].add(k2, k1, Integer.parseInt(edge.substring(edge.lastIndexOf(' ') + 1, edge.length())));
		}

//		//Preprocessing
//		AdjList corners = new AdjList((width+1)*(height+1));
//
//		for (int i = 0; i < corners.size(); i++)
//		{
//			if(i - 1 >= 0 && (i - 1) % (width + 1) != width)
//			{
//				corners.add(i, i-1, Integer.MAX_VALUE);
//
//				if(i < width + 1)
//				{
//					AdjList box = madj[i % (width + 1) - 1][0];
//
//					box.dijkstra(box.size() - 3);
//				}
//				else if(i > (width + 1) * height - 1)
//				{
//
//				}
//				else
//				{
//
//				}
//			}
//
//			if(i + 1 < corners.size() && (i + 1) % (width + 1) != 0)
//			{
//				corners.add(i, i+1, Integer.MAX_VALUE);
//			}
//
//			if(i - (width + 1) >= 0)
//			{
//				corners.add(i, i - (width + 1), Integer.MAX_VALUE);
//			}
//
//			if(i + (width + 1) < corners.size())
//			{
//				corners.add(i, i + (width + 1), Integer.MAX_VALUE);
//			}
//
//		}

		while(sc.hasNext())	//Loop through queries and return shortest paths for queries.
		{
			String query = sc.nextLine();

			//TODO calculate shortest path for current query.
		}
	}
}
