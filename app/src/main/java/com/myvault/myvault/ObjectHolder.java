package com.myvault.myvault;

import java.io.Serializable;

public class ObjectHolder implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private Account account;
	private Bookmark bookmark;
	private Car car;
	private Contact contact;
	private CreditCard creditCard;
	private Insurance insurance;
	private Media media;
	private Note note;
	private Password password;
	private Reminder reminder;
	private SocialSecurity socialSecurity;
	
	
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Bookmark getBookmark() {
		return bookmark;
	}
	public void setBookmark(Bookmark bookmark) {
		this.bookmark = bookmark;
	}
	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public CreditCard getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	public Insurance getInsurance() {
		return insurance;
	}
	public void setInsurance(Insurance insurance) {
		this.insurance = insurance;
	}
	public Media getMedia() {
		return media;
	}
	public void setMedia(Media media) {
		this.media = media;
	}
	public Note getNote() {
		return note;
	}
	public void setNote(Note note) {
		this.note = note;
	}
	public Password getPassword() {
		return password;
	}
	public void setPassword(Password password) {
		this.password = password;
	}
	public Reminder getReminder() {
		return reminder;
	}
	public void setReminder(Reminder reminder) {
		this.reminder = reminder;
	}
	public SocialSecurity getSocialSecurity() {
		return socialSecurity;
	}
	public void setSocialSecurity(SocialSecurity socialSecurity) {
		this.socialSecurity = socialSecurity;
	}
	
	
	
	
}
