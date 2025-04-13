import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";


/**
 * Komponenta pro editaci platnosti pojištění u konkrétní pojištěné osoby.
 * - Načítá existující údaje o pojištění ze serveru.
 * - Umožňuje změnu platnosti pojištění (od/do).
 * - Umožňuje uživateli uložit změny nebo zrušit editaci.
 */
const EditInsuranceForPerson = () => {
    const { id, insuranceId } = useParams(); // ID osoby a ID pojištění
    const navigate = useNavigate();
    const [insurance, setInsurance] = useState(null);
    const [validFrom, setValidFrom] = useState("");
    const [validTo, setValidTo] = useState("");

    /**
     * Načítání dat pojištění při načtení komponenty.
     */
    useEffect(() => {
        fetch(`/api/insurances/${insuranceId}`)
            .then(response => response.json())
            .then(data => {
                setInsurance(data);
                setValidFrom(data.validFrom || "");
                setValidTo(data.validTo || "");
            })
            .catch(error => console.error("Chyba při načítání pojištění:", error));
    }, [insuranceId]);

    /**
     * Odeslání upravených dat na server.
     */
    const handleSubmit = (e) => {
        e.preventDefault();

        fetch(`/api/insurances/${insuranceId}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                ...insurance,
                validFrom,
                validTo
            }),
        })
            .then(response => {
                if (response.ok) {
                    navigate(`/insured-persons/${id}`);
                } else {
                    alert("Chyba při aktualizaci pojištění");
                }
            })
            .catch(error => console.error("Chyba při aktualizaci pojištění:", error));
    };

    if (!insurance) return <p className="text-center text-gray-600 mt-10">Načítání...</p>;

    return (
        <div className="max-w-3xl mx-auto my-20 p-6 bg-white shadow-lg rounded-lg border border-gray-200">
            <h2 className="text-3xl font-bold text-center text-gray-800 mb-6">Editace pojištění</h2>

            <form onSubmit={handleSubmit} className="space-y-4">
                {/* Typ pojištění */}
                <div>
                    <label className="block text-gray-700 font-medium">Typ pojištění</label>
                    <input
                        type="text"
                        className="w-full p-2 border border-gray-300 rounded-md bg-gray-100 cursor-not-allowed"
                        value={insurance.type}
                        readOnly
                    />
                </div>

                {/* Částka */}
                <div>
                    <label className="block text-gray-700 font-medium">Částka</label>
                    <input
                        type="number"
                        className="w-full p-2 border border-gray-300 rounded-md bg-gray-100 cursor-not-allowed"
                        value={insurance.amount}
                        readOnly
                    />
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
                        className="bg-blue-600 text-white px-4 py-2 rounded-lg shadow-md hover:bg-blue-700 transition"
                    >
                        Uložit změny
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

export default EditInsuranceForPerson;
