package com.zouhu;

import java.util.Arrays;

/**
 * 堆排序
 * <p>
 *     1. 构建大顶堆：从最后一个非叶子节点开始，逐个调整节点，确保每个子树都满足大顶堆性质。
 *     2. 排序过程：循环中将堆顶（最大值）与当前堆的末尾元素交换，然后调整剩余元素以维持大顶堆性质，直至堆为空。
 * </p>
 *
 * @author zouhu
 * @data 2024-09-25 11:58
 */
public class HeapSort {

    /**
     * 堆排序的完整案例
     * <p>
     *     1. 构建大顶堆：从最后一个非叶子节点开始，逐个调整节点，确保每个子树都满足大顶堆性质。
     *     2. 排序过程：循环中将堆顶（最大值）与当前堆的末尾元素交换，然后调整剩余元素以维持大顶堆性质，直至堆为空。
     * <p>
     *      排序过程可以理解为将堆顶元素放到有序区
     * </p>
     *
     * @param arr 待排序数组
     */
    public static void buildMaxHeapAndSort(int[] arr){
        // 构建大顶堆
        int heapSize = arr.length;
        for(int i = heapSize/2 - 1; i >= 0; i--){
            //从第一个非叶子结点从下至上，从右至左调整结构
            adjustHeap(arr, i, heapSize);
        }
        // System.out.println(Arrays.toString(arr));

        // 调整堆结构+交换堆顶元素与末尾元素，直到堆为空
        // 构建大顶堆的主要目的是为了能够快速访问并移除当前数组中的最大值，将最大值放到有序区
        for(int j = heapSize - 1; j > 0; j--){
            // 将堆顶元素与末尾元素进行交换， 使最大元素"下沉"到数组末尾
            swap(arr,0, j);
            --heapSize;
            //重新对堆进行调整
            adjustHeap(arr, 0, j);
        }
    }

    /**
     * 找到第 k 大的元素
     * <p>
     *
     * </p>
     *
     * @param arr 待排序数组
     * @param k 要查找的第k大的元素的索引（1-based）
     * @return 第 k 大的元素
     */
    public static int findKthLargest(int[] arr, int k) {
        // 构建大小为  arr.length 的大顶堆
        int heapSize = arr.length;
        for (int i = heapSize / 2 - 1; i >= 0; i--) {
            adjustHeap(arr, i, heapSize);
        }
        System.out.println(Arrays.toString(arr));

        // 遍历剩余元素，将大顶堆的前k-1个最大元素移到数组末尾（有序区）
        // 结束循环，有序区的序号为 [arr.length - k + 1, arr.length - 1]， 包含 k-1 个元素
        for (int j = arr.length - 1; j >= arr.length - k + 1; j--) {
            swap(arr, 0, j);
            heapSize--;
            adjustHeap(arr, 0, heapSize);
        }
        System.out.println(Arrays.toString(arr));

        return arr[0]; // 堆顶元素即为第 k 大的元素
    }

    /**
     * 构建大顶堆, 没有排序
     *
     * @param arr
     * @param heapSize
     */
    public static void buildMaxHeap(int []arr, int heapSize){
        for(int i = heapSize/2 - 1; i >= 0; i--){
            //从第一个非叶子结点从下至上，从右至左调整结构
            adjustHeap(arr, i, heapSize);
        }
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 调整堆使其满足最大堆的性质（仅是调整过程，建立在大顶堆已构建的基础上）
     * <p>
     *      此方法用于在进行堆排序时，将任意元素下沉到正确的位置，以保持堆的特性
     *      即堆的每个父节点的值都大于或等于其子节点的值
     * </p>
     *
     * @param arr 堆的表示数组
     * @param i 需要调整的部分的根节点索引
     * @param heapSize 堆的大小，表示堆中有效元素的数量
     */
    public static void adjustHeap(int []arr, int i, int heapSize) {
        // 先取出当前元素i，作为临时变量用于后续调整
        int temp = arr[i];

        // 从 i 结点的左子结点开始，也就是 2i+1 处开始，遍历一个从i节点开始的完全二叉树的所有左子节点,进行调整
        // 循环，直到子结点为空（或者子结点已小于 temp）
        for(int k = i * 2 + 1; k < heapSize; k = k * 2 + 1) {
            // 如果左子结点小于右子结点，选择较大的右子结点
            if(k + 1 < heapSize && arr[k] < arr[k + 1]) {
                k++;
            }

            // 如果子节点大于父节点，将子节点值赋给父节点，继续向下调整
            if(arr[k] > temp) {
                arr[i] = arr[k];
                i = k;  // 以子节点 k 继续向下调整
            } else {
                // 当子节点不大于父节点时，调整结束
                break;
            }
        }

        // 将temp值放到最终调整后的位置
        arr[i] = temp;
    }


    /**
     * 交换元素
     * @param arr
     * @param a
     * @param b
     */
    public static void swap(int []arr,int a ,int b){
        int temp=arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    public static void main(String[] args) {
        int []arr = {9, 3, 7, 4, 5, 6, 8, 2, 1};

        // buildMaxHeapAndSort(arr);
        // System.out.println(Arrays.toString(arr));

        System.out.println(findKthLargest(arr, 3));


    }
}
