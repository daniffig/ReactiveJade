import React, {Component} from 'react';

import {
  Button,
  Icon,
  ListItem
} from 'react-native-elements';

export default class HardwareSnifferJourneyReportListItem extends React.Component {

  render() {

    const journeyReport = this.props.journeyReport;

    return (
      <ListItem
        rightElement={
          <Button
            icon={
              <Icon name="expand-more" color="black" />
            }
            type="clear"
          />
        }
        title={journeyReport.agentName}
        subtitle={journeyReport.sentAt}
      />
    )
  }
}