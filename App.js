/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, {Component} from 'react';
import {Platform, StyleSheet, Text, View, Button} from 'react-native';

import AgentComponent from './AgentComponent';
import ToastExample from './ToastExample';

import DeviceInfo from 'react-native-device-info';

import DeviceEventEmitter from 'react-native';

const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' + 'Cmd+D or shake for dev menu',
  android:
    'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});

type Props = {};
export default class App extends Component<Props> {

  componentWillMount() {
  }

  handlePress() {
    DeviceEventEmitter.addListener("testMsg", function (e) {
      ToastExample.show("msg", ToastExample.SHORT);
    });
    // AgentComponent.show(
    //   'fede14',
    //   (msg) => {
    //     console.warn(msg);
    //   },
    //   (value) => {
    //     ToastExample.show(value, ToastExample.SHORT);
    //   }
    // )

    AgentComponent.start(
      (msg) => {
        console.warn(msg);
      },
      (msg) => {
        console.warn(msg);
      }
    )
  }

  render() {
    carrierName = DeviceInfo.getCarrier();

    return (
      <View style={styles.container}>
        <Button
          onPress={this.handlePress}
          title="Toast"
        />
        <Text style={styles.welcome}>{carrierName}</Text>
        <Text style={styles.welcome}>Welcome to React Native!</Text>
        <Text style={styles.instructions}>To get started, edit App.js</Text>
        <Text style={styles.instructions}>{instructions}</Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});
