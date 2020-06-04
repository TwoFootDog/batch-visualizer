import React from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
// import Box from '@material-ui/core/Box';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from '@material-ui/core/Typography';
import { withStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import { withRouter } from 'react-router-dom';
import * as RestApi from '../Common/RestApi';
import NoticeDialog from '../Component/Dialog/NoticeDialog';
import { cyan } from '@material-ui/core/colors';


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
    marginTop: theme.spacing(1),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
    backgroundColor: 'darkcyan',
    color: 'white',
  },
});

const loginFailString = 'your username/password is invalid';
const signInSuccessMessageHead = '로그인 성공';
let signInSuccessMessageBody;

class SignIn extends React.Component {
  state = {
    userId: null,
    userPasswd: null,
    token: null,
    message: null,
  }

  constructor(props) {
    super(props);
    signInSuccessMessageBody = '님. 접속을 환영합니다.';
    this.noticeDialogRef = React.createRef();

    if (this.props.isLogin) {
      console.log('already sign in');
      this.props.history.push(`/`);
    }
  }

  handleChange = (e) => {
    this.setState({
        [e.target.name]: e.target.value
    })
  }

  handleSubmit = async (e) => {
    e.preventDefault(); // 페이징 리로딩 방지
    const user = this.state;
    let result = null;
    let isAdmin = false;

    try {
      result = await RestApi.signIn(user);
      console.log('SignIn Api Call Success : ' + JSON.stringify(result));
      console.log('SignIn Api Call Success : ' + result);
    } catch(err) {
      console.log('SignIn API Call Fail : ' + err);
    }

    if (result != null) {
      if (result.data.authorities[0].authority === 'ROLE_ADMIN') {
        console.log('result isAdmin>>>>' + isAdmin);
        isAdmin = true;
      }  

      this.props.handleUserInfo(result.data.userId, result.data.token, isAdmin); // userId와 token정보를 parent component로 전달
      
      window.localStorage.clear();  // local 정보를 삭제한다
      window.localStorage.setItem('token', result.data.token); // local의 token정보 갱신
      
      signInSuccessMessageBody = result.data.userId + '님 접속을 환영합니다.';
      this.noticeDialogRef.current.handleClickOpen();
    } else {
      console.log('fire');
      this.setState({
        message: loginFailString,
      })
      this.props.history.push(`/signin`); // 로그인에 실패했을 경우 /signin으로 이동
    }
  }

  componentWillUpdate(nextProps, nextState) {
    // shouldComponentUpdate가 true를 반환했을 때만 호출됨
    console.log('componentWillUpdate');
}

  render() {
    
    const {classes} = this.props;
    const {userId, userPasswd, message} = this.state;
    console.log("signin render")

    return (
      <div>
        <Container component="main" maxWidth="xs">
          <CssBaseline />
          <div className={classes.paper}>
            <Avatar className={classes.avatar}>
              <LockOutlinedIcon />
            </Avatar>
            <Typography component="h1" variant="h5">
              Sign in
            </Typography>
            <form className={classes.form} noValidate onSubmit={this.handleSubmit}>
              <TextField
                variant="filled"
                margin="normal"
                required
                fullWidth
                id="userId"
                label="User ID"
                name="userId"
                // autoComplete="email"
                autoFocus
                value={userId}
                onChange={this.handleChange}
              />
              <TextField
                variant="filled"
                margin="normal"
                required
                fullWidth
                name="userPasswd"
                label="Password"
                type="password"
                id="userPasswd"
                autoComplete="current-password"
                value={userPasswd}
                onChange={this.handleChange}
              />
              <FormControlLabel
                control={<Checkbox value="remember" color="primary" />}
                label="Remember me"
              />
              <div style={{color: 'red'}}>{message}</div>
              <Button
                type="submit"
                fullWidth
                variant="contained"
                className={classes.submit}              
              >
                Sign In
              </Button>
              <Grid container>
                <Grid item xs>
                  <Link href="/SignUp" variant="body2">
                    {"Don't have an account? Sign Up"}
                  </Link>
                </Grid>
              </Grid>
            </form>
          </div>
        </Container>
        <NoticeDialog page={'SignIn'} messageHead={signInSuccessMessageHead} messageBody={userId + signInSuccessMessageBody} buttonWord1={'홈으로'} ref={this.noticeDialogRef}/>
      </div>
    );
  }
}

export default withStyles(useStyles)(withRouter(SignIn));