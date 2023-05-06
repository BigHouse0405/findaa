import React, {useState} from 'react';
import AuthenticationFailureEvent from "../AuthenticationFailureEvent";
import {Button, Input, InputLabel} from "@mui/material";
import {Buttons, WebSocketForm} from "../../styles/styles";

interface SendMessageProps {
    stompClient: any;
    connected: boolean;
}

const SendEvent = ({stompClient, connected}: SendMessageProps) => {
    const [content, setContent] = useState<string>('');

    const onSend = () => {
        if (stompClient !== null) {
            const authenticationFailureEvent = {
                content,
                timestamp: new Date().toISOString()
            } as AuthenticationFailureEvent;

            stompClient.send('/app/newError', {}, JSON.stringify(authenticationFailureEvent));
            setContent('');
        }
    };

    return (
        <WebSocketForm>
            <InputLabel htmlFor="name">Message</InputLabel>
            <Input type="text" id="name" placeholder="" value={content}
                   onChange={(e) => setContent(e.target.value)}/>
            <Buttons>
                <Button id="send" type="button" onClick={onSend} disabled={!connected || !content} variant="contained"
                        color="primary">
                    Send
                </Button>
            </Buttons>
        </WebSocketForm>
    );
};

export default SendEvent;
