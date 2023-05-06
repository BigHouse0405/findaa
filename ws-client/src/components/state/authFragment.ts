import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import {RootState} from "./store";

interface AuthState {
    accessToken: string | null;
}

const initialState: AuthState = {
    accessToken: null,
};

export const authSlice = createSlice({
    name: 'auth',
    initialState,
    reducers: {
        setAccessToken(state, action: PayloadAction<string>) {
            state.accessToken = action.payload;
        },
        clearAccessToken(state) {
            state.accessToken = null;
        },
    },
});

export const { setAccessToken, clearAccessToken } = authSlice.actions;

export const selectIsAuthenticated = (state: RootState) => state.auth.accessToken !== null;

export default authSlice.reducer;
