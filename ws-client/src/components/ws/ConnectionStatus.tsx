import React from 'react';
import {InputLabel} from '@mui/material';
import {Circle} from "@mui/icons-material";
import {ConnectionStatusContainer} from "../../styles/styles";

type Props = {
    connected: boolean;
};

const ConnectionStatus: React.FC<Props> = ({connected}) => {
    return (<ConnectionStatusContainer>
            <InputLabel htmlFor="connect">WebSocket connection:</InputLabel>
            <Circle color={connected ? 'primary' : 'error'}/>
        </ConnectionStatusContainer>
    );
};

export default ConnectionStatus;
