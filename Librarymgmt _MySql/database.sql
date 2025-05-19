CREATE DATABASE librarydb;
USE librarydb;
-- Paste the converted SQL schema here.
-- Create Lib_users table
CREATE TABLE Lib_users (
    user_id VARCHAR(20) PRIMARY KEY,
    password VARCHAR(20) NOT NULL,
    role ENUM('admin', 'student')
);

-- Insert Admin and Student Users
INSERT INTO Lib_users (user_id, password, role) VALUES ('admin', 'admin123', 'admin');
INSERT INTO Lib_users (user_id, password, role) VALUES ('student1', 'stud123', 'student');
INSERT INTO Lib_users (user_id, password, role) VALUES ('student2', 'stud456', 'student');

-- Create books table
CREATE TABLE Lib_books (
    book_id VARCHAR(10) PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(50),
    publisher VARCHAR(50),
    quantity INT DEFAULT 0 CHECK (quantity >= 0)
);

-- Insert books
INSERT INTO Lib_books (book_id, title, author, publisher, quantity) VALUES 
('B001', 'Java Programming', 'Herbert Schildt', 'McGraw Hill', 5),
('B002', 'Let Us C', 'Yashavant Kanetkar', 'BPB', 3);


INSERT INTO Lib_books (book_id, title, author, publisher, quantity) VALUES 
('B003', 'Python Programming', 'Guido van Rossum', 'O\'Reilly Media', 4),
('B004', 'Data Structures and Algorithms', 'Thomas H. Cormen', 'MIT Press', 7),
('B005', 'Introduction to Algorithms', 'Cormen, Leiserson, Rivest, Stein', 'MIT Press', 10),
('B006', 'Clean Code', 'Robert C. Martin', 'Prentice Hall', 3),
('B007', 'The Pragmatic Programmer', 'Andrew Hunt', 'Addison-Wesley', 6),
('B008', 'Database Systems', 'Ramez Elmasri, Shamkant B. Navathe', 'Pearson', 5),
('B009', 'Operating System Concepts', 'Abraham Silberschatz', 'Wiley', 8),
('B010', 'Artificial Intelligence: A Modern Approach', 'Stuart Russell, Peter Norvig', 'Pearson', 4);


CREATE TABLE issued_books (
    issue_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id VARCHAR(20),
    book_id VARCHAR(10),
    issue_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    return_status VARCHAR(10) DEFAULT 'pending',

    CONSTRAINT fk_student FOREIGN KEY (student_id) REFERENCES Lib_users(user_id),
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES Lib_books(book_id)
);


INSERT INTO issued_books (student_id, book_id)
VALUES ('student1', 'B001');
