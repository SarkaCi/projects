import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import {UserPlus } from "lucide-react";  // Moderní ikony

/**
 * Komponenta pro zobrazení seznamu pojištěných osob.
 * - Načítá data ze serveru (`/api/insured-persons`).
 * - Umožňuje mazání pojištěných osob.
 * - Automaticky přepíná mezi tabulkovým zobrazením (desktop) a kartami (mobilní zařízení).
 *
 * @param {boolean} isMobile - Určuje, zda se komponenta zobrazí v mobilním režimu.
 */
const InsuredPersonList = ({ isMobile }) => {
    const [insuredPersons, setInsuredPersons] = useState([]);

    useEffect(() => {
        fetch("/api/insured-persons")
            .then(response => response.json())
            .then(data => setInsuredPersons(data))
            .catch(error => console.error("Chyba při načítání osob:", error));
    }, []);

    /**
     * Funkce pro smazání pojištěné osoby.
     * - Po potvrzení uživatelem odešle DELETE požadavek na server.
     * - Aktualizuje seznam pojištěných osob v UI.
     *
     * @param {number} id - ID pojištěné osoby k odstranění.
     */
    const handleDelete = (id) => {
        if (window.confirm("Opravdu chcete smazat tuto osobu?")) {
            fetch(`/api/insured-persons/${id}`, { method: "DELETE" })
                .then(() => setInsuredPersons(insuredPersons.filter(p => p.id !== id)))
                .catch(error => console.error("Chyba při mazání osoby:", error));
        }
    };

    return (
        <div className="max-w-6xl mx-auto p-6">
            <h2 className="text-3xl font-bold text-center text-gray-800 mb-6">Seznam pojištěných osob</h2>

            {/* Tlačítko pro přidání osoby */}
            <div className="flex justify-end mb-4">
                <Link
                    to="/insured-persons/create"
                    className="flex items-center gap-2 bg-green-600 text-white px-4 py-2 rounded-lg shadow-md hover:bg-green-700 transition">
                    <UserPlus size={20} /> Přidat osobu
                </Link>
            </div>

            {/* Dynamické zobrazení karet pro mobilní zařízení */}
            {isMobile ? (
                <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4 mt-6">
                    {insuredPersons.map(person => (
                        <div key={person.id} className="bg-white shadow-md rounded-lg p-4">
                            <h3 className="text-xl font-semibold text-gray-800">{person.firstName} {person.lastName}</h3>
                            <p>{person.email}</p>
                            <p>{person.phone}</p>
                            <div className="mt-4 flex justify-between gap-2">
                                <Link to={`/insured-persons/${person.id}`} className="bg-blue-600 text-white px-4 py-2 rounded-md">Detail</Link>
                                <Link to={`/insured-persons/edit/${person.id}`} className="bg-yellow-500 text-white px-4 py-2 rounded-md">Editovat</Link>
                                <button onClick={() => handleDelete(person.id)} className="bg-red-600 text-white px-4 py-2 rounded-md">Smazat</button>
                            </div>
                        </div>
                    ))}
                </div>
            ) : (
                <div className="overflow-x-auto bg-white shadow-lg rounded-lg mt-6">
                    <table className="w-full border-collapse">
                        <thead>
                        <tr className="bg-gray-800 text-white">
                            <th className="p-3 text-left">ID</th>
                            <th className="p-3 text-left">Jméno</th>
                            <th className="p-3 text-left">Email</th>
                            <th className="p-3 text-left">Telefon</th>
                            <th className="p-3 text-center">Akce</th>
                        </tr>
                        </thead>
                        <tbody>
                        {insuredPersons.map(person => (
                            <tr key={person.id} className="border-b hover:bg-gray-100 transition">
                                <td className="p-3">{person.id}</td>
                                <td className="p-3">{person.firstName} {person.lastName}</td>
                                <td className="p-3">{person.email}</td>
                                <td className="p-3">{person.phone}</td>
                                <td className="p-3 flex justify-center gap-2">
                                    <Link to={`/insured-persons/${person.id}`} className="bg-blue-600 text-white px-4 py-2 rounded-md">Detail</Link>
                                    <Link to={`/insured-persons/edit/${person.id}`} className="bg-yellow-500 text-white px-4 py-2 rounded-md">Editovat</Link>
                                    <button onClick={() => handleDelete(person.id)} className="bg-red-600 text-white px-4 py-2 rounded-md">Smazat</button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
};

export default InsuredPersonList;
