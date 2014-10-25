package server.handler;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

import server.DatabaseException;
import server.facade.Server_Facade;
import shared.communication.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GetFields_Handler implements HttpHandler 
{
	private Logger logger = Logger.getLogger("GerFields_Handler");
	@Override
	public void handle(HttpExchange exchange) throws IOException 
	{
		Server_Facade facade = new Server_Facade();
		XStream xs = new XStream(new DomDriver());
		BufferedInputStream bis = new BufferedInputStream(exchange.getRequestBody());
		GetFields_Params params = (GetFields_Params)xs.fromXML(bis);
		bis.close();
		GetFields_Result result = null;
		try
		{
			result = facade.getFields(params);
			exchange.sendResponseHeaders(200, 0);
			OutputStream response = exchange.getResponseBody();
			xs.toXML(result, response);
			response.close();
		}
		catch(DatabaseException e)
		{
			logger.info(e.getMessage());
			throw new IOException(e);
		}
	}

}
