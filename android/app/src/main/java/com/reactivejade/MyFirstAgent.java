package com.reactivejade;

import android.widget.Toast;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactContext;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyFirstAgent extends Agent {

  private final static Logger logger = Logger.getLogger("com.reactivejade.MyFirstAgent");

  private ReactContext context;
  private Callback successCallback;

  protected void setup() {
    Object[] args = getArguments();

    logger.log(Level.WARNING, "setup");

    if (args != null && args.length > 0) {

      if (args[0] instanceof ReactContext) {
        context = (ReactContext) args[0];
      }

      if (args[1] != null) {
        successCallback = (Callback) args[1];
      }
    }

    // System.out.println("Hello there! from " + getAID().getName());

    addBehaviour(new TickerBehaviour(this, 100) {
      protected void onTick() {
        successCallback.invoke("General Kenobi!");
        // Toast.makeText(context, "General Kenobi!", Toast.LENGTH_SHORT).show();
      }
    });

  }

  protected void takeDown() {
    System.out.println("Cya!");
  }
}