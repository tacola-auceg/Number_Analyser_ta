package org.apache.nutch.analysis.unl.ta;

//import org.apache.nutch.unl.utils.*;
import java.util.*;

public class Method {

    static String x = "";
    static String y = "";
    static UnicodeConverter TC;
    static ADictionary dictionary;
    static Stack originalStack;
    static Stack originalStack1;

    public static int analyse(String inputString, Stack allStk, boolean analysePart) {
        try {
            System.out.println("Enter into Method analyse method");

            // not necessary now
            int c = 0;
            boolean checkNum;
            boolean isNoun;
            boolean isRoot;
            boolean isVerb;
            boolean isTourism;

            boolean isTamil = true;
            boolean isNumber;


            for (int i = 0; i < inputString.length(); i++) {

                int j = inputString.charAt(i);
                // this for loop is for remove the special character and unwanted character and english letter.
                if ((j > 57) && (j <= 127)) {
                    System.out.println("Enter into Method nonTamil method");
                    isTamil = false;
                    return 0;
                }
            }


            byte topElmt[] = UnicodeConverter.convert(inputString);

            checkNum = NounMisc.checkNumber(allStk, inputString);
            if (checkNum) {
                System.out.println("Enter into Method checkNum method");
                return c;
            }

            // root word

            int stkSize = allStk.size();
            isRoot = ADictionary.check(allStk, topElmt);
            if (isRoot) {
                System.out.println("Enter into Method isRoot method");
                return c;
            }

            c = 17;

            isTourism = ADictionary.tourismDomain(allStk, topElmt);

            if (isTourism) {
                System.out.println("Enter into Method isTourism method");
                return c;
            }


            // prefix
            boolean chkPrefix = Noun.checkPrefix(allStk, topElmt, analysePart);
            if (chkPrefix) {
                System.out.println("Enter into Method chkPrefix method");
                //return c;
            }

//System.out.println("out next");
            // noun analysis
            isNoun = Noun.check(allStk, topElmt, analysePart);

            if (isNoun) {
                System.out.println("Enter into Method isNoune method");
                return c;
            }

          //  isNumber=ADictionary.Number_Handle(allStk, topElmt);

         //   if(isNumber){
               // System.out.println("Enter into Method isNoune method");
              //  return c;
          //  }

            // verb analysis
            isVerb = Verb.check(allStk, topElmt, analysePart);

            // mARRu
            if (isVerb) {
                System.out.println("Enter into Method isVerb method");
                return c;
            }

            if (!(isNoun || isVerb)) {
                System.out.println("Enter into Method isNoun | isVerb method");
                if (Doubleword.check(allStk, topElmt, analysePart)) {
                    System.out.println("Enter into Method DoubleWord.check method");
                    return c;
                }
            }

            return 0;
        } catch (Exception ex) {
            String xxx = "";
            ex.printStackTrace();
            return 0;
        }

    }
}
