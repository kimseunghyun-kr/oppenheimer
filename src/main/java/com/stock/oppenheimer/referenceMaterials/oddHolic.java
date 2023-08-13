package com.stock.oppenheimer.referenceMaterials;

import java.util.ArrayList;

import static java.lang.Math.*;

public class oddHolic {
//    public int[] findMaxMinOddNoPoss(int num) {
//
//    }

    public int MaxRecursible(int num) {
        int numdigit = String.valueOf(num).length();
        int currMaxRecurse = 0;

        if(numdigit == 1) {
            return num%2;
        } else if (numdigit == 2) {
            int tenth = (num/10) % 2;
            int oneth = (num%10)%2 ;
            return MaxRecursible((num/10) + (num % 10)) + tenth + oneth;
        } else {
            for(int i = 1 ; i < numdigit - 1 ; i++) {
                for(int j = i + 1 ; j < numdigit ; j++) {
                    int num1 = (int) (num / pow(10,j));
                    int num2 = (int) (num / pow(10,i) - num1 * pow(10, j-i));
                    int num3 = (int) (num - num1 * pow(10,j) - num2 * pow(10,i));

                    int Onum1 = num1 % 2;
                    int Onum2 = num2 % 2;
                    int Onum3 = num3 % 2;
                    currMaxRecurse = max(currMaxRecurse,MaxRecursible(num1 + num2 + num3)+Onum1+Onum2+Onum3);
                }
            }
            return currMaxRecurse;
        }
    }

    public int MinRecursible(int num) {
        int numdigit = String.valueOf(num).length();
        int currMinRecurse = Integer.MAX_VALUE;

        if(numdigit == 1) {
            return num%2;
        } else if (numdigit == 2) {
            int tenth = (num/10) % 2;
            int oneth = (num%10)%2 ;
            return MaxRecursible((num/10) + (num % 10)) + tenth + oneth;
        } else {
            for(int i = 1 ; i < numdigit - 1 ; i++) {
                for(int j = i + 1 ; j < numdigit ; j++) {
                    int num1 = (int) (num / pow(10,j));
                    int num2 = (int) (num / pow(10,i) - num1 * pow(10, j-i));
                    int num3 = (int) (num - num1 * pow(10,j) - num2 * pow(10,i));

                    int Onum1 = num1 % 2;
                    int Onum2 = num2 % 2;
                    int Onum3 = num3 % 2;

                    int nextStep = MinRecursible(num1 + num2 + num3)+Onum1+Onum2+Onum3;
                    if(nextStep == 3) {
                        int u = 1;
                    }
                    currMinRecurse = min(currMinRecurse, nextStep);
                }
            }
            return currMinRecurse;
        }
    }

}
