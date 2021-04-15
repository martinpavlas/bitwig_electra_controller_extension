package com.electra;

import java.util.HashMap;
import java.util.UUID;
import java.util.Map;

import com.bitwig.extension.api.Host;
import com.bitwig.extension.controller.api.CursorDevice;
import com.bitwig.extension.controller.api.Parameter;
import com.bitwig.extension.controller.api.PinnableCursorDevice;
import com.bitwig.extension.controller.api.SpecificBitwigDevice;
import com.bitwig.extension.controller.api.SpecificPluginDevice;

public class Devices {
	
	public HashMap<String,String> vst3IDs;
	public HashMap<String,UUID> bitwigDeviceIDs;
	public HashMap<String,SpecificPluginDevice> vst3s = new HashMap<String,SpecificPluginDevice>();
	public HashMap<String,SpecificBitwigDevice> bitwigDevices = new HashMap<String,SpecificBitwigDevice>();
	public HashMap<String,Parameter[]> parameters = new HashMap<String,Parameter[]>();
	
	
	public Parameter [] fabfilterProQ3Parameters = new Parameter[100];
	public Parameter [] fabfilterProC2Parameters = new Parameter[100];
	public Parameter [] eClapParameters = new Parameter[100];
	public Parameter [] fm4Parameters = new Parameter[100];
	
	
	public Devices(CursorDevice cursorDevice) {
		

		//vst3 devices
		vst3s.put(DeviceNames.FABFILTER_PRO_Q3, cursorDevice.createSpecificVst3Device(DeviceNames.FABFILTER_PRO_Q3_ID));
		vst3s.put(DeviceNames.FABFILTER_PRO_C2, cursorDevice.createSpecificVst3Device(DeviceNames.FABFILTER_PRO_C2_ID));
		
		//Bitwig Devices
		bitwigDevices.put("E-Clap", cursorDevice.createSpecificBitwigDevice(UUID.fromString("7a0a94df-3aa4-4bb5-8e24-2511999871ad")));
		bitwigDevices.put("FM-4", cursorDevice.createSpecificBitwigDevice(UUID.fromString("7a0a94df-3aa4-4bb5-8e24-2511999871ad")));
		
		//Create Parameters
		for (int i=0; i<100; i++) {
			
			//vst3 parameters
			fabfilterProQ3Parameters[i] = vst3s.get(DeviceNames.FABFILTER_PRO_Q3).createParameter(i);
			fabfilterProC2Parameters[i] = vst3s.get(DeviceNames.FABFILTER_PRO_C2).createParameter(i);
			
			//bitwig device parameters
			if (i<DeviceNames.E_CLAP_PARAMETER_NAMES.length) {
				eClapParameters[i] = bitwigDevices.get(DeviceNames.E_CLAP).createParameter(DeviceNames.E_CLAP_PARAMETER_NAMES[i]);
			}
			if (i<DeviceNames.FM_4_PARAMETER_NAMES.length) {
				fm4Parameters[i] = bitwigDevices.get(DeviceNames.FM_4).createParameter(DeviceNames.FM_4_PARAMETER_NAMES[i]);
			}
			
		
		}
		
		//put VST3 Parameters in HashMap
		parameters.put(DeviceNames.FABFILTER_PRO_Q3, fabfilterProQ3Parameters);
		parameters.put(DeviceNames.FABFILTER_PRO_C2, fabfilterProC2Parameters);
		
		//put VST3 Parameters in HashMap
		parameters.put(DeviceNames.E_CLAP, eClapParameters);
		parameters.put(DeviceNames.FM_4, fm4Parameters);
		
	}

	
	public SpecificPluginDevice getVST3(String vst3Name) {
		
		SpecificPluginDevice vst3 = vst3s.get(vst3Name);
		
		return vst3;
	}
	
	public SpecificBitwigDevice getBitwigDevice(String bitwigDeviceName) {
		
		SpecificBitwigDevice bitwigDevice = bitwigDevices.get(bitwigDeviceName);
		
		return bitwigDevice;
	}
	
	public Parameter getParameter(String deviceName, int parameterIndex) {
		
		Parameter param = parameters.get(deviceName)[parameterIndex];
		
		
		return param;
	}
	


}
