import {styled} from "@mui/material/styles";


export const WebSocketContainer = styled('div')(({theme}) => ({
    marginTop: theme.spacing(2),
    width: '100%',
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    [theme.breakpoints.up('sm')]: {
        width: '80%',
    },
}));

export const ConnectionStatusContainer = styled('div')(({theme}) => ({
    marginTop: '2vw',
    width: '100%',
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    '& .table-header': {
        '& th': {
            textAlign: 'center'
        }
    }
}));


export const Buttons = styled('div')(({theme}) => ({
    marginTop: theme.spacing(2),
    width: '100%',
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    '& > button': {
        backgroundColor: 'rgba(0, 0, 0, 0.0)',
        borderRadius: '4px',
        padding: theme.spacing(1, 2),
        margin: theme.spacing(0, 1),
        color: 'grey',
        '&:hover': {
            backgroundColor: 'rgba(249, 246, 237, 1)',
            cursor: 'pointer',
        },
        '&:disabled': {
            color: 'rgba(0, 0, 0, 0.1)',
            backgroundColor: 'rgba(199, 0, 0, 0.31)',
        },
    },
}));

export const WebSocketForm = styled('div')(({theme}) => ({
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    marginBottom: theme.spacing(2),
}));

export const Root = styled('div')(({theme}) => ({
    padding: theme.spacing(2, 2, 0),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'flex-start',
    height: '100vh',
    fontFamily: 'Roboto',

}));

export const SendEventContainer = styled('div')(({theme}) => ({
    marginTop: '2vw',
    height: '5vw',
}));

