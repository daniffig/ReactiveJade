import React, {Component} from 'react';

import {
  View,
  ScrollView
} from 'react-native';

import {
  Divider,
  Overlay,
  Text
} from 'react-native-elements';

import HardwareSnifferReport from './../HardwareSnifferReport';

export default class HardwareSnifferJourneyReport extends React.Component {

  render() {
    const journeyReport = this.props.journeyReport;
    const isVisible = journeyReport !== null;

    reportList = isVisible ? journeyReport.reportList : [];

    console.log(reportList);
    
    return (
      <Overlay isVisible={isVisible}
        onBackdropPress={this.props.closeHardwareSnifferJourneyReport}
      >
        <View style={{ flex: 1, flexDirection: 'column' }}>
          <Text>
            <Text style={{fontWeight: "bold"}}>Reported by </Text>
            <Text>{journeyReport.agentName}</Text>
            <Text style={{fontWeight: "bold"}}> at </Text>
            <Text>{journeyReport.sentAt}</Text>
            <Text style={{fontWeight: "bold"}}> taking </Text>
            <Text>{journeyReport.elapsedTime}ms</Text>
          </Text>
          <Divider />
          <ScrollView overScrollMode="never">
          {
            reportList.map((report, i) => (
              <View key={i}>
                <HardwareSnifferReport
                  key={i}
                  report={report}
                />
                <Divider />
              </View>
              
            ))
          }
          </ScrollView>
        </View>
        
      </Overlay>
    )
  }
}
