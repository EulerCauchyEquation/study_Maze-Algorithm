# 미로 알고리즘 ( Maze Algorithm )

## 1. 소개
미로 알고리즘은 여러가지의 알고리즘 유형이 있다. <br>
이번 실험은 가장 기본이 되는 재귀(recursive)방법 대신 DFS(Depth First Search)방식 중 Stack을 이용하여 풀어보려 한다. <br>
<br>
인접행렬 `N X N`인 Board를 준비하여, 방향을 정해두고 한 칸씩 탐색한다.  <br>
말(user)은 한 칸(1x1)이라는 조건으로 알고리즘을 작성한다.

<img src="/png/png_2.png" width="500" height="509"></img><br>
 <2020 카카오 블라인드 온라인 코딩 테스트 1차 문제>
<br><br>

## 2. 알고리즘 예시
3X3 board판을 예로 들겠다. <br>
외곽은 나갈 수 없으며, 벽으로 막힌 곳은 지나갈 수 없다. 목표지점은 `(3,3)`좌표로, 시작지점은 `(0,0)`으로 세팅한다.<br>
<img src="/png/png_3.png" width="500" height="509"></img>

```java
if( x < 0 || x >= gameboard.length || y < 0 || y >= gameboard.length)
			// 보드 밖의 좌표이면
			return false;		
		if(gameboard[x][y] == VISITED)
			// 이미 방문한 지점이면
			return false;		
		if(gameboard[x][y] == WALL)
			// 벽으로 막혔으면
			return false;
```   
<br><br>

### 2.1 보드판에 나타나는 숫자

칸의 나타나는 숫자는 flag표시이다.<br>
0 - 갈 수있는 길<br>
1 - 벽으로 막혀있음<br>
2 - 방문을 한 표시<br>
3 - Backtrack<br>

<img src="/png/png_4.png" width="300" height="309"></img>

```java
public static final int PATH = 0;
public static final int WALL = 1;
public static final int VISITED = 2;
public static final int BACKTRACKED = 3;
```

### 2.2 탐색 방향

탐색 방향은 북 -> 동 -> 남 - > 서 순으로 한다.

<img src="/png/png_5.png" width="450" height="309"></img>

```java
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
```
<br>

### 2. 3알고리즘 실행

<img src="/png/gif_1.gif" ></img>

지나갈 수 있는 길은 visted 처리하면서, 이동 방향을 stack에 넣는다. 만일 어느 곳으로도 갈 수 없다면, <br>
stack에서 꺼내 방금왔던 이동 방향의 정 반대로

```java
int d = stack.pop();
cur = move(cur, (d + 2) % 4, cur.step - 1);  /* 돌아왔던 방향으로 이동 */
```

돌아가면 되돌아갈 수 있다.  되돌아오면서 이번엔 다른 방향으로 탐색하면서 돌아온다. 다시 갈 수 있는 길이 <br>
있다면 거기부터 다시 탐색한다.

---------------------------------


<br><br>
