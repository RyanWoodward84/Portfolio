package question2;
import question2.temp.*;

public class ASubClass extends AClass
{
	private int asprivate;
	protected int asprotected;
	public int aspublic;
	int aspackage;

	/**
	 * Think about these declarations
	 */
	BClass bobj = new BClass();
	CClass cobj = new CClass();

	/**
	 * Constructor for objects of class ASubClass
	 */
	public ASubClass()
	{
		//Initialize instance variables
		asprivate = 1;
		asprotected = 2;
		aspublic = 3;
		aspackage = 4;
	}

	public int addem()
	{
		//System.out.println(bobj.bprivate);
		//This line will not print out because bprivate has a private access specifier and is only visible within
		//that class
		System.out.println(bobj.bprotected);
		//This line will print since bprotected is visible within the package
		System.out.println(bobj.bpublic);
		//This line will print because bpublic is visible everywhere
		System.out.println(bobj.bpackage);
		//This line will print, without an access specifier by default it is visible to everyone within the same package
		//System.out.println(cobj.cprivate);
		//This line will not print as cprivate is only visible within that class
		//System.out.println(cobj.cprotected);
		//This line will not print as cprotected is only visible within that package, which is a subpackage in this case
		System.out.println(cobj.cpublic);
		//This line will print as cpublic is visible everywhere
		//System.out.println(cobj.cpackage);
		//This line will not print as cpackage is only visible within the package, which is a subpackage in this case
		//System.out.println(aprivate);
		//This line will not print because aprivate is only visible within that class
		System.out.println(aprotected);
		//This line will print because aprotected is visible to everyone within the same package
		System.out.println(apublic);
		//This line will print because apublic is visible everywhere
		System.out.println(apackage);
		//This line will print because apackage, by default, is visible to everyone within the package, which includes
		//sub-classes
		return asprivate + asprotected + aspublic + aspackage + aprotected + apublic + apackage;
	}

}
