import React from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import SettingsIcon from '@material-ui/icons/Settings';
import Typography from '@material-ui/core/Typography';
import { withStyles } from '@material-ui/core/styles';
import { withRouter } from 'react-router-dom';
import Container from '@material-ui/core/Container';
import SelectsComponent from '../SelectsComponent';
import NoticeDialog from '../Dialog/NoticeDialog';
import * as RESTAPI from '../../Common/RestApi';


const useStyles = theme => ({
    '@global': {
      body: {
        backgroundColor: '#e6e6e6',
      },
    },
    paper: {
      backgroundColor: 'white',
      marginTop: theme.spacing(20),
      padding: theme.spacing(1),
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
    },
    avatar: {
      margin: theme.spacing(1),
      backgroundColor: theme.palette.secondary.main,
    },
    form: {
      width: '100%', // Fix IE 11 issue.
      marginTop: theme.spacing(3),
    },
    submit: {
      margin: theme.spacing(3, 0, 2),
      backgroundColor: 'darkcyan',
      color: 'white',
    },
    deleteAccount: {
      margin: theme.spacing(3, 0, 2),
      backgroundColor: 'red',
      color: 'white',
    },
  });

const profileUpdateFailMessage = 'profile 변경에 실패했습니다.';
const profileUpdateSuccessMessageHead = 'profile 변경 완료';
const profileUpdateSuccessMessageBody = 'profile 변경이 완료되었습니다.';
const deleteAccountMessageHead = '회원탈퇴';
const deleteAccountMessageBody = '정말로 탈퇴하시겠습니까?';

class ProfileComponent extends React.Component {
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
        this.updateProfileDialogRef = React.createRef();  // updateProfile버튼 클릭시 popup되는 dialog창 참조값
        this.deleteAccountDialogRef = React.createRef();  // deleteAccount 클릭시 popup되는 dialog창 참조값
    }

    // 회원정보를 가져오는 함수
    getUserInfo = async () => {
        let userInfo;
        try {
          userInfo = await RESTAPI.getUserInfoByToken();
          console.log('settings getUserInfo >>>>>>', JSON.stringify(userInfo));
          console.log('userInfo.data.authorities[0].authority >>>>>>', userInfo.data.authorities[0].authority);
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

    // updateProfile 클릭 시 profile을 update해주는 함수
    handleSubmit = async (e) => {
      e.preventDefault(); // 페이징 리로딩 방지
      let result = null;
      try {
        result = await RESTAPI.setUserInfo(this.state);
        console.log('setUserInfo API Call Success : ' + result);
      } catch(err) {
        console.log('setUserInfo API Call Fail : ' + err);
      }

      if (result != null) { // profile 변경에 성공했을 경우
        this.updateProfileDialogRef.current.handleClickOpen();  // 변경이 완료되었습니다 dialog popup
      } else {              // profile변경에 실패했을 경우
        this.setState({
          ...this.state,
          message: profileUpdateFailMessage // 에러메시지 setting
        })
      }
    }

    // deleteAccount 클릭 시 회원탈퇴 처리하는 함수
    handleDeleteAccount = () => {
      console.log('handleDeleteAccount');
      this.deleteAccountDialogRef.current.handleClickOpen();
    }

    // text edit 값 변경 시 호출되는 함수
    handleChange = (e) => {
      this.setState({
        [e.target.name]: e.target.value
      })
    }

    handleAuthority = (authority) => {
      this.setState({
        ...this.state,
        authority: authority
      })
    }
    
    render() {
      const {classes} = this.props;
      const {userId, userPasswd, userEmail, userFirstName, userLastName, authority, message} = this.state;

      if (userId != null) { // getUserInfo 호출 후 리턴이 되면 화면을 띄워줌
        return(
          <div>
            <Container component="main" maxWidth="xs">
              <CssBaseline />
              <div className={classes.paper}>
                <Avatar className={classes.avatar}>
                  <SettingsIcon />
                </Avatar>
                <Typography component="h1" variant="h5">
                  Profile
                </Typography>
                <form className={classes.form} noValidate onSubmit={this.handleSubmit}>
                  <Grid container spacing={2}>
                      <Grid item xs={12}>
                          <TextField
                          variant="filled"
                          required
                          fullWidth
                          id="userId"
                          label="User ID"
                          name="userId"
                          value={userId}
                          InputProps={{
                            readOnly: true,
                          }}
                          onChange={this.handleChange}
                          />
                      </Grid>
                    <Grid item xs={12}>
                      <TextField
                        variant="filled"
                        required
                        fullWidth
                        name="userPasswd"
                        label="Password"
                        type="password"
                        id="userPasswd"
                        autoComplete="current-password"
                        autoFocus
                        value={userPasswd}
                        onChange={this.handleChange}
                      />
                    </Grid>
                    <Grid item xs={12}>
                      <TextField
                        variant="filled"
                        required
                        fullWidth
                        id="userEmail"
                        label="Email Address"
                        name="userEmail"
                        autoComplete="email"
                        value={userEmail}
                        onChange={this.handleChange}
                      />
                    </Grid>
                    <Grid item xs={12} sm={6}>
                      <TextField
                        autoComplete="fname"
                        name="userFirstName"
                        variant="filled"
                        required
                        fullWidth
                        id="userFirstName"
                        label="First Name"
                        value={userFirstName}
                        onChange={this.handleChange}
                      />
                    </Grid>
                    <Grid item xs={12} sm={6}>
                      <TextField
                        variant="filled"
                        required
                        fullWidth
                        id="userLastName"
                        label="Last Name"
                        name="userLastName"
                        autoComplete="lname"
                        value={userLastName}
                        onChange={this.handleChange}
                      />
                    </Grid>
                    <Grid item xs={12} sm={6}>
                      <SelectsComponent authority={authority} handleAuthority={this.handleAuthority} />
                    </Grid>
                  </Grid>
                  <div style={{color: 'red', textAlign: 'center', marginTop: '20px'}}>{message}</div>
                  <Button
                    type="submit"
                    fullWidth
                    variant="contained"
                    className={classes.submit}
                  >
                    Update Profile
                  </Button>
                </form>
                <Button
                    type="submit"
                    fullWidth
                    variant="contained"
                    className={classes.deleteAccount}
                    onClick={this.handleDeleteAccount}
                  >
                    DeleteAccount
                  </Button>
              </div>
            </Container>
            <NoticeDialog 
              messageHead={profileUpdateSuccessMessageHead} 
              messageBody={profileUpdateSuccessMessageBody} 
              buttonWord1={'홈으로'} 
              ref={this.updateProfileDialogRef}/>
            <NoticeDialog 
              messageHead={deleteAccountMessageHead} 
              messageBody={deleteAccountMessageBody} 
              buttonWord1={'탈퇴하기'} 
              buttonWord2={'취소'} 
              user={this.state} 
              ref={this.deleteAccountDialogRef}
              handleSignOut={this.props.handleSignOut}
              />
          </div>
        )
      } else {  // getUserInfo 호출 후 리턴이 되지 않았을 때
          return null;  
      }
    }
}

export default withStyles(useStyles)(withRouter(ProfileComponent));
