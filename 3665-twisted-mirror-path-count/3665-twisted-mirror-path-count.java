import java.util.Arrays;

class Solution {
    private int[][] memo;
    private int[][] grid;
    private int m, n;
    private final int MOD = 1000000007;

    public int uniquePaths(int[][] grid) {
        this.grid = grid;
        this.m = grid.length;
        this.n = grid[0].length;
        this.memo = new int[m][n];
        
        // Initialize memoization table with -1
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        return getPaths(0, 0);
    }

    private int getPaths(int r, int c) {
        // If out of bounds, this path is invalid
        if (r >= m || c >= n) return 0;
        
        // Base case: successfully reached the destination
        if (r == m - 1 && c == n - 1) return 1;

        // Return cached result if already calculated
        if (memo[r][c] != -1) return memo[r][c];

        long totalPaths = 0;

        // Option 1: Try to move RIGHT from the current empty cell
        int[] nextRightCell = simulateReflection(r, c + 1, 'R');
        if (nextRightCell != null) {
            totalPaths = (totalPaths + getPaths(nextRightCell[0], nextRightCell[1])) % MOD;
        }

        // Option 2: Try to move DOWN from the current empty cell
        int[] nextDownCell = simulateReflection(r + 1, c, 'D');
        if (nextDownCell != null) {
            totalPaths = (totalPaths + getPaths(nextDownCell[0], nextDownCell[1])) % MOD;
        }

        memo[r][c] = (int) totalPaths;
        return memo[r][c];
    }

    // Follows the mirror reflection chain until landing on an empty cell (0)
    private int[] simulateReflection(int r, int c, char dir) {
        while (r < m && c < n) {
            if (grid[r][c] == 0) {
                // Safely landed on an empty cell
                return new int[]{r, c};
            }
            
            // Hit a mirror: redirect based on incoming direction
            if (dir == 'R') {
                r++;       // Turned down
                dir = 'D'; // New direction is DOWN
            } else {
                c++;       // Turned right
                dir = 'R'; // New direction is RIGHT
            }
        }
        // Went out of bounds during reflection
        return null; 
    }
}
