#include <HashMap.h>
#include <XBee.h>

//const byte HASH_SYZE = 10;

//HashType<char*,char*> hashRawArray[HASH_SYZE];
//HashMap<char*,char*> hashmap = HashMap<char*,char*>( hashRawArray , HASH_SYZE);


XBee xbee = XBee();
uint8_t payload[] = { 0, 0 };

XBeeAddress64 addr64 = XBeeAddress64(0x0013a200, 0x40a59dd6); //ENDEREÇO 64 BITS DO COORDENADOR
ZBTxRequest zbTx = ZBTxRequest(addr64, payload, sizeof(payload));




//void enviarCSR{
  //ESTA FUNÇÃO É RESPONSÁVEL PELO PROCESSO DO CREATE SOURCE ROUTE
//}

//int processaDados(){
//  hashMap[0]("0x01","0x02");
//  hashMap[1]("0x03","0x04");
//}

//void processa(){
//  //ESTA FUNÇÃO ESTA REALIZA: (GERAR DADOS), (ENVIAR E RECEBER DADOS)
//}

void setup(){
  Serial.begin(9600);
  xbee.setSerial(Serial);
}

void loop(){  

  xbee.send(zbTx);
  
  /*
  if(){ //NÃO EXISTE ROTA
    processa();    
  }
  
 xbee.readPacket();
 
 if (xbee.getResponse().isAvailable){
 
   if (xbee.getResponse().getApiId() == ZB_EXPLICIT_TX_REQUEST){
     //RECEBENDO DADOS DO COORDENADOR
   }
   
 } 
  */
}




