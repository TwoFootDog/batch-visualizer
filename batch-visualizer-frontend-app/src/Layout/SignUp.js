import React from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
// import FormControlLabel from '@material-ui/core/FormControlLabel';
// import Checkbox from '@material-ui/core/Checkbox';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
// import Box from '@material-ui/core/Box';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from '@material-ui/core/Typography';
// import { makeStyles } from '@material-ui/core/styles';
import { withStyles } from '@material-ui/core/styles';
import { withRouter } from 'react-router-dom';
import Container from '@material-ui/core/Container';
import * as RestApi from '../Common/RestApi';
import NoticeDialog from '../Component/Dialog/NoticeDialog';

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
});


const signUpSuccessMessageHead = '회원가입 성공';
let signUpSuccessMessageBody;

class SignUp extends React.Component {
  state = {
    userId: null,
    userPasswd: null,
    userEmail: null,
    userFirstName: null,
    userLastName: null,
  }

  constructor(props) {
    super(props);
    signUpSuccessMessageBody = '님. 회원 가입을 축하합니다. 아래 버튼을 눌러 로그인하세요. ';
    this.noticeDialogRef = React.createRef();
  }

  handleSubmit = async (e) => {
    e.preventDefault(); // 페이지 리로딩 방지
    const user = this.state;  
    try {
      const result = await RestApi.signUp(user);
      console.log('SignUp Api Call Success : ' + JSON.stringify(result));
      // this.props.history.push('/welcome');  // welcome 페이지로 이동
      this.noticeDialogRef.current.handleClickOpen();
    } catch(err) {
      console.log('SignUp Api Call Fail : ' + err);
    }
  }

  handleChange = (e) => {
    this.setState({
      [e.target.name]: e.target.value
    })
  }
  render() {
    const {classes} = this.props;
    const {userId, userPasswd, userEmail, userFirstName, userLastName} = this.state;

    return (
      <div>
        <Container component="main" maxWidth="xs">
          <CssBaseline />
          <div className={classes.paper}>
            <Avatar className={classes.avatar}>
              <LockOutlinedIcon />
            </Avatar>
            <Typography component="h1" variant="h5">
              Sign up
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
                    onChange={this.handleChange}
                    autoFocus
                    // autoComplete="email"
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
              </Grid>
              <Button
                type="submit"
                fullWidth
                variant="contained"
                // color="primary"
                className={classes.submit}
              >
                Sign Up
              </Button>
              <Grid container justify="flex-end">
                <Grid item xs>
                  <Link href="/SignIn" variant="body2">
                    Already have an account? Sign in
                  </Link>
                </Grid>
              </Grid>
            </form>
          </div>
        </Container>
        <NoticeDialog page={'Signup'} messageHead={signUpSuccessMessageHead} messageBody={userId + signUpSuccessMessageBody} buttonWord1={'로그인하기'} ref={this.noticeDialogRef}/>
      </div>
    );
  }
  
}
export default withStyles(useStyles)(withRouter(SignUp));