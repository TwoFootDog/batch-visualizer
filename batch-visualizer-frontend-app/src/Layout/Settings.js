import React from 'react';
import ProfileComponent from '../Component/ProfileComponent';
import * as RESTAPI from '../Common/RestApi';
import { withRouter } from 'react-router-dom';




class Settings extends React.Component {

  state = {
    userId: null,
    userPasswd: null,
    userEmail: null,
    userFirstName: null,
    userLastName: null,
    roleName: null,
    message: null 
  }

constructor(props) {
    super(props);
    console.log('settings constructor call');
    this.getUserInfo();
  }

//회원정보를 가져오는 함수
  getUserInfo = async () => {
      let userInfo;
      try {
        if (this.props.userId == null) {
          userInfo = await RESTAPI.getUserInfoByToken();
        } else {
          userInfo = await RESTAPI.getUserInfoByUserId(this.props.userId);
        }
        console.log('settings getUserInfo >>>>>>', JSON.stringify(userInfo));
        console.log('userInfo.data.authorities[0].roleName >>>>>>', userInfo.data.roleName);
        this.setState({
            ...this.state,
            userId: userInfo.data.userId,
            userEmail: userInfo.data.userEmail,
            userFirstName: userInfo.data.userFirstName,
            userLastName: userInfo.data.userLastName,
            roleName: userInfo.data.roleName
        })
      } catch (err) {
        console.log("not signin");
        this.props.history.push('/signin');
      }
  }

  render() {
    const {userId} = this.state;

    if (!this.props.isLogin) {
      console.log("not signin");
      this.props.history.push('/signin');
    } 

    if (userId != null) {
      return (
        <div>
            <ProfileComponent 
              isLogin={this.props.isLogin}
              userInfo={this.state} 
              page={'Settings'}
              tokenValidChk={this.props.tokenValidChk}
              handleSignOut={this.props.handleSignOut} />
        </div>
      )
    } else {
      return null;
    }
  }
}

export default withRouter(Settings);