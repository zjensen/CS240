package server.handler;

import java.io.File;
import java.io.OutputStream;
import java.util.logging.Logger;

import server.facade.Server_Facade;
import shared.communication.DownloadFile_Params;
import shared.communication.DownloadFile_Result;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class DownloadFile_Handler implements HttpHandler
{
	private Logger logger = Logger.getLogger("DownloadFile_Handler");
	@Override
	public void handle(HttpExchange exchange)
	{
		logger.info("DownloadFile Handler");
		Server_Facade facade = new Server_Facade();
		String url;
		try 
		{
			url = new File("").getAbsolutePath() + exchange.getRequestURI().getPath();
			DownloadFile_Result result = null;
			DownloadFile_Params params= new DownloadFile_Params(url);
			result = facade.downloadFile(params);
			OutputStream response = exchange.getResponseBody();
			exchange.sendResponseHeaders(200, 0);
			response.write(result.getFileBytes());
			response.close();
		} 
		catch (Exception e)
		{
			//System.out.println( e.getClass().getName() + ": " + e.getMessage() );
			return;
		}
	}
}
