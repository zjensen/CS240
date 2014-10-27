package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import server.facade.Server_Facade;
import server.handler.*;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Server 
{
	private static int SERVER_PORT_NUMBER = 8080;
	private static final int MAX_WAITING_CONNECTIONS = 10;
	
	private static Logger logger;
	
	static 
	{
		try 
		{
			initLog();
		}
		catch (IOException e) 
		{
			System.out.println("Could not initialize log: " + e.getMessage());
		}
	}
	
	private static void initLog() throws IOException {
		
		Level logLevel = Level.FINE;
		
		logger = Logger.getLogger("server"); 
		logger.setLevel(logLevel);
		logger.setUseParentHandlers(false);
		
		Handler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(logLevel);
		consoleHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(consoleHandler);

		FileHandler fileHandler = new FileHandler("log.txt", false);
		fileHandler.setLevel(logLevel);
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
	}
	
private HttpServer server;
	
	private Server() 
	{
		return;
	}
	private Server(int port)
	{
		Server.SERVER_PORT_NUMBER = port;
	}
	private void run() 
	{
		
		logger.info("Initializing Model");
		
		try 
		{
			Server_Facade.initialize();		
		}
		catch (ServerException e) 
		{
			logger.log(Level.SEVERE, e.getMessage(), e);
			return;
		}
		
		logger.info("Initializing HTTP Server");
		
		try {
			server = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER),MAX_WAITING_CONNECTIONS);
		} 
		catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);			
			return;
		}

		server.setExecutor(null); // use the default executor
		
		server.createContext("/validateUser", vuh);
		server.createContext("/getProjects",gph);
		server.createContext("/getSampleImage",gsih);
		server.createContext("/downloadBatch",dbh);
		server.createContext("/getFields",gfh);
		server.createContext("/submitBatch",sbh);
		server.createContext("/search",sh);
		server.createContext("/records",dfh);
		
		logger.info("Starting HTTP Server");

		server.start();
	}

	private HttpHandler vuh = new ValidateUser_Handler();
	private HttpHandler gph = new GetProjects_Handler();
	private HttpHandler gsih = new GetSampleImage_Handler();
	private HttpHandler dbh = new DownloadBatch_Handler();
	private HttpHandler gfh = new GetFields_Handler();
	private HttpHandler sbh = new SubmitBatch_Handler();
	private HttpHandler sh = new Search_Handler();
	private HttpHandler dfh = new DownloadFile_Handler();
	
	public static void main(String[] args) 
	{
		if(args[0].isEmpty())
		{
			new Server().run();
		}
		else
		{
			new Server(Integer.valueOf(args[0])).run();
		}
	}
}
