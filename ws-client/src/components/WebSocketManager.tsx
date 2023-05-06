import React, {useState} from 'react';
import SockJS from 'sockjs-client';
import {Stomp} from "@stomp/stompjs";
import {authenticate} from "./Authentication";
import AuthenticationFailureEvent from "./AuthenticationFailureEvent";
import SendEvent from "./ws/SendEvent";
import {SendEventContainer, WebSocketContainer} from "../styles/styles";
import EventsTable from "./ws/EventsTable";
import ConnectionStatus from "./ws/ConnectionStatus";
import WebSocketButtons from "./ws/WebSocketButtons";
import {useAppDispatch, useAppSelector} from "./state/hooks";


const WebSocketManager = () => {
    const [stompClient, setStompClient] = useState<any>(null);
    const [connected, setConnected] = useState<boolean>(false);
    const [authenticationFailureEvents, setAuthenticationFailureEvents] = useState<AuthenticationFailureEvent[]>([]);
    const wsUrl = process.env.REACT_APP_BNAUTH_URL + ":8443/ws";

    const oauthToken = useAppSelector(state => state.auth.accessToken)
    const dispatch = useAppDispatch();

    const onConnect = () => {
        const headers = {
            'Authorization': `Bearer ${oauthToken}`
        };

        const socket = new SockJS(wsUrl!);
        const client = Stomp.over(socket);

        client.connect(headers, (frame: any) => {
            setConnected(true);
            console.log('Connected: ' + frame);

            client.subscribe('/topic/errors', (event: any) => {
                const authenticationFailureEvent = JSON.parse(event.body) as AuthenticationFailureEvent;
                makeEventText(authenticationFailureEvent)
                makeEventDateText(authenticationFailureEvent);
                setAuthenticationFailureEvents(prev => [...prev, authenticationFailureEvent]);
            });
            setStompClient(client);
        });
    };

    const makeEventDateText = (event: AuthenticationFailureEvent) => {
        const datetime = event.timestamp.split("T")
        const date = datetime[0];
        const time = datetime[1].split(".")[0];
        event.timestamp = date + " / " + time;
    }

    const makeEventText = (event: AuthenticationFailureEvent) => {
        event.content = "Authentication failure: " + event.content;
    }

    const onDisconnect = () => {
        if (stompClient !== null) {
            stompClient.disconnect();
        }

        setAuthenticationFailureEvents([])
        setConnected(false);
        console.log('Disconnected');
    };

    //todo: EventsTable can't have this css...
    return (<>
            <EventsTable events={authenticationFailureEvents}/>
            <WebSocketContainer>
                <WebSocketButtons
                    onConnect={onConnect}
                    onDisconnect={onDisconnect}
                    onWrongLoginEvent={() => {
                        authenticate("usera", dispatch);
                    }}
                    connected={connected}
                />
                <ConnectionStatus connected={connected}/>
                {(connected && oauthToken) && (
                    <SendEventContainer>
                        <SendEvent stompClient={stompClient} connected={connected}/>
                    </SendEventContainer>
                )}
            </WebSocketContainer>
        </>

    );
}


export default WebSocketManager;
