import React, {Component} from 'react';

import {
  ListItem,
  Text
} from 'react-native-elements';

export default class HardwareSnifferReportListItem extends React.Component {

  render() {
    return (
      <ListItem
        rightElement={<Icon name="menu" color="black" />}
        key={0}
        title="title"
        subtitle="subtitle"
      />
    )
  }
}