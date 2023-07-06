package br.com.empresa.exemploQRCode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import org.json.JSONObject;

public class Auth {	

	public final String client_id = "Client_Id_1aa3fb7f7e106e00aa73f65fab8b309e5bd189fd";
	public final String client_secret = "Client_Secret_28d820b044fd200786308b14ff3c59303cf6633f";	             	
	public final String basicAuth = Base64.getEncoder().encodeToString(((client_id+':'+client_secret).getBytes()));

	public String geraToken() {
		String access_token = "";

		try {
			// Diretório em que seu certificado em formato .p12 deve ser
			// inserido
			System.setProperty("javax.net.ssl.keyStore", "CASA_DIARIA_H.p12");
			SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();

			URL url = new URL("https://api-pix-h.gerencianet.com.br/oauth/token"); // Homologação
			
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "Basic " + basicAuth);
			conn.setSSLSocketFactory(sslsocketfactory);
			String input = "{\"grant_type\": \"client_credentials\"}";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			InputStreamReader reader = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(reader);

			String response;
			
			StringBuilder responseBuilder = new StringBuilder();
						
			while ((response = br.readLine()) != null) {
				System.out.println(response);
				responseBuilder.append(response);
			}
			
			try {
				JSONObject jsonObject = new JSONObject(responseBuilder.toString());
				access_token = jsonObject.getString("access_token");
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();			
		}
		
		return access_token;

	}
}
