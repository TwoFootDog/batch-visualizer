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
  //   fontSize: '30px',
  // },
  table: {
    marginTop: theme.spacing(5),
    padding: theme.spacing(0,10,0,10),
    minWidth: 1400,
    maxWidth: 1600
  }
})

class TpBatchExecList extends React.Component {
  state = {
    response: null, // 배치 전체 리스트 호출 응답값
    columns: [
        { title: '배치수행SEQ', field: 'batchHistoryId.batchExecSequence'},
        { title: '배치ID', field: 'batchId'},
        // { title: '마스터배치ID', field: 'masterBatchId'},
        // { title: '시작추적번호', field: 'fromTraceNumber'},
        // { title: '종료추적번호', field: 'toTraceNumber'},
        { title: '배치실행Explanation', field: 'batchExecExplanation'},
        { title: '배치실행Read건수', field: 'batchExecReadCount'},
        { title: '배치실행처리건수', field: 'batchExecProcessCount'},
        { title: '배치실행예외건수', field: 'batchExecExceptionCount'},
        { title: '배치실행상태', field: 'batchExecStatus'},
        { title: '배치Input파일명', field: 'batchExecInputFileName'},
        { title: '배치Output파일명', field: 'batchExecOutputFileName'},
    ],
    options: {
      headerStyle: {
        backgroundColor: '#039be5',
        color: '#FFF'
      },
      pageSize: 10
    }
  } 

  constructor(props) {
    super(props);
    this.fetchBatchHistoryListAll();
    console.log("TpBatchExecList constructor call>>>>>>>>>>>>>>>>>>>>>" );
  }

  fetchBatchHistoryListAll = async () => {
    const response = await RESTAPI.getBatchHistoryListAll();
    this.setState({
        ...this.state,
        response: response
    });
    console.log("fetchBatchHistoryListAll>>>>>>" + JSON.stringify(response));
}

  render() {
    const {classes} = this.props;

    if (!this.props.isLogin) {
      console.log("not signin");
      this.props.history.push('/signin');
    }
    
    if (this.state.response != null) {
      return(
        // <div>
        //   <div className={classes.subject}>TP배치프로그램 실행현황</div>
        //       <div  className={classes.table}>
        //           <BatchHistoryTableComponent masterBatchId={null}/>
        //       </div>
        // </div>

        <div className={classes.table}>
          <MaterialTableComponent 
            title={'TP배치프로그램 실행현황'}
            columns={this.state.columns}
            data={this.state.response.data.batchJobs} 
            options={this.state.options}/>
        </div>
      );
    } else {
      return null;
    }
  }
}

export default withStyles(useStyles)(withRouter(TpBatchExecList));