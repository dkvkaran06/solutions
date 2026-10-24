class Solution {
    // Total days in each month for a non-leap year
    private final int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public int countDaysTogether(String arriveAlice, String leaveAlice, String arriveBob, String leaveBob) {
        // Convert all date strings to their absolute day number of the year
        int startA = convertToDayOfYear(arriveAlice);
        int endA = convertToDayOfYear(leaveAlice);
        int startB = convertToDayOfYear(arriveBob);
        int endB = convertToDayOfYear(leaveBob);

        // Find the intersection boundary of the two date ranges
        int overlapStart = Math.max(startA, startB);
        int overlapEnd = Math.min(endA, endB);

        // Calculate overlap length. If they don't overlap, return 0.
        return Math.max(0, overlapEnd - overlapStart + 1);
    }

    private int convertToDayOfYear(String date) {
        // Parse month and day from "MM-DD" format
        int month = Integer.parseInt(date.substring(0, 2));
        int day = Integer.parseInt(date.substring(3, 5));

        int dayCount = 0;
        // Add the total days of all previous completed months
        for (int i = 0; i < month - 1; i++) {
            dayCount += daysInMonth[i];
        }
        
        // Add the remaining days of the current month
        return dayCount + day;
    }
}
