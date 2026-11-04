
import java.util.*;

class Solution {
    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;
        int m = n - x + 1;

        long[] cost = new long[m];

        TreeMap<Integer, Integer> left = new TreeMap<>();
        TreeMap<Integer, Integer> right = new TreeMap<>();

        long sumL = 0, sumR = 0;
        int sizeL = 0, sizeR = 0;

        for (int i = 0; i < n; i++) {
            int v = nums[i];

            if (left.isEmpty() || v <= left.lastKey()) {
                left.merge(v, 1, Integer::sum);
                sumL += v;
                sizeL++;
            } else {
                right.merge(v, 1, Integer::sum);
                sumR += v;
                sizeR++;
            }

            if (i >= x) {
                int out = nums[i - x];

                if (left.containsKey(out)) {
                    remove(left, out);
                    sumL -= out;
                    sizeL--;
                } else {
                    remove(right, out);
                    sumR -= out;
                    sizeR--;
                }
            }

            while (sizeL > sizeR + 1) {
                int a = left.lastKey();
                remove(left, a);
                sumL -= a;
                sizeL--;

                right.merge(a, 1, Integer::sum);
                sumR += a;
                sizeR++;
            }

            while (sizeL < sizeR) {
                int a = right.firstKey();
                remove(right, a);
                sumR -= a;
                sizeR--;

                left.merge(a, 1, Integer::sum);
                sumL += a;
                sizeL++;
            }

            if (i >= x - 1) {
                long median = left.lastKey();
                cost[i - x + 1] =
                    median * sizeL - sumL +
                    sumR - median * sizeR;
            }
        }

        // DP: dp[j][i] = minimum cost to select
        // exactly j subarrays using first i elements.

        long INF = Long.MAX_VALUE / 4;
        long[][] dp = new long[k + 1][n + 1];

        for (long[] row : dp) {
            Arrays.fill(row, INF);
        }

        dp[0][0] = 0;

        for (int j = 0; j <= k; j++) {
            for (int i = 0; i <= n; i++) {

                if (dp[j][i] == INF) continue;

                // Skip element
                if (i < n) {
                    dp[j][i + 1] = Math.min(
                        dp[j][i + 1], dp[j][i]
                    );
                }

                // Take subarray [i, i+x-1]
                if (j < k && i + x <= n) {
                    dp[j + 1][i + x] = Math.min(
                        dp[j + 1][i + x],
                        dp[j][i] + cost[i]
                    );
                }
            }
        }

        return dp[k][n];
    }

    private void remove(TreeMap<Integer, Integer> map, int val) {
        int cnt = map.get(val);

        if (cnt == 1) {
            map.remove(val);
        } else {
            map.put(val, cnt - 1);
        }
    }
}
