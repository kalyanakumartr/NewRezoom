package org.hbs.core.beans.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hbs.core.util.ICRUDBean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "country")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Country extends DisplayOrderAndStatus implements ICRUDBean, Comparable<Country>
{

	private static final long	serialVersionUID	= 8372130046238222330L;
	protected String			country;
	protected String			countryName;
	public Country()
	{
		super();
	}

	public Country(String country, String countryName, boolean status)
	{
		super();
		this.country = country;
		this.countryName = countryName;
		this.status = status;
	}

	@Id
	@Column(name = "country")
	public String getCountry()
	{
		return country;
	}

	@Column(name = "countryName")
	public String getCountryName()
	{
		return countryName;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public void setCountryName(String countryName)
	{
		this.countryName = countryName;
	}

	@Override
	public int compareTo(Country country)
	{
		return countryName.compareTo(country.getCountryName());
	}
}
