import React from 'react';
import * as RESTAPI from '../Common/RestApi';
import MaterialTableComponent from '../Component/MaterialTableComponent';
import { withRouter } from 'react-router-dom';
import { withStyles } from '@material-ui/core/styles';

const useStyles = theme => ({
  table: {
    marginTop: theme.spacing(5),
    padding: theme.spacing(0,10,0,10),
    minWidth: 1000,
    maxWidth: 1200,
  }
})

class UserinfoList extends React.Component {
  state = {
    response: null, // 회원 전체 리스트 호출 응답값
    columns: [
        { title: '회원ID', field: 'userId'},
        { title: '이메일주소', field: 'userEmail'},
        { title: '이름', field: 'userFirstName'},
        { title: '성', field: 'userLastName'},
        { title: '권한', field: 'roleName'},
    ],
    options: {
      headerStyle: {
        backgroundColor: '#019658',
        color: '#FFF'
      },
      pageSize: 10
    }
  }
  constructor(props) {
    super(props);
    this.getAllUserInfo();
    console.log("getAllUserInfo constructor call>>>>>>>>>>>>>>>>>>>>>" );
  }

  getAllUserInfo = async () => {
    const response = await RESTAPI.getAllUserInfo();
    this.setState({
        ...this.state,
        response: response,
    })
    console.log("response>>>>>" + JSON.stringify(response.data));
}

  handleTableRowClick = (userId) => {
    console.log('settings>>>>>>>>>>>>>');
    this.props.history.push({
      pathname: `/userInfo/userId/${userId}`,
      state: { userId: userId }
    });
  }

  render() {
    const {classes} = this.props;

    if (!this.props.isLogin) {
      console.log("not signin");
      this.props.history.push('/signin');
    } else if (!this.props.isAdmin) {
        console.log("not admin");
        this.props.history.push('/');
    }

    
  
    if (this.state.response != null) {
      return(
        <div  className={classes.table}>
          <MaterialTableComponent 
            title={'회원 전체 목록'}
            columns={this.state.columns}
            data={this.state.response.data}
            options={this.state.options}
            handleTableRowClick={this.handleTableRowClick} />
        </div>
      );
    } else {
      return null;
    }
  }
}

export default withStyles(useStyles)(withRouter(UserinfoList));