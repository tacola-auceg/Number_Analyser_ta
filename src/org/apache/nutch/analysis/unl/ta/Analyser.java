package org.apache.nutch.analysis.unl.ta;

import java.io.*;
import java.util.Stack;
import java.util.Set;
import java.util.HashSet;
import java.util.Vector;
import java.util.ResourceBundle;
import java.util.*;
//import org.apache.nutch.unl.UNL;

public class Analyser {

    static int value = 0;
    static ArrayList<Integer> ar = new ArrayList<Integer>();
    static ArrayList ar1 = new ArrayList();
    static boolean analysePart = true;
    static boolean print;
    public static BufferedWriter bw1 = null;
    public static BufferedWriter bw2 = null;
    public static BufferedWriter bw4 = null;
    public static BufferedWriter bw5 = null;
    public static BufferedWriter bw6 = null;
    public static ResourceBundle Tags = ResourceBundle.getBundle("org/apache/nutch/analysis/unl/ta/Tag/" + Tag.ENGLISH_EXPAND, new Locale("en", "US"));

    public static String analyseF(String input, boolean analysePart) {
        //System.out.println("Enter into Analyser analyseF method");
        Number_Conversion nc = new Number_Conversion();
        
        String output = "<" + input + ">" + ":\n";
        String in = input;

        try {
            bw1 = new BufferedWriter(new FileWriter("./all_output.txt", true));
            bw5 = new BufferedWriter(new FileWriter("./first_unknown.txt", true));
            bw4 = new BufferedWriter(new FileWriter("./colloquial_unknown.txt", true));
            bw2 = new BufferedWriter(new FileWriter("./compound_unknown.txt", true));
            bw6 = new BufferedWriter(new FileWriter("./number_unknown.txt", true));
            Stack allStk = new Stack();
            int count = 0;
            Method.analyse(input, allStk, analysePart);
            int size = allStk.size();
            if (size == 0) {
                output += "<Error>\n";
                return output;
            }

            for (int i = 0; i < size; i++) {
                Stack outputStack = (Stack) allStk.get(i);
                String part = "<";
                boolean flag = false;
                String stag = "~";
                while (!outputStack.empty()) {
                    Entry entry = (Entry) outputStack.pop();
                    String s = UnicodeConverter.revert(entry.getPart());
                    int tag = entry.getTag();
                    if (tag != -1) {
                        flag = true;
                        if (part.length() == 1) {
                            part += s;
                        } else {
                            part += "+" + s;
                        }
                        s += " < ";
                        s += Tags.getString(String.valueOf(tag));
                        s += " " + "&" + " ";
                        stag = stag + "\t" + String.valueOf(tag);
                        s += String.valueOf(tag);

                        s += " > ";
                    } else {
                        count++;
                        s += "<0>";
                        break;
                    }
                    output += s;
                }

                if (!stag.equals("~")) {
                    if (allStk.size() != 1 && flag == true) {
                        output += Utils.newLineStr + part + '>' + Utils.newLineStr;
                    }
                }

            }
            output += " count=" + count + "\n";
            if (count == (size - 1) && size == 4) {
                if (output.startsWith("<#>:")) {
                    output = "<<**********>\n";
                } else {
                    output += "<unknown>\n";
                    // bw5.write("\n"+input+"# ");
                }
            }


            /* if(output.contains("< charNumbers & 98 >") || (output.contains(" < charNumbers & 98 > "))){
            //System.out.println("Go to one_number function");
            // String value=nc.one_number(output);
            String value=nc.check_l(output);


            }*/
            //  if()
            //  bw5.write("\n"+input+"# ");
            //System.out.println("Output of general analyser " + output);
            if (!(output.contains("count=2"))) { //for 104 114 14
                
                if (output.contains("<unknown>\n") || output.contains("count=4") || output.contains("count=5") || output.contains("null")) {
                    //bw5.write(input+"\n");
                    ////////System.out.println("Output of general analyser " + output);
                    ////////System.out.println("Output result of general analyser " + input);
                    bw5.write(input + "\n");
                    bw5.close();
                }
                output += handle_doublenoun(input) + "\n";
                //System.out.println("out " + output);
                if ((output.contains("null")) || (output.contains("<unknown>")) || (output.contains("count=4"))) {
                    //System.out.println("unknown of compound words " + output);
                    bw2.write(input + "\n");
                    bw2.close();
                    output += handle_colloquial(input) + "\n";

                    //System.out.println("Value of output in analyser " + output);
                    if (output.contains("<unknown>\n") || output.contains("count=4") || output.contains("count=1") || output.contains("null")) {
                        ////////System.out.println("Unlknown colloquial words");
                        bw4.write(input + "\n");

                    }
                }
            }
            else{
                String[] temp = output.split("\\n");
                for(String s:temp)
                    System.out.println("Output :"+ s);
            }
            bw1.write(output.toString());// + "# ");
            //System.out.println("VAlu eof steonf "+output);

            bw1.close();
            //  bw2.close();
            bw4.close();
            //bw6.close();
        } catch (Exception e) {
            //////System.err.println("Analyser analyseF: " + e);
            e.printStackTrace();
            //return input;
        }
        //System.out.println("VAlu eof steonf " + output);
String s1="";
        if (output.contains("< charNumbers & 98 >") || (output.contains(" < charNumbers & 98 > ")) || (output.contains("< charNumbers & 98 >  < charNumbers & 98 >"))) {
            //System.out.println("Go to one_number function");
            // String value=nc.one_number(output);f
            value = nc.check_l(output);
            System.out.println("Value of Analyser :" + value);
            System.out.println("Output of the Analyser :"+ output);
            String s=(Integer.toString(value));
            s1 = output+"\t"+s;
            System.out.println("------------------------------------------------------------");
            System.out.println("Value + Output of analyser :"+s1);
            System.out.println("------------------------------------------------------------");
            return s1;
        }

        //System.out.println("value of oputput outside "+s1);
        return s1;

    }

    public static String analyseW(String input, boolean analysePart) {
        //System.out.println("Enter into Analyser analyseW method");
        String output = input + ":";

        try {
            Stack allStk = new Stack();


            Method.analyse(input, allStk, analysePart);
            int size = allStk.size();

            if (size == 0) {
                output += " Unknown";
                return output;
            }
            for (int i = 0; i < size; i++) {
                Stack outputStack = (Stack) allStk.get(i);

                while (!outputStack.empty()) {
                    Entry entry = (Entry) outputStack.pop();
                    String s = UnicodeConverter.revert(entry.getPart());

                    output += Utils.newLineStr_OS;
                    output += s;
                    int tag = entry.getTag();

                    if (tag != -1) {
                        s = " < ";
                        s += Tags.getString(String.valueOf(tag));
                        s += " > ";
                    } else {
                        s = "";
                    }
                    output += s;
                }
                if (allStk.size() != 1) {
                    output += Utils.newLineStr_OS + "***";
                }
            }
        } catch (Exception e) {
            ////System.err.println("Analyser analyseW: " + e);
            e.printStackTrace();
            return input;
        }

        return output;
    }
    public static String[] sandhi_new = {"14", "16", "18", "20", "22", "30"};//க்,ச்,ட்,த்,ப்,ற்
    public static String[] valinam = {"15", "17", "19", "21", "23", "31"};
    public static String[] uir = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

    //handle double noun for poo
    public static String check_noun(String str) {
        //System.out.println("Enter into Analyser check_noun method");

        byte[] topElmt2 = UnicodeConverter.convert(str);
        String strrev = "";

        String noun_str = "";
        boolean isNoun = Noun.check(new Stack(), topElmt2, true);
        if (isNoun) {
            ////////System.out.println("Inside noun check" + str);
            noun_str = str + "<Noun>";
            //////////System.out.println("kalvi" + noun_str);
        }
        return noun_str;
    }

    public static String handle_doublenoun(String str) {
        //System.out.println("Enter into Analyser handle_doublenoun method");
        String result_dnoun = "";
        try {
            String str_noun = "";
            String new_str = "";
            boolean isNoun = false;
            Stack s = new Stack();
            Stack allstk = new Stack();
            byte topElmt3[] = UnicodeConverter.convert(str);
            result_dnoun = ADictionary.handle_double(topElmt3);
        } catch (Exception e) {
        }
        return result_dnoun;

    }

    public static String handle_colloquial(String str) {
        //System.out.println("Enter into Analyser handle_colloquial method");
        String colloquial_result = "";
        try {
            Stack s = new Stack();
            Stack allstk = new Stack();
            byte topElmt4[] = UnicodeConverter.convert(str);
            colloquial_result = ADictionary.colloquial(allstk, topElmt4);
        } catch (Exception e) {
        }

        return colloquial_result;
    }

    public static Vector analyse(String input, boolean analysePart) {
        //System.out.println("Enter into Analyser analyse method");
        Vector output = new Vector();

        try {
            Stack allStk = new Stack();
            Method.analyse(input, allStk, analysePart);
            for (int i = 0; i < allStk.size(); i++) {
                Stack outputStack = (Stack) allStk.get(i);
                Vector part = new Vector();
                Vector tag = new Vector();
                while (!outputStack.empty()) {
                    Entry entry = (Entry) outputStack.pop();
                    String s = UnicodeConverter.revert(entry.getPart());
                    part.add(s);
                    int tagInt = entry.getTag();
                    tag.add(new Integer(tagInt));
                }
                output.add(new Element(part, tag));
            }
        } catch (Exception e) {

            return null;
        }

        return output;
    }

    public static Vector getVector(Stack inputStk) {
        //System.out.println("Enter into Analyser getVector method");
        Vector output = new Vector();

        try {
            for (int i = 0; i < inputStk.size(); i++) {
                Stack outputStack = (Stack) inputStk.get(i);
                Vector part = new Vector();
                Vector tag = new Vector();

                while (!outputStack.empty()) {
                    Entry entry = (Entry) outputStack.pop();
                    String s = UnicodeConverter.revert(entry.getPart());

                    part.add(s);
                    int tagInt = entry.getTag();

                    if (tagInt != -1) {
                        s = String.valueOf(tagInt);
                    } else {
                        s = "";
                    }
                    tag.add(s);
                }
                output.add(new Element(part, tag));
            }
        } catch (Exception e) {

            return null;
        }
        return output;
    }

    // call this before analysing a large document
    // load btree into memory
    public static void init() {
        //System.out.println("Enter into Analyser init method");
        analyse(UnicodeConverter.revert(Constant.vel), analysePart);
    }

    public static void main(String args[]) {
        //System.out.println("Enter into Analyser main method");
        try {

            boolean flag = false;

            Vector v = new Vector();
            String str = "";
            Hashtable h = new Hashtable();
            String s = "";
            String key2 = "";
            String output = "";
            String[] sptsap = null;
            BufferedReader inbuff = new BufferedReader(new FileReader("./in.txt"));
            BufferedWriter outbuff = new BufferedWriter(new FileWriter("/root/out.txt"));
            BufferedWriter unknownbuff = new BufferedWriter(new FileWriter("/root/unknown.txt"));
            while ((str = inbuff.readLine()) != null) {
                // //System.out.println("STRING "+str);
                if (str.indexOf("#") == -1) {
                    // //System.out.println("STRING in "+str);

                    String[] spt = str.split(" ");
                    for (int l = 0; l < spt.length; l++) {
                        //System.out.println("Value osf spt length " + spt.length);

                        s = spt[l].toString();
                        output = Analyser.analyseF(s, true);
                        //System.out.println("Ending------- " + output);
                        //System.out.println("Ending of value------- " + value);



                        ar.add(value);
                        if (h.isEmpty()) {
                            //System.out.println("Entering if ");
                            h.put(s, value);
                        }
                        if (ar.size() > 1) {
                            //System.out.println("Value of ar.size() " + ar.size());
                            h.put(s, value);
                        } else {
                        }
                    }
                    int c = 0;
                    // ar1.add(ar);
                    //System.out.println("############################### " + ar.toString());
                    //System.out.println("ar.size " + ar.size());

                    int sn3 = 0;
                    int sn4 = 0;
                    int sn6 = 0;
                    int sn7 = 0;
                    int sn9 = 0;
                    int snnew = 0;
                    int sn8 = 0;
                    int snnew1 = 0;

                    //sn3=ar.get(0);



                    //System.out.println("value of ar size " + ar.size());
                    if (ar.size() > 2) {
                        //System.out.println("Enter into");
                        sn4 = ar.get(1);
                        if (sn4 == 100000) {
                            if (ar.size() > 4) {
                                for (int k = 4; k < ar.size(); k++) {
                                    sn6 = ar.get(k);
                                    //System.out.println("   values  " + sn3 + "  " + sn4 + "  " + sn6);

                                    snnew = ar.get(2);
                                    snnew1 = ar.get(3);
                                    if (sn4 == 100000) {
                                        sn3 = ar.get(0);
                                        //System.out.println("value of sn3 if sn4 equals lakh " + sn3);
                                        // int sn7 = 0;
                                        sn7 = sn3 * sn4;
                                        //  c = sn7;
                                        //System.out.println("value of sn6 if sn4 equals lakh " + sn7);
                                        if (snnew1 == 1000) {
                                            sn9 = snnew1 * snnew;
                                            //System.out.println("value of sn9 if snnew1 equals thousand " + sn9);
                                        } else {
                                            sn9 = snnew1 + snnew;
                                            //System.out.println("value of sn9 if snnew1 not equals thousand " + sn9);
                                        }
                                        //sn10=sn9;
                                        sn8 += sn6;
                                        //System.out.println("add sn8 in 1 " + sn8);
                                        //c += sn8;
                                        c = sn7 + sn9 + sn8;
                                        //System.out.println("mul " + c);
                                    } else if (sn4 != 100000) {
                                        sn3 = ar.get(0);
                                        if ((sn4 == 1000)) {
                                            // int sn7 = 0;
                                            sn7 = sn3 * sn4;
                                            //System.out.println("value of sn7 in else if when sn4 equals thousand" + sn7);
                                            sn8 += sn6;
                                            //System.out.println("add sn8 " + sn8);
                                            c = sn8 + sn7;
                                            //System.out.println("add " + c);
                                        } else {
                                            //          int sn7 = 0;
                                            sn7 = sn3 + sn4;
                                            //System.out.println("value of sn7 in else if " + sn7);
                                            sn8 += sn6;
                                            //System.out.println("add sn8 " + sn8);
                                            c = sn8 + sn7;
                                            //System.out.println("add " + c);
                                        }
                                    }
                                }
                            } else if (ar.size() <= 4) {
                                //System.out.println("Enter into less than 4");
                                for (int k = 2; k < ar.size(); k++) {
                                    //System.out.println("Enter into 2 in k");
                                    if (sn4 == 100000) {
                                        sn6 = ar.get(k);
                                        sn3 = ar.get(0);
                                        //System.out.println("value of sn3 if sn4 equals lakh se" + sn3);
                                        // int sn7 = 0;
                                        sn7 = sn3 * sn4;
                                        //  c = sn7;
                                        //System.out.println("value of sn6 if sn4 equals lakh dec" + sn7);
                                        if (snnew1 == 1000) {
                                            sn9 = snnew1 * snnew;
                                            //System.out.println("value of sn9 if snnew1 equals thousand fre" + sn9);
                                        } else {
                                            sn9 = 0;
                                            //System.out.println("value of sn9 if snnew1 not equals thousand gte" + sn9);
                                        }
                                        //sn10=sn9;
                                        sn8 += sn6;
                                        //System.out.println("add sn8 in 1 sda" + sn8);
                                        //c += sn8;
                                        c = sn7 + sn9 + sn8;
                                        //System.out.println("mul " + c);
                                    } else if (sn4 != 100000) {
                                        //System.out.println("Enter into not equal 100000");
                                        sn3 = ar.get(0);
                                        if ((sn4 == 1000)) {
                                            // int sn7 = 0;
                                            sn7 = sn3 * sn4;
                                            //System.out.println("value of sn7 in else if when sn4 equals thousand afa" + sn7);
                                            sn8 += sn6;
                                            //System.out.println("add sn8 cgrt" + sn8);
                                            c = sn8 + sn7;
                                            //System.out.println("add gty" + c);
                                        } else {
                                            //System.out.println("Enter into not equal 1000");//          int sn7 = 0;
                                            sn7 = sn3 + sn4;
                                            //System.out.println("value of sn7 in else if ytrt" + sn7);
                                            sn8 += sn6;
                                            //System.out.println("add sn8 urte" + sn8);
                                            c = sn8 + sn7;
                                            //System.out.println("add arqwr" + c);
                                        }
                                    }
                                }
                            }
                        } else if (sn4 == 1000) {
                            //System.out.println("value of ar size inside if afr" + ar.size());
                            for (int k = 2; k < ar.size(); k++) {
                                sn6 = ar.get(k);
                                //System.out.println("   values  " + sn3 + "  " + sn4 + "  " + sn6);

                                //  snnew=ar.get(0);
                                //   snnew1=ar.get(1);

                                if ((sn4 == 1000)) {
                                    sn3 = ar.get(0);
                                    //    int sn7 = 0;
                                    sn7 = sn3 * sn4;
                                    //System.out.println("value of sn7 in else if when sn4 equals thousand arwa" + sn7);
                                    //System.out.println("value of sn6 in else if when sn4 equals thousand asagea" + sn6);
                                    sn8 += sn6;
                                    //System.out.println("add sn8 " + sn8);
                                    c = sn8 + sn7;
                                    //System.out.println("add " + c);
                                } else {
                                    sn3 = ar.get(0);
                                    // int sn7 = 0;
                                    sn7 = sn3 + sn4;
                                    //System.out.println("value of sn7 in else if " + sn7);
                                    sn8 += sn6;
                                    //System.out.println("add sn8 " + sn8);
                                    c = sn8 + sn7;
                                    //System.out.println("add " + c);
                                }
                            }
                        } else if (sn4 != 1000) {
                            //System.out.println("value of ar size inside if afr" + ar.size());
                            for (int k = 1; k < ar.size(); k++) {
                                sn6 = ar.get(k);
                                //System.out.println("   values  " + sn3 + "  " + sn4 + "  " + sn6);

                                //  snnew=ar.get(0);
                                //   snnew1=ar.get(1);

                                if ((sn4 != 1000)) {
                                    sn3 = ar.get(0);
                                    //    int sn7 = 0;
                                    sn7 = sn3;
                                    //System.out.println("value of sn7 in else if when sn4 equals thousand arwa" + sn7);
                                    //System.out.println("value of sn6 in else if when sn4 equals thousand asagea" + sn6);
                                    sn8 += sn6;
                                    //System.out.println("add sn8 " + sn8);
                                    c = sn8 + sn7;
                                    //System.out.println("add " + c);
                                } else {
                                    sn3 = ar.get(0);
                                    // int sn7 = 0;
                                    sn7 = sn3 + sn4;
                                    //System.out.println("value of sn7 in else if " + sn7);
                                    sn8 += sn6;
                                    //System.out.println("add sn8 " + sn8);
                                    c = sn8 + sn7;
                                    //System.out.println("add " + c);
                                }
                            }
                        }
                    } else if ((ar.size() > 1) && (ar.size() <= 2)) {
                        sn4 = ar.get(1);
                        sn3 = ar.get(0);
                        c = sn3 + sn4;
                        //System.out.println("add two values " + c);
                    } else {
                        c = ar.get(0);
                        //System.out.println("enter " + c);
                    }
                    /*      if (ar.size() > 4) {
                    sn4 = ar.get(1);
                    //System.out.println("value of ar size inside if " + ar.size());
                    for (int k = 4; k < ar.size(); k++) {
                    sn6 = ar.get(k);
                    //System.out.println("   values  " + sn3 + "  " + sn4 + "  " + sn6);

                    snnew = ar.get(2);
                    snnew1 = ar.get(3);
                    if (sn4 == 100000) {
                    sn3 = ar.get(0);
                    //System.out.println("value of sn3 if sn4 equals lakh " + sn3);
                    // int sn7 = 0;
                    sn7 = sn3 * sn4;
                    //  c = sn7;
                    //System.out.println("value of sn6 if sn4 equals lakh " + sn7);
                    if (snnew1 == 1000) {
                    sn9 = snnew1 * snnew;
                    //System.out.println("value of sn9 if snnew1 equals thousand " + sn9);
                    } else {
                    sn9 = snnew1 + snnew;
                    //System.out.println("value of sn9 if snnew1 not equals thousand " + sn9);
                    }
                    //sn10=sn9;
                    sn8 += sn6;
                    //System.out.println("add sn8 in 1 " + sn8);
                    //c += sn8;
                    c = sn7 + sn9 + sn8;
                    //System.out.println("mul " + c);
                    } else if (sn4 != 100000) {
                    sn3 = ar.get(0);
                    if ((sn4 == 1000)) {
                    // int sn7 = 0;
                    sn7 = sn3 * sn4;
                    //System.out.println("value of sn7 in else if when sn4 equals thousand" + sn7);
                    sn8 += sn6;
                    //System.out.println("add sn8 " + sn8);
                    c = sn8 + sn7;
                    //System.out.println("add " + c);
                    } else {
                    //          int sn7 = 0;
                    sn7 = sn3 + sn4;
                    //System.out.println("value of sn7 in else if " + sn7);
                    sn8 += sn6;
                    //System.out.println("add sn8 " + sn8);
                    c = sn8 + sn7;
                    //System.out.println("add " + c);
                    }
                    }
                    }
                    } else if ((ar.size() > 2) && (ar.size() <= 4)) {
                    sn4 = ar.get(1);
                    //System.out.println("value of ar size inside if " + ar.size());
                    for (int k = 2; k < ar.size(); k++) {
                    sn6 = ar.get(k);
                    //System.out.println("   values  " + sn3 + "  " + sn4 + "  " + sn6);

                    //  snnew=ar.get(0);
                    //   snnew1=ar.get(1);

                    if ((sn4 == 1000)) {
                    sn3 = ar.get(0);
                    //    int sn7 = 0;
                    sn7 = sn3 * sn4;
                    //System.out.println("value of sn7 in else if when sn4 equals thousand" + sn7);
                    //System.out.println("value of sn6 in else if when sn4 equals thousand" + sn6);
                    sn8 += sn6;
                    //System.out.println("add sn8 " + sn8);
                    c = sn8 + sn7;
                    //System.out.println("add " + c);
                    } else {
                    sn3 = ar.get(0);
                    // int sn7 = 0;
                    sn7 = sn3 + sn4;
                    //System.out.println("value of sn7 in else if " + sn7);
                    sn8 += sn6;
                    //System.out.println("add sn8 " + sn8);
                    c = sn8 + sn7;
                    //System.out.println("add " + c);
                    }

                    }
                    } else if ((ar.size() > 1) && (ar.size() <= 2)) {
                    sn4 = ar.get(1);
                    sn3 = ar.get(0);
                    c = sn3 + sn4;
                    //System.out.println("add two values " + c);
                    } else {
                    c = ar.get(0);
                    //System.out.println("enter " + c);
                    }*/



                    bw6.write(str + "\n" + h + "......>" + c + "\n");


                    // ar.remove(value);
                   /* for(int m=0;m<ar1.size();m++){
                    String get_m=ar1.get(m).toString();
                    //System.out.println("VVVVVVVV "+get_m);
                    if(get_m.contains("[")){
                    get_m.replace("[", "");
                    get_m.replace("]", "");
                    String[] a=get_m.split(",");
                    //System.out.println("VVVVVVVV enter "+a[0]+"  "+a[1]);
                    int g3=Integer.parseInt(a[0])+Integer.parseInt(a[1]);
                    // Integer g1=(Integer.parseInt(str))a[0];
                    int g1=Integer.parseInt(a[0]);
                    //System.out.println("VVVVVVVV enter g1 "+g1);
                    int g2=Integer.parseInt(a[1]);
                    // int g3=g1+g2;
                    //System.out.println("VVVVVVVVvvvv "+g3);
                    }
                    }*/
                    outbuff.write(output);
                    ar.clear();
                    h.clear();
                    bw6.close();
                    //outbuff.write("******");

                    /*else if(str.indexOf("#") == -1) {
                    String analysed =Analyser.analyseF(str,true);
                    outbuff.write(analysed);
                    }*/
                }
            }

            inbuff.close();
            outbuff.close();
            unknownbuff.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    // for procesing analyser output------------------
    static Set d = new HashSet();
    static String y = "";

    static {

        d.add(String.valueOf(Tag.Numbers));
        d.add(String.valueOf(Tag.Noun));
        d.add(String.valueOf(Tag.Pronoun));
        d.add(String.valueOf(Tag.InterrogativeNoun));
        d.add(String.valueOf(Tag.PronounOblique));
        d.add(String.valueOf(Tag.Verb));
        d.add(String.valueOf(Tag.FiniteVerb));
        d.add(String.valueOf(Tag.NegativeFiniteVerb));
        d.add(String.valueOf(Tag.Adjective));
        d.add(String.valueOf(Tag.DemonstrativeAdjective));
        d.add(String.valueOf(Tag.InterrogativeAdjective));
        d.add(String.valueOf(Tag.Adverb));
        d.add(String.valueOf(Tag.Interjection));
        d.add(String.valueOf(Tag.Conjunction));
        d.add(String.valueOf(Tag.Particle));
        d.add(String.valueOf(Tag.Intensifier));
        d.add(String.valueOf(Tag.Postposition));
        d.add(String.valueOf(Tag.cNumbers));


    }

    public static boolean isNoun(Vector v) {
        //System.out.println("Enter into Analyser isNoun method");


        for (int i = 0; i
                < v.size(); i++) {
            Element elmt = (Element) v.get(i);
            Vector tag = elmt.getTag();


            int index = elmt.containsTag(new Integer(Tag.Noun));



            if (index != -1) {
                return true;


            }
        }
        return false;


    }

    public static boolean is2320(Vector v) {
        //System.out.println("Enter into Analyser is2320 method");


        for (int i = 0; i
                < v.size(); i++) {
            Element elmt = (Element) v.get(i);
            Vector tag = elmt.getTag();


            int index = elmt.containsTag(new Integer(2320));



            if (index != -1) {
                return true;


            }
        }
        return false;


    }

    public static boolean isNoun(String word) {
        //System.out.println("Enter into Analyser isNoun method");


        return isNoun(analyse(word, analysePart));


    }

    public static boolean isVerb(Vector v) {
        //System.out.println("Enter into Analyser isVerb vector method");


        for (int i = 0; i
                < v.size(); i++) {
            Element elmt = (Element) v.get(i);
            Vector tag = elmt.getTag();


            int index = elmt.containsTag(new Integer(Tag.Verb));



            if (index != -1) {
                return true;


            }
        }
        return false;


    }

    public static boolean isVerb(String word) {
        //System.out.println("Enter into Analyser isVerb word method");


        return isVerb(analyse(word, analysePart));


    }

    public static String getRoot(Vector v) {
        //System.out.println("Enter into Analyser getRoot vector method");


        for (int i = 0; i
                < v.size(); i++) {
            Element elmt = (Element) v.get(i);
            Vector partV = elmt.getPart();

            // String tag = null;



            if (partV.size() > 0) {
                return (String) partV.get(0);


            }
        }
        return null;


    }

    public static String getRoot(String word) {
        //System.out.println("Enter into Analyser getRoot word method");


        return getRoot(analyse(word, analysePart));


    }

    public static boolean isAnalysed(Vector v) {
        //System.out.println("Enter into Analyser isAnalysed vetor method");


        if (v.size() > 0) {
            return true;


        }
        return false;


    }

    public static boolean isAnalysed(String input) {
        //System.out.println("Enter into Analyser isAnalysed word method");
        Vector v = analyse(input, analysePart);



        if (v.size() > 0) {
            return true;


        }
        return false;


    }

    //start of analyser from agaraadhi
    public ArrayList onlineDictAnalyser(String input) {
        input = removeSymbols(input.trim());
        ArrayList arr = new ArrayList();
        input = analyseF(input.trim(), true);


        if (!input.contains("<Error>")) {
            arr.add(input);
            arr.add(getAanlyserFormat(input));
            input = getRootWord(input);
            //////System.out.println("--->>>" + input);



            if (input.length() > 1) {
                arr.add(input.trim());
                input = getTypes(arr.get(0).toString());
                arr.add(input.trim());
                arr.add(getTranslation(arr.get(1).toString(), arr.get(0).toString(), arr.get(2).toString()));


            } else {
                arr = new ArrayList();
                arr.add("Analyser Not Found");
                arr.add("Analyser Not Found");
                arr.add("Analyser Not Found");
                arr.add("none");
                arr.add("Analyser Not Found");


            }
        } else {
            arr.add("Analyser Not Found");
            arr.add("Analyser Not Found");
            arr.add("Analyser Not Found");
            arr.add("none");
            arr.add("Analyser Not Found");


        }
        return arr;


    }

    public String getAanlyserFormat(String input) {
        if (input.contains("Demonstrative Adjective")) {
            input = isDoubleRoot(input);


        } else {
            input = isSingleRoot(input);


        }
        return input;


    }

    public String isSingleRoot(String input) {
        String alsFormat = "";
        String[] spt = input.split("\n");


        for (int i = 0; i
                < spt.length; i++) {
            if (spt[i].contains(":") && spt.length > 1) {
                i++;


                while (spt[i].contains("<")) {
                    int start = spt[i].indexOf("<");


                    int end = 0;


                    if (spt[i].contains(">")) {
                        end = spt[i].indexOf(">");


                    } else {
                        end = spt[i].length() - 2;


                    }
                    if (end != spt[i].length() - 2) {
                        alsFormat += spt[i].substring(0, start) + "+";


                    } else {
                        alsFormat += checkIkkal(spt[i].substring(0, start));


                    }
                    spt[i] = spt[i].substring(end + 1, spt[i].length());


                }
                alsFormat += "<br/>";


            }
        }
        return alsFormat;


    }

    public String isDoubleRoot(String input) {
        String alsFormat = "";
        String[] spt = input.split("\n");


        while (spt[spt.length - 2].contains("<")) {
            int start = spt[spt.length - 2].indexOf("<");


            int end = 0;


            if (spt[spt.length - 2].contains(">")) {
                end = spt[spt.length - 2].indexOf(">");


            } else {
                end = spt[spt.length - 2].length() - 2;


            }
            if (end != spt[spt.length - 2].length() - 2) {
                alsFormat += spt[spt.length - 2].substring(0, start) + "+";


            } else {

                alsFormat += checkIkkal(spt[spt.length - 2].substring(0, start));


            }
            spt[spt.length - 2] = spt[spt.length - 2].substring(end + 1, spt[spt.length - 2].length());


        }
        alsFormat += "<br/>";


        return alsFormat;


    }

    public String checkIkkal(String input) {
        if (input.contains("க்கள்")) {
            input = input.replace("க்", "க் + ");


        }
        return input;


    }

    public String getRootWord(String input) {
        try {
            if (input.contains("Demonstrative Adjective")) {
                String[] spt = input.split("\n");


                int string = spt.length - 2;


                int end = spt[string].indexOf("<");
                input = spt[string].substring(0, end);


            } else {
                int start = input.indexOf(":");
                input = input.substring(start + 1, input.length() - 1);


                int end = input.indexOf("<");
                input = input.substring(1, end - 1);


            }
            return input;


        } catch (Exception e) {
            return "Word Not Found";


        }
    }

    public String getTypes(String input) {
        try {
            if (input.contains("Demonstrative Adjective")) {
                String[] spt = input.split("\n");


                int string = spt.length - 2;


                int start = spt[string].indexOf("<");


                int end = spt[string].indexOf(">");
                input = spt[string].substring(start + 1, end - 1);


            } else {
                int start = input.indexOf(":");
                input = input.substring(start + 1, input.length() - 1);
                start = input.indexOf("<");


                int end = input.indexOf(">");
                input = input.substring(start + 1, end - 1);


            }
            return input;


        } catch (Exception e) {
            return "none";


        }
    }

    public String removeSymbols(String input) {
        String[] symbols = {"!", "@", "#", "$", "%", "^", "&", "_", "{", "}", "=", "[", "]", "|", "/", "\\", "\"", ">", "<", ";", ":", "'", ",", ".", "~", "`", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "?", "+", "-", "*", "(", ")"};


        for (int i = 0; i
                < symbols.length; i++) {
            if (input.contains(symbols[i])) {
                input = input.replace(symbols[i], "");


            }
        }
        return input;


    }

    public String getTranslation(String getSplitedCon, String getAnalyser, String getRoot) {
        String transout = "";
        String Analyser = getSplitedCon.replaceAll("<br/>", "");
        String splitVal[] = Analyser.split("[+]");


        for (int i = 0; i
                < splitVal.length; i++) {
            //////System.out.println(splitVal[i].trim());
            if (i == 0) {
                transout = splitVal[i].trim() + "- root\n";


            } else {
                transout += splitVal[i].trim() + "-" + rules(splitVal[i].trim(), getAnalyser, getRoot) + "\n";


            }
        }
        return transout;


    }

    public String rules(String val, String outputVal, String root) {
        String transOp = "";


        if (val.equals("ஆக")) {
            transOp = "defines thing which is in a state or has an attribute [or] thing which restrict a focused thing.";


        } else if (val.equals("போல") || val.equals("விட") || val.equals("மிக") || val.equals("காட்டிலும்")) {
            transOp = "defines thing used as the basis for expressing degree.";


        } else if (val.equals("உக்காக") || val.equals("உக்கு")) {
            transOp = "defines not directly related beneficiary [or] purpose of an event.";


        } else if (val.equals("வ்")) {
            if (outputVal.contains("Verb")) {
                transOp = "future Tense";


            } else {
                transOp = "connectivity Character";


            }
        } else if (val.equals("ஓடு")) {
            transOp = "defines thing not in focus which initiates an implicit event which is done in parallel.";


        } else if (val.equals("என்பது") || val.equals("எனப்படுவது")) {
            transOp = "defines equivalent concept.";


        } else if (val.equals("உடன்") || val.equals("உம்")) {
            transOp = "defines thing that is directly affected by an implicit event done in parallel.";


        } else if (val.equals("கொண்டு")) {
            transOp = "defines co occured event or state for a focused event or state.";


        } else if (val.equals("பொது")) {
            transOp = "defines a period of time during an event occur.";


        } else if (val.equals("இல்")) {
            if (outputVal.contains("இருந்து")) {
                transOp = "defines range between two things.";


            } else {
                transOp = "defines place of an event occurs [or] place in focus where an event affects.";


            }
        } else if (val.equals("இடம்")) {
            transOp = "indicates a location.";


        } else if (outputVal.contains("இருந்து")) {
            transOp = "defines place from an event occur.";


        } else if (val.equals("முதல்")) {
            transOp = "defines place of an event begins.";


        } else if (val.equals("வரை")) {
            transOp = "defines place of an event ends.";


        } else if (val.equals("ஆல்")) {
            transOp = "defines instrument to carry out an event.";


        } else if (val.equals("ஆ") || val.equals("அ")) {
            transOp = "defines thing in focus which is directly affected by an event.";


        } else if (val.equals("மீது")) {
            transOp = "defines place in focus where an event affects.";


        } else if (val.equals("இன்") || val.equals("உடைய")) {
            transOp = "defines concept of which a focused thing is a part [or] Possessor of a thing.";


        } else if (val.equals("கள்")) {
            transOp = "plurals";


        } else if (val.equals("உள்") || val.equals("உள்ளே")) {
            transOp = "in";


        } else if (val.equals("ஏன்")) {
            transOp = "first person Singular";


        } else if (val.equals("ந்து") || val.equals("ந்த்")) {
            transOp = "past tense";


        } else if (val.equals("ஓடு")) {
            transOp = " ";


        } else if (val.equals("மீது") || val.equals("மேல்")) {
            transOp = "on";


        } else if (val.equals("க்கிறு") || val.equals("கிறு") || val.equals("கிற்") || val.equals("க்கிற்")) {
            transOp = "present tense";


        } else if (val.equals("உம்")) {
            if (outputVal.contains("Verb")) {
                transOp = "future tense";


            } else if (outputVal.contains("Noun")) {
                transOp = "even [or] also ";


            }
        } else if (val.equals("ஐ")) {
            transOp = "defines direct object";


        } else if (val.equals("இ")) {
            transOp = "past tense";


        } else if (val.equals("ப்ப்")) {
            transOp = "future tense";


        } else if (val.equals("த்") || val.equals("த்த்") || val.equals("ள்") || val.equals("உ")) {
            transOp = "future tense";


        } else if (val.equals("ஆன்")) {
            transOp = "masculine singular";


        } else if (val.equals("ஆள்")) {
            transOp = "feminine singular";


        } else if (val.equals("க்") || val.equals("ய்")) {
            transOp = "connective character";


        } else if (val.equals("க்கள்")) {
            transOp = "plural";


        } else {
            transOp = "";


        }
        return transOp;

    }
}//End of analyser from agaraadhi
/* public ArrayList process(String queryString){
ArrayList arr = null;return arr;}

public String debugQueryExpansionOutput(boolean debugQE,boolean singleWdwithanalyser,boolean singleWdwithoutanalyser,boolean multiWd){return null;}
public String debugQueryTranslationOutput(boolean translation,boolean ActualQW,boolean expansion,boolean icliofCombination){return null;}
public String debugSearchOutput(boolean Search,boolean Rank){return null;}
public  Hashtable<String,Hashtable<String,String>> getOutput(){return null;}
public  Hashtable<String,Hashtable<String,Double>> getTime(){return null;}
public  Hashtable<String,Hashtable<String,String>> getCount(){return null;}
public Hashtable<String,Hashtable<String,String>>  getRank(){return null;}
public Hashtable<String,String> getVersion(){return null;}
public void finishedDebugTool(){}
}*/
