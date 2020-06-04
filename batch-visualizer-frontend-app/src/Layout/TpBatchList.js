import React from 'react';
import * as RESTAPI from '../Common/RestApi';
import MaterialTableComponent from '../Component/MaterialTableComponent';
import { withRouter } from 'react-router-dom';
import { withStyles } from '@material-ui/core/styles';

const useStyles = theme => ({
  // subject: {
  //   marginTop: theme.spacing(5),
  //   marginBottom: theme.spacing(5),
  //   padding: theme.spacing(0,10,0,10),
  //   // textAlign: 'center',
  //   fontSize: '30px',
  // },
  table: {
    marginTop: theme.spacing(5),
    padding: theme.spacing(0,10,0,10),
    minWidth: 1300,
    maxWidth: 1600
  }
})

class TableLayout extends React.Component {
  state = {
    response: null, // 배치 전체 리스트 호출 응답값
    columns: [
        { title: '배치프로그램시스템ID', field: 'batchJobId.masterBatchId'},
        { title: '배치프로그램한글명', field: 'masterBatchName'},
        { title: '배치파일ID', field: 'batchFile.fileId'},
        { title: '배치파일명', field: 'batchFile.fileName'},
        { title: '호스트명', field: 'hostname'},
        { title: '파일유입여부', field: 'inputFileYn'},
        { title: '적용여부', field: 'applyYn'}
    ],
    options: {
      headerStyle: {
        backgroundColor: '#01579b',
        color: '#FFF'
      },
      pageSize: 10
    }
  }
  constructor(props) {
    super(props);
    this.fetchBatchList();
    console.log("TpBatchList constructor call>>>>>>>>>>>>>>>>>>>>>" );
  }

  fetchBatchList = async () => {
    const response = await RESTAPI.getBatchList();
    this.setState({
        ...this.state,
        response: response,
    })
    console.log("response>>>>>" + JSON.stringify(response.data.batchJobs));
}

  handleTableRowClick = (masterBatchId) => {
    console.log('masterBatchId>>>>>>>>>>>>>'+ masterBatchId);
        this.props.history.push({
      pathname: `/batchDetail/masterBatchId/${masterBatchId}`,
      state: { masterBatchId: masterBatchId }
    });
  }

  render() {
    const {classes} = this.props;

    if (!this.props.isLogin) {
      console.log("not signin");
      this.props.history.push('/signin');
    }
    
  
    if (this.state.response != null) {
      return(
        <div  className={classes.table}>
          <MaterialTableComponent 
            title={'TP배치프로그램 목록'}
            columns={this.state.columns}
            data={this.state.response.data.batchJobs}
            options={this.state.options}
            handleTableRowClick={this.handleTableRowClick}/>
        </div>
      );
    } else {
      return null;
    }
  }
}

export default withStyles(useStyles)(withRouter(TableLayout));