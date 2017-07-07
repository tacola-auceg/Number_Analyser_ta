package org.apache.nutch.analysis.unl.ta;
//import org.apache.nutch.unl.utils.*;
import java.util.*;

// import demo.*;
public class Doubleword {

    static String x = "";
    static UnicodeConverter TC;
    static int c = 0;

    public static boolean check(Stack allStk, byte[] topElmt, boolean analysePart) {
        System.out.println("Enter into DOUBLEWORD check method");
        boolean isDoubleword = false;
        // rule chking flow randomly chosen.

        try {
            //////////System.out.println("rule_a");
            if (rule_a(allStk, topElmt)) {
                //System.out.println("rule_a True");
                isDoubleword = true;
            }

            if ((isDoubleword != true) && rule_c1(allStk, topElmt)) {
                //System.out.println("rule_c1 True");
                isDoubleword = true;
            }
            if ((isDoubleword != true) && rule_c(allStk, topElmt, analysePart)) {
                //System.out.println("rule_c True");
                isDoubleword = true;
            }

            if ((isDoubleword != true) && rule_b(allStk, topElmt)) {
                //System.out.println("rule_b True");
                isDoubleword = true;
            }
            if ((isDoubleword != true) && rule_e(allStk, topElmt)) {
                //System.out.println("rule_e True");
                isDoubleword = true;
            }
            if ((isDoubleword != true) && rule_d(allStk, topElmt)) {
                //System.out.println("rule_d True");
                isDoubleword = true;
            }
            if ((isDoubleword != true) && rule_double(allStk, topElmt)) {
                //System.out.println("rule_double True");
                isDoubleword = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //  ////////System.out.println( "CCCCCC"+ inputString+":"+isDoubleword);
        return isDoubleword;
    }

    // rule a <Noun/PronounClitic/InterrogativeNoun+Clitic>
    public static boolean rule_a(Stack allStk, byte[] topElmt) {
        System.out.println("Enter into DOUBLEWORD rule_a method");

        Stack s = new Stack();

        s.push(new Entry(topElmt, -1));
        Sandhi.kctp(s);

        if (Clitic.check(s)) {
            ////////System.out.println("Clitic True");
            c++;
            if (ADictionary.noun_PronounClitic_InterrogativeNoun(s)) {
                allStk.push(s);
                return true;
            }
            if (Sandhi.handleException(s)) {
                c++;
                if (ADictionary.noun_PronounClitic_InterrogativeNoun(s)) {
                    allStk.push(s);
                    return true;
                }
            }
        } else {
            ////////System.out.println("RULE a false");
        }
        System.out.println("RULE a false");
        return false;
    }

    // rule b <Postposition/Adverb+Clitic>
    public static boolean rule_b(Stack allStk, byte[] topElmt) {
        System.out.println("Enter into DOUBLEWORD rule_b method");

        Stack s = new Stack();

        s.push(new Entry(topElmt, -1));
        Sandhi.kctp(s);

        if (Clitic.check(s)) {
            c++;
            if (ADictionary.postposition(s)) {
                allStk.push(s);
                return true;
            }
            c++;
            if (ADictionary.adverb(s)) {
                allStk.push(s);
                return true;
            }
        }
        System.out.println("RULE b false");
        return false;
    }

    public static boolean rule_double(Stack allStk, byte[] topElmt) {
        System.out.println("Enter into DOUBLEWORD rule_double method");

        boolean pposition = false;
        Stack s = new Stack();
        //byte b[]=TC.convert(inputString);
        s.push(new Entry(topElmt, -1));
        Sandhi.kctp(s);
        if (Postposition.checkExp(s)) {
            pposition = true;
        }
        ////////System.out.println("pposition is"+pposition);
        if (!pposition) {
            if (ADictionary.dnoun(s)) {
                System.out.println("Dnoun is ture");
                allStk.push(s);
                return true;
            }


        }
        System.out.println("RULE double false");
        return false;
    }

    public static boolean rule_c(Stack allStk, byte[] topElmt, boolean analysePart) {
        System.out.println("Enter into DOUBLEWORD rule_c method");

        Stack s = new Stack();

        s.push(new Entry(topElmt, -1));
        Sandhi.kctp(s);

        boolean anySuffixFound = false,
                hasSth = false, pposition = false;

        if (Clitic.check(s)) {
            hasSth = true;
        }

        if (Postposition.check(s)) {
            hasSth = true;
        }

        if (Case.check(s, false)) {
            anySuffixFound = true;
        }
        ////////System.out.println("ANY"+anySuffixFound+":"+hasSth);
        if (anySuffixFound) {

            c++;
            //System.out.println("Enter into if part of doubleOwed");
            if (ADictionary.noun_PronounCase_InterrogativeNoun(s)) {

                allStk.push(s);
                return true;
            }


            if (Sandhi.handleException(s)) {
                c++;
                //System.out.println("Enter into if part of doubleOwd");
                if (ADictionary.noun_PronounCase_InterrogativeNoun(s)) {

                    allStk.push(s);
                    return true;
                }
            }
        }



        if (anySuffixFound && SingPluralMarker.check(s)) {

            //byte[] topElmt = ((Entry) s.peek()).getPart();
            //System.out.println("LEN"+topElmt.length);
            c++;

            boolean chk = ADictionary.dnoun(s);

            if (chk) {
                //System.out.println("Enter into if part of");
                allStk.push(s);
                return true;
            }
        }
        ////////System.out.println("doubleword bharath");
// this is for checking the input comes with maan		
        if (Postposition.checkExp(s)) {
            //System.out.println("Enter into if doubleOwed");
            pposition = true;
        }
        //System.out.println("pposition is"+pposition);
        if (!pposition) {
            ////System.out.println("Stack value "+s);
            ////System.out.println()
            if (ADictionary.dnoun(s)) {
                //System.out.println("Double word true in not of position");

                allStk.push(s);
                return true;
            }
        }

        if (analysePart) {
            allStk.push(s);
        }
        System.out.println("RULE c false");
        return false;
    }

    // why split - anu
    // rule c1 <Noun/Noun+Plural/PronounCase/InterrogativeNoun+Postposition+Clitic>
    public static boolean rule_c1(Stack allStk, byte[] topElmt) {
        System.out.println("Enter into DOUBLEWORD rule_c1 method");

        Stack s = new Stack();

        s.push(new Entry(topElmt, -1));
        Sandhi.kctp(s);

        boolean anySuffixFound = false;

        if (Clitic.check(s)) {
            ;
        }
        // anySuffixFound = true;

        if (Postposition.check(s)) {
            anySuffixFound = true;
        }

        if (anySuffixFound) {
            c++;
            if (ADictionary.noun_PronounCase_InterrogativeNoun(s)) {
                allStk.push(s);
                return true;
            }
            if (Sandhi.handleException(s)) {
                c++;
                if (ADictionary.noun_PronounCase_InterrogativeNoun(s)) {
                    allStk.push(s);
                    return true;
                }
            }
        }

        if (SingPluralMarker.check(s)) {
            c++;
            if (ADictionary.dnoun(s)) {
                allStk.push(s);
                return true;
            }
        }
        System.out.println("RULE c1 false");
        return false;
    }

    // rule d <Noun/angu_ingu_engu+AdjSuffix+Pronominal+Plural+Case+Postposition+Clitic>
    // AdjSuffix+Pronominal is a must
    public static boolean rule_d(Stack allStk, byte[] topElmt) {
        System.out.println("Enter into DOUBLEWORD rule_d method");

        Stack s = new Stack();

        s.push(new Entry(topElmt, -1));
        Sandhi.kctp(s);

        boolean anySuffixFound = false,
                hasSth = false,
                pp = false;

        if (Clitic.check(s)) {
            hasSth = true;
        }

        if (Postposition.check(s)) {
            pp = true;
        }

        if (Case.check(s, true)) {
            hasSth = true;
        }

        if (SingPluralMarker.check(s)) {
            hasSth = true;
        }
        if (org.apache.nutch.analysis.unl.ta.NounMisc.pronominal(s)) {
            anySuffixFound = true;
        }

        if (NounMisc.adjective(s)) {
            anySuffixFound = true;
        }

        // anu
        if (pp) {
            if (NounMisc.angu_ingu_engu(s)) // angku, ingku, engku
            {
                allStk.push(s);
                return true;
            }
        }
        if (anySuffixFound) {
            c++;
            if (ADictionary.dnoun(s)) {
                allStk.push(s);
                return true;
            }
            if (NounMisc.angu_ingu_engu(s)) // angku, ingku, engku
            {
                allStk.push(s);
                return true;
            }
            if (Sandhi.handleException(s)) {
                c++;
                if (ADictionary.dnoun(s)) {
                    allStk.push(s);
                    return true;
                }
            }
        }

        if (SingPluralMarker.check(s)) {
            c++;
            if (ADictionary.dnoun(s)) {
                allStk.push(s);
                return true;
            }
        }
        System.out.println("RULE d false");
        ////////System.out.println( "end of.................................................... RULE d");
        return false;
    }

    // rule e <Noun/Noun+Plural/PronounClitic/InterrogativeNoun+AdvSuffix/FiniteVerb+Clitic>
    public static boolean rule_e(Stack allStk, byte[] topElmt) {
        System.out.println("Enter into DOUBLEWORD rule_e method");

        Stack s = new Stack();

        s.push(new Entry(topElmt, -1));
        Sandhi.kctp(s);

        boolean anySuffixFound = false,
                hasClitic = false;

        if (Clitic.check(s)) {
            hasClitic = true;
        }

        if (NounMisc.adverb(s)) {
            anySuffixFound = true;
        }

        if (NounMisc.finiteVerb(s)) {
            anySuffixFound = true;
        }

        if (anySuffixFound) {
            c++;
            if (ADictionary.noun_PronounClitic_InterrogativeNoun(s)) {
                allStk.push(s);
                return true;
            }
            if (Sandhi.handleException(s)) {
                c++;
                if (ADictionary.noun_PronounClitic_InterrogativeNoun(s)) {
                    allStk.push(s);
                    return true;
                }
            }
            if (SingPluralMarker.check(s)) {
                c++;
                if (ADictionary.dnoun(s)) {
                    allStk.push(s);
                    return true;
                }
            }
        }
        System.out.println("RULE e false");
        return false;
    }

    /* public static boolean rule_f(Stack allStk, String inputString)
    {


    }*/
    public static boolean checkPrefix(Stack allStk, byte[] inputByte, boolean analysePart) {
        System.out.println("Enter into DOUBLEWORD checkPrefix method");
        Stack s = new Stack();

        s.push(new Entry(inputByte, -1));

        //byte[] inputByte = ((Entry) s.peek()).getPart();

        if (inputByte.length <= 3) {
            return false;
        }

        byte[] inputByte1 = null;
        byte[] prefix = BooleanMethod.startsWith_prefix(inputByte);

        if (prefix != null) {
            ////////System.out.println( "Prefix: Yes");
            inputByte1 = ByteMeth.subArray(inputByte, 2, inputByte.length);
            s.pop();
            s.push(new Entry((new byte[]{prefix[1]}), Tag.Sandhi));
            s.push(
                    new Entry((new byte[]{prefix[0]}), Tag.DemonstrativeAdjective)); // temp name for tag

            // noun inflections
            //String nounStr = UnicodeConverter.revert(inputByte1);

            if (check(allStk, inputByte1, analysePart)) {
                //clia.unl.unicode.utils.Utils.printOut(Analyser.print,x + "Prefix + Noun Inflections");
                int size = allStk.size();
                Stack[] nounInfStk = new Stack[size];

                for (int i = 0; i < size; i++) {
                    nounInfStk[i] = (Stack) allStk.pop();
                }

                for (int i = 0; i < size; i++) {
                    Misc.merge(s, nounInfStk[i]);
                    allStk.push(nounInfStk[i]);
                }
            }

            // noun
            Stack nounStk = new Stack();

            nounStk.push(new Entry(inputByte1, -1));
            if (ADictionary.dnoun(nounStk)) {
                ////////System.out.println( "Prefix + Noun");
                Misc.merge(s, nounStk);
                allStk.push(nounStk);
                return true;
            }

        }
        return false;
    }
    // last
}
