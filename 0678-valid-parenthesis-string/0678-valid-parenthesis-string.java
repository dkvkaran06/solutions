class Solution {
    public boolean checkValidString(String s) {
        int minOpen = 0;
        int maxOpen = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(') {
                minOpen++;
                maxOpen++;
            } else if (c == ')') {
                minOpen--;
                maxOpen--;
            } else if (c == '*') {
                minOpen--; // If treated as ')'
                maxOpen++; // If treated as '('
            }

            // If maxOpen is negative, we have too many ')' brackets
            if (maxOpen < 0) {
                return false;
            }

            // minOpen cannot be negative; reset it to 0
            if (minOpen < 0) {
                minOpen = 0;
            }
        }

        // The string is valid if we can successfully match all open brackets
        return minOpen == 0;
    }
}
