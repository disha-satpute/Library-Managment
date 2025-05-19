package model;

import java.util.Date;

public class Issue {
    private int issueId;
    private String studentId;
    private String bookId;
    private Date issueDate;
    private String returnStatus;

    public Issue() {
        // Default constructor
    }

    public Issue(int issueId, String studentId, String bookId, Date issueDate, String returnStatus) {
        this.issueId = issueId;
        this.studentId = studentId;
        this.bookId = bookId;
        this.issueDate = issueDate;
        this.returnStatus = returnStatus;
    }

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }
}
