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
        int totalComparisons = 0;
        long totalDuration = 0;
        RandomGen randomGen = new RandomGen();
        MergeSort mergeSort = new MergeSort();

        for(int t = 0; t < numTrials; t++){
            int[] randomArray = randomGen.randNumArr(size, min, max);

            //System.out.println("::: Input Array :::");
            // for(int i = 0; i < randomArray.length; i++){
            //     System.out.print(randomArray[i] + " ");
            // }
            // System.out.println();

            long startTime = System.nanoTime();
            mergeSort.sort(randomArray, 0, randomArray.length - 1);
            long endTime = System.nanoTime();

            totalDuration += endTime - startTime;

            totalComparisons += mergeSort.comparisons;
            mergeSort.comparisons = 0;

        }


        //mergeSort.printSort(randomArray);
        System.out.println("MergeSort average clock time = " + totalDuration/numTrials + " nanoseconds");
        System.out.println("Average number of comparisons =  " + totalComparisons/numTrials);
        
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
