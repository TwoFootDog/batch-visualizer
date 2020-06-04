import React from 'react';
import { Link } from 'react-router-dom';
import { makeStyles } from '@material-ui/core/styles';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';


const useStyles = makeStyles(theme => ({
    MenuItem: {
      color: 'grey',
      fontWeight: theme.typography.fontWeightBold
    }
}));


const TopAdminPageMenuComponent = React.forwardRef((props, ref) => {
    const classes = useStyles();
    const [anchorElTab, setAnchorElTab] = React.useState(null);
    
    React.useImperativeHandle(ref, () => ({
        handleTabClick(event) {
            event.stopPropagation();
            setAnchorElTab(event.currentTarget);
            props.handleOpenAdminTabMenu();
        }
    }));

    function handleTabMenuClose() {
        setAnchorElTab(null);
        props.handleOpenAdminTabMenu();
    }

    return (
        <div>
            <Menu
              id="top-nav-bar-menu"
              anchorEl={anchorElTab}
              keepMounted
              open={Boolean(anchorElTab)}
              onClose={handleTabMenuClose}
              // elevation={0}
              getContentAnchorEl={null}
              anchorOrigin={{
                vertical: 'bottom',
                horizontal: 'center',
              }}
              transformOrigin={{
                vertical: 'top',
                horizontal: 'center',
              }}
              >
                  <MenuItem className={classes.MenuItem} onClick={handleTabMenuClose} component={Link} to="/userInfoList">회원목록조회</MenuItem>
                  <MenuItem className={classes.MenuItem} component="a" href="http://66.42.43.41:30000/" target="_blank" onClick={handleTabMenuClose}>Jenkins 바로가기</MenuItem>
                  <MenuItem className={classes.MenuItem} component="a" href="https://about.gitlab.com/" target="_blank" onClick={handleTabMenuClose}>gitlab 바로가기</MenuItem>
                  <MenuItem className={classes.MenuItem} component="a" href="https://66.42.43.41:30834/" target="_blank" onClick={handleTabMenuClose}>k8s Dashboard 바로가기 </MenuItem>
                  <MenuItem className={classes.MenuItem} component="a" href="http://66.42.43.41:30505/" target="_blank" onClick={handleTabMenuClose}>WeaveScope 바로가기 </MenuItem>
                  <MenuItem className={classes.MenuItem} component="a" href="http://66.42.43.41:32349/sonar/" target="_blank" onClick={handleTabMenuClose}>Sonarqube 바로가기 </MenuItem>
                  <MenuItem className={classes.MenuItem} component="a" href="https://hub.docker.com/" target="_blank" onClick={handleTabMenuClose}>docker hub 바로가기</MenuItem>
          </Menu>
        </div>
    )
});

export default TopAdminPageMenuComponent;