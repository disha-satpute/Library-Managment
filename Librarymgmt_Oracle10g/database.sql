-- DATABASE STRUCTURE

-- Create Lib_users table
CREATE TABLE Lib_users (
    user_id VARCHAR2(20) PRIMARY KEY,
    password VARCHAR2(20) NOT NULL,
    role VARCHAR2(20) CHECK (role IN ('admin', 'student'))
);

-- Insert Admin and Student Users
INSERT INTO Lib_users (user_id, password, role) VALUES ('admin', 'admin123', 'admin');
INSERT INTO Lib_users (user_id, password, role) VALUES ('student1', 'stud123', 'student');
INSERT INTO Lib_users (user_id, password, role) VALUES ('student2', 'stud456', 'student');

-- Verify inserted data
SELECT * FROM Lib_users;

-- Create books table
CREATE TABLE Lib_books (
    book_id VARCHAR2(10) PRIMARY KEY,
    title VARCHAR2(100) NOT NULL,
    author VARCHAR2(50),
    publisher VARCHAR2(50),
    quantity NUMBER DEFAULT 0 CHECK (quantity >= 0)
);

-- Insert books
INSERT INTO Lib_books (book_id, title, author, publisher, quantity) VALUES 
('B001', 'Java Programming', 'Herbert Schildt', 'McGraw Hill', 5);

INSERT INTO Lib_books (book_id, title, author, publisher, quantity) VALUES 
('B002', 'Effective Java', 'Joshua Bloch', 'Addison-Wesley', 3);

-- Verify inserted data
SELECT * FROM Lib_books;

-- Create issued_books table with correct foreign key references
CREATE TABLE issued_books (
    issue_id NUMBER PRIMARY KEY,
    student_id VARCHAR2(20),
    book_id VARCHAR2(10),
    issue_date DATE DEFAULT SYSDATE,
    return_status VARCHAR2(10) DEFAULT 'pending',
    
    -- Foreign key to Lib_users (user_id)
    CONSTRAINT fk_student FOREIGN KEY (student_id) REFERENCES Lib_users(user_id),
    
    -- Foreign key to Lib_books (book_id)
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES Lib_books(book_id)
);

-- Create sequence for issue_id
CREATE SEQUENCE issue_seq START WITH 1 INCREMENT BY 1;

-- Insert into issued_books table
INSERT INTO issued_books (issue_id, student_id, book_id)
VALUES (issue_seq.NEXTVAL, 'student1', 'B001');
