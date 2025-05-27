package test.base;

public class CharToInt {

    public static void main(String[] args) {
        char c = '1';
        int i = (int) c;
        System.out.println(i);

        int i2 = 1;
        char c2 = (char) i2;
        System.out.println("c2: " + c2);

        char c3 = '1';
        System.out.println(c3);

        String s = "1.wav";
        System.out.println(s.contains(".wav"));
    }

}
