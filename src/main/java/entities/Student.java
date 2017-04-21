package entities;

import java.io.Serializable;
import java.sql.Date;

public class Student implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private Date birthday;
	private String description;
	private Integer avgscore;

	public Student(String name, Date birthday, String description,
			Integer avgscore) {
		super();
		this.name = name;
		this.birthday = birthday;
		this.description = description;
		this.avgscore = avgscore;
	}

	public Student(String id, String name, Date birthday, String description,
			Integer avgscore) {
		this(name, birthday, description, avgscore);
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getAvgscore() {
		return avgscore;
	}

	public void setAvgscore(Integer avgscore) {
		this.avgscore = avgscore;
	}

	@Override
	public String toString() {
		return this.id + "&" + this.name + "&" + this.birthday + "&"
				+ this.description + "&" + this.avgscore;
	}
}
