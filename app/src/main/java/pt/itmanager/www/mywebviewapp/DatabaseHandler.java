package pt.itmanager.www.mywebviewapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "contactsManager";

	// Contacts table name
	private static final String TABLE_CONTACTS = "contacts";
	private static final String TABLE_CONTACTS_ADDI = "contacts_addi";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_ID2 = "id2";
	private static final String KEY_NR_FUNC = "numero_funcionario";
	private static final String KEY_DEPARTAMENTO = "departamento";
	private static final String KEY_TELEMOVEL = "telemovel";

	private static final String KEY_MAIL = "mail";
	private static final String KEY_NOME = "nome";
	private static final String KEY_ULTIMO_NOME = "ultimonome";

	// NOTE_TAGS Table - column names
	private static final String KEY_CA_ID = "id";
	private static final String KEY_CA_ID2 = "id2";
	private static final String KEY_CA_CONTACT_TYPE_ID = "contact_type_id";
	private static final String KEY_CA_CONTACT_TYPE_NAME = "contact_type_name";
	private static final String KEY_CA_CONTACT_ID = "contact_id";
	private static final String KEY_CA_CONTACT_NAME = "contact_name";
	private static final String KEY_CA_CONTACT_NUMBER = "contact_number";


	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_ID2 + " TEXT," + KEY_NR_FUNC + " TEXT,"
				+ KEY_DEPARTAMENTO + " TEXT,"
				+ KEY_MAIL + " TEXT,"
				+ KEY_NOME + " TEXT,"
				+ KEY_ULTIMO_NOME + " TEXT" +")";
		String CREATE_CONTACTS_ADDI_TABLE = "CREATE TABLE " + TABLE_CONTACTS_ADDI + "("
				+ KEY_CA_ID + " INTEGER PRIMARY KEY," + KEY_CA_ID2 + " TEXT," + KEY_CA_CONTACT_TYPE_ID + " TEXT,"
				+ KEY_CA_CONTACT_TYPE_NAME + " TEXT,"
				+ KEY_CA_CONTACT_ID + " TEXT,"
				+ KEY_CA_CONTACT_NAME + " TEXT,"
				+ KEY_CA_CONTACT_NUMBER + " TEXT" +")";
		db.execSQL(CREATE_CONTACTS_TABLE);
		db.execSQL(CREATE_CONTACTS_ADDI_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS_ADDI);

		// Create tables again
		onCreate(db);
	}


	// Adding new contact
	void addContactAdicionar(ContactAdditional contactAdditional) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_CA_ID2, contactAdditional.getID2()); // Contact Name
		values.put(KEY_CA_CONTACT_TYPE_ID, contactAdditional.getContactoTypeId()); // Contact Name
		values.put(KEY_CA_CONTACT_TYPE_NAME, contactAdditional.getContactTypeName()); // Contact Phone
		values.put(KEY_CA_CONTACT_ID, contactAdditional.getContactId()); // Contact Phone
		values.put(KEY_CA_CONTACT_NAME, contactAdditional.getContactName()); // Contact Phone
		values.put(KEY_CA_CONTACT_NUMBER, contactAdditional.getContactNumber()); // Contact Phone

		// Inserting Row
		Log.d("Name: ", values.toString());
		db.insert(TABLE_CONTACTS_ADDI, null, values);
		db.close(); // Closing database connection
	}

	// Getting single contact
	public int verifyContactAdditionalExist(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String Query = "Select * from " + TABLE_CONTACTS_ADDI + " where " + KEY_CA_ID + " = " + id;
		Cursor cursor = db.rawQuery(Query, null);
		Integer status = 1;
		if(cursor.getCount() <= 0){
			status = 0;
			return status;
		}
		return status;
	}

	public int verifyContactoAdditionalExistByNumber(String number) {
		SQLiteDatabase db = this.getReadableDatabase();

		String Query = "Select * from " + TABLE_CONTACTS_ADDI + " where " + KEY_CA_CONTACT_NUMBER + " = " + number;
		Log.d("QUERY: ", Query);
		Cursor cursor = db.rawQuery(Query, null);
		Integer status = 1;
		if(cursor.getCount() <= 0){
			status = 0;
			return status;
		}
		return status;
	}

	// Getting single contact
	ContactAdditional getContactAdditionalByNumber(String number) {
		SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.query(TABLE_CONTACTS_ADDI, new String[] { KEY_CA_ID, KEY_CA_ID2,
						KEY_CA_CONTACT_TYPE_ID, KEY_CA_CONTACT_TYPE_NAME, KEY_CA_CONTACT_ID, KEY_CA_CONTACT_NAME, KEY_CA_CONTACT_NUMBER }, KEY_CA_CONTACT_NUMBER + "=?",
				new String[] { String.valueOf(number) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		ContactAdditional contactA = new ContactAdditional(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
		// return contact
		return contactA;
	}

	public int updateContactAdditional(ContactAdditional contactAdditional) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		//values.put(KEY_CA_ID2, contactAdditional.getID2()); // Contact Name
		values.put(KEY_CA_CONTACT_TYPE_ID, contactAdditional.getContactoTypeId()); // Contact Name
		values.put(KEY_CA_CONTACT_TYPE_NAME, contactAdditional.getContactTypeName()); // Contact Phone
		values.put(KEY_CA_CONTACT_ID, contactAdditional.getContactId()); // Contact Phone
		values.put(KEY_CA_CONTACT_NAME, contactAdditional.getContactName()); // Contact Phone
		values.put(KEY_CA_CONTACT_NUMBER, contactAdditional.getContactNumber()); // Contact Phone
		Log.d("Cont: ", values.toString());
		// updating row
		return db.update(TABLE_CONTACTS_ADDI, values, KEY_CA_ID2 + " = ?",
				new String[] { String.valueOf(contactAdditional.getID2()) });
	}

	// Getting single contactAd
	public int verifyContactoExistAd(String id, String contact_id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String Query = "Select * from " + TABLE_CONTACTS_ADDI + " where " + KEY_CA_ID2 + " = " + id +" AND "+ KEY_CA_CONTACT_ID + " = " + contact_id;
		Log.d("Query: ", Query);
		Cursor cursor = db.rawQuery(Query, null);
		Integer status = 1;
		if(cursor.getCount() <= 0){
			status = 0;
			return status;
		}
		return status;
	}


	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new contact
	void addContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID2, contact.getID2()); // Contact Name
		values.put(KEY_NR_FUNC, contact.getNumeroFuncionario()); // Contact Name
		values.put(KEY_DEPARTAMENTO, contact.getDepartamento()); // Contact Phone
		values.put(KEY_MAIL, contact.getMail()); // Contact Phone
		values.put(KEY_NOME, contact.getNome()); // Contact Phone
		values.put(KEY_ULTIMO_NOME, contact.getUltimoNome()); // Contact Phone

		// Inserting Row
		Log.d("Cont: ", values.toString());
		db.insert(TABLE_CONTACTS, null, values);
		db.close(); // Closing database connection
	}

	// Getting single contact
	Contact getContact(String id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID, KEY_ID2,
						KEY_NR_FUNC, KEY_DEPARTAMENTO, KEY_MAIL, KEY_NOME, KEY_ULTIMO_NOME }, KEY_ID2 + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
		// return contact
		return contact;
	}

	// Getting single contact
	Contact getContactByNumber(String number) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID, KEY_ID2,
				KEY_NR_FUNC, KEY_DEPARTAMENTO, KEY_MAIL, KEY_NOME, KEY_ULTIMO_NOME }, KEY_TELEMOVEL + "=?",
				new String[] { String.valueOf(number) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
		// return contact
		return contact;
	}


	// Getting single contact
	public int verifyContactoExist(String id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String Query = "Select * from " + TABLE_CONTACTS + " where " + KEY_ID2 + " = " + id;
		Cursor cursor = db.rawQuery(Query, null);
		Integer status = 1;
		if(cursor.getCount() <= 0){
			status = 0;
			return status;
		}
		return status;
	}

	// Getting All Contacts
	public List<Contact> getAllContacts() {
		List<Contact> contactList = new ArrayList<Contact>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		//Log.d("cor",  DatabaseUtils.dumpCursorToString(cursor));
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Contact contact = new Contact();
				contact.setID(Integer.parseInt(cursor.getString(0)));
				contact.setID2(cursor.getString(1));
				contact.setNumeroFuncionario(cursor.getString(2)); // Contact Name
				contact.setDepartamento(cursor.getString(3)); // Contact Phone
				contact.setMail(cursor.getString(4)); // Contact Phone
				contact.setNome(cursor.getString(5)); // Contact Phone
				contact.setUltimoNome(cursor.getString(6)); // Contact Phone
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}

		// return contact list
		return contactList;
	}

	// Updating single contact
	public int updateContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID2, contact.getNumeroFuncionario()); // Contact Name
		values.put(KEY_NR_FUNC, contact.getNumeroFuncionario()); // Contact Name
		values.put(KEY_DEPARTAMENTO, contact.getDepartamento()); // Contact Phone
		values.put(KEY_MAIL, contact.getMail()); // Contact Phone
		values.put(KEY_NOME, contact.getNome()); // Contact Phone
		values.put(KEY_ULTIMO_NOME, contact.getUltimoNome()); // Contact Phone

		// updating row
		return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(contact.getID()) });
	}

	// Deleting single contact
	public void deleteContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
				new String[] { String.valueOf(contact.getID()) });
		db.close();
	}


	// Getting contacts Count
	public int getContactsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}
