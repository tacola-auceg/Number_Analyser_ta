package org.apache.nutch.analysis.unl.ta;

//import org.apache.nutch.util.NutchConfiguration;
//import org.apache.hadoop.conf.Configuration;
import java.util.TreeSet;
import java.util.Stack;
import java.util.Vector;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
//import org.apache.nutch.unl.UNL;

public class ADictionary {

    static ByteMeth ByteMeth;
    static UnicodeConverter TC;
    static String x = "";
    static String y = "";
    static boolean b = true;
    // initialise dictionary
    static BTree Noun_BTree = new BTree();
    static BTree Verb_BTree = new BTree();
    static BTree Adjective_BTree = new BTree();
    static BTree Adverb_BTree = new BTree();
    static BTree Particle_BTree = new BTree();
    static BTree Neg_Finite_Verb_BTree = new BTree();
    static BTree Conjuntion_BTree = new BTree();
    static BTree Interjection_BTree = new BTree();
    static BTree Interrogative_BTree = new BTree();
    static BTree Interrogative_Adj_BTree = new BTree();
    static BTree Demo_Adj_BTree = new BTree();
    static BTree Finite_Verb_BTree = new BTree();
    static BTree Postposition_BTree = new BTree();
    static BTree Intensifier_BTree = new BTree();
    static BTree Non_Tamil_BTree = new BTree();
    static BTree m_End_Noun_BTree = new BTree();
    static BTree ProNoun_BTree = new BTree();
    static BTree En_BTree = new BTree();
    static BTree Entity_BTree = new BTree();
    static BTree Char_Num_BTree = new BTree();

    // load dictionary in memory
    static {
        // Configuration conf = NutchConfiguration.create();
        //String path = conf.get("analyzer");
        //  String path="./src/Analyser/Analyser/";
        String path = "./Analyser/";
        Verb_BTree.updateDictTree(path + "Verb_vi.txt");
        Noun_BTree.updateDictTree(path + "Noun_pe.txt");
        Adjective_BTree.updateDictTree(path + "Adjective_pe_a.txt");
        Adverb_BTree.updateDictTree(path + "Adverb_vi_a.txt");
        Particle_BTree.updateDictTree(path + "Particle_i_so.txt");
        Postposition_BTree.updateDictTree(path + "Postposition_sollurubu.txt");
        Neg_Finite_Verb_BTree.updateDictTree(path + "Neg_Finite_Verb_e_v_mu.txt");
        Conjuntion_BTree.updateDictTree(path + "Conjuction_i_i_so.txt");
        Interjection_BTree.updateDictTree(path + "Interjection_vi_i_so.txt");
        Interrogative_BTree.updateDictTree(path + "Interrogative_Noun_vi_pe.txt");
        Interrogative_Adj_BTree.updateDictTree(path + "Interrogative_Adj_vi_pe_a.txt");
        Demo_Adj_BTree.updateDictTree(path + "Demon_Adjective_su_pe_a.txt");
        Finite_Verb_BTree.updateDictTree(path + "Finite_Verb_vi_mu.txt");

        Intensifier_BTree.updateDictTree(path + "Intensifier.txt");
        Non_Tamil_BTree.updateDictTree(path + "NonTamil.txt");
        m_End_Noun_BTree.updateDictTree(path + "m_End_Noun.txt");
        ProNoun_BTree.updateDictTree(path + "pronoun.txt");
        En_BTree.updateDictTree(path + "En.txt");
        Entity_BTree.updateDictTree(path + "entitylist.txt");
        Char_Num_BTree.updateDictTree(path + "Char_Number.txt");

        b = false;

    }

    // dictionary checking order - wont follow any rule
    public static boolean check(Stack allStk, byte topElmt1[]) {

        System.out.println("Enter into ADictionary check method");
        Number_Conversion nc = new Number_Conversion();
        Stack s2 = new Stack();
        s2.push(new Entry(topElmt1, -1));
        Stack s2new = new Stack();
        Sandhi.kctp(s2);
        allStk.push(s2);
        String input = "";
        String inputstring = "";
        Stack s1 = (Stack) allStk.pop();
        byte[] topElmt = ((Entry) s1.peek()).getPart();
        byte[] oldTopElmt = topElmt.clone();
        //String input = TC.revert(topElmt);
        ////System.out.print("here");
        for (int i = 0; i < topElmt.length; i++) {
            //	//System.out.print(topElmt[i]);
            input += topElmt[i];
        }
        for (int i = 0; i < topElmt1.length; i++) {
            inputstring += topElmt1[i];
        }

        ////////System.out.println("chk"+input);
        boolean found = false;
        boolean isVerb = false;

        if (Entity_BTree.contains(inputstring)) {
            Stack s = new Stack();
            s.push(new Entry(topElmt1, Tag.Entity));
            allStk.push(s);
            found = true;
            return true;
        } else if (Char_Num_BTree.contains(inputstring)) {
            //System.out.println("Enter into char number btree " + inputstring);
           /* Stack s = new Stack();
            s.push(new Entry(topElmt1, Tag.cNumbers));
            allStk.push(s);
            found = true;
            Stack snew = (Stack) allStk.pop();
            byte[] topElmtn = ((Entry) snew.peek()).getPart();
            byte[] oldTopElmtn = topElmtn.clone();
            String sen=UnicodeConverter.revert(oldTopElmtn);
            nc.check_num(sen);
            
            return true;*/
            Stack s = (Stack) s1.clone();

            // doubt why poping?
            s.pop();
            s.push(new Entry(topElmt, Tag.cNumbers));
            allStk.push(s);
            s2new.push(s);
            Stack snew = (Stack) s2new.pop();
            byte[] topElmtn = ((Entry) snew.peek()).getPart();
            byte[] oldTopElmtn = topElmtn.clone();
            String sen = UnicodeConverter.revert(oldTopElmtn);
            //nc.check_num(sen);
            nc.check_l(sen);
            found = true;
        } else if (BooleanMethod.isPronoun(topElmt)) {

            Stack s = (Stack) s1.clone();

            // doubt why poping?
            s.pop();
            s.push(new Entry(topElmt, Tag.Pronoun));
            allStk.push(s);
            found = true;
        } ////////System.out.println("2");
        else if (BooleanMethod.isPronoun_Case(topElmt)) {

            Stack s = (Stack) s1.clone();

            s.pop();
            s.push(new Entry(topElmt, Tag.PronounCase));
            allStk.push(s);
            found = true;
        } //////"3");
        else if (BooleanMethod.isPronoun_Clitic(topElmt)) {
            //////////System.out.println("pronoun clitic");
            ////////System.out.println( "Pronoun Clitic");
            Stack s = (Stack) s1.clone();

            s.pop();
            s.push(new Entry(topElmt, Tag.PronounClitic));
            allStk.push(s);
            found = true;
        } ////////System.out.println("4");
        else if (Demo_Adj_BTree.contains(input)) {

            //////////System.out.println("Demo Adj");
            ////////System.out.println( "Demo Adj");
            Stack s = (Stack) s1.clone();

            s.pop();
            s.push(new Entry(topElmt, Tag.DemonstrativeAdjective));
            allStk.push(s);
            found = true;
        } ////////System.out.println("5");
        else if (Neg_Finite_Verb_BTree.contains(input)) {
            //////////System.out.println("NegFiniteVerb");
            ////////System.out.println( "NegFiniteVerb");
            Stack s = (Stack) s1.clone();

            s.pop();
            s.push(new Entry(topElmt, Tag.NegativeFiniteVerb));
            allStk.push(s);
            found = true;

        } ////////System.out.println("6");
        ////////System.out.println("size of Tree"+Finite_Verb_BTree.size());
        else if (Finite_Verb_BTree.contains(input)) {
            //////////System.out.println("Finite Verb");
            ////////System.out.println( "Finite Verb");
            Stack s = (Stack) s1.clone();

            s.pop();
            s.push(new Entry(topElmt, Tag.FiniteVerb));
            allStk.push(s);
            found = true;

        } ////////System.out.println("7");
        ////////System.out.println("size of stack is."+Finite_Verb_BTree.size());
        else if (Interjection_BTree.contains(input)) {
            //////////System.out.println("Interjection");
            ////////System.out.println( "Interjection");
            Stack s = (Stack) s1.clone();

            s.pop();
            s.push(new Entry(topElmt, Tag.Interjection));
            allStk.push(s);
            found = true;

        } ////////System.out.println("8");
        else if (Interrogative_BTree.contains(input)) {
            //////////System.out.println("InterrogativeNoun");
            ////////System.out.println( "InterrogativeNoun");
            Stack s = (Stack) s1.clone();

            s.pop();
            s.push(new Entry(topElmt, Tag.InterrogativeNoun));
            allStk.push(s);
            found = true;
        } ////////System.out.println("9");
        else if (Interrogative_Adj_BTree.contains(input)) {
            //////////System.out.println("InterrogativeAdj");
            ////////System.out.println( "InterrogativeAdj");
            Stack s = (Stack) s1.clone();

            s.pop();
            s.push(new Entry(topElmt, Tag.InterrogativeAdjective));
            allStk.push(s);
            found = true;
        } ////////System.out.println("10");
        else if (Conjuntion_BTree.contains(input)) {
            //////////System.out.println("IIS");
            ////////System.out.println( "IIS");
            Stack s = (Stack) s1.clone();

            s.pop();
            s.push(new Entry(topElmt, Tag.Conjunction));
            allStk.push(s);
            found = true;
        } ////////System.out.println("11");
        else if (Adjective_BTree.contains(input)) {
            //////////System.out.println("ADJ");
            ////////System.out.println( "ADJ");
            Stack s = (Stack) s1.clone();

            s.pop();
            s.push(new Entry(topElmt, Tag.Adjective));
            allStk.push(s);
            found = true;

        } ////////System.out.println("12");
        else if (Intensifier_BTree.contains(input)) {
            //////////System.out.println("Intensifier");
            ////////System.out.println( "Intensifier");
            Stack s = (Stack) s1.clone();

            s.pop();
            s.push(new Entry(topElmt, Tag.Intensifier));
            allStk.push(s);
            found = true;
        } ////////System.out.println("13");
        else if (Particle_BTree.contains(input)) {
            //////////System.out.println("Particle");
            ////////System.out.println( "Particle");
            Stack s = (Stack) s1.clone();

            s.pop();
            s.push(new Entry(topElmt, Tag.Particle));
            allStk.push(s);
            found = true;
        } ////////System.out.println("14");
        else if (Postposition_BTree.contains(input)) {
            ////////System.out.println("postPosition");
            ////////System.out.println( "Postposition");
            Stack s = (Stack) s1.clone();

            s.pop();
            s.push(new Entry(topElmt, Tag.Postposition));
            allStk.push(s);
            found = true;
        } ////////System.out.println("15");
        else if (Verb_BTree.contains(input)) {
            //////////System.out.println("verb");
            ////////System.out.println( "Verb");
            Stack s = (Stack) s1.clone();

            s.pop();
            s.push(new Entry(topElmt, Tag.Verb));
            allStk.push(s);
            found = true;
            isVerb = true;
        } ////////System.out.println("16");
        else if (Adverb_BTree.contains(input)) {
            //////////System.out.println("adverb");
            ////////System.out.println( "Adverb");
            Stack s = (Stack) s1.clone();

            s.pop();
            s.push(new Entry(topElmt, Tag.Adverb));
            allStk.push(s);
            found = true;
        } ////////System.out.println("17");
        ////////System.out.println("Non_Tamil_BTree"+Non_Tamil_BTree.size());
        else if (Non_Tamil_BTree.contains(inputstring)) {
            //////////System.out.println("Nn-Tamil");
            ////////System.out.println( "Non Tamil");
            Stack s = new Stack();

            s.push(new Entry(topElmt1, Tag.NonTamilNoun));
            allStk.push(s);
            found = true;
        } ////////System.out.println("18");
        //////System.out.println("noun btree"+input);
        else if (Noun_BTree.contains(input)) {
            //////////System.out.println("Noun");
            ////////System.out.println( "Noun");
            Stack s = (Stack) s1.clone();
            s.pop();
            s.push(new Entry(topElmt, Tag.Noun));
            allStk.push(s);
            found = true;
        } ////////System.out.println("19");
        else if (m_End_Noun_BTree.contains(input)) {
            //		////////System.out.println("m_End_Noun");
            ////////System.out.println( "m_End_Noun");
            Stack s = (Stack) s1.clone();
            s.pop();
            s.push(new Entry(topElmt, Tag.AdjectivalNoun));
            allStk.push(s);
            found = true;
        } ////////System.out.println("20");
        else if (Entity_BTree.contains(input)) {
            //Utils.printOut(Analyser.print, x + "Entity");
            Stack s = (Stack) s1.clone();
            s.pop();
            s.push(new Entry(topElmt, Tag.Entity));
            allStk.push(s);
            found = true;
        }
        if (!found) {
        }
        ////////System.out.println("Hello bharath");
        // found for future
        if (found) {
            return true;
        }


        return false;
    }

    public static boolean tourismDomain(Stack allStk, byte[] topElmt1) {
        try {
            System.out.println("Enter into ADictionary tourismDomain method");
            BufferedWriter bw = new BufferedWriter(new FileWriter("./output.txt", true));
            Stack s2 = new Stack();
            s2.push(new Entry(topElmt1, -1));
            Sandhi.kctp(s2);
            allStk.push(s2);
            Stack s1 = (Stack) allStk.pop();
            byte[] topElmt = ((Entry) s1.peek()).getPart();
            String ahcheck = "";

            //for making அ as ஏ  ex அங்க  becomes அங்கே
            if ((topElmt[topElmt.length - 1] == 1) && ((topElmt[topElmt.length - 2] == 14) || (topElmt[topElmt.length - 2] == 29))) {
                byte[] oldTopElmt = topElmt.clone();
                oldTopElmt[oldTopElmt.length - 1] = 8; // 8 is internal code value of ஏ
                for (int i = 0; i < oldTopElmt.length; i++) {
                    ahcheck += oldTopElmt[i];
                }
                if (Adverb_BTree.contains(ahcheck)) {
                    Stack s = (Stack) s1.clone();
                    s.pop();
                    s.push(new Entry(oldTopElmt, Tag.Adverb));
                    allStk.push(s);
                    return true;
                }
            }



            //for making அ  as ஐ  ex எத்தன becomes எத்தனை
            if (topElmt[topElmt.length - 1] == 1) {

                byte[] oldTopElmt = topElmt.clone();
                oldTopElmt[oldTopElmt.length - 1] = 9; // 9 is internal code value of ஐ
                for (int i = 0; i < oldTopElmt.length; i++) {
                    ahcheck += oldTopElmt[i];
                }
                if (Interrogative_BTree.contains(ahcheck)) {
                    Stack s = (Stack) s1.clone();
                    s.pop();
                    s.push(new Entry(oldTopElmt, Tag.InterrogativeNoun));
                    allStk.push(s);
                    return true;
                } else if (ProNoun_BTree.contains(ahcheck)) {

                    Stack s = (Stack) s1.clone();
                    s.pop();
                    s.push(new Entry(oldTopElmt, Tag.Pronoun));
                    allStk.push(s);
                    return true;
                } else if (Noun_BTree.contains(ahcheck)) {
                    Stack s = (Stack) s1.clone();
                    s.pop();
                    s.push(new Entry(oldTopElmt, Tag.Noun));
                    allStk.push(s);
                    return true;
                } else if (Neg_Finite_Verb_BTree.contains(ahcheck)) {
                    Stack s = (Stack) s1.clone();
                    s.pop();
                    s.push(new Entry(oldTopElmt, Tag.NegativeFiniteVerb));
                    allStk.push(s);
                    return true;
                } else if (Entity_BTree.contains(ahcheck)) {
                    Stack s = (Stack) s1.clone();
                    s.pop();

                    s.push(new Entry(oldTopElmt, Tag.Entity));
                    allStk.push(s);
                    return true;
                }
            }


            //adding ள்  at the end of word ex அவ become அவள்
            if ((topElmt[topElmt.length - 1] == 1) && ((topElmt[topElmt.length - 2] == 27) || (topElmt[topElmt.length - 2] == 14))) {
                int length = topElmt.length + 1;
                byte[] oldTopElmt = new byte[length];
                for (int i = 0; i < topElmt.length; i++) {
                    oldTopElmt[i] = topElmt[i];
                }
                oldTopElmt[topElmt.length] = 29;  // 29 is internal code value of ள்
                ahcheck = "";
                for (int i = 0; i < oldTopElmt.length; i++) {
                    ahcheck += oldTopElmt[i];
                }
                if (ProNoun_BTree.contains(ahcheck)) {
                    Stack s = (Stack) s1.clone();
                    s.pop();
                    s.push(new Entry(oldTopElmt, Tag.Pronoun));
                    allStk.push(s);
                    return true;
                } else if (Noun_BTree.contains(ahcheck)) {
                    Stack s = (Stack) s1.clone();
                    s.pop();
                    s.push(new Entry(oldTopElmt, Tag.Noun));
                    allStk.push(s);
                    return true;
                }

            }

            //உ rule for colloquial words
            if (ByteMeth.endsWith(topElmt, Constant.u)) {
                //bw.write(str+"\n");
                ////System.out.println(UnicodeConverter.revert(topElmt).toString());
                String chk = "";
                byte[] oldTopElmt = new byte[topElmt.length];
                for (int i = 0; i < topElmt.length; i++) {
                    oldTopElmt[i] = topElmt[i];
                }
                oldTopElmt = ByteMeth.subArray(oldTopElmt, 0, oldTopElmt.length - Constant.u.length);

                chk = "";
                for (int i = 0; i < oldTopElmt.length; i++) {
                    chk += oldTopElmt[i];
                }
                if (Verb_BTree.contains(chk)) {

                    Stack s = (Stack) s1.clone();
                    s.pop();
                    s.push(new Entry(oldTopElmt, Tag.Verb));
                    allStk.push(s);
                    return true;


                } else if (Interrogative_BTree.contains(chk)) {

                    Stack s = (Stack) s1.clone();
                    s.pop();

                    s.push(new Entry(oldTopElmt, Tag.InterrogativeNoun));
                    allStk.push(s);
                    return true;

                } else if (ProNoun_BTree.contains(chk)) {

                    Stack s = (Stack) s1.clone();
                    s.pop();
                    s.push(new Entry(oldTopElmt, Tag.Pronoun));
                    allStk.push(s);
                    return true;

                } else if (Noun_BTree.contains(chk)) {

                    Stack s = (Stack) s1.clone();
                    s.pop();
                    s.push(new Entry(oldTopElmt, Tag.Noun));
                    allStk.push(s);
                    return true;

                }
            }
//above is mine
            // for making அ as ஆ  ex சீன becomes சீனா
            if (topElmt[topElmt.length - 1] == 1) {
                byte[] oldTopElmt = topElmt.clone();
                oldTopElmt[oldTopElmt.length - 1] = 2; // 2 is internal code value of ஆ
                String str = UnicodeConverter.revert(oldTopElmt);
                for (int i = 0; i < oldTopElmt.length; i++) {
                    ahcheck += oldTopElmt[i];
                }
                if (Entity_BTree.contains(ahcheck)) {
                    bw.write(str + "\n");
                    Stack s = (Stack) s1.clone();
                    s.pop();
                    s.push(new Entry(topElmt, Tag.Entity));
                    allStk.push(s);
                    return true;

                }


            }
            ////System.out.println("before revert="+UnicodeConverter.revert(topElmt));
            // adding ம்  at the end of word ex மகாபல்லிபுர become மகாபல்லிபுரம்

            if (topElmt[topElmt.length - 1] == 1) {
                ////System.out.println("ன்  adding");
                int length = topElmt.length + 1;
                byte[] oldTopElmt = new byte[length];
                for (int i = 0; i < topElmt.length; i++) {
                    oldTopElmt[i] = topElmt[i];
                }
                //////System.out.println("revert="+UnicodeConverter.revert(oldTopElmt));
                oldTopElmt[topElmt.length] = 31; //31 is internalcode value of ன்
                String str = UnicodeConverter.revert(oldTopElmt);
                //////System.out.println("after added ன் revert="+UnicodeConverter.revert(oldTopElmt));
                ahcheck = "";
                for (int i = 0; i < oldTopElmt.length; i++) {
                    ahcheck += oldTopElmt[i];
                }
                if (Entity_BTree.contains(ahcheck)) {
                    bw.write(str + "\n");
                    Stack s = (Stack) s1.clone();
                    s.pop();
                    s.push(new Entry(oldTopElmt, Tag.Entity));
                    allStk.push(s);
                    return true;

                }
            }
            if (topElmt[topElmt.length - 1] == 1) {
                ////System.out.println("ர் adding");
                int length = topElmt.length + 1;
                byte[] oldTopElmt = new byte[length];
                for (int i = 0; i < topElmt.length; i++) {



                    oldTopElmt[i] = topElmt[i];
                }
                ////System.out.println("revert="+UnicodeConverter.revert(oldTopElmt));
                oldTopElmt[topElmt.length] = 25;  // 25 is internal code value of ர்
                String str = UnicodeConverter.revert(oldTopElmt);
                ////System.out.println("after added ர் revert="+UnicodeConverter.revert(oldTopElmt));
                ahcheck = "";
                for (int i = 0; i < oldTopElmt.length; i++) {
                    ahcheck += oldTopElmt[i];
                }
                if (Entity_BTree.contains(ahcheck)) {
                    bw.write(str + "\n");
                    Stack s = (Stack) s1.clone();
                    s.pop();
                    s.push(new Entry(oldTopElmt, Tag.Entity));
                    allStk.push(s);
                    return true;

                }

            }
            if (topElmt[topElmt.length - 1] == 1) {
                ////System.out.println("ம்  adding");
                int length = topElmt.length + 1;
                byte[] oldTopElmt = new byte[length];
                for (int i = 0; i < topElmt.length; i++) {
                    oldTopElmt[i] = topElmt[i];
                }
                ////System.out.println("revert="+UnicodeConverter.revert(oldTopElmt));
                oldTopElmt[topElmt.length] = 23; //23 is internal code value of ம்
                String str = UnicodeConverter.revert(oldTopElmt);
                ahcheck = "";
                for (int i = 0; i < oldTopElmt.length; i++) {
                    ahcheck += oldTopElmt[i];
                }
                if (Entity_BTree.contains(ahcheck)) {
                    bw.write(str + "\n");
                    Stack s = (Stack) s1.clone();
                    s.pop();
                    s.push(new Entry(oldTopElmt, Tag.Entity));
                    allStk.push(s);
                    return true;

                }
            }
            if (ByteMeth.endsWith(topElmt, Constant.eyya)) {
                ////System.out.println(UnicodeConverter.revert(topElmt).toString());
                String chk = "";
                byte[] oldTopElmt = new byte[topElmt.length];
                for (int i = 0; i < topElmt.length; i++) {
                    oldTopElmt[i] = topElmt[i];
                }
                oldTopElmt = ByteMeth.subArray(oldTopElmt, 0, oldTopElmt.length - Constant.eyya.length);
                oldTopElmt = Sandhi.remove_kctp1(oldTopElmt);
                for (int i = 0; i < oldTopElmt.length; i++) {
                    chk += oldTopElmt[i];
                }
                if (Entity_BTree.contains(chk)) {

                    Stack s = (Stack) s1.clone();
                    s.pop();
                    s.push(new Entry(oldTopElmt, Tag.Entity));
                    allStk.push(s);
                    return true;

                }


            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    public static boolean adjectivalNoun(Stack allStk, byte[] topElmt) {
        System.out.println("Enter into ADictionary adjectivalNoun method");
        //////////System.out.println("ADictionary --- adjectivalNoun");
        Stack s = new Stack();

        s.push(new Entry(topElmt, -1));
        Sandhi.kctp(s);
        String inputstring = "";
        byte[] inputByte = topElmt;
        byte[] inputByte1 = null;

        if (inputByte[inputByte.length - 1] == Constant.a[0]) {
            inputByte1 = ByteMeth.addArray(inputByte, Constant.m);
            for (int i = 0; i < inputByte1.length; i++) {
                inputstring += inputByte1[i];
            }
            if (Noun_BTree.contains(inputstring)) {
                ////////System.out.println( "Adjectival Noun");
                s.pop();
                s.push(new Entry((inputByte), Tag.AdjectivalNoun));
                allStk.push(s);
                return true;
            }
        }
        return false;
    }

    public static boolean doubleNoun(Stack allStk, byte[] topElmt) {
        System.out.println("Enter into ADictionary doubleNoun method");
        //////System.out.println("ADictionary --- Double Noun called");
        Stack s = new Stack();

        s.push(new Entry(topElmt, -1));
        if (ADictionary.dnoun(s)) {
            allStk.push(s);
            //////System.out.println("I am true"+s.size());
            return true;
        }
        return false;
    }

    public static boolean noun_PronounCase_InterrogativeNoun(Stack s) {
        System.out.println("Enter into ADictionary noun_PronounCase_InterrogativeNoun method");
        String x = "";
        ////////System.out.println("Noun/PronounCase/Interrogative Noun");
        byte[] topElmt = ((Entry) s.peek()).getPart();
        byte[] oldTopElmt = topElmt;
        String input = "";
        for (int i = 0; i < topElmt.length; i++) {
            input += topElmt[i];
        }

        if (Noun_BTree.contains(input)) {
            ////////System.out.println( "Noun");
            s.push(new Entry(((Entry) s.pop()).getPart(), Tag.Noun));
            return true;
        }
        if (BooleanMethod.isPronoun(topElmt)) {
            ////////System.out.println( "Pronoun");
            s.push(new Entry(((Entry) s.pop()).getPart(), Tag.Pronoun));
            return true;
        }
        if (BooleanMethod.isPronoun_Case(topElmt)) {
            ////////System.out.println( "Pronoun Case");
            s.push(new Entry(((Entry) s.pop()).getPart(), Tag.PronounCase));
            return true;
        }
        if (Interrogative_BTree.contains(input)) {
            ////////System.out.println( "Interrogative Noun");
            s.push(new Entry(((Entry) s.pop()).getPart(), Tag.InterrogativeNoun));
            return true;
        }
        if (Char_Num_BTree.contains(input)) {
            ////////System.out.println( "Interrogative Noun");
            s.push(new Entry(((Entry) s.pop()).getPart(), Tag.cNumbers));
            return true;
        }

        return false;
    }

    public static boolean noun_PronounClitic_InterrogativeNoun(Stack s) {
        System.out.println("Enter into ADictionary noun_PronounClitic_InterrogativeNoun method");
        String x = "";
        ////////System.out.println("Noun/PronounClitic/Interrogative Noun");
        byte[] topElmt = ((Entry) s.peek()).getPart();
        byte[] oldTopElmt = topElmt;
        String input = "";
        for (int i = 0; i < topElmt.length; i++) {
            input += topElmt[i];
        }
        ////////System.out.println("revert"+UnicodeConverter.revert(topElmt));
        if (Noun_BTree.contains(input)) {
            ////////System.out.println( "Noun");
            s.push(new Entry(((Entry) s.pop()).getPart(), Tag.Noun));
            return true;
        }
        if (BooleanMethod.isPronoun(topElmt)) {
            ////////System.out.println( "Pronoun");
            s.push(new Entry(((Entry) s.pop()).getPart(), Tag.Pronoun));
            return true;
        }
        if (BooleanMethod.isPronoun_Clitic(topElmt)) {
            ////////System.out.println( "Pronoun Clitic");
            s.push(new Entry(((Entry) s.pop()).getPart(), Tag.PronounClitic));
            return true;
        }
        if (Interrogative_BTree.contains(input)) {
            ////////System.out.println( "Interrogative Noun");
            s.push(new Entry(((Entry) s.pop()).getPart(), Tag.InterrogativeNoun));
            return true;
        }
        if (ADictionary.dnoun(s)) {
            return true;
        }
        return false;
    }

    public static boolean postposition(Stack s) {
        System.out.println("Enter into ADictionary postposition method");
        String x = "";
        byte[] topElmt = ((Entry) s.peek()).getPart();
        byte[] oldTopElmt = topElmt;
        String input = "";
        for (int i = 0; i < topElmt.length; i++) {
            input += topElmt[i];
        }
        if (Postposition_BTree.contains(input)) {

            s.push(new Entry(((Entry) s.pop()).getPart(), Tag.Postposition));
            return true;
        }
        return false;
    }

    public static boolean noun(Stack s) {
        System.out.println("Enter into ADictionary noun method");
        byte[] topElmt = ((Entry) s.peek()).getPart();
        byte[] oldTopElmt = topElmt;
        String input = "";
        for (int i = 0; i < topElmt.length; i++) {
            input += topElmt[i];
        }


        if (Noun_BTree.contains(input)) {

            s.push(new Entry(((Entry) s.pop()).getPart(), Tag.Noun));
            return true;
        } else if (Char_Num_BTree.contains(input)) {

            s.push(new Entry(((Entry) s.pop()).getPart(), Tag.cNumbers));
            return true;
        } else if (Entity_BTree.contains(input)) {
            s.push(new Entry(((Entry) s.pop()).getPart(), Tag.Entity));
            return true;
        } else if (ProNoun_BTree.contains(input)) {

            s.push(new Entry(((Entry) s.pop()).getPart(), Tag.Pronoun));
            return true;
        } else if (En_BTree.contains(input)) {
            if (ByteMeth.endsWith(topElmt, Constant.pathu)) {

                s.push(new Entry(((Entry) s.pop()).getPart(), Tag.Enpathu));
                return true;
            } else {

                s.push(new Entry(((Entry) s.pop()).getPart(), Tag.Enpavar));
                return true;
            }
        } else if (Particle_BTree.contains(input)) {

            s.push(new Entry(((Entry) s.pop()).getPart(), Tag.Particle));
            return true;

        }

        return false;

    }
    public static String[] sandhi_new = {"14", "16", "18", "20", "22", "30"};//க்,ச்,ட்,த்,ப்,ற்

    public static String handle_double(byte[] topElmt3) //Double noun with alteration , insertion and deletion
    {
        System.out.println("Enter into ADictionary handle_double method");
        int itr = 1;
        int numMatches = 0;

        Stack s = new Stack();
        // byte topElmt3[]=UnicodeConverter.convert(str);
        //ArrayList value=UnicodeConverter.getValue();
        String str = UnicodeConverter.revert(topElmt3);
        for (int i = 0; i < topElmt3.length; i++) {
            s.push(new Entry(topElmt3, -1));
        }
        int length = 0;
        topElmt3 = ((Entry) s.peek()).getPart();
        byte[] oldTopElmt = topElmt3;
        byte[] checkElmt = topElmt3;
        String input = "";
        StringBuffer output = new StringBuffer();
        String tag_str = "";
        int count = 0;
        String in = "";
        String ins = "";


        boolean flag = false;
        boolean flag1 = false;
        ArrayList<String> tag_output = new ArrayList<String>();
        Stack s2 = new Stack();
        TreeSet set = new TreeSet();
        length = topElmt3.length;
        byte[] topElmt_new = null;
        byte[] topEl = null;
        String s1 = "";
        byte[] remain_str;
        while (itr != length) {

            checkElmt = ByteMeth.subArray(topElmt3, (topElmt3.length) - itr, topElmt3.length);
            input = "";
            for (int j = 0; j < checkElmt.length; j++) {
                input += checkElmt[j];

            }

            // System.out.println("Valus of cjheckELmt "+UnicodeConverter.revert(checkElmt));
            if (Noun_BTree.contains(input)) {
                System.out.println("1 in");
                s.pop();
                s.push(new Entry(checkElmt, Tag.Noun));
                tag_str = UnicodeConverter.revert(checkElmt);
                in = tag_str;
                flag = true;
                tag_output.add(tag_str);
            } else if (m_End_Noun_BTree.contains(input)) {
                tag_str = UnicodeConverter.revert(checkElmt);
                tag_output.add(tag_str);
            } else if (Char_Num_BTree.contains(input)) {
                System.out.println("2 in");
                System.out.println("Valus of cjheckELmt   in " + UnicodeConverter.revert(checkElmt));
                s.pop();
                s.push(new Entry(checkElmt, Tag.cNumbers));
                System.out.println("Valus of cjheckELmt " + UnicodeConverter.revert(checkElmt));
                tag_str = UnicodeConverter.revert(checkElmt);
                System.out.println("VAlue of in com " + tag_str);
                // output.append(tag_str);

                ins = tag_str;
                flag1 = true;

            }

            itr++;

        }


        String ip = "";
        //System.out.println("Value of tag_output "+tag_output.size()+" "+tag_output);
        if (tag_output.size() > 1) {

            String noun_out = "";
            noun_out = tag_output.get(tag_output.size() - 1).toString();

            //System.out.println("Noun+out "+noun_out);


            byte[] noun_char = UnicodeConverter.convert(noun_out);

            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - noun_char.length));
            System.out.println("remain_charut " + UnicodeConverter.revert(remain_char));

            String tag = check_ending(remain_char);
            System.out.println("The tag insiede is" + tag);
            output.append(tag);

            //output.append(noun_out + "<Noun &" + Tag.Noun+">");

        } else {


            byte[] tag_noun = UnicodeConverter.convert(tag_str);
            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - tag_noun.length));


            ////System.out.println("topElmt3"+topElmt3.length+"\t"+"noun_out"+tag_noun.length);


            String re = UnicodeConverter.revert(remain_char);
            System.out.println("The remain char" + re);

            String tag = check_ending(remain_char);
            System.out.println("The tag is" + tag);
            String f = "";
            if (tag != null) {
                System.out.println("Value of 1" + tag);
                output.append(tag);
                if (flag) {
                    output.append(in + "<Noun & " + Tag.Noun + ">");
                } else if (flag1) {
                    output.append(ins + " " + " < charNumbers " + "& " + Tag.cNumbers + " > ");
                }

            } else {
                System.out.println("Value of 2");
                String input1 = "";
                for (int j = 0; j < remain_char.length; j++) {
                    input1 += remain_char[j];

                }
                boolean ir_toin = false;
                if (remain_char[remain_char.length - 1] == 30) {
                    System.out.println("Value of 3");
                    oldTopElmt = remain_char.clone();
                    oldTopElmt[oldTopElmt.length - 1] = 31;

                    for (int i2 = 0; i2 < oldTopElmt.length; i2++) {
                        ip += oldTopElmt[i2];
                    }

                    tag = checknoun_string_andtag(ip, oldTopElmt);

                    output.append(tag);

                    output.append(tag_output.toString() + "<Noun>" + Tag.Noun);
                    ir_toin = true;
                } else if (Non_Tamil_BTree.contains(input)) {
                    System.out.println("Value of 4");
                    output.append(tag_output.toString() + "<Non_Tamil>" + Tag.NonTamilNoun);
                }
                if (Entity_BTree.contains(input)) {
                    System.out.println("Value of 5");
                    output.append(tag_output.toString() + "<Entity>" + Tag.NonTamilNoun);
                } else if (Char_Num_BTree.contains(input)) {
                    System.out.println("Value of 6");
                    output.append(tag_output.toString() + " " + "< charNumbers " + "& " + Tag.cNumbers + " > ");
                }

            }//ELSE


        }
        /* String get_h=output.toString();
        if (get_h.contains("<unknown>") || get_h.contains("count=4") || get_h.contains("count==1") || get_h.contains("null")) {
        //System.out.println("unknown compound "+get_h+"\n");

        }*/

        ////System.out.println("The output is"+output.toString());
        return output.toString();

    }//method end

    public static String check_ending(byte[] checkElmt) {
        System.out.println("Enter into ADictionary check_ending method");
        Stack s = new Stack();
        byte[] oldTopElmt;
        byte[] sandhiElmt;
        byte[] check_num;
        String alter_str = "";
        String input = "";
        boolean sandhi_check = false;
        boolean ending_check = false;
        String input1 = "";
        String input2 = "";
        String input3 = "";
        String input4 = "";
        String input5 = "";
        String noun_str = "";
        String output = "";
        String noun_ch;
        String sandhi_str = "";
        String tag_str = "";
//boolean mEndnoun_check=false;

        int itr1 = 1;
        int length1 = 0;

        String input6 = "";
        String input7 = "";
        System.out.println("The length of the checkElmt" + UnicodeConverter.revert(checkElmt));

        while (itr1 != length1) {

            check_num = ByteMeth.subArray(checkElmt, (checkElmt.length) - itr1, checkElmt.length);
            input = "";
            for (int j = 0; j < check_num.length; j++) {
                input += check_num[j];
            }

            if (Char_Num_BTree.contains(input)) {
                System.out.println("2 in");
                System.out.println("Valus of cjheckELmt   in " + UnicodeConverter.revert(check_num));
                s.pop();
                s.push(new Entry(check_num, Tag.cNumbers));
                System.out.println("Valus of cjheckELmt " + UnicodeConverter.revert(check_num));
                tag_str = UnicodeConverter.revert(check_num);
                System.out.println("VAlue of in com " + tag_str);
                // output.append(tag_str);


            }

            itr1++;


        }


        if (checkElmt[checkElmt.length - 1] == 20) {
            oldTopElmt = checkElmt.clone();
            oldTopElmt[oldTopElmt.length - 1] = 5;

            for (int i2 = 0; i2 < oldTopElmt.length; i2++) {
                input1 += oldTopElmt[i2];
            }


            alter_str = checknoun_string_andtag(input1, oldTopElmt);

            ending_check = true;

        }
        if (checkElmt.length != 0) {
            if (ByteMeth.endsWith(checkElmt, Constant.k)
                    || ByteMeth.endsWith(checkElmt, Constant.s)
                    || ByteMeth.endsWith(checkElmt, Constant.th)
                    || ByteMeth.endsWith(checkElmt, Constant.sandhi_yi)
                    || ByteMeth.endsWith(checkElmt, Constant.p) || ByteMeth.endsWith(checkElmt, Constant.inth) || ByteMeth.endsWith(checkElmt, Constant.ing) || ByteMeth.endsWith(checkElmt, Constant.sandhi_iCH) && !ByteMeth.endsWith(checkElmt, Constant.yng)) {

                sandhiElmt = ByteMeth.subArray(checkElmt, checkElmt.length - 1, checkElmt.length);
                //byte c=checkElmt[checkElmt.length-1];
                sandhi_str += sandhiElmt;
                sandhi_str = UnicodeConverter.revert(sandhiElmt);
                ////System.out.println("Yes sandhi checking"+sandhi_check+sandhiElmt+sandhi_str);
                //alter_str=sandhi_str+"<sandhi>";
                sandhi_check = true;
                checkElmt = ByteMeth.subArray(checkElmt, 0, checkElmt.length - Constant.k.length);


            }


            //System.out.println("before in" + UnicodeConverter.revert(checkElmt));
            if (checkElmt[checkElmt.length - 1] == 30) {
                oldTopElmt = checkElmt.clone();
                oldTopElmt[oldTopElmt.length - 1] = 26;

                for (int i2 = 0; i2 < oldTopElmt.length; i2++) {
                    input1 += oldTopElmt[i2];
                }

                alter_str = checknoun_string_andtag(input1, oldTopElmt);

                ending_check = true;


            } else if (checkElmt[checkElmt.length - 1] == 22) {
                oldTopElmt = checkElmt.clone();
                oldTopElmt[oldTopElmt.length - 1] = 23;

                for (int i2 = 0; i2 < oldTopElmt.length; i2++) {
                    input3 += oldTopElmt[i2];
                }
                alter_str = checknoun_string_andtag(input3, oldTopElmt);
                //output.append(iPtoIm);
                ending_check = true;
            } else if (checkElmt[checkElmt.length - 1] == 18) {
                oldTopElmt = checkElmt.clone();
                oldTopElmt[oldTopElmt.length - 1] = 19;

                for (int i2 = 0; i2 < oldTopElmt.length; i2++) {
                    input4 += oldTopElmt[i2];
                }


                alter_str = checknoun_string_andtag(input4, oldTopElmt);
                //output.append(iTtoIn);
                ending_check = true;
            } else if (checkElmt[checkElmt.length - 1] == 24) {

                oldTopElmt = ByteMeth.subArray(checkElmt, 0, checkElmt.length - 1);
                //System.out.println("Now the String is" + UnicodeConverter.revert(oldTopElmt));
                for (int i2 = 0; i2 < oldTopElmt.length; i2++) {
                    input5 += oldTopElmt[i2];
                }
                alter_str = checknoun_string_andtag(input5, oldTopElmt);
                //output.append(yam_rmv);
                ending_check = true;
            } else if (ByteMeth.endsWith(checkElmt, Constant.yam)) {
                //System.out.println("YAM");
                oldTopElmt = ByteMeth.subArray(checkElmt, 0, checkElmt.length - Constant.yam.length);
                for (int i2 = 0; i2 < oldTopElmt.length; i2++) {
                    input5 += oldTopElmt[i2];
                }
                alter_str = checknoun_string_andtag(input5, oldTopElmt);
                //output.append(yam_rmv);
                ending_check = true;
            } else if (ByteMeth.endsWith(checkElmt, Constant.yng)) {
                oldTopElmt = ByteMeth.subArray(checkElmt, 0, checkElmt.length - Constant.yng.length);
                for (int i2 = 0; i2 < oldTopElmt.length; i2++) {
                    input6 += oldTopElmt[i2];
                }
                alter_str = checknoun_string_andtag(input6, oldTopElmt);
//					output.append(yng_rmv);
                ending_check = true;
            } else if (ByteMeth.endsWith(checkElmt, Constant.ang)) {
                oldTopElmt = ByteMeth.subArray(checkElmt, 0, checkElmt.length - Constant.ang.length);
                for (int i2 = 0; i2 < oldTopElmt.length; i2++) {
                    input7 += oldTopElmt[i2];
                }
                alter_str = checknoun_string_andtag(input7, oldTopElmt);

                ending_check = true;
            } else if (ByteMeth.isEqual(checkElmt, Constant.per)) {
                oldTopElmt = Constant.perumai;
                for (int i2 = 0; i2 < oldTopElmt.length; i2++) {
                    input7 += oldTopElmt[i2];
                }
                //System.out.println("OOR" + UnicodeConverter.revert(oldTopElmt));
                alter_str = checknoun_string_andtag(input7, oldTopElmt);

                ending_check = true;
                if (alter_str == null) {
                    if (Adjective_BTree.contains(input7)) {
                        alter_str = UnicodeConverter.revert(oldTopElmt) + "<Adjective>" + Tag.Adjective;
                    }
                }
            } else if (ByteMeth.isEqual(checkElmt, Constant.citrur)) {
                oldTopElmt = Constant.sirumai;
                for (int i2 = 0; i2 < oldTopElmt.length; i2++) {
                    input7 += oldTopElmt[i2];
                }
                //System.out.println("OOR" + UnicodeConverter.revert(oldTopElmt));

                alter_str = checknoun_string_andtag(input7, oldTopElmt);

                ending_check = true;
                if (alter_str == null) {
                    if (Adjective_BTree.contains(input7)) {
                        alter_str = UnicodeConverter.revert(oldTopElmt) + "<Adjective>" + Tag.Adjective;
                    }
                }
            } else if (ending_check == false) {
                for (int i2 = 0; i2 < checkElmt.length; i2++) {
                    input7 += checkElmt[i2];
                }
                String rec_noun_check = UnicodeConverter.revert(checkElmt);

                if (sandhi_check == true) {
                    noun_ch = checknoun_string_andtag(input7, checkElmt);
                    ////System.out.println("Before m_end"+UnicodeConverter.revert(checkElmt));
                    if (noun_ch == null) {
                        if (m_End_Noun_BTree.contains(input7)) {
                            noun_ch = UnicodeConverter.revert(checkElmt) + "<m_EndNoun>";


                        } else {
                            noun_ch = handle_double(checkElmt);
                            ////System.out.println("Recursive Success"+noun_ch);
                        }
                    }

                    alter_str = alter_str + noun_ch + sandhi_str + "<sandhi>";
                } else {
                    //alter_str= alter_str+handle_double(rec_noun_check);
                    noun_ch = checknoun_string_andtag(input7, checkElmt);
                    alter_str = alter_str + noun_ch;
                }

            }



        }
        ////System.out.println("The remaining tagged output is"+alter_str);
        return alter_str;
    }

    public static String checknoun_string_andtag(String input, byte[] TopElmt) {
        System.out.println("Enter into ADictionary checknoun_string_andtag method");
        StringBuffer output = new StringBuffer();
        TreeSet set = new TreeSet();
        String tag_str = "";
        ////System.out.println("IRTOIN"+UnicodeConverter.revert(TopElmt));
        if (Noun_BTree.contains(input)) {


            tag_str = UnicodeConverter.revert(TopElmt);
            //if(set.add(tag_str))
            output.append(tag_str + "<Noun &" + Tag.Noun + ">");
            return output.toString();


        } else if (m_End_Noun_BTree.contains(input)) {
            tag_str = UnicodeConverter.revert(TopElmt);
            //if(set.add(tag_str))
            output.append(tag_str + "<AdjectivalNoun>" + Tag.AdjectivalNoun);
            return output.toString();
        } else if (Demo_Adj_BTree.contains(input)) {
            tag_str = UnicodeConverter.revert(TopElmt);
            //if(set.add(tag_str))
            output.append(tag_str + "<Demo_Adj>" + Tag.DemonstrativeAdjective);
            return output.toString();
        } else if (Adjective_BTree.contains(input)) {
            tag_str = UnicodeConverter.revert(TopElmt);
            //if(set.add(tag_str))
            output.append(tag_str + "<Adjective>" + Tag.Adjective);
            return output.toString();
        } else if (Char_Num_BTree.contains(input)) {
            tag_str = UnicodeConverter.revert(TopElmt);
            //if(set.add(tag_str))
            output.append(tag_str + " " + "< charNumbers " + "& " + Tag.cNumbers + " > ");
            return output.toString();
        }

        return null;
    }

    public static String colloquial(Stack allStk, byte[] topElmt3) {
        System.out.println("Enter into ADictionary colloquial method");
        String new_pro = UnicodeConverter.revert(topElmt3);
        System.out.println("topElmt3 " + new_pro);
        Number_Conversion nc = new Number_Conversion();
        String analysed = "";
        String result = "";
        try {
            int itr = 1;
            int itr_del = 1;
            int itr_len2 = 2;
            BufferedWriter bw = new BufferedWriter(new FileWriter("./outputcollquial.txt", true));
            Stack s = new Stack();
            for (int i = 0; i < topElmt3.length; i++) {
                s.push(new Entry(topElmt3, -1));
            }
            int length = 0;
            topElmt3 = ((Entry) s.peek()).getPart();
            byte[] checkElmt = topElmt3;
            length = topElmt3.length;
            byte[] topElmt_new = null;
            byte[] topElmt_news = null;
            byte[] checkElmt_new = null;
            byte[] checkElmt_new_next = null;
            byte[] check_value = null;
            byte[] check_value_add = null;
            byte[] end_char = null;
            byte[] prev_char = null;
            byte[] ch1 = null;
            byte[] ch2 = null;
            byte[] ch3 = null;
            byte[] ch4 = null;
            byte[] next_char = null;
            byte[] cf = null;
            String s1 = "";
            //String result = "";
            String values = "";

            //String str = UnicodeConverter.revert(checkElmt);
            s1 = UnicodeConverter.revert(checkElmt);
            //System.out.println("Value of whole word outside " + s1);
            while (itr != length) {
                // //System.out.println("enter into collo");
                checkElmt = ByteMeth.subArray(topElmt3, (topElmt3.length) - itr, topElmt3.length);
                String str = UnicodeConverter.revert(checkElmt);
                s1 = UnicodeConverter.revert(checkElmt);
                System.out.println("Value of whole word " + s1);
                // //System.out.println("Length of whole word" + topElmt3.length);
//



                if (ByteMeth.startsWith(checkElmt, Constant.chchu)) {
                    ////System.out.println("pooochchu");
                    if ((checkElmt.length > 3)) {
                        check_value = ByteMeth.subArray(checkElmt, 3, checkElmt.length); //ppochchu
                        String s5 = UnicodeConverter.revert(check_value);
                        ////System.out.println("Result of check_value " + s5);
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        String s6 = UnicodeConverter.revert(prev_char);
                        ////System.out.println("Result of prev_char " + s6);
                        //int check=checkElmt.length;
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        String sr9 = UnicodeConverter.revert(cf);
                        ////System.out.println("cf " + sr9);
                        if (ByteMeth.contains(cf, Constant.chchu)) {
                            String sr5 = UnicodeConverter.revert(checkElmt);
                            ////System.out.println("checkElmt after contains " + sr5);

                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                                String sr7 = UnicodeConverter.revert(checkElmt_new);
                                ////System.out.println("checkElmt_new " + sr7);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.A)) || (ByteMeth.startsWith(checkElmt_new, Constant.O))) {
                                    topElmt_new = ByteMeth.addArray(Constant.yitru, check_value);
                                    end_char = ByteMeth.addArray(prev_char, topElmt_new);
                                    result = UnicodeConverter.revert(end_char);
                                } //if close
                                else if ((ByteMeth.startsWith(checkElmt_new, Constant.i)) || (ByteMeth.startsWith(checkElmt_new, Constant.ai))) {
                                    topElmt_new = ByteMeth.addArray(Constant.ththu, check_value);
                                    end_char = ByteMeth.addArray(prev_char, topElmt_new);
                                    result = UnicodeConverter.revert(end_char);
                                }
                            }

                        }
                    } else { //போச்சு
                        if (ByteMeth.contains(checkElmt, Constant.chchu)) {
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length));
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                                String s3 = UnicodeConverter.revert(checkElmt_new);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.A)) || (ByteMeth.startsWith(checkElmt_new, Constant.O))) {
                                    topElmt_news = ByteMeth.addArray(remain_char, Constant.yitru);
                                    String tag_first = UnicodeConverter.revert(topElmt_news);
                                    result = tag_first;
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.i)) || (ByteMeth.startsWith(checkElmt_new, Constant.ai))) {
                                    topElmt_news = ByteMeth.addArray(remain_char, Constant.ththu);
                                    String tag_first = UnicodeConverter.revert(topElmt_news);
                                    result = tag_first;
                                }
                            }
                        }
                    }
                    //  analysed = Analyser.analyseF(result, true);
                    // break;
                }


                if (ByteMeth.startsWith(checkElmt, Constant.nju)) { //செஞ்சுப்பாரு
                    ////System.out.println("Enter into seinjuppaaru");
                    values = UnicodeConverter.revert(checkElmt);
                    if ((checkElmt.length > 3)) {
                        check_value = ByteMeth.subArray(checkElmt, 3, checkElmt.length);
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        if (ByteMeth.contains(cf, Constant.nju)) {
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.A)) || (ByteMeth.startsWith(checkElmt_new, Constant.E))) {
                                    topElmt_new = ByteMeth.addArray(Constant.Yndhu, check_value);
                                    end_char = ByteMeth.addArray(prev_char, topElmt_new);
                                    result = UnicodeConverter.revert(end_char);
                                } //if close
                                else if ((ByteMeth.startsWith(checkElmt_new, Constant.e))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.ai))) {
                                    topElmt_new = ByteMeth.addArray(Constant.ydhu, check_value);
                                    end_char = ByteMeth.addArray(prev_char, topElmt_new);
                                    result = UnicodeConverter.revert(end_char);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.i))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.ai))) {
                                    topElmt_new = ByteMeth.addArray(Constant.n_thu, check_value);
                                    end_char = ByteMeth.addArray(prev_char, topElmt_new);
                                    result = UnicodeConverter.revert(end_char);
                                }
                            }
                        }
                    } else { //செஞ்சு
                        if (ByteMeth.contains(checkElmt, Constant.nju)) {
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length));
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);

                                if ((ByteMeth.startsWith(checkElmt_new, Constant.A)) || (ByteMeth.startsWith(checkElmt_new, Constant.E))) {
                                    topElmt_news = ByteMeth.addArray(remain_char, Constant.Yndhu);
                                    String tag_first = UnicodeConverter.revert(topElmt_news);
                                    result = tag_first;
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.e))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.ai))) {
                                    topElmt_news = ByteMeth.addArray(remain_char, Constant.ydhu);
                                    String tag_first = UnicodeConverter.revert(topElmt_news);
                                    result = tag_first;
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.i))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.ai))) {
                                    topElmt_news = ByteMeth.addArray(remain_char, Constant.n_thu);
                                    String tag_first = UnicodeConverter.revert(topElmt_news);
                                    result = tag_first;
                                }
                            }
                        }
                    }
                    //  analysed = Analyser.analyseF(result, true);
                    //break;
                }

                if (ByteMeth.startsWith(checkElmt, Constant.AAththu)) {
                    ////System.out.println("Enter into ppaaththu");
                    if ((checkElmt.length > 4)) {
                        check_value = ByteMeth.subArray(checkElmt, 4, checkElmt.length); //ppochchu
                        String s5 = UnicodeConverter.revert(check_value);
                        ////System.out.println("Result of check_value " + s5);
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        String s6 = UnicodeConverter.revert(prev_char);
                        ////System.out.println("Result of prev_char " + s6);
                        //int check=checkElmt.length;
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        String sr9 = UnicodeConverter.revert(cf);
                        ////System.out.println("cf " + sr9);
                        if (ByteMeth.contains(cf, Constant.AAththu)) {
                            String sr5 = UnicodeConverter.revert(checkElmt);
                            ////System.out.println("checkElmt after contains " + sr5);

                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                                String sr7 = UnicodeConverter.revert(checkElmt_new);
                                ////System.out.println("checkElmt_new " + sr7);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.p))) { //paaththuppochchu
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.Ar);
                                    next_char = ByteMeth.addArray(topElmt_new, Constant.ththu);
                                    end_char = ByteMeth.addArray(next_char, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.k))) { //kaathuppochchu
                                    ////System.out.println("Enter into k of long word");
                                    topElmt_new = ByteMeth.addArray(Constant.RRu, check_value);
                                    next_char = ByteMeth.addArray(prev_char, Constant.A);
                                    end_char = ByteMeth.addArray(next_char, topElmt_new);
                                    String tag_first = UnicodeConverter.revert(end_char);
                                    result = tag_first;
                                }
                            }

                        }
                    } else {
                        if (ByteMeth.contains(checkElmt, Constant.AAththu)) {
                            ////System.out.println("Enter into else if Aa at last");
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.p))) { //paaththu
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.Ar);
                                    next_char = ByteMeth.addArray(topElmt_new, Constant.ththu);
                                    result = UnicodeConverter.revert(next_char);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.k))) { //kaathu
                                    String sr = UnicodeConverter.revert(remain_char);
                                    next_char = ByteMeth.addArray(remain_char, Constant.A);
                                    topElmt_new = ByteMeth.addArray(next_char, Constant.RRu);
                                    String tag_first = UnicodeConverter.revert(topElmt_new);
                                    result = tag_first;
                                }
                            }
                        }
                    }
                    //        analysed = Analyser.analyseF(result, true);
                    // break;
                }

                if (ByteMeth.startsWith(checkElmt, Constant.Num)) {
                    ////System.out.println("Enter into num");
                    if ((checkElmt.length > 3)) {
                        check_value = ByteMeth.subArray(checkElmt, 3, checkElmt.length); //ppochchu
                        // String s5 = UnicodeConverter.revert(check_value);
                        // ////System.out.println("Result of check_value " + s5);
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        //  String s6 = UnicodeConverter.revert(prev_char);
                        //  ////System.out.println("Result of prev_char " + s6);
                        //int check=checkElmt.length;
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        // ////System.out.println("cf " + sr9);
                        if (ByteMeth.contains(cf, Constant.Num)) {
                            //   String sr5 = UnicodeConverter.revert(checkElmt);
                            //   ////System.out.println("checkElmt after contains " + sr5);

                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                                //  String sr7 = UnicodeConverter.revert(checkElmt_new);
                                //  ////System.out.println("checkElmt_new " + sr7);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.E))) { //paaththuppochchu
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.Ndum);
                                    //next_char = ByteMeth.addArray(topElmt_new, Constant.ththu);
                                    end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                } else { //kaathuppochchu
                                    ////System.out.println("Enter into k of long word");
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.vENdum);
                                    //next_char = ByteMeth.addArray(prev_char, Constant.A);
                                    end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    String tag_first = UnicodeConverter.revert(end_char);
                                    result = tag_first;
                                }
                            }

                        }
                    } else {
                        if (ByteMeth.contains(checkElmt, Constant.Num)) {
                            ////System.out.println("Enter into else if Aa at last");
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.E))) { //paaththu
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.Ndum);
                                    //next_char = ByteMeth.addArray(topElmt_new, Constant.ththu);
                                    result = UnicodeConverter.revert(topElmt_new);
                                } else { //kaathu
                                    String sr = UnicodeConverter.revert(remain_char);
                                    next_char = ByteMeth.addArray(remain_char, Constant.vENdum);
                                    //topElmt_new = ByteMeth.addArray(next_char, Constant.RRu);
                                    String tag_first = UnicodeConverter.revert(next_char);
                                    result = tag_first;
                                }
                            }
                        }
                        //        analysed = Analyser.analyseF(result, true);
                        // break;
                    }
                }

                /* if (ByteMeth.startsWith(checkElmt, Constant.pa)) {
                //System.out.println("Enter into pa");
                if ((checkElmt.length > 2)) {
                check_value = ByteMeth.subArray(checkElmt, 2, checkElmt.length); //ppochchu
                prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                if (ByteMeth.contains(cf, Constant.pa)) {
                if (itr_del != length) {
                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                if ((ByteMeth.startsWith(checkElmt_new, Constant.p))) { //paaththuppochchu
                topElmt_new = ByteMeth.addArray(prev_char, Constant.pozuthu);
                end_char = ByteMeth.addArray(topElmt_new, check_value);
                result = UnicodeConverter.revert(end_char);
                }
                }

                }
                } else {
                if (ByteMeth.contains(checkElmt, Constant.pa)) {

                //System.out.println("Enter into else if Aa at last");
                byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                if (itr_del != length) {
                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                if ((ByteMeth.startsWith(checkElmt_new, Constant.p))) { //paaththu
                topElmt_new = ByteMeth.addArray(remain_char, Constant.pozuthu);
                result = UnicodeConverter.revert(topElmt_new);
                }
                }
                }
                }
                }*/

                //la before l present then tha add e.g pollaa to polladha
                if (ByteMeth.startsWith(checkElmt, Constant.laa)) {
                    //System.out.println("Enter into pa");
                    /*  if ((checkElmt.length > 2)) {
                    check_value = ByteMeth.subArray(checkElmt, 2, checkElmt.length); //ppochchu
                    prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                    cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                    if (ByteMeth.contains(cf, Constant.laa)) {
                    if (itr_del != length) {
                    checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                    checkElmt_new_next= ByteMeth.subArray(checkElmt, itr_len2-(checkElmt.length),checkElmt.length);
                    String check_next=UnicodeConverter.revert(checkElmt_new_next);
                    //System.out.println("Checnk_next "+check_next);
                    if ((ByteMeth.startsWith(checkElmt_new, Constant.l)) || (ByteMeth.startsWith(checkElmt_new_next, Constant.tha)) ) { //paaththuppochchu
                    topElmt_new = ByteMeth.addArray(prev_char, cf);
                    check_value_add=ByteMeth.addArray(topElmt_new, Constant.tha);
                    end_char = ByteMeth.addArray(check_value_add, check_value);
                    result = UnicodeConverter.revert(end_char);
                    }
                    }

                    }
                    } else {*/
                    if (ByteMeth.contains(checkElmt, Constant.laa)) {

                        //System.out.println("Enter into else if Aa at last");
                        byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                        if (itr_del != length) {
                            checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                            if ((ByteMeth.startsWith(checkElmt_new, Constant.l))) { //paaththu
                                topElmt_new = ByteMeth.addArray(remain_char, checkElmt);
                                check_value_add = ByteMeth.addArray(topElmt_new, Constant.tha);
                                result = UnicodeConverter.revert(check_value_add);
                            }
                        }
                        // }
                    }
                }


                if (ByteMeth.startsWith(checkElmt, Constant.En)) {
                    //System.out.println("Enter into En");
                    if ((checkElmt.length > 2)) {
                        check_value = ByteMeth.subArray(checkElmt, 2, checkElmt.length); //ppochchu
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        if (ByteMeth.contains(cf, Constant.En)) {
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.k))) { //paaththuppochchu
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.iren);
                                    end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                }
                            }

                        }
                    } else {
                        if (ByteMeth.contains(checkElmt, Constant.En)) {

                            //System.out.println("Enter into else if Aa at last");
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.k))) { //paaththu
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.iren);
                                    result = UnicodeConverter.revert(topElmt_new);
                                }
                            }
                        }
                    }
                }


                /* if (ByteMeth.startsWith(checkElmt, Constant.kA)) {
                //System.out.println("Enter into En");
                if ((checkElmt.length > 2)) {
                check_value = ByteMeth.subArray(checkElmt, 2, checkElmt.length); //ppochchu
                prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                if (ByteMeth.contains(cf, Constant.kA)) {
                if (itr_del != length) {
                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                if ((ByteMeth.startsWith(checkElmt_new, Constant.k))) { //paaththuppochchu
                topElmt_new = ByteMeth.addArray(prev_char, Constant.kinradhA);
                end_char = ByteMeth.addArray(topElmt_new, check_value);
                result = UnicodeConverter.revert(end_char);
                }
                }

                }
                } else {
                if (ByteMeth.contains(checkElmt, Constant.kA)) {

                //System.out.println("Enter into else if Aa at last");
                byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                if (itr_del != length) {
                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                if ((ByteMeth.startsWith(checkElmt_new, Constant.k))) { //paaththu
                topElmt_new = ByteMeth.addArray(remain_char, Constant.kinradhA);
                result = UnicodeConverter.revert(topElmt_new);
                }
                }
                }
                }
                }*/


                if (ByteMeth.startsWith(checkElmt, Constant.nikku)) {
                    //System.out.println("Enter into nikku");
                    if ((checkElmt.length > 5)) {
                        check_value = ByteMeth.subArray(checkElmt, 5, checkElmt.length); //ppochchu
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        if (ByteMeth.contains(cf, Constant.nikku)) {
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.n))) { //paaththuppochchu
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.raiku);
                                    end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                }
                            }

                        }
                    } else {

                        if (ByteMeth.contains(checkElmt, Constant.nikku)) {
                            //System.out.println("Enter into else if Aa at last");
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.n))) { //paaththu
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.raiku);
                                    result = UnicodeConverter.revert(topElmt_new);
                                }
                            }
                        }
                    }
                }

                if (ByteMeth.startsWith(checkElmt, Constant.pO)) {
                    //System.out.println("Enter into pO");
                    if ((checkElmt.length > 2)) {
                        check_value = ByteMeth.subArray(checkElmt, 2, checkElmt.length); //ppochchu
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        if (ByteMeth.contains(cf, Constant.pO)) {
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.p))) { //paaththuppochchu
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.pozuthu);
                                    end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                }
                            }

                        }
                    } else {
                        //System.out.println("Enter into else if Aa at last");
                        if (ByteMeth.contains(checkElmt, Constant.pO)) {
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.p))) { //paaththu
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.pozuthu);
                                    result = UnicodeConverter.revert(topElmt_new);
                                }
                            }
                        }
                    }
                }


                if (ByteMeth.startsWith(checkElmt, Constant.inga)) {
                    //System.out.println("Enter into inga");
                    if ((checkElmt.length > 4)) {
                        check_value = ByteMeth.subArray(checkElmt, 4, checkElmt.length); //ppochchu
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        if (ByteMeth.contains(cf, Constant.inga)) {
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.k))) { //paaththuppochchu
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.ireerkal);
                                    end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                }
                            }

                        }
                    } else {
                        //System.out.println("Enter into else if Aa at last");
                        if (ByteMeth.contains(checkElmt, Constant.inga)) {
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.k))) { //paaththu
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.ireerkal);
                                    result = UnicodeConverter.revert(topElmt_new);
                                }
                            }
                        }
                    }
                }


                if (ByteMeth.startsWith(checkElmt, Constant.kinu)) {
                    //System.out.println("Enter into kinu");
                    if ((checkElmt.length > 4)) {
                        check_value = ByteMeth.subArray(checkElmt, 4, checkElmt.length); //ppochchu
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        if (ByteMeth.contains(cf, Constant.kinu)) {
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                                if ((ByteMeth.isEqual(checkElmt_new, Constant.k))) { //paaththuppochchu
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.koNdiru);
                                    end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                } else if (!(ByteMeth.isEqual(checkElmt_new, Constant.k))) {
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.kkoNdiru);
                                    end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                }
                            }

                        }
                    } else {
                        //System.out.println("Enter into else if Aa at last");
                        if (ByteMeth.contains(checkElmt, Constant.kinu)) {
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                                if ((ByteMeth.isEqual(checkElmt_new, Constant.k))) { //paaththu
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.koNdiru);
                                    result = UnicodeConverter.revert(topElmt_new);
                                } else if (!(ByteMeth.isEqual(checkElmt_new, Constant.k))) {
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.kkoNdiru);
                                    result = UnicodeConverter.revert(topElmt_new);
                                }
                            }
                        }
                    }
                }

                if (ByteMeth.startsWith(checkElmt, Constant.mputtu)) {
                    //System.out.println("Enter into mpuuttu");
                    if ((checkElmt.length > 6)) {
                        check_value = ByteMeth.subArray(checkElmt, 6, checkElmt.length); //ppochchu
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        if (ByteMeth.contains(cf, Constant.mputtu)) {
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.i)) || (ByteMeth.startsWith(checkElmt_new, Constant.a)) || (ByteMeth.startsWith(checkElmt_new, Constant.e))) { //paaththuppochchu
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.vvalavu);
                                    end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                }
                            }

                        }
                    } else {
                        //System.out.println("Enter into else if Aa at last");
                        if (ByteMeth.contains(checkElmt, Constant.mputtu)) {
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.i)) || (ByteMeth.startsWith(checkElmt_new, Constant.a)) || (ByteMeth.startsWith(checkElmt_new, Constant.e))) { //paaththu
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.vvalavu);
                                    result = UnicodeConverter.revert(topElmt_new);
                                }
                            }
                        }
                    }
                }

                if (ByteMeth.startsWith(checkElmt, Constant.yinum)) {
                    //System.out.println("Enter into yinum");
                    if ((checkElmt.length > 5)) {
                        check_value = ByteMeth.subArray(checkElmt, 5, checkElmt.length); //ppochchu
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        if (ByteMeth.contains(cf, Constant.yinum)) {
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.A))) { //paaththuppochchu
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.naalum);
                                    end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                }
                            }

                        }
                    } else {
                        //System.out.println("Enter into else if Aa at last");
                        if (ByteMeth.contains(checkElmt, Constant.yinum)) {
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.A))) { //paaththu
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.naalum);
                                    result = UnicodeConverter.revert(topElmt_new);
                                }
                            }
                        }
                    }
                }

                if (ByteMeth.startsWith(checkElmt, Constant.uchchi)) {
                    //System.out.println("Enter into uchchi");
                    if ((checkElmt.length > 4)) {
                        check_value = ByteMeth.subArray(checkElmt, 4, checkElmt.length); //ppochchu
                        String f = UnicodeConverter.revert(check_value);
                        //System.out.println("Enter into check_value " + f);
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        String h = UnicodeConverter.revert(prev_char);
                        //System.out.println("Enter into prev_char " + h);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        if (ByteMeth.contains(cf, Constant.uchchi)) {
                            //System.out.println("Enter into uchchi");
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                                if ((ByteMeth.isEqual(checkElmt_new, Constant.th))) { //paaththuppochchu
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.athu);
                                    end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                }/* else if (ByteMeth.isEqual(checkElmt_new, Constant.t)) {
                                if (itr_len2 != length) {
                                check_value_add = ByteMeth.subArray(prev_char, (prev_char.length) - itr_len2, prev_char.length);
                                if (ByteMeth.isEqual(check_value_add, Constant.u)) {
                                prev_char[prev_char.length - 1] = 27;
                                topElmt_new = ByteMeth.addArray(prev_char, Constant.ittadhu);
                                end_char = ByteMeth.addArray(topElmt_new, check_value);
                                result = UnicodeConverter.revert(end_char);
                                } else if (ByteMeth.isEqual(check_value_add, Constant.i)) {
                                prev_char[prev_char.length - 1] = 27;
                                prev_char[prev_char.length - 2] = 5;
                                topElmt_new = ByteMeth.addArray(prev_char, Constant.ittadhu);
                                end_char = ByteMeth.addArray(topElmt_new, check_value);
                                result = UnicodeConverter.revert(end_char);

                                }
                                }
                                }*/

                            }
                        }
                    } else {
                        if (ByteMeth.contains(checkElmt, Constant.uchchi)) {
                            String k = UnicodeConverter.revert(checkElmt);

                            //System.out.println("Enter into else if Aa at last " + k);
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                            String e = UnicodeConverter.revert(remain_char);
                            //System.out.println("Enter into remain_char " + e);
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                                String d = UnicodeConverter.revert(checkElmt_new);
                                //System.out.println("Enter into uchchi of else " + d);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.th))) { //paaththu
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.athu);
                                    result = UnicodeConverter.revert(topElmt_new);
                                }
                            }
                        }

                    }
                }

                if (ByteMeth.startsWith(checkElmt, Constant.duchchi)) {
                    //System.out.println("Enter into duchchi");
                    if ((checkElmt.length > 5)) {
                        check_value = ByteMeth.subArray(checkElmt, 5, checkElmt.length); //ppochchu
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        if (ByteMeth.contains(cf, Constant.duchchi)) {
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.u))) { //paaththuppochchu
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.vittadhu);
                                    end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                } else {
                                    prev_char[prev_char.length - 1] = 5;
                                    //System.out.println("Prev_cahr " + prev_char);
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.vittadhu);
                                    end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                }
                            }

                        }
                    } else {
                        //System.out.println("Enter into else if Aa at last");
                        if (ByteMeth.contains(checkElmt, Constant.duchchi)) {
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.u))) { //paaththu
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.vittadhu);
                                    result = UnicodeConverter.revert(topElmt_new);
                                } else {
                                    remain_char[remain_char.length - 1] = 5;
                                    //System.out.println("remain_cahr " + remain_char);
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.vittadhu);
                                    result = UnicodeConverter.revert(topElmt_new);
                                }
                            }
                        }
                    }
                }

                if (ByteMeth.startsWith(checkElmt, Constant.itta)) {
                    //System.out.println("Enter into itta");
                    if ((checkElmt.length > 4)) {
                        check_value = ByteMeth.subArray(checkElmt, 4, checkElmt.length); //ppochchu
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        if (ByteMeth.contains(cf, Constant.itta)) {
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.k))) { //paaththuppochchu
                                    prev_char[prev_char.length - 1] = 29;
                                    //System.out.println("Prev_cahr " + prev_char);
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.itam);
                                    end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                }
                            }

                        }
                    } else {
                        //System.out.println("Enter into else if Aa at last");
                        if (ByteMeth.contains(checkElmt, Constant.itta)) {
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.k))) { //paaththu
                                    remain_char[remain_char.length - 1] = 29;
                                    //System.out.println("remain_cahr " + remain_char);
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.itam);
                                    result = UnicodeConverter.revert(topElmt_new);
                                }
                            }
                        }
                    }
                }

                if (ByteMeth.startsWith(checkElmt, Constant.Oda)) {
                    //System.out.println("Enter into itta");
                    if ((checkElmt.length > 3)) {
                        check_value = ByteMeth.subArray(checkElmt, 3, checkElmt.length); //ppochchu
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        if (ByteMeth.contains(cf, Constant.Oda)) {
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.n)) || (ByteMeth.startsWith(checkElmt_new, Constant.y)) || (ByteMeth.startsWith(checkElmt_new, Constant.r))) { //paaththuppochchu
                                    //prev_char[prev_char.length - 1] = 29;
                                    //System.out.println("Prev_cahr " + prev_char);
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.utan);
                                    end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                }
                            }

                        }
                    } else {
                        //System.out.println("Enter into else if Aa at last");
                        if (ByteMeth.contains(checkElmt, Constant.Oda)) {
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.n)) || (ByteMeth.startsWith(checkElmt_new, Constant.y)) || (ByteMeth.startsWith(checkElmt_new, Constant.r))) { //paaththu
                                    //remain_char[remain_char.length - 1] = 29;
                                    //System.out.println("remain_cahr " + remain_char);
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.utan);
                                    result = UnicodeConverter.revert(topElmt_new);
                                }
                            }
                        }
                    }
                }

                if (ByteMeth.startsWith(checkElmt, Constant.kaLaa)) {
                    //System.out.println("Enter into itta");
                    if ((checkElmt.length > 4)) {
                        check_value = ByteMeth.subArray(checkElmt, 4, checkElmt.length); //ppochchu
                        String o = UnicodeConverter.revert(check_value);
                        //System.out.println("Check value " + o);
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        String oo = UnicodeConverter.revert(prev_char);
                        //System.out.println("prev value " + oo);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        if (ByteMeth.contains(cf, Constant.kaLaa)) {
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.ng))) {//|| (ByteMeth.startsWith(checkElmt_new, Constant.y))) { //paaththuppochchu
                                    prev_char[prev_char.length - 1] = 25;
                                    String j = UnicodeConverter.revert(prev_char);

                                    //System.out.println("Prev_cahr " + prev_char + "  " + j);
                                    topElmt_new = ByteMeth.addArray(prev_char, cf);
                                    end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    String p = UnicodeConverter.revert(end_char);
                                    //System.out.println("End char " + p);
                                    result = UnicodeConverter.revert(end_char);
                                }
                            }

                        }
                    } else {
                        //System.out.println("Enter into else if Aa at last");
                        if (ByteMeth.contains(checkElmt, Constant.kaLaa)) {
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.ng))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.y))) { //paaththu
                                    remain_char[remain_char.length - 1] = 25;
                                    String l = UnicodeConverter.revert(remain_char);
                                    //System.out.println("remain_cahr " + remain_char + "       " + l);

                                    topElmt_new = ByteMeth.addArray(remain_char, checkElmt);
                                    String u = UnicodeConverter.revert(topElmt_new);
                                    //System.out.println("String of u " + u);
                                    result = UnicodeConverter.revert(topElmt_new);
                                }
                            }
                        }
                    }
                }


                if (ByteMeth.startsWith(checkElmt, Constant.la)) {
                    //System.out.println("Enter into itta");
                    if ((checkElmt.length > 2)) {
                        check_value = ByteMeth.subArray(checkElmt, 2, checkElmt.length); //ppochchu
                        String o = UnicodeConverter.revert(check_value);
                        ////System.out.println("Check value "+o);
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        String oo = UnicodeConverter.revert(prev_char);
                        ////System.out.println("prev value "+oo);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        if (ByteMeth.contains(cf, Constant.la)) {
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.r))) {//|| (ByteMeth.startsWith(checkElmt_new, Constant.y))) { //paaththuppochchu
                                    String j = UnicodeConverter.revert(prev_char);
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.il);
                                    end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.ai))) {//|| (ByteMeth.startsWith(checkElmt_new, Constant.r))) {//|| (ByteMeth.startsWith(checkElmt_new, Constant.y))) { //paaththuppochchu
                                    String j = UnicodeConverter.revert(prev_char);
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.yil);
                                    end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.a))) {//|| (ByteMeth.startsWith(checkElmt_new, Constant.r))) {//|| (ByteMeth.startsWith(checkElmt_new, Constant.y))) { //paaththuppochchu
                                    String j = UnicodeConverter.revert(prev_char);
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.villai);
                                    end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.i))) {//|| (ByteMeth.startsWith(checkElmt_new, Constant.r))) {//|| (ByteMeth.startsWith(checkElmt_new, Constant.y))) { //paaththuppochchu
                                    String j = UnicodeConverter.revert(prev_char);
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.l);
                                    end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.u))) {//|| (ByteMeth.startsWith(checkElmt_new, Constant.r))) {//|| (ByteMeth.startsWith(checkElmt_new, Constant.y))) { //paaththuppochchu
                                    prev_char[prev_char.length - 1] = 3;
                                    //prev_char[prev_char.length - 2] = 3;
                                    String j = UnicodeConverter.revert(prev_char);

                                    // //System.out.println("Prev_cahr " + prev_char+"  "+j);
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.l);
                                    String k = UnicodeConverter.revert(topElmt_new);

                                    ////System.out.println("topElmt_new  " + k);
                                    ///topElmt_new = ByteMeth.addArray(topElmt_new, cf);
                                    end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    String p = UnicodeConverter.revert(end_char);
                                    ////System.out.println("End char " +p);
                                    result = UnicodeConverter.revert(end_char);
                                }

                            }

                        }
                    } else {
                        ////System.out.println("Enter into else if Aa at last");
                        if (ByteMeth.contains(checkElmt, Constant.la)) {
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.r))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.y))) { //paaththu
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.il);
                                    result = UnicodeConverter.revert(topElmt_new);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.ai))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.r))){// || (ByteMeth.startsWith(checkElmt_new, Constant.y))) { //paaththu
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.yil);
                                    result = UnicodeConverter.revert(topElmt_new);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.a))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.r))){// || (ByteMeth.startsWith(checkElmt_new, Constant.y))) { //paaththu
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.villai);
                                    result = UnicodeConverter.revert(topElmt_new);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.i))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.r))){// || (ByteMeth.startsWith(checkElmt_new, Constant.y))) { //paaththu
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.l);
                                    result = UnicodeConverter.revert(topElmt_new);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.u))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.r))){// || (ByteMeth.startsWith(checkElmt_new, Constant.y))) { //paaththu
                                    remain_char[remain_char.length - 1] = 3;
                                    String l = UnicodeConverter.revert(remain_char);
                                    ////System.out.println("remain_cahr " + remain_char+"       "+l);

                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.l);
                                    String u = UnicodeConverter.revert(topElmt_new);
                                    ////System.out.println("String of u "+u);
                                    result = UnicodeConverter.revert(topElmt_new);
                                }
                            }
                        }
                    }
                }

                if (ByteMeth.startsWith(checkElmt, Constant.ththu)) {
                    //System.out.println("Enter into itta");
                    if ((checkElmt.length > 3)) {
                        check_value = ByteMeth.subArray(checkElmt, 3, checkElmt.length); //ppochchu
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        if (ByteMeth.contains(cf, Constant.thu)) {
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.A)) || (ByteMeth.startsWith(checkElmt_new, Constant.E))) { //paaththuppochchu
                                    //prev_char[prev_char.length - 1] = 30;
                                    //System.out.println("Prev_cahr " + prev_char);
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.RRu);
                                    end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                }
                            }
                        }
                    } else {
                        //System.out.println("Enter into else if Aa at last");
                        if (ByteMeth.contains(checkElmt, Constant.ththu)) {
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.A)) || (ByteMeth.startsWith(checkElmt_new, Constant.E))) { //paaththu
                                    // remain_char[remain_char.length - 1] = 30;
                                    //System.out.println("remain_cahr " + remain_char);
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.RRu);
                                    result = UnicodeConverter.revert(topElmt_new);
                                }
                            }
                        }
                    }
                }


                if (ByteMeth.startsWith(checkElmt, Constant.udhu)) {
                    //System.out.println("Enter into itta");
                    if ((checkElmt.length > 3)) {
                        check_value = ByteMeth.subArray(checkElmt, 3, checkElmt.length); //ppochchu
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        if (ByteMeth.contains(cf, Constant.udhu)) {
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.k))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.y))) { //paaththuppochchu
                                    //String j = UnicodeConverter.revert(prev_char);
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.inRathu);
                                    end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.y))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.y))) { //paaththuppochchu
                                    //prev_char[prev_char.length - 1] = 14;
                                    ////System.out.println("Prev_cahr " + prev_char);
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.kinRathu);
                                    end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.v))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.y))) { //paaththuppochchu
                                    //prev_char[prev_char.length] = 5;
                                    String j = UnicodeConverter.revert(prev_char);
                                    //System.out.println("Prev_cahr in u " + j);
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.ukinRathu);
                                    end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                }

                            }
                        }
                    } else {
                        //System.out.println("Enter into else if Aa at last");
                        if (ByteMeth.contains(checkElmt, Constant.udhu)) {
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.k))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.y))) { //paaththu
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.inRathu);
                                    result = UnicodeConverter.revert(topElmt_new);

                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.y))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.y))) { //paaththu

                                    //remain_char[remain_char.length - 1] = 14;
                                    ////System.out.println("remain_cahr " + remain_char);
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.kinRathu);
                                    result = UnicodeConverter.revert(topElmt_new);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.v))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.y))) { //paaththu

                                    //remain_char[remain_char.length] = 5;
                                    String p = UnicodeConverter.revert(remain_char);
                                    //System.out.println("remain_cahr " + p);
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.ukinRathu);
                                    result = UnicodeConverter.revert(topElmt_new);
                                }
                            }
                        }
                    }
                }


                if (ByteMeth.startsWith(checkElmt, Constant.nnu)) {
                    //System.out.println("Enter into itta");
                    if ((checkElmt.length > 3)) {
                        check_value = ByteMeth.subArray(checkElmt, 3, checkElmt.length); //ppochchu
                        String o = UnicodeConverter.revert(check_value);
                        ////System.out.println("Check value "+o);
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        String oo = UnicodeConverter.revert(prev_char);
                        ////System.out.println("prev value "+oo);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        if (ByteMeth.contains(cf, Constant.nnu)) {
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.A)) || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu
                                    prev_char[prev_char.length - 1] = 7;
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.nRu);
                                    end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                } else {
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.nRu);
                                    end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                }
                            }
                        }
                    } else {
                        ////System.out.println("Enter into else if Aa at last");
                        if (ByteMeth.contains(checkElmt, Constant.nnu)) {
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.A)) || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) {
                                    remain_char[remain_char.length - 1] = 7;
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.nRu);
                                    result = UnicodeConverter.revert(topElmt_new);
                                } else {
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.nRu);
                                    result = UnicodeConverter.revert(topElmt_new);
                                }
                            }
                        }
                    }
                }


                //For number analyser

                if ((ByteMeth.startsWith(checkElmt, Constant.onr)) || (ByteMeth.startsWith(checkElmt, Constant.Onr))) {
                    System.out.println("Enter into numbers");
                    if ((checkElmt.length > 3)) {
                        check_value = ByteMeth.subArray(checkElmt, 3, checkElmt.length); //ppochchu
                        String o = UnicodeConverter.revert(check_value);
                        ////System.out.println("Check value "+o);
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        String oo = UnicodeConverter.revert(prev_char);
                        ////System.out.println("prev value "+oo);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        if ((ByteMeth.contains(cf, Constant.onr)) || (ByteMeth.contains(cf, Constant.Onr))) {//
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.th)) || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu
                                    prev_char[prev_char.length - 1] = 5;
                                    end_char = ByteMeth.addArray(prev_char, checkElmt);
                                    String p = UnicodeConverter.revert(end_char);
                                    System.out.println("Check value  " + p);
                                    //end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.n))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu
                                    prev_char[prev_char.length - 1] = 5;
                                    prev_char[prev_char.length - 2] = 20;
                                    // System.out.println("else if "+UnicodeConverter.revert(topElmt_new));
                                    // topElmt_new = ByteMeth.addArray(prev_char, Constant.thu);

                                    end_char = ByteMeth.addArray(prev_char, checkElmt);
                                    System.out.println("else if  " + UnicodeConverter.revert(end_char));
                                    result = UnicodeConverter.revert(end_char);
                                }
                            }
                        }
                    } else {
                        //////System.out.println("Enter into else if Aa at last");
                        if ((ByteMeth.contains(checkElmt, Constant.onr)) || (ByteMeth.contains(checkElmt, Constant.Onr))) {
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                            // System.out.println("REmainc har " + UnicodeConverter.revert(remain_char));
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.th)) || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) {
                                    remain_char[remain_char.length - 1] = 5;
                                    topElmt_new = ByteMeth.addArray(remain_char, checkElmt);
                                    System.out.println("topelmt " + UnicodeConverter.revert(topElmt_new));
                                    result = UnicodeConverter.revert(topElmt_new);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.n))) {
                                    remain_char[remain_char.length - 1] = 5;
                                    remain_char[remain_char.length - 2] = 20;
                                    topElmt_new = ByteMeth.addArray(remain_char, checkElmt);
                                    result = UnicodeConverter.revert(topElmt_new);
                                }
                            }
                        }
                    }
                }


                if (ByteMeth.startsWith(checkElmt, Constant.erand)) {// || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu)) {
                    //System.out.println("Enter into two numbers");
                    if ((checkElmt.length > 5)) {
                        check_value = ByteMeth.subArray(checkElmt, 5, checkElmt.length); //ppochchu
                        String o = UnicodeConverter.revert(check_value);
                        //////System.out.println("Check value "+o);
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        String oo = UnicodeConverter.revert(prev_char);
                        //////System.out.println("prev value "+oo);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        if (ByteMeth.contains(cf, Constant.erand)) {
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.th)) || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu
                                    prev_char[prev_char.length - 1] = 5;
                                    end_char = ByteMeth.addArray(prev_char, checkElmt);
                                    String p = UnicodeConverter.revert(end_char);
                                    ////System.out.println("Check value  " + p);
                                    //end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.n))) {//
                                    prev_char[prev_char.length - 2] = 20;
                                    prev_char[prev_char.length - 1] = 20;
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.u);
                                    System.out.println("topElmt_new in  if " + UnicodeConverter.revert(topElmt_new));
                                    // remain_char_add=ByteMeth.addArray(prev_char, topElmt_new);
                                    end_char = ByteMeth.addArray(topElmt_new, checkElmt);
                                    System.out.println("End charin if " + UnicodeConverter.revert(end_char));
                                    result = UnicodeConverter.revert(end_char);
                                }
                            }
                        } else {
                            //////System.out.println("Enter into else if Aa at last");
                            if (ByteMeth.contains(checkElmt, Constant.erand)) {
                                byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                                if (itr_del != length) {
                                    checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                                    if ((ByteMeth.startsWith(checkElmt_new, Constant.th)) || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) {
                                        remain_char[remain_char.length - 1] = 5;
                                        topElmt_new = ByteMeth.addArray(remain_char, checkElmt);
                                        result = UnicodeConverter.revert(topElmt_new);
                                    } else if ((ByteMeth.startsWith(checkElmt_new, Constant.n))) {//
                                        remain_char[remain_char.length - 2] = 20;
                                        remain_char[remain_char.length - 1] = 20;
                                        topElmt_new = ByteMeth.addArray(remain_char, Constant.u);
                                        System.out.println("topElmt_new " + UnicodeConverter.revert(topElmt_new));
                                        // remain_char_add=ByteMeth.addArray(remain_char, topElmt_new);
                                        end_char = ByteMeth.addArray(topElmt_new, checkElmt);
                                        System.out.println("End char " + UnicodeConverter.revert(end_char));
                                        result = UnicodeConverter.revert(end_char);

                                    }
                                }
                            }
                        }
                    }
                }

                if (ByteMeth.startsWith(checkElmt, Constant.mUnr)) {// || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu)) {
                    System.out.println("Enter into munru numbers");
                    if ((checkElmt.length > 4)) {
                        //System.out.println("Enter into munru");
                        check_value = ByteMeth.subArray(checkElmt, 4, checkElmt.length); //ppochchu
                        String o = UnicodeConverter.revert(check_value);
                        System.out.println("Check value in munru " + o); //u
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        String oo = UnicodeConverter.revert(prev_char);
                        System.out.println("prev value munru" + oo);//irupatthu
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        String ooo = UnicodeConverter.revert(cf);
                        System.out.println("cf munru" + ooo);//mUnr
                        if (!ByteMeth.contains(prev_char, Constant.padhi)) {
                            if (ByteMeth.contains(cf, Constant.mUnr)) {
                                if (itr_len2 != length) {
                                    checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_len2, prev_char.length);
                                    if ((ByteMeth.startsWith(checkElmt_new, Constant.th))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                        String so = UnicodeConverter.revert(checkElmt_new);
                                        System.out.println("prev value in if after cf munru" + so);

                                        ch1 = ByteMeth.subArray(prev_char, 0, (prev_char.length - 1) - Constant.thu.length);
                                        String so1 = UnicodeConverter.revert(ch1);
                                        System.out.println("prev value in if after prev_char munru" + so1);

                                        String p1 = UnicodeConverter.revert(ch1);
                                        System.out.println("Check value ch1 munru " + p1);
                                        ch2 = ByteMeth.addArray(ch1, Constant.thu);
                                        String p2 = UnicodeConverter.revert(ch2);
                                        System.out.println("Check value padhu onru ch2 in munru" + p2);

                                        ch3 = ByteMeth.addArray(ch2, checkElmt);
                                        String p3 = UnicodeConverter.revert(ch3);
                                        System.out.println("Check value irupadhu onru ch3 munru" + p3);
                                        result = UnicodeConverter.revert(ch3);
                                    } else if ((ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                        String so = UnicodeConverter.revert(checkElmt_new);
                                        System.out.println("prev value in if after cf munr" + so);

                                        ch1 = ByteMeth.subArray(prev_char, 0, (prev_char.length - 1) - Constant.Ru.length);
                                        String so1 = UnicodeConverter.revert(ch1);
                                        System.out.println("prev value in if after prev_char munr" + so1);

                                        String p1 = UnicodeConverter.revert(ch1);
                                        System.out.println("Check value ch1 " + p1);
                                        ch2 = ByteMeth.addArray(ch1, Constant.Ru);
                                        String p2 = UnicodeConverter.revert(ch2);
                                        System.out.println("Check value padhu onru ch2 munr " + p2);

                                        ch3 = ByteMeth.addArray(ch2, checkElmt);
                                        String p3 = UnicodeConverter.revert(ch3);
                                        System.out.println("Check value irupadhu onru ch3 munr" + p3);
                                        result = UnicodeConverter.revert(ch3);
                                    }
                                }
                            }
                        }else{
                             if (ByteMeth.contains(cf, Constant.mUnr)) {
                                if (itr_del != length) {
                                    checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                                    if ((ByteMeth.startsWith(checkElmt_new, Constant.i))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                        String so = UnicodeConverter.revert(checkElmt_new);
                                        System.out.println("prev value in if after cf munru" + so);//th

                                        ch1 = ByteMeth.subArray(prev_char, 0, (prev_char.length) - Constant.i.length);
                                        //  String so1 = UnicodeConverter.revert(ch1);
                                        //  System.out.println("prev value in if after prev_char munru" + so1);

                                        String p1 = UnicodeConverter.revert(ch1);
                                        System.out.println("Check value ch1 munru " + p1);
                                        ch2 = ByteMeth.addArray(ch1, Constant.thu);
                                        String p2 = UnicodeConverter.revert(ch2);
                                       System.out.println("Check value padhu onru ch2 in munru" + p2);

                                        ch3 = ByteMeth.addArray(ch2, checkElmt);
                                        String p3 = UnicodeConverter.revert(ch3);
                                        System.out.println("Check value irupadhu onru ch3 munru" + p3);
                                        result = UnicodeConverter.revert(ch3);
                                    } else if ((ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                        String so = UnicodeConverter.revert(checkElmt_new);
                                        System.out.println("prev value in if after cf" + so);

                                        ch1 = ByteMeth.subArray(prev_char, 0, (prev_char.length - 1) - Constant.Ru.length);
                                        //String so1 = UnicodeConverter.revert(ch1);
                                        //System.out.println("prev value in if after prev_char" + so1);

                                        String p1 = UnicodeConverter.revert(ch1);
                                        System.out.println("Check value ch1 " + p1);
                                        ch2 = ByteMeth.addArray(ch1, Constant.Ru);
                                        String p2 = UnicodeConverter.revert(ch2);
                                        System.out.println("Check value padhu onru ch2 " + p2);

                                        ch3 = ByteMeth.addArray(ch2, checkElmt);
                                        String p3 = UnicodeConverter.revert(ch3);
                                        System.out.println("Check value irupadhu onru ch3 " + p3);
                                        result = UnicodeConverter.revert(ch3);
                                    }
                                }
                            }
                        }
                    }
                }






                /*   if (ByteMeth.startsWith(checkElmt, Constant.mUnr)) {// || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu)) {
                ////System.out.println("Enter into numbers");
                if ((checkElmt.length > 4)) {
                //System.out.println("Enter into munru");
                check_value = ByteMeth.subArray(checkElmt, 4, checkElmt.length); //ppochchu
                String o = UnicodeConverter.revert(check_value);
                System.out.println("Check value munru" + o);
                prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                String oo = UnicodeConverter.revert(prev_char);
                System.out.println("prev value munru "+oo);
                cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);


                if (ByteMeth.contains(cf, Constant.mUnr)) {
                if (itr_del != length) {
                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                if ((ByteMeth.startsWith(checkElmt_new, Constant.i))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                String so = UnicodeConverter.revert(checkElmt_new);
                System.out.println("prev value in if after cf munru" + so);

                ch1 = ByteMeth.subArray(prev_char, 0, (prev_char.length - 1) - Constant.i.length);

                String p1 = UnicodeConverter.revert(ch1);
                System.out.println("Check value ch1 munru" + p1);
                ch2 = ByteMeth.addArray(ch1, Constant.ththu);
                String p2 = UnicodeConverter.revert(ch2);
                System.out.println("Check value padhu onru ch2 " + p2);

                ch3 = ByteMeth.addArray(ch2, checkElmt);
                String p3 = UnicodeConverter.revert(ch3);
                System.out.println("Check value irupadhu onru ch3 munru " + p3);
                result = UnicodeConverter.revert(ch3);
                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.u))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                String so = UnicodeConverter.revert(checkElmt_new);
                //System.out.println("prev value in if after cf" + so);

                ch1 = ByteMeth.subArray(prev_char, 0, (prev_char.length - 1) - Constant.Ru.length);
                String so1 = UnicodeConverter.revert(ch1);
                System.out.println("prev value in if after prev_char ru munru" + so1);

                String p1 = UnicodeConverter.revert(ch1);
                System.out.println("Check value ch1 ru munru " + p1);
                ch2 = ByteMeth.addArray(ch1, Constant.Ru);
                String p2 = UnicodeConverter.revert(ch2);
                System.out.println("Check value padhu onru ch2 ru munru" + p2);

                ch3 = ByteMeth.addArray(ch2, checkElmt);
                String p3 = UnicodeConverter.revert(ch3);
                //System.out.println("Check value irupadhu onru ch3 " + p3);
                result = UnicodeConverter.revert(ch3);
                }
                }
                }
                }
                }*/


                if (ByteMeth.startsWith(checkElmt, Constant.Ang)) {// || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu)) {
                    ////System.out.println("Enter into numbers");
                    if ((checkElmt.length > 3)) {
                        System.out.println("Enter into Ang");
                        check_value = ByteMeth.subArray(checkElmt, 3, checkElmt.length); //ppochchu
                        String o = UnicodeConverter.revert(check_value);
                        System.out.println("Check value " + o);
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        String oo = UnicodeConverter.revert(prev_char);
                        System.out.println("prev value " + oo);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        String ooo = UnicodeConverter.revert(cf);
                        System.out.println("cf " + ooo);

                        if (ByteMeth.contains(cf, Constant.Ang)) {
                            if (itr_len2 != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_len2, prev_char.length);
                                String checkElmt_n = UnicodeConverter.revert(checkElmt_new);
                                System.out.println("checkElmt_new " + checkElmt_n);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.in))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf" + so);

                                    ch1 = ByteMeth.subArray(prev_char, 0, (prev_char.length - 1) - Constant.thu.length);
                                    String so1 = UnicodeConverter.revert(ch1);
                                    System.out.println("prev value in if after prev_char" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 " + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.ththu);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2 " + p2);

                                    ch3 = ByteMeth.addArray(Constant.n_, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);

                                    System.out.println("Check value irupadhu onru ch3 " + p3);
                                    ch4 = ByteMeth.addArray(ch2, ch3);
                                    String p4 = UnicodeConverter.revert(ch4);

                                    System.out.println("Check value irupadhu onru ch4 " + p4);
                                    result = UnicodeConverter.revert(ch4);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf" + so);

                                    ch1 = ByteMeth.subArray(prev_char, 0, (prev_char.length - 1) - Constant.Ru.length);
                                    //String so1 = UnicodeConverter.revert(ch1);
                                    //System.out.println("prev value in if after prev_char" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 " + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.Ru);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2 " + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 " + p3);
                                    result = UnicodeConverter.revert(ch3);
                                }
                            }
                        }
                    }
                }


                if (ByteMeth.startsWith(checkElmt, Constant.naang)) {// || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu)) {
                    ////System.out.println("Enter into numbers");
                    if ((checkElmt.length > 4)) {
                        //System.out.println("Enter into munru");
                        check_value = ByteMeth.subArray(checkElmt, 4, checkElmt.length); //ppochchu
                        String o = UnicodeConverter.revert(check_value);
                        //System.out.println("Check value " + o);
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        //String oo = UnicodeConverter.revert();
                        //////System.out.println("prev value "+oo);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);


                        if (ByteMeth.contains(cf, Constant.naang)) {
                            if (itr_len2 != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_len2, prev_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.th))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf" + so);

                                    ch1 = ByteMeth.subArray(prev_char, 0, (prev_char.length - 1) - Constant.thu.length);
                                    String so1 = UnicodeConverter.revert(ch1);
                                    System.out.println("prev value in if after prev_char" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 " + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.thu);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2 " + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 " + p3);
                                    result = UnicodeConverter.revert(ch3);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf" + so);

                                    ch1 = ByteMeth.subArray(prev_char, 0, (prev_char.length - 1) - Constant.Ru.length);
                                    //String so1 = UnicodeConverter.revert(ch1);
                                    //System.out.println("prev value in if after prev_char" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 " + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.Ru);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2 " + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 " + p3);
                                    result = UnicodeConverter.revert(ch3);
                                }
                            }
                        }
                    }
                }





                if (ByteMeth.startsWith(checkElmt, Constant.aindh)) {// || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu)) {
                    //System.out.println("Enter into five numbers");
                    if ((checkElmt.length > 3)) {
                        check_value = ByteMeth.subArray(checkElmt, 3, checkElmt.length); //ppochchu
                        String o = UnicodeConverter.revert(check_value);
                        //////System.out.println("Check value "+o);
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        String oo = UnicodeConverter.revert(prev_char);
                        //////System.out.println("prev value "+oo);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        if (ByteMeth.contains(cf, Constant.aindh)) {
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.th)) || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu
                                    prev_char[prev_char.length - 1] = 5;
                                    end_char = ByteMeth.addArray(prev_char, checkElmt);
                                    String p = UnicodeConverter.revert(end_char);
                                    ////System.out.println("Check value  " + p);
                                    //end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.n))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu
                                    prev_char[prev_char.length - 1] = 5;
                                    prev_char[prev_char.length - 2] = 20;
                                    // System.out.println("else if "+UnicodeConverter.revert(topElmt_new));
                                    // topElmt_new = ByteMeth.addArray(prev_char, Constant.thu);

                                    end_char = ByteMeth.addArray(prev_char, checkElmt);
                                    System.out.println("else if  " + UnicodeConverter.revert(end_char));
                                    result = UnicodeConverter.revert(end_char);
                                }
                            }
                        }
                    } else {
                        //////System.out.println("Enter into else if Aa at last");
                        if (ByteMeth.contains(checkElmt, Constant.aindh)) {
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.th)) || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) {
                                    remain_char[remain_char.length - 1] = 5;
                                    topElmt_new = ByteMeth.addArray(remain_char, checkElmt);
                                    result = UnicodeConverter.revert(topElmt_new);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.n))) {
                                    remain_char[remain_char.length - 1] = 5;
                                    remain_char[remain_char.length - 2] = 20;
                                    topElmt_new = ByteMeth.addArray(remain_char, checkElmt);
                                    result = UnicodeConverter.revert(topElmt_new);
                                }
                            }
                        }
                    }
                }


                if (ByteMeth.startsWith(checkElmt, Constant.aar)) {// || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu)) {
                    ////System.out.println("Enter into numbers");
                    if ((checkElmt.length > 2)) {
                        check_value = ByteMeth.subArray(checkElmt, 2, checkElmt.length); //ppochchu
                        String o = UnicodeConverter.revert(check_value);
                        //////System.out.println("Check value "+o);
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        String oo = UnicodeConverter.revert(prev_char);
                        //////System.out.println("prev value "+oo);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        if (ByteMeth.contains(cf, Constant.aar)) {
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.th)) || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu
                                    prev_char[prev_char.length - 1] = 5;
                                    end_char = ByteMeth.addArray(prev_char, checkElmt);
                                    String p = UnicodeConverter.revert(end_char);
                                    ////System.out.println("Check value  " + p);
                                    //end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.n))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu
                                    prev_char[prev_char.length - 1] = 5;
                                    prev_char[prev_char.length - 2] = 20;
                                    // System.out.println("else if "+UnicodeConverter.revert(topElmt_new));
                                    // topElmt_new = ByteMeth.addArray(prev_char, Constant.thu);

                                    end_char = ByteMeth.addArray(prev_char, checkElmt);
                                    System.out.println("else if  " + UnicodeConverter.revert(end_char));
                                    result = UnicodeConverter.revert(end_char);
                                }
                            }
                        }
                    } else {
                        //////System.out.println("Enter into else if Aa at last");
                        if (ByteMeth.contains(checkElmt, Constant.aar)) {
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.th)) || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) {
                                    remain_char[remain_char.length - 1] = 5;
                                    topElmt_new = ByteMeth.addArray(remain_char, checkElmt);
                                    result = UnicodeConverter.revert(topElmt_new);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.n))) {
                                    remain_char[remain_char.length - 1] = 5;
                                    remain_char[remain_char.length - 2] = 20;
                                    topElmt_new = ByteMeth.addArray(remain_char, checkElmt);
                                    result = UnicodeConverter.revert(topElmt_new);
                                }
                            }
                        }
                    }
                }

                if (ByteMeth.startsWith(checkElmt, Constant.Ez)) {// || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu)) {
                    ////System.out.println("Enter into numbers");
                    if ((checkElmt.length > 2)) {
                        check_value = ByteMeth.subArray(checkElmt, 2, checkElmt.length); //ppochchu
                        String o = UnicodeConverter.revert(check_value);
                        //////System.out.println("Check value "+o);
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        String oo = UnicodeConverter.revert(prev_char);
                        //////System.out.println("prev value "+oo);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        if (ByteMeth.contains(cf, Constant.Ez)) {
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.th)) || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu
                                    prev_char[prev_char.length - 1] = 5;
                                    end_char = ByteMeth.addArray(prev_char, checkElmt);
                                    String p = UnicodeConverter.revert(end_char);
                                    ////System.out.println("Check value  " + p);
                                    //end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.n))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu
                                    prev_char[prev_char.length - 1] = 5;
                                    prev_char[prev_char.length - 2] = 20;
                                    // System.out.println("else if "+UnicodeConverter.revert(topElmt_new));
                                    // topElmt_new = ByteMeth.addArray(prev_char, Constant.thu);

                                    end_char = ByteMeth.addArray(prev_char, checkElmt);
                                    System.out.println("else if  " + UnicodeConverter.revert(end_char));
                                    result = UnicodeConverter.revert(end_char);
                                }
                            }
                        }
                    } else {
                        //////System.out.println("Enter into else if Aa at last");
                        if (ByteMeth.contains(checkElmt, Constant.Ez)) {
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.th)) || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) {
                                    remain_char[remain_char.length - 1] = 5;
                                    topElmt_new = ByteMeth.addArray(remain_char, checkElmt);
                                    result = UnicodeConverter.revert(topElmt_new);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.n))) {
                                    remain_char[remain_char.length - 1] = 5;
                                    remain_char[remain_char.length - 2] = 20;
                                    topElmt_new = ByteMeth.addArray(remain_char, checkElmt);
                                    result = UnicodeConverter.revert(topElmt_new);
                                }
                            }
                        }
                    }
                }

                if (ByteMeth.startsWith(checkElmt, Constant.ett)) {// || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu)) {
                    ////System.out.println("Enter into numbers");
                    if ((checkElmt.length > 3)) {
                        check_value = ByteMeth.subArray(checkElmt, 3, checkElmt.length); //ppochchu
                        String o = UnicodeConverter.revert(check_value);
                        //////System.out.println("Check value "+o);
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        String oo = UnicodeConverter.revert(prev_char);
                        //////System.out.println("prev value "+oo);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        if (ByteMeth.contains(cf, Constant.ett)) {
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.th)) || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu
                                    prev_char[prev_char.length - 1] = 5;
                                    end_char = ByteMeth.addArray(prev_char, checkElmt);
                                    String p = UnicodeConverter.revert(end_char);
                                    ////System.out.println("Check value  " + p);
                                    //end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.n))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu
                                    prev_char[prev_char.length - 1] = 5;
                                    prev_char[prev_char.length - 2] = 20;
                                    // System.out.println("else if "+UnicodeConverter.revert(topElmt_new));
                                    // topElmt_new = ByteMeth.addArray(prev_char, Constant.thu);

                                    end_char = ByteMeth.addArray(prev_char, checkElmt);
                                    System.out.println("else if ettu " + UnicodeConverter.revert(end_char));
                                    result = UnicodeConverter.revert(end_char);
                                }
                            }
                        }
                    } else {
                        //////System.out.println("Enter into else if Aa at last");
                        if (ByteMeth.contains(checkElmt, Constant.ett)) {
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.th)) || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) {
                                    remain_char[remain_char.length - 1] = 5;
                                    topElmt_new = ByteMeth.addArray(remain_char, checkElmt);

                                    result = UnicodeConverter.revert(topElmt_new);
                                    System.out.println("else if ettu for aruba" + result);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.n))) {
                                    remain_char[remain_char.length - 1] = 5;
                                    remain_char[remain_char.length - 2] = 20;
                                    topElmt_new = ByteMeth.addArray(remain_char, checkElmt);
                                    result = UnicodeConverter.revert(topElmt_new);
                                    System.out.println("else if ettu for padhin" + result);
                                }
                            }
                        }
                    }
                }


                if (ByteMeth.startsWith(checkElmt, Constant.onbadh)) {// || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu)) {
                    ////System.out.println("Enter into numbers");
                    if ((checkElmt.length > 5)) {
                        check_value = ByteMeth.subArray(checkElmt, 5, checkElmt.length); //ppochchu
                        String o = UnicodeConverter.revert(check_value);
                        System.out.println("Check value in onbadhu" + o);
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        String oo = UnicodeConverter.revert(prev_char);
                        System.out.println("prev value in onbadhu " + oo);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        String ooo = UnicodeConverter.revert(cf);
                        System.out.println("cf in onbadhu " + ooo);
                        System.out.println("prev_char in length before if " + prev_char.length);
                        if (ByteMeth.contains(cf, Constant.onbadh)) {
                            System.out.println("prev_char in length after if " + prev_char.length);
                            if (prev_char.length > 4) {
                                System.out.println("prev_char in length inside " + prev_char.length);
                                if (itr_del != length) {
                                    checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                                    if ((ByteMeth.startsWith(checkElmt_new, Constant.th)) || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu
                                        prev_char[prev_char.length - 1] = 5;
                                        end_char = ByteMeth.addArray(prev_char, checkElmt);
                                        String p = UnicodeConverter.revert(end_char);
                                        System.out.println("Check value in onbadhu " + p);
                                        //end_char = ByteMeth.addArray(topElmt_new, check_value);
                                        result = UnicodeConverter.revert(end_char);
                                        System.out.println("else if obnbadh for  " + result);
                                    }
                                }
                            } else {
                                System.out.println("prev_char in length inside else if " + prev_char.length);
                                if (itr_del != length) {
                                    checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                                    if ((ByteMeth.startsWith(checkElmt_new, Constant.th)) || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu
                                        //  prev_char[prev_char.length - 1] = 5;
                                        ch1 = ByteMeth.addArray(prev_char, Constant.u);
                                        String p = UnicodeConverter.revert(ch1);
                                        System.out.println("Check value in onbadhu in patthumbadhu " + p);
                                        end_char = ByteMeth.addArray(ch1, checkElmt);
                                        result = UnicodeConverter.revert(end_char);
                                        System.out.println("else if obnbadh for patthuonbadhu " + result);
                                    }
                                }
                            }
                        }
                    } else {
                        //////System.out.println("Enter into else if Aa at last");
                        if (ByteMeth.contains(checkElmt, Constant.onbadh)) {
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.th)) || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) {
                                    remain_char[remain_char.length - 1] = 5;
                                    topElmt_new = ByteMeth.addArray(remain_char, checkElmt);
                                    result = UnicodeConverter.revert(topElmt_new);
                                    System.out.println("else if onbadhu for patthu" + result);
                                }
                            }
                        }
                    }
                }


                if (ByteMeth.startsWith(checkElmt, Constant.patthu)) {// || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu)) {
                    System.out.println("Enter into numbers patthu");
                    if ((checkElmt.length > 5)) {
                        //System.out.println("Enter into munru");
                        check_value = ByteMeth.subArray(checkElmt, 5, checkElmt.length); //ppochchu
                        String o = UnicodeConverter.revert(check_value);
                        System.out.println("Check value patthu " + o);
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        String oo = UnicodeConverter.revert(prev_char);
                        System.out.println("prev value patthu" + oo);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        System.out.println("prev value patthu cf" + UnicodeConverter.revert(cf));

                        if (!(ByteMeth.contains(prev_char, Constant.aRu)) || (!(ByteMeth.contains(prev_char, Constant.erNooru)))) {
                            if (ByteMeth.contains(cf, Constant.patthu)) {
                                if (itr_len2 != length) {
                                    checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_len2, prev_char.length);
                                    if ((ByteMeth.startsWith(checkElmt_new, Constant.th))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                        String so = UnicodeConverter.revert(checkElmt_new);
                                        System.out.println("prev value in if after cf patthu" + so);

                                        ch1 = ByteMeth.subArray(prev_char, 0, (prev_char.length - 1) - Constant.thu.length);
                                        String so1 = UnicodeConverter.revert(ch1);
                                        System.out.println("prev value in if after prev_char patthu" + so1);

                                        String p1 = UnicodeConverter.revert(ch1);
                                        System.out.println("Check value ch1 " + p1);
                                        ch2 = ByteMeth.addArray(ch1, Constant.thu);
                                        String p2 = UnicodeConverter.revert(ch2);
                                        System.out.println("Check value padhu onru ch2 " + p2);

                                        ch3 = ByteMeth.addArray(ch2, checkElmt);
                                        String p3 = UnicodeConverter.revert(ch3);
                                        System.out.println("Check value irupadhu onru ch3 " + p3);
                                        result = UnicodeConverter.revert(ch3);
                                    } else if ((ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                        String so = UnicodeConverter.revert(checkElmt_new);
                                        System.out.println("prev value in if after cf patthu else if" + so);

                                        ch1 = ByteMeth.subArray(prev_char, 0, (prev_char.length - 1) - Constant.Ru.length);
                                        String so1 = UnicodeConverter.revert(ch1);
                                        System.out.println("prev value in if after prev_char patthu" + so1);

                                        String p1 = UnicodeConverter.revert(ch1);
                                        System.out.println("Check value ch1 patthu" + p1);
                                        ch2 = ByteMeth.addArray(ch1, Constant.Ru);
                                        String p2 = UnicodeConverter.revert(ch2);
                                        System.out.println("Check value padhu onru ch2 patthu " + p2);

                                        ch3 = ByteMeth.addArray(ch2, checkElmt);
                                        String p3 = UnicodeConverter.revert(ch3);
                                        System.out.println("Check value irupadhu onru ch3 patthu" + p3);
                                        result = UnicodeConverter.revert(ch3);
                                    }
                                }
                            }
                        }
                    } else {
                        if (ByteMeth.contains(checkElmt, Constant.patthu)) {
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length));
                            if (itr_len2 != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_len2, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.th))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf" + so);

                                    ch1 = ByteMeth.subArray(remain_char, 0, (remain_char.length - 1) - Constant.thu.length);
                                    String so1 = UnicodeConverter.revert(ch1);
                                    System.out.println("prev value in if after prev_char" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 " + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.thu);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2 " + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 " + p3);
                                    result = UnicodeConverter.revert(ch3);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.R))) {
                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf" + so);

                                    ch1 = ByteMeth.subArray(remain_char, 0, (remain_char.length - 1) - Constant.Ru.length);
                                    String so1 = UnicodeConverter.revert(ch1);
                                    System.out.println("prev value in if after prev_char" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 else" + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.Ru);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2  else" + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 else" + p3);
                                    result = UnicodeConverter.revert(ch3);
                                }
                            }
                        }
                    }
                }


                if (ByteMeth.startsWith(checkElmt, Constant.Ayira)) {// || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu)) {
                    ////System.out.println("Enter into numbers");
                    if ((checkElmt.length > 5)) {
                        check_value = ByteMeth.subArray(checkElmt, 5, checkElmt.length); //ppochchu
                        String o = UnicodeConverter.revert(check_value);
                        //////System.out.println("Check value "+o);
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        String oo = UnicodeConverter.revert(prev_char);
                        //////System.out.println("prev value "+oo);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        if (ByteMeth.contains(cf, Constant.Ayira)) {
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.t)) || (ByteMeth.startsWith(checkElmt_new, Constant.k)) || (ByteMeth.startsWith(checkElmt_new, Constant.R)) || (ByteMeth.startsWith(checkElmt_new, Constant.th)) || (ByteMeth.startsWith(checkElmt_new, Constant.zh))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.th))) { //paaththuppochchu
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.u);
                                    System.out.println("Aimba " + UnicodeConverter.revert(topElmt_new));
                                    end_char = ByteMeth.addArray(topElmt_new, checkElmt);
                                    System.out.println("Aimba check " + UnicodeConverter.revert(checkElmt));
                                    //result = UnicodeConverter.revert(end_char);
                                    // end_char = ByteMeth.addArray(prev_char, checkElmt);
                                    String p = UnicodeConverter.revert(end_char);
                                    System.out.println("Check value in ayiram  " + p);
                                    //end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.v))) {
                                    prev_char[prev_char.length - 1] = 31;
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.Ru);
                                    end_char = ByteMeth.addArray(topElmt_new, checkElmt);
                                    result = UnicodeConverter.revert(end_char);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.l))) {
                                    prev_char[prev_char.length - 1] = 31;
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.ku);
                                    end_char = ByteMeth.addArray(topElmt_new, checkElmt);
                                    result = UnicodeConverter.revert(end_char);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.r))) {
                                    prev_char[prev_char.length - 1] = 31;
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.Ru);
                                    end_char = ByteMeth.addArray(topElmt_new, checkElmt);
                                    result = UnicodeConverter.revert(end_char);
                                }
                            }
                        }
                    } else {
                        //////System.out.println("Enter into else if Aa at last");
                        if (ByteMeth.contains(checkElmt, Constant.Ayira)) {
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.t)) || (ByteMeth.startsWith(checkElmt_new, Constant.k)) || (ByteMeth.startsWith(checkElmt_new, Constant.R)) || (ByteMeth.startsWith(checkElmt_new, Constant.th)) || (ByteMeth.startsWith(checkElmt_new, Constant.zh))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.th))) { //paaththuppochchu
                                    //remain_char[remain_char.length - 1] = 5;
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.u);
                                    checkElmt_new_next = ByteMeth.addArray(topElmt_new, checkElmt);
                                    String ps = UnicodeConverter.revert(checkElmt_new_next);
                                    //System.out.println("Check value in ayiram else " + ps);
                                    result = UnicodeConverter.revert(checkElmt_new_next);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.v))) {
                                    remain_char[remain_char.length - 1] = 31;
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.Ru);
                                    end_char = ByteMeth.addArray(topElmt_new, checkElmt);
                                    result = UnicodeConverter.revert(end_char);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.l))) {
                                    remain_char[remain_char.length - 1] = 31;
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.ku);
                                    end_char = ByteMeth.addArray(topElmt_new, checkElmt);
                                    result = UnicodeConverter.revert(end_char);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.r)) || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {
                                    remain_char[remain_char.length - 1] = 31;
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.Ru);
                                    end_char = ByteMeth.addArray(topElmt_new, checkElmt);
                                    result = UnicodeConverter.revert(end_char);
                                }
                            }
                        }
                    }
                }




                if (ByteMeth.startsWith(checkElmt, Constant.latcha)) {// || (ByteMeth.startsWith(checkElmt, Constant.ilatcha)) ) {// || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu)) {
                    ////System.out.println("Enter into numbers");
                    if ((checkElmt.length > 5)) {
                        check_value = ByteMeth.subArray(checkElmt, 5, checkElmt.length); //ppochchu
                        String o = UnicodeConverter.revert(check_value);
                        //////System.out.println("Check value "+o);
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        String oo = UnicodeConverter.revert(prev_char);
                        //////System.out.println("prev value "+oo);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        if (ByteMeth.contains(cf, Constant.latcha)) {
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.t)) || (ByteMeth.startsWith(checkElmt_new, Constant.k)) || (ByteMeth.startsWith(checkElmt_new, Constant.R)) || (ByteMeth.startsWith(checkElmt_new, Constant.th)) || (ByteMeth.startsWith(checkElmt_new, Constant.zh))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.th))) { //paaththuppochchu
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.u);
                                    end_char = ByteMeth.addArray(topElmt_new, checkElmt);
                                    //result = UnicodeConverter.revert(end_char);
                                    // end_char = ByteMeth.addArray(prev_char, checkElmt);
                                    String p = UnicodeConverter.revert(end_char);
                                    //System.out.println("Check value in ayiram  " + p);
                                    //end_char = ByteMeth.addArray(topElmt_new, check_value);
                                    result = UnicodeConverter.revert(end_char);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.v))) {
                                    prev_char[prev_char.length - 1] = 31;
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.Ru);
                                    end_char = ByteMeth.addArray(topElmt_new, checkElmt);
                                    result = UnicodeConverter.revert(end_char);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.l))) {
                                    prev_char[prev_char.length - 1] = 31;
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.ku);
                                    end_char = ByteMeth.addArray(topElmt_new, checkElmt);
                                    result = UnicodeConverter.revert(end_char);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.r))) {
                                    prev_char[prev_char.length - 1] = 31;
                                    topElmt_new = ByteMeth.addArray(prev_char, Constant.Ru);
                                    end_char = ByteMeth.addArray(topElmt_new, checkElmt);
                                    result = UnicodeConverter.revert(end_char);
                                }
                            }
                        }
                    } else {
                        //////System.out.println("Enter into else if Aa at last");
                        if (ByteMeth.contains(checkElmt, Constant.latcha)) {
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                            if (itr_del != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.t)) || (ByteMeth.startsWith(checkElmt_new, Constant.k)) || (ByteMeth.startsWith(checkElmt_new, Constant.R)) || (ByteMeth.startsWith(checkElmt_new, Constant.th)) || (ByteMeth.startsWith(checkElmt_new, Constant.zh))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.th))) { //paaththuppochchu
                                    //remain_char[remain_char.length - 1] = 5;
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.u);
                                    checkElmt_new_next = ByteMeth.addArray(topElmt_new, checkElmt);
                                    String ps = UnicodeConverter.revert(checkElmt_new_next);
                                    //System.out.println("Check value in ayiram else " + ps);
                                    result = UnicodeConverter.revert(checkElmt_new_next);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.v))) {
                                    remain_char[remain_char.length - 1] = 31;
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.Ru);
                                    end_char = ByteMeth.addArray(topElmt_new, checkElmt);
                                    result = UnicodeConverter.revert(end_char);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.l))) {
                                    remain_char[remain_char.length - 1] = 31;
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.ku);
                                    end_char = ByteMeth.addArray(topElmt_new, checkElmt);
                                    result = UnicodeConverter.revert(end_char);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.r)) || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {
                                    remain_char[remain_char.length - 1] = 31;
                                    topElmt_new = ByteMeth.addArray(remain_char, Constant.Ru);
                                    end_char = ByteMeth.addArray(topElmt_new, checkElmt);
                                    result = UnicodeConverter.revert(end_char);
                                }
                            }
                        }
                    }
                }


                /*if (ByteMeth.startsWith(checkElmt, Constant.aimbadhth)) {// || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu)) {
                //System.out.println("Enter into aimbadhuth numbers");
                if ((checkElmt.length > 5)) {
                check_value = ByteMeth.subArray(checkElmt, 5, checkElmt.length); //ppochchu
                String o = UnicodeConverter.revert(check_value);
                //////System.out.println("Check value "+o);
                prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                String oo = UnicodeConverter.revert(prev_char);
                //////System.out.println("prev value "+oo);
                cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                if (ByteMeth.contains(cf, Constant.aimbadhth)) {
                if (itr_del != length) {
                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                if ((ByteMeth.startsWith(checkElmt_new, Constant.th)) || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu
                prev_char[prev_char.length - 1] = 5;
                end_char = ByteMeth.addArray(prev_char, checkElmt);
                String p = UnicodeConverter.revert(end_char);
                ////System.out.println("Check value  " + p);
                //end_char = ByteMeth.addArray(topElmt_new, check_value);
                result = UnicodeConverter.revert(end_char);
                }
                }
                }
                } else {
                //////System.out.println("Enter into else if Aa at last");
                if (ByteMeth.contains(checkElmt, Constant.aimbadhth)) {
                byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                if (itr_del != length) {
                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                if ((ByteMeth.startsWith(checkElmt_new, Constant.th)) || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) {
                remain_char[remain_char.length - 1] = 5;
                topElmt_new = ByteMeth.addArray(remain_char, checkElmt);
                result = UnicodeConverter.revert(topElmt_new);
                }
                }
                }
                }
                }*/


                /*     if (ByteMeth.startsWith(checkElmt, Constant.erubadh)) {// || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu)) {
                System.out.println("Enter into erubadh numbers "+UnicodeConverter.revert(checkElmt));
                if ((checkElmt.length > 6)) {
                check_value = ByteMeth.subArray(checkElmt, 6, checkElmt.length); //ppochchu
                String o = UnicodeConverter.revert(check_value);
                System.out.println("Check value "+o);
                prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                String oo = UnicodeConverter.revert(prev_char);
                System.out.println("prev value "+oo);
                cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                String ooo = UnicodeConverter.revert(cf);
                System.out.println("cf "+ooo);
                if (ByteMeth.contains(cf, Constant.erubadh)) {
                if (itr_del != length) {
                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                System.out.println("Chc "+UnicodeConverter.revert(checkElmt_new));
                if ((ByteMeth.startsWith(checkElmt_new, Constant.th)) || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu
                prev_char[prev_char.length - 1] = 5;
                end_char = ByteMeth.addArray(prev_char, checkElmt);
                String p = UnicodeConverter.revert(end_char);
                ////System.out.println("Check value  " + p);
                //end_char = ByteMeth.addArray(topElmt_new, check_value);
                result = UnicodeConverter.revert(end_char);
                }else if ((ByteMeth.startsWith(checkElmt_new, Constant.u))) {
                ch1 = ByteMeth.subArray(prev_char, 0, (prev_char.length - 1) - Constant.Ru.length);
                String p1 = UnicodeConverter.revert(ch1);
                ch2=ByteMeth.addArray(ch1, Constant.Ru);
                String p2=UnicodeConverter.revert(ch2);
                ch3=ByteMeth.addArray(ch2,checkElmt);
                String p3=UnicodeConverter.revert(ch3);
                System.out.println("Check value ch1 in erubadhu " + p1);
                System.out.println("Check value ch2 in erubadhu " + p2);
                System.out.println("Check value ch3 in erubadhu " + p3);
                result = UnicodeConverter.revert(ch3);
                break;
                }

                }
                }
                } else {
                //////System.out.println("Enter into else if Aa at last");
                if (ByteMeth.contains(checkElmt, Constant.erubadh)) {
                byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                if (itr_del != length) {
                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                if ((ByteMeth.startsWith(checkElmt_new, Constant.th)) || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) {
                remain_char[remain_char.length - 1] = 5;
                topElmt_new = ByteMeth.addArray(remain_char, checkElmt);
                result = UnicodeConverter.revert(topElmt_new);
                }
                }
                }
                }
                }*/


                if (ByteMeth.startsWith(checkElmt, Constant.erubadhu)) {// || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu)) {
                    System.out.println("Enter into numbers erubadhu");
                    if ((checkElmt.length > 7)) {
                        System.out.println("Enter into 20");
                        check_value = ByteMeth.subArray(checkElmt, 7, checkElmt.length); //ppochchu
                        String o = UnicodeConverter.revert(check_value);
                        System.out.println("Check value " + o);
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        String oo = UnicodeConverter.revert(prev_char);
                        System.out.println("prev value " + oo);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        System.out.println("prev value cf" + UnicodeConverter.revert(cf));
                        if (!(ByteMeth.contains(prev_char, Constant.erNooru))) {
                            if (ByteMeth.contains(cf, Constant.erubadhu)) {

                                if (itr_len2 != length) {
                                    checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_len2, prev_char.length);
                                    if ((ByteMeth.startsWith(checkElmt_new, Constant.th))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                        String so = UnicodeConverter.revert(checkElmt_new);
                                        System.out.println("prev value in if after cf" + so);

                                        ch1 = ByteMeth.subArray(prev_char, 0, (prev_char.length - 1) - Constant.thu.length);
                                        String so1 = UnicodeConverter.revert(ch1);
                                        System.out.println("prev value in if after prev_char" + so1);

                                        String p1 = UnicodeConverter.revert(ch1);
                                        System.out.println("Check value ch1 " + p1);
                                        ch2 = ByteMeth.addArray(ch1, Constant.thu);
                                        String p2 = UnicodeConverter.revert(ch2);
                                        System.out.println("Check value padhu onru ch2 " + p2);

                                        ch3 = ByteMeth.addArray(ch2, checkElmt);
                                        String p3 = UnicodeConverter.revert(ch3);
                                        System.out.println("Check value irupadhu onru ch3 " + p3);
                                        result = UnicodeConverter.revert(ch3);
                                    } else if ((ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                        String so = UnicodeConverter.revert(checkElmt_new);
                                        System.out.println("prev value in if after cf" + so);

                                        ch1 = ByteMeth.subArray(prev_char, 0, (prev_char.length - 1) - Constant.Ru.length);
                                        String so1 = UnicodeConverter.revert(ch1);
                                        System.out.println("prev value in if after prev_char" + so1);

                                        String p1 = UnicodeConverter.revert(ch1);
                                        System.out.println("Check value ch1 " + p1);
                                        ch2 = ByteMeth.addArray(ch1, Constant.Ru);
                                        String p2 = UnicodeConverter.revert(ch2);
                                        System.out.println("Check value padhu onru ch2 " + p2);

                                        ch3 = ByteMeth.addArray(ch2, checkElmt);
                                        String p3 = UnicodeConverter.revert(ch3);
                                        System.out.println("Check value irupadhu onru ch3 " + p3);
                                        result = UnicodeConverter.revert(ch3);
                                    }
                                }
                            }
                        }

                    } else {
                        if (ByteMeth.contains(checkElmt, Constant.erubadhu)) {
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length));
                            System.out.println("prev value in if after remain_char" + UnicodeConverter.revert(remain_char));
                            if (itr_len2 != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_len2, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.th))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf " + so);

                                    ch1 = ByteMeth.subArray(remain_char, 0, (remain_char.length - 1) - Constant.thu.length);
                                    String so1 = UnicodeConverter.revert(ch1);
                                    System.out.println("prev value in if after prev_char" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 " + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.thu);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2 " + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 " + p3);
                                    result = UnicodeConverter.revert(ch3);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.R))) {
                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf 2" + so);

                                    ch1 = ByteMeth.subArray(remain_char, 0, (remain_char.length - 1) - Constant.Ru.length);
                                    String so1 = UnicodeConverter.revert(ch1);
                                    System.out.println("prev value in if after prev_char 2" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 else" + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.Ru);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2  else 2" + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 else 2" + p3);
                                    result = UnicodeConverter.revert(ch3);
                                }
                                //    }
                            }
                        }
                    }
                }



                /*  if (ByteMeth.startsWith(checkElmt, Constant.muppadh)) {// || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu)) {
                //System.out.println("Enter into aimbadhuth numbers");
                if ((checkElmt.length > 6)) {
                check_value = ByteMeth.subArray(checkElmt, 6, checkElmt.length); //ppochchu
                String o = UnicodeConverter.revert(check_value);
                //////System.out.println("Check value "+o);
                prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                String oo = UnicodeConverter.revert(prev_char);
                //////System.out.println("prev value "+oo);
                cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                if (ByteMeth.contains(cf, Constant.muppadh)) {
                if (itr_del != length) {
                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                if ((ByteMeth.startsWith(checkElmt_new, Constant.th)) || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu
                prev_char[prev_char.length - 1] = 5;
                end_char = ByteMeth.addArray(prev_char, checkElmt);
                String p = UnicodeConverter.revert(end_char);
                ////System.out.println("Check value  " + p);
                //end_char = ByteMeth.addArray(topElmt_new, check_value);
                result = UnicodeConverter.revert(end_char);
                }

                }
                }
                } else {
                //////System.out.println("Enter into else if Aa at last");
                if (ByteMeth.contains(checkElmt, Constant.muppadh)) {
                byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                if (itr_del != length) {
                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                if ((ByteMeth.startsWith(checkElmt_new, Constant.th)) || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) {
                remain_char[remain_char.length - 1] = 5;
                topElmt_new = ByteMeth.addArray(remain_char, checkElmt);
                result = UnicodeConverter.revert(topElmt_new);
                }
                }
                }
                }
                }


                if (ByteMeth.startsWith(checkElmt, Constant.nARbadh)) {// || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu)) {
                //System.out.println("Enter into aimbadhuth numbers");
                if ((checkElmt.length > 6)) {
                check_value = ByteMeth.subArray(checkElmt, 6, checkElmt.length); //ppochchu
                String o = UnicodeConverter.revert(check_value);
                //////System.out.println("Check value "+o);
                prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                String oo = UnicodeConverter.revert(prev_char);
                //////System.out.println("prev value "+oo);
                cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                if (ByteMeth.contains(cf, Constant.nARbadh)) {
                if (itr_del != length) {
                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_del, prev_char.length);
                if ((ByteMeth.startsWith(checkElmt_new, Constant.th)) || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu
                prev_char[prev_char.length - 1] = 5;
                end_char = ByteMeth.addArray(prev_char, checkElmt);
                String p = UnicodeConverter.revert(end_char);
                ////System.out.println("Check value  " + p);
                //end_char = ByteMeth.addArray(topElmt_new, check_value);
                result = UnicodeConverter.revert(end_char);
                }

                }
                }
                } else {
                //////System.out.println("Enter into else if Aa at last");
                if (ByteMeth.contains(checkElmt, Constant.nARbadh)) {
                byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length)); //p
                if (itr_del != length) {
                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_del, remain_char.length);
                if ((ByteMeth.startsWith(checkElmt_new, Constant.th)) || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) {
                remain_char[remain_char.length - 1] = 5;
                topElmt_new = ByteMeth.addArray(remain_char, checkElmt);
                result = UnicodeConverter.revert(topElmt_new);
                }
                }
                }
                }
                }*/


                if (ByteMeth.startsWith(checkElmt, Constant.muppadhu)) {// || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu)) {
                    System.out.println("Enter into numbers muppadhu");
                    if ((checkElmt.length > 7)) {
                        System.out.println("Enter into 20");
                        check_value = ByteMeth.subArray(checkElmt, 7, checkElmt.length); //ppochchu
                        String o = UnicodeConverter.revert(check_value);
                        System.out.println("Check value " + o);
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        String oo = UnicodeConverter.revert(prev_char);
                        System.out.println("prev value " + oo);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        System.out.println("prev value cf" + UnicodeConverter.revert(cf));
                        if (!(ByteMeth.contains(prev_char, Constant.erNooru))) {
                            if (ByteMeth.contains(cf, Constant.muppadhu)) {

                                if (itr_len2 != length) {
                                    checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_len2, prev_char.length);
                                    if ((ByteMeth.startsWith(checkElmt_new, Constant.th))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                        String so = UnicodeConverter.revert(checkElmt_new);
                                        System.out.println("prev value in if after cf" + so);

                                        ch1 = ByteMeth.subArray(prev_char, 0, (prev_char.length - 1) - Constant.thu.length);
                                        String so1 = UnicodeConverter.revert(ch1);
                                        System.out.println("prev value in if muppadhu after prev_char" + so1);

                                        String p1 = UnicodeConverter.revert(ch1);
                                        System.out.println("Check value ch1 muppadhu " + p1);
                                        ch2 = ByteMeth.addArray(ch1, Constant.thu);
                                        String p2 = UnicodeConverter.revert(ch2);
                                        System.out.println("Check value muppadhu onru ch2 " + p2);

                                        ch3 = ByteMeth.addArray(ch2, checkElmt);
                                        String p3 = UnicodeConverter.revert(ch3);
                                        System.out.println("Check value muppadhu onru ch3 " + p3);
                                        result = UnicodeConverter.revert(ch3);
                                    } else if ((ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                        String so = UnicodeConverter.revert(checkElmt_new);
                                        System.out.println("prev value in if muppadhu after cf" + so);

                                        ch1 = ByteMeth.subArray(prev_char, 0, (prev_char.length - 1) - Constant.Ru.length);
                                        String so1 = UnicodeConverter.revert(ch1);
                                        System.out.println("prev value in if after muppadhu prev_char" + so1);

                                        String p1 = UnicodeConverter.revert(ch1);
                                        System.out.println("Check value ch1 muppadhu" + p1);
                                        ch2 = ByteMeth.addArray(ch1, Constant.Ru);
                                        String p2 = UnicodeConverter.revert(ch2);
                                        System.out.println("Check value muppadhu onru ch2 " + p2);

                                        ch3 = ByteMeth.addArray(ch2, checkElmt);
                                        String p3 = UnicodeConverter.revert(ch3);
                                        System.out.println("Check value muppadhu onru ch3 " + p3);
                                        result = UnicodeConverter.revert(ch3);
                                    }
                                }
                            }
                        }

                    } else {
                        if (ByteMeth.contains(checkElmt, Constant.muppadhu)) {
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length));
                            System.out.println("prev value in if after remain_char muppadhu" + UnicodeConverter.revert(remain_char));
                            if (itr_len2 != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_len2, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.th))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf muppadhu" + so);

                                    ch1 = ByteMeth.subArray(remain_char, 0, (remain_char.length - 1) - Constant.thu.length);
                                    String so1 = UnicodeConverter.revert(ch1);
                                    System.out.println("prev value in if after prev_char muppadhu" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 muppadhu " + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.thu);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2 muppadhu " + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 muppadhu " + p3);
                                    result = UnicodeConverter.revert(ch3);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.R))) {
                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf 2 muppadhu" + so);

                                    ch1 = ByteMeth.subArray(remain_char, 0, (remain_char.length - 1) - Constant.Ru.length);
                                    String so1 = UnicodeConverter.revert(ch1);
                                    System.out.println("prev value in if after prev_char 2 muppadhu" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 else muppadhu" + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.Ru);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2  else 2 muppadhu" + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 else 2 muppadhu" + p3);
                                    result = UnicodeConverter.revert(ch3);
                                }
                                //    }
                            }
                        }
                    }
                }

                if (ByteMeth.startsWith(checkElmt, Constant.nARbadhu)) {// || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu)) {
                    System.out.println("Enter into numbers erubadhu");
                    if ((checkElmt.length > 7)) {
                        System.out.println("Enter into 20");
                        check_value = ByteMeth.subArray(checkElmt, 7, checkElmt.length); //ppochchu
                        String o = UnicodeConverter.revert(check_value);
                        System.out.println("Check value " + o);
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        String oo = UnicodeConverter.revert(prev_char);
                        System.out.println("prev value " + oo);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        System.out.println("prev value cf" + UnicodeConverter.revert(cf));

                        if (ByteMeth.contains(cf, Constant.nARbadhu)) {
                            if (itr_len2 != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_len2, prev_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.th))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf" + so);

                                    ch1 = ByteMeth.subArray(prev_char, 0, (prev_char.length - 1) - Constant.thu.length);
                                    String so1 = UnicodeConverter.revert(ch1);
                                    System.out.println("prev value in if after prev_char" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 " + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.thu);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2 " + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 " + p3);
                                    result = UnicodeConverter.revert(ch3);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf" + so);

                                    ch1 = ByteMeth.subArray(prev_char, 0, (prev_char.length - 1) - Constant.Ru.length);
                                    String so1 = UnicodeConverter.revert(ch1);
                                    System.out.println("prev value in if after prev_char" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 " + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.Ru);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2 " + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 " + p3);
                                    result = UnicodeConverter.revert(ch3);
                                }
                            }
                        }
                    } else {
                        if (ByteMeth.contains(checkElmt, Constant.nARbadhu)) {
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length));
                            if (itr_len2 != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_len2, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.th))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf" + so);

                                    ch1 = ByteMeth.subArray(remain_char, 0, (remain_char.length - 1) - Constant.thu.length);
                                    String so1 = UnicodeConverter.revert(ch1);
                                    System.out.println("prev value in if after prev_char" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 " + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.thu);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2 " + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 " + p3);
                                    result = UnicodeConverter.revert(ch3);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.R))) {
                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf" + so);

                                    ch1 = ByteMeth.subArray(remain_char, 0, (remain_char.length - 1) - Constant.Ru.length);
                                    String so1 = UnicodeConverter.revert(ch1);
                                    System.out.println("prev value in if after prev_char" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 else" + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.Ru);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2  else" + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 else" + p3);
                                    result = UnicodeConverter.revert(ch3);
                                }
                            }
                        }
                    }
                }

                if (ByteMeth.startsWith(checkElmt, Constant.aimbadhthu)) {// || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu)) {
                    System.out.println("Enter into numbers erubadhu");
                    if ((checkElmt.length > 6)) {
                        System.out.println("Enter into 20");
                        check_value = ByteMeth.subArray(checkElmt, 6, checkElmt.length); //ppochchu
                        String o = UnicodeConverter.revert(check_value);
                        System.out.println("Check value " + o);
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        String oo = UnicodeConverter.revert(prev_char);
                        System.out.println("prev value " + oo);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        System.out.println("prev value cf" + UnicodeConverter.revert(cf));

                        if (ByteMeth.contains(cf, Constant.aimbadhthu)) {
                            if (itr_len2 != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_len2, prev_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.th))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf" + so);

                                    ch1 = ByteMeth.subArray(prev_char, 0, (prev_char.length - 1) - Constant.thu.length);
                                    String so1 = UnicodeConverter.revert(ch1);
                                    System.out.println("prev value in if after prev_char" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 " + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.thu);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2 " + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 " + p3);
                                    result = UnicodeConverter.revert(ch3);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf" + so);

                                    ch1 = ByteMeth.subArray(prev_char, 0, (prev_char.length - 1) - Constant.Ru.length);
                                    String so1 = UnicodeConverter.revert(ch1);
                                    System.out.println("prev value in if after prev_char" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 " + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.Ru);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2 " + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 " + p3);
                                    result = UnicodeConverter.revert(ch3);
                                }
                            }
                        }
                    } else {
                        if (ByteMeth.contains(checkElmt, Constant.aimbadhthu)) {
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length));
                            if (itr_len2 != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_len2, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.th))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf" + so);

                                    ch1 = ByteMeth.subArray(remain_char, 0, (remain_char.length - 1) - Constant.thu.length);
                                    String so1 = UnicodeConverter.revert(ch1);
                                    System.out.println("prev value in if after prev_char" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 " + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.thu);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2 " + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 " + p3);
                                    result = UnicodeConverter.revert(ch3);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.R))) {
                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf" + so);

                                    ch1 = ByteMeth.subArray(remain_char, 0, (remain_char.length - 1) - Constant.Ru.length);
                                    String so1 = UnicodeConverter.revert(ch1);
                                    System.out.println("prev value in if after prev_char" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 else" + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.Ru);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2  else" + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 else" + p3);
                                    result = UnicodeConverter.revert(ch3);
                                }
                            }
                        }
                    }
                }


                if (ByteMeth.startsWith(checkElmt, Constant.arubadhu)) {// || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu)) {
                    System.out.println("Enter into numbers erubadhu");
                    if ((checkElmt.length > 7)) {
                        System.out.println("Enter into 20");
                        check_value = ByteMeth.subArray(checkElmt, 7, checkElmt.length); //ppochchu
                        String o = UnicodeConverter.revert(check_value);
                        System.out.println("Check value " + o);
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        String oo = UnicodeConverter.revert(prev_char);
                        System.out.println("prev value " + oo);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        System.out.println("prev value cf" + UnicodeConverter.revert(cf));

                        if (ByteMeth.contains(cf, Constant.arubadhu)) {
                            if (itr_len2 != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_len2, prev_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.th))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf" + so);

                                    ch1 = ByteMeth.subArray(prev_char, 0, (prev_char.length - 1) - Constant.thu.length);
                                    String so1 = UnicodeConverter.revert(ch1);
                                    System.out.println("prev value in if after prev_char" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 " + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.thu);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2 " + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 " + p3);
                                    result = UnicodeConverter.revert(ch3);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf" + so);

                                    ch1 = ByteMeth.subArray(prev_char, 0, (prev_char.length - 1) - Constant.Ru.length);
                                    String so1 = UnicodeConverter.revert(ch1);
                                    System.out.println("prev value in if after prev_char" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 " + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.Ru);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2 " + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 " + p3);
                                    result = UnicodeConverter.revert(ch3);
                                }
                            }
                        }
                    } else {
                        if (ByteMeth.contains(checkElmt, Constant.arubadhu)) {
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length));
                            if (itr_len2 != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_len2, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.th))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf" + so);

                                    ch1 = ByteMeth.subArray(remain_char, 0, (remain_char.length - 1) - Constant.thu.length);
                                    String so1 = UnicodeConverter.revert(ch1);
                                    System.out.println("prev value in if after prev_char" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 " + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.thu);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2 " + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 " + p3);
                                    result = UnicodeConverter.revert(ch3);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.R))) {
                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf" + so);

                                    ch1 = ByteMeth.subArray(remain_char, 0, (remain_char.length - 1) - Constant.Ru.length);
                                    String so1 = UnicodeConverter.revert(ch1);
                                    System.out.println("prev value in if after prev_char" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 else" + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.Ru);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2  else" + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 else" + p3);
                                    result = UnicodeConverter.revert(ch3);
                                }
                            }
                        }
                    }
                }

                if (ByteMeth.startsWith(checkElmt, Constant.ezhubadhu)) {// || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu)) {
                    System.out.println("Enter into numbers erubadhu");
                    if ((checkElmt.length > 7)) {
                        System.out.println("Enter into 20");
                        check_value = ByteMeth.subArray(checkElmt, 7, checkElmt.length); //ppochchu
                        String o = UnicodeConverter.revert(check_value);
                        System.out.println("Check value " + o);
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        String oo = UnicodeConverter.revert(prev_char);
                        System.out.println("prev value " + oo);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        System.out.println("prev value cf" + UnicodeConverter.revert(cf));

                        if (ByteMeth.contains(cf, Constant.ezhubadhu)) {
                            if (itr_len2 != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_len2, prev_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.th))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf" + so);

                                    ch1 = ByteMeth.subArray(prev_char, 0, (prev_char.length - 1) - Constant.thu.length);
                                    String so1 = UnicodeConverter.revert(ch1);
                                    System.out.println("prev value in if after prev_char" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 " + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.thu);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2 " + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 " + p3);
                                    result = UnicodeConverter.revert(ch3);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf" + so);

                                    ch1 = ByteMeth.subArray(prev_char, 0, (prev_char.length - 1) - Constant.Ru.length);
                                    String so1 = UnicodeConverter.revert(ch1);
                                    System.out.println("prev value in if after prev_char" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 " + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.Ru);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2 " + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 " + p3);
                                    result = UnicodeConverter.revert(ch3);
                                }
                            }
                        }
                    } else {
                        if (ByteMeth.contains(checkElmt, Constant.ezhubadhu)) {
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length));
                            if (itr_len2 != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_len2, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.th))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf" + so);

                                    ch1 = ByteMeth.subArray(remain_char, 0, (remain_char.length - 1) - Constant.thu.length);
                                    String so1 = UnicodeConverter.revert(ch1);
                                    System.out.println("prev value in if after prev_char" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 " + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.thu);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2 " + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 " + p3);
                                    result = UnicodeConverter.revert(ch3);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.R))) {
                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf" + so);

                                    ch1 = ByteMeth.subArray(remain_char, 0, (remain_char.length - 1) - Constant.Ru.length);
                                    String so1 = UnicodeConverter.revert(ch1);
                                    System.out.println("prev value in if after prev_char" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 else" + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.Ru);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2  else" + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 else" + p3);
                                    result = UnicodeConverter.revert(ch3);
                                }
                            }
                        }
                    }
                }


                if (ByteMeth.startsWith(checkElmt, Constant.enbadhu)) {// || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu)) {
                    System.out.println("Enter into numbers erubadhu");
                    if ((checkElmt.length > 6)) {
                        System.out.println("Enter into 20");
                        check_value = ByteMeth.subArray(checkElmt, 6, checkElmt.length); //ppochchu
                        String o = UnicodeConverter.revert(check_value);
                        System.out.println("Check value " + o);
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        String oo = UnicodeConverter.revert(prev_char);
                        System.out.println("prev value " + oo);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        System.out.println("prev value cf" + UnicodeConverter.revert(cf));

                        if (ByteMeth.contains(cf, Constant.enbadhu)) {
                            if (itr_len2 != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_len2, prev_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.th))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf" + so);

                                    ch1 = ByteMeth.subArray(prev_char, 0, (prev_char.length - 1) - Constant.thu.length);
                                    String so1 = UnicodeConverter.revert(ch1);
                                    System.out.println("prev value in if after prev_char" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 " + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.thu);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2 " + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 " + p3);
                                    result = UnicodeConverter.revert(ch3);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf" + so);

                                    ch1 = ByteMeth.subArray(prev_char, 0, (prev_char.length - 1) - Constant.Ru.length);
                                    String so1 = UnicodeConverter.revert(ch1);
                                    System.out.println("prev value in if after prev_char" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 " + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.Ru);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2 " + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 " + p3);
                                    result = UnicodeConverter.revert(ch3);
                                }
                            }
                        }
                    } else {
                        if (ByteMeth.contains(checkElmt, Constant.enbadhu)) {
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length));
                            if (itr_len2 != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_len2, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.th))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf" + so);

                                    ch1 = ByteMeth.subArray(remain_char, 0, (remain_char.length - 1) - Constant.thu.length);
                                    String so1 = UnicodeConverter.revert(ch1);
                                    System.out.println("prev value in if after prev_char" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 " + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.thu);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2 " + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 " + p3);
                                    result = UnicodeConverter.revert(ch3);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.R))) {
                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf" + so);

                                    ch1 = ByteMeth.subArray(remain_char, 0, (remain_char.length - 1) - Constant.Ru.length);
                                    String so1 = UnicodeConverter.revert(ch1);
                                    System.out.println("prev value in if after prev_char" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 else" + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.Ru);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2  else" + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 else" + p3);
                                    result = UnicodeConverter.revert(ch3);
                                }
                            }
                        }
                    }
                }


                if (ByteMeth.startsWith(checkElmt, Constant.thonnootru)) {// || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu) || ByteMeth.startsWith(checkElmt, Constant.nnu)) {
                    System.out.println("Enter into numbers erubadhu");
                    if ((checkElmt.length > 7)) {
                        System.out.println("Enter into 20");
                        check_value = ByteMeth.subArray(checkElmt, 7, checkElmt.length); //ppochchu
                        String o = UnicodeConverter.revert(check_value);
                        System.out.println("Check value " + o);
                        prev_char = ByteMeth.subArray(topElmt3, 0, topElmt3.length - checkElmt.length);
                        String oo = UnicodeConverter.revert(prev_char);
                        System.out.println("prev value " + oo);
                        cf = ByteMeth.subArray(checkElmt, 0, checkElmt.length - check_value.length);
                        System.out.println("prev value cf" + UnicodeConverter.revert(cf));

                        if (ByteMeth.contains(cf, Constant.thonnootru)) {
                            if (itr_len2 != length) {
                                checkElmt_new = ByteMeth.subArray(prev_char, (prev_char.length) - itr_len2, prev_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.th))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf" + so);

                                    ch1 = ByteMeth.subArray(prev_char, 0, (prev_char.length - 1) - Constant.thu.length);
                                    String so1 = UnicodeConverter.revert(ch1);
                                    System.out.println("prev value in if after prev_char" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 " + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.thu);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2 " + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 " + p3);
                                    result = UnicodeConverter.revert(ch3);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf" + so);

                                    ch1 = ByteMeth.subArray(prev_char, 0, (prev_char.length - 1) - Constant.Ru.length);
                                    String so1 = UnicodeConverter.revert(ch1);
                                    System.out.println("prev value in if after prev_char" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 " + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.Ru);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2 " + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 " + p3);
                                    result = UnicodeConverter.revert(ch3);
                                }
                            }
                        }
                    } else {
                        if (ByteMeth.contains(checkElmt, Constant.thonnootru)) {
                            byte[] remain_char = ByteMeth.subArray(topElmt3, 0, (topElmt3.length - checkElmt.length));
                            if (itr_len2 != length) {
                                checkElmt_new = ByteMeth.subArray(remain_char, (remain_char.length) - itr_len2, remain_char.length);
                                if ((ByteMeth.startsWith(checkElmt_new, Constant.th))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.R))) {// || (ByteMeth.startsWith(checkElmt_new, Constant.u)) || (ByteMeth.startsWith(checkElmt_new, Constant.m))) { //paaththuppochchu

                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf" + so);

                                    ch1 = ByteMeth.subArray(remain_char, 0, (remain_char.length - 1) - Constant.thu.length);
                                    String so1 = UnicodeConverter.revert(ch1);
                                    System.out.println("prev value in if after prev_char" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 " + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.thu);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2 " + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 " + p3);
                                    result = UnicodeConverter.revert(ch3);
                                } else if ((ByteMeth.startsWith(checkElmt_new, Constant.R))) {
                                    String so = UnicodeConverter.revert(checkElmt_new);
                                    System.out.println("prev value in if after cf" + so);

                                    ch1 = ByteMeth.subArray(remain_char, 0, (remain_char.length - 1) - Constant.Ru.length);
                                    String so1 = UnicodeConverter.revert(ch1);
                                    System.out.println("prev value in if after prev_char" + so1);

                                    String p1 = UnicodeConverter.revert(ch1);
                                    System.out.println("Check value ch1 else" + p1);
                                    ch2 = ByteMeth.addArray(ch1, Constant.Ru);
                                    String p2 = UnicodeConverter.revert(ch2);
                                    System.out.println("Check value padhu onru ch2  else" + p2);

                                    ch3 = ByteMeth.addArray(ch2, checkElmt);
                                    String p3 = UnicodeConverter.revert(ch3);
                                    System.out.println("Check value irupadhu onru ch3 else" + p3);
                                    result = UnicodeConverter.revert(ch3);
                                }
                            }
                        }
                    }
                }




                itr++;
            }
            analysed = Analyser.analyseF(result, true);
            Integer n = nc.check_l(analysed);//.check_l(analysed);
            System.out.println("vakue cioahf n " + n);
            System.out.println("Value of ans " + analysed);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return analysed;

    }

    //double noun
    public static boolean dnoun(Stack s) {
        System.out.println("Enter into ADictionary dnoun method");
        int i = 1;
        int length = 0;
        byte[] topElmt = ((Entry) s.peek()).getPart();
        byte[] oldTopElmt = topElmt;
        byte[] checkElmt = topElmt;
        String input = "";

        length = topElmt.length;
        while (i != length) {

            checkElmt = ByteMeth.subArray(topElmt, (topElmt.length) - i, topElmt.length);
            // System.out.println("check Element  of whike        "+UnicodeConverter.revert(checkElmt));
            input = "";
            for (int j = 0; j < checkElmt.length; j++) {

                input += checkElmt[j];


                System.out.println("Enter into input " + UnicodeConverter.revert(checkElmt));
            }

            if (Noun_BTree.contains(input)) {
                s.pop();
                s.push(new Entry(checkElmt, Tag.Noun));
                ////System.out.println("break");
                break;
            } else if (Adjective_BTree.contains(input)) {
                //////////System.out.println("ADJ");
                //System.out.println( "ADJ");
                //Stack s = (Stack) s1.clone();

                s.pop();
                s.push(new Entry(checkElmt, Tag.Adjective));
                break;

            } else if (Char_Num_BTree.contains(input)) {
                //System.out.println("Enter into char num");
                s.pop();
                s.push(new Entry(checkElmt, Tag.cNumbers));
                break;
            }

            i++;
            //System.out.println("Value of i "+i);
        }

        //  if(!(ByteMeth.contains(checkElmt,)))
        String subwrd1 = UnicodeConverter.revert(checkElmt);
        System.out.println("check Element in if   1     " + subwrd1);
        if (i != length) {
            checkElmt = ByteMeth.subArray(topElmt, 0, topElmt.length - i);
            String subwrd = UnicodeConverter.revert(checkElmt);
            System.out.println("check Element in if        " + subwrd);

            s.push(new Entry(checkElmt, -1, oldTopElmt));
            Sandhi.kctp(s);
            //Sandhi.kctp(s);
            checkElmt = ((Entry) s.peek()).getPart();
            input = "";
            System.out.println("check Element         " + UnicodeConverter.revert(checkElmt));
            // colloquial(s,checkElmt);
            // for the word of like நாற்பதுஐந்துஐந்துநாற்பதுஐந்து and 122 நூற்றிருபத்திரண்டு . But i create problem for
            System.out.println("check Element length bfore for      " + checkElmt.length);
            for (int i1 = 0; i1 < checkElmt.length; i1++) {
                input += checkElmt[i1];
            }
            if (Noun_BTree.contains(input)) {
                ////////System.out.println( "Noun");
                s.pop();
                s.push(new Entry(checkElmt, Tag.Noun));
                return true;
            }
            if (m_End_Noun_BTree.contains(input)) {
                ////////System.out.println( "Noun");
                s.pop();
                s.push(new Entry(checkElmt, Tag.AdjectivalNoun));
                return true;
            }
            if (Char_Num_BTree.contains(input)) {
                System.out.println("char number");
                s.pop();
                s.push(new Entry(checkElmt, Tag.cNumbers));
                return true;
            }

            //   System.out.println("Entering ");
            //   if(checkElmt.length > 1){

            dnoun(s);
            //  }else{
            //     System.out.println(":enter ");
            //     return true;
            // }



        }
        ////////System.out.println(" after while");

        return false;
    }

    public static boolean m_EndNoun(Stack s) {
        System.out.println("Enter into ADictionary m_EndNoun method");
        byte[] topElmt = ((Entry) s.peek()).getPart();
        byte[] oldTopElmt = topElmt;
        String input = "";
        String input1 = "";
        String input2 = "";
        String input3 = "";
        for (int i = 0; i < topElmt.length; i++) {
            input += topElmt[i];
        }
        System.out.println("Entwer " + UnicodeConverter.revert(topElmt));
        if (ByteMeth.endsWith(topElmt, Constant.u)) {
            System.out.println("Entwering");
            topElmt = ByteMeth.subArray(topElmt, 0,
                    topElmt.length - Constant.u.length);
            topElmt = ByteMeth.addArray(topElmt, Constant.a);
        }


        /*     if(ByteMeth.contains(topElmt, Constant.Ayira)){
        if(topElmt.length>5){
        System.out.println("Entering");
        byte[] new_assign=ByteMeth.subArray(topElmt, 0,topElmt.length-Constant.Ayira.length);
        System.out.println("Valur odf patthy "+UnicodeConverter.revert(new_assign));
        byte[] new_assing1=ByteMeth.addArray(new_assign, Constant.u);
        //   for (int i = 0; i < new_assing1.length; i++) {
        ////  input1 += new_assing1[i];
        //   }
        // if (Char_Num_BTree.contains(input1)) {
        //System.out.println("char number");



        System.out.println("Valur odf patthy1 "+UnicodeConverter.revert(new_assing1));
        byte[] new_assign2=ByteMeth.addArray(Constant.Ayira, Constant.m);
        //  byte[] new_assign3=ByteMeth.addArray(new_assing1,new_assign2);
        System.out.println("Valur odf patthy1 "+UnicodeConverter.revert(new_assign2));
        for (int i = 0; i < new_assign2.length; i++) {
        input2 += new_assign2[i];
        }
        if (Char_Num_BTree.contains(input2)) {
        //System.out.println("char number");
        // s.pop();
        s.pop();
        s.push(new Entry(new_assign2, Tag.cNumbers));
        s.push(new Entry(new_assing1, Tag.cNumbers));
        return true;

        //   }
        }
        //String v=UnicodeConverter.revert(new_assign3);
        //String g=Analyser.analyseF(v, true);
        // System.out.println("valuisd "+g);
        }else{
        byte[] new_assigned=ByteMeth.addArray(Constant.Ayira, Constant.m);
        for (int i = 0; i < new_assigned.length; i++) {
        input += new_assigned[i];
        }
        if (Char_Num_BTree.contains(input)) {
        ////////System.out.println( "AdjectivalNoun");
        s.pop();
        s.push(new Entry(new_assigned, Tag.cNumbers));
        return true;
        }
        }

        }*/




        //  if (m_End_Noun_BTree.contains(input)) {
        // byte[] nr=UnicodeConverter.convert(input);
        // System.out.println("valuisd "+UnicodeConverter.revert(nr));
       /*  byte[] new_assigned=ByteMeth.addArray(nr, Constant.m);
        for (int i = 0; i < new_assigned.length; i++) {
        input += new_assigned[i];
        }*/



        /*  if (m_End_Noun_BTree.contains(input)) {
        s.push(new Entry(((Entry) s.pop()).getPart(), Tag.AdjectivalNoun));
        return true;
        }*/
        return false;
    }

    // Adverb
    public static boolean adverb(Stack s) {
        System.out.println("Enter into ADictionary adverb method");
        Sandhi.kctp(s);		//this statement is for checking whether the adverb comes with sandhi or not
        byte[] topElmt = ((Entry) s.peek()).getPart();
        byte[] oldTopElmt = topElmt;
        String input = "";
        for (int i = 0; i < topElmt.length; i++) {
            input += topElmt[i];
        }

        if (Adverb_BTree.contains(input)) {
            ////////System.out.println( "Adverb");
            s.push(new Entry(((Entry) s.pop()).getPart(), Tag.Adverb));
            return true;
        }
        return false;
    }

    // Verb
    public static boolean verb(Stack s) {
        System.out.println("Enter into ADictionary verb method");
        ////////System.out.println( "Chking Verb Dic");
        byte[] topElmt = ((Entry) s.peek()).getPart();
        byte[] oldTopElmt = topElmt;
        String input = "";
        for (int i = 0; i < topElmt.length; i++) {
            input += topElmt[i];
        }

        System.out.println("revert  1 " + UnicodeConverter.revert(topElmt));
        if (Verb_BTree.contains(input)) {
            ////////System.out.println("Verb_BTree True");
            if (ByteMeth.isEqual(topElmt, Constant.en)) {
                ////////System.out.println( "En");
                s.push(new Entry(((Entry) s.pop()).getPart(), Tag.En));
                return true;
            } else {
                ////////System.out.println( "Verb");
                s.push(new Entry(((Entry) s.pop()).getPart(), Tag.Verb));
                return true;
            }
        }
        return false;
    }

    //Nverb 1-06-06
    public static boolean Nverb(Stack s) {
        System.out.println("Enter into ADictionary Nverb method");
        int i = 1;
        int length = 0;
        ////////System.out.println( "Chking Noun+Verb Dic");
        byte[] topElmt = ((Entry) s.peek()).getPart();
        byte[] oldTopElmt = topElmt;
        String input = "";

        byte[] checkElmt = topElmt;
        length = topElmt.length;
        while (i != length) {
            checkElmt = ByteMeth.subArray(topElmt, (topElmt.length) - i,
                    topElmt.length);
            input = "";
            for (int i1 = 0; i1 < checkElmt.length; i1++) {
                input += checkElmt[i1];
            }
            // System.out.println("check Element in ADictionary NVerb       "+UnicodeConverter.revert(checkElmt));
            if (!(ByteMeth.contains(checkElmt, Constant.ippu))) {
                if (Verb_BTree.contains(input)) {
                    System.out.println("Chking Verb Dic----mine" + UnicodeConverter.revert(checkElmt));
                    ////////System.out.println( "Verb");
                    s.pop();
                    s.push(new Entry(checkElmt, Tag.Verb));
                    break;
                }
            }

            i++;
            //  }
        }

        if (i != length) {

            checkElmt = ByteMeth.subArray(topElmt, 0, topElmt.length - i);
            input = "";
            for (int i1 = 0; i1 < checkElmt.length; i1++) {
                input += checkElmt[i1];
            }
            System.out.println("check Element in i!=len    " + UnicodeConverter.revert(checkElmt));
            s.push(new Entry(checkElmt, -1, oldTopElmt));
            Sandhi.kctp(s);

            Case.check(s, true);

            checkElmt = ((Entry) s.peek()).getPart();
            if (Noun_BTree.contains(input)) {
                ////////System.out.println( "Noun");
                s.pop();
                s.push(new Entry(checkElmt, Tag.Noun));
                return true;
            } else {
                s.push(new Entry(checkElmt, -1));
            }
        }
        ////////System.out.println("return false--mine"+checkElmt);
        return false;
    }

    public static boolean longVowel(Stack s) {
        System.out.println("Enter into ADictionary longVowel method");
        byte[] topElmt = ((Entry) s.peek()).getPart();
        byte[] oldTopElmt = topElmt;
        if (topElmt[topElmt.length - 1] == 1) {

            topElmt[topElmt.length - 1] = 2;
            String input = "";
            for (int i = 0; i < topElmt.length; i++) {
                input += topElmt[i];
            }

            if (Entity_BTree.contains(input)) {
                s.push(new Entry(((Entry) s.pop()).getPart(), Tag.Entity));
                return true;
            }
            return false;
        }
        return false;
    }
}

