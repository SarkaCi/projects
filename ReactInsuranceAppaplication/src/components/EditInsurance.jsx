import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";

/**
 * Komponenta pro editaci pojištění.
 * - Načítá údaje o pojištění z API na základě ID.
 * - Umožňuje upravit typ a částku pojištění.
 * - Číslo pojistky je pouze pro čtení (generované automaticky).
 */
const EditInsurance = () => {
    const { id } = useParams(); // Získání ID z URL
    const navigate = useNavigate();

    // Stav pro uchování údajů o pojištění
    const [insurance, setInsurance] = useState({
        policyNumber: "",
        type: "",
        amount: "",
    });

    /**
     * Načítání dat při načtení stránky.
     */
    useEffect(() => {
        console.log(`📡 Volám API: /api/insurances/${id}`);
        fetch(`/api/insurances/${id}`)
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Pojištění nenalezeno");
                }
                return response.json();
            })
            .then((data) => {
                console.log("📩 Data z API:", data);
                setInsurance(data);
            })
            .catch((error) => {
                console.error("🚨 Chyba při načítání pojištění:", error);
                alert("Pojištění nenalezeno.");
                navigate("/insurance"); // Přesměrování zpět na hlavní stránku
            });
    }, [id, navigate]);

    /**
     * Odeslání upravených dat na server.
     */
    const handleSubmit = async (event) => {
        event.preventDefault();

        try {
            console.log("📤 Odesílám data:", insurance);
            const response = await fetch(`/api/insurances/${id}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(insurance),
            });

            if (response.ok) {
                navigate("/insurance"); // Přesměrování zpět na seznam
            } else {
                alert("🚨 Chyba při ukládání změn.");
            }
        } catch (error) {
            console.error("🚨 Chyba při odesílání dat:", error);
        }
    };

    return (
        <div className="max-w-3xl mx-auto my-20 p-6 bg-white shadow-lg rounded-lg border border-gray-200">
            <h2 className="text-3xl font-bold text-center text-gray-800 mb-6">Editace pojištění</h2>

            <form onSubmit={handleSubmit} className="space-y-4">
                {/* Číslo pojistky */}
                <div>
                    <label className="block text-gray-700 font-medium">Číslo pojistky</label>
                    <input
                        type="text"
                        className="w-full p-2 border border-gray-300 rounded-md bg-gray-100 cursor-not-allowed"
                        value={insurance.policyNumber}
                        readOnly
                    />
                </div>

                {/* Typ pojištění */}
                <div>
                    <label className="block text-gray-700 font-medium">Typ pojištění</label>
                    <input
                        type="text"
                        className="w-full p-2 border border-gray-300 rounded-md"
                        value={insurance.type}
                        onChange={(e) => setInsurance({ ...insurance, type: e.target.value })}
                        required
                    />
                </div>

                {/* Částka pojištění */}
                <div>
                    <label className="block text-gray-700 font-medium">Částka (Kč)</label>
                    <input
                        type="number"
                        className="w-full p-2 border border-gray-300 rounded-md"
                        value={insurance.amount}
                        onChange={(e) => setInsurance({ ...insurance, amount: e.target.value })}
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
                        onClick={() => navigate("/insurance")}
                    >
                        Zrušit
                    </button>
                </div>
            </form>
        </div>
    );
};

export default EditInsurance;
