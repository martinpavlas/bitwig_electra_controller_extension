package com.electra;
import java.util.UUID;

import com.bitwig.extension.api.PlatformType;
import com.bitwig.extension.controller.AutoDetectionMidiPortNamesList;
import com.bitwig.extension.controller.ControllerExtensionDefinition;
import com.bitwig.extension.controller.api.ControllerHost;

public class Electra2ExtensionDefinition extends ControllerExtensionDefinition
{
   private static final UUID DRIVER_ID = UUID.fromString("6b94a253-168c-4554-b325-bfa9ee329e62");
   
   public Electra2ExtensionDefinition()
   {
   }

   @Override
   public String getName()
   {
      return "Electra2";
   }
   
   @Override
   public String getAuthor()
   {
      return "andersskibsted";
   }

   @Override
   public String getVersion()
   {
      return "0.1";
   }

   @Override
   public UUID getId()
   {
      return DRIVER_ID;
   }
   
   @Override
   public String getHardwareVendor()
   {
      return "Electra";
   }
   
   @Override
   public String getHardwareModel()
   {
      return "Electra2";
   }

   @Override
   public int getRequiredAPIVersion()
   {
      return 13;
   }

   @Override
   public int getNumMidiInPorts()
   {
      return 2;
   }

   @Override
   public int getNumMidiOutPorts()
   {
      return 2;
   }

   @Override
   public void listAutoDetectionMidiPortNames(final AutoDetectionMidiPortNamesList list, final PlatformType platformType)
   {
      if (platformType == PlatformType.WINDOWS)
      {
         // TODO: Set the correct names of the ports for auto detection on Windows platform here
         // and uncomment this when port names are correct.
         // list.add(new String[]{"Input Port 0", "Input Port 1"}, new String[]{"Output Port 0", "Output Port 1"});
      }
      else if (platformType == PlatformType.MAC)
      {
         // TODO: Set the correct names of the ports for auto detection on Windows platform here
         // and uncomment this when port names are correct.
         // list.add(new String[]{"Input Port 0", "Input Port 1"}, new String[]{"Output Port 0", "Output Port 1"});
      }
      else if (platformType == PlatformType.LINUX)
      {
         // TODO: Set the correct names of the ports for auto detection on Windows platform here
         // and uncomment this when port names are correct.
         // list.add(new String[]{"Input Port 0", "Input Port 1"}, new String[]{"Output Port 0", "Output Port 1"});
      }
   }

   @Override
   public Electra2Extension createInstance(final ControllerHost host)
   {
      return new Electra2Extension(this, host);
   }
}
