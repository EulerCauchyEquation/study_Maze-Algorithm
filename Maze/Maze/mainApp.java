package Maze;

import java.util.PriorityQueue;
import java.util.Stack;

public class mainApp {

	public static void main(String[] args) {

		System.out.println(solution(new int[][] {
			{ 0, 0, 0, 1, 1 }, 
			{ 0, 0, 0, 1, 1 }, 
			{ 0, 1, 0, 1, 1 },
			{ 1, 1, 0, 0, 1 }, 
			{ 0, 0, 0, 0, 0 } 
			}));
		System.out.println(solution(new int[][] {
			{ 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 1, 0 }, 
			{ 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 1, 0 }, 
			{ 0, 0, 0, 0, 0 } 
			}));
		System.out.println(solution(new int[][] {
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
			{ 0, 1, 1, 1, 0, 1, 0, 1, 1, 0}, 
			{ 0, 0, 0, 1, 1, 1, 1, 0, 1, 0}, 
			{ 0, 1, 0, 1, 0, 1, 0, 0, 0, 0}, 
			{ 1, 1, 0, 1, 0, 1, 1, 1, 1, 1}, 
			{ 0, 0, 0, 1, 0, 0, 0, 0, 0, 0}, 
			{ 0, 1, 0, 0, 0, 1, 1, 1, 1, 0}, 
			{ 0, 1, 1, 1, 1, 0, 1, 1, 1, 1}, 
			{ 0, 1, 0, 0, 0, 1, 0, 0, 0, 0}, 
			{ 0, 0, 0, 1, 0, 0, 0, 0, 0, 0}, 
		}));
	}

	public static final int PATH = 0;
	public static final int WALL = 1;
	public static final int VISITED = 2;
	public static final int BACKTRACKED = 3;
	public static int[][] gameboard;
	
	public static int solution(int[][] board) {
		
		gameboard = board;
		Stack<Integer> stack = new Stack<>(); 
		PriorityQueue<Integer> moveCnt = new PriorityQueue<>();

		int startX = 0, startY = 0;
		Point2D cur = new Point2D(startX, startY, 0);
		gameboard[cur.fx][cur.fy] = VISITED;
		
		int init_dir = 0; /* 처음으로 시도해 볼 이동 방향 플래그 */
		
		// DFS방식으로 순회
		for(;;) {
			 
			System.out.println("("+cur.fx + "," + cur.fy + ")\t" +cur.step);
			
			if(isGoal(cur.fx, cur.fy)) 
				moveCnt.offer(cur.step);
			
			boolean isMoved = false;  		/* 이동하였는지 체크 */
			for(int dir = init_dir ; dir < 4; dir++){ 	/* 0:N 1:W 2:S 3:E */
				
				if(isMoveable(cur, dir)) {
					
					stack.push(dir);		/* 이동 방향 스택 */
					cur = move(cur, dir, cur.step + 1);	/* 이동 방향으로 이동 */
					init_dir = 0;			/* 처음 방문하는 위치는 항상 0번 방향부터 */
					isMoved = true;
					break;
				}
			}
			if(!isMoved) {
				
				gameboard[cur.fx][cur.fy] = BACKTRACKED;
				if(stack.isEmpty()) {
					
					if(moveCnt.isEmpty())
						// 나갈 수 있는 방법이 없다
						return 0;
					else
						// 나가는 방법 중 최단 거리 반환
						return moveCnt.poll();
				}
				int d = stack.pop();
				cur = move(cur, (d + 2) % 4, cur.step - 1);  /* 돌아왔던 방향으로 이동 */
				
				init_dir = d + 1;    /* 되돌아간 위치에서 d+1부터 시도 */
			}
		}
	}
	public static Point2D move(Point2D p, int dir, int step) {
		
		int x = p.fx, y = p.fy;
		
		switch (dir) {
			case 0:
				// 북쪽
				x -=1;
				break;
			case 1:
				// 동쪽
				y += 1;
				break;
			case 2:
				// 남쪽
				x += 1;
				break;
			case 3:
				// 서쪽
				y -= 1;
				break;
			default:
				break;
		}
		gameboard[x][y] = VISITED;
		return new Point2D(x, y, step);
	}
	public static boolean isMoveable(Point2D p, int dir) {
		
		int x = p.fx, y = p.fy;
		
		switch (dir) {
			case 0:
				// 북쪽
				x -=1;
				break;
			case 1:
				// 동쪽
				y += 1;
				break;
			case 2:
				// 남쪽
				x += 1;
				break;
			case 3:
				// 서쪽
				y -= 1;
				break;
			default:
				break;
		}
		
		if( x < 0 || x >= gameboard.length || y < 0 || y >= gameboard.length)
			// 보드 밖의 좌표이면
			return false;
		
		if(gameboard[x][y] == VISITED)
			// 이미 방문한 지점이면
			return false;
		
		if(gameboard[x][y] == WALL)
			// 벽으로 막혔으면
			return false;
		
		return true;
	}
	public static boolean isGoal(int x, int y) {
		
		return x == gameboard.length - 1 && y == gameboard.length - 1;
	}
}

class Point2D {
	
	int fx;
	int fy;
	int step;
	
	public Point2D(int fx, int fy, int step) {
		
		this.fx = fx;
		this.fy = fy;
		this.step = step;
	} 

}
