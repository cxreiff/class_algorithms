/**
 * Test Class.
 */

public class Tester
{
	public static void main(String[] args)
	{
		String edge = "v0.0.1 v0.0.10 5";

		System.out.println(edge.substring(edge.lastIndexOf(' ') + 1));
	}
}
