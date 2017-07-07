package org.apache.nutch.analysis.unl.ta;

//import org.apache.nutch.unl.utils.*;
import java.util.*;

// import demo.*;
public class Noun {

    static String x = "";
    static UnicodeConverter TC;
    static int c = 0;

    public static boolean check(Stack allStk, byte[] topElmt, boolean analysePart) {
        System.out.println("Enter into Noun check method");
        boolean isNoun = false;
        System.out.println("value of topElmt in noun check "+UnicodeConverter.revert(topElmt));
        try {

            if (rule_a(allStk, topElmt)) {
                //System.out.println("rule_a True");
                isNoun = true;
            }
            else if (rule_c1(allStk, topElmt)) {
                System.out.println("rule_c1 True");
                isNoun = true;
            } else if (rule_c(allStk, topElmt, analysePart)) {
                System.out.println("rule_c True");
                isNoun = true;
            } else if (rule_b(allStk, topElmt)) {
                System.out.println("rule_b True");
                isNoun = true;
            } else if (rule_e(allStk, topElmt)) {
                System.out.println("rule_e True");
                isNoun = true;
            } else if (rule_d(allStk, topElmt)) {
               System.out.println("rule_d True");
                isNoun = true;
            }
            ////////System.out.println("CCCCCC"+ isNoun);
        } catch (Exception e) {
            e.printStackTrace();
            //////System.out.println("printStackTrace:"+e);
        }
        ////System.out.println("noun is "+isNoun);
        return isNoun;

    }

    // rule a <Noun/PronounClitic/InterrogativeNoun+Clitic>
    public static boolean rule_a(Stack allStk, byte[] topElmt) {
        System.out.println("Enter into Noun rule_a method");
        System.out.println("In rule_a of noun "+UnicodeConverter.revert(topElmt));
        Stack s = new Stack();

        s.push(new Entry(topElmt, -1));
        Sandhi.kctp(s);
        Sandhi.check(s);
        byte[] oldTopElmt = topElmt;
        if (Clitic.check(s)) {
            c++;
            System.out.println("Enter into clitic rule_a of noun");
            if (ADictionary.noun_PronounClitic_InterrogativeNoun(s)) {
                System.out.println("noun_PronounClitic_InterrogativeNoun");
                allStk.push(s);
                return true;
            }
            if (Sandhi.handleException(s)) {
                c++;
                if (ADictionary.noun_PronounClitic_InterrogativeNoun(s)) {
                    allStk.push(s);
                    return true;
                }
                ////System.out.println("noun_PronounClitic_InterrogativeNoun1");
            }
        }

        System.out.println("Afalse in Noun rule_a");
        return false;
    }

    // rule b <Postposition/Adverb+Clitic>
    public static boolean rule_b(Stack allStk, byte[] topElmt) {
        System.out.println("Enter into Noun rule_b method");
        Stack s = new Stack();

        s.push(new Entry(topElmt, -1));
        Sandhi.kctp(s);

        if (Clitic.check(s)) {
            c++;
            if (ADictionary.adverb(s)) {
                allStk.push(s);
                return true;
            }
            if (ADictionary.noun(s)) {
                //allStk.push(s);
                return false;
            }
            c++;
            if (ADictionary.postposition(s)) {
                allStk.push(s);
                return true;
            }

        }
        System.out.println("Afalse in Noun rule_b");
        return false;
    }

    public static boolean rule_c(Stack allStk, byte[] topElmt, boolean analysePart) {
        System.out.println("Enter into Noun rule_c method");
        Stack s = new Stack();

        s.push(new Entry(topElmt, -1));
        Sandhi.kctp(s);

        boolean anySuffixFound = false,
                hasSth = false;

        if (Clitic.check(s)) {
            ////System.out.println("clitic for noun true");
            hasSth = true;
        }
        if (ADictionary.noun(s)) {
            allStk.push(s);
            return true;
        }

       


        if (Postposition.check(s)) {
            ////System.out.println("Postposition True");
            hasSth = true;
        }
        if (ADictionary.noun(s)) {
            allStk.push(s);
            return true;
        }

        if (Case.check(s, false)) {
            System.out.println("Case True");
            anySuffixFound = true;
        }
        if (ADictionary.noun(s)) {
            allStk.push(s);
            return true;
        }

        if (anySuffixFound) {
            System.out.println("Enter into anysuffixfound");
            c++;
            if (ADictionary.noun_PronounCase_InterrogativeNoun(s)) {
                System.out.println("Enter into anysuffixfound noun_PronounCase");
                allStk.push(s);
                return true;
            }
            if (Sandhi.handleException(s)) {
                System.out.println("Enter into anysuffixfound sandhi");
                c++;
                if (ADictionary.noun_PronounCase_InterrogativeNoun(s)) {
                    allStk.push(s);
                    return true;
                }
            }
        }

        if (anySuffixFound && SingPluralMarker.check(s)) {
            c++;
            if (ADictionary.noun(s)) {
                allStk.push(s);
                return true;
            }
        }

        if (analysePart) {
            allStk.push(s);
        }
        System.out.println("Afalse in Noun rule_c");
        return false;
    }

    // why split - anu
    // rule c1 <Noun/Noun+Plural/PronounCase/InterrogativeNoun+Postposition+Clitic>
    public static boolean rule_c1(Stack allStk, byte[] topElmt) {
        System.out.println("Enter into Noun rule_c1 method");
        Stack s = new Stack();

        s.push(new Entry(topElmt, -1));
        Sandhi.kctp(s);

        boolean anySuffixFound = false;

        if (Clitic.check(s)) {
            ;
        }
        // anySuffixFound = true;

        if (Postposition.check(s)) {
            System.out.println("In Noun Postposition True");
            anySuffixFound = true;
        }

        if (anySuffixFound) {
            System.out.println("suffix true");
            c++;
            if (ADictionary.noun_PronounCase_InterrogativeNoun(s)) {
                ////System.out.println("In noun_PronounCase_InterrogativeNoun True");
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
            System.out.println("SingPluralMarker");
            c++;
            if (ADictionary.noun(s)) {
                allStk.push(s);
                return true;
            }
        }
        System.out.println("Afalse in Noun rule_c1");
        return false;
    }

    // rule d <Noun/angu_ingu_engu+AdjSuffix+Pronominal+Plural+Case+Postposition+Clitic>
    // AdjSuffix+Pronominal is a must
    public static boolean rule_d(Stack allStk, byte[] topElmt) {
        System.out.println("Enter into Noun rule_d method");
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
        if (NounMisc.pronominal(s)) {
            anySuffixFound = true;
        }

        if (NounMisc.adjective(s)) {
            anySuffixFound = true;
        }

        // anu
        if (pp) {
            System.out.println("pp");
            if (NounMisc.angu_ingu_engu(s)) // angku, ingku, engku
            {
                allStk.push(s);
                return true;
            }
        }
        if (anySuffixFound) {
            System.out.println("pp any");
            c++;
            if (ADictionary.noun(s)) {
               System.out.println("pp any 1");
                allStk.push(s);
                return true;
            }
            if (NounMisc.angu_ingu_engu(s)) // angku, ingku, engku
            {
                System.out.println("pp any 2");
                allStk.push(s);
                return true;
            }
            if (Sandhi.handleException(s)) {
                System.out.println("pp any 3");
                c++;
                if (ADictionary.noun(s)) {
                    allStk.push(s);
                    return true;
                }
            }
        }
        System.out.println("pp any before");
        if (SingPluralMarker.check(s)) {
            System.out.println("Entering");
            c++;
            if (ADictionary.noun(s)) {
                System.out.println("Entering Noun");
                allStk.push(s);
                return true;
            }
        }
        System.out.println("Afalse in Noun rule_d");
        //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "end of.................................................... RULE d");
        return false;
    }

    // rule e <Noun/Noun+Plural/PronounClitic/InterrogativeNoun+AdvSuffix/FiniteVerb+Clitic>
    public static boolean rule_e(Stack allStk, byte[] topElmt) {
        System.out.println("Enter into Noun rule_e method");
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
                if (ADictionary.noun(s)) {
                    allStk.push(s);
                    return true;
                }
            }
        }
        System.out.println("Afalse in Noun rule_e");
        return false;
    }

    public static boolean checkPrefix(Stack allStk, byte[] topElmt, boolean analysePart) {
        System.out.println("Enter into Noun checkPrefix method");
        Stack s = new Stack();

        s.push(new Entry(topElmt, -1));

        byte[] inputByte = ((Entry) s.peek()).getPart();

        //////System.out.println("inputByte:"+inputByte.toString());
        if (inputByte.length <= 3) {
            return false;
        }

        byte[] inputByte1 = null;
        byte[] prefix = BooleanMethod.startsWith_prefix(inputByte);

        //////System.out.println("PREFIX:"+prefix.toString());
        if (prefix != null) {

            s.pop();
            if (prefix.length == 3) {
                inputByte1 = ByteMeth.subArray(inputByte, prefix.length - 1, inputByte.length);
                s.push(new Entry((new byte[]{prefix[1]}), Tag.Sandhi));
                s.push(
                        new Entry((new byte[]{prefix[0]}), Tag.DemonstrativeAdjective)); // temp name for tag
            } else {

                s.push(new Entry(prefix, Tag.DemonstrativeAdjective));
                inputByte1 = ByteMeth.subArray(inputByte, prefix.length, inputByte.length);
            }

            //String nounStr = UnicodeConverter.revert(inputByte1);

            //////System.out.println(nounStr);
            if (check(allStk, inputByte1, analysePart)) {
                //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "Prefix + Noun Inflections");
                int size = allStk.size();
                Stack[] nounInfStk = new Stack[size];

                for (int i = 0; i < size; i++) {
                    nounInfStk[i] = (Stack) allStk.pop();
                }

                for (int i = 0; i < size; i++) {
                    Misc.merge(s, nounInfStk[i]);
                    allStk.push(nounInfStk[i]);
                }
                //return true;
            }
            ////System.out.println("process completed");
            // noun
            Stack nounStk = new Stack();

            nounStk.push(new Entry(inputByte1, -1));
            if (ADictionary.noun(nounStk)) {
                //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "Prefix + Noun");
                Misc.merge(s, nounStk);
                allStk.push(nounStk);
                return true;
            }

        }
        return false;
    }
    // last
}
