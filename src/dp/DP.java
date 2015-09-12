package dp;

/**
 * ダイナミックプログラミング
 * 
 * @author admin
 */
public class DP {

	private int[] c1, c2;
	private int[][] memo;
	private int restriction;

	public int startDP(int[] costFunc_l, int[] costFunc_u, int cost) {
		c1 = costFunc_l;
		c2 = costFunc_u;
		int max = 0;
		int size = c1.length;

		for (int i = 0; i < size; i++) {
			if (max < c2[i]) {
				max = c2[i];
			}
		}
		restriction = max;
		memo = new int[size + 1][cost + max + 1];
		for (int i = 0; i <= size; i++) {
			for (int j = 0; j <= cost + max; j++) {
				memo[i][j] = -1;
			}
		}
		return comeBackDP(size, cost + restriction);
	}

	public int comeBackDP(int N, int cost) {
		if (memo[N][cost] != -1) {
			return memo[N][cost];
		}

		if (N == 0 || cost <= restriction) {
			if (cost <= restriction) {
				memo[N][cost] = 0;
			} else {
				memo[N][cost] = 10000;
			}
		} else {
			int road = comeBackDP(N - 1, cost);
			if (cost > restriction) {
				int c = cost + c1[N - 1] - c2[N - 1];
				int n = (comeBackDP((N - 1), c) + c1[N - 1]);
				memo[N][cost] = Math.min(road, n);
			} else {
				memo[N][cost] = road;
			}
		}

		return memo[N][cost];
	}

	public int comeBackDP(int studentNum, int[] costFunc_l, int[] costFunc_u,
			int r, int r2, int pos) {
		/*
		 * costFuncには[下限制約][上限制約]が入っているとする 下限制約のみ行う
		 */
		int size = costFunc_l.length;

		for (int i = pos; i < size; i++) {
			if (costFunc_l[i] == 0)
				continue;

			if (studentNum <= 0) {// 入れられる生徒の数がいなくなったら
				return r2;
			}
			int l = costFunc_l[i];
			costFunc_l[i] = 0;
			int num = comeBackDP((studentNum + l - costFunc_u[i]), costFunc_l,
					costFunc_u, r, (r2 + l), (pos + 1));
			costFunc_l[i] = l;
			if (r > num) {
				r = num;
			}
		}
		return r;
	}
}
