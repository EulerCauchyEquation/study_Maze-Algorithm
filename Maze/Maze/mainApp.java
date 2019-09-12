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
		
		int init_dir = 0; /* ó������ �õ��� �� �̵� ���� �÷��� */
		
		// DFS������� ��ȸ
		for(;;) {
			 
			System.out.println("("+cur.fx + "," + cur.fy + ")\t" +cur.step);
			
			if(isGoal(cur.fx, cur.fy)) 
				moveCnt.offer(cur.step);
			
			boolean isMoved = false;  		/* �̵��Ͽ����� üũ */
			for(int dir = init_dir ; dir < 4; dir++){ 	/* 0:N 1:W 2:S 3:E */
				
				if(isMoveable(cur, dir)) {
					
					stack.push(dir);		/* �̵� ���� ���� */
					cur = move(cur, dir, cur.step + 1);	/* �̵� �������� �̵� */
					init_dir = 0;			/* ó�� �湮�ϴ� ��ġ�� �׻� 0�� ������� */
					isMoved = true;
					break;
				}
			}
			if(!isMoved) {
				
				gameboard[cur.fx][cur.fy] = BACKTRACKED;
				if(stack.isEmpty()) {
					
					if(moveCnt.isEmpty())
						// ���� �� �ִ� ����� ����
						return 0;
					else
						// ������ ��� �� �ִ� �Ÿ� ��ȯ
						return moveCnt.poll();
				}
				int d = stack.pop();
				cur = move(cur, (d + 2) % 4, cur.step - 1);  /* ���ƿԴ� �������� �̵� */
				
				init_dir = d + 1;    /* �ǵ��ư� ��ġ���� d+1���� �õ� */
			}
		}
	}
	public static Point2D move(Point2D p, int dir, int step) {
		
		int x = p.fx, y = p.fy;
		
		switch (dir) {
			case 0:
				// ����
				x -=1;
				break;
			case 1:
				// ����
				y += 1;
				break;
			case 2:
				// ����
				x += 1;
				break;
			case 3:
				// ����
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
				// ����
				x -=1;
				break;
			case 1:
				// ����
				y += 1;
				break;
			case 2:
				// ����
				x += 1;
				break;
			case 3:
				// ����
				y -= 1;
				break;
			default:
				break;
		}
		
		if( x < 0 || x >= gameboard.length || y < 0 || y >= gameboard.length)
			// ���� ���� ��ǥ�̸�
			return false;
		
		if(gameboard[x][y] == VISITED)
			// �̹� �湮�� �����̸�
			return false;
		
		if(gameboard[x][y] == WALL)
			// ������ ��������
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
