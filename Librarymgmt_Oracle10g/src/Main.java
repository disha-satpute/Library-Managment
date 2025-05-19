import ui.LoginForm;

public class Main {
    public static void main(String[] args) {
        new LoginForm().setVisible(true);
    }
}

/*

javac -d bin -cp lib/ojdbc11.jar (Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName })
java -cp "bin;lib/ojdbc11.jar" Main

*/