import React from 'react';
import {
    Button,
} from "@mui/material";
import {Buttons} from "../../styles/styles";


type WebSocketButtonsProps = {
    onConnect: () => void;
    onDisconnect: () => void;
    onWrongLoginEvent: () => void;
    connected: boolean;
}

const WebSocketButtons = ({onConnect, onDisconnect, onWrongLoginEvent, connected}: WebSocketButtonsProps) => {
    return (
            <Buttons>
                <Button
                    id="connect"
                    variant="contained"
                    color="error"
                    type="button"
                    onClick={onConnect}
                    disabled={connected}
                >
                    Connect
                </Button>
                <Button
                    id="disconnect"
                    variant="contained"
                    color="error"
                    type="button"
                    onClick={onDisconnect}
                    disabled={!connected}
                >
                    Disconnect
                </Button>
                <Button
                    id="disconnect"
                    variant="contained"
                    color="error"
                    type="button"
                    onClick={onWrongLoginEvent}
                    disabled={!connected}
                >
                    Wrong login event
                </Button>
            </Buttons>
    );
}


export default WebSocketButtons;
