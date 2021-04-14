package com.electra;


import com.bitwig.extension.controller.api.MidiOut;

public class SendMidi {
	
	public static void out(int byte1, int byte2, int byte3, MidiOut outPort)
	{
		
		outPort.sendMidi(byte1, byte2, byte3);
		
	}

}
