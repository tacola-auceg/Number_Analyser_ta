package org.apache.nutch.analysis.unl.ta;

import java.io.PrintStream;
import java.util.Hashtable;

/**
 *
 * @author root
 */
public class Number_Conversion {

    PrintStream bwnew = null;
    Hashtable<String, Integer> index = new Hashtable<String, Integer>();
    String out = "";
    int tot = 0;
    int tot_nee=0;
    int tot_new = 0;
    int tot_n = 0;
    int tot_ne = 0;
    int res = 0;
    int tot_three = 0;
    int tot_first = 0;

    public Integer check_l(String out) {


        try {

            //bwnew = new PrintStream(new FileOutputStream("/root/NetBeansProjects/Conversion/number.txt"));
            index.put("ஒன்று", 1);
            index.put("ஒரு", 1);
            index.put("இரண்டு", 2);
            index.put("மூன்று", 3);
            index.put("நான்கு", 4);
            index.put("ஐந்து", 5);
            index.put("ஆறு", 6);
            index.put("ஏழு", 7);
            index.put("எட்டு", 8);
            index.put("ஒன்பது", 9);
            index.put("பத்து", 10);
            index.put("இருபது", 20);
            index.put("முப்பது", 30);
            index.put("நாற்பது", 40);
            index.put("ஐம்பது", 50);
            index.put("அறுபது", 60);
            index.put("எழுபது", 70);
            index.put("எண்பது",80);
            index.put("தொண்ணூறு", 90);
            index.put("நூறு", 100);
            index.put("இருநூறு",200);
            index.put("முன்னூறு",300);
            index.put("நானூறு",400);
            index.put("ஐநூறு",500);
            index.put("அறநூறு", 600);
            index.put("எழுநூறு",700);
            index.put("எண்ணூறு",800);
            index.put("தொள்ளாயிரம்",900);
            index.put("ஆயிரம்", 1000);
            index.put("லட்சம்",100000);
            index.put("கோடி",10000000);
           // index.put("ஒரு இலட்சம்",100000);
           
            
            

           // tot_ne = index.get(out.trim());


          //  System.out.println("Enter into number " + tot_ne);

            System.out.println("Value of out in check_l " + out);
            String[] st = out.split("\n");
            String ge = "";
            String temp = "";
            String temp1 = "";
            for (String tem : st) {
                if ((tem.contains("< charNumbers & 98 >")) || (tem.contains("< charNumbers & 98 >  < charNumbers & 98 >"))) {
                    ge = tem.replace("< charNumbers & 98 >", ",");
                    if (ge.contains("  ,  ,")) {
                        temp = ge.replace("  ,  ,", ",");
                    } else {
                        temp = ge;
                    }
                    System.out.println(".....> " + temp);
                    break;
                }
            }


            String gv = "";
            String[] numCon = temp.split(",");
            /* String g = "";
            for (int i = 0; i < numCon.length; i++) {
            g = numCon[i].toString();
            System.out.println("Value of g " + g);
            }*/
            //gv=numCon[0].toString();
            for (int k = 0; k < numCon.length; k++) {
                gv = numCon[0].toString();
                System.out.println("Value of gv " + gv);
                if (gv.contains("ஆயிரம்")) {
                    tot_ne = index.get(gv.trim());
                    System.out.println("Value of tot_new in one number " + tot_ne);
                    //break;
                } else {
                    tot_ne = index.get(gv.trim());
                    System.out.println("Enter into number " + tot_ne);
                }
            }

            String g = numCon[0];
            String g1 = numCon[1];
            String g2 = numCon[2];
            System.out.println("Value of g n all " + g + "  " + g1 + " " + g2);
            for (String num : numCon) {
                System.out.println("Value of num length " + num.length());
                if ((num.length() > 2)) {
                    System.out.println("Enter into if");
                    if ((!(g.contains("ஆயிரம்"))) && (!(g1.contains("ஆயிரம்"))) && (!(g2.contains("ஆயிரம்")))) {//&& (!(g2.contains("")))) {
                        System.out.println("Value of g2 "+g2);
                        //System.out.println("Value of g " + g);
                        //System.out.println("------:num>" + num.trim());
                        if((g2.contains("ஒன்று")) || (g2.contains("இரண்டு")) || (g2.contains("மூன்று")) ||(g2.contains("நான்கு")) ||(g2.contains("ஐந்து")) || (g2.contains("ஆறு")) || (g2.contains("ஏழு")) || (g2.contains("எட்டு")) || (g2.contains("ஒன்பது")) || (g2.contains("பத்து"))){
                        System.out.println("Enter into 1");
                        
                        tot = index.get(g.trim());
                        tot_nee=tot+index.get(g1.trim());
                        tot_ne = tot_nee + index.get(g2.trim());

                        System.out.println("Value of tot length in if else " + tot_ne);
                        }else {//if ((!(g2.contains("ஆயிரம்"))) || ((g2.contains("")))) {
                        System.out.println("Enter into 2");
                        //System.out.println("Value of g2 in  " + g2);
                        //System.out.println("------:num>" + num.trim());
                        tot = index.get(g.trim());
                        tot_ne = tot + index.get(g1.trim());
                        //System.out.println("Value of g2 " + g2);
                        //tot_ne = tot_nee + index.get(g2.trim());
                        System.out.println("Value of tot length in if " + tot_ne);
                        //bwnew.write(tot_ne);
                        //break;
                    }
                        //bwnew.write(tot_ne);
                        //break;
                    }/*else if (gv.contains("ஆயிரம்")) {
                    tot_new = index.get(gv.trim());
                    System.out.println("Value of tot_new in one number " + tot_new);
                    break;
                    }*/ else if ((g1.contains("ஆயிரம்"))) {
                        int to_m = 1;
                        //System.out.println("Enter into 3");
                        tot = index.get(g.trim());
                        to_m = tot * index.get(g1.trim());
                        tot_ne = to_m;
                        // System.out.println("Value of to_m " + res);
                        //bwnew.write(to_m);
                        //break;
                        // System.out.println("Value of tot "+tot);

                    } else if ((g2.contains("ஆயிரம்"))) {
                        /*(((!(g.contains("ஆயிரம்"))) && (!(g1.contains("ஆயிரம்")))) &&*/
                        //System.out.println("Enter into 4");
                        tot = index.get(g.trim());
                        tot_n = index.get(g1.trim());
                        int tot_add = tot + tot_n;
                        tot_three = tot_add * index.get(g2.trim());
                        tot_ne = tot_three;
                        // System.out.println("Value of to_m in three " + tot_three);
                        //bwnew.write(tot_three);
                        //break;
                        //System.out.println("Value of tot in three "+tot);
                        //
                    }

                }

//bwnew.close();
            }




        } catch (Exception e) {
            e.printStackTrace();
        }




        System.out.println("Value of tot length " + tot_ne);
        System.out.println("Value of to_m " + res);
        System.out.println("Value of to_m in three " + tot_three);

        int result = tot_ne;
        System.out.println("Value of out in number_conversion res" + result);
        return result;
    }

 /*   public Integer check_num(String out) {
        int val = 0;
        try {
            index.put("ஒன்று", 1);
            index.put("இரண்டு", 2);
            index.put("மூன்று", 3);
            index.put("நான்கு", 4);
            index.put("ஐந்து", 5);
            index.put("ஆறு", 6);
            index.put("ஏழு", 7);
            index.put("எட்டு", 8);
            index.put("ஒன்பது", 9);
            index.put("பத்து", 10);
            index.put("இருபது", 20);
            index.put("முப்பது", 30);
            index.put("நாற்பது", 40);
            index.put("நூறு", 100);
            index.put("ஐம்பது", 50);
            // System.out.println("Enter into number");

            System.out.println("Value of out " + out);

            val = index.get(out.trim());
            System.out.println("Value of val in check " + val);
            bwnew.write(val);
            // bwnew.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return val;
    }*/

    /* public String one_number(String out) {
    index.put("இருபது", 20);
    index.put("ஐந்து", 5);
    index.put("பத்து", 10);
    index.put("ஆறு", 6);
    index.put("ஒன்று", 1);
    index.put("நான்கு", 4);
    index.put("ஆயிரம்", 1000);
    index.put("மூன்று", 3);
    index.put("இரண்டு", 2);
    index.put("நூறு", 100);
    index.put("ஐம்பது", 50);
    index.put("ஒன்பது", 9);

    int val=0;
    int tot = 0;
    int tot_new = 0;
    int tot_n = 0;
    int tot_ne = 0;
    int to_m = 1;
    int tot_three = 0;
    int tot_first = 0;
    // System.out.println("Enter into number");

    System.out.println("Value of out in one_nmymner " + out);
    String[] st = out.split("\n");

    String temp = "";
    for (String tem : st) {
    if (tem.contains("< charNumbers & 98 >")) {
    temp = tem.replace("< charNumbers & 98 >", ",");
    System.out.println("--->" + temp);
    break;
    }
    }
    //String num="";
    String[] numCon = temp.split(",");
    String v=numCon[0].toString();
    String v1=numCon[1].toString();
    System.out.println("Value of num in the outside "+v);
    System.out.println("Value of num in the outside v1 "+v1);
    for (String num : numCon) {
    // System.out.println("Value of num length " + num.length());
    if (num.length() > 2) {
    if ((!(v.contains("ஆயிரம்"))) && (!(v1.contains("ஆயிரம்")))){// && (!(g2.contains("ஆயிரம்")))) {
    //System.out.println("Enter into 1");
    //System.out.println("Value of g " + g);
    //System.out.println("------:num>" + num.trim());
    tot = index.get(v.trim());
    tot_ne = tot + index.get(v1.trim());
    System.out.println("Value of tot length in one number" + tot_ne);
    bwnew.write(tot);
    break;
    } else if (v.contains("ஆயிரம்")) {
    tot_new = index.get(v.trim());
    System.out.println("Value of tot_new in one number " + tot_new);
    break;
    } else if ((v1.contains("ஆயிரம்"))) {
    //System.out.println("Enter into 3");
    tot = index.get(v.trim());
    to_m = tot * index.get(v1.trim());
    System.out.println("Value of to_m in one number " + to_m);
    bwnew.write(to_m);
    break;
    // System.out.println("Value of tot "+tot);

    }
    }
    break;
    //bwnew.close();
    }
    return out;
    }*/
}
