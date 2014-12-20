import java.util.Random;
import java.util.Vector;


public class KMeans
{
	public static void main(String[] args){
		
		KMeans e = new KMeans();
		
		double[][] sources = e.dCenters(Integer.parseInt(args[0]));
		double[][] tempCent = e.dCenters(Integer.parseInt(args[0]));
		
		Vector<double[]> cent = new Vector<double[]>();
		
		for (int i=0; i<tempCent.length; i++) {
			
				double[] one = new double[3];
				one[0] = tempCent[i][0];
				one[1] = tempCent[i][1];
				one[2] = 0;
				cent.add(one);
		}
		
		Vector<double[]> correct = new Vector<double[]>();
		
		for (int i=0; i<sources.length; i++) {
			
			double[] one = new double[3];
			one[0] = sources[i][0];
			one[1] = sources[i][1];
			one[2] = 5;
			correct.add(one);
		}
		
		Vector<double[]> data = e.dData(sources,1000);
		
		Scatterplot sp = new Scatterplot(data);
		boolean finished = false;
		boolean[] isCorrects = new boolean[cent.size()];
		
		while(!finished){
			
			for(int j=0; j<data.size(); j++){
				
				double big = Double.MAX_VALUE;
				
				for(int k=0; k<cent.size(); k++){
					double temp = Math.sqrt((Math.abs(data.elementAt(j)[0]-cent.elementAt(k)[0]))*(Math.abs(data.elementAt(j)[0]-cent.elementAt(k)[0])) +
							(Math.abs(data.elementAt(j)[1]-cent.elementAt(k)[1]))*(Math.abs(data.elementAt(j)[1]-cent.elementAt(k)[1])));
					if(temp < big){
							big = temp;
							data.set(j, new double[]{data.elementAt(j)[0],data.elementAt(j)[1],k});
					}
				
				}
			}
			
			double[][] values = new double[cent.size()][3];
			
			for(int j=0; j<data.size(); j++){
					
				values[(int)(data.elementAt(j)[2])][0] += data.elementAt(j)[0];
				values[(int)(data.elementAt(j)[2])][1] += data.elementAt(j)[1];
				values[(int)(data.elementAt(j)[2])][2] += 1;
			}
			
			finished = true;
			for(int k=0; k<values.length; k++){
				
				
				if(Math.sqrt(Math.abs((values[k][0] / values[k][2]) - cent.elementAt(k)[0])*Math.abs((values[k][0] / values[k][2])-cent.elementAt(k)[0])+
						Math.abs((values[k][1] / values[k][2]) - cent.elementAt(k)[1])*Math.abs((values[k][1] / values[k][2]) - cent.elementAt(k)[1])) > 0.00001) finished = false;
				cent.set(k, new double[]{values[k][0] / values[k][2], values[k][1] / values[k][2]});
			}
			
			sp.update(sp.getGraphics());
		}
		for(int j=0;j<cent.size();j++){
			for(int k=0;k<correct.size();k++){
				if(Math.sqrt(Math.abs((correct.elementAt(k)[0]) - cent.elementAt(j)[0])*Math.abs((correct.elementAt(k)[0])-cent.elementAt(j)[0])+
						Math.abs((correct.elementAt(k)[1]) - cent.elementAt(j)[1])*Math.abs((correct.elementAt(k)[1]) - cent.elementAt(j)[1])) < 0.1) isCorrects[j] = true;
			}
		}

		boolean isCorrect = true;
		
		for(int j=0; j<isCorrects.length; j++){
			if(isCorrects[j]==false)isCorrect=false;
		}
		
		System.out.println("Correct? : "+isCorrect);
		
//		new Scatterplot(cent);
//		new Scatterplot(correct);
	}

	
	public double[][] dCenters(int howMany) {
		double[][] ret = new double[howMany][2];
		
		for (int i =0; i< ret.length; i++) {
			ret[i][0] = Math.random();
			ret[i][1] = Math.random();
		}
		
		return ret;
	}
	
	public Vector<double[]> dData(double[][] centers, int perCenter) {
		
		Random r = new Random();
		Vector<double[]> ret = new Vector<double[]>();
			
		for (int i=0; i<centers.length; i++) {
			for (int j=0; j<perCenter; j++) {
				double[] one = new double[3];
				one[0] = centers[i][0] + r.nextGaussian()*0.1;
				one[1] = centers[i][1] + r.nextGaussian()*0.1;
				one[2] = 0;
				ret.add(one);
			}
		}
		
		return ret;
		
	}
}
