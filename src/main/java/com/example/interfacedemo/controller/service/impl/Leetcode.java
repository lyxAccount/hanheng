package com.example.interfacedemo.controller.service.impl;

public class Leetcode {
    public static void main(String[] args) {
        int[] nums = {8,6,-1,0,3,5,9,12};
        int target = 3;

        System.out.println( search(nums,target));
    }

    public static int search(int[] nums, int target) {
        int n = nums.length - 1;
        int left = 0;
        int right = n;

        while (left <= right) {
            int mid = (right + left) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] >= nums[left]) {
                if (nums[mid] > target && target >= nums[left]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                if (nums[mid] < target && target <=nums[right]){
                    left = mid + 1;
                }else{
                    right = mid - 1;
                }
            }
        }
        return -1;
    }
}
