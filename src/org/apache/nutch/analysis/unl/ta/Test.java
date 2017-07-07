package org.apache.nutch.analysis.unl.ta;
import java.util.*;
import java.io.*;

public class Test{



public static void main(String args[])
{
/*	try
	{
		BufferedReader input =  new BufferedReader(new FileReader("./input.txt"));
	
	        String line = null;
	
		while (( line = input.readLine()) != null&&line!=""){
		String analysed =org.apache.nutch.analysis.unl.ta.Analyser.analyseF(line.trim(),true);
	//	System.out.println(analysed);
		}
	
	}
	catch(Exception e)
	{
	}
*/		
	//System.out.println("output is kkk:"+analysed);
	String analysed1 =org.apache.nutch.analysis.unl.ta.Analyser.analyseF("ஆய்வாளராகிய",true);
		
	System.out.println("output is kkk:"+analysed1);
	/*String analyse[]=analysed.split("#");
			if(analyse.length!=0)
			{
				//System.out.println(analyse[0]+":");
				for(int i=1;i<analyse.length;i++)
				{
					//System.out.println(analyse[i]);
				}
				////System.out.println(analysed);
				//System.out.println("----"+"\n");	
				////System.out.println(n++);
			}
			else
			{
				//System.out.println(analysed+": Unknown");	
			}
*/	
/*try {
	ArrayList list=new ArrayList();
	BufferedWriter input1 =  new BufferedWriter(new FileWriter("/root/eyya.txt"));
	BufferedReader input =  new BufferedReader(new FileReader("/root/analyzereyya.txt"));
	int n=0;	
        String line = null;
	int num=0;
	long st=0;//System.currentTimeMillis();
	st=System.currentTimeMillis();	 
        while (( line = input.readLine()) != null&&line!=""){
	//	list.add(line);
	//}
	
	//for(int i=0;i<list.size();i++)
	//{
		////System.out.println("docid"+i);
		//BufferedReader breader=new BufferedReader(new FileReader("./resource/unl/"+list.get(i)));
		//String str=null;
		//while((str=breader.readLine())!=null)
		//{
		//	String words[]=str.split(" ");
		//	for(int j=0;j<words.length;j++)
		//	{
				String line1=line.trim();
				
						 	

				
				//System.out.println(line1);
			//if(line1.length()>2)
			//{
				String analysed =org.apache.nutch.analysis.unl.ta.Analyser.analyseF(line1,true);
			
			////System.out.println(analysed);
//			Thread.sleep(500);
				num++;
				String analyse[]=analysed.split("#");
				if(analyse.length!=0)
				{
					input1.write(analyse[0]+":"+"\n");
					for(int k=1;k<analyse.length;k++)
					{
						input1.write(analyse[k]+"\n");
					}
				////System.out.println(analysed);
				input1.write("----"+"\n");	
				////System.out.println(n++);
				}
				else
				{
					input1.write(analysed+": Unknown"+"\n");	
				}
			//}
		
		//}

	}
			long end=System.currentTimeMillis();		//	input1.write("Time taken by OptimizedAnalyzer in MilliSecond	:"+diff+"\n");	
			long diff=end-st;	
			input1.write("Total time taken is	:"+diff+"\n");
			input1.close();
			input.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();

		}*/
	/*	try
		{
		long start=System.currentTimeMillis();
		File f=new File("./resource/unl/SentenceExtraction/Output/content");
		File files[]=f.listFiles();
		//BufferedWriter input1 =  new BufferedWriter(new FileWriter("/root/bharath/new1.txt"));
		int num=0;
			for(int i=0;i<files.length;i++)
			{
				BufferedReader br=new BufferedReader(new FileReader(files[i]));
				//BufferedWriter bw=new BufferedWriter(new File)
				String line="";
				while((line=br.readLine())!=null)
				{
					String wrds[]=line.split(" ");
					for(int j=0;j<wrds.length;j++)
					{
					num++;
					if(wrds[j].endsWith("."))
					wrds[j].replace(".","");
					////System.out.println(wrds[j]);
					//input1.write("input wrd is "+wrds[j]+"\n");
					//System.out.println("input word is ="+wrds[j]);
					String analysed =org.apache.nutch.analysis.unl.ta.Analyser.analyseF(wrds[j],true);
					////System.out.println(analysed);
			
					/*String analyse[]=analysed.split("#");
					if(analyse.length!=0)
					{
						input1.write(analyse[0]+":"+"\n");
						for(int k=1;k<analyse.length;k++)
						{
						input1.write(analyse[k]+"\n");
						
						}
						////System.out.println(analysed);
						input1.write("----"+"\n");	
						//////System.out.println(n++);
					}
					else
					{
						input1.write(analysed+": Unknown"+"\n");	
					}

					}
				}
			
			}
			long end=System.currentTimeMillis();
			long diff=end-start;
		System.out.println("Total time taken is="+diff);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}*/
}


}


