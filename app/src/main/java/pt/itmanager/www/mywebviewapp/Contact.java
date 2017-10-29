package pt.itmanager.www.mywebviewapp;

public class Contact {
	
	//private variables
	int _id;
	String _id2;
	String _numero_funcionario;
	String _departamento;
	String _mail;
	String _nome;
	String _ultimonome;
	
	// Empty constructor
	public Contact(){
		
	}
	// constructor
	public Contact(int id, String id2, String numero_funcionario, String departamento, String mail, String nome, String ultimonome){
		this._id = id;
		this._id2 = id2;
		this._numero_funcionario = numero_funcionario;
		this._departamento = departamento;
		this._mail = mail;
		this._nome = nome;
		this._ultimonome = ultimonome;
	}

	// constructor
	public Contact(String id2, String numero_funcionario, String departamento, String mail, String nome, String ultimonome){
		this._id2 = id2;
		this._numero_funcionario = numero_funcionario;
		this._departamento = departamento;
		this._mail = mail;
		this._nome = nome;
		this._ultimonome = ultimonome;
	}
	// getting ID
	public int getID(){	return this._id;}
	
	// setting id
	public void setID(int id){
		this._id = id;
	}

	// getting ID
	public String getID2(){	return this._id2;}

	// setting id
	public void setID2(String id2){
		this._id2 = id2;
	}

	// getting name
	public String getNome(){ return this._nome;}

	// setting name
	public void setNome(String nome){ this._nome = nome;}

    // getting name
    public String getUltimoNome(){ return this._ultimonome;}

    // setting name
    public void setUltimoNome(String ultimonome){ this._ultimonome = ultimonome;}

    // getting name
    public String getMail(){ return this._mail;}

    // setting name
    public void setMail(String mail){ this._mail = mail;}

    // getting name
    public String getDepartamento(){ return this._departamento;}

    // setting name
    public void setDepartamento(String departamento){ this._departamento = departamento;}

    // getting name
    public String getNumeroFuncionario(){ return this._numero_funcionario;}

    // setting name
    public void setNumeroFuncionario(String numero_funcionario){ this._numero_funcionario = numero_funcionario;}

}
