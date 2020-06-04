import React from 'react';
import { Flowpoint, Flowspace } from 'flowpoints';
import HelpDialog from '../Component/BatchDiagram/HelpDialog';
import MainLibrary from '../Component/BatchDiagram/MainLibrary';
import axios from 'axios';
import { withRouter } from 'react-router-dom';
import TextField from '@material-ui/core/TextField';

class BatchDiagram extends React.Component {

  constructor(props) {
		super(props);
    if (!this.props.isLogin) {
      console.log('not signin');
      this.props.history.push('/signin');
    } else {
		  // Helpers
      this.diagramRef = null;
      this.fetchBatchList();
    }
    // Building state library
    this.state = MainLibrary()
    
  }

  shapeBox = (shape, hname) => {
    var msg = '['
    shape.map(val => {
      msg += val + ','
    })
    if (shape.length > 0) msg = msg.substring(0, msg.length - 1)
    msg += ']'
    return (
      <div style={{textAlign:'center', padding:0, }}>
        {/* paddingpaddingBottom:0, paddingTop:0 */}
        {
          hname
        }
      </div>
    )
  }

  getBatchList = () => {
    // return axios.get('http://198.13.47.188:8080/graph/BTPBATCH001');
    return axios.get('http://66.42.43.41:31700/graph/BTPBATCH001');
  }
  
  getBatchInfo = (batchName) => {
    return axios.get('http://66.42.43.41:31700/bo/' + batchName);
    // return axios.get('http://66.42.43.41:31700/bo');
    // return axios.get('http://198.13.47.188:8080/bo/' + batchName);
	}

  fetchBatchList = async () => {
		console.log("fetchBatchList() is called.");
		const response = await this.getBatchList();
		this.setState({
			...this.state,
			data: response.data,
		});
		console.log("qqqqqq : " + (this.state.data)['batchNodes'][0]['batchId']);
  }
  
	getBoBatchInfo = async (batchName) => {
    console.log("fetchBatch() is called. : " + batchName);
    
    const response = await this.getBatchInfo(batchName);
    this.setState({
      ...this.state,
      batchInfoData: response.data,
    });
    
		console.log("HELP DIALOGUE : " + (this.state.batchInfoData).toString());
  }

  handleClick(key, e) {

    this.getBoBatchInfo(key);
    // Loading from state
    var state = this.state;
    state.clicked = true;
    state.visual.show_help_dialog = !state.visual.show_help_dialog;
    state.visual.show_batch_name = key;
    state.selected = key;
    this.setState(state)
    console.log("key: " + key + ", e: " + e);
    console.log("this.state.selected: " + this.state.selected);
    // this.showHideHelp(key);

  }

  handleHover(name, e){
    
    let state = this.state;

    if(e === true){
      
      state.selected = name;
      this.setState(state)
      
    } else {
    
      if(!state.clicked){
        console.log("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        state.selected = null;
        this.setState(state);
      }
    }

  }

  showHideHelp(key) {
    var visual = this.state.visual;
    visual.show_help_dialog = !visual.show_help_dialog;
    visual.show_batch_name = key;
    this.setState({visual})
  }

  addFlowpoint(name, hname, posX, posY) {

    // Loading from state
    var flowpoints = this.state.flowpoints;
    var settings = this.state.settings;

    // Creating flowpoint
    const base_ref = hname;
    var newPoint = {
      base_ref: base_ref,
      name: name,
      outputs: {},
      is_output: false,
      concat_inputs: false,
      concat_dim: 0,
      output_shape: [],
      contiguous: false,
      reshape_ndims: 0,
      reshape_dims: [],
      pos: {
        x: posX,
        y: posY
      },
      // content: this.getEmptyFlowpointContent(base_ref)
    }
    flowpoints[name] = newPoint;

    settings.selected = '' + settings.count
    settings.count += 1
    settings.lastPos = {
      x: settings.lastPos.x,
      y: settings.lastPos.y + 90
    }

  }

  handleChange = (e) => {
    this.setState({
      ...this.state,
      selected: e.target.value
    })
  }
  

  render(){
    
    var aaa = 'Unloaded';
    if(this.state.data != null){
      // aaa = JSON.stringify(this.state.data.batchNodes);
      console.log(aaa);
      var nodes = this.state.flowpoints;
			let batchNodes = this.state.data.batchNodes;
			for(var k = 0; k < batchNodes.length; k++) {

				// console.log('aaasadasdasadw222222aa: ' + batchNodes[k].batchId);
        this.addFlowpoint(batchNodes[k].batchId, batchNodes[k].batchName, batchNodes[k].posX, batchNodes[k].posY);
      }

      for(var i = 0; i < batchNodes.length; i++){
				// const port1 = node1.addPort(new DefaultPortModel(false, 'out-1', 'next'));	
        console.log("qqqqqqqqqqqqqqqqq" + this.state.selected)
        var currentNode = batchNodes[i].batchId;
        var currentNodeHname = batchNodes[i].batchName;
        var nexts = batchNodes[i].next;
				for(var j = 0; j < nexts.length; j++){
          // console.log("@@@@@@@: " + currentNodeHname);
          // console.log("@@@##@@@: " + nodes[nexts[j]].base_ref);
          // console.log("@@@####@@@: " + this.state.selected);
          if(this.state.selected !== '' && (currentNode === this.state.selected || currentNodeHname.indexOf(this.state.selected) !== -1)){
            nodes[currentNode].outputs[nexts[j]] = {
              // NODE -> 'SELECTED' ON
              outputColor: this.state.colors.SELECTED_LINK_OUTPUT,
              inputColor: this.state.colors.SELECTED_LINK_INPUT,
              width: 4
            }
            continue;
          }
          // console.log("@@@@@@@ TRUE? : "+ (this.state.selected !== '' && (nexts[j] === this.state.selected || nodes[nexts[j]].base_ref.indexOf(this.state.selected) !== -1)));
          if(this.state.selected !== '' && (nexts[j] === this.state.selected || nodes[nexts[j]].base_ref.indexOf(this.state.selected) !== -1)){

            nodes[currentNode].outputs[nexts[j]] = {
              // outputLoc: 4,
              // inputLoc: 20,
              outputColor: this.state.colors.SELECTED_LINK_OUTPUT,
              inputColor: this.state.colors.SELECTED_LINK_INPUT,
              width: 4
            }
            continue;

          }
					nodes[currentNode].outputs[nexts[j]] = {
            // outputLoc: 4,
            // inputLoc: 20,
            outputColor: this.state.colors.DEFAULT_LINK_OUTPUT,
            inputColor:this.state.colors.DEFAULT_LINK_INPUT,
            width: 2
          }
				
				}	
				
      }

      var diagramDivStyle={
        boxShadow: "1px 3px 1px #9E9E9E", display: 'flex',  justifyContent:'center', alignItems:'center', paddingLeft:20, paddingRight:20, paddingBottom: 20, border:'1px solid'
      }
      
		}
    
    return (
     
      <div style={{backgroundColor: (this.state.visual.darkTheme ? 'black' : 'white'), paddingLeft:30, paddingRight:30, paddingBottom: 30}}>
        
        <div style={{display: 'flex',  justifyContent:'center', alignItems:'center'}}>
          <h1>
            <p></p>
            <p>일마감 FLOW DIAGRAM</p>
          </h1>
        </div>
        <div style={{ paddingLeft:20, paddingRight:20 }}>
        <form onKeyUp={this.handleKeyUp}>
        <p> 
          
            <TextField
              variant="outlined"
              margin="dense"              
              id="search"
              label="Search.."
              name="search"
              // autoComplete="email"
              // autoFocus
              onChange={this.handleChange}
            />
        </p>
        </form>
        </div>
        
        <div style={ {boxShadow: "1px 2px 8px #9E9E9E", display: 'flex',  justifyContent:'center', alignItems:'center', paddingLeft:20, paddingRight:20, paddingBottom: 20, borderRadius:'3px'}}>
        <Flowspace
          theme={this.state.visual.theme}
          variant={this.state.visual.variant}
          background={this.state.visual.darkTheme ? 'black' : this.state.colors.FLOWSPACE_BACKGROUND}
          selected={this.state.settings.selected}
          getDiagramRef={ref => {this.diagramRef = ref}}
          avoidCollisions
          onClick={() => {
            
          
          }}
          style={{
            height: '75vh',
            // width: 'calc(100vw - ' + this.state.visual.drawerWidth * this.state.visual.drawerOpen + ')',
            width: '100vw',
            marginLeft: this.state.visual.drawerWidth * this.state.visual.drawerOpen,
            transition: 'margin-left 0.4s ease-out',
            overflowY: 'hidden',
            overflowX: 'auto'
          }}>

          {
            Object.keys(this.state.flowpoints).map(key => {
              const point = this.state.flowpoints[key];
              // console.log('!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ' + point.base_ref);
              return (
                <Flowpoint
                  key={key}
                  outputs={point.outputs}
                  onClick={(key, e) => {this.handleClick(point.name, e)}}                  
                  startPosition={point.pos}
                  snap={{x:10, y:10}}
                  style={{
                    width:'150',
                    height:'30',
                    minWidth:150,
                    maxHeight:150,
                    backgroundColor: (this.state.selected !== '' && (point.base_ref.indexOf(this.state.selected) !== -1 || key === this.state.selected))? this.state.colors.SELECTED_NODE_BACKGROUND: this.state.colors.DEFAULT_NODE_BACKGROUND,
                    color:(this.state.selected !== '' && (point.base_ref.indexOf(this.state.selected) !== -1 || key === this.state.selected))? this.state.colors.SELECTED_FONT: this.state.colors.DEFAULT_FONT,
                    borderColor: (this.state.selected !== '' && (point.base_ref.indexOf(this.state.selected) !== -1 || key === this.state.selected))? this.state.colors.SELECTED_NODE_BORDER: this.state.colors.DEFAULT_NODE_BORDER,
                    paddingBottom: 10,
                    overflowX:'auto',
                    borderRadius:'4px',
                    borderWidth:'2px'
                    // border: '0.5px solid #2E9AFE',
                    
                  }}
                  onDrag={ (pos) => {
                    var flowpoints = this.state.flowpoints;
                    var settings = this.state.settings;
                    flowpoints[key].pos = pos;
                    
                    // this.setState({flowpoints, settings})
                  }}
                  onHover={ (e) => {
                    var flowpoints = this.state.flowpoints;
                    console.log('$$$$$$' + point.name + " " + Object.keys(flowpoints['BTPBATCH001'].outputs['BTPBATCH002']));
                    this.handleHover(point.name, e)}
                  }
                  >
                  <div style={{height:'auto', paddingTop:10}}>
                  </div>
                    {this.shapeBox(point.output_shape, point.name)}
                  
                  <div style={{height:'auto', width:'150', paddingLeft:10, paddingRight:10, fontSize:'small'}}>
                      {
                        (this.state.visual.showShape) ? this.shapeBox(point.output_shape, point.base_ref) : null
                        // && this.state.environment.library in this.state.environment.autoparams
                      }
                  </div>
                </Flowpoint>
              )
            })
          }

        </Flowspace>
        </div>
        <HelpDialog
          open={this.state.visual.show_help_dialog}
          batchName = {this.state.visual.show_batch_name}
          batchInfoData = {this.state.batchInfoData}
          onClose={() => {
            var state = this. state;
            state.clicked = false;
            state.visual.show_help_dialog = false;
            state.visual.show_batch_name = 'None selected';
            this.setState(state)
          }}/>

      </div>
    )
  }
}

export default withRouter(BatchDiagram);