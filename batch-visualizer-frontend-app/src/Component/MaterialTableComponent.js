import React from 'react';
import {makeStyles, withStyles} from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import MaterialTable from 'material-table';

import * as RESTAPI from '../Common/RestApi';


import { forwardRef } from 'react';
import AddBox from '@material-ui/icons/AddBox';
import ArrowUpward from '@material-ui/icons/ArrowUpward';
import Check from '@material-ui/icons/Check';
import ChevronLeft from '@material-ui/icons/ChevronLeft';
import ChevronRight from '@material-ui/icons/ChevronRight';
import Clear from '@material-ui/icons/Clear';
import DeleteOutline from '@material-ui/icons/DeleteOutline';
import Edit from '@material-ui/icons/Edit';
import FilterList from '@material-ui/icons/FilterList';
import FirstPage from '@material-ui/icons/FirstPage';
import LastPage from '@material-ui/icons/LastPage';
import Remove from '@material-ui/icons/Remove';
import SaveAlt from '@material-ui/icons/SaveAlt';
import Search from '@material-ui/icons/Search';
import ViewColumn from '@material-ui/icons/ViewColumn';

const tableIcons = {
    Add: forwardRef((props, ref) => <AddBox {...props} ref={ref} />),
    Check: forwardRef((props, ref) => <Check {...props} ref={ref} />),
    Clear: forwardRef((props, ref) => <Clear {...props} ref={ref} />),
    Delete: forwardRef((props, ref) => <DeleteOutline {...props} ref={ref} />),
    DetailPanel: forwardRef((props, ref) => <ChevronRight {...props} ref={ref} />),
    Edit: forwardRef((props, ref) => <Edit {...props} ref={ref} />),
    Export: forwardRef((props, ref) => <SaveAlt {...props} ref={ref} />),
    Filter: forwardRef((props, ref) => <FilterList {...props} ref={ref} />),
    FirstPage: forwardRef((props, ref) => <FirstPage {...props} ref={ref} />),
    LastPage: forwardRef((props, ref) => <LastPage {...props} ref={ref} />),
    NextPage: forwardRef((props, ref) => <ChevronRight {...props} ref={ref} />),
    PreviousPage: forwardRef((props, ref) => <ChevronLeft {...props} ref={ref} />),
    ResetSearch: forwardRef((props, ref) => <Clear {...props} ref={ref} />),
    Search: forwardRef((props, ref) => <Search {...props} ref={ref} />),
    SortArrow: forwardRef((props, ref) => <ArrowUpward {...props} ref={ref} />),
    ThirdStateCheck: forwardRef((props, ref) => <Remove {...props} ref={ref} />),
    ViewColumn: forwardRef((props, ref) => <ViewColumn {...props} ref={ref} />)
  };



class MaterialTableComponent extends React.Component {
    state = {
        response: null, // 배치 전체 리스트 호출 응답값
        // columns: [
        //     { title: '배치프로그램시스템ID', field: 'batchJobId.masterBatchId'},
        //     { title: '배치프로그램한글명', field: 'masterBatchName'},
        //     { title: '배치파일ID', field: 'batchFile.fileId'},
        //     { title: '배치파일명', field: 'batchFile.fileName'},
        //     { title: '호스트명', field: 'hostname'},
        //     { title: '파일유입여부', field: 'inputFileYn'},
        //     { title: '적용여부', field: 'applyYn'},
        // ]
    }

    constructor(props) {
        super(props);
        // this.fetchBatchList();
        console.log("constructor call>>>>>>>>>>>>>>>>>>>>>" );
    }
    
    componentDidMount () {
        
    }

    // 배치 전체 리스트를 호출하는 API를 호출하는 함수
    // fetchBatchList = async () => {
    //     const response = await RESTAPI.getBatchList();
    //     this.setState({
    //         ...this.state,
    //         response: response,
    //     })
    //     console.log("response>>>>>" + JSON.stringify(response.data.batchJobs));
    // }

    handleClick = (event, data) => {
        if (this.props.handleTableRowClick != null) {
            console.log("event" + event);
            console.log("row" + data);
            if (data.batchJobId != null) {  // 배치 리스트에서 호출된 경우
                this.props.handleTableRowClick(data.batchJobId.masterBatchId);
            } else if(data.userId != null) {
                console.log('data.userId>>>>>>>>>>>' + data.userId);
                this.props.handleTableRowClick(data.userId);
            }
        }
    }

    render() {
        const {classes} = this.props;
        return(
            <div>
            <MaterialTable
                title={this.props.title}
                columns={this.props.columns}
                data={this.props.data}
                // data={this.state.response.data.batchJobs}
                icons={tableIcons}
                options={this.props.options}
                onRowClick={this.handleClick}></MaterialTable>
            </div>
        )
    }
}

export default MaterialTableComponent;