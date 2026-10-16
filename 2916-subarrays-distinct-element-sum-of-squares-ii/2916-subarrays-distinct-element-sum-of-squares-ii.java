import java.util.*;

class Solution {
    private static final int MOD = 1_000_000_007;

    public int sumCounts(int[] nums) {
        int n = nums.length;
        LazySegmentTree tree = new LazySegmentTree(n);
        Map<Integer, Integer> lastSeen = new HashMap<>();
        long totalSum = 0;
        long currentSquareSum = 0;

        for (int r = 0; r < n; r++) {
            int l = lastSeen.getOrDefault(nums[r], -1) + 1;
            lastSeen.put(nums[r], r);

            // When elements in range [l, r] increase their distinct count by 1:
            // (v + 1)^2 = v^2 + 2v + 1
            // We update the segment tree and add contributions
            long rangeSum = tree.querySum(l, r);
            long rangeLen = (r - l + 1);
            
            currentSquareSum = (currentSquareSum + 2 * rangeSum + rangeLen) % MOD;
            tree.updateAdd(l, r, 1);

            totalSum = (totalSum + currentSquareSum) % MOD;
        }

        return (int) totalSum;
    }

    private static class LazySegmentTree {
        private final int n;
        private final long[] sum;
        private final long[] lazy;

        public LazySegmentTree(int n) {
            this.n = n;
            this.sum = new long[4 * n];
            this.lazy = new long[4 * n];
        }

        private void push(int idx, int l, int r) {
            if (lazy[idx] != 0) {
                int mid = (l + r) / 2;
                apply(2 * idx, l, mid, lazy[idx]);
                apply(2 * idx + 1, mid + 1, r, lazy[idx]);
                lazy[idx] = 0;
            }
        }

        private void apply(int idx, int l, int r, long val) {
            sum[idx] = (sum[idx] + val * (r - l + 1)) % MOD;
            lazy[idx] = (lazy[idx] + val) % MOD;
        }

        public void updateAdd(int ql, int qr, long val) {
            updateAdd(1, 0, n - 1, ql, qr, val);
        }

        private void updateAdd(int idx, int l, int r, int ql, int qr, long val) {
            if (ql <= l && r <= qr) {
                apply(idx, l, r, val);
                return;
            }
            push(idx, l, r);
            int mid = (l + r) / 2;
            if (ql <= mid) updateAdd(2 * idx, l, mid, ql, qr, val);
            if (qr > mid) updateAdd(2 * idx + 1, mid + 1, r, ql, qr, val);
            sum[idx] = (sum[2 * idx] + sum[2 * idx + 1]) % MOD;
        }

        public long querySum(int ql, int qr) {
            return querySum(1, 0, n - 1, ql, qr);
        }

        private long querySum(int idx, int l, int r, int ql, int qr) {
            if (ql <= l && r <= qr) {
                return sum[idx];
            }
            push(idx, l, r);
            int mid = (l + r) / 2;
            long res = 0;
            if (ql <= mid) res = (res + querySum(2 * idx, l, mid, ql, qr)) % MOD;
            if (qr > mid) res = (res + querySum(2 * idx + 1, mid + 1, r, ql, qr)) % MOD;
            return res;
        }
    }
}
