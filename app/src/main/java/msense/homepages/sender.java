package msense.homepages;

import android.os.StrictMode;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;


public class sender {
	
	
	Socket socket = null;
	DataOutputStream dataOutputStream = null;
	DataInputStream dataInputStream = null;
	String ir_ip_address,stb_ip_address;
	
	public void ir_ipaddress(String irip) {
		ir_ip_address=irip;
	}
	public void stb_ipaddress(String stbip) {
		stb_ip_address=stbip;
	}
	
	public void senderfunction(float x1, float y1,String amess)
	{
		try {
			/*InetAddress IPAddress;
        	IPAddress = InetAddress.getByName(getLocalIpAddress());*/
			   
			  //socket = new Socket("10.0.2.2", 13055);
			socket = new Socket("192.168.2.62", 13000);
			//socket = new Socket(stb_ip_address, 13000);
			//iTach test
			//socket = new Socket("192.168.1.2", 4998);
			  dataOutputStream = new DataOutputStream(socket.getOutputStream());
			  dataInputStream = new DataInputStream(socket.getInputStream());
			  String s=null;
			  if (amess.toString().equals("mouseclick"))
				  {
				  s = x1+","+y1+","+"mouseclick";
				  }
			  else if (amess.toString().equals("keyboardclick"))
			  {
				  s = x1+","+y1+","+"keyboardclick";//new String("sendir,1:2,1");
			  }
			  else
			  {
				  s = x1+","+y1+","+"xymove";//new String("sendir,1:2,1");
			  }	
			  
			  Log.d("XYcoOrdinates", s);
			  
			  byte[] a = new byte[2024];
			  a=s.getBytes();
			  //byte a[2024] = s.getBytes();
			
			  dataOutputStream.write(a,0,a.length);
			  //kaeman --trying to wait for input stream
			 int num = 10000000;
			  for(int i=1; i < num; i++) {
			  if(dataInputStream.available() != 0) {
				  long length = dataInputStream.available();
				  byte[] bytes = new byte[(int) length];
				  bytes=getBytesFromInputStream(dataInputStream);
				  Log.d("TCPsender", new String(bytes));
				  /* textIn.setText(new String(bytes));
				  editIn.setText(new String(bytes));*/
				  i=10000001;
			  break;
			  }
			  } 
		 	} catch (UnknownHostException e) {
				 Log.d("TagServer()", e.toString());
			} catch (IOException e) {
				 Log.d("TagServer()", e.toString());
			}
			finally{
			 if (socket != null){
			  try {
			   socket.close();
			  } catch (IOException e) {
				   Log.d("TagServer()", e.toString());
			  }
			 }

			 if (dataOutputStream != null){
			  try {
			   dataOutputStream.close();
			  } catch (IOException e) {
				   Log.d("TagServer()", e.toString());
			  }
			 }

			 if (dataInputStream != null){
			  try {
			   dataInputStream.close();
			  } catch (IOException e) {
				   Log.d("TagServer()", e.toString());
			  } 
			 }
			 
			}
	}
	
	public void itachstreaming(String s)
	{
		 StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	     StrictMode.setThreadPolicy(policy);
		try {
			//iTach test
			socket = new Socket("10.90.1.11", 4998);
			//socket = new Socket(ir_ip_address, 4998);
			  dataOutputStream = new DataOutputStream(socket.getOutputStream());
			  dataInputStream = new DataInputStream(socket.getInputStream());
			//Converting string to byte	  
			  byte[] a = new byte[2024];
			  a=s.getBytes();
			//Sending Byte to itach receiver
			  dataOutputStream.write(a,0,a.length);
			  //Log.v("OutputDATA", s);		
			//Receiving response from TCP listener
			 int num = 10000000;
			  for(int i=1; i < num; i++) {
			  if(dataInputStream.available() != 0) {
				  long length = dataInputStream.available();
				  byte[] bytes = new byte[(int) length];
				  bytes=getBytesFromInputStream(dataInputStream);
				  String out=  new String(bytes);
				  Log.v("TCPsender", out);
				  //try to control the speed  
		           /* try {
					Thread.sleep(100);
		            } catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
		            }*/
		            //end control the speed  
				  i=10000001;
			  break;
			  }			  
			  } 
			
		 	} catch (UnknownHostException e) {
				 Log.d("TagServer()", e.toString());
			} catch (IOException e) {
				 Log.d("TagServer()", e.toString());
			}
			finally{
			 if (socket != null){
			  try {
			   socket.close();
			  } catch (IOException e) {
				   Log.d("TagServer()", e.toString());
			  }
			 }

			 if (dataOutputStream != null){
			  try {
			   dataOutputStream.close();
			  } catch (IOException e) {
				   Log.d("TagServer()", e.toString());
			  }
			 }

			 if (dataInputStream != null){
			  try {
			   dataInputStream.close();
			  } catch (IOException e) {
				   Log.d("TagServer()", e.toString());
			  } 
			 }
			 
			}
	}

	///Reading bytes from socket over network --------------kaeman
	public static byte[] getBytesFromInputStream(DataInputStream is)
	throws IOException {

	// Get the size of the file
	long length = is.available();

	if (length > Integer.MAX_VALUE) {
	// File is too large
	}

	// Create the byte array to hold the data
	byte[] bytes = new byte[(int) length];

	// Read in the bytes
	int offset = 0;
	int numRead = 0;
	while (offset < bytes.length
	&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
	offset += numRead;
	}

	// Ensure all the bytes have been read in
	if (offset < bytes.length) {
	throw new IOException("Could not completely read file ");
	}

	// Close the input stream and return bytes
	is.close();
	return bytes;
	}
	
	 // gets the ip address of your phone's network
    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) { return inetAddress.getHostAddress().toString(); }
                }
            }
        } catch (SocketException ex) {
            Log.e("ServerActivity", ex.toString());
        }
        return null;
    }
	}