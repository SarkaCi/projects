import { useState } from "react";
import { useNavigate } from "react-router-dom";

/**
 * Komponenta pro vytvoření nového pojištění.
 * - Umožňuje uživateli zadat číslo pojistky, typ pojištění a částku.
 * - Odesílá data na server pomocí API požadavku.
 * - Po úspěšném vytvoření přesměruje zpět na seznam pojištění.
 */
const CreateInsurance = () => {
    const [policyNumber, setPolicyNumber] = useState("");
    const [type, setType] = useState("");
    const [amount, setAmount] = useState("");
    const navigate = useNavigate(); // Pro přesměrování po úspěšném vytvoření

    /**
     * Odeslání formuláře na server.
     */
    const handleSubmit = async (event) => {
        event.preventDefault();
        const insurance = { policyNumber, type, amount: parseFloat(amount) };

        try {
            const response = await fetch("/api/insurances", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(insurance),
            });

            if (response.ok) {
                navigate("/insurance"); // Po úspěšném vytvoření přesměruje zpět na seznam
            } else {
                alert("Chyba při vytváření pojištění.");
            }
        } catch (error) {
            console.error("Chyba při odesílání dat:", error);
        }
    };

    return (
        <div className="max-w-3xl mx-auto my-20 p-6 bg-white shadow-lg rounded-lg border border-gray-200">
            <h2 className="text-3xl font-bold text-center text-gray-800 mb-6">Vytvořit pojištění</h2>

            <form onSubmit={handleSubmit} className="space-y-4">
                {/* Číslo pojistky */}
                <div>
                    <label className="block text-gray-700 font-medium">Číslo pojistky</label>
                    <input
                        type="text"
                        className="w-full p-2 border border-gray-300 rounded-md"
                        value={policyNumber}
                        onChange={(e) => setPolicyNumber(e.target.value)}
                        required
                    />
                </div>

                {/* Typ pojištění */}
                <div>
                    <label className="block text-gray-700 font-medium">Typ pojištění</label>
                    <input
                        type="text"
                        className="w-full p-2 border border-gray-300 rounded-md"
                        value={type}
                        onChange={(e) => setType(e.target.value)}
                        required
                    />
                </div>

                {/* Částka */}
                <div>
                    <label className="block text-gray-700 font-medium">Částka (Kč)</label>
                    <input
                        type="number"
                        className="w-full p-2 border border-gray-300 rounded-md"
                        value={amount}
                        onChange={(e) => setAmount(e.target.value)}
                        required
                    />
                </div>

                {/* Tlačítka */}
                <div className="flex justify-between">
                    <button
                        type="submit"
                        className="bg-green-600 text-white px-4 py-2 rounded-lg shadow-md hover:bg-green-700 transition"
                    >
                        Vytvořit
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

export default CreateInsurance;
