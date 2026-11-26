class Solution {
    public boolean canAliceWin(int n) {
        int stonesToRemove = 10;
        int turns = 0;

        // Keep playing as long as there are enough stones for the current turn
        while (n >= stonesToRemove) {
            n -= stonesToRemove;
            stonesToRemove--;
            turns++;
        }

        // Alice goes first (odd turns: 1, 3, 5...).
        // If total successful turns is odd, Alice made the last valid move and wins.
        return turns % 2 == 1;
    }
}
