// Beale.java: implement the Beale cipher
// Usage: java Beale (-d | -e) keyFile
//  The program reads a separate file (keyFile) for the key or pad.
//  The message is just the standard input.
//  Caps and punctuation marks in the message remain unchanged.
//  Only lowercase letters are used in the key file/
import java.io.*;
public class Beale {
   private Reader messIn; // System.in for message
   private Reader keyIn;  // keyFile for key file

   // Beale: constructor -- just open files
   public Beale(String keyFile) {
      messIn = new InputStreamReader(System.in);
      try {
         keyIn = new FileReader(keyFile);
      } catch (IOException e) {
         System.out.println("Exception opening keyFile");
      }
   }

   // (en|de)crypt: just feed in opposite parameters
   public void encrypt() { translate(1); }
   public void decrypt() { translate(-1); }

   // translate: read keyFile and input, translate
   private void translate(int direction) {
      char c, key_c;
      while ((byte)(c = getNextChar(messIn)) != -1) {
         if (Character.isLowerCase(c)) {
            // fetch lowercase letter from key file
            while (!Character.isLowerCase(key_c = getNextChar(keyIn)))
               ;
            c = rotate(c, ((direction*(key_c - 'a')) + 26)%26);
         }
         System.out.print(c);
      }
   }

   // getNextChar: fetches next char.  Also opens input file
   public char getNextChar(Reader in) {
      char ch = ' '; // = ' ' to keep compiler happy
      try {
         ch = (char)in.read();
      } catch (IOException e) {
         System.out.println("Exception reading character");
      }
      return ch;
   }
   
   // rotate: translate using rotation -- simpler version
   //  This just uses arithmetic on char types,
   public char rotate(char c, int key) {
      int res = ((c - 'a') + key + 26)%26 + 'a';
      return (char)res;
   }

   // main: check command, (en|de)crypt, feed in keyFile
   public static void main(String[] args) {
      if (args.length != 2) {
         System.out.println("Usage: java Beale (-d | -e) keyFile");
         System.exit(1);
      }
      Beale cipher = new Beale(args[1]);
      if (args[0].equals("-e")) cipher.encrypt();
      else if (args[0].equals("-d")) cipher.decrypt();
      else {
         System.out.println("Usage: java Beale (-d | -e) keyFile");
         System.exit(1);
      }
   }
}