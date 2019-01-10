package com.reactivejade;

import com.facebook.react.ReactActivity;

import java.util.logging.Level;

import jade.android.AndroidHelper;
import jade.android.MicroRuntimeService;
import jade.android.MicroRuntimeServiceBinder;
import jade.android.RuntimeCallback;
import jade.core.MicroRuntime;
import jade.util.Logger;
import jade.util.leap.Properties;
import jade.wrapper.AgentController;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

public class MainActivity extends ReactActivity {
  private Logger logger = Logger.getJADELogger(this.getClass().getName());

  private MicroRuntimeServiceBinder microRuntimeServiceBinder;
  private ServiceConnection serviceConnection;

  private String nickname;

    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     */
    @Override
    protected String getMainComponentName() {
        return "ReactiveJade";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      logger.log(Level.SEVERE, "Unexpected exception creating chat agent!");
    }

    private void startContainer(final String nickname, Properties profile,
        final RuntimeCallback<AgentController> agentStartupCallback) {
      if (!MicroRuntime.isRunning()) {
        microRuntimeServiceBinder.startAgentContainer(profile,
            new RuntimeCallback<Void>() {
              @Override
              public void onSuccess(Void thisIsNull) {
                startAgent(nickname, agentStartupCallback);
              }

              @Override
              public void onFailure(Throwable throwable) {

              }
            });
      } else {
        startAgent(nickname, agentStartupCallback);
      }
    }

    private void startAgent(final String nickname,
        final RuntimeCallback<AgentController> agentStartupCallback) {

    }

    public void startChat(final String nickname, final String host,
        final String port,
        final RuntimeCallback<AgentController> agentStartupCallback) {

      final Properties profile = new Properties();

      if (microRuntimeServiceBinder == null) {
        serviceConnection = new ServiceConnection() {
          public void onServiceConnected(ComponentName className,
              IBinder service) {
            microRuntimeServiceBinder = (MicroRuntimeServiceBinder) service;
            startContainer(nickname, profile, agentStartupCallback);
          };

          public void onServiceDisconnected(ComponentName className) {
            microRuntimeServiceBinder = null;
          }
        };

        bindService(new Intent(getApplicationContext(),
            MicroRuntimeService.class), serviceConnection,
            Context.BIND_AUTO_CREATE);
      } else {
        startContainer(nickname, profile, agentStartupCallback);
      }      
    }
}
