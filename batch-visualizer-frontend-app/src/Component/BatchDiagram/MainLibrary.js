

export function MainLibrary() {
  return {
    // newly added
    data: null,
    batchInfoData: null,
    batchName: null,
    flowpoints: {},
    selected: null,
    clicked: false,
    visual: {
      darkTheme: false,
      theme: 'black',
      background: 'black',
      variant: 'paper',
      drawerOpen: false,
      drawerWidth: 10,
      showShape: true,
      showName: true,
      snap: true,
      show_load_dialog: false,
      load_dialog_error: false,
      show_save_dialog: false,
      show_help_dialog: false,
      show_batch_name: 'None selected',
      outputColor: '#767676',
      inputColor: '#000000'
    },
    settings: {
      tab: 'Misc',
      modelID: null,
      baseUrl: window.location.href.split('/?')[0],
      count: 0,
      selected: null,
      lastPos: {x:50, y:-40}
    },
    notification: {
      show: false,
      content: {
        msg: 'Hello world',
        color: '#00e676'
      },
      queue: []
    },
    colors:{
      FLOWSPACE_BACKGROUND: '#FFFFFF',
      DEFAULT_NODE_BACKGROUND: '#E0ECF8',
      SELECTED_NODE_BACKGROUND: '#ffbab8',
      DEFAULT_NODE_BORDER: '#08088A',
      SELECTED_NODE_BORDER: '#DF013A',
      DEFAULT_LINK_INPUT: '#00047d',
      DEFAULT_LINK_OUTPUT: '#00047d',
      SELECTED_LINK_INPUT: '#fc0000',
      SELECTED_LINK_OUTPUT: '#fc0000',
      DEFAULT_FONT: '#000000',
      SELECTED_FONT: '#380200',

    }
  }
}

export default MainLibrary;