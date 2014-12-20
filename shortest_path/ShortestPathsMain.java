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

public class ShortestPathsMain
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

		AdjList badj = new AdjList(width * height * (n+4));

		while(sc.hasNext())
		{
			String edge = sc.nextLine();

			if(edge.charAt(0) == 'q') break;

			int split = edge.indexOf(' ');
			int w1 = Integer.parseInt(edge.substring(split + 2, edge.indexOf('.', split + 2)));
			int h1 = Integer.parseInt(edge.substring(split + 4, edge.indexOf('.', split + 4)));
			int k1 = Integer.parseInt(edge.substring(split + 6, edge.lastIndexOf(' ')));

			int w2;
			int h2;
			int k2;

			if(edge.charAt(0) == 'v')
			{
				k2 = Integer.parseInt(edge.substring(5, edge.indexOf(' ')));

				w2 = w1;
				h2 = h1;
			}
			else
			{
				k2 = n;

				w2 = Integer.parseInt(edge.substring(1, edge.indexOf('.')));
				h2 = Integer.parseInt(edge.substring(3, edge.indexOf(' ')));

				if(w2 == width)
				{
					w2--;
					k2 += 1;
				}
				if(h2 == height)
				{
					h2--;
					k2 += 2;
				}
			}

			badj.add(k1 + (w1 * (n+4)) + (h1 * (width * (n+4))),
					k2 + (w2 * (n+4)) + (h2 * (width * (n+4))),
					Integer.parseInt(edge.substring(edge.lastIndexOf(' ') + 1, edge.length())));

			badj.add(k2 + (w2 * (n+4)) + (h2 * (width * (n+4))),
					k1 + (w1 * (n+4)) + (h1 * (width * (n+4))),
					Integer.parseInt(edge.substring(edge.lastIndexOf(' ') + 1, edge.length())));
		}

		while(sc.hasNext())	//Loop through queries and return shortest paths for queries.
		{
			String query = sc.nextLine();

			if(query.length() < 2) break;

			int split = query.indexOf(' ');

			int w1 = Integer.parseInt(query.substring(1, query.indexOf('.')));
			int h1 = Integer.parseInt(query.substring(3, query.indexOf('.', 3)));
			int k1 = Integer.parseInt(query.substring(5, split));

			int w2 = Integer.parseInt(query.substring(split + 2, query.indexOf('.', split + 2)));
			int h2 = Integer.parseInt(query.substring(split + 4, query.indexOf('.', split + 4)));
			int k2 = Integer.parseInt(query.substring(split + 6, query.length()));

			int source = k1 + (w1 * (n+4)) + (h1 * (width * (n+4)));
			int dest = k2 + (w2 * (n+4)) + (h2 * (width * (n+4)));

			AdjList.Element[] result = badj.dijkstra(source);

			String toPrint = "]";

			int printer = dest;

			while(printer != source)
			{
				int w = (printer % ((n+4) * width)) / (n+4);
				int h = (printer / ((n+4) * width));
				int k = printer - ((w * (n+4)) + (h * ((n+4) * width)));

				if(printer != dest) toPrint = ", "+toPrint;

				if(k < n)
				{
					toPrint = "v"+w+"."+h+"."+k+toPrint;
				}
				else if(k == n)
				{
					toPrint = "g"+w+"."+h+toPrint;
				}
				else if(k == n+1)
				{
					toPrint = "g"+(w+1)+"."+h+toPrint;
				}
				else if(k == n+2)
				{
					toPrint = "g"+w+"."+(h+1)+toPrint;
				}
				else if(k == n+3)
				{
					toPrint = "g"+(w+1)+"."+(h+1)+toPrint;
				}

				printer = result[printer].getParent();
			}

			int w = (source % ((n+4) * width)) / (n+4);
			int h = (source / ((n+4) * width));
			int k = source - ((w * (n+4)) + (h * ((n+4) * width)));

			toPrint = "v"+w+"."+h+"."+k+", "+toPrint;

			toPrint = result[dest].getWeight() + " [" + toPrint;

			System.out.println(toPrint);
		}
	}
}
