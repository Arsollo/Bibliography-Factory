//----------------------------------------------------
//Assignment #3
//Written by: Arsany Fahmy (40157267)
//----------------------------------------------------

//-----------------------------------------------------------
//Important note: the use of BufferedReader has been
//implemented instead of Scanner because of an unexpected
//problem that occurred when loading files (the scanner would
//stop reading a file unexpectedly without any reason)
//-----------------------------------------------------------


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This class reads a series of 10 bibliography files, checks if they are valid and create 3 files
 * containing the following created reference styles: IEE, ACM and NJ
 * @author Arsany Fahmy
 */
public class BibliographyFactory 
{
	static File[] files = new File[10]; //10 files
	static BufferedReader[] buff = new BufferedReader[10]; //10 scanners to read 10 files
	static BufferedReader[] buff2 = new BufferedReader[10]; //10 more scanners for IEE usage
	static BufferedReader[] buff3 = new BufferedReader[10]; //10 more scanners for ACM usage
	static BufferedReader[] buff4 = new BufferedReader[10]; //10 more scanners for NJ usage
	static String line; //to display a line of a file
	static File[] outputFiles = new File[30]; //30 output files
	static PrintWriter[] pw = new PrintWriter[30]; //a printWriter for each output file
	static int numOfInvalidFiles = 0; //to keep track of invalid files

	/**
	 * Main method were the Bibliography is created for each file
	 * @param Keyboard Scanner that takes input from the keyboard
	 */
	public static void main(String[] args) 
	{
		//Scanner Keyboard
		Scanner Keyboard = new Scanner(System.in);
		
		//welcome message
		System.out.println("Welcome to BibliographyFactory!");
		System.out.println(" ");
		
		//Creating the 10 files
		for(int i = 0; i < 10; i++)
		{
			files[i] = new File("Latex" + (i+1) + ".bib");
		}
		
		//Creating 10 bufferedReader (4 times)
		for(int i = 0; i < 10; i++)
		{
			//if we want to display the content of all 10 files:
			/*
			try 
			{
				buff[i] = new BufferedReader(new FileReader(files[i]));
		    } catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			 System.out.println("");
			 System.out.println("**************************************************");
			 System.out.println("Latex" + (i+1) + ".bib: ");
			 System.out.println("");
			 try {
				while(buff[i].readLine() != null)
				 {
				 	try 
				 	{
						System.out.println(buff[i].readLine());
					} catch (IOException e)
				 	{
						e.printStackTrace();
					}
				 }
			} catch (IOException e) 
			 {
				e.printStackTrace();
			}
			*/
			try
			{
				buff[i] = new BufferedReader(new FileReader(files[i]));
				buff2[i] = new BufferedReader(new FileReader(files[i]));
				buff3[i] = new BufferedReader(new FileReader(files[i]));
				buff4[i] = new BufferedReader(new FileReader(files[i]));
			} catch (FileNotFoundException e)
			{
				//if file not found: error message and closing files and terminating program
				System.out.println("Could not open file " + "Latex" + (i+1) + ".bib for"
						+ " reading.\n Please check if file exists!! Program will terminate after"
						+ "closing any opened files.");
				for (int j = 0; j < i; j++)
				{
					try {
						buff[j].close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				System.exit(0);
			}
		}
		
		//Creating 30 output files
		/* outputFiles[0-9]: IEE reference Files (10)
		 * outputFiles[10-19]: ACM reference Files (10)
		 * outputFiles[20-29]: NJ reference Files (10)
		 */
		for (int i = 0; i < 30; i++)
		{
			//Creating 10 IEEEi files
			if (i < 10)
			{
				outputFiles[i] = new File("IEEE" + (i+1) + ".json");
				try {
					pw[i] = new PrintWriter(outputFiles[i]);
				} catch (FileNotFoundException e) {
					System.out.println("Error: cannot open or create The following"
							+ " file: " + outputFiles[i] + ". Deleting all other files!");
					for (int j = 0; j < i; j++)
					{
						outputFiles[j].delete();
					}
					System.exit(0);
				}
				
			}
			//Creating 10 ACMi files
			if (i >= 10 && i < 20)
			{
				int fileNumber = i-9;
				outputFiles[i] = new File("ACM" + (fileNumber) + ".json");
				try {
					pw[i] = new PrintWriter(outputFiles[i]);
				} catch (FileNotFoundException e) {
					System.out.println("Error: cannot open or create The following"
							+ " file: " + outputFiles[i] + ". Deleting all other files!");
					for (int j = 0; j < i; j++)
					{
						outputFiles[j].delete();
					}
					System.exit(0);
				}
			}
			//Creating 10 Nii files
			if (i >= 20 && i < 30)
			{
				int fileNumber = i-19;
				outputFiles[i] = new File("NJ" + (fileNumber) + ".json");
				try {
					pw[i] = new PrintWriter(outputFiles[i]);
				} catch (FileNotFoundException e) {
					System.out.println("Error: cannot open or create The following"
							+ " file: " + outputFiles[i] + ". Deleting all other files!");
					for (int j = 0; j < i; j++)
					{
						outputFiles[j].delete();
					}
					System.exit(0);
				}
			}
			
		}
		//To check if a file if valid or not and deleting its corresponding reference files if not valid
		processFilesForValidation();
		
		//Summary of the execution
		System.out.println(" ");
		System.out.println("########################################################################################################################################");
		System.out.println(" A total of " + numOfInvalidFiles + " files were invalid, and could nnot be processed."
				+ " All other " + (10-numOfInvalidFiles) + " valid files have been created.");
		
		//Asking user if they want to review a certain file and displaying the content of that file
		boolean secondChance = false; //user can have a second chance if first attempt was invalid
		do
		{
			try
			{
				System.out.print("Please enter the name of one of the files that you need to review: ");
				String review = Keyboard.nextLine();
				BufferedReader reviewReader = new BufferedReader(new FileReader(review));
				System.out.println("Here are the contents of the successfully created Jason file: " + review );
				System.out.println(" ");
				
				line = reviewReader.readLine() ;
				while (line != null)
				{
					System.out.println(line);
					line = reviewReader.readLine() ;
				}
				
				//Final goodbye
				Keyboard.close();
				reviewReader.close();
				System.out.println(" ");
				System.out.println("Goodbye! Hope you have enjoyed creating the needed files using BibiographyFactory.");
				System.exit(0);
				
			}catch(FileNotFoundException e)
			{
				if(secondChance == false) //if first time 
				{
					secondChance = true; //give a second chance
					System.out.println("Could not open input file. File does not exist; possibly it could not"
							+ " be created!");
					System.out.println(" ");
					System.out.println("However, you will be allowed a second chance to enter another file name.");
				}
				else //if second chance already used
				{
					System.out.println(" ");
					System.out.println(" Once again, could not open input file. File does not exist; possibly it could not"
							+ " be created!");
					System.out.println("Sorry! I am unable to display your desired files! Program will exit!");
					System.exit(0);
				}
				
			} catch (IOException e)
			{
				
				e.printStackTrace();
			}
			
		}while (secondChance == true);
		
		//closing Keyboard scanner
		Keyboard.close();	
	}
	
	/**
	 * This method process each file to check if it's a valid file or not and deletes its corresponding output files (reference files) if not valid
	 * @param isValid To check if a file is valid or not
	 * @param count To count how many articles in the file
	 * @param missingField To display to user which field makes the file invalid
	 */
	private static void processFilesForValidation() 
	{
		boolean isValid = true;
		int count = 0; //to count how many articles in a file
		String missingField = "string"; //where the missing field of the invalid file is going to be stored
		
		//Are the files valid?
		for (int i = 0; i < 10; i++) //to go through the 10 files
		{	
			try
			{
				count = 0;
				isValid = true;
				try {
					while((line = buff[i].readLine()) != null)
					{
						if (line.contains("{}"))
						{
							missingField = line.substring(0,(line.indexOf('{')-1));
							isValid = false;
						}
						if (line.contains("@ARTICLE"))
							count++;
					}
				} catch (IOException e)
				{
					e.printStackTrace();
				}
				
				//Dealing with valid files
				if (isValid == true)
				{
					//storing the IEE references created in the corresponding created files
					IEE(i, count);
					ACM(i, count);
					NJ(i, count);
				}
				//dealing with invalid files
				else if (isValid == false)
				{
					numOfInvalidFiles ++;
					throw new FileInvalidException();
				}
				
			}catch(FileInvalidException e)
			{
				//displaying error message
				System.out.println("Error: Detected empty field!");
				System.out.println("=============================");
				System.out.println(" ");
				System.out.println("Problem detected with input file: " + files[i]);
				System.out.println("File is invalid: Field \"" + missingField + "\" is empty. Processing"
						+ " of this file has been stoped at this point. Other empty fields may be present as well!");
				System.out.println(" ");
				
				//deleting the invalid output files
				outputFiles[i].delete(); //deleting IEE reference File
				outputFiles[i+10].delete(); //deleting ACM reference File
				outputFiles[i+20].delete(); //deleting NJ reference File
			}
		}
			
		
	}
	
	/**
	 * This method creates an IEE reference style for each article in a certain file in a separate folder
	 * @param index The index of the File being processed in the files[] array
	 * @param numArticles The number of articles in the file being processed
	 * @param articles A 2D array of Strings where rach article in the file will be stored each line on its own --> articles[articles][lines]
	 * @param iee An array of Strings where the final created IEE reference will be stored for later use
	 */
	private static void IEE(int index, int numArticles)
	{
		String[][] articles = new String[numArticles][14]; //where the whole file is going to be stored each article on its own
		String[] iee = new String [numArticles]; //where the created iee references will be stored
		
		//to fill the array of articles with content of file
		for (int i = 0; i < numArticles; i++)
		{
			for (int j = 0; j < 13; j++)
			{
				//to avoid having empty lines in the array
				try {
					line = buff2[index].readLine();
					while ((line.length() <= 1) && (line != "{") && (line != "}"))
					{
						line = buff2[index].readLine();
					}
					articles[i][j] = line;
				} catch (IOException e) {
					e.printStackTrace();
				}
				articles[i][13] = "}";
			}
		}
		/* to see the content of the articles array
		System.out.println("*************************************************** ");
		System.out.println("File: " + (index+1));
		for(int i = 0; i < numArticles; i++)
		{
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@");
			System.out.println("Article: " + (i+1));
			for (int j = 0; j < 14; j++)
			{
				System.out.println(articles[i][j]);
			}
			
			
		}
		String test = "\n";
		System.out.println("which files? " + (index+1));
		System.out.println("how many articles? " + numArticles);
		System.out.println(articles[0][6].isEmpty());
		System.out.println(test.length());*/
		
		//Creating the reference
		for (int i = 0; i < numArticles; i++) //loop going through each article
		{
			String author = "author";
			String title = "title";
			String journal = "journal";
			String volume = "volume";
			String number = "number";
			String pages = "pages";
			String month = "month";
			String year = "year";
			
			for(int y = 0; y < 14; y++) //loop going through each line (14 lines total)
			{
				if (articles[i][y].startsWith("author=") == true)
				{
					//the author part
					author = articles[i][y].replaceAll("and", ",").substring(8);
					author = author.substring(0, (author.length() - 3));
					author = author.replace(" ,", ",");
				}
				else if (articles[i][y].startsWith("title=") == true)
				{
					//the title part
					title = articles[i][y].substring(7,(articles[i][y].indexOf('}')));
				}
				else if (articles[i][y].startsWith("journal=") == true)
				{
					//the journal part
					journal = articles[i][y].substring(9,(articles[i][y].indexOf('}')));
				}
				else if (articles[i][y].startsWith("volume=") == true)
				{
					//the volume part
					volume = articles[i][y].substring(8,(articles[i][y].indexOf('}')));
				}
				else if (articles[i][y].startsWith("number=") == true)
				{
					//the number part
					number = articles[i][y].substring(8,(articles[i][y].indexOf('}')));
				}
				else if (articles[i][y].startsWith("pages=") == true)
				{
					//the pages part
					pages = articles[i][y].substring(7,(articles[i][y].indexOf('}')));
				}
				else if (articles[i][y].startsWith("month=") == true)
				{
					//the month part
					month = articles[i][y].substring(7,(articles[i][y].indexOf('}')));
				}
				else if (articles[i][y].startsWith("year=") == true)
				{
					//the year part
					year = articles[i][y].substring(6,(articles[i][y].indexOf('}')));
				}
			}
			

			//all put together
			String reference;
			reference = author + ". \"" + title + "\", " + 
					journal + ", vol. " + volume + ", no. " + number + ", p. "
					+ pages + ", "  + month + " " + year + ".";
			
			//storing the reference in the reference array
			iee[i] = reference;
		}
		
		//Uploading the created references in their corresponding files
		for(int i = 0; i < numArticles; i++)
		{
			pw[index].println(iee[i]);
			pw[index].println(" ");
		}
		//closing printWriter
		pw[index].close();
	}
	
	/**
	 * This method creates an ACM reference style for each article in a certain file in a separate folder
	 * @param index The index of the File being processed in the files[] array
	 * @param numArticles The number of articles in the file being processed
	 * @param articles A 2D array of Strings where each article in the file will be stored each line on its own --> articles[articles][lines]
	 * @param ACM An array of Strings where the final created ACM reference will be stored for later use
	 */
	private static void ACM(int index, int numArticles)
	{
		String[][] articles = new String[numArticles][14]; //where the whole file is going to be stored each article on its own
		String[] ACM = new String [numArticles]; //where the created ACM references will be stored
		
		//to fill the array of articles with content of file
		for (int i = 0; i < numArticles; i++)
		{
			for (int j = 0; j < 13; j++)
			{
				//to avoid having empty lines in the array
				try {
					line = buff3[index].readLine();
					while ((line.length() <= 1) && (line != "{") && (line != "}"))
					{
						line = buff3[index].readLine();
					}
					articles[i][j] = line;
				} catch (IOException e) {
					e.printStackTrace();
				}
				articles[i][13] = "}";
			}
		}
		/* to see the content of the articles array
		System.out.println("*************************************************** ");
		System.out.println("File: " + (index+1));
		for(int i = 0; i < numArticles; i++)
		{
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@");
			System.out.println("Article: " + (i+1));
			for (int j = 0; j < 14; j++)
			{
				System.out.println(articles[i][j]);
			}
			
			
		}
		String test = "\n";
		System.out.println("which files? " + (index+1));
		System.out.println("how many articles? " + numArticles);
		System.out.println(articles[0][6].isEmpty());
		System.out.println(test.length());*/
		
		//Creating the reference
		for (int i = 0; i < numArticles; i++) //loop going through each article
		{
			String author = "author";
			String title = "title";
			String journal = "journal";
			String volume = "volume";
			String number = "number";
			String pages = "pages";
			String month = "month";
			String year = "year";
			String DOI = "doi";
			
			for(int y = 0; y < 14; y++) //loop going through each line (14 lines total)
			{
				if (articles[i][y].startsWith("author=") == true)
				{
					//the author part
					if (articles[i][y].contains("and"))
					{
						author = articles[i][y].substring(8, (articles[i][y].indexOf("and")-1));
					}
					else
						author = articles[i][y].substring(8, (articles[i][y].length()-3));
				}
				else if (articles[i][y].startsWith("title=") == true)
				{
					//the title part
					title = articles[i][y].substring(7,(articles[i][y].indexOf('}')));
				}
				else if (articles[i][y].startsWith("journal=") == true)
				{
					//the journal part
					journal = articles[i][y].substring(9,(articles[i][y].indexOf('}')));
				}
				else if (articles[i][y].startsWith("volume=") == true)
				{
					//the volume part
					volume = articles[i][y].substring(8,(articles[i][y].indexOf('}')));
				}
				else if (articles[i][y].startsWith("number=") == true)
				{
					//the number part
					number = articles[i][y].substring(8,(articles[i][y].indexOf('}')));
				}
				else if (articles[i][y].startsWith("pages=") == true)
				{
					//the pages part
					pages = articles[i][y].substring(7,(articles[i][y].indexOf('}')));
				}
				else if (articles[i][y].startsWith("month=") == true)
				{
					//the month part
					month = articles[i][y].substring(7,(articles[i][y].indexOf('}')));
				}
				else if (articles[i][y].startsWith("year=") == true)
				{
					//the year part
					year = articles[i][y].substring(6,(articles[i][y].indexOf('}')));
				}
				else if (articles[i][y].startsWith("doi=") == true)
				{
					//the Doi part
					DOI = articles[i][y].substring(5,(articles[i][y].indexOf('}')));
				}
			}

			//all put together
			String reference;
			reference = "[" + (i+1) + "]     " + author + " et al. " + year + ". " + title + ". " + journal + ". " 
			+ volume + ", " + number + " (" + year + "), " + pages + ". DOI:https://doi.org/" + DOI + ". ";
			
			//storing the reference in the reference array
			ACM[i] = reference;
		}
		
		//Uploading the created references in their corresponding files
		for(int i = 0; i < numArticles; i++)
		{
			pw[(index+10)].println(ACM[i]);
			pw[(index+10)].println(" ");
		}
		//closing printWriter
		pw[(index+10)].close();
	}
	
	/**
	 * This method creates an NJ reference style for each article in a certain file in a separate folder
	 * @param index The index of the File being processed in the files[] array
	 * @param numArticles The number of articles in the file being processed
	 * @param articles A 2D array of Strings where each article in the file will be stored each line on its own --> articles[articles][lines]
	 * @param NJ An array of Strings where the final created NJ reference will be stored for later use
	 */
	private static void NJ(int index, int numArticles)
	{
		String[][] articles = new String[numArticles][14]; //where the whole file is going to be stored each article on its own
		String[] NJ = new String [numArticles]; //where the created ACM references will be stored
		
		//to fill the array of articles with content of file
		for (int i = 0; i < numArticles; i++)
		{
			for (int j = 0; j < 13; j++)
			{
				//to avoid having empty lines in the array
				try {
					line = buff4[index].readLine();
					while ((line.length() <= 1) && (line != "{") && (line != "}"))
					{
						line = buff4[index].readLine();
					}
					articles[i][j] = line;
				} catch (IOException e) {
					e.printStackTrace();
				}
				articles[i][13] = "}";
			}
		}
		/* to see the content of the articles array
		System.out.println("*************************************************** ");
		System.out.println("File: " + (index+1));
		for(int i = 0; i < numArticles; i++)
		{
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@");
			System.out.println("Article: " + (i+1));
			for (int j = 0; j < 14; j++)
			{
				System.out.println(articles[i][j]);
			}
			
			
		}
		String test = "\n";
		System.out.println("which files? " + (index+1));
		System.out.println("how many articles? " + numArticles);
		System.out.println(articles[0][6].isEmpty());
		System.out.println(test.length());*/
		
		//Creating the reference
		for (int i = 0; i < numArticles; i++) //loop going through each article
		{
			String author = "author";
			String title = "title";
			String journal = "journal";
			String volume = "volume";
			String number = "number";
			String pages = "pages";
			String month = "month";
			String year = "year";
			String DOI = "doi";
			
			for(int y = 0; y < 14; y++) //loop going through each line (14 lines total)
			{
				if (articles[i][y].startsWith("author=") == true)
				{
					//the author part
					author = articles[i][y].replaceAll("and", "&").substring(8);
					author = author.substring(0, (author.length() - 3));
				}
				else if (articles[i][y].startsWith("title=") == true)
				{
					//the title part
					title = articles[i][y].substring(7,(articles[i][y].indexOf('}')));
				}
				else if (articles[i][y].startsWith("journal=") == true)
				{
					//the journal part
					journal = articles[i][y].substring(9,(articles[i][y].indexOf('}')));
				}
				else if (articles[i][y].startsWith("volume=") == true)
				{
					//the volume part
					volume = articles[i][y].substring(8,(articles[i][y].indexOf('}')));
				}
				else if (articles[i][y].startsWith("number=") == true)
				{
					//the number part
					number = articles[i][y].substring(8,(articles[i][y].indexOf('}')));
				}
				else if (articles[i][y].startsWith("pages=") == true)
				{
					//the pages part
					pages = articles[i][y].substring(7,(articles[i][y].indexOf('}')));
				}
				else if (articles[i][y].startsWith("month=") == true)
				{
					//the month part
					month = articles[i][y].substring(7,(articles[i][y].indexOf('}')));
				}
				else if (articles[i][y].startsWith("year=") == true)
				{
					//the year part
					year = articles[i][y].substring(6,(articles[i][y].indexOf('}')));
				}
				else if (articles[i][y].startsWith("doi=") == true)
				{
					//the Doi part
					DOI = articles[i][y].substring(5,(articles[i][y].indexOf('}')));
				}
			}
			
			//all put together
			String reference;
			reference = author + ". " + title + ". " + journal + ". " 
			+ volume + ", " + pages + "(" + year + "). ";
			
			//storing the reference in the reference array
			NJ[i] = reference;
		}
		
		//Uploading the created references in their corresponding files
		for(int i = 0; i < numArticles; i++)
		{
			pw[(index+20)].println(NJ[i]);
			pw[(index+20)].println(" ");
		}
		//closing printWriter
		pw[(index+20)].close();
	}		
											


}
