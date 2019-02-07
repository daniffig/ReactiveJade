/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, {Component} from 'react';
import {
  Button,
  DeviceEventEmitter,
  Platform,
  StyleSheet,
  Text,
  TextInput,
  ToastAndroid,
  View,
} from 'react-native';

import DeviceInfo from 'react-native-device-info';

import ReactiveJade from './ReactiveJade';

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
      // platformHost: '10.1.37.240',
      platformHost: '192.168.0.6',
      platformPost: '1099',
      containerName: DeviceInfo.getDeviceName(),
      assignedContainerName: null,
      assignedAgentName: null
    }
  }

  componentWillMount() {
    DeviceEventEmitter.addListener('log', function(params) {
      console.log(params);
    });
  }

  startContainer = () => {
    console.log('App.js > startContainer');

    var _this = this;
    
    ReactiveJade.startContainer(
      this.state.containerName,
      this.state.platformHost,
      this.state.platformPort,
      (params) => {
        console.log('App.js > startContainer > success');

        _this.setState({ assignedContainerName: params.assignedContainerName });

        console.log(params.message);

        ToastAndroid.show(params.message, ToastAndroid.SHORT);
      },
      (params) => {
        console.log('App.js > startContainer > error');

        console.log(params.message);
        
        ToastAndroid.show(params.message, ToastAndroid.SHORT);
      }
    );
  }

  stopContainer = () => {
    console.log('App.js > stopContainer');

    var _this = this;

    ReactiveJade.stopContainer(
      (params) => {
        console.log('App.js > stopContainer > success');

        _this.setState({ assignedContainerName: null });

        console.log(params.message);
        
        ToastAndroid.show(params.message, ToastAndroid.SHORT);
      },
      (params) => {
        console.log('App.js > stopContainer > error');

        console.log(params.message);
        
        ToastAndroid.show(params.message, ToastAndroid.SHORT);
      }
    );
  }

  startAgent = () => {
    console.log('App.js > startAgent');

    var _this = this;

    ReactiveJade.startAgent(
      _this.state.containerName,
      (params) => {
        console.log('App.js > startAgent > success');

        _this.setState({ assignedAgentName: params.assignedAgentName });

        console.log(params.message);
        
        ToastAndroid.show(params.message, ToastAndroid.SHORT);
      },
      (params) => {
        console.log('App.js > startAgent > error');

        console.log(params.message);
        
        ToastAndroid.show(params.message, ToastAndroid.SHORT);
      }
    );
  }

  stopAgent = () => {
    console.log('App.js > stopAgent');

    var _this = this;

    ReactiveJade.stopAgent(
      (params) => {
        console.log('App.js > stopAgent > success');

        _this.setState({ assignedAgentName: null });

        console.log(params.message);
        
        ToastAndroid.show(params.message, ToastAndroid.SHORT);
      },
      (params) => {
        console.log('App.js > stopAgent > error');

        console.log(params.message);
        
        ToastAndroid.show(params.message, ToastAndroid.SHORT);

      }
    );
  }

  getContainers = () => {
    AgentComponent.getContainers(
      (msg) => {
        console.log(msg);
      }
    );
  }

  render() {
    carrierName = DeviceInfo.getCarrier();

    if (this.state.assignedContainerName == null) {
      containerButton = <Button onPress={this.startContainer} title="Start Container" />;
      agentButton = null;
    } else {
      containerButton = <Button onPress={this.stopContainer}  title="Stop Container" />;

      if (this.state.assignedAgentName == null) {
        agentButton = <Button onPress={this.startAgent} title="Start Agent" />
      } else {
        agentButton = <Button onPress={this.stopAgent} title="Stop Agent" />
      }
    }

    return (
      <View style={styles.container}>
        <TextInput
          onChangeText={(platformHost) => this.setState({platformHost})}
          value={this.state.platformHost}
        />
        <TextInput
          onChangeText={(containerName) => this.setState({containerName})}
          value={this.state.containerName}
        />
        {containerButton}
        {agentButton}
        <Button
          onPress={this.getContainers}
          title="getContainers"
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
