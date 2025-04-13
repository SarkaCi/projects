import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { PlusCircle} from "lucide-react"; // Moderní ikony

/**
 * Komponenta pro zobrazení seznamu pojištění.
 * - Načítá data ze serveru (`/api/insurances`).
 * - Umožňuje mazání pojištění.
 * - Automaticky přepíná mezi tabulkovým zobrazením (desktop) a kartami (mobilní zařízení).
 *
 * @param {boolean} isMobile - Určuje, zda se komponenta zobrazí v mobilním režimu.
 */
const InsuranceList = ({ isMobile }) => {
    const [insurances, setInsurances] = useState([]);

    useEffect(() => {
        fetch("/api/insurances")
            .then(response => response.json())
            .then(data => setInsurances(data))
            .catch(error => console.error("Chyba při načítání pojištění:", error));
    }, []);

    /**
     * Funkce pro smazání pojištění.
     * - Po potvrzení uživatelem odešle DELETE požadavek na server.
     * - Aktualizuje seznam pojištění v UI.
     *
     * @param {number} id - ID pojištění k odstranění.
     */
    const handleDelete = (id) => {
        if (window.confirm("Opravdu chceš smazat toto pojištění?")) {
            fetch(`/api/insurances/${id}`, { method: "DELETE" })
                .then(response => {
                    if (response.ok) {
                        setInsurances(insurances.filter(insurance => insurance.id !== id));
                    } else {
                        alert("Chyba při mazání pojištění.");
                    }
                })
                .catch(error => console.error("Chyba při mazání pojištění:", error));
        }
    };

    return (
        <div className="max-w-6xl mx-auto p-6">
            <h2 className="text-3xl font-bold text-center text-gray-800 mb-6">Seznam pojištění</h2>

            {/* Tlačítko pro přidání pojištění */}
            <div className="flex justify-end mb-4">
                <Link to="/create" className="flex items-center gap-2 bg-green-600 text-white px-4 py-2 rounded-lg shadow-md hover:bg-green-700 transition">
                    <PlusCircle size={20} /> Přidat pojištění
                </Link>
            </div>

            {/* Dynamické zobrazení karet pro mobilní zařízení */}
            {isMobile ? (
                <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4 mt-6">
                    {insurances.map(insurance => (
                        <div key={insurance.id} className="bg-white shadow-md rounded-lg p-4">
                            <h3 className="text-xl font-semibold text-gray-800">{insurance.policyNumber}</h3>
                            <p>{insurance.type}</p>
                            <p>{insurance.amount} Kč</p>
                            <div className="mt-4 flex justify-between gap-2">
                                <Link to={`/edit/${insurance.id}`} className="bg-yellow-500 text-white px-4 py-2 rounded-md">Editovat</Link>
                                <button onClick={() => handleDelete(insurance.id)} className="bg-red-600 text-white px-4 py-2 rounded-md">Smazat</button>
                            </div>
                        </div>
                    ))}
                </div>
            ) : (
                <div className="overflow-x-auto bg-white shadow-lg rounded-lg mt-6">
                    <table className="w-full border-collapse">
                        <thead>
                        <tr className="bg-gray-800 text-white">
                            <th className="p-3 text-left">Číslo pojistky</th>
                            <th className="p-3 text-left">Typ</th>
                            <th className="p-3 text-left">Částka (Kč)</th>
                            <th className="p-3 text-center">Akce</th>
                        </tr>
                        </thead>
                        <tbody>
                        {insurances.length > 0 ? (
                            insurances.map(insurance => (
                                <tr key={insurance.id} className="border-b hover:bg-gray-100 transition">
                                    <td className="p-3">{insurance.policyNumber}</td>
                                    <td className="p-3">{insurance.type}</td>
                                    <td className="p-3">{insurance.amount}</td>
                                    <td className="p-3 flex justify-center gap-2">
                                        <Link to={`/edit/${insurance.id}`} className="bg-yellow-500 text-white px-4 py-2 rounded-md">Editovat</Link>
                                        <button onClick={() => handleDelete(insurance.id)} className="bg-red-600 text-white px-4 py-2 rounded-md">Smazat</button>
                                    </td>
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan="4" className="p-4 text-center text-gray-500">Žádná pojištění</td>
                            </tr>
                        )}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
};

export default InsuranceList;
