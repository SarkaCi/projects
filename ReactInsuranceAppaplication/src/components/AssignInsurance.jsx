import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

/**
 * Komponenta pro přiřazení pojištění k pojištěné osobě.
 * - Načítá seznam dostupných pojištění.
 * - Umožňuje uživateli vybrat pojištění a zadat platnost.
 * - Odesílá data na server pomocí API požadavku.
 * - Po úspěšném přiřazení přesměruje zpět na detail osoby.
 */
const AssignInsurance = () => {
    const { id } = useParams(); // ID osoby
    const navigate = useNavigate();
    const [insurances, setInsurances] = useState([]);
    const [selectedInsurance, setSelectedInsurance] = useState("");
    const [validFrom, setValidFrom] = useState("");
    const [validTo, setValidTo] = useState("");

    /**
     * Načítání seznamu pojištění při načtení stránky.
     */
    useEffect(() => {
        fetch("/api/insurances")
            .then(response => response.json())
            .then(data => setInsurances(data))
            .catch(error => console.error("Chyba při načítání pojištění:", error));
    }, []);

    /**
     * Odeslání formuláře na server pro přiřazení pojištění osobě.
     */
    const handleSubmit = (e) => {
        e.preventDefault();

        fetch(`/api/insured-persons/${id}/assign-insurance/${selectedInsurance}`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                validFrom,
                validTo
            }),
        })
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error("Chyba při přiřazování pojištění");
                }
            })
            .then(() => {
                navigate(`/insured-persons/${id}`); // ✅ Po přiřazení přesměrování zpět
            })
            .catch(error => console.error("Chyba při přiřazování pojištění:", error));
    };

    return (
        <div className="max-w-3xl mx-auto my-20 p-6 bg-white shadow-lg rounded-lg border border-gray-200">
            <h2 className="text-3xl font-bold text-center text-gray-800 mb-6">Přidat pojištění</h2>

            <form onSubmit={handleSubmit} className="space-y-4">
                {/* Výběr pojištění */}
                <div>
                    <label className="block text-gray-700 font-medium">Vyber pojištění</label>
                    <select
                        className="w-full p-2 border border-gray-300 rounded-md bg-white"
                        value={selectedInsurance}
                        onChange={(e) => setSelectedInsurance(e.target.value)}
                        required
                    >
                        <option value="">-- Vyber pojištění --</option>
                        {insurances.map((insurance) => (
                            <option key={insurance.id} value={insurance.id}>
                                {insurance.policyNumber} - {insurance.type} ({insurance.amount} Kč)
                            </option>
                        ))}
                    </select>
                </div>

                {/* Platnost od */}
                <div>
                    <label className="block text-gray-700 font-medium">Platnost od</label>
                    <input
                        type="date"
                        className="w-full p-2 border border-gray-300 rounded-md"
                        value={validFrom}
                        onChange={(e) => setValidFrom(e.target.value)}
                        required
                    />
                </div>

                {/* Platnost do */}
                <div>
                    <label className="block text-gray-700 font-medium">Platnost do</label>
                    <input
                        type="date"
                        className="w-full p-2 border border-gray-300 rounded-md"
                        value={validTo}
                        onChange={(e) => setValidTo(e.target.value)}
                        required
                    />
                </div>

                {/* Tlačítka */}
                <div className="flex justify-between">
                    <button
                        type="submit"
                        className="bg-green-600 text-white px-4 py-2 rounded-lg shadow-md hover:bg-green-700 transition"
                    >
                        Přidat
                    </button>
                    <button
                        type="button"
                        className="bg-gray-600 text-white px-4 py-2 rounded-lg shadow-md hover:bg-gray-700 transition"
                        onClick={() => navigate(`/insured-persons/${id}`)}
                    >
                        Zrušit
                    </button>
                </div>
            </form>
        </div>
    );
};

export default AssignInsurance;
