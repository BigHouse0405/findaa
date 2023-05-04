import React, { useEffect } from 'react';
import { Button, styled, Typography } from '@mui/material';
import { css } from '@emotion/react';
import WebSocketList from './websocket/WebSocketList';

const PREFIX = 'App';

const classes = {
    containerStyles: `${PREFIX}-containerStyles`,
};

const Root = styled('div')(({ theme }) => ({
    [`& .${classes.containerStyles}`]: {
        padding: theme.spacing(6, 2, 0),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'flex-start',
        height: '100vh',
    },
}));

const WebSocketContainer = styled('div')(({ theme }) => ({
    marginTop: theme.spacing(2),
    width: '100%',
    [theme.breakpoints.up('sm')]: {
        width: '80%',
    },
}));

function App() {
    useEffect(() => {
        console.log('Mounted App component');
    }, []);

    return (
        <Root>
            <div className={classes.containerStyles}>
                <Typography variant="h5" component="h1" sx={{
                    position: 'fixed',
                    top: '2px',
                }}>
                    Websocket event failure reader
                </Typography>
                <Button variant="contained" color="primary" onClick={() => console.log('button')} sx={{
                    marginTop: '3vw',
                }}>
                    OAuth2 https login
                </Button>
                <WebSocketContainer>
                    <WebSocketList />
                </WebSocketContainer>

            </div>
        </Root>
    );
}

export default App;
