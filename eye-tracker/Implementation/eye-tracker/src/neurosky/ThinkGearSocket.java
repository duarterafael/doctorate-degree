/*
 * 
 * This provides a simple socket connector to the NeuroSky MindWave ThinkGear connector.
 * For more info visit http://crea.tion.to/processing/thinkgear-java-socket
 * 
 * No warranty or any stuffs like that.
 * 
 * Have fun!
 * Andreas Borg
 * borg@elevated.to
 * 
 * 
 * (c) 2010
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 * 
 * @author		Andreas Borg, borg@elevated.to
 * @modified	June, 2011
 * @version		1.0
 * 
 * 
 * This library is following the same design as the one developed by Jorge C. S. Cardoso for the MindSet device.
 * The MindWave device can communicate to a socket over JSON instead of the serial port. That makes it easier and tidier
 * to talk between the device and Java. For instructions on how to use the callback listeners please refer to
 * 
 * http://jorgecardoso.eu/processing/MindSetProcessing/
 * 
 * 
 * Data is passed back to the application via the following callback methods:
 * 
 * 
 * public void attentionEvent(int attentionLevel)
 * Returns the current attention level [0, 100].
 * Values in [1, 20] are considered strongly lowered.
 * Values in [20, 40] are considered reduced levels.
 * Values in [40, 60] are considered neutral.
 * Values in [60, 80] are considered slightly elevated.
 * Values in [80, 100] are considered elevated.
 * 
 * public void meditationEvent(int meditationLevel)
 * Returns the current meditation level [0, 100].
 * The interpretation of the values is the same as for the attentionLevel.
 * 
 * 
 * public void poorSignalEvent(int signalLevel)
 * Returns the signal level [0, 200]. The greater the value, the more noise is detected in the signal.
 * 200 is a special value  that means that the ThinkGear contacts are not touching the skin.
 * 
 * 
 * public void eegEvent(int delta, int theta, int low_alpha, int high_alpha, int low_beta, int high_beta, int low_gamma, int mid_gamma) </code><br>
 * Returns the EEG data. The values have no units.
 * 
 * 
 * 
 * public void rawEvent(int [])
 * Returns the the current 512 raw signal samples [-32768, 32767]. 
 * 
 * 
 */ 
package neurosky;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.time.DateUtils;

import Business.ExprerimentCSVWritter;
import neurosky.json.JSONException;
import neurosky.json.JSONObject;
import neurosky.outpup.EEGAction;
import neurosky.outpup.EEGDataManager;
import neurosky.outpup.EEGRaw;


public class ThinkGearSocket  implements Runnable{
	public Socket neuroSocket;
	public OutputStream outStream;
	public InputStream inStream;
	public BufferedReader stdIn;
	public String appName="";
	public String appKey="";
	private Thread t;
  
	  private int raw[] = new int[512];
	  private int index = 0;
	  private EEGDataManager eegDataManager;
  
	public final static String VERSION = "1.0";
  
	public static void main(String _args[]) {
		ThinkGearSocket neuroSocket = new ThinkGearSocket();
//		  try {
//		    neuroSocket.start();
//		    neuroSocket.stop();
//		    HashMap<Date, EEGRaw> x = neuroSocket.getEEGDataManager().getEEGRawMap();
//		    for (Entry<Date, EEGRaw> pair : x.entrySet()) {
//		        System.out.println("Key"+pair.getKey().toString());
//		        System.out.println("Value"+pair.getValue().toString());
//		    }
//		  } 
//		  catch (Exception e) {
//		    System.out.println("Is ThinkGear running??");
//		  }
//		  
		  try {
			neuroSocket.start();
		    Thread.sleep(60000);
		    neuroSocket.stop();
		    HashMap<Date, EEGRaw> y = neuroSocket.getEEGDataManager().getEEGRawMap();
		    storeNeuroSkyData(neuroSocket.getEEGDataManager());
		    for (Entry<Date, EEGRaw> pair : y.entrySet()) {
		        System.out.println("Key"+pair.getKey().toString());
		        System.out.println("Value"+pair.getValue().toString());
		    }
		    
		  } 
		  catch (Exception e) {
		    System.out.println("Is ThinkGear running??");
		    e.printStackTrace();
		  }
	}
	
	private static void storeNeuroSkyData(EEGDataManager eegDataManager)
	{
		List<String> headers = new LinkedList<String>();
		headers.add("Time stamp");
		headers.add("Poor Signal Level");
		headers.add("Blink Strength");
		headers.add("Attention Level");
		headers.add("Meditation Level");
		headers.add("Delta");
		headers.add("Theta");
		headers.add("Low Alpha");
		headers.add("High Alpha");
		headers.add("Low Beta");
		headers.add("High Beta");
		headers.add("Low Gamma");
		headers.add("Mid Beta");
		
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss.sss");
		
		List<List<String>> dataList = new LinkedList<>();
		for (Entry<Date, EEGRaw> pair : eegDataManager.getEEGRawMap().entrySet()) {
			List<String> data = new LinkedList<String>();
			data.add(dateFormat.format(pair.getKey()));
			EEGRaw raw = pair.getValue();
			data.add(rawDataToString(pair.getValue().getPoorSignalLevel()));
			data.add(rawDataToString(pair.getValue().getBlinkStrength()));
			data.add(rawDataToString(pair.getValue().getAttentionLevel()));
			data.add(rawDataToString(pair.getValue().getMeditationLevel()));
			data.add(rawDataToString(pair.getValue().getDelta()));
			data.add(rawDataToString(pair.getValue().getTheta()));
			data.add(rawDataToString(pair.getValue().getLow_alpha()));
			data.add(rawDataToString(pair.getValue().getHigh_alpha()));
			data.add(rawDataToString(pair.getValue().getLow_beta()));
			data.add(rawDataToString(pair.getValue().getHigh_beta()));
			data.add(rawDataToString(pair.getValue().getLow_gamma()));
			data.add(rawDataToString(pair.getValue().getMid_gamma()));
			dataList.add(data);
		 }
		
		
		String fileName = "1teste_neuro_sky.csv";
		ExprerimentCSVWritter neuroskyCSVWritter = new ExprerimentCSVWritter(headers, dataList, null, fileName);
		neuroskyCSVWritter.WriteData();
	}
	
	private static String rawDataToString(Integer data)
	{
		if(data == null)
			return "";
		else
			return data.toString();
	}
	
  
  private boolean running = true;
	  public ThinkGearSocket(String _appName,String _appKey){
		  appName = _appName;//these were mentioned in the documentation as required, but test prove they are not.
		  appKey = _appKey;  

	  }

	public ThinkGearSocket(){
		eegDataManager = new EEGDataManager();
	}
	
	public EEGDataManager getEEGDataManager()
	{
		return eegDataManager;
	}
	
	
	 public boolean isRunning(){
		 return running;
	 }
	
	
	public static String version() {
		return VERSION;
	}
		
		
	public void start() throws ConnectException{
		System.out.println("Start");
		try {
			neuroSocket = new Socket("127.0.0.1",13854);	
		} catch (ConnectException e) {
			e.printStackTrace();
			System.out.println("Oi plonker! Is ThinkkGear running?");
			running = false;
			throw e;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			inStream  = neuroSocket.getInputStream();
			outStream = neuroSocket.getOutputStream();
			stdIn = new BufferedReader(new InputStreamReader(neuroSocket.getInputStream()));
			running = true;
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		
		if(appName !="" && appKey !=""){
			JSONObject appAuth = new JSONObject();
			try {
				appAuth.put("appName", appName);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				appAuth.put("appKey", appKey);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//throws some error
			sendMessage(appAuth.toString());
			System.out.println("appAuth"+appAuth);
		}
		
		
		JSONObject format = new JSONObject();
		try {
			format.put("enableRawOutput", true);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("raw error");
			e.printStackTrace();
		}
		try {
			format.put("format", "Json");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("Json error");
			e.printStackTrace();
		}
		//System.out.println("format "+format);
		sendMessage(format.toString());
		 t = new Thread(this);
	    t.start();
	    
	}
	
	
	@SuppressWarnings("deprecation")
	public void stop(){
		System.out.println("Stop");
		if(running){
			t.interrupt();
			try {
				
				if(neuroSocket.isConnected())
				{
					neuroSocket.close();	
				}
				
				inStream.close();
				outStream.close();
				stdIn.close();
				stdIn = null;
			}catch (SocketException e) {
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//System.out.println("Socket close issue");
			}
			
		}
		running = false;
	}
	public void sendMessage(String msg){
		PrintWriter out = new PrintWriter(outStream, true);
		//System.out.println("sendmsg");
		out.println(msg);
	}
	@Override
	public void run() {
		if(running && neuroSocket.isConnected()){
			String userInput;
	
			try {
				while ((userInput = stdIn.readLine()) != null) {
					//System.out.print(userInput);
					String[] packets = userInput.split("/\r/");
					for(int s=0;s<packets.length;s++){
						if(((String) packets[s]).indexOf("{")>-1){
							JSONObject obj = new JSONObject((String) packets[s]);
							parsePacket(obj);
						}
						
						//String name = obj.get("name").toString();
					}
					
		
				}
			} 
			catch(SocketException e){
				System.out.println("For some reason stdIn throws error even if closed");
			//	maybe it takes a cycle to close properly?
				e.printStackTrace();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			running = false;
		}
	}
	
	  public void triggerPoorSignalEvent(Date timeStamp, int poorSignalLevel) {
		  //System.out.println("timeStamp"+timeStamp+"poorSignalLevel "+poorSignalLevel);
		  eegDataManager.AddEEGMap(EEGAction.POOR_SIGNAL_LEVEL, timeStamp, poorSignalLevel, null, null, null, null, null, null, null, null, null, null, null);
			
	  }  

	
	  public void triggerBlinkEvent(Date timeStamp, int blinkStrength) {
		//  System.out.println("timeStamp"+timeStamp+"blinkStrength "+blinkStrength);
		  eegDataManager.AddEEGMap(EEGAction.BLINK_STRENGTH, timeStamp, null, blinkStrength, null, null, null, null, null, null, null, null, null, null);
			
	  }


	  public void triggerAttentionAndMeditationEvent(Date timeStamp, int attentionLevel, int meditationLevel) {
		  System.out.println("timeStamp"+timeStamp+"attentionLevel "+attentionLevel);
		  eegDataManager.AddEEGMap(EEGAction.E_SENSE, timeStamp, null, null, attentionLevel, meditationLevel, null, null, null, null, null, null, null, null);
			
	  }


	  public void triggerEEGEvent(Date timeStamp, 
			  					int delta, 
			  					int theta, 
			  					int low_alpha, 
			  					int high_alpha, 
			  					int low_beta, 
			  					int high_beta, 
			  					int low_gamma, 
			  					int mid_gamma) {
//		  System.out.println("timeStamp "+timeStamp);
//		  System.out.println("delta "+delta);
//		  System.out.println("theta "+theta);
//		  System.out.println("low_alpha "+low_alpha);
//		  System.out.println("high_alpha "+high_alpha);
//		  System.out.println("low_beta "+low_beta);
//		  System.out.println("high_beta "+high_beta);
//		  System.out.println("low_gamma "+low_gamma);
//		  System.out.println("mid_gamma "+mid_gamma);
		  eegDataManager.AddEEGMap(EEGAction.EEG_POWER, timeStamp, null, null, null, null, delta, theta, low_alpha, high_alpha, low_beta, high_beta, low_gamma, mid_gamma);
			
	  }


//	  public void triggerRawEvent(int []values) {
//	    if (rawEventMethod != null) {
//	      try {
//	        rawEventMethod.invoke(this, new Object[] {
//	          values
//	        }   
//	        );
//	      } 
//	      catch (Exception e) {
//	        System.err.println("Disabling rawEvent()  because of an error.");
//	        e.printStackTrace();
//	        rawEventMethod = null;
//	      }
//	    }
//	  }	
	  
	  
	  public void parsePacket(JSONObject data){
			Iterator itr = data.keys(); 
			while(itr.hasNext()) {

			    Object e = itr.next(); 
			    String key = e.toString();
			    
			    Date timeStamp = DateUtils.truncate(new Date(), Calendar.SECOND);
			    
			    
			    try{
			    	
				    if(key.matches("poorSignalLevel")){
				    	triggerPoorSignalEvent(timeStamp, data.getInt(e.toString()));
				    	
				    }
				  if(key.matches("rawEeg")){
				    	 int rawValue =  (Integer) data.get("rawEeg");
				          raw[index] = rawValue;
				          index++;
				          if (index == 512) {
				            index = 0;
				            int rawCopy[] = new int[512];
				            rawCopy = Arrays.copyOf(raw, raw.length);
				           // triggerRawEvent(rawCopy);
				          }
				    }
				    if(key.matches("blinkStrength")){
				    	triggerBlinkEvent(timeStamp, data.getInt(e.toString()));
				    	
				    }  
				    	
				    if(key.matches("eSense")){
				    	JSONObject esense = data.getJSONObject("eSense");
				    	triggerAttentionAndMeditationEvent(timeStamp, esense.getInt("attention"), esense.getInt("meditation"));
				    	
				    }
				    if(key.matches("eegPower")){
				    	JSONObject eegPower = data.getJSONObject("eegPower");
//				    	 System.out.println(">>>>delta "+eegPower.getDouble("delta"));
//				    	 System.out.println(">>>>theta "+eegPower.getDouble("theta"));
//				    	 System.out.println(">>>>lowAlpha "+eegPower.getDouble("lowAlpha"));
//				    	 System.out.println(">>>>highAlpha "+eegPower.getDouble("highAlpha"));
//				    	 System.out.println(">>>>lowBeta "+eegPower.getDouble("lowBeta"));
//				    	 System.out.println(">>>>highBeta "+eegPower.getDouble("highBeta"));
//				    	 System.out.println(">>>>highBeta "+eegPower.getDouble("highBeta"));
//				    	 System.out.println(">>>>lowGamma "+eegPower.getDouble("highGamma"));
				    	triggerEEGEvent(timeStamp, eegPower.getInt("delta"), eegPower.getInt("theta"), eegPower.getInt("lowAlpha"), eegPower.getInt("highAlpha"),eegPower.getInt("lowBeta"), eegPower.getInt("highBeta"),eegPower.getInt("lowGamma"), eegPower.getInt("highGamma"));
						
				    	//System.out.println(key);
				    }			    
				   
				    
			    }
			    catch(Exception ex){
			    	
			    	ex.printStackTrace();
			    }
			} 
			
			// 
	  }
}
