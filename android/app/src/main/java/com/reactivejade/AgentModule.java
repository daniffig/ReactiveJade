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
  private AgentController agentController;

  public AgentModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return "AgentComponent";
  }

  @ReactMethod
  public void killAgent(
      Callback callback
  ) {

  }

  @ReactMethod
  public void stop(
      String agentName,
      Callback errorCallback,
      Callback successCallback
  ) {
    if (this.agentContainer == null) {
      errorCallback.invoke("agentContainer is null");
    } else {
      try {
        this.agentContainer.kill();
        this.agentContainer = null;

        errorCallback.invoke("success!");
      } catch (Exception e) {
        errorCallback.invoke(e.getMessage());
      }
    }
  }

  private AgentContainer startContainer(
      String mainHost,
      String mainPort
  ) {
    Profile profile = new ProfileImpl();
    profile.setParameter(Profile.MAIN, "peripheral");
    profile.setParameter(Profile.CONTAINER_NAME, "Xiaomi Mi 8");
    profile.setParameter(Profile.MAIN_HOST, mainHost);
    profile.setParameter(Profile.MAIN_PORT, mainPort);

    return Runtime.instance().createAgentContainer(profile);
  }

  @ReactMethod
  public void start(
      String mainHost,
      String mainPort,
      Callback errorCallback,
      Callback successCallback) {

    if (this.agentContainer == null) {
      this.agentContainer = this.startContainer(mainHost, mainPort);
    }

    if (this.agentController == null) {
      try {
        this.agentController = this.agentContainer.createNewAgent(
          "myFirstAgent",
          MyFirstAgent.class.getName(),
          new Object[] {
            getReactApplicationContext()
          }
        );

        this.agentController.start();

        successCallback.invoke(this.agentController.getName());
      } catch (Exception e) {
        errorCallback.invoke(e.getMessage());
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

  @ReactMethod
  public void getContainers(
      Callback callback
  ) {
  }
}


