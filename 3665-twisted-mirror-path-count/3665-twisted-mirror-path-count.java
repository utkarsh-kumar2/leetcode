
class Solution {
    static final int MOD = 1_000_000_007;

    int m, n;
    int[][] grid;
    int[][][] memo;

    int[] resolve(int r, int c, int dir) {

        if (r < 0 || r >= m || c < 0 || c >= n) {
            return new int[]{-1, -1};
        }

        if (memo[r][c][dir] != -2) {
            return new int[]{
                memo[r][c][dir] / n,
                memo[r][c][dir] % n
            };
        }

        int nr = r, nc = c, nd = dir;

        if (grid[r][c] == 1) {
            if (dir == 0) {
                nr++;
                nd = 1;
            } else {
                nc++;
                nd = 0;
            }

            int[] ans = resolve(nr, nc, nd);
            memo[r][c][dir] =
                ans[0] == -1 ? -1 : ans[0] * n + ans[1];

            return ans;
        }

        memo[r][c][dir] = r * n + c;
        return new int[]{r, c};
    }

    public int uniquePaths(int[][] grid) {
        this.grid = grid;
        m = grid.length;
        n = grid[0].length;

        memo = new int[m][n][2];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                memo[i][j][0] = -2;
                memo[i][j][1] = -2;
            }
        }

        int[][] dp = new int[m][n];
        dp[0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (dp[i][j] == 0) continue;

                // Move right
                int[] right = resolve(i, j + 1, 0);

                if (right[0] != -1) {
                    int r = right[0], c = right[1];
                    dp[r][c] = (int)(
                        (dp[r][c] + (long)dp[i][j]) % MOD
                    );
                }

                // Move down
                int[] down = resolve(i + 1, j, 1);

                if (down[0] != -1) {
                    int r = down[0], c = down[1];
                    dp[r][c] = (int)(
                        (dp[r][c] + (long)dp[i][j]) % MOD
                    );
                }
            }
        }

        return dp[m - 1][n - 1];
    }
}
