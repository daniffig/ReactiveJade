import React, {Component} from 'react';

import {
  View,
  ScrollView
} from 'react-native';

import {
  Text
} from 'react-native-elements';

import HardwareSnifferJourneyReportListItem from './../HardwareSnifferJourneyReportListItem';

export default class HardwareSnifferJourneyReportList extends React.Component {

  render() {
    const journeyReportList = this.props.journeyReportList;
    
    return (
      <View>
        <ScrollView>
          {
            journeyReportList.map((journeyReport, i) => (
              <HardwareSnifferJourneyReportListItem
                key={journeyReportList.length - 1 - i}
                journeyReport={journeyReport}
              />
            ))
          }
        </ScrollView>
      </View>
    )
  }
}
