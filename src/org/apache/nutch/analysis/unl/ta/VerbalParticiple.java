package org.apache.nutch.analysis.unl.ta;
//import org.apache.nutch.unl.utils.*;
import java.util.*;

public class VerbalParticiple {

    static String x = "";

    public static boolean check(Stack s) {
        System.out.println("Enter into VerbalParticiple check method");
        StringBuffer out_put = new StringBuffer();
        //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "VP");
        byte[] topElmt = ((Entry) s.peek()).getPart();
        byte[] oldTopElmt = topElmt;
        byte[] topElmt1 = null;
        byte[] topElmt2 = null;
        byte[] topElmt3 = null;
        byte[] check_value = null;
        String input2 = "";
        String tag_str = "";
        int itra = 1;
        int len = 0;
        String[] spt;
        // n_thu
        if (ByteMeth.endsWith(topElmt, Constant.n_thu)) {
            System.out.println("enter 1");
            //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "n_thu");
            s.pop();
            s.push(new Entry(Constant.u, Tag.VerbalParticipleSuffix));
            s.push(new Entry(Constant.n_th, Tag.PastTenseMarker));
            topElmt = ByteMeth.subArray(topElmt, 0,
                    topElmt.length - Constant.n_thu.length);
            if (ByteMeth.contains(topElmt, Constant.va)
                    || ByteMeth.contains(topElmt, Constant.tha)
                    || ByteMeth.contains(topElmt, Constant.se)) {
                topElmt = ByteMeth.replace(topElmt, Constant.A, 1);
            }
            s.push(new Entry(topElmt, -1, oldTopElmt));
            return true;
        }
        // ththu
        if (ByteMeth.endsWith(topElmt, Constant.ththu)) {
            System.out.println("enter 2");
            //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "ththu");
            s.pop();
            s.push(new Entry(Constant.u, Tag.VerbalParticipleSuffix));
            s.push(new Entry(Constant.thth, Tag.PastTenseMarker));
            topElmt = ByteMeth.subArray(topElmt, 0,
                    topElmt.length - Constant.ththu.length);
            System.out.println("Value of thu in VP " + UnicodeConverter.revert(topElmt));

            if (ByteMeth.contains(topElmt, Constant.Ayira)) {
                if ((topElmt.length > 5) && (topElmt.length <= 11)) {
                    topElmt1 = ByteMeth.subArray(topElmt, 0, topElmt.length - Constant.Ayira.length);
                    System.out.println("vajdouiay "+UnicodeConverter.revert(topElmt1));
                    if(ByteMeth.contains(topElmt1, Constant.thool)){
                        topElmt = ByteMeth.addArray(Constant.Ayira, Constant.m);
                 //   s.push(new Entry(topElmt, Tag.cNumbers));
                    topElmt2 = ByteMeth.addArray(topElmt1, topElmt);
                    System.out.println("vajdo "+UnicodeConverter.revert(topElmt));
                    s.push(new Entry(topElmt2, Tag.cNumbers));
                    }else{
                    topElmt = ByteMeth.addArray(Constant.Ayira, Constant.m);
                    s.push(new Entry(topElmt, Tag.cNumbers));
                    topElmt2 = ByteMeth.addArray(topElmt1, Constant.u);
                    s.push(new Entry(topElmt2, Tag.cNumbers));
                    }
                } else if (topElmt.length > 11) {
                    System.out.println("Check_value " + UnicodeConverter.revert(topElmt));
                    if (ByteMeth.contains(topElmt, Constant.Ayira)) {
                        topElmt1 = ByteMeth.subArray(topElmt, 0, topElmt.length - Constant.Ayira.length);
                        topElmt = ByteMeth.addArray(Constant.Ayira, Constant.m);
                        s.push(new Entry(topElmt, Tag.cNumbers));
                        topElmt2 = ByteMeth.addArray(topElmt1, Constant.u);
                        String v = ADictionary.handle_double(topElmt2);
                        byte[] tot = UnicodeConverter.convert(v);
                        s.push(new Entry(tot, Tag.cNumbers));

                        //out_put.append(v);



                        //String r=Analyser.analyseF(v, true);
                        //topElmt3=UnicodeConverter.convert(v);
                        // System.out.println("Value opf vsafa " + v);
                        //System.out.println("Value opf vsafa " + UnicodeConverter.revert(topElmt2));
                    }

                } else if (topElmt.length <= 5) {
                    topElmt = ByteMeth.addArray(topElmt, Constant.m);
                    System.out.println("Value of thu in VP if  else" + UnicodeConverter.revert(topElmt));
                    s.push(new Entry(topElmt, -1, oldTopElmt));
                }
                return true;
            }

            if (ByteMeth.contains(topElmt, Constant.latcha)) {
                if ((topElmt.length > 5) && (topElmt.length <= 10)) {
                    topElmt1 = ByteMeth.subArray(topElmt, 0, topElmt.length - Constant.latcha.length);
                    topElmt = ByteMeth.addArray(Constant.latcha, Constant.m);
                    s.push(new Entry(topElmt, Tag.cNumbers));
                    topElmt2 = ByteMeth.addArray(topElmt1, Constant.u);
                    s.push(new Entry(topElmt2, Tag.cNumbers));
                } else if (topElmt.length > 10) {
                    System.out.println("Check_value " + UnicodeConverter.revert(topElmt));
                    if (ByteMeth.contains(topElmt, Constant.latcha)) {
                        topElmt1 = ByteMeth.subArray(topElmt, 0, topElmt.length - Constant.latcha.length);
                        topElmt = ByteMeth.addArray(Constant.latcha, Constant.m);
                        s.push(new Entry(topElmt, Tag.cNumbers));
                        topElmt2 = ByteMeth.addArray(topElmt1, Constant.u);
                        String v = ADictionary.handle_double(topElmt2);
                        byte[] tot = UnicodeConverter.convert(v);
                        s.push(new Entry(tot, Tag.cNumbers));

                        //out_put.append(v);



                        //String r=Analyser.analyseF(v, true);
                        //topElmt3=UnicodeConverter.convert(v);
                        // System.out.println("Value opf vsafa " + v);
                        //System.out.println("Value opf vsafa " + UnicodeConverter.revert(topElmt2));
                    }

                } else if (topElmt.length <= 5) {
                    topElmt = ByteMeth.addArray(topElmt, Constant.m);
                    System.out.println("Value of thu in VP if  else" + UnicodeConverter.revert(topElmt));
                    s.push(new Entry(topElmt, -1, oldTopElmt));
                }
                return true;
            }
            if (ByteMeth.contains(topElmt, Constant.va)
                    || ByteMeth.contains(topElmt, Constant.tha)
                    || ByteMeth.contains(topElmt, Constant.se)) {
                System.out.println("enter 4");
                topElmt = ByteMeth.replace(topElmt, Constant.A, 1);
                // System.out.println("Value of thu in VP if "+UnicodeConverter.revert(topElmt));
            }
            s.push(new Entry(topElmt, -1, oldTopElmt));
            return true;
        }
        // thu
        if (ByteMeth.endsWith(topElmt, Constant.thu)
                && !ByteMeth.endsWith(topElmt, Constant.athu)) {
            System.out.println("enter 5");
            //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "thu");
            s.pop();
            s.push(new Entry(Constant.u, Tag.VerbalParticipleSuffix));
            s.push(new Entry(Constant.th, Tag.PastTenseMarker));
            topElmt = ByteMeth.subArray(topElmt, 0,
                    topElmt.length - Constant.thu.length);

            if (ByteMeth.contains(topElmt, Constant.va)
                    || ByteMeth.contains(topElmt, Constant.tha)
                    || ByteMeth.contains(topElmt, Constant.se)) {

                topElmt = ByteMeth.replace(topElmt, Constant.A, 1);
            }
            s.push(new Entry(topElmt, -1, oldTopElmt));
            return true;
        }
        // tu

        if (ByteMeth.endsWith(topElmt, Constant.tu)
                && !ByteMeth.endsWith(topElmt, Constant.patu)
                && (!ByteMeth.endsWith(topElmt, Constant.pattu)
                || ByteMeth.isEqual(topElmt, Constant.Itupattu))) {
            System.out.println("enter 6");
            //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "tu");
            s.pop();
            s.push(new Entry(Constant.u, Tag.VerbalParticipleSuffix));
            s.push(new Entry(Constant.t, Tag.PastTenseMarker));
            topElmt = ByteMeth.subArray(topElmt, 0,
                    topElmt.length - Constant.tu.length);

            if (!(ByteMeth.contains(topElmt, Constant.et))) {
                if (ByteMeth.contains(topElmt, Constant.kaN)) {
                    topElmt = ByteMeth.replace(topElmt, Constant.AN, 2);
                } else if (ByteMeth.endsWith(topElmt, Constant.N)
                        && !ByteMeth.isEqual(topElmt, Constant.uN)
                        && !ByteMeth.isEqual(topElmt, Constant.pUN)) {
                    topElmt = ByteMeth.replace(topElmt, Constant.L, 1);
                } else if (ByteMeth.isEqual(topElmt, Constant.kEt)) {
                    topElmt = ByteMeth.replace(topElmt, Constant.L, 1);
                } else if (ByteMeth.endsWith(topElmt, Constant.t)) {
                    topElmt = ByteMeth.addArray(topElmt, Constant.u);
                }
            }

            s.push(new Entry(topElmt, -1, oldTopElmt));
            return true;
        }
        // Ru
        if (ByteMeth.endsWith(topElmt, Constant.Ru)
                && !ByteMeth.endsWith(topElmt, Constant.kkinRu)
                && !ByteMeth.endsWith(topElmt, Constant.kinRu)
                && !ByteMeth.endsWith(topElmt, Constant.kkiRu)) {
            System.out.println("enter 7");
            //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "Ru");
            s.pop();
            s.push(new Entry(Constant.u, Tag.VerbalParticipleSuffix));
            s.push(new Entry(Constant.R, Tag.PastTenseMarker));
            topElmt = ByteMeth.subArray(topElmt, 0,
                    topElmt.length - Constant.Ru.length);
            if (ByteMeth.isEqual(topElmt, Constant.kaR)
                    || ByteMeth.isEqual(topElmt, Constant.viR)) {
                topElmt = ByteMeth.replace(topElmt, Constant.l, 1);
            }
            if (ByteMeth.endsWith(topElmt, Constant.R)) {
                topElmt = ByteMeth.addArray(topElmt, Constant.u);
            }

            // senRaan, enRaan,iinRaan - pbm
            if (ByteMeth.contains(topElmt, Constant.en)
                    && !ByteMeth.contains(topElmt, Constant.sen)
                    && !ByteMeth.contains(topElmt, Constant.ven)
                    && !ByteMeth.contains(topElmt, Constant.men)) {
                s.push(new Entry(topElmt, -1, oldTopElmt));
                return true;
            }

            if (ByteMeth.endsWith(topElmt, Constant.n)
                    && !ByteMeth.isEqual(topElmt, Constant.thin)
                    && !ByteMeth.isEqual(topElmt, Constant.iin)) {
                topElmt = ByteMeth.replace(topElmt, Constant.l, 1);
            }

            s.push(new Entry(topElmt, -1, oldTopElmt));
            return true;
        }
        // y
        if (ByteMeth.endsWith(topElmt, Constant.y)) {
            System.out.println("enter 8");
            //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "y");
            s.pop();
            s.push(new Entry(Constant.y, Tag.VerbalParticipleSuffix));
            topElmt = ByteMeth.subArray(topElmt, 0,
                    topElmt.length - Constant.y.length);
            // doubt
            if (ByteMeth.endsWith(topElmt, Constant.i)) {
                topElmt = ByteMeth.replace(topElmt, Constant.u, 1);
            }
            s.push(new Entry(topElmt, -1, oldTopElmt));
            return true;
        }
        return false;
    }
}
