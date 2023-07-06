package br.com.empresa.exemploQRCode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

import com.mifmif.common.regex.Generex;

public class Cob {
	
	public String doCob(String token){
		
		String payload;
		String payloadB;
    
    	 
    	// Cobrança
    	StringBuilder responseBuilder = new StringBuilder();
    	HttpsURLConnection conn = null;
    	
    	try {
    		String txid;
    		//^[a-zA-Z0-9]{26,35}$ -- NÃO PRECISAMOS NO  ^ E O $
    		Generex generex = new Generex("[a-zA-Z0-9]{26,35}");
    		txid = generex.random();
    		
    		payloadB = "{\r\n"
			 + " \"calendario\": {\r\n"
			 + " \"expiracao\": 3600\r\n"
			 + "},\r\n"
			 + "\"devedor\": {\r\n"
			 + " \"cpf\": \"12345678909\",\r\n"
			 + " \"nome\": \"Francisco da Silva\"\r\n"
			 + "},\r\n"
			 + "\"valor\": {\r\n"
			 + "\"original\": \"0.10\"\r\n"
			 + " },\r\n"
			 + "\"chave\": \"7c6e5c39-5edf-41ea-a2c4-dc1a9bb13bdc\",\r\n"
			 + " \"solicitacaoPagador\": \"Cobrança dos serviços prestados.\"\r\n"
			+ "}";
    		
    		payload = "{\r\n"
        			+ "  \"calendario\": {\r\n"
        			+ "    \"expiracao\": 3600\r\n"
        			+ "  },\r\n"
        			+ "  \"devedor\": {\r\n"
        			+ "    \"cpf\": \"02138568911\",\r\n"
        			+ "    \"nome\": \"Francis Carlos Pinheiro Bueno\"\r\n"
        			+ "  },\r\n"
        			+ "  \"valor\": {\r\n"
        			+ "    \"original\": \"0.05\"\r\n"
        			+ "  },\r\n"        			  
        			+ "  \"chave\": \"7c6e5c39-5edf-41ea-a2c4-dc1a9bb13bdc\",\r\n"        			
        			+ "  \"solicitacaoPagador\": \"Serviço realizado Central Diarista.\",\r\n"
        			+ "  \"infoAdicionais\": [\r\n"
        			+ "    {\r\n"
        			+ "      \"nome\": \"Campo 1\",\r\n"
        			+ "      \"valor\": \" 1580 \"\r\n"
        			+ "    },\r\n"
        			+ "    {\r\n"
        			+ "      \"nome\": \"Campo 2\",\r\n"
        			+ "      \"valor\": \" seq-1 \"\r\n"
        			+ "    }\r\n"
        			+ "  ]\r\n"
        			+ "}";
    		    	
    		URL url = new URL ("https://api-pix-h.gerencianet.com.br/v2/cob/"+txid);               
            conn = (HttpsURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer "+ token);
           
            OutputStream os = conn.getOutputStream();
            os.write(payload.getBytes());
            os.flush();     
            
            InputStreamReader reader = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(reader);

            String response;
            
            System.out.println("Enviando: " + payload);
            
            while ((response = br.readLine()) != null) {
              System.out.println("recebido: " + response);
              responseBuilder.append(response);
            }
            conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return responseBuilder.toString();
	}
	
	public int getIdCob(String cob) {
		int id = 0;
    	try {
    		JSONObject jsonObject = new JSONObject(cob);
			JSONObject loc = (JSONObject) jsonObject.get("loc");
			id = loc.getInt("id");
		} catch (Exception e) {
			System.out.println("Erro na obtenção do id da Cobrança");
			e.printStackTrace();
		}
		return id;
	}
	
}
