package com.reactivejade;

import com.facebook.react.ReactActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

public class MainActivity extends ReactActivity {
  // private Logger logger = Logger.getJADELogger(this.getClass().getName());

  /**
   * Returns the name of the main component registered from JavaScript.
   * This is used to schedule rendering of the component.
   */
  @Override
  protected String getMainComponentName() {
      return "ReactiveJade";
  }

  // @Override
  // public void onCreate(Bundle savedInstanceState) {
  //   super.onCreate(savedInstanceState);

  //   logger.log(Level.SEVERE, "Unexpected exception creating chat agent!");
  // }
}
