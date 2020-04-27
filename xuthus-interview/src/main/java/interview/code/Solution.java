/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: Solution
 * Author:   Xuthus
 * Date:     2020/4/13 12:54
 * Description: 面试题解决方案
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package interview.code;

import org.junit.Test;

import java.util.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈面试题解决方案〉
 *
 * @author Xuthus
 * @create 2020/4/13
 * @since 1.0.0
 */
public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        // 1、麻将问题
        // mahjong();

        // 2、走格子问题
        // solution.movingCount(36,100,100);
        // System.out.println(solution.count);

        // 3、矩阵中的路径
        // boolean flag = solution.hasPath("ABCESFCSADEE".toCharArray(),3,4,"SEE".toCharArray());
        // System.out.println(flag);

        // 4、滑动窗口最大值
        // System.out.println(solution.maxInWindows2(new int[]{2,3,4,2,6,2,5,1},3));

        // 5、矩阵变换
        // solution.GetResult(5);

        // 6、计算字符个数
        // solution.findTargetCount();

        // 7、字符串最后一个单词的长度
        // solution.lastWordLength();

        // 8、随机数去重和排序
        // solution.handleRandomInt();

        // 9、字符串分隔
        // solution.splitString();

        // 10、质数因子
        // solution.splitBigNum();

        // 11、百钱买百鸡
        // solution.buySomething();

        // 12、合并表记录
        // solution.mergeSameIndex();

        // 13、求给定二叉树的最小深度
        solution.minDepthTree();
    }

    /**
     * 题目描述：
     * 清一色是麻将番种之一，指由一种花色的序数牌组成的和牌.
     * 数字1-9，每个数字最多有4张牌
     * 我们不考虑具体花色，我们只看数字组合。
     * 刻子：三张一样的牌；如: 111, 222, 333, ..., 999
     * 顺子：三张连续的牌；如: 123, 234, 345, ..., 789
     * 对子：两张相同的牌；如: 11, 22, 33, ..., 99
     * 需要实现一个程序，判断给定牌，是否可以和牌（胡牌）。
     * 和牌要求：
     * - 麻将牌张数只能是 2, 5, 8, 11, 14
     * - 给定牌可以组合成，除1个对子以外其他都是刻子或顺子
     * 举例： - "11" -> "11", 1对子，可以和牌
     * - "11122233" -> "111"+"222"+"33", 2刻子，1对子，可以
     * - "11223344567" -> "11"+"234"+"234"+"567", 1对子，3顺子，可以
     * -> "123"+"123"+"44"+"567", 另一种组合，也可以
     * 输入描述:
     * 合法C字符串，只包含'1'-'9'，且已经按从小到大顺序排好；字符串长度不超过15。同一个数字最多出现4次，与实际相符。
     */
    private static void mahjong() {
        Scanner scanner = new Scanner(System.in);
        String input;
        while (scanner.hasNext()) {
            input = scanner.next();
            if (input == "exit") {
                break;
            }

            check(input);
        }
    }

    private static void check(String input) {
        if (!checkLength(input)) {
            System.out.println(false);
            return;
        }

        long timeStart = System.nanoTime();
        long freeMemoryStart = Runtime.getRuntime().freeMemory();
        // 先取对子，然后判断剩下的是不是符合顺子+刻子的组合
        Set<Character> duiSet = new HashSet<>();
        char[] chars = input.toCharArray();
        List<Integer> list = new ArrayList<>(chars.length - 2);
        for (int i = 0; i < chars.length; i++) {
            if (canDui(duiSet, chars, i)) {
                duiSet.add(chars[i]);
                // 判断剩下的是不是顺子+刻子的组合,如果是则返回true,不是则尝试下一个对子
                initList(list, chars, i);
                if (isShunAndKe(list)) {
                    System.out.println(true);
                    return;
                }
            }
        }

        // 全部尝试过都不满足则输出false
        System.out.println(false);
    }

    private static void initList(List<Integer> list, char[] chars, int i) {
        list.clear();
        for (int j = 0; j < chars.length; j++) {
            if (j != i && j != i + 1) {
                list.add((int) chars[j]);
            }
        }
    }

    /**
     * 判断是不是顺子+刻子的组合
     *
     * @param list
     */
    private static boolean isShunAndKe(List<Integer> list) {
        Integer head = list.get(0);
        // 获取刻子,三个连续相等
        if (2 < list.size() && Objects.equals(head,
                list.get(1)) && Objects.equals(head,
                list.get(2))) {
            // 移除头部三个
            list.remove(0);
            list.remove(0);
            list.remove(0);
            return list.isEmpty() || isShunAndKe(list);
        } else if (list.indexOf(head + 1) > 0 && list.indexOf(head + 2) > 0) {
            list.remove(0);
            list.remove(list.indexOf(head + 1));
            list.remove(list.indexOf(head + 2));
            return list.isEmpty() || isShunAndKe(list);
        } else {
            // 第一个数字既不能组成刻子也不能组成顺子
            return false;
        }
    }

    /**
     * 判断是都能作为对子
     *
     * @param duiSet    尝试成为对子的集合
     * @param chars
     * @param currIndex
     * @return
     */
    private static boolean canDui(Set<Character> duiSet, char[] chars, int currIndex) {
        return !duiSet.contains(chars[currIndex])
                && currIndex < chars.length - 1
                && chars[currIndex] == chars[currIndex + 1];
    }

    /**
     * 检查输入数据长度是否正确
     *
     * @param input
     * @return
     */
    private static boolean checkLength(String input) {
        return 0 < input.length() && input.length() < 15 && (input.length() - 2) % 3 == 0;
    }

    /**
     * 列举上下左右四种走法,每走一步的坐标变化
     */
    private int[] xStep = {0, 0, -1, 1};
    private int[] yStep = {1, -1, 0, 0};

    // 走的格子计数器,坐标原点的格子已经站住了，所以是从1开始
    private int count = 1;

    /**
     * 题目描述
     * 地上有一个m行和n列的方格。一个机器人从坐标0,0的格子开始移动，每一次只能向左，右，上，下四个方向移动一格，但是不能进入行坐标和列坐标的数位之和大于k的格子。 例如，当k为18时，机器人能够进入方格（35,
     * 37），因为3+5+3+7 = 18。但是，它不能进入方格（35,38），因为3+5+3+8 = 19。请问该机器人能够达到多少个格子？
     *
     * @param threshold 指定的限制的数字
     * @param rows      指定方格的行数
     * @param cols      指定方格的列数
     * @return
     */
    public int movingCount(int threshold, int rows, int cols) {
        if (threshold < 0 || rows <= 0 || cols <= 0) {
            return 0;
        }

        // 走一格记一下数，走过了的不计
        boolean[][] history = new boolean[rows][cols];
        history[0][0] = true;
        move(threshold, rows, cols, 0, 0, history);
        return count;

    }

    private void move(int threshold, int rows, int cols, int orgX, int orgY, boolean[][] history) {
        int x;
        int y;
        // 尝试走当前格子上下左右四个格子
        for (int i = 0; i < 4; i++) {
            // 变换坐标
            x = orgX + xStep[i];
            y = orgY + yStep[i];

            // 没走过，且合法则将计数器增加
            if (isValid(threshold, rows, cols, x, y) && !history[x][y]) {
                count++;
                history[x][y] = true;
                // 以当前格子为基础继续下一步移动
                move(threshold, rows, cols, x, y, history);
            }

        }
    }

    private boolean isValid(int threshold, int rows, int cols, int x, int y) {
        return 0 <= x && x < rows && 0 <= y && y < cols && sum(x) + sum(y) <= threshold;
    }

    /**
     * 获取数字的位数之和
     */
    private int sum(int i) {
        int sum = 0;
        while (i > 0) {
            sum += i % 10;
            i = i / 10;
        }
        return sum;
    }

    /**
     * 请设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。
     * 路径可以从矩阵中的任意一个格子开始，每一步可以在矩阵中向左，向右，向上，向下移动一个格子。
     * 如果一条路径经过了矩阵中的某一个格子，则该路径不能再进入该格子。 例如
     * |a   b   c   e|
     * |s   f   c   s|
     * |a   d   e   c|
     * 矩阵中包含一条字符串"bcced"的路径，但是矩阵中不包含"abcb"路径，
     * 因为字符串的第一个字符b占据了矩阵中的第一行第二个格子之后，路径不能再次进入该格子。
     *
     * @param matrix
     * @param rows
     * @param cols
     * @param str
     * @return
     */
    public boolean hasPath(char[] matrix, int rows, int cols, char[] str) {
        boolean[] history = new boolean[matrix.length];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // 查找字符串的第一个字符
                if (findSubPath(matrix, rows, cols, str, i, j, 0, history)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean findSubPath(char[] matrix, int rows, int cols, char[] str, int x, int y, int strIndex, boolean[] history) {
        // 超出边界返回false
        if (x < 0 || x >= rows || y < 0 || y >= cols) {
            return false;
        }

        // 当前坐标对应数组的下标
        int curr = x * cols + y;
        // 如果当前位置对应的值和要找的字符不一致，或者已经访问过了则false
        if (matrix[curr] != str[strIndex] || history[curr]) {
            return false;
        }

        // 如果找到了最后一个字符串，则说明子串全部找到了
        if (strIndex == str.length - 1) {
            return true;
        }

        // 如果当前位置找到了，则继续向下找
        history[curr] = true;

        // 如果当前位置不是第一行，向上找到下一个字符串
        strIndex++;
        boolean flag = false;
        for (int i = 0; i < 4; i++) {
            flag = flag || findSubPath(matrix, rows, cols, str, x + xStep[i], y + yStep[i], strIndex, history);
        }
        history[curr] = flag;
        return flag;
    }

    /**
     * 给定一个数组和滑动窗口的大小，找出所有滑动窗口里数值的最大值。
     * 例如，如果输入数组{2,3,4,2,6,2,5,1}及滑动窗口的大小3，那么一共存在6个滑动窗口，他们的最大值分别为{4,4,6,6,6,5}；
     * 针对数组{2,3,4,2,6,2,5,1}的滑动窗口有以下6个：
     * {[2,3,4],2,6,2,5,1}，{2,[3,4,2],6,2,5,1}，{2,3,[4,2,6],2,5,1}，{2,3,4,[2,6,2],5,1}，{2,3,4,2,[6,2,5],1}，{2,3,4,2,6,[2,5,1]}。
     *
     * @param num
     * @param size
     * @return
     */
    public ArrayList<Integer> maxInWindows(int[] num, int size) {
        ArrayList<Integer> list = new ArrayList(num.length - size + 1);
        if (size == 0) {
            return list;
        }
        int max = 0;
        for (int i = 0; i < num.length - size + 1; i++) {

            if (i > 0 && num[i - 1] < max) {
                max = Math.max(max, num[i + size - 1]);
                list.add(max);
                continue;
            }

            max = 0;
            for (int j = i; j < i + size; j++) {
                max = Math.max(max, num[j]);
            }
            list.add(max);
        }
        return list;
    }

    public ArrayList<Integer> maxInWindows2(int[] num, int size) {
        ArrayList<Integer> list = new ArrayList(num.length - size + 1);
        if (size == 0) {
            return list;
        }

        // 从大到小自动排序的队列
        PriorityQueue<Integer> high = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });

        for (int i = 0; i < num.length; i++) {
            if (i >= size && !high.isEmpty()) {
                high.remove(num[i - size]);
            }
            high.offer(num[i]);
            if (high.size() == size) {
                list.add(high.peek());
            }
        }
        return list;
    }

    /**
     * 题目说明
     * 蛇形矩阵是由1开始的自然数依次排列成的一个矩阵上三角形。
     *
     * @param num
     */
    public void GetResult(int num) {
        // 初始值a[0][0] = 1；
        // a[0][j] = (j+3)*j/2 +1
        // a[i][j] = (i+2)*(i+2j-1)/2 +(j+1)*j/2 -j+2
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num - i; j++) {
                System.out.print((i + 2) * (i + 2 * j - 1) / 2 + (j + 1) * j / 2 - j + 2 + "\t");
            }
            System.out.println("");
        }
    }

    /**
     * 计算字符串最后一个单词的长度，单词以空格隔开。
     */
    public void lastWordLength() {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        str = str.trim();
        char[] arr = str.toCharArray();
        int count = 0;
        for (int i = arr.length; i > 0; i--) {
            count++;
            if (arr[i - 1] == ' ') {
                System.out.println(count - 1);
                return;
            }
        }

        System.out.println(count);
    }

    /**
     * 题目描述
     * 写出一个程序，接受一个由字母和数字组成的字符串，和一个字符，然后输出输入字符串中含有该字符的个数。不区分大小写。
     */
    public void findTargetCount() {
        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        String str = "";
        String tar = "";
        while (sc.hasNext()) {
            if (flag) {
                str = sc.next();
            } else {
                tar = sc.next();
            }

            if ("exit".equalsIgnoreCase(str)) {
                System.exit(0);
            }

            flag = !flag;
            if (flag) {
                char target = tar.toCharArray()[0];
                char[] arr = str.toCharArray();
                System.out.println(findTargetCount(arr, target, 0, arr.length - 1));
            }

        }


    }

    private int findTargetCount(char[] arr, char target, int left, int right) {
        if (left == right) {
            return Objects.equals(arr[left], target) ? 1 : 0;
        }
        int mid = (left + right) / 2;
        return findTargetCount(arr, target, left, mid) + findTargetCount(arr, target, mid + 1, right);
    }


    /**
     * 明明想在学校中请一些同学一起做一项问卷调查，为了实验的客观性，他先用计算机生成了N个1到1000之间的随机整数（N≤1000
     * ），对于其中重复的数字，只保留一个，把其余相同的数去掉，不同的数对应着不同的学生的学号。
     * 然后再把这些数从小到大排序，按照排好的顺序去找同学做调查。请你协助明明完成“去重”与“排序”的工作
     * (同一个测试用例里可能会有多组数据，希望大家能正确处理)。
     * Input Param
     * n               输入随机数的个数
     * inputArray      n个随机整数组成的数组
     * Return Value
     * OutputArray    输出处理后的随机整数
     * <p>
     * 样例输入解释：
     * 第一组是3个数字，分别是：2，2，1。
     * 第二组是11个数字，分别是：10，20，40，32，67，40，20，89，300，400，15。
     * 输入：
     * 3
     * 2
     * 2
     * 1
     * 11
     * 10
     * 20
     * 40
     * 32
     * 67
     * 40
     * 20
     * 89
     * 300
     * 400
     * 15
     * <p>
     * 输出：
     * 1
     * 2
     * 10
     * 15
     * 20
     * 32
     * 40
     * 67
     * 89
     * 300
     * 400
     */
    public void handleRandomInt() {
        Scanner sc = new Scanner(System.in);
        int length = sc.nextInt();

        String lenStr;
        int[] intArr = new int[length];
        // String p = ".*(\r\n|[\n\r\u2028\u2029\u0085])|.+[0-9]+$";
        while (sc.hasNextInt()) {
            if (length == 0) {
                sc.nextLine();
                lenStr = sc.nextLine();
                if (lenStr.isEmpty()) {
                    break;
                } else {
                    length = Integer.valueOf(lenStr);
                    intArr = Arrays.copyOf(intArr, intArr.length + length);
                }
            }

            for (int i = 0; i < length; i++) {
                intArr[i] = sc.nextInt();
            }
            length = 0;
        }

        Arrays.sort(intArr);
        System.out.println(intArr[0]);
        for (int i = 1; i < intArr.length; i++) {
            if (intArr[i] != intArr[i - 1]) {
                System.out.println(intArr[i]);
            }
        }
    }


    public void splitString() {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {
            String input = sc.next();
            int len = input.length();
            int m = len % 8;


            for (int i = 0; i < len >> 3; i++) {
                if ((i + 1) << 3 <= len) {
                    System.out.println(input.substring(i << 3, (i + 1) << 3));
                }
            }

            if (m > 0) {
                StringBuffer sb = new StringBuffer(8);
                int start = len <= 8 ? 0 : len - m;
                String tail = input.substring(start, len);
                sb.append(tail);
                for (int i = 0; i < 8 - m; i++) {
                    sb.append("0");
                }
                System.out.println(sb.toString());
            }
        }
    }


    public void splitBigNum() {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            long n = sc.nextLong();
            StringBuffer sb = new StringBuffer();
            for (long i = 2; i < n; i++) {
                while (n % i == 0) {
                    sb.append(i + " ");
                    n /= i;
                }
            }
            System.out.println(sb.toString());
        }
    }


    /**
     * 题目描述
     * 公元前五世纪，我国古代数学家张丘建在《算经》一书中提出了“百鸡问题”：
     * 鸡翁一值钱五，鸡母一值钱三，鸡雏三值钱一。
     * 百钱买百鸡，问鸡翁、鸡母、鸡雏各几何？
     */
    public void buySomething() {
        for (int x = 0; x < 14; x++) {
            for (int y = 0; y <= 25; y++) {
                if (7 * x + 4 * y == 100) {
                    System.out.println(x + " " + y + " " + (100 - x - y));
                }
            }
        }
    }

    /**
     * 写出一个程序，接受一个正浮点数值，输出该数值的近似整数值。如果小数点后数值大于等于5,向上取整；小于5，则向下取整。
     */
    public void similarInteger(){
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextFloat()) {
            float f = sc.nextFloat();
            float m = f * 10 % 10;
            if(m<5){
                System.out.println((int)f);
            }else{
                System.out.println((int)f + 1);
            }
        }
        sc.close();
    }

    /**
     * 数据表记录包含表索引和数值（int范围的整数），请对表索引相同的记录进行合并，即将相同索引的数值进行求和运算，
     * 输出按照key值升序进行输出。
     */
    public void mergeSameIndex() {
        Scanner sc = new Scanner(System.in);
        Map<Integer, Integer> map = null;
        int k = 0;
        int v = 0;
        while (sc.hasNextInt()) {
            int len = sc.nextInt();
            map = new TreeMap<>();
            for (int i = 0; i < len; i++) {
                k = sc.nextInt();
                v = sc.nextInt();
                if (map.containsKey(k)) {
                    v += map.get(k);
                }
                map.put(k, v);
            }

            map.forEach((x,y) ->{
                System.out.println(x+" "+y);
            });

        }
        sc.close();
    }

    /**
     * 题目描述
     * 求给定二叉树的最小深度。最小深度是指树的根结点到最近叶子结点的最短路径上结点的数量
     * @return
     */
    public int minDepthTree(){
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.left.left = new TreeNode(3);
        root.right = new TreeNode(3);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(5);
        if(root == null){
            return 0;
        }
        int result = findNode(root);
        System.out.println(result);
        return result;
    }

    private int findNode(TreeNode root) {
        if (root == null) {
            return 0;
        }

        if (root.left == null && root.right != null) {
            return findNode(root.right) + 1;
        }

        if (root.right == null && root.left != null) {
            return findNode(root.left) + 1;
        }

        int left = findNode(root.left);
        int right = findNode(root.right);
        return left > right ? right + 1 : left + 1;
    }

    /**
     * 计算逆波兰式（后缀表达式）的值
     * 运算符仅包含"+","-","*"和"/"，被操作数可能是整数或其他表达式
     */
    @Test
    public void evalRPN() {
        String[] tokens = new String[]{"3","-4","+"};

        int result = 0;
        if(tokens.length == 1){
            result = Integer.valueOf(tokens[0]);
        }
        Stack<Integer> stack = new Stack<>();

        int p1 = 0;
        int p2 = 0;
        for (String token: tokens){
            if(token.matches("[-]?[0-9]+")){
                stack.add(Integer.valueOf(token));
            }else{
                p2 = stack.pop();
                p1 = stack.pop();
                switch (token){
                    case "+": result = p1 + p2;
                        break;
                    case "-": result = p1 - p2;
                        break;
                    case "*": result = p1 * p2;
                        break;
                    case "/": result = p1 / p2;
                        break;
                    default: return;
                }
                stack.add(result);
            }
        }
        System.out.println(result);
    }

    /**
     * 对于给定的n个位于同一二维平面上的点，求最多能有多少个直线
     */
    @Test
    public void maxLines() {
        Point[] points = new Point[]{new Point(1,1),new Point(1,2),new Point(3,3),new Point(2,4)};
        Point p;
        Point c;
        Set<Double> kSet = null;
        // 斜率
        double k = 0f;
        int count = 0;
        int rep = 0;
        for(int i=0;i < points.length;i++){
            // 当前点
            c = points[i];
            kSet = new HashSet<>();
            rep = 0;
            for(int j=i+1;j < points.length;j++) {
                p = points[j];
                k = (p.x - c.x) == 0 ? 0 : (double)(p.y - c.y) / (double)(p.x - c.x);
                if (kSet.contains(k)) {
                    rep++;
                }else {
                    kSet.add(k);
                }
            }

            count += kSet.size() - rep;
            if(kSet.size() == 1){
                break;
            }
        }
        System.out.println(count);
    }

    /**
     * 对于给定的n个位于同一二维平面上的点，求最多能有多少个点位于同一直线上
     */
    @Test
    public void maxPoints() {
        Point[] points = new Point[]{new Point(0, 0), new Point(1, 1), new Point(1, -1)};
        Point p;
        Point c;
        Map<String,Integer> kMap = null;
        int max = 0;
        for (int i = 0; i < points.length; i++) {
            // 当前点
            c = points[i];
            kMap = new HashMap<>();

            int count = 1;
            int maxC = 0;
            for (int j = i + 1; j < points.length; j++) {
                p = points[j];

                // 如果点重合
                if (p.x == c.x && p.y == c.y) {
                    count++;
                    continue;
                }

                int d = mode(p.y - c.y, p.x - c.x);
                String k = (p.y - c.y) / d + ":" + (p.x - c.x) / d;
                kMap.put(k, kMap.getOrDefault(k, 0) + 1);
                maxC = Math.max(maxC, kMap.get(k));
            }

            max = Math.max(maxC + count, max);
            if (kMap.size() == 1) {
                break;
            }
        }
        System.out.println(max);
    }

    private int mode(int a, int b) {
        return b == 0 ? a : mode(b, a % b);
    }

    /**
     * 二叉树的序列化是指：
     * 把一棵二叉树按照某种遍历方式的结果以某种格式保存为字符串，从而使得内存中建立起来的二叉树可以持久保存。
     * 序列化可以基于先序、中序、后序、层序的二叉树遍历方式来进行修改，序列化的结果是一个字符串，
     * 序列化时通过 某种符号表示空节点（#），以 ！ 表示一个结点值的结束（value!）
     */
    @Test
    public void serializeBinaryTree(){
        int [] arr = {8,6,10,5,7,9,11};
        TreeNode root = getTree(arr);
        String str = serialize(root, new StringBuilder()).toString();
        root = deserialize(str.split("!"));
    }

    private TreeNode getTree(int[] arr) {
        TreeNode lastNode = null;
        TreeNode root = new TreeNode(arr[0]);
        int index = 1;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (index < arr.length && !queue.isEmpty()) {
            lastNode = queue.poll();
            lastNode.left = new TreeNode(arr[index]);
            queue.offer(lastNode.left);


            if (index + 1 < arr.length) {
                lastNode.right = new TreeNode(arr[index+1]);
                queue.offer(lastNode.right);
            }
            index += 2;
        }

        return root;
    }

    StringBuilder serialize(TreeNode root,StringBuilder sb) {
        if (root == null) {
            sb.append("#!");
            return sb;
        }

        sb.append(root.val).append("!");
        serialize(root.left, sb);
        serialize(root.right, sb);
        return sb;
    }

    int index = 0;
    TreeNode deserialize(String[] arr) {
        if(index < arr.length){
            if("#".equals(arr[index])){
                index ++;
                return null;
            }
            TreeNode root = new TreeNode(Integer.parseInt(arr[index]));
            index ++;
            root.left = deserialize(arr);
            root.right = deserialize(arr);
            return root;
        }else{
            return null;
        }
    }

    private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
    private class Point {
        int x;
        int y;

        Point() {
            x = 0;
            y = 0;
        }

        Point(int a, int b) {
            x = a;
            y = b;
        }
    }

}
