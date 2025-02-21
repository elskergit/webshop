import { ParcelsContextProvider } from "../../services/parcels/context";


export function AddParcel() {

  
  return (
    <ParcelsContextProvider>
      <h1> Add Parcel </h1>
    </ParcelsContextProvider>
  );
}