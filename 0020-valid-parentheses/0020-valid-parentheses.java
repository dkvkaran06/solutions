import java.util.Stack;

class Solution {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        
        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(')');
            } else if (c == '{') {
                stack.push('}');
            } else if (c == '[') {
                stack.push(']');
            } else {
                // If stack is empty or the top element doesn't match the closing bracket
                if (stack.isEmpty() || stack.pop() != c) {
                    return false;
                }
            }
        }
        
        // The string is valid only if all brackets found their match
        return stack.isEmpty();
    }
}
