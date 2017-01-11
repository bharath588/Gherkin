package mobile;

/**
 * Command Prompt - this class contains method to run windows and mac commands
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CommandPrompt {

    static Process p;
   
    private List<String> cmd = new ArrayList<String>();
    ProcessBuilder builder;
    public static void main(String s[]) throws InterruptedException, IOException{
    	String[] cmd = {"ps -ax" };	
    	   String process = "ps -ax|grep -i \"iproxy\"|grep -v grep";
    	System.out.println(new CommandPrompt().runCommand1("ps -ax|grep -i 'iproxy'"));
    }


    public String runCommand(String[] command) throws InterruptedException, IOException {
    	cmd = Arrays.asList(command);     
        BufferedReader r = getBufferedReaderTest(cmd);
        String line = "";
        String allLine = "";
        int i = 1;
        while ((line = r.readLine()) != null) {
            allLine = allLine + "" + line + "\n";
            if (line.contains("Console LogLevel: debug") && line.contains("Complete")) {
                break;
            }
            i++;
        }
        System.out.println(allLine);
        return allLine;

    }
    
    public String runCommand1(String command) throws InterruptedException, IOException {
        p = Runtime.getRuntime().exec(command);
        // get std output
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = "";
        String allLine = "";
        int i = 1;
        while ((line = r.readLine()) != null) {
            allLine = allLine + "" + line + "\n";
            if (line.contains("Console LogLevel: debug") && line.contains("Complete")) {
                break;
            }
            i++;
        }
        return allLine;

    }
    
    public BufferedReader getBufferedReaderTest(List<String> cmd2) throws IOException {
        List commands = new ArrayList<>();
        commands.add("/usr/local/bin/"+cmd2.get(0));
     //   commands.add("-c");
        for(int i =1; i < cmd2.size();i++){
        commands.add(cmd2.get(i));
        }
        ProcessBuilder builder = new ProcessBuilder(commands);
        Map<String, String> environ = builder.environment();

        final Process process = builder.start();
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        return new BufferedReader(isr);
    }

    public String runCommandThruProcessBuilder(String command)
        throws InterruptedException, IOException {
        BufferedReader br = getBufferedReader(command);
        String line;
        String allLine = "";
        while ((line = br.readLine()) != null) {
            allLine = allLine + "" + line + "\n";
            System.out.println(allLine);
        }
        return allLine.split(":")[1].replace("\n", "").trim();
    }

    public String runProcessCommandToGetDeviceID(String command)
        throws InterruptedException, IOException {
        BufferedReader br = getBufferedReader(command);
        String line;
        String allLine = "";
        while ((line = br.readLine()) != null) {
            allLine = allLine.trim() + "" + line.trim() + "\n";           
        }
        return allLine.trim();
    }

    public BufferedReader getBufferedReader(String command) throws IOException {
        List<String> commands = new ArrayList<>();
        commands.add("/bin/sh");
        commands.add("-c");
        commands.add(command);
        ProcessBuilder builder = new ProcessBuilder(commands);
        Map<String, String> environ = builder.environment();

        final Process process = builder.start();
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        return new BufferedReader(isr);
    }

    public void runCommandThruProcess(String command)
        throws InterruptedException, IOException {
        BufferedReader br = getBufferedReader(command);
        String line;
        String allLine = "";
        while ((line = br.readLine()) != null) {
            allLine = allLine + "" + line + "\n";
            System.out.println(allLine);
        }
    }
}
