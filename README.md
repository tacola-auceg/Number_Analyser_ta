# Number_Analyser_ta
Tamil numerical word analyser and word to numeric converter

How to run and test the code:

Download the file and extract it in to folder named Conversion. The folder can be opened as a project using Netbeans.

Main file: Number_Frame.java in the default package. When run opens a frame where the input can be given and output can be verified.

Input: Tamil Numerical word for anlaysis. Given in the text field near the Word_Number label.

Output: Analysed output and the converted number. 

Analysed Output - Shown in the Text area below.

Converted Number - shown in the Text field below when the Number button is clicked.


Interface: Has buttons Analyse and Number. When the Analyse button is clicked it shows the analysed output of the input word and when the Number button is clicked it shows the converted Number if the word is a proper tamil numerical word.

Example: 
Input : பன்னிரண்டு

Output (Analused Output): 
	<பன்னிரண்டு>:
	ட் < Past Tense Marker & 800 > உ < Verbal Participle Suffix & 900 > 
	<ட்+உ>
	 count=3
	<unknown>

	<பத்துஇரண்டு>:
	ட் < Past Tense Marker & 800 > உ < Verbal Participle Suffix & 900 > 
	<ட்+உ>
	பத்து < charNumbers & 98 > இரண்டு < charNumbers & 98 > 
	<பத்து+இரண்டு>
	 count=2

Output (Converted number): 12 

Input : பன்னிரெண்டு

Output (Analused Output): 

Output (Converted number): 

(If the word cannot be analysed there will be no output. This implies the word is not a proper tamil word or is a colloquial word. Likewise you can check whether the entered word is a proper tamil numerical word or not.)
