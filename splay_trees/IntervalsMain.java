import java.util.Scanner;

/**
 * jreiff17
 * cosc 301
 * programming assignment 2
 * IntervalsMain.java
 */

public class IntervalsMain
{
	static IntervalSplayTree ist;

	public static void main(String args[])
	{
		ist = new IntervalSplayTree();

		Scanner sc = new Scanner(System.in);

		while(sc.hasNext())
		{
			String op = sc.next();

			if(op.equals("add"))
			{
				add(sc.nextInt(), sc.nextInt());
			}
			else if(op.equals("remove"))
			{
				remove(sc.nextInt(), sc.nextInt());
			}
			else
			{
				System.out.println("INVALID INPUT");
				System.exit(1);
			}
		}

		printList();
	}

	private static void add(int lo, int hi)
	{
		if(lo>hi) return;
		int[] result = ist.add(lo,hi);
		switch(result[0])
		{
			case 0:
				System.out.println(result[1]+" added "+result[2]+" "+result[3]);
				break;

			case 1:
				System.out.println(result[1]+" not added "+result[2]+" "+result[3]);
				break;
		}
	}
	private static void remove(int lo, int hi)
	{
		if(lo>hi) return;
		int[] result = ist.remove(lo,hi);
		switch(result[0])
		{
			case 0:
				System.out.println(result[1]+" not found "+result[2]+" "+result[3]);
				break;

			case 1:
				System.out.println(result[1]+" truncated "+result[2]+" "+result[3]);
				break;

			case 2:
				System.out.println(result[1]+" split "+result[2]+" "+result[3]);
				break;

			case 3:
				System.out.println(result[1]+" removed "+result[2]+" "+result[3]);
				break;
		}
	}

	private static void printList()
	{
		System.out.println(ist.listString());
	}
//	private static void printTree()
//	{
//		System.out.println(ist.treeString()+"\n");
//	}
}
