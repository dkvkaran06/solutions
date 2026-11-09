import java.util.*;

class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        // Min-heap to store busy servers: stores int[]{freeTime, serverId}
        PriorityQueue<int[]> busyHeap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        
        // TreeSet to keep track of available server IDs
        TreeSet<Integer> idleServers = new TreeSet<>();
        for (int i = 0; i < k; i++) {
            idleServers.add(i);
        }
        
        int[] requestCount = new int[k];
        
        for (int i = 0; i < arrival.length; i++) {
            int currTime = arrival[i];
            
            // Free up servers whose job finish time <= current arrival time
            while (!busyHeap.isEmpty() && busyHeap.peek()[0] <= currTime) {
                int[] finished = busyHeap.poll();
                idleServers.add(finished[1]);
            }
            
            // If all servers are busy, drop the request
            if (idleServers.isEmpty()) {
                continue;
            }
            
            // Find preferred server: i % k
            Integer target = idleServers.ceiling(i % k);
            if (target == null) {
                target = idleServers.first(); // Wrap around to the smallest available server ID
            }
            
            // Assign request
            idleServers.remove(target);
            requestCount[target]++;
            busyHeap.offer(new int[]{currTime + load[i], target});
        }
        
        // Find the maximum requests handled by any server
        int maxRequests = 0;
        for (int count : requestCount) {
            maxRequests = Math.max(maxRequests, count);
        }
        
        // Collect all servers that handled maxRequests
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            if (requestCount[i] == maxRequests) {
                result.add(i);
            }
        }
        
        return result;
    }
}
