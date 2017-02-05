package com.amal.dagger.sorting;

/**
 * Created by amal on 4/2/17.
 */

public class QuickSort implements Sort{
    @Override
    public int[] sortArray(int[] in) {
        sort(in, 0, in.length - 1);
        return in;
    }

    public int pivot(int[] a, int lo, int hi){
        int mid = (lo+hi)/2;
        int pivot = a[lo] + a[hi] + a[mid] - Math.min(Math.min(a[lo], a[hi]), a[mid]) - Math.max(Math.max(a[lo], a[hi]), a[mid]);

        if(pivot == a[lo])
            return lo;
        else if(pivot == a[hi])
            return hi;
        return mid;
    }

    public int partition(int[] a, int lo, int hi){

        int k = pivot(a, lo, hi);
        //System.out.println(k);
        swap(a, lo, k);
        //System.out.println(a);
        int j = hi + 1;
        int i = lo;
        while(true){

            while(a[lo] < a[--j])
                if(j==lo)   break;

            while(a[++i] < a[lo])
                if(i==hi) break;

            if(i >= j)  break;
            swap(a, i, j);
        }
        swap(a, lo, j);
        return j;
    }

    public void sort(int[] a, int lo, int hi){
        if(hi<=lo)  return;
        int p = partition(a, lo, hi);
        sort(a, lo, p-1);
        sort(a, p+1, hi);

    }

    public void swap(int[] a, int b, int c){
        int swap = a[b];
        a[b] = a[c];
        a[c] = swap;
    }


}
