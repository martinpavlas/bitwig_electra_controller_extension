package com.electra;

import com.bitwig.extension.controller.api.AbsoluteHardwareKnob;
import com.bitwig.extension.controller.api.ControllerHost;
import com.bitwig.extension.controller.api.CursorChannel;
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
import java.util.UUID;


public class Electra2Extension extends ControllerExtension
{
	private HardwareSurface hardwareSurface;
	
	
   protected Electra2Extension(final Electra2ExtensionDefinition definition, final ControllerHost host)
   {
      super(definition, host);
   }

   @Override
   public void init()
   {
	  
      final ControllerHost host = getHost();
      hardwareSurface = host.createHardwareSurface();
      final MidiIn port = host.getMidiInPort (0);

      
      AbsoluteHardwareKnob [] knob = new AbsoluteHardwareKnob[37];
      
      final MidiOut outPort2 = host.getMidiOutPort(1);
      final MidiOut outPort = host.getMidiOutPort(0);
      for( int i=0; i<37; i++) {
    	  knob[i] = this.hardwareSurface.createAbsoluteHardwareKnob("ABS_KNOB"+i);
    	  knob[i].setAdjustValueMatcher(port.createAbsoluteCCValueMatcher(0, i));
    	  
      }
      /*
      AbsoluteHardwareKnob knob = this.hardwareSurface.createAbsoluteHardwareKnob("ABS_KNOB");
      AbsoluteHardwareKnob knob2 = this.hardwareSurface.createAbsoluteHardwareKnob("ABS_KNOB2");
      AbsoluteHardwareKnob knob3 = this.hardwareSurface.createAbsoluteHardwareKnob("ABS_KNOB3");
      AbsoluteHardwareKnob knob4 = this.hardwareSurface.createAbsoluteHardwareKnob("ABS_KNOB4");
      
      
      knob.setAdjustValueMatcher(port.createAbsoluteCCValueMatcher(0, 1));
      knob2.setAdjustValueMatcher(port.createAbsoluteCCValueMatcher(0, 2));
      knob3.setAdjustValueMatcher(port.createAbsoluteCCValueMatcher(0, 3));
      knob4.setAdjustValueMatcher(port.createAbsoluteCCValueMatcher(0, 4));
      */
      
      final UUID eClapID = UUID.fromString("89eba41d-46d3-4506-8ce6-ba9fe3e3bee4");
      final UUID fm4ID = UUID.fromString("7a0a94df-3aa4-4bb5-8e24-2511999871ad");
      
      
      
      final CursorTrack cursorTrack = host.createCursorTrack("ELECTRA_CURSOR_TRACK", "Cursor Track", 2, 0, true);
      
      final PinnableCursorDevice cursorDevice = cursorTrack.createCursorDevice("ELECTRA_CURSOR_DEVICE", "Cursor Device", 0, CursorDeviceFollowMode.FOLLOW_SELECTION);
      final SendBank sendBank1 = cursorTrack.sendBank(); 
      
      final SpecificPluginDevice fabQ3 = cursorDevice.createSpecificVst3Device("72C4DB717A4D459AB97E51745D84B39D");
      final SpecificPluginDevice fabC2 = cursorDevice.createSpecificVst3Device("79F415E3C8E74807AD5DA3CF7024F618");
      final SpecificBitwigDevice eClap = cursorDevice.createSpecificBitwigDevice(eClapID);
      final SpecificBitwigDevice fm4 = cursorDevice.createSpecificBitwigDevice(fm4ID);
      
      
      Parameter [] fabQ3Params = new Parameter[100];
      Parameter [] fabC2Params = new Parameter[100];
      Parameter [] eClapParams = new Parameter[100];
      Parameter [] fm4Params = new Parameter[100];
      
      for(int i=0; i<100; i++){
    	  fabQ3Params[i] = fabQ3.createParameter(i);
    	  fabC2Params[i] = fabC2.createParameter(i);
    	  
      };
      eClapParams[0] = eClap.createParameter("DECAY");
      eClapParams[1] = eClap.createParameter("REPEAT_TIME");
      eClapParams[2] = eClap.createParameter("REPEAT_DURATION");
      eClapParams[3] = eClap.createParameter("WIDTH");
      eClapParams[4] = eClap.createParameter("FREQ");
      eClapParams[5] = eClap.createParameter("Q");
      eClapParams[6] = eClap.createParameter("VELOCITY_SENSITIVITY");
      eClapParams[7] = eClap.createParameter("OUTPUT");
      
      fm4Params[0] = fm4.createParameter("OP1_RATIO");
      fm4Params[1] = fm4.createParameter("OP2_RATIO");
      fm4Params[2] = fm4.createParameter("OP3_RATIO");
      fm4Params[3] = fm4.createParameter("OP4_RATIO");
      fm4Params[4] = fm4.createParameter("AEG_ATTACK");
      fm4Params[5] = fm4.createParameter("AEG_DECAY");
      fm4Params[6] = fm4.createParameter("AEG_SUSTAIN");
      fm4Params[7] = fm4.createParameter("AEG_RELEASE");
      
      for(int i=0; i<32; i++) {
    	  
    	  int parameterNumber = ParameterMappings.fabQ3[i];
    	  fabQ3Params[parameterNumber].addBinding(knob[i+1]);
      }
      for(int i=0; i<32; i++) {
    	  
    	  int parameterNumber = ParameterMappings.fabC2[i];
    	  fabC2Params[parameterNumber].addBinding(knob[i+1]);
      }
      for(int i=0; i<8; i++) {
    	  
    	  int parameterNumber = ParameterMappings.eClap[i];
    	  eClapParams[i].addBinding(knob[parameterNumber]);
      }
      for(int i=0; i<8; i++) {
    	  
    	  int parameterNumber = ParameterMappings.fm4[i];
    	  fm4Params[i].addBinding(knob[parameterNumber]);
      }
      /*
      Parameter fabQ3Freq = fabQ3.createParameter(2);
  
      Parameter fabQ3Gain = fabQ3.createParameter(3);
      Parameter fabC2Freq = fabC2.createParameter(2);
      Parameter fabC2Gain = fabC2.createParameter(3);
      */
      
      sendBank1.getItemAt(0).addBinding(knob[30]);
      sendBank1.getItemAt(1).addBinding(knob[31]);
      /*
      fabQ3Freq.addBinding(knob);
      knob2.addBinding(fabQ3Gain);
      fabC2Freq.addBinding(knob);
      knob2.addBinding(fabC2Gain);
      fabC2Gain.markInterested();
		*/
      
     /// a lot of out
      
      cursorDevice.name().addValueObserver((newName) -> {
    	  host.println(newName);
    	  if (newName.equals("FabFilter Pro-Q 3")) {
    		outPort.sendMidi(0xc0, 1, 127); 
    		outPort2.sendMidi(0xc0, 1, 127); 
    		};
    	  if (newName.equals("FabFilter Pro-C 2")) {
        		outPort.sendMidi(0xc0, 2, 127); 
        		outPort2.sendMidi(0xc0, 2, 127); 
        	};
    	  if (newName.equals("E-Clap")) {
      		outPort.sendMidi(0xc0, 3, 127); 
      		outPort2.sendMidi(0xc0, 3, 127); 
      		};
      	  if (newName.equals("FM-4")) {
        		outPort.sendMidi(0xc0, 4, 127); 
        		outPort2.sendMidi(0xc0, 4, 127); 
        	};
      });
      
      host.getMidiInPort(0).setMidiCallback((ShortMidiMessageReceivedCallback)msg -> {
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
	   
	   /*
	     
	   if (msg.isProgramChange()) {
		   if (msg.getData1() == 1) {
			   getHost().getMidiOutPort(0).sendMidi(0xc0, 1, 0); 
		   }
		   if (msg.getData1() == 2) {
			   getHost().getMidiOutPort(0).sendMidi(0xc0, 2, 0); 
		   }
		   if (msg.getData1() == 3) {
			   getHost().getMidiOutPort(0).sendMidi(0xc0, 1, 0); 
		   }
		   if (msg.getData1() == 4) {
			   getHost().getMidiOutPort(0).sendMidi(0xc0, 1, 0); 
		   }
	   }
*/
	
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

   public void sendMidiOut(int byte1, int byte2, int byte3)
   {
	   //getHost().getMidiOutPort(0).sendMidi(byte1, byte2, byte3);
	   
   }



}
