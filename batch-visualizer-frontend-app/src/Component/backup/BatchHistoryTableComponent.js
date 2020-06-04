import React from 'react';
import * as RESTAPI  from '../Common/RestApi';
import {makeStyles, withStyles} from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';


const StyledTableCell = withStyles(theme => ({
    head: {
        backgroundColor: '#505050',
        color: theme.palette.common.white,
    },
    body: {
    },
}))(TableCell);

const StyledTableRow = withStyles(theme => ({
    root: {
        '&:nth-of-type(odd)': {
            backgroundColor: theme.palette.background.default,
        },
    },
}))(TableRow);

const useStyles = theme => ({
    root: {
        width: '100%',
        minWidth: 1700
        // padding: theme.spacing(5),

    },
    subject: {
        fontSize: '25px',
        paddingLeft: theme.spacing(2),
        // textAlign: 'center',
        marginTop: theme.spacing(6),
        marginBottom: theme.spacing(2)
    },
    table: {
        // minWidth: 1100
    },
});

class BatchHistoryTableComponent extends React.Component {
    state = {
        response: null
    }

    constructor(props) {
        super(props);
        if (props.masterBatchId === null) {
            this.fetchBatchHistoryListAll();
        } else {
            this.fetchBatchHistoryList();
        }
        
    }

    fetchBatchHistoryListAll = async () => {
        const response = await RESTAPI.getBatchHistoryListAll();
        this.setState({
            ...this.state,
            response: response
        });
        console.log("fetchBatchHistoryListAll>>>>>>" + JSON.stringify(response));
    }

    fetchBatchHistoryList = async () => {
        const response = await RESTAPI.getBatchHistoryList(this.props.masterBatchId);
        this.setState({
            ...this.state,
            response: response
        });
        console.log("fetchBatchHistoryList>>>>>>" + JSON.stringify(response));
    }

    render() {
        const {classes} = this.props;

        const tableBody = () => {
            if (this.state.response != null) {  // 최초 render시엔 response가 null임
                if (this.state.response.data.error.errorCode === '0000') {  // 히스토리 데이터가 미존재하는 경우
                    let tableData = this.state.response.data.batchJobs;
                    console.log("tableData>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + JSON.stringify(tableData));
                    return (
                        tableData.map(data => (
                            <StyledTableRow hover>
                                <StyledTableCell>{data.batchHistoryId.batchExecSequence}</StyledTableCell>
                                <StyledTableCell>{data.batchId}</StyledTableCell>
                                <StyledTableCell>{data.masterBatchId}</StyledTableCell>
                                <StyledTableCell>{data.fromTraceNumber}</StyledTableCell>
                                <StyledTableCell>{data.toTraceNumber}</StyledTableCell>
                                <StyledTableCell>{data.batchExecExplanation}</StyledTableCell>
                                {/* <StyledTableCell>{data.batchExecFromDate}</StyledTableCell> */}
                                {/* <StyledTableCell>{data.batchExecToDate}</StyledTableCell> */}
                                <StyledTableCell>{data.batchExecReadCount}</StyledTableCell>
                                <StyledTableCell>{data.batchExecProcessCount}</StyledTableCell>
                                <StyledTableCell>{data.batchExecExceptionCount}</StyledTableCell>
                                <StyledTableCell>{data.batchExecStatus}</StyledTableCell>
                                <StyledTableCell>{data.batchExecInputFileName}</StyledTableCell>
                                <StyledTableCell>{data.batchExecOutputFileName}</StyledTableCell>
                            </StyledTableRow>
                        ))
                    )
                } else {
                    return tableNullData();
                }
            } else {
                return tableNullData();
            }
        }
        
        /* 데이터가 없는 경우 호출되는 테이블 값*/
        const tableNullData = () => {
            return (
                <StyledTableRow>
                <StyledTableCell></StyledTableCell>
                <StyledTableCell></StyledTableCell>
                <StyledTableCell></StyledTableCell>
                <StyledTableCell></StyledTableCell>
                <StyledTableCell></StyledTableCell>
                <StyledTableCell></StyledTableCell>
                <StyledTableCell></StyledTableCell>
                <StyledTableCell></StyledTableCell>
                <StyledTableCell></StyledTableCell>
                <StyledTableCell></StyledTableCell>
                <StyledTableCell></StyledTableCell>
                <StyledTableCell></StyledTableCell>
                </StyledTableRow>
            )
        }

        return(
            <div className={classes.root}>
                <div className={classes.subject}>
                    배치실행이력
                </div>
                <Table className={classes.table}>
                    <TableHead>
                        <TableRow>
                            <StyledTableCell>배치수행SEQ</StyledTableCell>
                            <StyledTableCell>배치ID</StyledTableCell>
                            <StyledTableCell>마스터배치ID</StyledTableCell>
                            <StyledTableCell>시작추적번호</StyledTableCell>
                            <StyledTableCell>종료추적번호</StyledTableCell>
                            <StyledTableCell>배치실행Explanation</StyledTableCell>
                            {/* <StyledTableCell>배치실행시작시간</StyledTableCell>
                            <StyledTableCell>배치실행종료시간</StyledTableCell> */}
                            <StyledTableCell>배치실행Read건수</StyledTableCell>
                            <StyledTableCell>배치실행처리건수</StyledTableCell>         
                            <StyledTableCell>배치실행예외건수</StyledTableCell>                   
                            <StyledTableCell>배치실행상태</StyledTableCell>
                            <StyledTableCell>배치Input파일명</StyledTableCell>
                            <StyledTableCell>배치Output파일명</StyledTableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {tableBody()}
                    </TableBody>
                </Table>
            </div>
        )
    }
}

export default withStyles(useStyles)(BatchHistoryTableComponent);