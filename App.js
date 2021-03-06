/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, {Component} from 'react';
import {
  // Button,
  DeviceEventEmitter,
  Platform,
  ScrollView,
  StyleSheet,
  ToastAndroid,
  View,
} from 'react-native';

import {
  Button,
  Card,
  Divider,
  Header,
  Icon,
  Input,
  ListItem,
  Overlay,
  Text
} from 'react-native-elements';

import DeviceInfo from 'react-native-device-info';

import ReactiveJade from './ReactiveJade';
import HardwareSnifferJourneyReportList from './components/HardwareSnifferJourneyReportList';
import HardwareSnifferJourneyReport from './components/HardwareSnifferJourneyReport';

type Props = {};
export default class App extends Component<Props> {

  constructor(props) {
    super(props);

    this.state = {
      // platformHost: '10.1.37.240',
      platformHost: '192.168.0.6',
      // platformHost: '192.168.0.10',
      platformPort: '1099',
      containerName: DeviceInfo.getDeviceName(),
      assignedContainerName: null,
      assignedAgentName: null,
      containerConfigurationIsVisible: false,
      hardwareSnifferJourneyReportList: [],
      hardwareSnifferJourneyReport: null
    }
  }

  componentWillMount() {
    var _this = this;

    DeviceEventEmitter.addListener('log', function(params) {
      console.log(params);
    });

    DeviceEventEmitter.addListener('reportList', function(params) {
      _this.setState({
        hardwareSnifferJourneyReportList: [params, ..._this.state.hardwareSnifferJourneyReportList]
      });
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
        
        ToastAndroid.show(params.message, ToastAndroid.LONG);
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
        _this.setState({ assignedAgentName: null });

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

  toggleContainerConfiguration = () => {
    this.setState({ containerConfigurationIsVisible: !this.state.containerConfigurationIsVisible });
  }

  openHardwareSnifferJourneyReport = (hardwareSnifferJourneyReport) => {
    console.log('App.js > openHardwareSnifferJourneyReport');

    this.setState({
      hardwareSnifferJourneyReport: hardwareSnifferJourneyReport
    });

    console.log(hardwareSnifferJourneyReport);
  }

  closeHardwareSnifferJourneyReport = () => {
    console.log('App.js > closeHardwareSnifferJourneyReport');

    this.setState(
      { hardwareSnifferJourneyReport: null }
    );
  }

  render() {
    carrierName = DeviceInfo.getCarrier();

    if (this.state.assignedContainerName == null) {
      containerButton = <Button
        // style={{ width: '100%' }}
        onPress={this.startContainer}
        title="Start Container" />;

      agentButton = null;
    } else {
      containerButton = <Button onPress={this.stopContainer} title="Stop Container" />;

      if (this.state.assignedAgentName == null) {
        agentButton = <Button onPress={this.startAgent} title="Start Agent" />
      } else {
        agentButton = <Button onPress={this.stopAgent} title="Stop Agent" />
      }
    }

    if (this.state.hardwareSnifferJourneyReport == null) {
      hardwareSnifferJourneyReport = null
    } else {
      hardwareSnifferJourneyReport = 
        <HardwareSnifferJourneyReport
          journeyReport={this.state.hardwareSnifferJourneyReport}
          closeHardwareSnifferJourneyReport={this.closeHardwareSnifferJourneyReport}        
        />
    }

    return (
      <View style={styles.container}>
        <Header
          placement="left"
          centerComponent={{
            text: 'ReactiveJade',
            style: { color: '#ffffff' }
          }}
          rightComponent={
            <Button
              icon={<Icon name="menu" color="white" />}
              onPress={this.toggleContainerConfiguration}
            ></Button>
          }
        />
        <Overlay
          isVisible={this.state.containerConfigurationIsVisible}
          overlayStyle={{
            height: 'auto'
          }}  
        >
          <View>
          <Text>Container Configuration</Text>
          <Input
            label="platformHost"
            onChangeText={(platformHost) => this.setState({platformHost})}
            value={this.state.platformHost}
          />
          <Input
            label="platformPort"
            onChangeText={(platformPort) => this.setState({platformPort})}
            value={this.state.platformPort}
          />
          <Input
            label="containerName"
            onChangeText={(containerName) => this.setState({containerName})}
            value={this.state.containerName}
          />
          <Button
            title="Close"
            onPress={this.toggleContainerConfiguration}
            containerStyle={{
              marginTop: 10
            }}
          ></Button>
          </View>
        </Overlay>

        {containerButton}
        {agentButton}

        <HardwareSnifferJourneyReportList
          journeyReportList={this.state.hardwareSnifferJourneyReportList}
          openHardwareSnifferJourneyReport={this.openHardwareSnifferJourneyReport}
        />

        {hardwareSnifferJourneyReport}
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    // justifyContent: 'center',
    // alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  // welcome: {
  //   fontSize: 20,
  //   textAlign: 'center',
  //   margin: 10,
  // },
  openContainerConfigurationButton: {
    color: 'white'
  },
  containerControlButton: {
    width: '100%'
  }
});
