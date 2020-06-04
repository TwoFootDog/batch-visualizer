import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import FormHelperText from '@material-ui/core/FormHelperText';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';

const useStyles = makeStyles(theme => ({
  root: {
    display: 'flex',
    flexWrap: 'wrap',
  },
  formControl: {
    margin: theme.spacing(1),
    minWidth: 120,
  },
  selectEmpty: {
    marginTop: theme.spacing(2),
  },
}));

const SelectsComponent = (props) => {
  const classes = useStyles();
  const [value, setValue] = React.useState({
    // authority: 'ROLE_USER'
    roleName: props.roleName
  });
  const [open, setOpen] = React.useState(false);

  const handleChange = event => { 
    setValue({
      [event.target.name]: event.target.value
    });
    console.log('value.roleName>>>>>>>>>>' + value.roleName);
    console.log('event.target.value>>>>>>>>>' + event.target.value);
    props.handleRoleName(event.target.value);
  };

  return (
    <form autoComplete="off">
      <FormControl className={classes.formControl}>
        <InputLabel htmlFor="name">roleName</InputLabel>
        <Select
          style={{width: '200px'}}
          value={value.roleName}
          onChange={handleChange}
          disabled={props.page === 'Settings' ? true : false}
          name="roleName"
          inputProps={{
            id: 'roleName',
          }}
        >
          <MenuItem value="USER">USER</MenuItem>
          <MenuItem value="ADMIN">ADMIN</MenuItem>
        </Select>
      </FormControl>
    </form>
  );
}

export default SelectsComponent; 