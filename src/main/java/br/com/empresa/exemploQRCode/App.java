package br.com.empresa.exemploQRCode;


public class App 
{
    public static void main( String[] args )
    {
        // Variable of Authentication
    	Auth auth = new Auth();    	
    	String access_token; 
    	
    	// Variable Cob
    	Cob cobranca = new Cob();
    	String resultCob;
    	int idCob = 0;
    	
    	// Variable QrCode
    	Loc loc = new Loc();
    	String resultLoc;
    	String qrCode="";
    	String image="";
    	String imageName;
    	
    	// ------------------------------------- 
    	// Authentication
    	access_token =  auth.geraToken();
    	System.out.println("Access_Token: " + access_token );
    	
    	// -------------------------------------
    	// Create Cob
    	resultCob = cobranca.doCob(access_token);
    	idCob = cobranca.getIdCob(resultCob);
    	System.out.println("idCobranca: " + idCob);
    	
    	//Emissão do QRCode de um location
    	resultLoc = loc.genQrCode(idCob, access_token);
    	qrCode = loc.getQrCode(resultLoc);
    	System.out.println("qrCode = "+qrCode);
    	
    	//----------------------------------------------------
    	//Salvar e exibir a imagem do QRCode
    	image = loc.getImage(resultLoc);
    	System.out.println("image = "+image);
    	imageName=loc.saveImage(image); //salvando a imagem
    	loc.showImage(imageName); //exibindo a imagem
    	
    }
}
