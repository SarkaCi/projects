import { Routes, Route, useLocation } from "react-router-dom";
import Navbar from "./components/Navbar";
import Home from "./components/Home";
import InsuranceList from "./components/InsuranceList";
import InsuredPersonList from "./components/InsuredPersonList";
import CreateInsurance from "./components/CreateInsurance";
import EditInsurance from "./components/EditInsurance";
import CreateInsuredPerson from "./components/CreateInsuredPerson";
import EditInsuredPerson from "./components/EditInsuredPerson";
import InsuredPersonDetail from "./components/InsuredPersonDetail";
import AssignInsurance from "./components/AssignInsurance";
import EditInsuranceForPerson from "./components/EditInsuranceForPerson";
import { useState, useEffect } from "react";

/**
 * Hlavní komponenta aplikace.
 * - Obsahuje navigaci a správu routování.
 * - Dynamicky detekuje, zda je aplikace zobrazena na mobilním zařízení.
 * - Skryje navbar na domovské stránce.
 */
function App() {
    const [isMobile, setIsMobile] = useState(false);  // Stav pro mobilní zařízení
    const location = useLocation();

    useEffect(() => {
        const handleResize = () => {
            setIsMobile(window.innerWidth <= 768);  // Pokud je šířka okna menší nebo rovna 768px, považujeme to za mobilní
        };

        handleResize();  // Zavoláme funkci při načtení komponenty
        window.addEventListener("resize", handleResize);  // Při každé změně velikosti okna zkontrolujeme šířku

        return () => window.removeEventListener("resize", handleResize);  // Uklidíme posluchače při odmontování komponenty
    }, []);

    return (
        <div>
            {/* Pokud se nacházíme na Home stránce, navbar se nezobrazí */}
            {location.pathname !== "/" && <Navbar />}

            <main className="flex-grow flex justify-center items-center">
                <div className="w-full max-w-full">
                    <Routes>
                        <Route path="/" element={<Home />} />
                        <Route path="/insurance" element={<InsuranceList isMobile={isMobile} />} />
                        <Route path="/insured-persons" element={<InsuredPersonList isMobile={isMobile} />} />
                        <Route path="/create" element={<CreateInsurance />} />
                        <Route path="/edit/:id" element={<EditInsurance />} />
                        <Route path="/insured-persons/create" element={<CreateInsuredPerson />} />
                        <Route path="/insured-persons/edit/:id" element={<EditInsuredPerson />} />
                        <Route path="/insured-persons/:id" element={<InsuredPersonDetail isMobile={isMobile} />} />
                        <Route path="/insured-persons/:id/assign-insurance" element={<AssignInsurance />} />
                        <Route path="/insured-persons/:id/edit-insurance/:insuranceId" element={<EditInsuranceForPerson />} />
                    </Routes>
                </div>
            </main>
        </div>
    );
}

export default App;
