class Solution {
    public int shortestPath(int n, int[][] edges, String labels, int k) {
        List<int[]>[]adj=new ArrayList[n];
        for(int i=0;i<n;i++)
        {
            adj[i]=new ArrayList<>();
        }
        for(int[] edge:edges)
        {
            int u=edge[0];
            int v=edge[1];
            int w=edge[2];
            adj[u].add(new int[]{v,w});
        }
        int[][] dist=new int[n][k+1];
        for(int i=0;i<n;i++)
        {
            Arrays.fill(dist[i],Integer.MAX_VALUE);
        }
        PriorityQueue<int[]> pq=new PriorityQueue<>((a,b) -> Integer.compare(a[2],b[2]));
        dist[0][1]=0;
        pq.offer(new int[]{0,1,0});
        while(!pq.isEmpty())
        {
            int[] curr=pq.poll();
            int u=curr[0];
            int count=curr[1];
            int d=curr[2];
            if(d>dist[u][count])
            {
                continue;
            }
            if(u==n-1)
            {
                return d;
            }
            for(int[] neighbor:adj[u])
            {
                int v=neighbor[0];
                int w=neighbor[1];
                int nextCount=(labels.charAt(v)==labels.charAt(u))?count+1:1;
                if(nextCount<=k)
                {
                    if(d+w<dist[v][nextCount])
                    {
                        dist[v][nextCount]=d+w;
                        pq.offer(new int[]{v,nextCount, d+w});
                    }
                }
            }
        }
        return -1;
    }
}