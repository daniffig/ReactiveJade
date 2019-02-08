import React, {Component} from 'react';

import {
  Icon,
  ListItem
} from 'react-native-elements';

export default class HardwareSnifferJourneyReportListItem extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      reportListIsVisible: false
    }
  }

  toggleReportList = () => {
    this.setState({ reportListIsVisible: !this.state.reportListIsVisible });
  }

  render() {

    const journeyReport = this.props.journeyReport;

    return (
      <ListItem
        rightElement={
          <Icon name="expand-more" color="black" />
        }
        title={journeyReport.agentName}
        subtitle={journeyReport.sentAt}
        onPress={
          () => this.props.openHardwareSnifferJourneyReport(journeyReport)
        }
      />
    )
  }
}