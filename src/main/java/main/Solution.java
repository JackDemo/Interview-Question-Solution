import java.util.Arrays;
import java.util.Stack;

class Solution {
    public boolean isValid(String s) {

        Stack<Character> stackin = new Stack<Character>();
        for(int i=0;i<s.length();i++){
            stackin.push(s.charAt(i));
        }
        int n = 0;
        while (!stackin.isEmpty()){
            if(!(transfor(stackin.peek())==s.charAt(n))){
                return false;
            }
            stackin.pop();
            n++;
        }
        return true;
    }

    public char transfor(char c){
        char re = ' ';
        if(c == '{')
            re = '}';
        if(c == '[')
            re = ']';
        if(c == '(')
            re = ')';
        if(c == '}')
            re = '{';
        if(c == ']')
            re = '[';
        if(c == ')')
            re = '(';
        return re;
    }

    public static void main(String[] args) {
//        System.out.println(new Solution().isValid("{[]}"));
//        System.out.println(new Solution().isValid("()[]{}"));
        int[] test = {73, 74, 75, 71, 69, 72, 76, 73};
        System.out.println(Arrays.toString(new Solution().dailyTemperatures(test)));
    }
    public int[] dailyTemperatures(int[] T) {
        int[] tem = new int[T.length];
        int n=0;
        int day=0;
        for(int i=0;i<T.length-1;i++){
            n = i+1;
            while(n<T.length){
                if(T[n]>T[i]){
                    day=n-i;
                }else{
                    tem[i] = day;
                    day = 0;
                    break;
                }
                n++;
            }
        }
        tem[T.length-1] = 0;
        return tem;
    }
}