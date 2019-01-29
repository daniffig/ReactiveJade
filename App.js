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

import DeviceInfo from 'react-native-device-info';

import { DeviceEventEmitter } from 'react-native';

const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' + 'Cmd+D or shake for dev menu',
  android:
    'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});

type Props = {};
export default class App extends Component<Props> {

  constructor(props) {
    super(props);

    this.state = {
      agent: null
    }
  }

  componentWillMount() {
    DeviceEventEmitter.addListener('testMsg', function(params) {
      console.log(params);
      // console.log(params.freeMemory);
      // console.log(params.maxMemory);
      console.log('componentWillMount');
    })
  }

  startAgent = () => {
    console.log('startAgent');

    AgentComponent.start(
      (msg) => {
        console.warn(msg);
      },
      (agent) => {
        this.setState({agent});
      }
    )
  }

  stopAgent = () => {
    console.log('stopAgent');

    AgentComponent.stop(
      'ag',
      (msg) => {
        console.log(msg);
      },
      (msg) => {
        console.log(msg);
      }
    )

    // if (this.state.agent != null) {
    //   console.log(this.state.agent);
    // }

    console.log(this.state);
  }

  render() {
    carrierName = DeviceInfo.getCarrier();

    return (
      <View style={styles.container}>
        <Button
          onPress={this.startAgent}
          title="Start Agent"
        />
        <Button
          onPress={this.stopAgent}
          title="Stop Agent"
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
