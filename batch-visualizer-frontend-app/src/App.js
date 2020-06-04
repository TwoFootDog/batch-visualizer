import React from 'react';
import { Route } from 'react-router-dom';
import TopTabComponent from './Component/TopTabComponent';
import Home from './Layout/Home';
import TpBatchList from './Layout/TpBatchList';
import TpBatchExecList from './Layout/TpBatchExecList';
import Etc from './Layout/Etc';
import BatchDiagram from './Layout/BatchDiagram';
import SignIn from './Layout/SignIn';
import SignUp from './Layout/SignUp';
import Settings from './Layout/Settings';
import UserInfo from './Layout/UserInfo';
import UserInfoList from './Layout/UserInfoList';
// import {Link} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.css'
import './App.css';
import { withStyles } from '@material-ui/core/styles';
import * as RestApi from './Common/RestApi';
import Welcome from './Layout/Welcome';
import BatchDetail from './Layout/BatchDetail';

const useStyles = theme => ({
  TopTab: {
    height: 75
  }
})
class App extends React.Component {
  state = {
    userId: null,
    userEmail: null,
    userFirstName: null,
    userLastName: null,
    token: null,
    isLogin: false,
    isTokenChk: false,
    isAdmin: false
  }
  constructor(props) {
    super(props);
    console.log('App constructor >>>>');
    this.tokenValidChk();
  }

  tokenValidChk = async () => {
    console.log('tokenvalidchk!!!');
    try {
      const userInfo = await RestApi.getUserInfoByToken();
      this.setState({
        ...this.state,
        userId: userInfo.data.userId,
        userEmail: userInfo.data.userEmail, 
        userFirstName: userInfo.data.userFirstName,
        userLastName: userInfo.data.userLastName,
        isLogin: true,      // 로그인 여부
        isTokenChk: true,   // token 체크 완료 여부
        isAdmin: userInfo.data.roleName === 'ADMIN' ? true : false //  admin 여부
      })
      console.log('this.state.isAdmin>>>>' + this.state.isAdmin);
    } catch (err) {
      console.log('tokenValidChk err >>>>>>>>>>>' + JSON.stringify(err));
      this.setState({
        ...this.state,
        isLogin: false,
        isTokenChk: true,
        isAdmin: false
      })
    }
  }

  handleUserInfo = (userId, token, isAdmin) => {
    console.log('handleUserInfo isAdmin>>>>>>' + isAdmin);
    this.setState({
      userId: userId,
      token: token,
      isLogin: true,
      isAdmin: isAdmin
    })
  }

  handleSignOut = () => {
    console.log('signout');
    this.setState({
      ...this.state,
      isLogin: false,
      isAdmin: false
    })
    window.localStorage.removeItem('token') // 로그아웃 시 local에 있는 token 정보를 삭제한다
  }

  componentWillMount = () => {
    // 컴포넌트가 화면에 나가기 직전에 호출됨
    console.log('app componentWillMount');
  }
  componentDidMount = () => {
    // 컴포넌트가 화면에 나타나게 됐을 때 호출됨
    console.log('app componentDidMount');
  }
  componentWillReceiveProps = () => {
    // props가 변경될 때 호출
    console.log('app componentWillReceiveProps');
  }

  // shouldComponentUpdate  = () => {
  //   // props가 변경될 때 호출
  //   console.log('app shouldComponentUpdate ');
  // }

  componentWillUpdate  = () => {
    // 컴포넌트가 업데이트 되기 전 호출
    console.log('app componentWillUpdate ');
  }

  componentDidUpdate   = () => {
    // 컴포넌트 리랜더링 후 호출
    console.log('app componentDidUpdate  ');
  }



  render() {
    const { classes } = this.props;
    const { isLogin, isAdmin } = this.state;
    
    console.log("start>>>>>>>>>>>>>>>>>>>>>>>>");
    if(this.state.isTokenChk == true) { // 토큰 체크가 완료되었으면 화면을 띄움
      return (
        <span>
          <div className={classes.TopTab}>
            <TopTabComponent isLogin={isLogin} isAdmin={isAdmin} handleSignOut={this.handleSignOut}/>
          </div>
          <div>
            <Route exact path="/" render = {() => <Home/>}/>
            <Route path="/tpBatchList" render = {() => <TpBatchList isLogin={isLogin}/>}/>
            <Route path="/tpBatchExecList" render = {() => <TpBatchExecList isLogin={isLogin}/>}/>
            {/* <Route path="/batchDetail/masterBatchId/:masterBatchId" render = {(props) => <BatchDetail isLogin={isLogin} masterBatchId={props.location.state.masterBatchId}/>}/> */}
            <Route path="/batchDetail/masterBatchId/:masterBatchId" render = {(props) => <BatchDetail isLogin={isLogin} masterBatchId={props.match.params.masterBatchId}/>}/>
            <Route path="/etc" render = {() => <Etc isLogin={isLogin}/>}/>
            <Route path="/batchDiagram" render = {() => <BatchDiagram isLogin={isLogin}/>}/>
            <Route path="/signin" render = {() => <SignIn isLogin={isLogin} handleUserInfo={this.handleUserInfo}/>}/>
            <Route path="/signup" render = {() => <SignUp/>}/>
            <Route path="/settings" render = {() => <Settings isLogin={isLogin} tokenValidChk={this.tokenValidChk} handleSignOut={this.handleSignOut}/>}/>
            <Route path="/userinfo/userId/:userId" render = {(props) => <UserInfo isLogin={isLogin} isAdmin={isAdmin} tokenValidChk={this.tokenValidChk} userId={props.match.params.userId} />}/>
            {/* <Route path="/userinfo/userId/:userId" render = {(props) => <UserInfo isLogin={isLogin} isAdmin={isAdmin} tokenValidChk={this.tokenValidChk} userId={props.location.state.userId} />}/>/ */}
            <Route path="/userInfoList" render= {() => <UserInfoList isLogin={isLogin} isAdmin={isAdmin} />}/>
            <Route path="/welcome" render= {() => <Welcome/>}/>
          </div>
        </span>
      )
    } else {  // 토큰 체크가 완료되기 전이면 화면을 띄우지 않음
      return null;
    }
  }
}

export default withStyles(useStyles)(App);
