import React, { useState } from 'react';
import SockJS from 'sockjs-client';
import { Stomp } from "@stomp/stompjs";
import {
    Button,
    FormControl,
    Input,
    InputLabel,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    Typography
} from "@mui/material";
import { styled } from '@mui/material/styles';

const WebSocketContainer = styled('div')(({ theme }) => ({
    marginTop: theme.spacing(2),
    width: '100%',
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
}));

const WebSocketForm = styled('div')(({ theme }) => ({
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    marginBottom: theme.spacing(2),
}));

const WebSocketList = () => {
    const [stompClient, setStompClient] = useState<any>(null);
    const [connected, setConnected] = useState<boolean>(false);
    const [content, setContent] = useState<string>('');
    const [authenticationFailureEvents, setAuthenticationFailureEvents] = useState<AuthenticationFailureEvent[]>([]);

    const onConnect = () => {
        const socket = new SockJS('https://localhost:8443/ws');
        const client = Stomp.over(socket);
        client.connect({}, (frame: any) => {
            setConnected(true);
            console.log('Connected: ' + frame);
            client.subscribe('/topic/errors', (event: any) => {
                const authenticationFailureEvent = JSON.parse(event.body) as AuthenticationFailureEvent;
                setAuthenticationFailureEvents(prev => [...prev, authenticationFailureEvent]);
            });
            setStompClient(client);
        });
    };

    const onDisconnect = () => {
        if (stompClient !== null) {
            stompClient.disconnect();
        }
        setConnected(false);
        console.log('Disconnected');
    };

    const onSend = () => {
        if (stompClient !== null) {
            const authenticationFailureEvent = { content, timestamp: new Date().toISOString() } as AuthenticationFailureEvent;
            stompClient.send('/app/newError', {}, JSON.stringify(authenticationFailureEvent));
            setContent('');
        }
    };

    return (
        <WebSocketContainer>
            <Typography variant="h5" component="h1">
                Websocket event failure reader
            </Typography>
            <Button
                id="connect"
                variant="contained"
                color="primary"
                type="button"
                onClick={onConnect}
                disabled={connected}
            >
                Connect
            </Button>
            <FormControl>
                <InputLabel htmlFor="connect">WebSocket connection:</InputLabel>
                <Input id="connect" type="button" onClick={onConnect} disabled={connected} />
                <Input id="disconnect" type="button" onClick={onDisconnect} disabled={!connected} />
            </FormControl>
            <WebSocketForm>
                <FormControl>
                    <InputLabel htmlFor="name">Message</InputLabel>
                    <Input type="text" id="name" placeholder="MessagePlaceholder" value={content} onChange={(e) => setContent(e.target.value)} />
                </FormControl>
                <Button id="send" type="button" onClick={onSend} disabled={!connected || !content} variant="contained" color="primary">
                    Send
                </Button>
            </WebSocketForm>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>Content</TableCell>
                        <TableCell>Timestamp</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {authenticationFailureEvents.map((event) => (
                        <TableRow key={event.content}>
                            <TableCell>Invalid credentials provided for user: {event.content}</TableCell>
                            <TableCell>{event.timestamp}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </WebSocketContainer>
    );
}


export default WebSocketList;


interface AuthenticationFailureEvent {
    content: string;
    timestamp: string;
}
