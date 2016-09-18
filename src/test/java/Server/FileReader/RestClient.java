package Server.FileReader;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;

public class RestClient {

	public int getLineNumber(String lineNumber) {
		try {
			Client client = ClientBuilder.newClient(new ClientConfig().register(LoggingFilter.class));
			WebTarget webTarget = client.target("http://localhost:2222/").path("lines").path(lineNumber);

			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_XML);
			javax.ws.rs.core.Response response = invocationBuilder.get();
			System.out.println(response.getStatus());

			String output = response.readEntity(String.class);

			System.out.println("Output from Server .... \n");
			System.out.println(output);
			return response.getStatus();

		} catch (Exception e) {

			e.printStackTrace();

		}
		return 0;

	}
}
