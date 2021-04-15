package com.electra;

import com.bitwig.extension.controller.api.AbsoluteHardwareKnob;
import com.bitwig.extension.controller.api.ControllerHost;
import com.bitwig.extension.controller.api.CursorChannel;
import com.bitwig.extension.controller.api.CursorDevice;
import com.bitwig.extension.controller.api.CursorDeviceFollowMode;
import com.bitwig.extension.controller.api.CursorTrack;
import com.bitwig.extension.controller.api.HardwareSurface;
import com.bitwig.extension.controller.api.MidiIn;
import com.bitwig.extension.controller.api.MidiOut;
import com.bitwig.extension.controller.api.Parameter;
import com.bitwig.extension.controller.api.PinnableCursorDevice;
import com.bitwig.extension.controller.api.Send;
import com.bitwig.extension.controller.api.SendBank;
import com.bitwig.extension.controller.api.SpecificBitwigDevice;
import com.bitwig.extension.controller.api.SpecificPluginDevice;
import com.bitwig.extension.api.util.midi.ShortMidiMessage;
import com.bitwig.extension.callback.ShortMidiDataReceivedCallback;
import com.bitwig.extension.callback.ShortMidiMessageReceivedCallback;
import com.bitwig.extension.controller.ControllerExtension;

import java.util.HashMap;
import java.util.UUID;


public class Electra2Extension extends ControllerExtension
{
	
	public HardwareSurface hardwareSurface;
	public ControllerHost host;
	public PinnableCursorDevice cursorDevice;
	public CursorTrack cursorTrack; 

	
   protected Electra2Extension(final Electra2ExtensionDefinition definition, final ControllerHost host)
   {
      super(definition, host);
   }

   @Override
   public void init()
   {
	  
      host = getHost();
      hardwareSurface = host.createHardwareSurface();
      final MidiIn inPort1 = host.getMidiInPort (0);
      final MidiIn inPort2 = host.getMidiInPort (1);
      final MidiOut outPort1 = host.getMidiOutPort (0);
      final MidiOut outPort2 = host.getMidiOutPort (1);
     
      //Create hardware knobs
      AbsoluteHardwareKnob [] knob = new AbsoluteHardwareKnob[37];
      /*
      final MidiOut outPort2 = host.getMidiOutPort(1);
      final MidiOut outPort = host.getMidiOutPort(0);
      */
      for( int i=0; i<37; i++) {
    	  knob[i] = this.hardwareSurface.createAbsoluteHardwareKnob("ABS_KNOB"+i);
    	  knob[i].setAdjustValueMatcher(inPort1.createAbsoluteCCValueMatcher(0, i));
    	  
      }

      final UUID eClapID = UUID.fromString("89eba41d-46d3-4506-8ce6-ba9fe3e3bee4");
      final UUID fm4ID = UUID.fromString("7a0a94df-3aa4-4bb5-8e24-2511999871ad");
      
      
      //Cursortracks and devices/banks
      cursorTrack = host.createCursorTrack("ELECTRA_CURSOR_TRACK", "Cursor Track", 2, 0, true);
      
      cursorDevice = cursorTrack.createCursorDevice("ELECTRA_CURSOR_DEVICE", "Cursor Device", 0, CursorDeviceFollowMode.FOLLOW_SELECTION);
      
      final Devices devices = new Devices(cursorDevice);
      //devices.setupAllDevices(cursorDevice);
      SendBank sendBank1 = cursorTrack.sendBank(); 
     /*
      final SpecificPluginDevice fabQ3 = cursorDevice.createSpecificVst3Device("72C4DB717A4D459AB97E51745D84B39D");
      final SpecificPluginDevice fabC2 = cursorDevice.createSpecificVst3Device("79F415E3C8E74807AD5DA3CF7024F618");
      final SpecificBitwigDevice eClap = cursorDevice.createSpecificBitwigDevice(eClapID);
      final SpecificBitwigDevice fm4 = cursorDevice.createSpecificBitwigDevice(fm4ID);
      
      /*
      HashMap<String,SpecificPluginDevice> devicesVST3 = new HashMap<String,SpecificPluginDevice>();
      devicesVST3.put("FabFilter Pro-Q 3", cursorDevice.createSpecificVst3Device("72C4DB717A4D459AB97E51745D84B39D"));
      devicesVST3.put("FabFilter Pro-C 3", cursorDevice.createSpecificVst3Device("79F415E3C8E74807AD5DA3CF7024F618"));
     
      HashMap<String,SpecificBitwigDevice> devicesBitwig = new HashMap<String,SpecificBitwigDevice>();
      devicesBitwig.put("E-Clap", cursorDevice.createSpecificBitwigDevice(UUID.fromString("7a0a94df-3aa4-4bb5-8e24-2511999871ad")));
      devicesBitwig.put("FM-4", cursorDevice.createSpecificBitwigDevice(UUID.fromString("7a0a94df-3aa4-4bb5-8e24-2511999871ad")));
      */
      /*HashMap<String, SpecificBitwigDevice> devicesBitwig = new HashMap<String,SpecificBitwigDevice>();
      devicesBitwig = bitwigDeviceCreator(cursorDevice);
      HashMap<String,SpecificPluginDevice> devicesVST3 = new HashMap<String,SpecificPluginDevice>();
      devicesVST3 = vst3Creator(cursorDevice);
      */
      
      /*
      //plugin parameters
      Parameter [] fabQ3Params = new Parameter[100];
      Parameter [] fabC2Params = new Parameter[100];
      Parameter [] eClapParams = new Parameter[100];
      Parameter [] fm4Params = new Parameter[100];
      
      
      //final SpecificPluginDevice fabQ32 = devices.getVST3(DeviceNames.FABFILTER_PRO_Q3);
      for(int i=0; i<100; i++){
    	  fabQ3Params[i] = devices.getVST3(DeviceNames.FABFILTER_PRO_Q3).createParameter(i);
    	  fabC2Params[i] = devices.getVST3(DeviceNames.FABFILTER_PRO_C2).createParameter(i);
    	  
      };
      /*
      eClapParams[0] = devices.getBitwigDevice("E-Clap").createParameter("DECAY");
      eClapParams[1] = devices.getBitwigDevice("E-Clap").createParameter("REPEAT_TIME");
      eClapParams[2] = devices.getBitwigDevice("E-Clap").createParameter("REPEAT_DURATION");
      eClapParams[3] = devices.getBitwigDevice("E-Clap").createParameter("WIDTH");
      eClapParams[4] = devices.getBitwigDevice("E-Clap").createParameter("FREQ");
      eClapParams[5] = devices.getBitwigDevice("E-Clap").createParameter("Q");
      eClapParams[6] = devices.getBitwigDevice("E-Clap").createParameter("VELOCITY_SENSITIVITY");
      eClapParams[7] = devices.getBitwigDevice("E-Clap").createParameter("OUTPUT");
      
      fm4Params[0] = devices.getBitwigDevice("FM-4").createParameter("OP1_RATIO");
      fm4Params[1] = devices.getBitwigDevice("FM-4").createParameter("OP2_RATIO");
      fm4Params[2] = devices.getBitwigDevice("FM-4").createParameter("OP3_RATIO");
      fm4Params[3] = devices.getBitwigDevice("FM-4").createParameter("OP4_RATIO");
      fm4Params[4] = devices.getBitwigDevice("FM-4").createParameter("AEG_ATTACK");
      fm4Params[5] = devices.getBitwigDevice("FM-4").createParameter("AEG_DECAY");
      fm4Params[6] = devices.getBitwigDevice("FM-4").createParameter("AEG_SUSTAIN");
      fm4Params[7] = devices.getBitwigDevice("FM-4").createParameter("AEG_RELEASE");
      */
      for(int i=0; i<32; i++) {
    	  
    	  int parameterNumber = ParameterMappings.fabQ3[i];
    	  devices.getParameter(DeviceNames.FABFILTER_PRO_Q3, parameterNumber).addBinding(knob[i]);
    	  parameterNumber = ParameterMappings.fabC2[i];
    	  devices.getParameter(DeviceNames.FABFILTER_PRO_C2, parameterNumber).addBinding(knob[i]);
    	  
    	  //[parameterNumber].addBinding(knob[i+1]);
      }
      /*
      for(int i=0; i<32; i++) {
    	  
    	  int parameterNumber = ParameterMappings.fabC2[i];
    	  fabC2Params[parameterNumber].addBinding(knob[i+1]);
      }
      */
      for(int i=0; i<8; i++) {
    	  
    	  int parameterNumber = ParameterMappings.eClap[i];
    	  devices.getParameter(DeviceNames.E_CLAP, i).addBinding(knob[parameterNumber]);
      }
      for(int i=0; i<8; i++) {
    	  
    	  int parameterNumber = ParameterMappings.fm4[i];
    	  devices.getParameter(DeviceNames.FM_4, i).addBinding(knob[parameterNumber]);
      }

      //map to sends
      sendBank1.getItemAt(0).addBinding(knob[30]);
      sendBank1.getItemAt(1).addBinding(knob[31]);

      
     /// send out prgrm change when plugin changes
      
      cursorDevice.name().addValueObserver((newName) -> {
    	  host.println(newName);
    	  if (newName.equals("FabFilter Pro-Q 3")) {
    		outPort1.sendMidi(0xc0, 1, 127); 
    		outPort2.sendMidi(0xc0, 1, 127); 
    		};
    	  if (newName.equals("FabFilter Pro-C 2")) {
        		outPort1.sendMidi(0xc0, 2, 127); 
        		outPort2.sendMidi(0xc0, 2, 127); 
        	};
    	  if (newName.equals("E-Clap")) {
      		outPort1.sendMidi(0xc0, 3, 127); 
      		outPort2.sendMidi(0xc0, 3, 127); 
      		};
      	  if (newName.equals("FM-4")) {
        		outPort1.sendMidi(0xc0, 4, 127); 
        		outPort2.sendMidi(0xc0, 4, 127); 
        	};
      });
      /* midi in callback
      /  change focused plugin and create plugins on prgm change messages
      /
      */
      inPort1.setMidiCallback((ShortMidiMessageReceivedCallback)msg -> {
    	  if (msg.isProgramChange()) {
    		  if (msg.getData1() == 1) {
    			  cursorDevice.selectPrevious();
    		  }
    		  if (msg.getData1() == 2) {
    			  cursorDevice.selectNext();
    			  
    		  }
    		  
    		  if (msg.getData1() == 3) {
    			  cursorDevice.afterDeviceInsertionPoint().insertBitwigDevice(fm4ID);
    		  	
    		  }
    		  if (msg.getData1() == 4 ) {
    			  cursorDevice.afterDeviceInsertionPoint().insertBitwigDevice(eClapID);
    		  }
    		  if (msg.getData1() == 5 ) {
    			  cursorDevice.afterDeviceInsertionPoint().insertVST3Device("72C4DB717A4D459AB97E51745D84B39D");
    		  }
    		  
    		  if (msg.getData1() == 6 ) {
    			  cursorDevice.afterDeviceInsertionPoint().insertVST3Device("79F415E3C8E74807AD5DA3CF7024F618");
    		  }
    	  }
    	  
      });
      
      host.showPopupNotification("Electra2 Initialized");
   }



private void midiReceived(ShortMidiMessage msg) {
	   
	  
   }

@Override
   public void exit()
   {
		
      getHost().showPopupNotification("Electra2 Exited");
   }

   @Override
   public void flush()
   {
	   if (this.hardwareSurface != null)
		   this.hardwareSurface.updateHardware(); 
	   
   }
   /*
   public HashMap<String, SpecificBitwigDevice> bitwigDeviceCreator (PinnableCursorDevice cursorD){
	   
	   HashMap<String,SpecificBitwigDevice> out = new HashMap<String,SpecificBitwigDevice>();
	   out.put("E-Clap", cursorDevice.createSpecificBitwigDevice(UUID.fromString("7a0a94df-3aa4-4bb5-8e24-2511999871ad")));
	   out.put("FM-4", cursorDevice.createSpecificBitwigDevice(UUID.fromString("7a0a94df-3aa4-4bb5-8e24-2511999871ad")));
	   
	   return out;
	   
   }
   
   public HashMap<String, SpecificPluginDevice> vst3Creator (PinnableCursorDevice cursorD){
	   
	      
	      HashMap<String,SpecificPluginDevice> out = new HashMap<String,SpecificPluginDevice>();
	      out.put("FabFilter Pro-Q 3", cursorDevice.createSpecificVst3Device("72C4DB717A4D459AB97E51745D84B39D"));
	      out.put("FabFilter Pro-C 2", cursorDevice.createSpecificVst3Device("79F415E3C8E74807AD5DA3CF7024F618"));
	      
	      return out;
	   
   }
   */





}
