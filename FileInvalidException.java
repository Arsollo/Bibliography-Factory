//----------------------------------------------------
//Assignment #3
//Written by: Arsany Fahmy (40157267)
//----------------------------------------------------

public class FileInvalidException extends Exception
{
	public FileInvalidException()
	{
		super("Error: Input File cannot be parsed due to"
				+ " missing information (i.e. month={},title"
				+ "={}, etc.)");
	}
	public FileInvalidException(String message)
	{
		super(message);
	}
}
