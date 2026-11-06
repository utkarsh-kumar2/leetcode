import java.util.Arrays;

class Solution {
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {
        Arrays.sort(logs, (a, b) -> Integer.compare(a[1], b[1]));

        int m = queries.length;
        int[][] q = new int[m][2];

        for (int i = 0; i < m; i++) {
            q[i][0] = queries[i];
            q[i][1] = i;
        }

        Arrays.sort(q, (a, b) -> Integer.compare(a[0], b[0]));

        int[] freq = new int[n + 1];
        int[] ans = new int[m];

        int left = 0, right = 0;
        int zeroServers = n;

        for (int[] query : q) {
            int time = query[0];
            int index = query[1];

            // Add logs up to query time
            while (right < logs.length && logs[right][1] <= time) {
                int server = logs[right][0];

                if (freq[server]++ == 0) {
                    zeroServers--;
                }

                right++;
            }

            // Remove logs outside the time window
            while (left < right && logs[left][1] < time - x) {
                int server = logs[left][0];

                if (--freq[server] == 0) {
                    zeroServers++;
                }

                left++;
            }

            ans[index] = zeroServers;
        }

        return ans;
    }
}