package pt.itmanager.www.mywebviewapp;

public class ContactAdditional {

	//private variables
	int _id;
	String _id2;
	String _contact_type_id;
	String _contact_type_name;
	String _contact_id;
	String _contact_name;
	String _contact_number;

	// constructor
	public ContactAdditional(int id, String id2, String contact_type_id, String contact_type_name, String contact_id, String contact_name, String contact_number){
		this._id = id;
		this._id2 = id2;
		this._contact_type_id = contact_type_id;
		this._contact_type_name = contact_type_name;
		this._contact_id = contact_id;
		this._contact_name = contact_name;
		this._contact_number = contact_number;
	}

	// constructor
	public ContactAdditional(String id2, String contact_type_id, String contact_type_name, String contact_id, String contact_name, String contact_number){
		this._id2 = id2;
		this._contact_type_id = contact_type_id;
		this._contact_type_name = contact_type_name;
		this._contact_id = contact_id;
		this._contact_name = contact_name;
		this._contact_number = contact_number;
	}
	// getting ID
	public int getID(){	return this._id;}
	
	// setting id
	public void setID(int id){
		this._id = id;
	}

	// getting ID2
	public String getID2(){ return this._id2;}

	// setting id2
	public void setID2(String id2){
		this._id2 = id2;
	}

	// getting name
	public String getContactoTypeId(){ return this._contact_type_id;}

	// setting name
	public void setContactoTypeId(String contact_type_id){ this._contact_type_id = contact_type_id;}

    // getting name
    public String getContactTypeName(){ return this._contact_type_name;}

    // setting name
    public void setContactTypeName(String contact_type_name){ this._contact_type_name = contact_type_name;}

	// getting name
	public String getContactId(){ return this._contact_id;}

	// setting name
	public void setContactId(String contact_id){ this._contact_id = contact_id;}

    // getting name
    public String getContactName(){ return this._contact_name;}

    // setting name
    public void setContactName(String contact_name){ this._contact_name = contact_name;}

    // getting name
    public String getContactNumber(){ return this._contact_number;}

    // setting name
    public void setContactNumber(String contact_number){ this._contact_number = contact_number;}

}
