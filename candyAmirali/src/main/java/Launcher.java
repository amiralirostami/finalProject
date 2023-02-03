import java.awt.*;
import java.io.Console;
import java.io.IOException;

/**
 * UI console
 */
public class Launcher {
    public static void main(String[] args) {
        Console console = System.console();
        if(console == null && !GraphicsEnvironment.isHeadless()){
            String filename = Main.class.getProtectionDomain().getCodeSource().getLocation().toString().substring(6);
            try {
                Runtime.getRuntime().exec(new String[]{"cmd","/c","start","cmd","/k","java -jar \"" + filename + "\""});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Main.main(new String[0]);
            System.out.println("Program has ended, please type 'exit' to close the console");
        }
    }

}
