import React, {Component} from 'react';

import {
  StyleSheet,
  View
} from 'react-native';

import {
  Divider,
  Text
} from 'react-native-elements';

// https://github.com/Gil2015/react-native-table-component

export default class HardwareSnifferReport extends React.Component {

  formatMemory = (memory) => {
    MiB = 1024.0;

    return (memory / MiB).toFixed(2) + " MiB";
  }

  render() {
    console.log("HardwareSnifferReport > render");

    const report = this.props.report;

    reportCommon =
    <View>
      <Text>  
        <Text style={styles.reportDt}>Container.Name: </Text>
        <Text style={styles.reportDd}>{report.containerName}</Text>
      </Text>
      <Text>
        <Text style={styles.reportDt}>Report.Date: </Text>
        <Text style={styles.reportDd}>{report.reportDate}</Text>
      </Text>
    </View>

    if (report.hasError) {
      reportDetail =
      <View>
        <Text>
          <Text style={styles.reportDt}>Report.hasError: </Text>
          <Text style={styles.reportDd}>{report.hasError.toString()}</Text>
        </Text>
        <Text>
          <Text style={styles.reportDt}>Report.errorMessage: </Text>
          <Text style={styles.reportDd}>{report.errorMessage}</Text>
        </Text>
      </View>
    } else {      
      reportDetail = 
      <View>
        <Text>
          <Text style={styles.reportDt}>PhysicalMemory.Total: </Text>
          <Text style={styles.reportDd}>{ this.formatMemory(report.totalPhysicalMemory) }</Text>
        </Text>
        <Text>
          <Text style={styles.reportDt}>PhysicalMemory.Free: </Text>
          <Text style={styles.reportDd}>{ this.formatMemory(report.freePhysicalMemory) }</Text>
        </Text>
        <Text>
          <Text style={styles.reportDt}>VirtualMemory.Total: </Text>
          <Text style={styles.reportDd}>{ this.formatMemory(report.totalVirtualMemory) }</Text>
        </Text>
        <Text>
          <Text style={styles.reportDt}>VirtualMemory.Free: </Text>
          <Text style={styles.reportDd}>{ this.formatMemory(report.freeVirtualMemory) }</Text>
        </Text>
        <Text>
          <Text style={styles.reportDt}>System.LoadAverage: </Text>
          <Text style={styles.reportDd}>{report.systemLoadAverage}</Text>
        </Text>
        <Text>
          <Text style={styles.reportDt}>OperatingSystem.Name: </Text>
          <Text style={styles.reportDd}>{report.operatingSystemName}</Text>
        </Text>
        <Text>
          <Text style={styles.reportDt}>VirtualMachine.Name: </Text>
          <Text style={styles.reportDd}>{report.virtualMachineName}</Text>
        </Text>
      </View>
    }

    return (
      <View>
        {reportCommon}
        {reportDetail}
      </View>
    )
  }
}

const styles = StyleSheet.create({
  reportDt: {
    fontWeight: 'bold'
  },
  reportDd: {
    textAlign: 'right',
    width: '100%'
  }
});