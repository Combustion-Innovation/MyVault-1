package com.myvault.myvault;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBOpenHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "myvaultdb";
    private static final int DATABASE_VERSION = 2;
    
    private SQLiteDatabase database;
    
    DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        
        db.execSQL("CREATE TABLE creditcards(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nickname, cardholder, brand, number, expiration, csv, expInMillis, notes)");
		db.execSQL("CREATE TABLE socialsecurity(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, name, ssnumber, notes)");
		db.execSQL("CREATE TABLE passwords(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, website, username, password, notes)");
		db.execSQL("CREATE TABLE accounts(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, business, accountnum, businessnum, notes)");
		db.execSQL("CREATE TABLE contacts(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, name, number, notes)");
		db.execSQL("CREATE TABLE notes(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, title, note)");
		db.execSQL("CREATE TABLE insurance(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, insurancecompany, member, memid, groupnum, type, code, notes)");
		db.execSQL("CREATE TABLE media(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, location, type)");
		db.execSQL("CREATE TABLE cars(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, make, model, plate, year, insurance, policy, agentname, agenttel, notes)");
		db.execSQL("CREATE TABLE bookmarks(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, website, url, nickname, notes)");
		db.execSQL("CREATE TABLE reminders(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nickname, name, alertdate, alerttime, location, alertMillis, notes)");
		    	
    	database = db;
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	public void setDatabase() {
		this.database = SQLiteDatabase.openOrCreateDatabase("/data/data/com.myvault.myvault/databases/myvaultdb", null);
	}
	/*
	 * 
	 * Custom CRUD operations
	 * 
	 * 
	 */
	
	
	
	public void addCar(String make, String model, String plate, String year, String insurance, String policy, String agentname, String agenttel, String notes) {
		
		setDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put("make", make);
		values.put("model", model);
		values.put("plate", plate);
		values.put("year", year);
		values.put("insurance", insurance);
		values.put("policy", policy);
		values.put("agentname", agentname);
		values.put("agenttel", agenttel);
		values.put("notes", notes);
		
		
		
		database.insert("cars", null, values);
		
	}
	
	public void deleteCar(int id) {
		
		setDatabase();
		
		String[] whereArgs = {Integer.toString(id)};
		
		database.delete("cars", "id = ?", whereArgs);
		
	}
	
	public void updateCar(int id, String make, String model, String plate, String year, String insurance, String policy, String agentname, String agenttel, String notes) {
				
		setDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put("make", make);
		values.put("model", model);
		values.put("plate", plate);
		values.put("year", year);
		values.put("insurance", insurance);
		values.put("policy", policy);
		values.put("agentname", agentname);
		values.put("agenttel", agenttel);
		values.put("notes", notes);
		
		String[] whereArgs = {Integer.toString(id)};
		
		database.update("cars", values, "id = ?", whereArgs);
		
	}
	
	public Car selectCarById(int id) {
		
		String[] columns = {"id", "make", "model", "plate", "year", "insurance", "policy", "agentname", "agenttel", "notes"};
		Car car = null;
		
		setDatabase();
		
		Cursor cursor = database.query("cars", columns, "id = ?", new String[] {String.valueOf(id)}, null, null, null, null);
		if(cursor != null) {
			cursor.moveToFirst();
			car = new Car();
			car.setId(cursor.getInt(0));
			car.setMake(cursor.getString(1));
			car.setModel(cursor.getString(2));
			car.setPlate(cursor.getString(3));
			car.setYear(cursor.getString(4));
			car.setInsurance(cursor.getString(5));
			car.setPolicy(cursor.getString(6));
			car.setAgentname(cursor.getString(7));
			car.setAgenttel(cursor.getString(8));
			car.setNotes(cursor.getString(9));
		}
		
		return car;
		
	}
	
	public ArrayList<Car> selectAllCars() {
		
		ArrayList<Car> cars = new ArrayList<Car>();
		
		String query = "SELECT * FROM cars";
		
		setDatabase();
		
		Cursor cursor = database.rawQuery(query, null);
		
		Car car = null;
		if(cursor.moveToFirst()) {
			do {
				car = new Car();
				car.setId(cursor.getInt(0));
				car.setMake(cursor.getString(1));
				car.setModel(cursor.getString(2));
				car.setPlate(cursor.getString(3));
				car.setYear(cursor.getString(4));
				car.setInsurance(cursor.getString(5));
				car.setPolicy(cursor.getString(6));
				car.setAgentname(cursor.getString(7));
				car.setAgenttel(cursor.getString(8));
				car.setNotes(cursor.getString(9));
				
				cars.add(car);
				
			} while(cursor.moveToNext());
		}
		
		return cars;
		
	}
	
	public void addAccount(String business, String accountnum, String businessnum, String notes) {
		
		setDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put("business", business);
		values.put("accountnum", accountnum);
		values.put("businessnum", businessnum);
		values.put("notes", notes);
		
		
		
		
		database.insert("accounts", null, values);
		
	}
	
	public void deleteAccount(int id) {
		
		setDatabase();
		
		String[] whereArgs = {Integer.toString(id)};
		
		database.delete("accounts", "id = ?", whereArgs);
		
	}
	
	public void updateAccount(int id, String business, String accountnum, String businessnum, String notes) {
		
		setDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put("business", business);
		values.put("accountnum", accountnum);
		values.put("businessnum", businessnum);
		values.put("notes", notes);
		
		
		String[] whereArgs = {Integer.toString(id)};
		
		database.update("accounts", values, "id = ?", whereArgs);
		
	}
	
	public Account selectAccountById(int id) {
		
		String[] columns = {"id", "business", "accountnum", "businessnum", "notes"};
		Account account = null;
		
		setDatabase();
		
		Cursor cursor = database.query("accounts", columns, "id = ?", new String[] {String.valueOf(id)}, null, null, null, null);
		if(cursor != null) {
			cursor.moveToFirst();
			account = new Account();
			account.setId(cursor.getInt(0));
			account.setBusiness(cursor.getString(1));
			account.setAccountnum(cursor.getString(2));
			account.setBusinessnum(cursor.getString(3));
			account.setNotes(cursor.getString(4));
			
		}
		
		return account;
		
	}
	
	public ArrayList<Account> selectAllAccounts() {
		
		ArrayList<Account> accounts = new ArrayList<Account>();
		
		String query = "SELECT * FROM accounts";
		
		setDatabase();
		
		Cursor cursor = database.rawQuery(query, null);
		
		Account account = null;
		if(cursor.moveToFirst()) {
			do {
				account = new Account();
				account.setId(cursor.getInt(0));
				account.setBusiness(cursor.getString(1));
				account.setAccountnum(cursor.getString(2));
				account.setBusinessnum(cursor.getString(3));
				account.setNotes(cursor.getString(4));
				
				accounts.add(account);
				
			} while(cursor.moveToNext());
		}
		
		return accounts;
		
	}
	
	public void addBookmark(String url, String nickname, String notes) {
		
		setDatabase();
		
		ContentValues values = new ContentValues();
		
		
		values.put("url", url);
		values.put("nickname", nickname);
		values.put("notes", notes);
		
		
		
		
		database.insert("bookmarks", null, values);
		
	}
	
	public void deleteBookmark(int id) {
		
		setDatabase();
		
		String[] whereArgs = {Integer.toString(id)};
		
		database.delete("bookmarks", "id = ?", whereArgs);
		
	}
	
	public void updateBookmark(int id, String url, String nickname, String notes) {
		
		setDatabase();
		
		ContentValues values = new ContentValues();
		
		
		values.put("url", url);
		values.put("nickname", nickname);
		values.put("notes", notes);
		
		
		String[] whereArgs = {Integer.toString(id)};
		
		database.update("bookmarks", values, "id = ?", whereArgs);
		
	}
	
	public Bookmark selectBookmarkById(int id) {
		
		String[] columns = {"id", "website", "url", "nickname", "notes"};
		Bookmark bookmark = null;

		setDatabase();
		
		Cursor cursor = database.query("bookmarks", columns, "id = ?", new String[] {String.valueOf(id)}, null, null, null, null);
		if(cursor != null) {
			cursor.moveToFirst();
			bookmark = new Bookmark();
			bookmark.setId(cursor.getInt(0));
			bookmark.setWebsite(cursor.getString(1));
			bookmark.setUrl(cursor.getString(2));
			bookmark.setNickname(cursor.getString(3));
			bookmark.setNotes(cursor.getString(4));
			
		}
		
		return bookmark;
		
	}
	
	public ArrayList<Bookmark> selectAllBookmarks() {
		
		ArrayList<Bookmark> bookmarks = new ArrayList<Bookmark>();
		
		String query = "SELECT * FROM bookmarks";
		
		setDatabase();
		
		Cursor cursor = database.rawQuery(query, null);
		
		Bookmark bookmark = null;
		if(cursor.moveToFirst()) {
			do {
				bookmark = new Bookmark();
				bookmark.setId(cursor.getInt(0));
				bookmark.setWebsite(cursor.getString(1));
				bookmark.setUrl(cursor.getString(2));
				bookmark.setNickname(cursor.getString(3));
				bookmark.setNotes(cursor.getString(4));
				
				bookmarks.add(bookmark);
				
			} while(cursor.moveToNext());
		}
		
		return bookmarks;
		
	}
	
	public void addContact(String name, String number, String notes) {
		
		setDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put("name", name);
		values.put("number", number);
		values.put("notes", notes);
		
		
		
		
		database.insert("contacts", null, values);
		
	}
	
	public void deleteContact(int id) {
		
		setDatabase();
		
		String[] whereArgs = {Integer.toString(id)};
		
		database.delete("contacts", "id = ?", whereArgs);
		
	}
	
	public void updateContact(int id, String name, String number, String notes) {
		
		setDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put("name", name);
		values.put("number", number);
		values.put("notes", notes);
		
		
		String[] whereArgs = {Integer.toString(id)};
		
		database.update("contacts", values, "id = ?", whereArgs);
		
	}
	
	public Contact selectContactById(int id) {
		
		String[] columns = {"id", "name", "number", "notes"};
		Contact contact = null;
		
		setDatabase();
		
		Cursor cursor = database.query("contacts", columns, "id = ?", new String[] {String.valueOf(id)}, null, null, null, null);
		if(cursor != null) {
			cursor.moveToFirst();
			contact = new Contact();
			contact.setId(cursor.getInt(0));
			contact.setName(cursor.getString(1));
			contact.setNumber(cursor.getString(2));
			contact.setNotes(cursor.getString(3));
			
			
		}
		
		return contact;
		
	}
	
	public ArrayList<Contact> selectAllContacts() {
		
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		
		String query = "SELECT * FROM contacts";
		
		setDatabase();
		
		Cursor cursor = database.rawQuery(query, null);
		
		Contact contact = null;
		if(cursor.moveToFirst()) {
			do {
				contact = new Contact();
				contact.setId(cursor.getInt(0));
				contact.setName(cursor.getString(1));
				contact.setNumber(cursor.getString(2));
				contact.setNotes(cursor.getString(3));
				
				contacts.add(contact);
				
			} while(cursor.moveToNext());
		}
		
		return contacts;
		
	}
	
	public void addCC(String nickname, String cardholder, String brand, String number, String expiration, String csv, long millis) {
		
		setDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put("nickname", nickname);
		values.put("cardholder", cardholder);
		values.put("brand", brand);
		values.put("number", number);
		values.put("expiration", expiration);
		values.put("csv", csv);
		values.put("expInMillis", millis);
		
		
		
		
		
		database.insert("creditcards", null, values);
		
	}
	
	public void deleteCC(int id) {
		
		setDatabase();
		
		String[] whereArgs = {Integer.toString(id)};
		
		database.delete("creditcards", "id = ?", whereArgs);
		
	}
	
	public void updateCC(int id, String nickname, String cardholder, String brand, String number, String expiration, String csv, long millis) {
		
		setDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put("nickname", nickname);
		values.put("cardholder", cardholder);
		values.put("brand", brand);
		values.put("number", number);
		values.put("expiration", expiration);
		values.put("csv", csv);
		values.put("expInMillis", millis);
		
		
		
		String[] whereArgs = {Integer.toString(id)};
		
		database.update("creditcards", values, "id = ?", whereArgs);
		
	}
	
	public CreditCard selectCCById(int id) {
		
		String[] columns = {"id", "nickname", "cardholder", "brand", "number", "expiration", "csv", "expInMillis", "notes"};
		CreditCard creditcard = null;
		
		setDatabase();
		
		
		Cursor cursor = database.query("creditcards", columns, "id = ?", new String[] {String.valueOf(id)}, null, null, null, null);
		if(cursor != null) {
			cursor.moveToFirst();
			creditcard = new CreditCard();
			creditcard.setId(cursor.getInt(0));
			creditcard.setNickname(cursor.getString(1));
			creditcard.setCardholder(cursor.getString(2));
			creditcard.setBrand(cursor.getString(3));
			creditcard.setNumber(cursor.getString(4));
			creditcard.setExpiration(cursor.getString(5));
			creditcard.setCsv(cursor.getString(6));
			creditcard.setExpMillis(cursor.getLong(7));
			creditcard.setNotes(cursor.getString(8));
			
			
			
		}
		
		return creditcard;
		
	}
	
	public ArrayList<CreditCard> selectAllCCs() {
		
		ArrayList<CreditCard> creditcards = new ArrayList<CreditCard>();
		
		String query = "SELECT * FROM creditcards";
		
		setDatabase();
		
		Cursor cursor = database.rawQuery(query, null);
		
		CreditCard creditcard = null;
		if(cursor.moveToFirst()) {
			do {
				creditcard = new CreditCard();
				creditcard.setId(cursor.getInt(0));
				creditcard.setNickname(cursor.getString(1));
				creditcard.setCardholder(cursor.getString(2));
				creditcard.setBrand(cursor.getString(3));
				creditcard.setNumber(cursor.getString(4));
				creditcard.setExpiration(cursor.getString(5));
				creditcard.setCsv(cursor.getString(6));
				creditcard.setExpMillis(cursor.getLong(7));
				creditcard.setNotes(cursor.getString(8));
				
				creditcards.add(creditcard);
				
			} while(cursor.moveToNext());
		}
		
		return creditcards;
		
	}
	
	public void addInsurance(String insurancecompany, String member, String memid, String groupnum, String type, String code, String notes) {
		
		setDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put("insurancecompany", insurancecompany);
		values.put("member", member);
		values.put("memid", memid);
		values.put("groupnum", groupnum);
		values.put("type", type);
		values.put("code", code);
		values.put("notes", notes);
		
		
		
		
		database.insert("insurance", null, values);
		
	}
	
	public void deleteInsurance(int id) {
		
		setDatabase();
		
		String[] whereArgs = {Integer.toString(id)};
		
		database.delete("insurance", "id = ?", whereArgs);
		
	}
	
	public void updateInsurance(int id, String insurancecompany, String member, String memid, String groupnum, String type, String code, String notes) {
		
		setDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put("insurancecompany", insurancecompany);
		values.put("member", member);
		values.put("memid", memid);
		values.put("groupnum", groupnum);
		values.put("type", type);
		values.put("code", code);
		values.put("notes", notes);
		
		
		String[] whereArgs = {Integer.toString(id)};
		
		database.update("insurance", values, "id = ?", whereArgs);
		
	}
	
	public Insurance selectInsuranceById(int id) {
		
		String[] columns = {"id", "insurancecompany", "member", "memid", "groupnum", "type", "code", "notes"};
		Insurance insurance = null;
		
		setDatabase();
		
		
		Cursor cursor = database.query("insurance", columns, "id = ?", new String[] {String.valueOf(id)}, null, null, null, null);
		if(cursor != null) {
			cursor.moveToFirst();
			insurance = new Insurance();
			insurance.setId(cursor.getInt(0));
			insurance.setInsurancecompany(cursor.getString(1));
			insurance.setMember(cursor.getString(2));
			insurance.setMemid(cursor.getString(3));
			insurance.setGroupnum(cursor.getString(4));
			insurance.setType(cursor.getString(5));
			insurance.setCode(cursor.getString(6));
			insurance.setNotes(cursor.getString(7));
			
			
		}
		
		return insurance;
		
	}
	
	public ArrayList<Insurance> selectAllInsurance() {
		
		ArrayList<Insurance> insurances = new ArrayList<Insurance>();
		
		String query = "SELECT * FROM insurance";
		
		setDatabase();
		
		Cursor cursor = database.rawQuery(query, null);
		
		Insurance insurance = null;
		if(cursor.moveToFirst()) {
			do {
				insurance = new Insurance();
				insurance.setId(cursor.getInt(0));
				insurance.setInsurancecompany(cursor.getString(1));
				insurance.setMember(cursor.getString(2));
				insurance.setMemid(cursor.getString(3));
				insurance.setGroupnum(cursor.getString(4));
				insurance.setType(cursor.getString(5));
				insurance.setCode(cursor.getString(6));
				insurance.setNotes(cursor.getString(7));
				
				insurances.add(insurance);
				
			} while(cursor.moveToNext());
		}
		
		return insurances;
		
	}
	
	public void addMedia(String location, int type) {
		
		setDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put("location", location);
		values.put("type", type);
			
		database.insert("media", null, values);
		database.close();
	}
	
	public void deleteMedia(int id) {
		
		setDatabase();
		
		String[] whereArgs = {Integer.toString(id)};
		
		database.delete("media", "id = ?", whereArgs);
		
	}
	
	public void updateMedia(int id, String location) {
		
		setDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put("location", location);
		
		
		String[] whereArgs = {Integer.toString(id)};
		
		database.update("media", values, "id = ?", whereArgs);
		
	}
	
	public Media selectMediaById(int id) {
		
		String[] columns = {"id", "location"};
		Media media = null;
		
		setDatabase();
		
		Cursor cursor = database.query("media", columns, "id = ?", new String[] {String.valueOf(id)}, null, null, null, null);
		if(cursor != null) {
			cursor.moveToFirst();
			media = new Media();
			media.setId(cursor.getInt(0));
			
			media.setLocation(cursor.getString(1));
			media.setType(cursor.getInt(2));
			
			
		}
		
		return media;
		
	}
	
	public ArrayList<Media> selectAllMedia() {
		
		ArrayList<Media> medias = new ArrayList<Media>();
		
		String query = "SELECT * FROM media";
		
		setDatabase();
		
		Cursor cursor = database.rawQuery(query, null);
		
		Media media = null;
		if(cursor.moveToFirst()) {
			do {
				media = new Media();
				media.setId(cursor.getInt(0));
				media.setLocation(cursor.getString(1));
				media.setType(cursor.getInt(2));
				
				medias.add(media);
				
			} while(cursor.moveToNext());
		}
		database.close();
		return medias;
		
	}
	
	public void addNote(String title, String note) {
		setDatabase();
		ContentValues values = new ContentValues();
		
		values.put("title", title);
		values.put("note", note);
			
		this.database.insert("notes", null, values);
		
	}
	
	public void deleteNote(int id) {
		
		setDatabase();
		
		String[] whereArgs = {Integer.toString(id)};
		
		database.delete("notes", "id = ?", whereArgs);
		
	}
	
	public void updateNote(int id, String title, String note) {
		
		Log.d("id", Integer.toString(id));
		
		setDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put("title", title);
		values.put("note", note);
		
		
		
		String[] whereArgs = {Integer.toString(id)};
		
		database.update("notes", values, "id = ?", whereArgs);		
	}
	
	public Note selectNoteById(int id) {
		
		String[] columns = {"id", "title", "note"};
		Note note = null;
		
		setDatabase();
		
		Cursor cursor = database.query("notes", columns, "id = ?", new String[] {String.valueOf(id)}, null, null, null, null);
		if(cursor != null) {
			cursor.moveToFirst();
			note = new Note();
			note.setId(cursor.getInt(0));
			note.setTitle(cursor.getString(1));
			note.setNote(cursor.getString(2));
		
			
			
			
		}
		
		return note;
		
	}
	
	public ArrayList<Note> selectAllNotes() {
		
		ArrayList<Note> notes = new ArrayList<Note>();
		
		String query = "SELECT * FROM notes";
		
		setDatabase();
		
		Cursor cursor = database.rawQuery(query, null);
		//Cursor cursor = database.query("notes", null, null, null, null, null, null);
		
		
		Note note = null;
		if(cursor.moveToFirst()) {
			do {
				note = new Note();
				note.setId(cursor.getInt(0));
				note.setTitle(cursor.getString(1));
				note.setNote(cursor.getString(2));
				
				notes.add(note);
				
			} while(cursor.moveToNext());
		}
		
		return notes;
		
	}
	
	public void addPassword(String website, String username, String password, String notes) {
		
		setDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put("website", website);
		values.put("username", username);
		values.put("password", password);
		values.put("notes", notes);
			
		database.insert("passwords", null, values);
		
	}
	
	public void deletePassword(int id) {
		
		setDatabase();
		
		String[] whereArgs = {Integer.toString(id)};
		
		database.delete("passwords", "id = ?", whereArgs);
		
	}
	
	public void updatePassword(int id, String website, String username, String password, String notes) {
		
		setDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put("website", website);
		values.put("username", username);
		values.put("password", password);
		values.put("notes", notes);
		
		
		String[] whereArgs = {Integer.toString(id)};
		
		database.update("passwords", values, "id = ?", whereArgs);
		
	}
	
	public Password selectPasswordById(int id) {
		
		String[] columns = {"id", "website", "username", "password", "notes"};
		Password password = null;
		
		setDatabase();
		
		
		Cursor cursor = database.query("passwords", columns, "id = ?", new String[] {String.valueOf(id)}, null, null, null, null);
		if(cursor != null) {
			cursor.moveToFirst();
			password = new Password();
			password.setId(cursor.getInt(0));
			password.setWebsite(cursor.getString(1));
			password.setUsername(cursor.getString(2));
			password.setPassword(cursor.getString(3));
			password.setNotes(cursor.getString(4));
			
		
			
			
			
		}
		
		return password;
		
	}
	
	public ArrayList<Password> selectAllPasswords() {
		
		ArrayList<Password> passwords = new ArrayList<Password>();
		
		String query = "SELECT * FROM passwords";
		
		
		setDatabase();
		
		Cursor cursor = database.rawQuery(query, null);
		
		Password password = null;
		
		if(cursor.moveToFirst()) {
			do {
				password = new Password();
				password.setId(cursor.getInt(0));
				password.setWebsite(cursor.getString(1));
				password.setUsername(cursor.getString(2));
				password.setPassword(cursor.getString(3));
				password.setNotes(cursor.getString(4));
				
				passwords.add(password);
				
			} while(cursor.moveToNext());
		}
		
		
		return passwords;
		
	}
	
	public void addReminder(String nickname, String name, String alertdate, String alerttime, String location, String notes, long millis) {
		
		setDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put("nickname", nickname);
		values.put("name", name);
		values.put("alertdate", alertdate);
		values.put("alerttime", alerttime);
		values.put("location", location);
		values.put("alertMillis", millis);
		values.put("notes", notes);
			
		database.insert("reminders", null, values);
		
	}
	
	public void deleteReminder(int id) {
		
		setDatabase();
		
		String[] whereArgs = {Integer.toString(id)};
		
		database.delete("reminders", "id = ?", whereArgs);
		
	}
	
	public void updateReminder(int id, String nickname, String name, String alertdate, String alerttime, String location, String notes, long millis) {
		
		setDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put("nickname", nickname);
		values.put("name", name);
		values.put("alertdate", alertdate);
		values.put("alerttime", alerttime);
		values.put("location", location);
		values.put("alertMillis", millis);
		values.put("notes", notes);
		
		
		String[] whereArgs = {Integer.toString(id)};
		
		database.update("reminders", values, "id = ?", whereArgs);
		
	}
	
	public Reminder selectReminderById(int id) {
		
		String[] columns = {"id", "nickname", "name", "alertdate", "alerttime", "location", "alertMillis", "notes"};
		Reminder reminder = null;	
		
		setDatabase();
		
		Cursor cursor = database.query("reminders", columns, "id = ?", new String[] {String.valueOf(id)}, null, null, null, null);
		if(cursor != null) {
			cursor.moveToFirst();
			reminder = new Reminder();
			reminder.setId(cursor.getInt(0));
			reminder.setNickname(cursor.getString(1));
			reminder.setName(cursor.getString(2));
			reminder.setAlertdate(cursor.getString(3));
			reminder.setAlerttime(cursor.getString(4));
			reminder.setLocation(cursor.getString(5));
			reminder.setAlertMillis(cursor.getLong(6));
			reminder.setNotes(cursor.getString(7));
			
			
		
			
			
			
		}
		
		return reminder;
		
	}
	
	public ArrayList<Reminder> selectAllReminders() {
		
		ArrayList<Reminder> reminders = new ArrayList<Reminder>();
		
		String query = "SELECT * FROM reminders";
		
		setDatabase();
		
		
		Cursor cursor = database.rawQuery(query, null);
		
		Reminder reminder = null;
		if(cursor.moveToFirst()) {
			do {
				reminder = new Reminder();
				reminder.setId(cursor.getInt(0));
				reminder.setNickname(cursor.getString(1));
				reminder.setName(cursor.getString(2));
				reminder.setAlertdate(cursor.getString(3));
				reminder.setAlerttime(cursor.getString(4));
				reminder.setLocation(cursor.getString(5));
				reminder.setAlertMillis(cursor.getLong(6));
				reminder.setNotes(cursor.getString(7));
				
				reminders.add(reminder);
				
			} while(cursor.moveToNext());
		}
		
		return reminders;
		
	}
	
	public void addSS(String name, String ssnumber, String notes) {
		
		setDatabase();
	
		ContentValues values = new ContentValues();
		
		values.put("name", name);
		values.put("ssnumber", ssnumber);
		values.put("notes", notes);
			
		database.insert("socialsecurity", null, values);
		
	}
	
	public void deleteSS(int id) {
		
		setDatabase();
		
		String[] whereArgs = {Integer.toString(id)};
		
		database.delete("socialsecurity", "id = ?", whereArgs);
		
	}
	
	public void updateSS(int id, String name, String ssnumber, String notes) {
		
		setDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put("name", name);
		values.put("ssnumber", ssnumber);
		values.put("notes", notes);
		
		
		String[] whereArgs = {Integer.toString(id)};
		
		database.update("socialsecurity", values, "id = ?", whereArgs);
		
	}
	
	public SocialSecurity selectSSById(int id) {
		
		String[] columns = {"id", "name", "ssnumber", "notes"};
		SocialSecurity socialsecurity = null;	
		
		setDatabase();
		
		Cursor cursor = database.query("socialsecurity", columns, "id = ?", new String[] {String.valueOf(id)}, null, null, null, null);
		if(cursor != null) {
			cursor.moveToFirst();
			socialsecurity = new SocialSecurity();
			socialsecurity.setId(cursor.getInt(0));
			socialsecurity.setName(cursor.getString(1));
			socialsecurity.setSsnumber(cursor.getString(2));
			socialsecurity.setNotes(cursor.getString(3));
									
		}
		
		return socialsecurity;
		
	}
	
	public ArrayList<SocialSecurity> selectAllSS() {
		
		ArrayList<SocialSecurity> socialsecurities = new ArrayList<SocialSecurity>();
		
		String query = "SELECT * FROM socialsecurity";
		
		setDatabase();
		
		Cursor cursor = database.rawQuery(query, null);
		
		SocialSecurity socialsecurity = null;
		if(cursor.moveToFirst()) {
			do {
				socialsecurity = new SocialSecurity();
				socialsecurity.setId(cursor.getInt(0));
				socialsecurity.setName(cursor.getString(1));
				socialsecurity.setSsnumber(cursor.getString(2));
				socialsecurity.setNotes(cursor.getString(3));
				
				socialsecurities.add(socialsecurity);
				
			} while(cursor.moveToNext());
		}
		
		return socialsecurities;
		
	}
	
}