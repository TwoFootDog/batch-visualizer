import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { Link } from 'react-router-dom';
import { ListItemIcon, Collapse, ListSubheader } from '@material-ui/core';
import Drawer from '@material-ui/core/Drawer';
import List from '@material-ui/core/List';
import Divider from '@material-ui/core/Divider';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import Button from '@material-ui/core/Button';

import IconButton from '@material-ui/core/IconButton';
import HomeIcon from '@material-ui/icons/Home';
import MenuIcon from '@material-ui/icons/Menu';
import Dvr from '@material-ui/icons/Dvr';
import PersonPinIcon from '@material-ui/icons/PersonPin';
import People from '@material-ui/icons/People';
import Filter from '@material-ui/icons/Filter';
import StarBorder from '@material-ui/icons/StarBorder';
import ExpandMore from '@material-ui/icons/ExpandMore';
import ExpandLess from '@material-ui/icons/ExpandLess';
import ThumbUpAlt from '@material-ui/icons/ThumbUpAlt';
import HomeIconImg from '../../image/HomeIcon.svg'


/* component style */
const useStyles = makeStyles(theme => ({
  list: { // list width
    width: 300
  },
  nested: { // sub list left padding(여백)
    paddingLeft: theme.spacing(4)
  },
  menuIcon: {
    paddingLeft: theme.spacing(2)
  },
  menuTextButton: {
    paddingLeft: theme.spacing(2.5)
  }
}));

/* function component */
const LeftTpSchedulerDropMenuComponent = React.forwardRef((props, ref) => {
  const classes = useStyles();  // use component style

  const [state, setState] = React.useState({  // state value & set function
    left: false,
  });

  const [openCollapse, setOpenCollapse] = React.useState(false);  // openCollapse & set function
  const [openAdminCollapse, setOpenAdminCollapse] = React.useState(false);  // openCollapse & set function

  // called by parent component(TopTabComponent)
  React.useImperativeHandle(ref, () => ({
     toggleDrawer(side, open) {
          setState({...state, [side]: open});
      }
  }));

  // left Drawer menu event function(side는 left만 가능. open이 true이면 drawer menu open, false이면 drawer menu close)
  const toggleDrawer = (side, open, signOut) => event => {
    if (event.type === 'keydown' && (event.key === 'Tab' || event.key === 'Shift')) {
        return;
      }
    setState({...state, [side]: open});
    if (signOut == true) {
      props.handleSignOut();
    }
  }

  // function called when a menu with a sub-menu(TP배치스케쥴러) is clicked
  const handleListItemClick = () => {
    setOpenCollapse(!openCollapse);
  }

  // function called when a menu with a sub-menu(Admin Page) is clicked
  const handleAdminListItemClick = () => {
    setOpenAdminCollapse(!openAdminCollapse);
  }

  // 프로젝트에서 사용하는 url 리턴해주는 함수
  const returnUrl = (text) => {
    if (text === 'Jenkins 바로가기')
      return 'http://66.42.43.41:30000/'
    else if(text === 'gitlab 바로가기')
      return 'https://about.gitlab.com/'
    else if(text === 'k8s Dashboard 바로가기')
      return 'https://66.42.43.41:30834/'
    else if(text === 'WeaveScope 바로가기')
      return 'http://66.42.43.41:30505/'
    else if(text === 'Sonarqube 바로가기')
      return 'http://66.42.43.41:32349/sonar/'
    else if(text === 'docker hub 바로가기')
      return 'https://hub.docker.com/'
  }
        
    return (
        <Drawer open={state.left} onClose={toggleDrawer(props.side, false, false)}>
            <div className={classes.list} role="presentation">
              <List>
                <IconButton className={classes.menuIcon} color="inherit" aria-label="menu" onClick={toggleDrawer(props.side, false, false)}>
                  <MenuIcon />
                </IconButton>
                  <Button size="small" className={classes.menuTextButton} component={Link} to="/" onClick={toggleDrawer(props.side, false, false)}>
                    <img src={HomeIconImg} width="25" height="20" style={{marginLeft: '-20px'}}/>Batch Visualizer
                  </Button>
              </List>
              <Divider />
              <List aria-labelledby="nested-list-subheader" 
                subheader={
                  <ListSubheader component="div" id="nested-list-subheader">Main Menu</ListSubheader>
              }>
                <ListItem button key={'Home'} component={Link} to="/" onClick={toggleDrawer(props.side, false, false)}>
                  <ListItemIcon><HomeIcon/></ListItemIcon>Home
                </ListItem>
                <ListItem button key={'TP배치스케쥴러'} onClick={handleListItemClick}>
                  <ListItemIcon><Dvr/></ListItemIcon>TP배치스케쥴러{openCollapse?<ExpandLess/>:<ExpandMore/>}
                </ListItem>
                <Collapse in={openCollapse} timeout="auto" unmountOnExit>
                  <List component="div" disablePadding>
                    <ListItem button className={classes.nested} component={Link} to="/tpBatchList" onClick={toggleDrawer(props.side, false, false)}>
                      <ListItemIcon><StarBorder/></ListItemIcon>
                      <ListItemText primary="승인배치리스트"/>
                    </ListItem>
                    <ListItem button className={classes.nested} component={Link} to="/tpBatchExecList" onClick={toggleDrawer(props.side, false, false)}>
                      <ListItemIcon><StarBorder/></ListItemIcon>
                      <ListItemText primary="승인배치현황"/>
                    </ListItem>
                    <ListItem button className={classes.nested} component={Link} to="/etc" onClick={toggleDrawer(props.side, false, false)}>
                      <ListItemIcon><StarBorder/></ListItemIcon>
                      <ListItemText primary="Table3"/>
                    </ListItem>
                  </List>
                </Collapse>
                <ListItem button key={'정산스케쥴러'} component={Link} to="/batchDiagram" onClick={toggleDrawer(props.side, false, false)}>
                  <ListItemIcon><Filter/></ListItemIcon>정산스케쥴러
                </ListItem>
              </List>
              <Divider />
              {props.isAdmin && 
                <List aria-labelledby="nested-list-subheader"
                  subheader={
                    <ListSubheader component="div" id="nested-list-subheader">Admin Menu</ListSubheader>
                  }>
                  <ListItem button key={'Admin Page'} onClick={handleAdminListItemClick}>
                    <ListItemIcon><ThumbUpAlt/></ListItemIcon>Admin Page{openCollapse?<ExpandLess/>:<ExpandMore/>}
                  </ListItem>
                  <Collapse in={openAdminCollapse} timeout="auto" unmountOnExit>
                    <List component="div" disablePadding>
                      <ListItem button className={classes.nested} component={Link} to="/userInfoList" onClick={toggleDrawer(props.side, false, false)}>
                        <ListItemIcon><StarBorder/></ListItemIcon>
                        <ListItemText primary="회원목록조회"/>
                      </ListItem>
                      {['Jenkins 바로가기', 'gitlab 바로가기', 'k8s Dashboard 바로가기', 'WeaveScope 바로가기', 'Sonarqube 바로가기', 'docker hub 바로가기'].map((text, index) => (
                            <ListItem button key={text} className={classes.nested}
                              component="a" 
                              href={returnUrl(text)} target="_blank" onClick={toggleDrawer(props.side, false, false)}>
                            <ListItemIcon><StarBorder/></ListItemIcon>
                            <ListItemText primary={text}/>
                          </ListItem>
                      ))}
                    </List>
                  </Collapse>
                  </List>}
              <Divider />
              <List aria-labelledby="nested-list-subheader" 
                subheader={
                  <ListSubheader component="div" id="nested-list-subheader">회원메뉴</ListSubheader>
              }>
                {!props.isLogin && 
                  ['Sign in', 'Sign up'].map((text, index) => (
                    <ListItem button key={text} component={Link} to={text === 'Sign in' ? '/signin' : '/signup'} onClick={toggleDrawer(props.side, false, false)}>
                      <ListItemIcon>{text === 'Sign in' ? <PersonPinIcon/> : <People/>}</ListItemIcon>
                      <ListItemText primary={text}/>
                    </ListItem>
                ))}
                {props.isLogin && 
                  <ListItem button key={1} component={Link} to='/' onClick={toggleDrawer(props.side, false, true)}>
                    <ListItemIcon><PersonPinIcon/> </ListItemIcon>
                    <ListItemText primary={'Sign Out'}/>
                  </ListItem>
                }
              </List>
            </div>
        </Drawer>
      )
          
});

export default LeftTpSchedulerDropMenuComponent;