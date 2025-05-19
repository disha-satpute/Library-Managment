import ui.LoginForm;

public class Main {
    public static void main(String[] args) {
        new LoginForm().setVisible(true);
    }
}

/*

javac -d bin -cp lib/mysql-connector-java-8.0.30.jar (Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName })
java -cp "bin;lib/mysql-connector-java-8.0.30.jar" Main

*/