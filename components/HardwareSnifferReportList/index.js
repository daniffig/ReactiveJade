import React, {Component} from 'react';

import {
  View,
} from 'react-native';

import {
  ListItem,
  Text
} from 'react-native-elements';

export default class HardwareSnifferReportList extends React.Component {

  render() {
    const props = this.props;
    
    return (
      <View>
        <ScrollView>

        </ScrollView>
      </View>
      <ListItem
        rightElement={<Icon name="menu" color="black" />}
        key={0}
        title="title"
        subtitle="subtitle"
      />
    )
  }
}