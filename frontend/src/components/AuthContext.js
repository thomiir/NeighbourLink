import { createContext, useState, useContext, useEffect } from "react";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [isLoggedIn, setIsLoggedIn] = useState(() => {
        return sessionStorage.getItem('isLoggedIn') === 'true';
    });

    const [username, setUsername] = useState(() => {
        return sessionStorage.getItem('username') || "";
    });

    const login = (user, isAuthenticated) => {
        console.log("Login function called with:", { user, isAuthenticated });
        setUsername(user);
        setIsLoggedIn(isAuthenticated);
        sessionStorage.setItem('username', user);
        sessionStorage.setItem('isLoggedIn', isAuthenticated.toString());
    };

    const logout = () => {
        setUsername("");
        setIsLoggedIn(false);
        sessionStorage.removeItem('username');
        sessionStorage.removeItem('isLoggedIn');
    };

    useEffect(() => {
        console.log("Auth state updated:", { isLoggedIn, username });
    }, [isLoggedIn, username]);

    return (
        <AuthContext.Provider value={{
            isLoggedIn,
            setIsLoggedIn,
            username,
            setUsername,
            login,
            logout
        }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);