
import java.util.Arrays;

class Solution {
    public String[] createGrid(int m, int n) {
        char[][] g = new char[m][n];
        
        // Fill the entire grid with walls ('#')
        for (int i = 0; i < m; i++) {
            Arrays.fill(g[i], '#');
        }
        
        // Make the entire top row free ('.')
        Arrays.fill(g[0], '.');
        
        // Make the entire last column free ('.')
        for (int i = 0; i < m; i++) {
            g[i][n - 1] = '.';
        }
        
        // Convert char[][] to String[]
        String[] ans = new String[m];
        for (int i = 0; i < m; i++) {
            ans[i] = new String(g[i]);
        }
        
        return ans;
    }
}
