package prng;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Random;

public class Proposal03 {
     public int round;
    
   
   public Proposal03(int i){
       this.round=i;
   }
   
 
    public byte [] GenwithLFSR(int nbreround, byte [] bytestab ){
    
        int b1,b2,b3,b4,b5,b6,xoredbit, round = 0;
        byte [] bytesinitialtab = bytestab;
        byte [] bytesresulttab = new byte [this.round];
       
        
        
       // printBytes(bytestab);
        
        bytesinitialtab[1] = XorReg(bytesinitialtab[1],bytesinitialtab[6] );
        bytesinitialtab[3] = XorReg(bytesinitialtab[3],bytesinitialtab[7] );
        
        
        while (round < nbreround ){   
            
            for (int i=0; i<8; i++){
                b1=converttobit(0,i,bytesinitialtab); b2=converttobit(1,i,bytesinitialtab);   
                b3=converttobit(2,i,bytesinitialtab); b4=converttobit(3,i,bytesinitialtab); 
                b5=converttobit(4,i,bytesinitialtab); b6=converttobit(5,i,bytesinitialtab);
                
                b2=FlipOnebit(b2); b5=FlipOnebit(b5);
                
                xoredbit= (b1^b2^b3)^(b4^b5^b6);
                
                bytesresulttab=Filltabres(bytesresulttab,round, i, xoredbit);
                
                bytesinitialtab=rotateLeft(bytesinitialtab, 48, 1,xoredbit);
            }
            
        round++;
        }
        
     //   printBytes( bytesresulttab);
       
    return bytesresulttab;
    }
    
     public byte XorReg (byte reg1 , byte reg2){
        
        byte reg = 0 ;
        int b1 , b2 , xoredbit ;
        for (int i=0; i<8; i++){
            b1=converttobit(i,reg1); b2=converttobit(i,reg1);   
            xoredbit= b1^b2;
            reg = (byte) (reg + xoredbit) ;  
        }
        return reg ; 
     }
    
     
    // stay ..GOOD
    public int FlipOnebit(int bitflip){
        if (bitflip==0)  bitflip=1;
        else bitflip=0;
    
    return bitflip;
    }
    
    // stay ..GOOD
    public int converttobit(int ibyte,int ibit,byte[] T){
        int b=(T[ibyte]>>(8-(ibit+1))) & 0x0001;
    return b;
    }
    
    public int converttobit(int ibit, byte T){
        int b=(T >>(8-(ibit+1))) & 0x0001;
    return b;
    }
    
    // stay 
    public byte [] Filltabres( byte[] bytestab,int posbyte, int posbit, int resbit){
        
        byte oldbyte=bytestab[posbyte];
        
        oldbyte=(byte)(((0xFF7F>>posbit)& oldbyte)& 0x00FF);
        byte newbyte=(byte)((resbit<<(7-posbit))|oldbyte);
       bytestab[posbyte]=newbyte;
         
    return bytestab;
    }
    
    
    // stay
    public byte[] rotateLeft(byte[] in, int len, int step,int bitvalue) {
        
        int numOfBytes = (len-1)/8 + 1;
        byte[] out = new byte[numOfBytes];
        for (int i=0; i<len; i++) {
            if (i==0)setBit(out,0,bitvalue);
            else{
                int val = getBit(in,(i+step)%len);
                setBit(out,i,val);
            } 
        }
    return out;
    }
    
    
    // stay
    public int getBit(byte[] data, int pos) {
        int posByte = pos/8; 
        int posBit = pos%8;
        byte valByte = data[posByte];
        int valInt = valByte>>(8-(posBit+1)) & 0x0001;
    
    return valInt;
    }
    
    // stay
    public void setBit(byte[] data, int pos, int val) {
    
        int posByte = pos/8; 
        int posBit = pos%8;
        byte oldByte = data[posByte];
        oldByte = (byte) (((0xFF7F>>posBit) & oldByte) & 0x00FF);
        byte newByte = (byte) ((val<<(8-(posBit+1))) | oldByte);
        data[posByte] = newByte;
    
    }
    
    
    // stay ..not-Yet
     public void writeBytes(byte[] data, String out) throws Exception {
        FileOutputStream fos = new FileOutputStream(out);
        fos.write(data);
        fos.close();
    }

    // stay  ..GOOD
   /*  public void printBytes(byte[] data) {
     
        for (int i=0; i<data.length; i++) System.out.print(byteToBits(data[i])+" ");
    }
    
    // stay  ..GOOD
    public String byteToBits(byte b) {
        StringBuffer buf = new StringBuffer();
        for (int i=0; i<8; i++) buf.append((int)(b>>(8-(i+1)) & 0x0001));
        
    return buf.toString();
    }*/
   

    
    
   
    // stay
    public byte[] readBytes(String in) throws Exception {
        FileInputStream fis = new FileInputStream(in);
        int numOfBytes = fis.available();
        byte[] buffer = new byte[numOfBytes];
        fis.read(buffer);
        fis.close();
    
    return buffer;
    }

    
    

    /*
    public void padBytesBlock(byte[] block, int dataSize) {

        int bSize = block.length;
        int padSize = bSize - dataSize;
        int pa² dValue = padSize%bSize;
        for (int i=0; i<padSize; i++) block[bSize-i-1] = (byte) 0x00;
    }
    */
    /*String keytxt= KeyTxt.getText();
         String r= RoundTxt.getText();
        
         byte[] Key = keytxt.getBytes();
         int Round = Integer.parseInt(r);
         
             
              if (keytxt.length() == 8 && Round>0 ) {
                   pos.writeBytes(pos.GenwithLFSR(Round, Key), "StreamKey.txt");*/

}
