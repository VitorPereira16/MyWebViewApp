package pt.itmanager.www.mywebviewapp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "contactsManager";

	// Contacts table name
	private static final String TABLE_CONTACTS = "contacts";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NR_FUNC = "numero_funcionario";
	private static final String KEY_DEPARTAMENTO = "departamento";
	private static final String KEY_TELEMOVEL = "telemovel";
	private static final String KEY_EXT_TELEMOVEL = "ext_telemovel";
	private static final String KEY_TELEFONE = "telefone";
	private static final String KEY_EXT_TELEFONE = "ext_telefone";
	private static final String KEY_MAIL = "mail";
	private static final String KEY_NOME = "nome";
	private static final String KEY_ULTIMO_NOME = "ultimonome";


	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NR_FUNC + " TEXT,"
				+ KEY_DEPARTAMENTO + " TEXT,"
				+ KEY_TELEMOVEL + " TEXT,"
				+ KEY_EXT_TELEMOVEL + " TEXT,"
				+ KEY_TELEFONE + " TEXT,"
				+ KEY_EXT_TELEFONE + " TEXT,"
				+ KEY_MAIL + " TEXT,"
				+ KEY_NOME + " TEXT,"
				+ KEY_ULTIMO_NOME + " TEXT" +")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new contact
	void addContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NR_FUNC, contact.getNumeroFuncionario()); // Contact Name
		values.put(KEY_DEPARTAMENTO, contact.getDepartamento()); // Contact Phone
		values.put(KEY_TELEMOVEL, contact.getTelemovel()); // Contact Phone
		values.put(KEY_EXT_TELEMOVEL, contact.getExtTelemovel()); // Contact Phone
		values.put(KEY_TELEFONE, contact.getTelefone()); // Contact Phone
		values.put(KEY_EXT_TELEFONE, contact.getExtTelefone()); // Contact Phone
		values.put(KEY_MAIL, contact.getMail()); // Contact Phone
		values.put(KEY_NOME, contact.getNome()); // Contact Phone
		values.put(KEY_ULTIMO_NOME, contact.getUltimoNome()); // Contact Phone

		// Inserting Row
		db.insert(TABLE_CONTACTS, null, values);
		db.close(); // Closing database connection
	}

	// Getting single contact
	Contact getContact(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
						KEY_NR_FUNC, KEY_DEPARTAMENTO, KEY_TELEMOVEL, KEY_EXT_TELEMOVEL, KEY_TELEFONE, KEY_EXT_TELEFONE, KEY_MAIL, KEY_NOME, KEY_ULTIMO_NOME }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));
		// return contact
		return contact;
	}

	// Getting single contact
	Contact getContactByNumber(String number) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
				KEY_NR_FUNC, KEY_DEPARTAMENTO, KEY_TELEMOVEL, KEY_EXT_TELEMOVEL, KEY_TELEFONE, KEY_EXT_TELEFONE, KEY_MAIL, KEY_NOME, KEY_ULTIMO_NOME }, KEY_TELEMOVEL + "=?",
				new String[] { String.valueOf(number) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));
		// return contact
		return contact;
	}

	// Getting single contact
	public int verifyContactoExist(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String Query = "Select * from " + TABLE_CONTACTS + " where " + KEY_ID + " = " + id;
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
		Log.d("cor",  DatabaseUtils.dumpCursorToString(cursor));
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Contact contact = new Contact();
				contact.setID(Integer.parseInt(cursor.getString(0)));
				contact.setNumeroFuncionario(cursor.getString(1)); // Contact Name
				contact.setDepartamento(cursor.getString(2)); // Contact Phone
				contact.setTelemovel(cursor.getString(3)); // Contact Phone
				contact.setExtTelemovel(cursor.getString(4)); // Contact Phone
				contact.setTelefone(cursor.getString(5)); // Contact Phone
				contact.setExtTelefone(cursor.getString(6)); // Contact Phone
				contact.setMail(cursor.getString(7)); // Contact Phone
				contact.setNome(cursor.getString(8)); // Contact Phone
				contact.setUltimoNome(cursor.getString(9)); // Contact Phone
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
		values.put(KEY_NR_FUNC, contact.getNumeroFuncionario()); // Contact Name
		values.put(KEY_DEPARTAMENTO, contact.getDepartamento()); // Contact Phone
		values.put(KEY_TELEMOVEL, contact.getTelemovel()); // Contact Phone
		values.put(KEY_EXT_TELEMOVEL, contact.getExtTelemovel()); // Contact Phone
		values.put(KEY_TELEFONE, contact.getTelefone()); // Contact Phone
		values.put(KEY_EXT_TELEFONE, contact.getExtTelefone()); // Contact Phone
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
