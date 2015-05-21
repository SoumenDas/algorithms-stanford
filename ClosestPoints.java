import java.util.ArrayList;
import java.util.Date;
public class ClosestPoints {
	public static void main(String[] args) {
		int size = 500;
		Details result = new Details();
		Point[] p = new Point[size];
		fillArray(p);
//		display(p);
		long lStartTime = new Date().getTime();
		Point[] px = new Point[size];
		copyArray(p,px);
		Point[] py = new Point[size];
		copyArray(p,py);

		mergeSort(px,0,size-1,"x");
		mergeSort(py,0,size-1,"y");

		result = splitPairs(px,py,0,size-1);
		System.out.println("The pairs are: ");
		System.out.println(py[result.index[0]].getX() +" "+py[result.index[0]].getY());
		System.out.println(py[result.index[1]].getX() +" "+py[result.index[1]].getY());
		System.out.println("Distance is: "+result.distance);
		long lEndTime = new Date().getTime();
		long difference = lEndTime - lStartTime;
		System.out.println("Elapsed milliseconds: " + difference);
	}

	static Details splitPairs(Point[] arrX, Point[] arrY,int low, int high){
		Details result = new Details();
		if (high-low == 2) {
			double d1 = distance(arrY,low,low+1).distance;				// Here arrY is used becoz all index are based on that
			double d2 = distance(arrY,low,low+2).distance;
			double d3 = distance(arrY,low+1,low+2).distance;
			
			if(d1<d2){
				if (d1<d3) {
					result.distance = d1;
					result.index[0] = low;
					result.index[1] = low+1;
				} else {
					result.distance = d3;
					result.index[0] = low+1;
					result.index[1] = low+2;
				}
			} else {
				result.distance = d2;
				result.index[0] = low;
				result.index[1] = low+2;
			}
			return result;
		}
		if (high-low == 1) {
			result.distance = distance(arrY,low,high).distance;
			result.index[0] = low;
			result.index[1] = high;
			return result; 
		}
		if (low<high) {
			int middle = low/2 + high/2;
			Details lr = splitPairs(arrX,arrY,low,middle);			// qx , qy
			Details rr = splitPairs(arrX,arrY,middle+1,high);		// rx , ry
			if (lr.distance < rr.distance) {
				return closestPair(arrX,arrY,middle,lr);
			} else {
				return closestPair(arrX,arrY,middle,rr);
			}
		}
		return result;
	}

	static Details closestPair(Point[] arrX, Point[] arrY,int middle,Details prevResult){
		Details result = new Details();
		int xMiddle = arrX[middle].getX();
		double best = prevResult.distance;
		int[] bestPair = new int[2];
		bestPair[0] = prevResult.index[0];								
		bestPair[1] = prevResult.index[1];

		ArrayList<Integer> indexList = new ArrayList<Integer>();
		//-------Filtering Step---------------------------------------- 
		for (int i=0;i < arrY.length ;i++ ) {
			if (arrY[i].getX() < (xMiddle+best) && arrY[i].getX() > (xMiddle-best))  {
				indexList.add(i);
			}
		}
		//---------------------------------------------------------
		int size = indexList.size();
		if (size<=1) {
			return prevResult;
		}

		for (int i=0;i<size-1;i++ ) {
			for (int j=i+1;j<Math.min(size,8) ;j++ ) {
				double d = distance(arrY,indexList.get(i),indexList.get(j)).distance;
				if (d < best) {
				 	best = d;
				 	bestPair[0] = indexList.get(i);
				 	bestPair[1] = indexList.get(j);
				} 
			}
		}
		result.distance = best;
		result.index[0] = bestPair[0];							
		result.index[1] = bestPair[1];
		return result;
	}

	static Details distance(Point[] arr,int low, int high){
		Details result = new Details();
		double x = Math.pow(arr[high].getX()-arr[low].getX(),2);
		double y = Math.pow(arr[high].getY()-arr[low].getY(),2);
		result.distance = Math.sqrt(x+y);
		result.index[0] = low;
		result.index[1] = high;
		return result;
	}

	static void fillArray(Point[] arr){
		int size = arr.length;
		for (int i=0;i<size ;i++ ) {
			int x = (int)(Math.random()*1000);
			int y = (int)(Math.random()*1000);
			arr[i] = new Point(x,y);
		}
	}

	static void copyArray(Point[] from, Point[] to){
		int size = to.length;
		for (int i=0; i<size; i++) {
			to[i] = from[i];
		}
	}

	static void display(Point[] arr){
		int size = arr.length;
		for (int i=0; i<size;i++  ) {
			System.out.println(arr[i].getX() + " "+ arr[i].getY());
		}
	}

	static void mergeSort(Point[] arr,int low,int high, String str){
		if (low<high) {
			int middle = low/2 + high/2;
			mergeSort(arr,low,middle,str);
			mergeSort(arr,middle+1,high,str);
			if (str.equals("x")) {
				mergeX(arr,low,middle,high);
			} else {
				mergeY(arr,low,middle,high);
			}	
		}
	}

	static void mergeX(Point[] arr, int low, int middle, int high){
		int size = arr.length;
		Point[] temp = new Point[size];
		copyArray(arr,temp);
		int left = low;
		int right = middle+1;
		int k = low;
		while(left<=middle && right<=high){
			if (temp[left].getX() > temp[right].getX()) {
				arr[k] = temp[right];
				right++;
			} else {
				arr[k] = temp[left];
				left++;
			}
			k++;
		}

		while(left<=middle){
			arr[k] = temp[left];
			k++;
			left++;
		}
	}

	static void mergeY(Point[] arr, int low, int middle, int high){
		int size = arr.length;
		Point[] temp = new Point[size];
		copyArray(arr,temp);
		int left = low;
		int right = middle+1;
		int k = low;
		while(left<=middle && right<=high){
			if (temp[left].getY() > temp[right].getY()) {
				arr[k] = temp[right];
				right++;
			} else {
				arr[k] = temp[left];
				left++;
			}
			k++;
		}

		while(left<=middle){
			arr[k] = temp[left];
			k++;
			left++;
		}
	}
}

class Point {
	private int x;
	private int y;

	Point(int x,int y){
		this.x = x;
		this.y = y;
	}

	public int getX(){
		return this.x;
	}

	public int getY(){
		return this.y;
	}

	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
}

class Details {
	public double distance = 0;
	public int[] index = {0,0};
}