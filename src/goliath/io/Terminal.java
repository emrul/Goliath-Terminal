package goliath.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Terminal
{
    private Process shell;
    private PrintWriter writer;
    private Scanner reader;
    private Scanner errorReader;
    private File workingDir;
    private String commandString;
    
    public Terminal()
    {   
        workingDir = new File("./");
        commandString = null;
    }
    public void startCommand()
    {   
        try
        {
            shell = Runtime.getRuntime().exec(commandString, null, workingDir);
        }
        catch (IOException ex)
        {
            Logger.getLogger(Terminal.class.getName()).log(Level.SEVERE, null, ex);
        }
        writer = new PrintWriter(shell.getOutputStream());
        reader = new Scanner(shell.getInputStream());
        errorReader = new Scanner(shell.getErrorStream()); 
    }
    public void print(String text)
    {
        writer.print(text);
    }
    public void println(String text)
    {
        writer.write(text);
        writer.flush();
    }
    public void flushInput()
    {
        writer.flush();
    }
    public void waitForExit()
    {
        try
        {
            shell.waitFor();
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(Terminal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void terminate()
    {
        writer.close();
        reader.close();
        errorReader.close();
        shell.destroy();   
    }
    public int getExitCode()
    {
        return shell.exitValue();
    }
    public PrintWriter getCommandPrinter()
    {
        return writer;
    }
    public Scanner getCommandReader()
    {
        return reader;
    }
    public Scanner getCommandErrorReader()
    {
        return errorReader;
    }
    public File getWorkingDirectory()
    {
        return workingDir;
    }
    public OutputStream getOutputStream()
    {
        return shell.getOutputStream();
    }
    public InputStream getInputStream()
    {
        return shell.getInputStream();
    }
    public InputStream getErrorStream()
    {
        return shell.getErrorStream();
    }
    public void setCommand(String cmd)
    {
        commandString = cmd;
    }
    public void setWorkingDirectory(File dir)
    {
        workingDir = dir;
    }
}