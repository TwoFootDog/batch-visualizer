import React from 'react';
import { withStyles } from '@material-ui/core/styles';
import * as RESTAPI from '../Common/RestApi';
import MaterialTableComponent from '../Component/MaterialTableComponent';
import { TextField } from '@material-ui/core';
import Typography from '@material-ui/core/Typography';
import BatchDetailComponent from '../Component/BatchDetailComponent';
import Container from '@material-ui/core/Container';



const useStyles = theme => ({
    root: {
        paddingTop: theme.spacing(5),
        // minWidth: 1000,
        minWidth: 1500,
        maxWidth: 1700,
        backgroundColor: '#fafafa',
        padding: theme.spacing(10,10,10,10), // 상 우 하 좌
        margin: theme.spacing(2,2,2,2)
    },
    subject: {
        textTransform: 'none', // 대문자로 자동변환 방지
        fontSize: '25px',
        fontWeight: theme.typography.fontWeightBold,
    },
    batchDetail: {
        paddingTop: theme.spacing(3)
    },
    batchHistoryTable: {
        paddingTop: theme.spacing(4)
    }
})

class BatchDetail extends React.Component {

    state = {
        response: null,     // 배치 실행이력 호출 응답값
        response2: null,    // 배치 상세정보 호출 응답값
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
              backgroundColor: '#106bf4',
              color: '#FFF'
            }
          }
      }

    constructor (props) {
        super(props);
        this.fetchBatchHistoryList();
        this.batchDetailCall(props.masterBatchId);
        console.log("TpBatchExecList constructor call>>>>>>>>>>>>>>>>>>>>>" );
    }

    fetchBatchHistoryList = async () => {
        const response = await RESTAPI.getBatchHistoryList(this.props.masterBatchId);
        this.setState({
            ...this.state,
            response: response
        });
        console.log("fetchBatchHistoryList>>>>>>" + JSON.stringify(response));
    }

    batchDetailCall = async (masterBatchId) => {
        const response = await RESTAPI.getBatchDetailInfo(masterBatchId);
        console.log('response>>>>>>>>>>>' + JSON.stringify(response));
        this.setState({
            ...this.state,
            response2: response,
        })
    }

    render () {
        if (!this.props.isLogin) {  // 로그인 여부 확인
            console.log("not signin");
            this.props.history.push('/signin');
          }
          
        const {classes} = this.props;

        if (this.state.response != null && this.state.response2 != null ) { // 배치 실행이력과 배치상세정보 값을 받아온 경우
            return(
                <div className={classes.root}>
                    <div className={classes.subject}>{this.props.masterBatchId}{' 배치정보'}</div>
                    <div className={classes.batchDetail}>
                        <BatchDetailComponent response={this.state.response2}/>
                    </div>
                    <div  className={classes.batchHistoryTable}>
                        <MaterialTableComponent 
                            title={'배치 실행현황'}
                            columns={this.state.columns}
                            data={this.state.response.data.batchJobs}
                            options={this.state.options}/>
                    </div>
                </div>
            );
          } else {
            return null;
          }
    }

}

export default withStyles(useStyles)(BatchDetail);