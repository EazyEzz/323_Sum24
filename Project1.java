import java.util.Arrays;
import java.util.Random;

/*
 * Essmer Sanchez
 * CSCI 323 Summer 24'
 * PROJECT 1
 */
public class Project1{

    public static void main(String[] args){
        int size = 10000;
        int min = 1;
        int max = 1000000;
        int numTrials = 100;
        int totalMergSortComparisons = 0;
        long totalMergeSortDuration = 0;
        int totalQuickComparisons = 0;
        long totalQuickDuration = 0;
        int totalRadixComparisons = 0;
        long totalRadixDuration = 0;
        RandomGen randomGen = new RandomGen();
        MergeSort mergeSort = new MergeSort();
        QuickSort quickSort = new QuickSort();
        RadixSort radixSort = new RadixSort();


        for(int t = 0; t < numTrials; t++){
            int[] randomArray = randomGen.randNumArr(size, min, max);
            int[] mergeSortArr = Arrays.copyOf(randomArray, randomArray.length);
            int[] quickSortArr = Arrays.copyOf(randomArray, randomArray.length);
            int[] radixSortArr = Arrays.copyOf(randomArray, randomArray.length);

            long startTime = System.nanoTime();
            mergeSort.sort(mergeSortArr, 0, mergeSortArr.length - 1);
            long endTime = System.nanoTime();
            totalMergeSortDuration += endTime - startTime;
            totalMergSortComparisons += mergeSort.comparisons;
            mergeSort.comparisons = 0;
    
            long quickStartTime = System.nanoTime();
            quickSort.sort(quickSortArr, 0, quickSortArr.length - 1);
            long quickEndTime = System.nanoTime();
            totalQuickDuration += quickEndTime - quickStartTime;
            totalQuickComparisons += quickSort.comparisons;
            quickSort.comparisons = 0;
    
            long radixStartTime = System.nanoTime();
            radixSort.sort(radixSortArr);
            long radixEndTime = System.nanoTime();
            totalRadixDuration += radixEndTime - radixStartTime;
            totalRadixComparisons += radixSort.comparisons;
            radixSort.comparisons = 0;

        }

        System.out.println("MergeSort average clock time = " + totalMergeSortDuration/numTrials + " nanoseconds");
        System.out.println("Average number of comparisons =  " + totalMergSortComparisons/numTrials);
        
        System.out.println("====================================================================================");

        System.out.println("QuickSort average clock time = " + totalQuickDuration/numTrials + " nanoseconds");
        System.out.println("Average number of comparisons =  " + totalQuickComparisons/numTrials);

        System.out.println("====================================================================================");

        System.out.println("RadixSort average clock time = " + totalRadixDuration/numTrials + " nanoseconds");
        System.out.println("Average number of comparisons =  " + totalRadixComparisons/numTrials);
    }
}
class MergeSort{
    int comparisons;

    public MergeSort(){
        comparisons = 0;
    }

    public void sort(int[] arr, int left, int right){
        if(left < right){
            int mid = left + (right-left)/2;
            sort(arr, left, mid);
            sort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private void merge(int[] arr, int left, int mid, int right){
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] tempL = new int[n1];
        int[] tempR = new int[n2];
        
        for(int i = 0; i < n1; i++){
            tempL[i] = arr[left + i];
        }
        for(int j = 0; j < n2; j++){
            tempR[j] = arr[mid + 1 + j];
        }

        int i = 0;
        int j = 0;
        int k = left;
        while(i < n1 && j < n2){
            comparisons++;
            if(tempL[i] <= tempR[j]){
                arr[k] = tempL[i];
                i++;
            } else {
                arr[k] = tempR[j];
                j++;
            }
            k++;
        }

        while(i < n1){
            arr[k] = tempL[i];
            i++;
            k++;
        }
        while(j < n2){
            arr[k] = tempR[j];
            j++;
            k++;
        }
    }

    public void printSort(int[] arr){
        System.out.println("::: Sorted Array ::");
        for(int i = 0; i < arr.length; i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}

class QuickSort{
    int comparisons;

    public QuickSort(){
        comparisons = 0;
    }

    public void sort(int[] arr, int left, int right){
        if(left < right){ //array size check > 2. Do nothing if array size = 0 or 1.
            int pivotIndex = partition(arr, left, right);
            sort(arr, left, pivotIndex - 1);
            sort(arr, pivotIndex + 1, right);
        }
    }

    private int partition(int[] arr, int left, int right){
        int mid = left + (right - left)/2;
        int pivotIndex = medianThree(arr, left, mid, right);
        swap(arr, pivotIndex, right);
        int pivotValue = arr[right];


        int j = left - 1;

        for(int i = left; i < right; i++){
            comparisons++;
            if(arr[i] < pivotValue){
                j++;
                swap(arr, j, i);
            }
        }
        
        swap(arr, j + 1, right);
        
        return j + 1;
    }

    private int medianThree(int[] arr, int a, int b, int c){
        if(arr[a] > arr[b]){
            swap(arr, a, b);
        }
        if(arr[a] > arr[c]){
            swap(arr, a, c);
        }
        if(arr[b] > arr[c]){
            swap(arr, b, c);
        }

        return b;
    }

    private void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public void printSort(int[] arr){
        System.out.println("::: Sorted Array ::");
        for(int i = 0; i < arr.length; i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}

class RandomGen{
    int size;
    int min;
    int max;

    public RandomGen(){
        size = 0;
        min = 0;
        max = 0;
    }

    public int[] randNumArr(int size, int min, int max){
        Random random = new Random();
        int[] randArr = new int[size];

        for(int i = 0; i < randArr.length; i++){
            randArr[i] = random.nextInt(max - min + 1) + min;
        }

        return randArr;
    }
}

class RadixSort{
    int comparisons;

    public RadixSort(){
        comparisons = 0;
    }

    public void sort(int[] arr){
        int max = getMax(arr);

        for(int position = 1; max/position > 0; position *= 10){
            countSort(arr,position);
        }
    }

    private void countSort(int[] arr, int pos){
        int length = arr.length;
        int[] output = new int[length];
        int[] countArr = new int[10];

        for(int i = 0; i < 10; i++){
            countArr[i] = 0;
        }

        for(int i = 0; i < length; i++){
            countArr[(arr[i] / pos) % 10]++;
        }

        for(int i = 1; i < 10; i++){
            countArr[i] += countArr[i - 1];
        }

        for(int i = length - 1; i >= 0; i--){
            output[countArr[(arr[i] / pos) % 10] -1 ] = arr[i];
            countArr[(arr[i] / pos) % 10]--;
            comparisons++;
        }

        for(int i = 0; i < length; i++){
            arr[i] = output[i];
        }
    }

    private int getMax(int[] arr){
        int l = arr.length;
        int max = arr[0];

        for(int i = 1; i < l; i++){
            if(arr[i] > max){
                max = arr[i];
            }
        }

        return max;
    }
}
