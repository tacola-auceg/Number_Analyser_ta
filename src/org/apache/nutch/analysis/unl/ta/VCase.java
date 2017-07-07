package org.apache.nutch.analysis.unl.ta;

//import org.apache.nutch.unl.utils.*;
import java.util.Stack;

public class VCase {

    static ByteMeth ByteMeth;
    static String x = "";

    // boolean pronominal has been added for clash with azakaanathu - thu: pronominal, athu - case
    public static boolean check(Stack s, boolean pronominal) {
        System.out.println("Enter into VCase check method");
        byte[] topElmt = ((Entry) s.peek()).getPart();
        byte[] oldTopElmt = topElmt;


        // Al
        if (ByteMeth.endsWith(topElmt, Constant.Al)
                && !ByteMeth.endsWith(topElmt, Constant.AnAl)
                && !ByteMeth.endsWith(topElmt, Constant.vittAl)
                && !ByteMeth.endsWith(topElmt, Constant.AthathAl)
                && !ByteMeth.endsWith(topElmt, Constant.athAl)) {
            ////System.out.println("Al true");
            //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "Al");
            s.pop();
            s.push(new Entry(Constant.Al, Tag.InstrumentalCase));
            topElmt = ByteMeth.subArray(topElmt, 0,
                    topElmt.length - Constant.Al.length);
            s.push(new Entry(topElmt, -1, oldTopElmt));
            Sandhi.check(s);
            return true;
        }

        // il
        if (ByteMeth.endsWith(topElmt, Constant.il) && !ByteMeth.endsWith(topElmt, Constant.Avitil)
                && !ByteMeth.endsWith(topElmt, Constant.kaiyil)) {

            ////System.out.println("il coming");
            //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "il");
            s.pop();
            s.push(new Entry(Constant.il, Tag.LocativeCase));
            topElmt = ByteMeth.subArray(topElmt, 0,
                    topElmt.length - Constant.il.length);
            s.push(new Entry(topElmt, -1, oldTopElmt));
            Sandhi.check(s);
            return true;
        }

        // kaN
        if (ByteMeth.endsWith(topElmt, Constant.kaN)) {
            ////System.out.println("Ends wid KaN");
            //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "kaN");
            s.pop();
            s.push(new Entry(Constant.kaN, Tag.LocativeCase));
            topElmt = ByteMeth.subArray(topElmt, 0,
                    topElmt.length - Constant.kaN.length);
            s.push(new Entry(topElmt, -1, oldTopElmt));

            Sandhi.check(s);

            return true;
        }

        // itam
        if (ByteMeth.endsWith(topElmt, Constant.itam)) {
            ////System.out.println("itam true");
            //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "itam");
            s.pop();
            s.push(new Entry(Constant.itam, Tag.LocativeCase));
            topElmt = ByteMeth.subArray(topElmt, 0,
                    topElmt.length - Constant.itam.length);
            s.push(new Entry(topElmt, -1, oldTopElmt));
            Sandhi.check(s);
            return true;
        }

        // in
        if (ByteMeth.endsWith(topElmt, Constant.in)) {
            ////System.out.println("in true");
            //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "in");
            s.pop();
            s.push(new Entry(Constant.in, Tag.GenitiveCase));
            topElmt = ByteMeth.subArray(topElmt, 0,
                    topElmt.length - Constant.in.length);
            s.push(new Entry(topElmt, -1, oldTopElmt));
            Sandhi.check(s);
            return true;
        }

        // athu
        if (pronominal) {

            if (ByteMeth.endsWith(topElmt, Constant.athu)
                    && !ByteMeth.endsWith(topElmt, Constant.Avathu)
                    && !ByteMeth.endsWith(topElmt,
                    ByteMeth.addArray(Constant.Ana, Constant.thu))
                    && !ByteMeth.endsWith(topElmt,
                    ByteMeth.addArray(Constant.iya, Constant.thu))
                    && !ByteMeth.endsWith(topElmt,
                    ByteMeth.addArray(Constant.uLLa, Constant.thu))
                    && !ByteMeth.endsWith(topElmt,
                    ByteMeth.addArray(Constant.aRRa, Constant.thu))) {
                ////System.out.println("athu true");
                //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "athu");
                s.pop();
                s.push(new Entry(Constant.athu, Tag.GenitiveCase));
                topElmt = ByteMeth.subArray(topElmt, 0,
                        topElmt.length - Constant.athu.length);
                s.push(new Entry(topElmt, -1, oldTopElmt));
                Sandhi.check(s);
                return true;
            }
        } else {
            if (ByteMeth.endsWith(topElmt, Constant.athu)) {
                ////System.out.println("athu true");
                //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "athu");
                s.pop();
                s.push(new Entry(Constant.athu, Tag.GenitiveCase));
                topElmt = ByteMeth.subArray(topElmt, 0,
                        topElmt.length - Constant.athu.length);
                s.push(new Entry(topElmt, -1, oldTopElmt));
                Sandhi.check(s);
                return true;
            }
        }
        // athaRku
        if (ByteMeth.endsWith(topElmt, Constant.athaRku)) {
            ////System.out.println("atharku true");
            //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "athaRku");
            s.pop();
            s.push(new Entry(Constant.athaRku, Tag.DativeCase));
            topElmt = ByteMeth.subArray(topElmt, 0,
                    topElmt.length - Constant.athaRku.length);
            s.push(new Entry(topElmt, -1, oldTopElmt));
            //	Sandhi.check(s);
            return true;
        }

        // iRku
        if (ByteMeth.endsWith(topElmt, Constant.iRku)) {
            ////System.out.println("irku true");
            //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "iRku");
            s.pop();
            s.push(new Entry(Constant.ku, Tag.DativeCase));
            s.push(new Entry(Constant.in, Tag.Euphonic));
            topElmt = ByteMeth.subArray(topElmt, 0,
                    topElmt.length - Constant.iRku.length);
            s.push(new Entry(topElmt, -1, oldTopElmt));
            Sandhi.check(s);
            return true;
        }

        // aRku
        if (ByteMeth.endsWith(topElmt, Constant.aRku)) {
            ////System.out.println("arku true");
            //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "aRku");
            s.pop();
            s.push(new Entry(Constant.ku, Tag.DativeCase));
            s.push(new Entry(Constant.an, Tag.Euphonic));
            topElmt = ByteMeth.subArray(topElmt, 0,
                    topElmt.length - Constant.aRku.length);
            s.push(new Entry(topElmt, -1, oldTopElmt));
            Sandhi.check(s);
            return true;
        }

        // akku
        if (ByteMeth.endsWith(topElmt, Constant.akku)) {
            ////System.out.println("akku true");
            topElmt = ByteMeth.subArray(topElmt, 0,
                    topElmt.length - Constant.akku.length);
            if (BooleanMethod.is_akku_Pronoun(topElmt)) {
                //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "akku");
                s.pop();
                s.push(new Entry(Constant.akku, Tag.DativeCase));
                s.push(new Entry(topElmt, -1, oldTopElmt));
                return true;
            }
        }

        // ukku
        if (ByteMeth.endsWith(topElmt, Constant.ukku)) {
            ////System.out.println("ukku true");
            //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "ukku");
            s.pop();
            s.push(new Entry(Constant.ukku, Tag.DativeCase));
            topElmt = ByteMeth.subArray(topElmt, 0,
                    topElmt.length - Constant.ukku.length);
            s.push(new Entry(topElmt, -1, oldTopElmt));
            Sandhi.check(s);
            return true;
        }

        // kku
        if (ByteMeth.endsWith(topElmt, Constant.kku)) {
            ////System.out.println("Vcase க்கு");
            //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "kku");
            s.pop();
            s.push(new Entry(Constant.kku, Tag.DativeCase));
            topElmt = ByteMeth.subArray(topElmt, 0,
                    topElmt.length - Constant.kku.length);
            s.push(new Entry(topElmt, -1, oldTopElmt));
            return true;

        }
// i command this if block bcoz ai does not come with verb.


        // ai
        if (ByteMeth.endsWith(topElmt, Constant.ai)
                && !ByteMeth.endsWith(topElmt,
                ByteMeth.addArray(Constant.Ana, Constant.vai))
                && !ByteMeth.endsWith(topElmt,
                ByteMeth.addArray(Constant.iya, Constant.vai))
                && !ByteMeth.endsWith(topElmt,
                ByteMeth.addArray(Constant.uLLa, Constant.vai))
                && !ByteMeth.endsWith(topElmt,
                ByteMeth.addArray(Constant.aRRa, Constant.vai))) {
            ////System.out.println("ai true");
            //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "ai");
            s.pop();
            s.push(new Entry(Constant.ai, Tag.AccusativeCase));
            topElmt = ByteMeth.subArray(topElmt, 0,
                    topElmt.length - Constant.ai.length);
            s.push(new Entry(topElmt, -1, oldTopElmt));
            Sandhi.check(s);
            return true;
        }

        // utan
        if (ByteMeth.endsWith(topElmt, Constant.utan)) {
            ////System.out.println("utan true");
            //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "utan");
            s.pop();
            s.push(new Entry(Constant.utan, Tag.AssociativeCase));
            topElmt = ByteMeth.subArray(topElmt, 0,
                    topElmt.length - Constant.utan.length);
            s.push(new Entry(topElmt, -1, oldTopElmt));
            Sandhi.check(s);
            return true;
        }

        // Otu
        ////System.out.println("before Odu.."+UnicodeConverter.revert(topElmt));
        if (ByteMeth.endsWith(topElmt, Constant.Otu)) {
            ////System.out.println("Otu true");
            //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "Otu");
            s.pop();
            s.push(new Entry(Constant.Otu, Tag.AssociativeCase));
            topElmt = ByteMeth.subArray(topElmt, 0,
                    topElmt.length - Constant.Otu.length);
            s.push(new Entry(topElmt, -1, oldTopElmt));
            Sandhi.check(s);
            return true;
        }
        ////System.out.println("Vcase false");
        return false;
    }
}
