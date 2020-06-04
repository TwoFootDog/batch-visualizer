import React from 'react';
import { Typography, Dialog, DialogTitle, DialogContentText, DialogContent, ExpansionPanel, ExpansionPanelSummary, Stepper, StepContent, StepLabel, Step, Link } from "@material-ui/core";
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import { makeStyles } from '@material-ui/core/styles';

const useStyles = makeStyles(theme => ({
  root: {
    width: '100%',
  },
  paper: {
    marginTop: theme.spacing(3),
    width: '100%',
    overflowX: 'auto',
    marginBottom: theme.spacing(2),
  },
  table: {
    minWidth: 650,
  },
}));

function createData(name, calories, fat, carbs, protein) {
  return { name, calories, fat, carbs, protein };
}

const rows = [
  createData('Frozen yoghurt', 159, 6.0, 24, 4.0),
  createData('Ice cream sandwich', 237, 9.0, 37, 4.3),
  createData('Eclair', 262, 16.0, 24, 6.0),
  createData('Cupcake', 305, 3.7, 67, 4.3),
  createData('Gingerbread', 356, 16.0, 49, 3.9),
];

export class HelpDialog extends React.Component{
  constructor(props) {
    super(props);
    this.state = { 
      step: 0,
      batchId: '',
      batchName: this.props.batchName,
      batchExecFilePath: '',
      batchType: '',
      batchExecDate: '',
      batchWorkFlag: '' 
    }
    
    console.log("this.props.batchName : " + this.props.batchName);
  }

  render() {
    
    // var classes = useStyles();

    if(this.props.batchInfoData !== null){
      var batchData = this.props.batchInfoData['batchJobs'][0];
      console.log("this.state.batchData: " + this.props.batchInfoData['batchJobs'][0]['batchName']);
      this.state.batchId = batchData['batchJobId']['batchId'];
      this.state.batchName = batchData['batchName'];
      this.state.batchExecFilePath = batchData['batchExecFilePathName'];
      this.state.batchType = batchData['batchType'];
      this.state.batchExecDate = batchData['batchExecDate'];
      this.state.batchWorkFlag = batchData['batchWorkFlag'];
      // this.setState(this.state);
    }
    
        
    return (
      
      <Dialog
        open={this.props.open}
        onClose={this.props.onClose}
        fullWidth
        maxWidth='sm'
        style={{maxHeight:'90vh', height:'auto'}}>
        <DialogTitle size="medium">
          <Typography variant="h5" component="h4">
           배치상세정보
          </Typography>
        </DialogTitle>
        
        <div style={{overflow:'hidden', width:'100%'}}>
          <DialogContent>
            <Typography gutterBottom>
            <Table size="small" aria-label="a dense table" minWidth>
                <TableRow>
                  <TableCell>배치 ID</TableCell>
                  <TableCell align="left">{this.state.batchId}</TableCell>
                </TableRow>
                <TableRow>
                  <TableCell>배치 이름</TableCell>
                  <TableCell>{this.state.batchName}</TableCell>
                </TableRow>
                <TableRow>
                  <TableCell>배치 실행 경로</TableCell>
                  <TableCell>{this.state.batchExecFilePath}</TableCell>
                </TableRow>
                <TableRow>
                  <TableCell>배치 유형</TableCell>
                  <TableCell>{this.state.batchType}</TableCell>
                </TableRow>
                <TableRow>
                  <TableCell>배치 실행 일시</TableCell>
                  <TableCell>{this.state.batchExecDate}</TableCell>
                </TableRow>
                <TableRow>
                  <TableCell>배치 작업 플래그</TableCell>
                  <TableCell>{this.state.batchWorkFlag}</TableCell>
                </TableRow>
                </Table>
              </Typography>
          </DialogContent>

        </div>
      </Dialog>
    )
  }
}

export default HelpDialog;