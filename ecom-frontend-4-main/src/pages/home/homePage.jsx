import { ParcelsContextProvider } from "../../services/parcels/context";

export function HomePage() {


    return (
        <ParcelsContextProvider>
          <h1> Home page </h1>
        </ParcelsContextProvider>
      );
}