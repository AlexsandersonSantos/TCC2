package com.TCC2.sistema;

import java.util.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.rapplogic.xbee.api.ApiId;
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeAddress16;
import com.rapplogic.xbee.api.XBeeAddress64;
import com.rapplogic.xbee.api.XBeePacket;
import com.rapplogic.xbee.api.XBeeResponse;
import com.rapplogic.xbee.api.wpan.TxRequest16;
import com.rapplogic.xbee.api.wpan.TxRequest64;
import com.rapplogic.xbee.api.wpan.TxStatusResponse;
import com.rapplogic.xbee.api.zigbee.ZNetExplicitTxRequest;
import com.rapplogic.xbee.api.zigbee.ZNetTxRequest;
import com.rapplogic.xbee.api.zigbee.ZNetTxStatusResponse;
import com.rapplogic.xbee.util.DoubleByte;
import com.rapplogic.xbee.api.RemoteAtRequest;
import com.rapplogic.xbee.api.RemoteAtResponse;

public class Sistema {
	
	
	
	private final static Logger log = Logger.getLogger(Sistema.class);
	
	private Sistema() throws Exception{	
		
				
		
		XBee xbee = new XBee();
		
	    String desconhecido = "0xFFFE";
	    
		   
	    //Seta o endereço de 16 bits desconhecido para o endereço de 64 bits (INICIALMENTE)
	    
	    Hashtable balance = new Hashtable();
	    Enumeration names;
	    String str;
	    
	    balance.put("0013a20040a59ed5", new String(desconhecido));
	    balance.put("0013a20040a59dd6", new String(desconhecido));
	    
	    String addr16 = "0x01, 0x02";
		
	    XBeeAddress64 endereco = new XBeeAddress64(0, 0x13, 0xa2, 0, 0x40, 0xa5, 0x9e, 0xd5);
		
		try{
		
			
			xbee.open("COM4",9600);
			
			
			
			//--------------------------------------------ENVIA SOLICITAÇÃO DE ENDEREÇO DE 16 BITS------------------------------------------------------
			
			XBeeAddress64 addr64 = new XBeeAddress64(0, 0x13, 0xa2, 0, 0x40, 0xa5, 0x9e, 0xd5);
			
			RemoteAtRequest request = new RemoteAtRequest(addr64, "OI");
			RemoteAtResponse response2 = (RemoteAtResponse) xbee.sendSynchronous(request, 10000);
				
			String dado2 = response2.toString();
			String[] s = dado2.split("=");						
					
			addr16 = s[10];					
					
			System.out.println("ENDEREÇO DE 16 BITS:  "+ addr16);
					
			//ENDEREÇO DE 64 BITS
					
			String sep2 = s[9];
			String[] s2 = sep2.split(",");
					
			String add64 = s2[0] + "," + s2[1] + "," + s2[2] + "," + s2[3] + "," + s2[4] + "," + s2[5] + "," + s2[6] + "," + s2[7];
					
			System.out.println("ENDEREÇO DE 64 BITS: "+ add64);
			
			//-------------------------------------APÓS A SOLICITAÇÃO DO ENDEREÇO DE 16 BITS GUARDA EM UMA ESTRUTURA DE DADOS-------------------------------------------	

		      
		    if(add64 != null){
		      balance.put("0013a20040a59ed5", new String(addr16));
		    }else{
		      balance.put("0013a20040a59ed5", new String(desconhecido));
		    }
		      

		    // --------------------------------- aqui é um teste para a visualização dos dados da tabela hash -----------------------------------------------------------
		    
		    names = balance.keys();
		    while(names.hasMoreElements()) {
		       str = (String) names.nextElement();
		       System.out.println(str + ": " +
		       balance.get(str));
		    }
		    System.out.println();
		    
		    
		    //-----------------------------------------------ENVIO UMA MENSAGEM PARA O NO ROTEADOR COM AS DECISÕES -----------------------------------------------------
		    
		    
		    ZNetTxRequest r = new ZNetTxRequest(endereco, new int[] {'H','I'});
		    
		    xbee.sendAsynchronous(r);
		    
		    ZNetTxStatusResponse response = new ZNetTxStatusResponse();
			
		    if(response.isSuccess()){
		    	System.out.println("PACOTE ENTREGUE COM SUCESSO");
		    	System.out.println("PACOTE: "+ response);
		    }else{
		    	System.out.println("OCORREU ALGUM ERRO: "+ response);
		    }
			
		
		}finally {
			xbee.close();
		}
	}
		
		
		
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		// Create a hash map
		PropertyConfigurator.configure("log4j.properties");
		
	      //HashTable();
	      
	      new Sistema();
	      
	}

}