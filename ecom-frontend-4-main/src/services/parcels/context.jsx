import { createContext, useEffect, useMemo, useState } from "react";
import { getRequest } from "../commonServices.jsx";
import PropTypes from "prop-types";

export const ParcelsContext = createContext();

export function ParcelsContextProvider({ children }) {
    const [parcels, setParcels] = useState([]);
    const [error, setError] = useState(null);

    const getParcels = () => {
        getRequest('parcels')
            .then((result) => {
                setParcels(result)
            })
            .catch((error) => {
                setError(error);
            });
    };

    useEffect(() => {
        getParcels();
    }, []);

    const contextValue = useMemo(() => ({
        parcels,
        error
    }), [parcels, error]);

    return (
        <ParcelsContext.Provider value={contextValue}>
            {children}
        </ParcelsContext.Provider>
    );
}

ParcelsContextProvider.propTypes = {
    children: PropTypes.node.isRequired,
};