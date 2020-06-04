import React from 'react';
import { withRouter } from 'react-router-dom';
import * as RESTAPI from '../../Common/RestApi';

import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';

/* NoticeDialog가 withRouter로 쌓여있기 때문에 parent component에서 ref로 NotecieDialog의 method를 호출할 수 없다.
하지만 withRouterAndRef를 정의해서 NoticeDialog를 withRouterAndRef로 감싸주면 parent component에서 ref로 method 호출이 가능하다 */
const withRouterAndRef = (WrappedComponent) => {
    class InnerComponentWithRef extends React.Component {    
          render() {
              const { forwardRef, ...rest } = this.props;
              return <WrappedComponent {...rest} ref={forwardRef} />;
          }
      }
      const ComponentWithRouter = withRouter(InnerComponentWithRef, { withRef: true });
      return React.forwardRef((props, ref) => {
          return <ComponentWithRouter {...props} forwardRef={ref} />;
        });
    }
    
class NoticeDialog extends React.Component {
    state = {
        open: false
    }

    constructor(props) {
        super(props);
    }

    handleClickOpen = () => {
        console.log('handleClickOpen');
        this.setState({open: true})
      };
    
    handleButtonWord1 = async () => {
        console.log('handleButtonWord1');
        this.setState({open: false})

        if (this.props.page === 'SignIn') {
            this.props.history.push('/');
        } else if (this.props.page === 'Settings') {
            if (this.props.buttonWord1 === '홈으로') {
                this.props.tokenValidChk();
                this.props.history.push('/');
            } else if (this.props.buttonWord1 === '탈퇴하기') {
                let result;
                try {
                    result = await RESTAPI.deleteAccount(this.props.user);
                    console.log('deleteAccount Api Call Success : ' + JSON.stringify(result));
                  } catch(err) {
                    console.log('deleteAccount API Call Fail : ' + err);
                  }
                if (result.data == true) {  // 탈퇴하기 성공했을 때
                    this.props.handleSignOut();
                    this.props.history.push('/');
                }
            }
        } else if (this.props.page === 'UserInfo') {
            if (this.props.buttonWord1 === '확인') {
                this.props.tokenValidChk();
                this.props.history.push('/userInfoList');
            } else if (this.props.buttonWord1 === '삭제하기') {
                let result;
                try {
                    result = await RESTAPI.deleteAccount(this.props.user);
                    console.log('deleteAccount Api Call Success : ' + JSON.stringify(result));
                  } catch(err) {
                    console.log('deleteAccount API Call Fail : ' + err);
                  }
                if (result.data == true) {  // 삭제하기 성공했을 때
                    this.props.tokenValidChk();
                    this.props.history.push('/userInfoList');
                }
            }
        } else if (this.props.page === 'Signup') {
            this.props.history.push('/signin');
        }       
      };
    
    handleButtonWord2 = () => {
        console.log('handleButtonWord2');
        this.setState({open: false})
    }

    render() {
        return(
            <div>
                <Dialog
                    open={this.state.open}
                    onClose={this.handleClose}
                    aria-labelledby="alert-dialog-title"
                    aria-describedby="alert-dialog-description">
                    <DialogTitle id="alert-dialog-title">{this.props.messageHead}</DialogTitle>
                    <DialogContent>
                    <DialogContentText id="alert-dialog-description">
                        {this.props.messageBody}
                    </DialogContentText>
                    </DialogContent>
                    <DialogActions>
                    {this.props.buttonWord2 && <Button onClick={this.handleButtonWord2} color="primary" autoFocus>
                        {this.props.buttonWord2}
                    </Button>}
                    {this.props.buttonWord1 && <Button onClick={this.handleButtonWord1} color="primary" autoFocus>
                        {this.props.buttonWord1}
                    </Button>}
                    </DialogActions>
                </Dialog>
            </div>
        )
    }
    
}

export default withRouterAndRef(NoticeDialog);    