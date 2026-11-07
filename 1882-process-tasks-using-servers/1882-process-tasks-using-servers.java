class Solution {
    public int[] assignTasks(int[] servers, int[] tasks) {
        int n = servers.length;
        int m = tasks.length;
        int[] ans = new int[m];

        java.util.Queue<int[]> free = new java.util.PriorityQueue<>((a, b) -> {
            if (a[0] != b[0]) return Integer.compare(a[0], b[0]);
            return Integer.compare(a[1], b[1]);
        });

        java.util.Queue<long[]> used = new java.util.PriorityQueue<>((a, b) -> {
            if (a[0] != b[0]) return Long.compare(a[0], b[0]);
            if (a[1] != b[1]) return Long.compare(a[1], b[1]);
            return Long.compare(a[2], b[2]);
        });

        for (int i = 0; i < n; i++) {
            free.offer(new int[]{servers[i], i});
        }

        long currTime = 0;
        for (int i = 0; i < m; i++) {
            currTime = Math.max(currTime, i);

            if (free.isEmpty() && !used.isEmpty() && used.peek()[0] > currTime) {
                currTime = used.peek()[0];
            }

            while (!used.isEmpty() && used.peek()[0] <= currTime) {
                long[] server = used.poll();
                free.offer(new int[]{(int) server[1], (int) server[2]});
            }

            int[] server = free.poll();
            ans[i] = server[1];
            used.offer(new long[]{currTime + tasks[i], server[0], server[1]});
        }

        return ans;
    }
}
