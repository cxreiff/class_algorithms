import java.util.ArrayList;

/**
 * COSC 301 - Programming Project 3 - jreiff17
 *
 * In adjacency list representations, the four corner vertexes in each subgraph are represented by the
 * four lists in indexes larger than k, the number of interior vertexes.
 */

class AdjList
{

	int n;

	ArrayList<ArrayList<Edge>> adj;

	public AdjList(int n)
	{
		this.n = n;

		adj = new ArrayList<ArrayList<Edge>>(n);

		for(int i = 0; i < n; i++)
		{
			adj.add(new ArrayList<Edge>());
			adj.get(i).add(new Edge(i, 0));
		}
	}

	public void add(int vertex1, int vertex2, int weight)
	{
		adj.get(vertex1).add(new Edge(vertex2, weight));
	}

	public Element[] dijkstra(int source)
	{
		Element[] paths = new Element[n];

		for (int i = 0; i < paths.length; i++)
		{
			paths[i] = new Element(Integer.MAX_VALUE / 2, -1);
		}

		PriorityQueue<Integer, Integer> grey = new PriorityQueue<Integer, Integer>();

		for (int i = 0; i < n; i++)
		{
			grey.addItem(i, Integer.MAX_VALUE / 2);
		}

		grey.changePriority(source, 0);
		paths[source].setWeight(0);

		while(grey.getSize() > 0)
		{
			int current = grey.removeItem();

			for (int i = 0; i < adj.get(current).size(); i++)
			{
				if(adj.get(current).get(i).getWeight() + paths[current].getWeight()
						< paths[adj.get(current).get(i).getK()].getWeight())
				{
					paths[adj.get(current).get(i).getK()].setWeight(adj.get(current).get(i).getWeight()
							+ paths[current].getWeight());

					paths[adj.get(current).get(i).getK()].setParent(current);

					grey.changePriority(adj.get(current).get(i).getK(),
							paths[adj.get(current).get(i).getK()].getWeight());
				}
			}
		}

		return paths;
	}

	public int size()
	{
		return adj.size();
	}

	public String toString()
	{
		return adj.toString();
	}

	class Edge
	{
		int k, weight;

		public Edge(int k, int weight)
		{
			this.k = k;
			this.weight = weight;
		}

		public int getK()
		{
			return k;
		}

		public void setK(int k)
		{
			this.k = k;
		}

		public int getWeight()
		{
			return weight;
		}

		public void setWeight(int weight)
		{
			this.weight = weight;
		}

		public String toString()
		{
			return "[ "+k+": "+weight+"]";
		}
	}

	class Element
	{
		int weight, parent;

		public Element(int weight, int parent)
		{
			this.weight = weight;
			this.parent = parent;
		}

		public int getWeight()
		{
			return weight;
		}

		public void setWeight(int weight)
		{
			this.weight = weight;
		}

		public int getParent()
		{
			return parent;
		}

		public void setParent(int parent)
		{
			this.parent = parent;
		}

		public String toString()
		{
			return "{ "+weight+", "+parent+" }";
		}
	}
}