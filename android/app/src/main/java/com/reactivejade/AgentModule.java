// AgentModule.java

package com.reactivejade;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.facebook.react.bridge.Callback;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.util.leap.Properties;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

import jade.android.MicroRuntimeService;
import jade.android.MicroRuntimeServiceBinder;
import jade.core.MicroRuntime;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AgentModule extends ReactContextBaseJavaModule {

  private final static Logger logger = Logger.getLogger("com.reactivejade.AgentModule");
  private AgentContainer agentContainer;
  private AgentController myAgent;

  public AgentModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return "AgentComponent";
  }

  @ReactMethod
  public void stop(
      String agentName,
      Callback errorCallback,
      Callback successCallback
  ) {
    if (myAgent != null) {
      try {
        myAgent.kill();
      } catch (Exception e) {

      }
    }
    // if (MicroRuntime.isRunning()) {
    //   try {
    //     MicroRuntime.killAgent("ag");

    //     successCallback.invoke("agent ag successfully stopped!");
    //   } catch (Exception e) {

    //   }
    // } else {
    //   errorCallback.invoke("runtime not running");
    // }
  }

  @ReactMethod
  public void start(
      Callback errorCallback,
      Callback successCallback) {

    // Properties profile = new Properties();
    //properties.setProperty(Profile.PLATFORM_ID, "JADE");
    // profile.setProperty(Profile.MAIN_HOST, "192.168.0.6");
    // profile.setProperty(Profile.MAIN_PORT, "1099");

    Profile profile = new ProfileImpl();
    profile.setParameter(Profile.MAIN, "peripheral");
    profile.setParameter(Profile.CONTAINER_NAME, "Xiaomi Mi 8");
    profile.setParameter(Profile.MAIN_HOST, "192.168.0.6");
    profile.setParameter(Profile.MAIN_PORT, "1099");

    // Profile profile = new ProfileImpl(properties);

    Runtime runtime = Runtime.instance();

    if (agentContainer == null) {
      agentContainer = runtime.createAgentContainer(profile);
    }

    if (myAgent == null) {
      try {
        myAgent = agentContainer.createNewAgent(
          "myFirstAgent",
          MyFirstAgent.class.getName(),
          new Object[] {
            getReactApplicationContext()
          }
        );

        // successCallback.invoke(agentContainer.getPlatformController().getName());
      } catch (Exception e) {
        errorCallback.invoke("An error happened while creating yout agent.");
      }
    }

    // Runtime.instance().startUp(profile);

    //MicroRuntime.startJADE(profile, null);

    // if (!MicroRuntime.isRunning()) {
    //   errorCallback.invoke("error!");
    // } else {
    //   try {
    //     MicroRuntime.startAgent(
    //         "ag",
    //         MyFirstAgent.class.getName(),
    //         new Object[] {
    //           getReactApplicationContext()
    //         }
    //     );

    //     successCallback.invoke("lolo");
    //   } catch (Exception e) {
    //     errorCallback.invoke("error while starting agent!");
    //   }
    // }

    // Runtime rt = Runtime.instance();
    // // final Properties profile = new Properties();
    // // profile.setProperty(Profile.MAIN_HOST, "localhost");
    // // profile.setProperty(Profile.MAIN_PORT, 8888);
    // // profile.setProperty(Profile.MAIN, Boolean.FALSE.toString());
    // // profile.setProperty(Profile.JVM, Profile.ANDROID);
    // Profile profile = new ProfileImpl("localhost", 8888, "main");
    // AgentContainer mainContainer = rt.createMainContainer(profile);

    // AgentController myFirstAgent = null;

    // try {
    //   myFirstAgent = mainContainer.createNewAgent(
    //       "myFirstAgent",
    //       "com.reactivejade.MyFirstAgent",
    //       new Object[] { getReactApplicationContext(), successCallback });

    //   myFirstAgent.start();
    //   // successCallback.invoke("Success!");
    // } catch (Exception e) {
    //   errorCallback.invoke(e.getMessage());
    // }
  }
}


