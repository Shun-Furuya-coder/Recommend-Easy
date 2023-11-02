package application.controller;

import java.time.LocalDate;
import java.util.List;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import java.io.Serializable;

import dbUtil.DbConnection;

public class RecommendationLetter implements Serializable {



	private static final long serialVersionUID = 1l;

	private String firstName;
	private String lastName;
	private String fullName;
	private LocalDate date;
	private String programName;
	private String targetSchool;
	private String firstSemester;
	private String firstGrade;
	private String gender;
	private List<String> courseInfo;
	private List<String> academicChar;
	private List<String> personalChar;
	private List<String> semesters;
	
	public RecommendationLetter(String firstName, String lastName, LocalDate date , String gender, String programName , String firstSemester , String firstGrade , List<String> courseInfo,
			List<String> academicChar, List<String> personalChar) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.date = date;
		this.gender = gender;
		this.programName = programName;
		this.courseInfo = courseInfo;
		this.academicChar = academicChar;
		this.personalChar = personalChar;
		this.firstGrade = firstGrade;
		this.firstSemester = firstSemester;
		this.fullName = firstName+" "+lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getFirstSemester() {
		return firstSemester;
	}

	public void setFirstSemester(String firstSemester) {
		this.firstSemester = firstSemester;
	}

	public String getFirstGrade() {
		return firstGrade;
	}

	public void setFirstGrade(String firstGrade) {
		this.firstGrade = firstGrade;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<String> getCourseInfo() {
		return courseInfo;
	}

	public void setCourseInfo(List<String> courseInfo) {
		this.courseInfo = courseInfo;
	}

	public List<String> getAcademicChar() {
		return academicChar;
	}

	public void setAcademicChar(List<String> academicChar) {
		this.academicChar = academicChar;
	}

	public List<String> getPersonalChar() {
		return personalChar;
	}

	public void setPersonalChar(List<String> personalChar) {
		this.personalChar = personalChar;
	}
	public void setTargetSchool(String targetSchool) {
		this.targetSchool = targetSchool;
	}
	public String getTargetSchool() {
		return this.targetSchool;
	}

	public void setSemesters(List<String> semesters) {
		this.semesters = semesters;
	}
	public List<String> getSemesters() {
		return this.semesters;
	}

	
	public String compileTemplate() {
	    String salutation = "For: " + fullName + "\n" +
	                        "Date: " + LocalDate.now() + "\n" +
	                        "To: Graduate Admissions Committee\n\n";

	    String intro = "I am writing this letter to recommend my former student " + fullName + " who is applying " +
	                   "for the " + programName + " in your school.\n\n" +
	                   "I met " + firstName + " in " + firstSemester + " when "+ (gender.equalsIgnoreCase("Male") ? "he" : "she") +" enrolled in my \"" +
	                   courseInfo.get(0).split(",")[0] + "\" course.\n\n" +
	                   firstName + " earned" + firstGrade + " from this tough course, and this shows how " +
	                   "knowledgeable and hard worker " + (gender.equalsIgnoreCase("Male") ? "he" : "she") + " is.\n";

	    //String courses = "";
	    /*if (courseInfo.size() > 1) {
	        courses += "[" + (gender.equalsIgnoreCase("Male") ? "He" : "She") + " also earned \"" + courseInfo.get(1) + "\" from my \"" +
	                   academicChar.get(1) + "\"";
	        for (int i = 2; i < courseInfo.size(); i++) {
	            courses += ", \"" + courseInfo.get(i) + "\" from my \"" + academicChar.get(i) + "\"";
	        }
	        courses += "] " + String.join(", ", courseInfo.subList(1, courseInfo.size())) + ".\n";
	    }*/
		String courses = this.courseInfo.size()==1 ? "" : ((gender.equalsIgnoreCase("Male") ? "He" : "She") + " also earned ");
		for(int i=1; i<courseInfo.size()-1; i++) {
			courses = courses + courseInfo.get(i).split(",")[1] + " from my " + courseInfo.get(i).split(",")[0] + ",";
		}
		if(courseInfo.size()!=1) {
			courses = courses + courseInfo.get(courseInfo.size()-1).split(",")[1] + " from my "+ courseInfo.get(courseInfo.size()-1).split(",")[0]+ ".\n\n";
		}

	    String academic = firstName + " " + String.join(", ", academicChar) + ".\n";

	    String personal = (gender.equalsIgnoreCase("Male") ? "He" : "She") + " was always " + String.join(", ", personalChar) + ".\n\n";

	    String termProject = "Furthermore, I noticed from the term project result, " + (gender.equalsIgnoreCase("Male") ? "he" : "she") +
	                         " developed leadership, time management, and problem-solving skills. " +
	                         (gender.equalsIgnoreCase("Male") ? "He" : "She") + " worked effectively with the team members and " +
	                         "delegated tasks appropriately. They were able to deliver a successful project " +
	                         "in a timely fashion.\n\n";
		String profName = "" , profPhone = "" , profTitle = "" , profEmail = "", profSchool = "", profDept = "";
		try {

			Connection connection = DbConnection.getConnection();

			Statement statement = connection.createStatement();

			ResultSet result = statement.executeQuery("SELECT * FROM user_info");

			result.next();

			profName = result.getString(2);
			profEmail = result.getString(4);
			profPhone = result.getString(5);
			profTitle = result.getString(6);
			profSchool = result.getString(7);
			profDept = result.getString(8);

		}
		catch(Exception ex) {

		}

	    String recommendation = "I believe that " + firstName + " has the capacity to excel at higher education " +
	                            "program and this is my pleasure to highly recommend " + (gender.equalsIgnoreCase("Male") ? "him" : "her") + ".\n\n" +
	                            "Please do not hesitate to contact me with further questions.\n\n" +
	                            "Very Respectfully,\n" +
	                            profName+"\n" +
	                            profTitle+"\n" +
	                            profSchool + ", " + profDept+"\n" +
	                            profEmail+"\n" +
	                            profPhone;

	    return salutation + intro + courses + academic + personal + termProject + recommendation;
	}
}