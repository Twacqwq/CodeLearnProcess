package main

func findMediaSortedArrays(nums1, nums2 []int) float64 {
	if len(nums1) > len(nums2) {
		return findMediaSortedArrays(nums2, nums1)
	}
	low, high, k, nums1Mid, nums2Mid := 0, len(nums1), (len(nums1)+len(nums2)+1)>>1, 0, 0
	for low <= high {
		nums1Mid = low + (high-low)>>1
		nums2Mid = k - nums1Mid
		if nums1Mid > 0 && nums1[nums1Mid-1] > nums2[nums2Mid-1] {
			high = nums1Mid - 1
		} else if nums1Mid != len(nums1) && nums1[nums1Mid] < nums2[nums2Mid-1] {

		}
	}
	return 0
}
