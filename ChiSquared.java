public class ChiSquared {
        
    public static void main(String[] args) {
        System.out.println(chiSquaredUniformityTest(new int[]{10,9,8,7,6}));
    }
    
    static double chiSquaredUniformityTest(int[] data) {
    	int total = 0;
    	for (int n : data) {
    		total += n;
    	}
    	double chiSquared = 0;
    	for (int n : data) {
    		double expected = (double) total / data.length;
    		double diff = n - expected;
    		chiSquared += diff * diff / expected;
    	}
    	return 1 - chiSquaredCDF(chiSquared, data.length - 1);
    }
    
    static double gamma(double n) {
    	n--;
    	return Math.sqrt(2*Math.PI*n)*Math.pow(n/Math.E, n);
    }
    static double lowerIncompleteGamma(double n, double x) {
    	double s = 0;
    	for (double t = 0; t < x; t+= .001) {
    		s += Math.pow(t, n-1)*Math.pow(Math.E, -t)*.001;
    	}
    	return s;
    }
    static double chiSquaredCDF(double x, double k) {
    	return lowerIncompleteGamma(k/2, x/2)/gamma(k/2);
    }
}