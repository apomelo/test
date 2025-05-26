package test.java.util;

import java.util.Random;

public class RandomTest {

    public static void main(String[] args) {
        
        Random r1 = new Random();
        Random r2 = new Random();
        
        int count1_1 = 0;
        int count1_2 = 0;
        
        int count2_1 = 0;
        int count2_2 = 0;
        int count2_3 = 0;
        
        for (int i = 0; i < 1000000; i ++) {
            int i1 = r1.nextInt(2);
            if (i1 == 0) count1_1 ++;
            if (i1 == 1) count1_2 ++;
            int i2 = r1.nextInt(3);
            if (i2 == 0) count2_1 ++;
            if (i2 == 1) count2_2 ++;
            if (i2 == 2) count2_3 ++;
//            System.out.println("r1 第 " + i + " 次随机：" + i1);
//            System.out.println("r1 第 " + i + " 次随机：" + r1.nextInt(2));
//            System.out.println("r2 第 " + i + " 次随机：" + r2.nextInt(10));
        }
        
        System.out.println("count1_1: " + count1_1);
        System.out.println("count1_2: " + count1_2);
        System.out.println("" + count1_1*1.0/count1_2);
        System.out.println();
        System.out.println("count2_1: " + count2_1);
        System.out.println("count2_2: " + count2_2);
        System.out.println("count2_3: " + count2_3);

    }

}
