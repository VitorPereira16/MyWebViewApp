package pt.itmanager.www.mywebviewapp;

public class Contact {
	
	//private variables
	int _id;
	String _numero_funcionario;
	String _departamento;
	String _telemovel;
	String _ext_telemovel;
	String _telefone;
	String _ext_telefone;
	String _mail;
	String _nome;
	String _ultimonome;
	
	// Empty constructor
	public Contact(){
		
	}
	// constructor
	public Contact(int id, String numero_funcionario, String departamento, String telemovel, String ext_telemovel, String telefone, String ext_telefone, String mail, String nome, String ultimonome){
		this._id = id;
		this._numero_funcionario = numero_funcionario;
		this._departamento = departamento;
		this._telemovel = telemovel;
		this._ext_telemovel = ext_telemovel;
		this._telefone = telefone;
		this._ext_telefone = ext_telefone;
		this._mail = mail;
		this._nome = nome;
		this._ultimonome = ultimonome;
	}

	// constructor
	public Contact(String numero_funcionario, String departamento, String telemovel, String ext_telemovel, String telefone, String ext_telefone, String mail, String nome, String ultimonome){
		this._numero_funcionario = numero_funcionario;
		this._departamento = departamento;
		this._telemovel = telemovel;
		this._ext_telemovel = ext_telemovel;
		this._telefone = telefone;
		this._ext_telefone = ext_telefone;
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
    public String getExtTelefone(){ return this._ext_telefone;}

    // setting name
    public void setExtTelefone(String ext_telefone){ this._ext_telefone = ext_telefone;}

    // getting name
    public String getTelefone(){ return this._telefone;}

    // setting name
    public void setTelefone(String telefone){ this._telefone = telefone;}

    // getting name
    public String getExtTelemovel(){ return this._ext_telemovel;}

    // setting name
    public void setExtTelemovel(String ext_telemovel){ this._ext_telemovel = ext_telemovel;}

    // getting name
    public String getTelemovel(){ return this._telemovel;}

    // setting name
    public void setTelemovel(String telemovel){ this._telemovel = telemovel;}

    // getting name
    public String getDepartamento(){ return this._departamento;}

    // setting name
    public void setDepartamento(String departamento){ this._departamento = departamento;}

    // getting name
    public String getNumeroFuncionario(){ return this._numero_funcionario;}

    // setting name
    public void setNumeroFuncionario(String numero_funcionario){ this._numero_funcionario = numero_funcionario;}

}
