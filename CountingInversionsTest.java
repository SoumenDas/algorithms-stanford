import java.util.Date;
public class CountingInversionsTest {
	static int count_inversion = 0;
	public static void main(String[] args) {
		
		int size = 10000;
		int[] num = new int[size];
		for (int i=0; i<size; i++) {
			num[i] = (int)(Math.random()*10000);
		}
		
		//int[] num = {1,3,5,2,4,6};
		long lStartTime = new Date().getTime();
		count(num,0,num.length-1);
		long lEndTime = new Date().getTime();
		long difference = lEndTime - lStartTime;
		System.out.println("Elapsed milliseconds: " + difference);
		System.out.println("Number of inversions: "+ count_inversion);
	}

	static void count(int[] arr,int low, int high){
		if (low < high) {
			int middle = low/2 + high/2;
			count(arr,low,middle);
			count(arr,middle+1,high);
			countSplit(arr, low, middle, high);
		}
	}

	static void countSplit(int[] arr,int low, int middle, int high){
		int size = arr.length;
		int[] temp = new int[size];
		for (int i=0;i<size ;i++ ) {
			temp[i] = arr[i];
		}
		int right = middle+1;
		while (right<=high) {
			int left = low;
			while(left<=middle){						// keep checking the left part
				if (temp[left]>temp[right]) {
					count_inversion++;
				}
				left++;
			}
			right++;
		}
		
	}
	
}

