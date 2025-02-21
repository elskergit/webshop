import { createContext, useContext, useEffect, useMemo, useState } from "react";
import { getRequest } from "../commonServices.jsx";
import PropTypes from "prop-types";
import { ParcelsContext } from "../parcels/context.jsx";

export const AddParcelContext = createContext();


export function AddParcelContextProvider({ children }) {
    const { setParcels } = useContext(ParcelsContext);
    const [ parcelData, setParcelData ] = useState({
        customerName: "",
        pickUpAddress: "",
        pickUpDate: "",
        destinationAddress: "",
        serviceTier: "",
        width: "",
        height: "",
        weight: "",
    });

    const handleAddParcel = async (parcelData) => {
        postRequest(parcelData)
            .then((result) => {
                setParcels((prevParcels) => [...prevParcels, result]);
            })
            .catch((error) => {
                setError(error);
            })
    }

    return (
        <AddParcelContext.Provider value={contextValue}>
            {children}
        </AddParcelContext.Provider>
    );
}

AddParcelContextProvider.propTypes = {
    children: PropTypes.node.isRequired,
}


