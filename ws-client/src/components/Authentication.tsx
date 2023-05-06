import React from 'react';

import { setAccessToken } from './state/authFragment';
import {Buttons} from "../styles/styles";
import {AppDispatch} from "./state/store";
import {useAppDispatch, useAppSelector} from "./state/hooks";
import {
    Button,
} from "@mui/material";

export const authenticate = async (pass: string, dispatch: AppDispatch) => {
    const authUrl = process.env.REACT_APP_BNAUTH_URL + ":8443/v1/authenticate";
    const myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    const raw = JSON.stringify({
        "login": "user",
        "password": pass
    });

    const requestOptions: RequestInit = {
        method: 'POST',
        headers: myHeaders,
        body: raw,
        redirect: 'follow',
        mode: 'cors'
    };

    return await fetch(authUrl, requestOptions)
        .then(response => response.json())
        .then(result => {
            dispatch(setAccessToken(result['accessToken']))
            console.log("Logged in")
        })
        .catch(error => console.log('Error logging in: ', error));
}

const AuthButton = () => {
    const dispatch = useAppDispatch();
    const oauthToken = useAppSelector(state => state.auth.accessToken)
    const isAuthenticated = Boolean(oauthToken)

    return (
        <Buttons sx={{marginTop: '3vw'}}>
            <Button
                id="auth"
                variant="contained"
                color="error"
                type="button"
                onClick={() => authenticate("user", dispatch)}
                disabled={isAuthenticated}
            >
                OAuth2 https login
            </Button>
        </Buttons>
    );
}


export default AuthButton;
