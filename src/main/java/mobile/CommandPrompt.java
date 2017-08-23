package mobile;

/**
 * Command Prompt - this class contains method to run windows and mac commands
 * @author sddprd
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
    	String[] cmd = {"sh ","ps -ax","grep -i '8100'" };	
    	   String process = "ps -ax|grep -i \"8100\"|grep -v grep";
   	System.out.println(new CommandPrompt().runCommand(cmd));
 
    }


    /**
     * Run Unix command in terminal
     * @param command Unix command to execute in Mac machine
     * @return   status of command 
     * @throws InterruptedException If interrupt  exception occur
     * @throws IOException If i/O exception occur
     */
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


    /**
     * Run Unix command in terminal
     * @param command  Unix command to execute in Mac machine
     * @return  
     * @throws InterruptedException
     * @throws IOException
     */
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
    /**
     * Return buffer reader input stream for list of command  
     * @param cmd2  Unix command to execute in Mac machine
     * @return Buffer reader 
     * @throws IOException If i/O exception occur
     */
    
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

    /**
     *  Run Process in Command terminal to get Device ID details
     * @param command  Unix command to execute in Mac machine
     * @return get device udid 
     * @throws InterruptedException If interrupt exception occur
     * @throws IOException If i/O exception occur
     */
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

    /**
     * Return buffer reader input stream for command  
     * @param cmd2  Unix command to execute in Mac machine
     * @return
     * @throws IOException- If i/O exception occur
     */
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

    /**
     *  Run Process in Command terminal to get Device ID details
     * 
     */
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
