
import java.util.ArrayList;
import java.util.Scanner;

public class ClosestPair
{
	public static void main(String[] args)
	{
		Scanner sc=new Scanner(System.in);
		
		ArrayList<String> input = new ArrayList<String>();
		while(sc.hasNextDouble()) input.add(sc.next());
		sc.close();
		
		//long time1 = System.currentTimeMillis();
		
		if(input.size()<5)
		{
			System.out.println("Invalid Input: Not enough points to find a pair. Exiting.");
			System.exit(1);
		}
		if(input.size()%2==0)
		{
			System.out.println("Invalid input: incomplete points. Exiting.");
			System.exit(1);
		}
		
		ArrayList<DPoint> listX = new ArrayList<DPoint>();
		ArrayList<DPoint> listY = new ArrayList<DPoint>();
		
		for(int i=1; i<input.size(); i+=2)
		{
			listX.add(new DPoint(Double.parseDouble(input.get(i)),Double.parseDouble(input.get(i+1))));
			listY.add(new DPoint(Double.parseDouble(input.get(i)),Double.parseDouble(input.get(i+1))));
		}

		sortX(listX);
		sortY(listY);
		
		double[] result = cp(listX, listY);
		
		//long time2 = System.currentTimeMillis();
		
		System.out.println("("+result[1]+","+result[2]+")-("+result[3]+","+result[4]+")="+result[0]);
		
		//System.out.println("Elapsed Time: "+(time2-time1));
		
		//n=1000 : 241 ms
		//n=10000 : 887 ms
		//n=100000 : 1937 ms
		
		//d:	result[0]
		//x1:	result[1]
		//y1:	result[2]
		//x2:	result[3]
		//y2:	result[4]
	}
	
	public static double[] cp(ArrayList<DPoint> listX, ArrayList<DPoint> listY)
	{
		if(listX.size()<=3) return bruteForce(listX);
		
		ArrayList<DPoint> listXL = new ArrayList<DPoint>(listX.size());
		ArrayList<DPoint> listXR = new ArrayList<DPoint>(listX.size());
		ArrayList<DPoint> listYL = new ArrayList<DPoint>(listX.size());
		ArrayList<DPoint> listYR = new ArrayList<DPoint>(listX.size());
		
		int mid = listX.size()/2;
		
		for(int i=0; i<mid;i++)
		{
			listXL.add(new DPoint(listX.get(i).getX(), listX.get(i).getY()));
			listXR.add(new DPoint(listX.get(mid+i).getX(), listX.get(mid+i).getY()));
		}
		for(int i=0;i<listY.size();i++)
		{
			if(listY.get(i).getX()<listX.get(mid).getX())
				listYL.add(new DPoint(listY.get(i).getX(), listY.get(i).getY()));
			else
				listYR.add(new DPoint(listY.get(i).getX(), listY.get(i).getY()));
		}
		
		double[] result={-1.0, -1.0, -1.0, -1.0, -1.0};
		
		double[] left = cp(listXL, listYL);
		double[] right = cp(listXR, listYR);
		
		if(left[0] < right[0]) result = left;
		else result = right;
		
		double midline = (listX.get(mid-1).getX()+listX.get(mid).getX())/2;
		ArrayList<DPoint> listMid = new ArrayList<DPoint>();
		
		for(DPoint i : listY)
		{
			if (i.getX() > midline-result[0] && i.getX() < midline+result[0])
				listMid.add(new DPoint(i.getX(), i.getY()));
		}
		
		for(int i=0;i<listMid.size();i++)
		{
			for(int j=i+1; j<listMid.size();j++)
			{
				double d = dist(listMid.get(i),listMid.get(j));
				if (d<result[0])
				{
					result[0] = d;
					result[1] = listMid.get(i).getX();
					result[2] = listMid.get(i).getY();
					result[3] = listMid.get(j).getX();
					result[4] = listMid.get(j).getY();
				}
				else if(Math.abs(listMid.get(i).getY()-listMid.get(j).getY()) > result[0]) break;
			}
		}
		
		return result;
	}
	
	public static double dist(DPoint p1, DPoint p2)
	{
		return Math.sqrt((p1.getX()-p2.getX())*(p1.getX()-p2.getX())
				+(p1.getY()-p2.getY())*(p1.getY()-p2.getY()));
	}
	
	public static double[] bruteForce(ArrayList<DPoint> listX)
	{
		double d1 = Double.MAX_VALUE;
		double d2 = Double.MAX_VALUE;
		double d3 = Double.MAX_VALUE;
		double[] result={-1.0, -1.0, -1.0, -1.0, -1.0};
		d1 = dist(listX.get(0), listX.get(1));
		if(listX.size()>2)
		{
			d2 = dist(listX.get(0), listX.get(2));
			d3 = dist(listX.get(1), listX.get(2));
		}
		if(d1<d2 && d1<d3)
		{
			result[0] = d1;
			result[1] = listX.get(0).getX();
			result[2] = listX.get(0).getY();
			result[3] = listX.get(1).getX();
			result[4] = listX.get(1).getY();
		}
		else if(d2<d1 && d2<d3)
		{
			result[0] = d2;
			result[1] = listX.get(0).getX();
			result[2] = listX.get(0).getY();
			result[3] = listX.get(2).getX();
			result[4] = listX.get(2).getY();
		}
		else
		{
			result[0] = d1;
			result[1] = listX.get(1).getX();
			result[2] = listX.get(1).getY();
			result[3] = listX.get(2).getX();
			result[4] = listX.get(2).getY();
		}
		
		return result;
	}
	
	public static void sortX(ArrayList<DPoint> listX)
	{
		ArrayList<DPoint> helper = new ArrayList<DPoint>(listX.size());
		
		for(int i=0;i<listX.size();i++)
		{
			helper.add(new DPoint(listX.get(i).getX(), listX.get(i).getY()));
		}
		
		mergeSortX(helper, listX, 0, listX.size()-1);
	}
	public static void mergeSortX(ArrayList<DPoint> helper, ArrayList<DPoint> listX, int lo, int hi)
	{
		if(helper.size() < 1) return;
		
		int mid = (lo+hi)/2;
		
		if(hi>lo)
		{
			mergeSortX(helper, listX, lo, mid);
			mergeSortX(helper, listX, mid+1, hi);
			mergeX(helper, listX, lo, mid, hi);
		}
	}
	public static void mergeX(ArrayList<DPoint> helper, ArrayList<DPoint> listX, int lo, int mid, int hi)
	{
		int i = lo;
		int j = mid+1;
		int k = lo;
		
		while(i<=mid && j<= hi)
		{
			if(helper.get(i).getX() < helper.get(j).getX())
			{
				listX.set(k, new DPoint(helper.get(i).getX(),helper.get(i).getY()));
				i++;
				k++;
			}
			else
			{
				listX.set(k, new DPoint(helper.get(j).getX(),helper.get(j).getY()));
				j++;
				k++;
			}
		}
		while(i<=mid)
		{
			listX.set(k, new DPoint(helper.get(i).getX(),helper.get(i).getY()));
			i++;
			k++;
		}
		
		for(int l=lo;l<=hi;l++)
		{
			helper.set(l, new DPoint(listX.get(l).getX(),listX.get(l).getY()));
		}
	}
	
	public static void sortY(ArrayList<DPoint> listY)
	{
		ArrayList<DPoint> helper = new ArrayList<DPoint>(listY.size());
		
		for(int i=0;i<listY.size();i++)
		{
			helper.add(new DPoint(listY.get(i).getX(), listY.get(i).getY()));
		}
		
		mergeSortY(helper, listY, 0, listY.size()-1);
	}
	public static void mergeSortY(ArrayList<DPoint> helper, ArrayList<DPoint> listY, int lo, int hi)
	{
		if(helper.size() < 2) return;
		
		int mid = (lo+hi)/2;
		
		if(hi>lo)
		{
			mergeSortY(helper, listY, lo, mid);
			mergeSortY(helper, listY, mid+1, hi);
			mergeY(helper, listY, lo, mid, hi);
		}
	}
	public static void mergeY(ArrayList<DPoint> helper, ArrayList<DPoint> listY, int lo, int mid, int hi)
	{
		int i = lo;
		int j = mid+1;
		int k = lo;
		
		while(i<=mid && j<= hi)
		{
			if(helper.get(i).getY() < helper.get(j).getY())
			{
				listY.set(k, new DPoint(helper.get(i).getX(),helper.get(i).getY()));
				i++;
				k++;
			}
			else
			{
				listY.set(k, new DPoint(helper.get(j).getX(),helper.get(j).getY()));
				j++;
				k++;
			}
		}
		while(i<=mid)
		{
			listY.set(k, new DPoint(helper.get(i).getX(),helper.get(i).getY()));
			i++;
			k++;
		}
		while(j<=hi)
		{
			listY.set(k, new DPoint(helper.get(j).getX(),helper.get(j).getY()));
			j++;
			k++;
		}
		
		for(int l=lo;l<=hi;l++)
		{
			helper.set(l, new DPoint(listY.get(l).getX(),listY.get(l).getY()));
		}
	}
}